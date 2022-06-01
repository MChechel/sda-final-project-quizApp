package com.teamA.session;


import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

@Component
public class SessionRegistry {
    private static final HashMap<String, String> SESSIONS = new HashMap<>();

    public String registerSession(final String username){
        //System.out.println("SESSIONS INPUT - id: " + sessionId + " username:"+username);

        if(username == null){
            throw new RuntimeException("Username need to be provided!");
        }
        String sessionId = generateSessionId();
        SESSIONS.put(sessionId, username);
        System.out.println("SESSIONS INPUT - id: " + sessionId + " username:"+username);
        return sessionId;
    }

    public String getUsernameForSession(final String sessionId){
        return SESSIONS.get(sessionId);
    }




    private String generateSessionId(){
        return new String(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8)));
    }
}
