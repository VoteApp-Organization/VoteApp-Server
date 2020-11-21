package pl.voteapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.voteapp.ConstVariables;
import pl.voteapp.exceptions.ApiError;
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
    public ResponseEntity<Object> getUser(@PathVariable("id") Optional<Long> id) {
        try{
            return new ResponseEntity<>(userRepository.findById(id.get()).get(), HttpStatus.OK) ;
        } catch(Exception ex){
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ConstVariables.ERROR_MESSAGE_NO_USER_WITH_SPECIFIED_ID, ConstVariables.ERROR_MESSAGE_WRONG_PARAMETERS);
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
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

    //EXAMPLE LOGIN BASIC FORM WITHOUT SAFE TOOLS
    @RequestMapping(value = "/loginUser", method = RequestMethod.POST)
    public ResponseEntity<Object> loginUser(@RequestBody User user) {
        try{
            return new ResponseEntity<>(userRepository.findByPhoneEmail(user.getEmail()), HttpStatus.OK);
        } catch(Exception ex){
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ConstVariables.ERROR_MESSAGE_NO_USER_WITH_SPECIFIED_CREDENTIALS);
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
    }
}
