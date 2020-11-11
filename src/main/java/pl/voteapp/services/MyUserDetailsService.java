package com.consultingRoom.consultingRoom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.voteapp.model.User;
import pl.voteapp.repository.UserRepository;
import pl.voteapp.security.MyUserDetails;

import java.util.Optional;


@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        System.out.println("erasd " + email);
        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + email));
        System.out.println("user " + user);
        return user.map(MyUserDetails::new).get();
    }

}


