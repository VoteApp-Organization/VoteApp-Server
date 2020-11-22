package pl.voteapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.voteapp.ConstVariables;
import pl.voteapp.QuestionWrapper;
import pl.voteapp.exceptions.ApiError;
import pl.voteapp.model.GroupAssigment;
import pl.voteapp.model.UserSurvey;
import pl.voteapp.model.Vote;
import pl.voteapp.repository.GroupAssigmentRepository;
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

    @GetMapping(path = {"getGroupSurveys/{group_Id}/{user_Id}"}, produces = "application/json")
    public ResponseEntity<Object> getSurveys(@PathVariable("user_Id") Long userId, @PathVariable("group_Id") Long groupId) {
        List<UserSurvey> userSurveys = userSurveyRepository.findUserSurveys(userId);
        List<GroupAssigment> surveysAssigments = groupAssigmentRepository.findAssigmentGroupToVote(groupId);

        System.out.println(userSurveys);
        System.out.println(userSurveys.size());

        System.out.println(surveysAssigments);
        System.out.println(surveysAssigments.size());
        //getting user assigments to votes by group
        Set<Long> voteIds = new HashSet<Long>();
        for (GroupAssigment assigment : surveysAssigments) {
            voteIds.add(assigment.getVote_Id());
        }
        List<Vote> surveys = voteRepository.findAllById(voteIds);
        System.out.println(surveys);
        System.out.println(surveys.size());

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
