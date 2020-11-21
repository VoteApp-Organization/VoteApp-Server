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
        Long user4 = createUser("Marian", "Gońcher","72483762", "Client");
        Long user5 = createUser("Agnieszka", "Orzeszek","7241642", "Client");
        Long user6 = createUser("Andrzej", "Szyszka","453098342", "Priest");

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
                3,
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


        //create groups
        Group__c group1 = createGroup("Dormitory", "Group of residents of the dormitory", false, user1);
        Group__c group2 = createGroup("College", "Best group ever", false, user1);
        Group__c group3 = createGroup("Poland Country", "Group of all citizens of Poland!", true, user1);
        Group__c group4 = createGroup("The Majcher family", "Group of all Majachers in Skierniewice", false, user2);

        //create assigments groups
        createGroupAssigment(group1.getId(), user1, null);
        createGroupAssigment(group1.getId(), user2, null);
        createGroupAssigment(group2.getId(), user1, null);
        createGroupAssigment(group2.getId(), user2, null);
        createGroupAssigment(group3.getId(), user1, null);
        createGroupAssigment(group3.getId(), user2, null);
        createGroupAssigment(group3.getId(), user3, null);
        createGroupAssigment(group4.getId(), user2, null);

        createGroupAssigment(group3.getId(), null, vote1);
        createGroupAssigment(group1.getId(), null, vote2);
        createGroupAssigment(group2.getId(), null, vote2);
        createGroupAssigment(group3.getId(), null, vote2);
        createGroupAssigment(group4.getId(), null, vote2);


        //create questions
        Question question1 = createQuestion("How often do you brush your teeth?", vote1);
        Question question2 = createQuestion("Do you like animals?", vote1);
        Question question3 = createQuestion("Have you ever been in Germany?", vote1);
        Question question4 = createQuestion("How many ECTS did you get?", vote2);
        Question question5 = createQuestion("Do you play soccer?", vote2);
        Question question6 = createQuestion("Resolve equation 2+2*2", vote2);
        Question question7 = createQuestion("What is your favourite movie?", vote2);

        //create assigments
        createUserSurvey(vote1, question1.getId());
        createUserSurvey(vote1, question2.getId());
        createUserSurvey(vote1, question3.getId());
        createUserSurvey(vote2, question4.getId());
        createUserSurvey(vote2, question5.getId());
        createUserSurvey(vote2, question6.getId());
        createUserSurvey(vote2, question7.getId());

        //i think we can delete voteId from object Vote later
        createAnswer(vote1, question1.getId(), "2x per day");
        createAnswer(vote1, question1.getId(), "3x per day");
        createAnswer(vote1, question1.getId(), "Never");
        createAnswer(vote1, question2.getId(), "Yes");
        createAnswer(vote1, question2.getId(), "Oh yeees");
        createAnswer(vote1, question2.getId(), "No man");
        createAnswer(vote1, question3.getId(), "Never");
        createAnswer(vote1, question3.getId(), "Once");
        createAnswer(vote1, question3.getId(), "No, but I am going to visit this country next summer hihi");
        createAnswer(vote2, question4.getId(), "20");
        createAnswer(vote2, question4.getId(), "30");
        createAnswer(vote2, question4.getId(), "40");
        createAnswer(vote2, question5.getId(), "nope");
        createAnswer(vote2, question5.getId(), "yea");
        createAnswer(vote2, question5.getId(), "Mhm");
        createAnswer(vote2, question6.getId(), "12");
        createAnswer(vote2, question6.getId(), "4");
        createAnswer(vote2, question6.getId(), "8");
        createAnswer(vote2, question7.getId(), "Jumanji");
        createAnswer(vote2, question7.getId(), "Pulp Fiction");
        createAnswer(vote2, question7.getId(), "Tabaluga");
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

    private Question createQuestion(String questionContent, Long questionId){
        Question question = new Question();
        question.setVote_id(questionId);
        question.setMandatoryQuestion(true);
        question.setMaximumCapacityOfAnswer(50);
        question.setMultipleChoice(false);
        question.setQuestionContent(questionContent);
        questionRepository.save(question);
        return question;
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
