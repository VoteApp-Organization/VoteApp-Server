package pl.voteapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.voteapp.ConstVariables;
import pl.voteapp.QuestionWrapper;
import pl.voteapp.exceptions.ApiError;
import pl.voteapp.exceptions.ApiSuccess;
import pl.voteapp.model.GroupAssigment;
import pl.voteapp.model.Group__c;
import pl.voteapp.model.UserSurvey;
import pl.voteapp.model.Vote;
import pl.voteapp.repository.GroupAssigmentRepository;
import pl.voteapp.repository.GroupRepository;
import pl.voteapp.repository.UserSurveyRepository;
import pl.voteapp.repository.VoteRepository;

import java.util.*;

@RestController
public class GroupController {

    @Autowired
    private GroupAssigmentRepository groupAssigmentRepository;

    @Autowired
    private UserSurveyRepository userSurveyRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private GroupRepository groupRepository;

    @RequestMapping(value = "/createNewGroup", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Object> createGroup(@RequestBody Group__c group) {
        try{
            group.setActive(true);
            groupRepository.save(group);
            List<String> transactions = new ArrayList<String>();
            transactions.add(ConstVariables.OT_GROUP + " " + ConstVariables.INSERT_SUCCESSFUL + " " + ConstVariables.ID_PRESENT + group.getId());
            ApiSuccess apiSuccess = new ApiSuccess(HttpStatus.OK, ConstVariables.GROUP_HAS_BEEN_CREATED_SUCCESSFULLY, transactions);
            return new ResponseEntity<Object>(apiSuccess, new HttpHeaders(), apiSuccess.getStatus());
        } catch(Exception ex){
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ConstVariables.ERROR_MESSAGE_INSERT_FAILED);
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
    }

    @RequestMapping(value = "/leaveGroup", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Object> leaveTheGroup(@RequestBody GroupAssigment groupAssigment) {
        try{
        groupAssigment = groupAssigmentRepository.findUserAssigmentToGroup(groupAssigment.getUser_Id(), groupAssigment.getGroup_Id());
        groupAssigmentRepository.delete(groupAssigment);
        List<String> transactions = new ArrayList<String>();
        transactions.add(ConstVariables.OT_GROUP_ASSIGNMENT + " " + ConstVariables.DELETE_SUCCESSFUL + " " + ConstVariables.ID_PRESENT + groupAssigment.getId());
        ApiSuccess apiSuccess = new ApiSuccess(HttpStatus.OK, ConstVariables.GROUP_HAS_BEEN_CREATED_SUCCESSFULLY, transactions);
        return new ResponseEntity<Object>(apiSuccess, new HttpHeaders(), apiSuccess.getStatus());
        } catch(Exception ex){
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ConstVariables.ERROR_MESSAGE_INSERT_FAILED);
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
    }

    @GetMapping(path = {"getGroupsByName"}, produces = "application/json")
    public ResponseEntity<Object> getGroupsByName(@RequestParam String searchName) {
        try{
            List<Group__c> groups = groupRepository.findGroupByName(searchName);
            ApiSuccess apiSuccess = new ApiSuccess(HttpStatus.OK, ConstVariables.GROUP_HAS_BEEN_FOUND, Arrays.asList(""));
            return new ResponseEntity<Object>(groups, new HttpHeaders(), apiSuccess.getStatus());
        } catch(Exception ex){
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ConstVariables.ERROR_MESSAGE_EMPTY_LIST_TO_RETURN);
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
    }

    @GetMapping(path = {"getGroupSurveys/{group_Id}/{user_Id}"}, produces = "application/json")
    public ResponseEntity<Object> getSurveys(@PathVariable("user_Id") Long userId, @PathVariable("group_Id") Long groupId) {
        List<UserSurvey> userSurveys = userSurveyRepository.findUserSurveys(userId);
        List<GroupAssigment> surveysAssigments = groupAssigmentRepository.findAssigmentGroupToVote(groupId);
        //getting user assigments to votes by group
        Set<Long> voteIds = new HashSet<Long>();
        for (GroupAssigment assigment : surveysAssigments) {
            voteIds.add(assigment.getVote_Id());
        }
        List<Vote> surveys = voteRepository.findAllById(voteIds);

        //preparing wrapper
        List<QuestionWrapper> wrappers = new ArrayList<QuestionWrapper>();
        Map<Long, UserSurvey> userQuestionsMap = new HashMap<Long, UserSurvey>();
        for (UserSurvey userSurvey : userSurveys) {
            userQuestionsMap.put(userSurvey.getSurvey_id(), userSurvey);
        }

        for (Vote survey : surveys) {
            UserSurvey userQuestion = userQuestionsMap.get(survey.getId());
            wrappers.add(new QuestionWrapper(survey, userQuestion));
        }

        if (!wrappers.isEmpty()) {
            return new ResponseEntity<Object>(wrappers, HttpStatus.OK);
        } else {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ConstVariables.ERROR_MESSAGE_EMPTY_LIST_TO_RETURN, ConstVariables.ERROR_MESSAGE_WRONG_PARAMETERS);
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
    }
}
