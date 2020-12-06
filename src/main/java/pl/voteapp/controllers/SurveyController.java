package pl.voteapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.voteapp.ConstVariables;
import pl.voteapp.exceptions.ApiError;
import pl.voteapp.exceptions.ApiSuccess;
import pl.voteapp.model.GroupAssigment;
import pl.voteapp.model.Question;
import pl.voteapp.model.UserSurvey;
import pl.voteapp.model.Vote;
import pl.voteapp.repository.GroupAssigmentRepository;
import pl.voteapp.repository.QuestionRepository;
import pl.voteapp.repository.UserSurveyRepository;
import pl.voteapp.repository.VoteRepository;
import pl.voteapp.wrappers.SurveyQuestion;
import pl.voteapp.wrappers.SurveyWrapper;

import java.util.ArrayList;
import java.util.List;


@RestController
public class SurveyController {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserSurveyRepository userSurveyRepository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    GroupAssigmentRepository assigmentRepository;

    @RequestMapping(path = {"getQuestionsOnSurvey/{survey_Id}"}, produces = "application/json", method= RequestMethod.GET)
    public ResponseEntity<Object> getQuestions(@PathVariable("survey_Id") Long survey_Id) {
        try {
            return new ResponseEntity<Object>(questionRepository.findBySurveyId(survey_Id), HttpStatus.OK);
        } catch (Exception ex) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ConstVariables.ERROR_MESSAGE_NO_QUESTIONS);
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
    }

    @RequestMapping(value = "/createNewSurvey", method = RequestMethod.POST)
   public ResponseEntity<Object> createSurvey(@RequestBody SurveyWrapper surveyWrapper) {
        try{
            Vote newVote = voteRepository.save(new Vote(surveyWrapper));
            List<Question> surveyQuestions = new ArrayList<Question>();
            for (SurveyQuestion question : surveyWrapper.questions) {
                surveyQuestions.add(new Question(question, newVote.getId()));
            }

            GroupAssigment assigment = new GroupAssigment(newVote.getId(),surveyWrapper.group_id, null);
            List<GroupAssigment> userAssigmentsToGroup = assigmentRepository.findAllUsersOnGroup(surveyWrapper.group_id);

            List<UserSurvey> groupUserSurveys = new ArrayList<UserSurvey>();
            for(GroupAssigment userAssigment : userAssigmentsToGroup){
                groupUserSurveys.add(new UserSurvey(newVote.getId(), userAssigment.getUser_Id(), false));
            }
            if (surveyWrapper.authorIsVoting) {
                groupUserSurveys.add(new UserSurvey(newVote.getId(), surveyWrapper.author_id, false));
            }

            questionRepository.saveAll(surveyQuestions);
            GroupAssigment groupAssigment = assigmentRepository.save(assigment);
            userSurveyRepository.saveAll(groupUserSurveys);

            List<String> transactions = new ArrayList<String>();
            transactions.add(ConstVariables.OT_SURVEY + " " + ConstVariables.INSERT_SUCCESSFUL + " " + ConstVariables.ID_PRESENT + newVote.getId());
            transactions.add(ConstVariables.OT_GROUP_ASSIGNMENT + " " + ConstVariables.INSERT_SUCCESSFUL + " " + ConstVariables.ID_PRESENT + groupAssigment.getId());
            transactions.add(ConstVariables.OT_QUESTION + " " + ConstVariables.INSERT_SUCCESSFUL + " " + ConstVariables.QUANTITY_PRESENT + surveyQuestions.size());
            transactions.add(ConstVariables.OT_USER_SURVEY + " " + ConstVariables.INSERT_SUCCESSFUL + " " + ConstVariables.QUANTITY_PRESENT + groupUserSurveys.size());
            ApiSuccess apiSuccess = new ApiSuccess(HttpStatus.OK, ConstVariables.GROUP_HAS_BEEN_LEFT_SUCCESSFULLY, transactions);
            return new ResponseEntity<>(apiSuccess, HttpStatus.OK);
        } catch(Exception ex){
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ConstVariables.ERROR_MESSAGE_FAILED_CREATING_SURVEY);
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
    }
}
