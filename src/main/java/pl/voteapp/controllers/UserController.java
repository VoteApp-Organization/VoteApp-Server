package pl.voteapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.voteapp.model.Group__c;
import pl.voteapp.model.GroupAssigment;
import pl.voteapp.model.User;
import pl.voteapp.repository.GroupAssigmentRepository;
import pl.voteapp.repository.GroupRepository;
import pl.voteapp.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupAssigmentRepository groupAssigmentRepository;

    @Autowired
    private GroupRepository groupRepository;

    @GetMapping(path = {"userView/{id}"}, produces = "application/json")
    public User getUser(@PathVariable("id") Optional<Long> id) {
        return userRepository.findById(id.get()).get() ;
        //TODO
        //Implement finding user by the phone number as a key
        //return userRepository.findByPhoneNumber(phoneNumber) ;
    }

    @RequestMapping(value = "getUserGroups/{id}", method = RequestMethod.GET)
    public @ResponseBody
    List<Group__c> getGroups(@PathVariable("id") Optional<Long> userid){
        //TODO
        //There must be easier way to get this data
        //To improve !
        List<GroupAssigment> groupAssigments = groupAssigmentRepository.findAssigmentUserToGroups(userid.get());
        Set<Long> groupsId = new HashSet<Long>();
        for(GroupAssigment assigment : groupAssigments){
            groupsId.add(assigment.getGroup_Id());
        }
        List<Group__c> groups = groupRepository.findAllById(groupsId);
        return groups;
    }
}
