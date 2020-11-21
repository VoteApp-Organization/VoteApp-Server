package pl.voteapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.voteapp.ConstVariables;
import pl.voteapp.exceptions.ApiError;
import pl.voteapp.repository.QuestionRepository;
import pl.voteapp.repository.UserSurveyRepository;


@RestController
public class SurveyController {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserSurveyRepository userSurveyRepository;

    @RequestMapping(path = {"getQuestionsOnSurvey/{survey_Id}"}, produces = "application/json", method= RequestMethod.GET)
    public ResponseEntity<Object> getQuestions(@PathVariable("survey_Id") Long survey_Id) {
        try {
            return new ResponseEntity<Object>(questionRepository.findBySurveyId(survey_Id), HttpStatus.OK);
        } catch (Exception ex) {
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ConstVariables.ERROR_MESSAGE_NO_QUESTIONS);
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
    }
}
