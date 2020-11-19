package pl.voteapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.voteapp.ConstVariables;
import pl.voteapp.exceptions.ApiError;
import pl.voteapp.model.GroupAssigment;
import pl.voteapp.model.User;
import pl.voteapp.model.Vote;
import pl.voteapp.repository.GroupAssigmentRepository;
import pl.voteapp.repository.GroupRepository;
import pl.voteapp.repository.VoteRepository;

import java.util.*;

@RestController
public class GroupController {

    @Autowired
    private GroupAssigmentRepository groupAssigmentRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private VoteRepository voteRepository;

    @GetMapping(path = {"getGroupSurveys/{id}"}, produces = "application/json")
    public ResponseEntity<Object> getSurveys(@PathVariable("id") Optional<Long> id) {
        List<GroupAssigment> surveysAssigments = groupAssigmentRepository.findAssigmentGroupToVote(id.get());
        Set<Long> voteIds = new HashSet<Long>();
        for(GroupAssigment assigment : surveysAssigments){
            voteIds.add(assigment.getVote_Id());
        }
        List<Vote> surveys = voteRepository.findAllById(voteIds);
        if (!surveys.isEmpty()) {
            return new ResponseEntity<Object>(surveys, HttpStatus.OK);
        } else {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ConstVariables.ERROR_MESSAGE_EMPTY_LIST_TO_RETURN, ConstVariables.ERROR_MESSAGE_WRONG_PARAMETERS);
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
    }
}
