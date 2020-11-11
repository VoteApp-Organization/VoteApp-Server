package pl.voteapp;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.voteapp.model.*;
import pl.voteapp.repository.*;

@SpringBootApplication
public class VoteAppApplication {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserQuestionRepository userQuestionRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupAssigmentRepository groupAssigmentRepository;

    public static void main(String[] args) {
        SpringApplication.run(VoteAppApplication.class, args);
    }

    @Bean
    InitializingBean loadInitialData() {
        return () -> {
            createExampleData();
            System.out.println(userRepository.findAll());
        };
    }

    //Create example data for tests
    public void createExampleData() {
        //create users
        User user1 = createUser("Marcin", "Marchewka","781098342", "Administrator", "test1234");
        User user2 = createUser("Tomasz", "Gońcher","72412342", "Client", "test1234");
        User user3 = createUser("Andrzej", "Szyszka","453098342", "Priest", "test1234");

        //create votes
        Vote vote1 = createVote("Voting for the new President of United Spring Technologies!", user1.getId());
        Vote vote2 = createVote("Vote for good people!", user1.getId());

        //create groups
        Group__c group1 = createGroup("Dormitory", "Group of residents of the dormitory", false, user1.getId());
        Group__c group2 = createGroup("College", "Best group ever", false, user1.getId());
        Group__c group3 = createGroup("Poland Country", "Group of all citizens of Poland!", true, user1.getId());
        Group__c group4 = createGroup("The Majcher family", "Group of all Majachers in Skierniewice", false, user2.getId());

        //create assigments groups
        createGroupAssigment(group1.getId(), user1.getId(), null);
        createGroupAssigment(group1.getId(), user2.getId(), null);
        createGroupAssigment(group2.getId(), user1.getId(), null);
        createGroupAssigment(group2.getId(), user2.getId(), null);
        createGroupAssigment(group3.getId(), user1.getId(), null);
        createGroupAssigment(group3.getId(), user2.getId(), null);
        createGroupAssigment(group3.getId(), user3.getId(), null);
        createGroupAssigment(group4.getId(), user2.getId(), null);

        createGroupAssigment(group3.getId(), null, vote1.getId());
        createGroupAssigment(group1.getId(), null, vote2.getId());
        createGroupAssigment(group2.getId(), null, vote2.getId());
        createGroupAssigment(group3.getId(), null, vote2.getId());
        createGroupAssigment(group4.getId(), null, vote2.getId());


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

    private Vote createVote(String name, Long authorId){
        Vote vote = new Vote();
        vote.setVoteTitle(name);
        vote.setAnonymousVote(true);
        vote.setMandatory(false);
        //vote.setStartDate((java.sql.Date) Utils.getCurrentDate());
        vote.setAuthor_id(authorId);
        voteRepository.save(vote);
        return vote;
    }

    private User createUser(String name, String city, String phone, String userType, String password){
        User user = new User();
        user.setName(name);
        user.setActive(true);
        user.setCity(city);
        user.setEmail(name + "@gmail.com");
        user.setMobileNumber(phone);
        user.setUserType(userType);
        user.setPassword(password);
        userRepository.save(user);
        return user;
    }

    private Question createQuestion(String questionContent){
        Question question = new Question();
        question.setMandatoryQuestion(true);
        question.setMaximumCapacityOfAnswer(50);
        question.setMultipleChoice(false);
        question.setQuestionContent(questionContent);
        questionRepository.save(question);
        return question;
    }

    private void createUserQuestion(Long userId, Long questionId){
        UserQuestion userQuestion = new UserQuestion();
        userQuestion.setAnswerHasBeenGiven(true);
        userQuestion.setUser_id(userId);
        userQuestion.setQuestion_id(questionId);
        userQuestionRepository.save(userQuestion);
    }

    private void createAnswer(Long voteId, Long questionId, String contentOfAnswer){
        Answer answer = new Answer();
        answer.setQuestion_id(questionId);
        answer.setVote_id(voteId);
        answer.setAnswerContent(contentOfAnswer);
        answerRepository.save(answer);
    }

    private Group__c createGroup(String groupName, String groupDescription, Boolean isPublic, Long authorId){
        Group__c group = new Group__c();
        group.setName(groupName);
        group.setDescription(groupDescription);
        group.setActive(true);
        group.setPublic(isPublic);
        group.setOwner_id(authorId);
        groupRepository.save(group);
        return group;
    }

    private void createGroupAssigment(Long groupId, Long userId, Long voteId){
        GroupAssigment assigment = new GroupAssigment();
        assigment.setGroup_Id(groupId);
        assigment.setUser_Id(userId);
        assigment.setVote_Id(voteId);
        groupAssigmentRepository.save(assigment);
    }
}
