package com.example;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class TxnService {

    private final String TXN_INITIATED_TOPIC = "txn-initiated";

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    TxnRepository txnRepository;

    @Autowired
    JSONParser jsonParser;

    public String initiate(InitiateTxnRequestDTO request) {

        Txn txn = request.to();
        txn = this.txnRepository.save(txn);

        String data = objectMapper.writeValueAsString(txn);

        this.kafkaTemplate.send(TXN_INITIATED_TOPIC, data);

        return txn.getExtTxnId();
    }

    @KafkaListener(topics = "wallet-updated", groupId = "txnGrp1")
    public void updateTxnOnWalletUpdate(String msg) throws ParseException {

        JSONObject data = (JSONObject) this.jsonParser.parse(msg);
        String walletStatus = (String) data.get("status");
        String extTxnId = (String) data.get("extTxnId");

        Txn txn = this.txnRepository.findByExtTxnId(extTxnId);

        if(walletStatus.equals("SUCCESS")){
            txn.setTxnStatus(TxnStatus.SUCCESSFUL);
        }else{
            txn.setTxnStatus(TxnStatus.FAILED);
        }

        this.txnRepository.save(txn); // this save will act like an update since we have the primary key in the txn object
    }
}
