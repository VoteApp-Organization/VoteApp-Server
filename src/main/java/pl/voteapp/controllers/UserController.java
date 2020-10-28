package pl.voteapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.voteapp.model.User;
import pl.voteapp.repository.UserRepository;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping(path = {"userView/{id}"}, produces = "application/json")
    public User getUser(@PathVariable("id") Optional<Long> id) {
        return userRepository.findById(id.get()).get() ;
        //TODO
        //Implement finding user by the phone number as a key
        //return userRepository.findByPhoneNumber(phoneNumber) ;
    }
}
