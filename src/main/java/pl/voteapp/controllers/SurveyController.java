package pl.voteapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.voteapp.QuestionWrapper;
import pl.voteapp.model.Question;
import pl.voteapp.model.UserQuestion;
import pl.voteapp.model.Vote;
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
    public List<QuestionWrapper> getQuestions(@PathVariable("survey_Id") Long survey_Id, @PathVariable("userid") Long userid) {
        List<Question> questions = questionRepository.findBySurveyId(survey_Id);
        System.out.println("questions " + questions);
        List<UserQuestion> userQuestions = userQuestionRepository.findUserGivenAnswers(userid);
        System.out.println("userQuestions " + userQuestions);
        Map<Long, UserQuestion> userQuestionsMap= new HashMap<Long, UserQuestion>();
        for(UserQuestion userQuestion : userQuestions){
            userQuestionsMap.put(userQuestion.getId(), userQuestion);
        }
        System.out.println("userQuestionsMap " + userQuestionsMap);
        List<QuestionWrapper> wrappers = new ArrayList<QuestionWrapper>();
        for(Question question : questions){
            UserQuestion userQuestion = userQuestionsMap.get(question.getId());
            wrappers.add(new QuestionWrapper(question, userQuestion));
        }
        System.out.println("wrappers " + wrappers);
        return wrappers;
    }
}
