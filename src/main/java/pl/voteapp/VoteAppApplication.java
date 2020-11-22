package pl.voteapp;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.voteapp.model.*;
import pl.voteapp.repository.*;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class VoteAppApplication {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserSurveyRepository userSurveyRepository;

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
        };
    }

    //Create example data for tests
    public void createExampleData() {
        //create users
        Long user1 = createUser("Marcin", "Marchewka","781098342", "Administrator");
        Long user2 = createUser("Tomasz", "Gońcher","77312442", "Client");
        Long user3 = createUser("Jakub", "Gońcher","7492342", "Client");
        Long user4 = createUser("Marian", "Gońcher","62483762", "Client");
        Long user5 = createUser("Agnieszka", "Orzeszek","7241642", "Client");
        Long user6 = createUser("Maja", "Wiesławska","5251349", "Client");
        Long user7 = createUser("Andrzej", "Szyszka","453098342", "Priest");

        //create votes
        Long vote1 = createVote(
                "Voting for the new President of United Spring Technologies!",
                null,
                "We are choosing new head of Spring Technology land",
                3,
                user1,
                false, 5, 10, 19, 10);
        Long vote2 = createVote("Vote for good people!",
                null,
                "Choose somebody who were helpful for you in last days",
                4,
                user1,
                true, 25,10,28,10);
        Long vote3 = createVote("Choose the funniest animal",
                "haslo1234",
                "We need to make decision, which animal is the funniest one! Through the years we were thinking" +
                        "that the cat rules in the internet, but now hamsters and owls want to change that!",
                10,
                user3,
                true,6,12,24,12);
        Long vote4 = createVote("Back.Choose your symptoms",
                "bedzieHashowaneHaslo",
                "We are conducting research on the XYZ disease, tell us about your symptoms",
                2,
                user1,
                false, 15,10,19,10);
        Long vote5 = createVote("What we should by Jarek for his Birthday",
                "hasloMaslo",
                "New trousers or Wyborowa 0.7, or 2x Krupnik 0.5",
                1,
                user2,
                false, 23,11,25,11);
        Long vote6 = createVote("Iphone12 survey",
                null,
                "What do you think about Iphone 12, describe you feelings. Rate 0-10",
                1,
                user2,
                true, 27,11,30,11);
        Long vote7 = createVote("Who is better CR7, Messi Or Ronaldo?",
                null,
                "Who is better?",
                1,
                user2,
                true, 17,10,17,12);
        Long vote8 = createVote("Rate scenario in new Tarantino movie",
                null,
                "Describe your feelings about Tarantino movie's scenario.",
                1,
                user2,
                true, 15,10,15,12);
        Long vote9 = createVote("Intel or Radeon!",
                null,
                "Choose light side of power!",
                1,
                user2,
                true, 1,11,1,12);
        Long vote10 = createVote("Fast and furious, whats next?",
                null,
                "Help us to make decision about future of legendary film series",
                8,
                user2,
                true, 1,12,21,12);
        Long vote11 = createVote("Poland imperial plans?",
                null,
                "Help us to make decision about, we like holidays at Lvov",
                1,
                user7,
                true, 1,12,21,12);


        //create groups
        Long group1 = createGroup("Dormitory", "Group of residents of the dormitory", false, user1);
        Long group2 = createGroup("College", "Best group ever", false, user1);
        Long group3 = createGroup("Poland Country", "Group of all citizens of Poland!", true, user1);
        Long group4 = createGroup("The Majcher family", "Group of all Majachers in Skierniewice", false, user2);

        //create assigments groups
        //groups to user
        createGroupAssigment(group1, user1, null);
        createGroupAssigment(group1, user2, null);
        createGroupAssigment(group2, user1, null);
        createGroupAssigment(group2, user2, null);
        createGroupAssigment(group3, user1, null);
        createGroupAssigment(group3, user2, null);
        createGroupAssigment(group3, user3, null);
        createGroupAssigment(group3, user4, null);
        createGroupAssigment(group3, user5, null);
        createGroupAssigment(group3, user6, null);
        createGroupAssigment(group3, user7, null);
        createGroupAssigment(group4, user2, null);

        //groups to vote
        createGroupAssigment(group3, null, vote1);
        createGroupAssigment(group1, null, vote2);
        createGroupAssigment(group2, null, vote2);
        createGroupAssigment(group3, null, vote2);
        createGroupAssigment(group4, null, vote2);

        createGroupAssigment(group3, null, vote3);
        createGroupAssigment(group3, null, vote4);
        createGroupAssigment(group3, null, vote5);
        createGroupAssigment(group3, null, vote6);
        createGroupAssigment(group3, null, vote7);
        createGroupAssigment(group3, null, vote8);
        createGroupAssigment(group3, null, vote9);
        createGroupAssigment(group3, null, vote10);
        createGroupAssigment(group3, null, vote11);


        //create questions
        //question of vote1
        Long question1 = createQuestion("How often do you brush your teeth?", vote1, null, null);
        Long question2 = createQuestion("Do you like animals?", vote1, null, null);
        Long question3 = createQuestion("Have you ever been in Germany?", vote1, null, null);
        //question of vote2
        Long question4 = createQuestion("How many ECTS did you get?", vote2, null, null);
        Long question5 = createQuestion("Do you play soccer?", vote2, null, null);
        Long question6 = createQuestion("Resolve equation 2+2*2", vote2, null, null);
        Long question7 = createQuestion("What is your favourite movie?", vote2, null, null);
        //question of vote3
        Long question8 = createQuestion("Elephant of giraffe?", vote3, "Picklist", Arrays.asList("Elephant", "Giraffe"));
        Long question9 = createQuestion("Dog or cat?", vote3, "Picklist", Arrays.asList("Dog", "Cat"));
        Long question10 = createQuestion("Owl or mockingbird", vote3, "Picklist", Arrays.asList("Owl", "Mockingbird"));
        Long question11 = createQuestion("Goat or horse?", vote3, "Picklist", Arrays.asList("Goat", "Horse"));
        Long question12 = createQuestion("Monkey or snake?", vote3, "Picklist", Arrays.asList("Monkey", "Snake"));
        Long question13 = createQuestion("Lion or Tiger?", vote3, "Picklist", Arrays.asList("Lion", "Tiger"));
        Long question14 = createQuestion("Duck or chicken?", vote3, "Picklist", Arrays.asList("Duck", "Chicken"));
        Long question15 = createQuestion("Bear or alligator?", vote3, "Picklist", Arrays.asList("Bear", "Alligator"));
        Long question16 = createQuestion("Hippo or rhino?", vote3, "Picklist", Arrays.asList("Hippo", "Rhino"));
        Long question17 = createQuestion("Whale or seal?", vote3, "Picklist", Arrays.asList("Whale", "Seal"));
        //question of vote4
        Long question18 = createQuestion("Do you have back cramps? Describe them.", vote4, null, null);
        Long question19 = createQuestion("Do you have back tremors? Describe them", vote4, null, null);
        //question of vote5
        Long question20 = createQuestion("Do you have any idea for a gift for Jaro?", vote5, null, null);
        //question of vote6
        Long question21 = createQuestion("Rate your feelings about new iPhone", vote6, null, null);
        //question of vote7
        Long question22 = createQuestion("Choose one!", vote7, null, null);
        //question of vote8
        Long question23 = createQuestion("Rate scenario! Describe your impressions", vote8, null, null);
        //question of vote9
        Long question24 = createQuestion("One cannot live while the other is alive", vote9, null, null);
        //question of vote10
        Long question25 = createQuestion("What do you think, what should be the next step for the family", vote10, null, null);
        //question of vote10
        Long question26 = createQuestion("Should we invade Ukraine?", vote11, "Checkbox", null);


        //create assigments, do we need assigments question to vote when question has reference to vote?
        /*
        createUserSurvey(vote1, question1);
        createUserSurvey(vote1, question2);
        createUserSurvey(vote1, question3);

        createUserSurvey(vote2, question4);
        createUserSurvey(vote2, question5);
        createUserSurvey(vote2, question6);
        createUserSurvey(vote2, question7);
         */

        //createAnswer
        createAnswer(vote1, question1, "2x per day");
        createAnswer(vote1, question1, "3x per day");
        createAnswer(vote1, question1, "Never");
        createAnswer(vote1, question2, "Yes");
        createAnswer(vote1, question2, "Oh yeees");
        createAnswer(vote1, question2, "No man");
        createAnswer(vote1, question3, "Never");
        createAnswer(vote1, question3, "Once");
        createAnswer(vote1, question3, "No, but I am going to visit this country next summer hihi");
        createAnswer(vote2, question4, "20");
        createAnswer(vote2, question4, "30");
        createAnswer(vote2, question4, "40");
        createAnswer(vote2, question5, "nope");
        createAnswer(vote2, question5, "yea");
        createAnswer(vote2, question5, "Mhm");
        createAnswer(vote2, question6, "12");
        createAnswer(vote2, question6, "4");
        createAnswer(vote2, question6, "8");
        createAnswer(vote2, question7, "Jumanji");
        createAnswer(vote2, question7, "Pulp Fiction");
        createAnswer(vote2, question7, "Tabaluga");
    }

    private Long createVote(String name, String password, String description,
                            Integer numberOfQuestion, Long authorId, Boolean isPublic,
                            Integer startDay, Integer startMonth, Integer endDay, Integer endMonth) {
        Vote vote = new Vote();
        vote.setVoteTitle(name);
        vote.setStartDate(Utils.getCurrentDate(2020, startMonth, startDay));
        vote.setEndDate(Utils.getCurrentDate(2020,endMonth,endDay));
        vote.setAuthor_id(authorId);
        vote.setPublicVote(isPublic);
        vote.setAnonymousVote(true);
        vote.setMandatory(false);
        vote.setSurveyDescription(description);
        vote.setVotePassword(password);
        vote.setNumberOfQuestions(numberOfQuestion);
        voteRepository.save(vote);
        return vote.getId();
    }

    private Long createUser(String name, String city, String phone, String userType){
        User user = new User();
        user.setName(name);
        user.setActive(true);
        user.setCity(city);
        user.setEmail(name + "@gmail.com");
        user.setMobileNumber(phone);
        user.setUserType(userType);
        user.setPassword("test1234");
        userRepository.save(user);
        return user.getId();
    }

    private Long createQuestion(String questionContent, Long voteId, String questionType, List<String> picklistValues){
        Question question = new Question();
        question.setVote_id(voteId);
        question.setMandatoryQuestion(true);
        question.setMaximumCapacityOfAnswer(50);
        question.setMultipleChoice(false);
        question.setQuestionContent(questionContent);
        question.setQuestionType(questionType);
        question.setPicklistValues(picklistValues);
        questionRepository.save(question);
        return question.getId();
    }

    private void createUserSurvey(Long userId, Long questionId){
        UserSurvey userQuestion = new UserSurvey();
        userQuestion.setAnswerHasBeenGiven(true);
        userQuestion.setUser_id(userId);
        userQuestion.setQuestion_id(questionId);
        userSurveyRepository.save(userQuestion);
    }

    private void createAnswer(Long voteId, Long questionId, String contentOfAnswer){
        Answer answer = new Answer();
        answer.setQuestion_id(questionId);
        answer.setVote_id(voteId);
        answer.setAnswerContent(contentOfAnswer);
        answerRepository.save(answer);
    }

    private Long createGroup(String groupName, String groupDescription, Boolean isPublic, Long authorId){
        Group__c group = new Group__c();
        group.setName(groupName);
        group.setDescription(groupDescription);
        group.setActive(true);
        group.setPublic(isPublic);
        group.setOwner_id(authorId);
        groupRepository.save(group);
        return group.getId();
    }

    private void createGroupAssigment(Long groupId, Long userId, Long voteId){
        GroupAssigment assigment = new GroupAssigment();
        assigment.setGroup_Id(groupId);
        assigment.setUser_Id(userId);
        assigment.setVote_Id(voteId);
        groupAssigmentRepository.save(assigment);
    }
}
