package com.teamA.service.implementation;

import com.teamA.model.MyOwnUserDetails;
import com.teamA.model.User;
import com.teamA.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;


@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {
    private UserRepository userRepository;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // userDetails is a wrapper around user model in DB!;
    // instead of returning user in this method, we have to return an implementation of userDetails
    // that is what Spring Security wats us to do!
    @Override
    public MyOwnUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        final Optional<User> user = userRepository.getUserByEmail(email);
        User user1 = userRepository.getUserByEmail(email);
//        user.orElseThrow( ()-> new UsernameNotFoundException("USER was not found by login-email: " + email)   );
//        return new MyOwnUserDetails(user.get());
//        return user.map(u -> new MyOwnUserDetails(user.get())).get();
        System.out.println(user1.toString());
        return new MyOwnUserDetails(user1);
    }
}
