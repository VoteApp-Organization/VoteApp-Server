package pl.voteapp;

import org.springframework.beans.factory.annotation.Autowired;
import pl.voteapp.model.*;
import pl.voteapp.repository.*;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Random;

public class Utils {
    @Autowired
    private static VoteRepository voteRepository;

    @Autowired
    private static UserRepository userRepository;

    @Autowired
    private static QuestionRepository questionRepository;

    @Autowired
    private static UserQuestionRepository userQuestionRepository;

    @Autowired
    private static AnswerRepository answerRepository;

    public static Date getCurrentDate(){
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();
        return dateobj;
    }

    //Create example data for tests
    public static void createExampleData() {
        User user1 = createUser("Marcin", "Marchewka","781098342", "Administrator");
        User user2 = createUser("Tomasz", "Gońcher","72412342", "Client");
        User user3 = createUser("Andrzej", "Szyszka","453098342", "Priest");
        Vote vote1 = createVote("Voting for the new President of United Spring Technologies!", user1.getId());
        Vote vote2 = createVote("Vote for good people!", user1.getId());

        //create questions
        Question question1 = createQuestion("How often do you brush your teeth?");
        Question question2 = createQuestion("Do you like animals?");
        Question question3 = createQuestion("Have you ever been in Germany?");
        Question question4 = createQuestion("How many ECTS did you get?");
        Question question5 = createQuestion("Do you play soccer?");
        Question question6 = createQuestion("Resolve equation 2+2*2");
        Question question7 = createQuestion("What is your favourite movie?");

        //create assigments
        createUserQuestion(vote1.getId(), question1.getId());
        createUserQuestion(vote1.getId(), question2.getId());
        createUserQuestion(vote1.getId(), question3.getId());
        createUserQuestion(vote2.getId(), question4.getId());
        createUserQuestion(vote2.getId(), question5.getId());
        createUserQuestion(vote2.getId(), question6.getId());
        createUserQuestion(vote2.getId(), question7.getId());

        //i think we can delete voteId from object Vote later
        createAnswer(vote1.getId(), question1.getId(), "2x per day");
        createAnswer(vote1.getId(), question1.getId(), "3x per day");
        createAnswer(vote1.getId(), question1.getId(), "Never");
        createAnswer(vote1.getId(), question2.getId(), "Yes");
        createAnswer(vote1.getId(), question2.getId(), "Oh yeees");
        createAnswer(vote1.getId(), question2.getId(), "No man");
        createAnswer(vote1.getId(), question3.getId(), "Never");
        createAnswer(vote1.getId(), question3.getId(), "Once");
        createAnswer(vote1.getId(), question3.getId(), "No, but I am going to visit this country next summer hihi");
        createAnswer(vote2.getId(), question4.getId(), "20");
        createAnswer(vote2.getId(), question4.getId(), "30");
        createAnswer(vote2.getId(), question4.getId(), "40");
        createAnswer(vote2.getId(), question5.getId(), "nope");
        createAnswer(vote2.getId(), question5.getId(), "yea");
        createAnswer(vote2.getId(), question5.getId(), "Mhm");
        createAnswer(vote2.getId(), question6.getId(), "12");
        createAnswer(vote2.getId(), question6.getId(), "4");
        createAnswer(vote2.getId(), question6.getId(), "8");
        createAnswer(vote2.getId(), question7.getId(), "Jumanji");
        createAnswer(vote2.getId(), question7.getId(), "Pulp Fiction");
        createAnswer(vote2.getId(), question7.getId(), "Tabaluga");
    }

    private static Vote createVote(String name, Long authorId){
        Vote vote = new Vote();
        vote.setVoteTitle(name);
        vote.setAnonymousVote(true);
        vote.setMandatory(false);
        vote.setStartDate((java.sql.Date) getCurrentDate());
        vote.setAuthor_id(authorId);
        voteRepository.save(vote);
        return vote;
    }

    private static User createUser(String name, String city, String phone, String userType){
        User user = new User();
        user.setName(name);
        user.setActive(true);
        user.setCity(city);
        user.setEmail(name + "@gmail.com");
        user.setMobileNumber(phone);
        user.setUserType(userType);
        userRepository.save(user);
        return user;
    }

    private static Question createQuestion(String questionContent){
        Question question = new Question();
        question.setMandatoryQuestion(true);
        question.setMaximumCapacityOfAnswer(50);
        question.setMultipleChoice(false);
        question.setQuestionContent(questionContent);
        questionRepository.save(question);
        return question;
    }

    private static void createUserQuestion(Long userId, Long questionId){
        UserQuestion userQuestion = new UserQuestion();
        userQuestion.setAnswerHasBeenGiven(true);
        userQuestion.setUser_id(userId);
        userQuestion.setQuestion_id(questionId);
        userQuestionRepository.save(userQuestion);
    }

    private static void createAnswer(Long voteId, Long questionId, String contentOfAnswer){
        Answer answer = new Answer();
        answer.setQuestion_id(questionId);
        answer.setVote_id(voteId);
        answer.setAnswerContent(contentOfAnswer);
        answerRepository.save(answer);
    }
}
