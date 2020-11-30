package pl.voteapp.controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
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
        try {
            return new ResponseEntity<>(userRepository.findById(id.get()).get(), HttpStatus.OK);
        } catch (Exception ex) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ConstVariables.ERROR_MESSAGE_NO_USER_WITH_SPECIFIED_ID, ConstVariables.ERROR_MESSAGE_WRONG_PARAMETERS);
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
    }

    @RequestMapping(value = "getUserGroups/{id}", method = RequestMethod.GET)
    public @ResponseBody
    List<Group__c> getGroups(@PathVariable("id") Optional<Long> userid) {
        List<GroupAssigment> groupAssigments = groupAssigmentRepository.findAssigmentUserToGroups(userid.get());
        List<Long> authorGroupIds = groupRepository.findGroupByAuthorId(userid.get());
        Set<Long> groupsId = new HashSet<Long>();
        for (Long currentId : authorGroupIds) {
            groupsId.add(currentId);
        }
        for (GroupAssigment assigment : groupAssigments) {
            groupsId.add(assigment.getGroup_Id());
        }
        List<Group__c> groups = groupRepository.findAllById(groupsId);
        return groups;
    }

    @RequestMapping(value = "/loginUser", method = RequestMethod.POST)
    public ResponseEntity<Object> loginUser(@RequestHeader("ID-TOKEN") String idToken) throws FirebaseAuthException {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            UserRecord userRecord = FirebaseAuth.getInstance().getUser(String.valueOf(decodedToken.getUid()));
            String uid = decodedToken.getUid();
            System.out.println(uid);
            return new ResponseEntity<>(userRepository.findByPhoneEmail(userRecord.getEmail()), HttpStatus.OK);
        } catch (Exception ex) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ConstVariables.ERROR_MESSAGE_NO_USER_WITH_SPECIFIED_CREDENTIALS);
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
    }

    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public ResponseEntity<Object> registerUser(@RequestHeader("ID-TOKEN") String idToken) {
        try {
            FirebaseToken decodedToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            UserRecord userRecord = FirebaseAuth.getInstance().getUser(String.valueOf(decodedToken.getUid()));
            User user = new User();
            user.setEmail(userRecord.getEmail());
            user.setName(userRecord.getDisplayName());
            user.setMobileNumber(userRecord.getPhoneNumber());
            return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
        } catch (Exception ex) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ConstVariables.ERROR_MESSAGE_NO_USER_WITH_SPECIFIED_CREDENTIALS);
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
    }
}
