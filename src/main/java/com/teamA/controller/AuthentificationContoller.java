package com.teamA.controller;

import com.teamA.dtos.ResponseDTO;
import com.teamA.dtos.UserDTO;
import com.teamA.session.SessionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class AuthentificationContoller {

    @Autowired
    public AuthenticationManager manager;
    @Autowired
    public SessionRegistry sessionRegistry;

    @CrossOrigin
    @PostMapping
    public ResponseEntity<ResponseDTO> login(@RequestBody UserDTO user){
        System.out.println("Password and username before authentification:" + user.getPassword() +" - " + user.getPassword()  );

        manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword())
        );
        System.out.println("Password na dsername after authentification" + user.getPassword() +" - " + user.getPassword()  );

        final String sessionId = sessionRegistry.registerSession(user.getUsername());
        ResponseDTO response = new ResponseDTO();
        response.setSessionId(sessionId);
        return ResponseEntity.ok(response);
            }
}
