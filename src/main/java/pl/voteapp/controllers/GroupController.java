package pl.voteapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
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
    public List<Vote> getSurveys(@PathVariable("id") Optional<Long> id) {
        List<GroupAssigment> surveysAssigments = groupAssigmentRepository.findAssigmentGroupToVote(id.get());
        Set<Long> voteIds = new HashSet<Long>();
        for(GroupAssigment assigment : surveysAssigments){
            voteIds.add(assigment.getVote_Id());
        }
        List<Vote> surveys = voteRepository.findAllById(voteIds);
        return surveys;
    }
}
