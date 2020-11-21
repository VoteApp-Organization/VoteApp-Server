package pl.voteapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.voteapp.ConstVariables;
import pl.voteapp.Utils;
import pl.voteapp.exceptions.ApiError;
import pl.voteapp.model.Answer;
import pl.voteapp.model.UserSurvey;
import pl.voteapp.repository.AnswerRepository;
import pl.voteapp.repository.UserSurveyRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class AnswerController {

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    UserSurveyRepository userSurveyRepository;

    @RequestMapping(value = "/saveAnswers", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Object> saveUserAnswers(@RequestBody List<Answer> answerList) {
        Optional<UserSurvey> survey = userSurveyRepository.findById(answerList.get(0).vote_id);
        survey.get().answerHasBeenGiven = true;
        survey.get().voteDate = Utils.getCurrentDate();
        userSurveyRepository.save(survey.get());
        answerRepository.saveAll(answerList);

        try{
            return new ResponseEntity<>(ConstVariables.ANSWERS_HAS_BEEN_CREATED_SUCCESSFULLY, HttpStatus.OK);
        } catch(Exception ex){
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ConstVariables.ERROR_MESSAGE_NO_USER_WITH_SPECIFIED_CREDENTIALS);
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
    }

}
