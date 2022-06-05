package com.teamA.controller;

import com.teamA.dtos.ResponseDTO;
import com.teamA.dtos.UserDTO;
import com.teamA.model.User;
import com.teamA.service.UserService;
import com.teamA.session.SessionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @Autowired
    private UserService userServiceImplementation;


    @CrossOrigin
    @PostMapping
    public ResponseEntity<ResponseDTO> login(@RequestBody UserDTO user){

        manager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword())
        );

        final String sessionId = sessionRegistry.registerSession(user.getUsername());
        ResponseDTO response = new ResponseDTO();
        response.setSessionId(sessionId);
        return ResponseEntity.ok(response);
            }

    @GetMapping("/checkEmail")
    public ResponseEntity<User> getUserByEmail(@RequestParam(name = "login", defaultValue = "user555") String email) {
        System.out.println("Testing inpit - login is "+email);
        User user = userServiceImplementation.getUserByEmail(email);
        try {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
