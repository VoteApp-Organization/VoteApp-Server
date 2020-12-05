package pl.voteapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.voteapp.ConstVariables;
import pl.voteapp.wrappers.GroupAssigmentWrapper;
import pl.voteapp.wrappers.QuestionWrapper;
import pl.voteapp.Utils;
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
            if(!group.getIs_public()){
                Set<Long> groupPasswords = groupRepository.getAllPasswords();
                group.setGroup_password(Utils.generateRandom(groupPasswords));
            }
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
        ApiSuccess apiSuccess = new ApiSuccess(HttpStatus.OK, ConstVariables.GROUP_HAS_BEEN_LEFT_SUCCESSFULLY, transactions);
        return new ResponseEntity<Object>(apiSuccess, new HttpHeaders(), apiSuccess.getStatus());
        } catch(Exception ex){
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ConstVariables.ERROR_MESSAGE_INSERT_FAILED);
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
    }

    @RequestMapping(value = "/deleteGroup", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Object> deleteGroup(@RequestBody GroupAssigment groupAssigment) {
        try{
            Optional<Group__c> group = groupRepository.findById(groupAssigment.getUser_Id());
            if (!group.get().getOwner_id().equals(groupAssigment.getUser_Id())) {
                ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "", ConstVariables.ERROR_MESSAGE_ONLY_GROUP_OWNER_CAN_DELETE_GROUP);
                return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
            }
            groupRepository.delete(group.get());
            List<GroupAssigment> groupAssignments = groupAssigmentRepository.findAllAssignmentsToGroup(groupAssigment.getGroup_Id());
            groupAssigmentRepository.deleteAll(groupAssignments);
            List<String> transactions = new ArrayList<String>();
            transactions.add(ConstVariables.OT_GROUP + " " + ConstVariables.DELETE_SUCCESSFUL + " " + ConstVariables.ID_PRESENT + group.get().getId());
            transactions.add(ConstVariables.OT_GROUP_ASSIGNMENT + " " + ConstVariables.DELETE_SUCCESSFUL + " " + ConstVariables.QUANTITY_PRESENT + groupAssignments.size());
            ApiSuccess apiSuccess = new ApiSuccess(HttpStatus.OK, ConstVariables.GROUP_HAS_BEEN_LEFT_SUCCESSFULLY, transactions);
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

    @RequestMapping(path = {"exploreGroupsByName/{user_Id}/{name}", "exploreGroupsByName/{user_Id}"}, produces = "application/json", method= RequestMethod.GET)
    public ResponseEntity<Object> exploreGroupsByName(@PathVariable("user_Id") Long userId, @PathVariable("name") Optional<String> searchName) {
        try{
            List<GroupAssigment> userGroups = groupAssigmentRepository.findAssigmentUserToGroups(userId);
            Set<Long> groupIds = new HashSet<Long>();
            for(GroupAssigment group : userGroups){
                groupIds.add(group.getGroup_Id());
            }

            List<Group__c> groups = groupRepository.exploreGroupByName(searchName.isPresent() ? searchName.get() : "", groupIds);
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

    @RequestMapping(value = "/joinGroup", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Object> joinGroup(@RequestBody GroupAssigmentWrapper groupAssigment) {
        try {
            Optional<Group__c> group;
            if (!groupAssigment.getGroup_Id().equals(Utils.EMPTY_STRING)) {
                group = groupRepository.findById(Long.valueOf(groupAssigment.getGroup_Id()));
            } else {
                group = groupRepository.findGroupByPassword(groupAssigment.getPassword());
            }

            if (group.isPresent()) {
                if (!group.get().getIs_public() && (groupAssigment.getPassword() == null || !groupAssigment.getPassword().equals(group.get().getGroup_password()))) {
                    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "", ConstVariables.ERROR_MESSAGE_INCORRECT_PASSWORD);
                    return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
                }
                //In this place we assume that this assigment does not exist right now
                //TODO create special identifier of assigment which will be unique
                GroupAssigment newGroupAssigment = groupAssigmentRepository.save(new GroupAssigment(null, group.get().getId(), groupAssigment.getUser_Id()));
                //create userSurveys
                List<Vote> groupSurveys = voteRepository.findGroupSurveys(groupAssigment.getVote_Id());
                List<UserSurvey> existingUserSurvey = userSurveyRepository.findUserSurveys(groupAssigment.getUser_Id());
                Map<Long, Vote> groupSurveysMap = new HashMap<Long, Vote>();
                for(Vote vote : groupSurveys){
                    groupSurveysMap.put(vote.getId(),vote);
                }

                for(UserSurvey userSurvey : existingUserSurvey){
                    if(groupSurveysMap.containsKey(userSurvey.getSurvey_id())){
                        groupSurveysMap.remove(userSurvey.getSurvey_id());
                    }
                }
                List<String> transactions = new ArrayList<String>();
                if(!groupSurveysMap.isEmpty()){
                    List<UserSurvey> newUserSurveys = new ArrayList<UserSurvey>();
                    for(Vote vote : groupSurveysMap.values()){
                        UserSurvey userSurvey = new UserSurvey(vote.getId(), groupAssigment.getUser_Id(), false);
                        newUserSurveys.add(userSurvey);
                    }
                    userSurveyRepository.saveAll(newUserSurveys);
                    transactions.add(ConstVariables.OT_USER_SURVEY + " " + ConstVariables.INSERT_SUCCESSFUL + " " + ConstVariables.QUANTITY_PRESENT + userSurveyRepository.findAll().size());
                }

                transactions.add(ConstVariables.OT_GROUP_ASSIGNMENT + " " + ConstVariables.INSERT_SUCCESSFUL + " " + ConstVariables.ID_PRESENT + newGroupAssigment.getId());
                ApiSuccess apiSuccess = new ApiSuccess(HttpStatus.OK, ConstVariables.GROUP_HAS_BEEN_JOINED, transactions);
                return new ResponseEntity<Object>(apiSuccess, new HttpHeaders(), apiSuccess.getStatus());
            } else {
                ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "", ConstVariables.ERROR_MESSAGE_GROUP_NOT_FOUND);
                return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
            }
        } catch (Exception ex) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ConstVariables.ERROR_MESSAGE_INSERT_FAILED);
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
    }
}
