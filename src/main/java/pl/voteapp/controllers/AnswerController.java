package pl.voteapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.voteapp.model.Question;
import pl.voteapp.model.Vote;
import pl.voteapp.repository.QuestionRepository;
import pl.voteapp.wrappers.AnswersWrapper;
import pl.voteapp.ConstVariables;
import pl.voteapp.SurveyResultWrapper;
import pl.voteapp.Utils;
import pl.voteapp.exceptions.ApiError;
import pl.voteapp.exceptions.ApiSuccess;
import pl.voteapp.model.Answer;
import pl.voteapp.model.UserSurvey;
import pl.voteapp.repository.AnswerRepository;
import pl.voteapp.repository.UserSurveyRepository;
import pl.voteapp.repository.VoteRepository;
import pl.voteapp.wrappers.QuestionAnswersWrapper;

import java.util.*;

@RestController
public class AnswerController {

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    UserSurveyRepository userSurveyRepository;

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    QuestionRepository questionRepository;

    @RequestMapping(value = "/saveAnswers", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Object> saveUserAnswers(@RequestBody AnswersWrapper answersWrapper) {
        List<String> transactions = new ArrayList<String>();
        Optional<UserSurvey> survey = userSurveyRepository.findById(answersWrapper.answers.get(0).vote_id);
        survey.get().answerHasBeenGiven = true;
        survey.get().voteDate = Utils.getCurrentDate();
        userSurveyRepository.save(survey.get());
        answerRepository.saveAll(answersWrapper.answers);
        transactions.add(ConstVariables.OT_ANSWER + " " + ConstVariables.UPDATE_SUCCESSFUL + " " + ConstVariables.ID_PRESENT + survey.get().getId());
        transactions.add(ConstVariables.OT_USER_SURVEY + " " + ConstVariables.INSERT_SUCCESSFUL + " " + ConstVariables.QUANTITY_PRESENT + answersWrapper.answers.size());

        try{
            ApiSuccess apiSuccess = new ApiSuccess(HttpStatus.OK, ConstVariables.ANSWERS_HAS_BEEN_CREATED_SUCCESSFULLY, transactions);
            return new ResponseEntity<Object>(apiSuccess, new HttpHeaders(), apiSuccess.getStatus());
        } catch(Exception ex){
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ConstVariables.ERROR_MESSAGE_INSERT_FAILED);
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
    }

    @RequestMapping(value = "/getSurveyAnswers/{survey_Id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Object> getSurveyAnswers(@PathVariable("survey_Id") Long surveyId) {
        List<Answer> answers = answerRepository.findSurveyAnswers(surveyId);
        //Boolean isSurveyAnonymous = voteRepository.findById(surveyId).get().getPublicVote();
        List<Question> questions = questionRepository.findBySurveyId(surveyId);
        Optional<Vote> vote = voteRepository.findById(surveyId);
        Map<Long, List<Answer>> questionMap = new HashMap<Long, List<Answer>>();
        for (Answer answer : answers) {
            if (questionMap.containsKey(answer.getQuestion_id())) {
                questionMap.get(answer.getQuestion_id()).add(answer);
            } else {
                List<Answer> questionNewList = new ArrayList<Answer>();
                questionNewList.add(answer);
                questionMap.put(answer.getQuestion_id(), questionNewList);
            }
        }

        List<QuestionAnswersWrapper> container = new ArrayList<QuestionAnswersWrapper>();
        for(Long questionAnswers : questionMap.keySet()){
            container.add(new QuestionAnswersWrapper(questionAnswers, "cycki", questionMap.get(questionAnswers)));
        }
        SurveyResultWrapper surveyContainer = new SurveyResultWrapper();
        surveyContainer.questions = container;
        surveyContainer.surveyTitle = vote.get().getVoteTitle();
        surveyContainer.surveyDescription = vote.get().getSurveyDescription();
        try{
            return new ResponseEntity<>(surveyContainer, HttpStatus.OK);
        } catch(Exception ex){
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ConstVariables.ERROR_MESSAGE_INSERT_FAILED);
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
    }
}
