package pl.voteapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.voteapp.ConstVariables;
import pl.voteapp.Utils;
import pl.voteapp.exceptions.ApiError;
import pl.voteapp.exceptions.ApiSuccess;
import pl.voteapp.model.Answer;
import pl.voteapp.model.UserSurvey;
import pl.voteapp.repository.AnswerRepository;
import pl.voteapp.repository.UserSurveyRepository;

import java.util.*;

@RestController
public class AnswerController {

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    UserSurveyRepository userSurveyRepository;

    @RequestMapping(value = "/saveAnswers", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Object> saveUserAnswers(@RequestBody List<Answer> answerList) {
        List<String> transactions = new ArrayList<String>();
        Optional<UserSurvey> survey = userSurveyRepository.findById(answerList.get(0).vote_id);
        survey.get().answerHasBeenGiven = true;
        survey.get().voteDate = Utils.getCurrentDate();
        userSurveyRepository.save(survey.get());
        answerRepository.saveAll(answerList);
        transactions.add(ConstVariables.OT_ANSWER + " " + ConstVariables.UPDATE_SUCCESSFUL + " " + ConstVariables.ID_PRESENT + survey.get().getId());
        transactions.add(ConstVariables.OT_USER_SURVEY + " " + ConstVariables.INSERT_SUCCESSFUL + " " + ConstVariables.QUANTITY_PRESENT + answerList.size());

        try{
            ApiSuccess apiSuccess = new ApiSuccess(HttpStatus.OK, ConstVariables.ANSWERS_HAS_BEEN_CREATED_SUCCESSFULLY, transactions);
            return new ResponseEntity<Object>(apiSuccess, new HttpHeaders(), apiSuccess.getStatus());
        } catch(Exception ex){
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ConstVariables.ERROR_MESSAGE_INSERT_FAILED);
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
    }

}
