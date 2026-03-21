package com.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class WalletService {

    private final String WALLET_UPDATED_TOPIC = "wallet-updated";

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    JSONParser jsonParser;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Value("${wallet.promotional.balance}")
    Long promotionalBalance;

    public Wallet get(Integer id){
        return walletRepository.findById(id).orElse(null);
    }

    @KafkaListener(topics = "user-created", groupId = "wallet-grp1")
    public void create(String msg) throws ParseException {

        JSONObject data = (JSONObject) this.jsonParser.parse(msg);
        String name = (String) data.get("name");
        Integer userId = ((Long) data.get("id")).intValue();

        Wallet wallet = Wallet.builder()
                .name(name)
                .balance(this.promotionalBalance)
                .userId(userId)
                .currency(Currency.INR)
                .build();

        this.walletRepository.save(wallet);

        //TODO: Publish a kafka event on successful wallet creation so that notification service can
        // listen from it and send out relevant emails to the end users
    }


    @KafkaListener(topics = "txn-initiated", groupId = "wallet-grp2")
    public void updateOnTxnInitiation(String msg) throws ParseException {
        JSONObject data = (JSONObject) this.jsonParser.parse(msg);

        Integer senderId = ((Long) data.get("senderId")).intValue();
        Integer receiverId = ((Long) data.get("receiverId")).intValue();
        Long amount = (Long) data.get("amount");
        String extTxnId = (String) data.get("extTxnId");


        Wallet senderWallet = walletRepository.findByUserId(senderId);
        Wallet receiverWallet = walletRepository.findByUserId(receiverId);

        JSONObject walletUpdatedData = new JSONObject();
        walletUpdatedData.put("senderWalletId", senderWallet.getId());
        walletUpdatedData.put("receiverWalletId", receiverWallet.getId());
        walletUpdatedData.put("amount", amount);
        walletUpdatedData.put("extTxnId", extTxnId);
        walletUpdatedData.put("senderId", senderId);
        walletUpdatedData.put("receiverId", receiverId);

        if(senderWallet == null || senderWallet.getBalance() < amount){
            //TODO: Publish a kafka event on the topic wallet_updated and we need to send the
            // wallet update status as FAILED
            walletUpdatedData.put("status", "FAILED"); // this is not the txn status, this is the status of wallet updates
            walletUpdatedData.put("failureReason", "Sender wallet is either not present or balance is insufficient");
            this.kafkaTemplate.send(WALLET_UPDATED_TOPIC, this.objectMapper.writeValueAsString(walletUpdatedData));
            return;
        }


        if(receiverWallet == null){
            //TODO: Publish a kafka event on the topic wallet_updated and we need to send the
            //      wallet update status as FAILED
            walletUpdatedData.put("status", "FAILED"); // this is not the txn status, this is the status of wallet updates
            walletUpdatedData.put("failureReason", "Receiver wallet is not present");
            this.kafkaTemplate.send(WALLET_UPDATED_TOPIC, this.objectMapper.writeValueAsString(walletUpdatedData));
            return;
        }

        senderWallet.setBalance(senderWallet.getBalance() - amount);
        receiverWallet.setBalance(receiverWallet.getBalance() + amount);

//        walletRepository.save(senderWallet);
//        walletRepository.save(receiverWallet);


        walletRepository.saveAll(List.of(senderWallet,receiverWallet)); //

        //TODO: Publish a kafka event on the topic wallet_updated with status as success so that
        // txn service can listen and completes the txn

        walletUpdatedData.put("status", "SUCCESS"); // this is not the txn status, this is the status of wallet updates
        this.kafkaTemplate.send(WALLET_UPDATED_TOPIC, this.objectMapper.writeValueAsString(walletUpdatedData));

    }

}
