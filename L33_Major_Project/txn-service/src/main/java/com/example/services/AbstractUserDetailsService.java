package com.example.services;

import com.example.models.UserResponse;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbstractUserDetailsService implements UserDetailsService {

    private ObjectMapper objectMapper = new ObjectMapper();

    private Logger logger = LoggerFactory.getLogger(AbstractUserDetailsService.class);

    RestTemplate restTemplate = new RestTemplate();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        logger.info("Loading UserDetails for {}", username);

        try {

            // TODO: Make an API call to the user service
            ResponseEntity<JSONObject> responseEntity = restTemplate.getForEntity(
                    "http://localhost:8080/users/internal/" + username,
                    JSONObject.class
            );

            JSONObject data = responseEntity.getBody();


            ArrayList<LinkedHashMap<String, String>> al =  (ArrayList<LinkedHashMap<String, String>>) data.get("authorities");

            List<GrantedAuthority> authorities = al.stream()
                    .map(x -> new SimpleGrantedAuthority((String)x.get("authority")))
                    .collect(Collectors.toList());

            UserResponse userResponse = UserResponse.builder()
                    .username(data.get("username").toString())
                    .password(data.get("password").toString())
                    .authorities(authorities)
                    .userId((Integer) data.get("id"))
                    .build();

            return userResponse;

//            return objectMapper.convertValue(responseEntity.getBody(), UserResponse.class);



//            logger.info("UserDetails -  {}", responseEntity.getBody());
//
//            return responseEntity.getBody();
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;


    }
}
