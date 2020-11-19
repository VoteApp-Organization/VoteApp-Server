package pl.voteapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.voteapp.ConstVariables;
import pl.voteapp.QuestionWrapper;
import pl.voteapp.exceptions.ApiError;
import pl.voteapp.model.Question;
import pl.voteapp.model.UserQuestion;
import pl.voteapp.repository.QuestionRepository;
import pl.voteapp.repository.UserQuestionRepository;

import java.util.*;

@RestController
public class SurveyController {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserQuestionRepository userQuestionRepository;

    @RequestMapping(path = {"getQuestionsOnSurvey/{survey_Id}/{userid}"}, produces = "application/json", method= RequestMethod.GET)
    public ResponseEntity<Object> getQuestions(@PathVariable("survey_Id") Long survey_Id, @PathVariable("userid") Long userid) {
        List<QuestionWrapper> wrappers = new ArrayList<QuestionWrapper>();
        try{
            List<Question> questions = questionRepository.findBySurveyId(survey_Id);
            List<UserQuestion> userQuestions = userQuestionRepository.findUserGivenAnswers(userid);

            Map<Long, UserQuestion> userQuestionsMap= new HashMap<Long, UserQuestion>();
            for(UserQuestion userQuestion : userQuestions){
                userQuestionsMap.put(userQuestion.getId(), userQuestion);
            }

            for(Question question : questions){
                UserQuestion userQuestion = userQuestionsMap.get(question.getId());
                wrappers.add(new QuestionWrapper(question, userQuestion));
            }
            if(!wrappers.isEmpty()){
                return new ResponseEntity<Object>(wrappers, HttpStatus.OK);
            } else {
                ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ConstVariables.ERROR_MESSAGE_EMPTY_LIST_TO_RETURN, ConstVariables.ERROR_MESSAGE_WRONG_PARAMETERS);
                return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
            }

        } catch(Exception ex){
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ConstVariables.ERROR_MESSAGE_NO_QUESTIONS);
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
    }
}
