package pl.voteapp.controllers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.voteapp.ConstVariables;
import pl.voteapp.exceptions.ApiError;
import pl.voteapp.exceptions.ApiSuccess;
import pl.voteapp.model.GroupAssigment;
import pl.voteapp.model.Vote;
import pl.voteapp.repository.GroupAssigmentRepository;
import pl.voteapp.repository.QuestionRepository;
import pl.voteapp.repository.UserSurveyRepository;
import pl.voteapp.repository.VoteRepository;

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
    public ResponseEntity<Object> createSurvey(@RequestHeader("ID-TOKEN") String idToken, @RequestHeader("group_Id") Long group_Id, @RequestBody Vote vote) {
        try{
            FirebaseAuth.getInstance().verifyIdToken(idToken);
            Vote newVote = voteRepository.save(vote);
            GroupAssigment assigment = new GroupAssigment();
            assigment.setGroup_Id(group_Id);
            assigment.setVote_Id(newVote.getId());
            GroupAssigment groupAssigment = assigmentRepository.save(assigment);
            List<String> transactions = new ArrayList<String>();
            transactions.add(ConstVariables.OT_SURVEY + " " + ConstVariables.INSERT_SUCCESSFUL + " " + ConstVariables.ID_PRESENT + newVote.getId());
            transactions.add(ConstVariables.OT_GROUP_ASSIGNMENT + " " + ConstVariables.INSERT_SUCCESSFUL + " " + ConstVariables.ID_PRESENT + groupAssigment.getId());
            ApiSuccess apiSuccess = new ApiSuccess(HttpStatus.OK, ConstVariables.GROUP_HAS_BEEN_LEFT_SUCCESSFULLY, transactions);
            return new ResponseEntity<>(apiSuccess, HttpStatus.OK);
        } catch(Exception ex){
            ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), ConstVariables.ERROR_MESSAGE_NO_USER_WITH_SPECIFIED_CREDENTIALS);
            return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
        }
    }
}
