package pl.voteapp.model;

import pl.voteapp.wrappers.SurveyQuestion;
import pl.voteapp.wrappers.SurveyWrapper;

import javax.persistence.*;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long vote_id;
    public String questionContent;
    public Boolean multipleChoice;
    public Boolean mandatoryQuestion;
    public Integer maximumCapacityOfAnswer;
    public String questionType;
    @ElementCollection
    public List<String> picklistValues;

    public Question() {
    }

    public Question(SurveyQuestion surveyQuestion) {
        this.questionContent = surveyQuestion.questionContent;
        this.multipleChoice = surveyQuestion.multipleChoice;
        this.mandatoryQuestion = surveyQuestion.mandatoryQuestion;
        this.maximumCapacityOfAnswer = surveyQuestion.maximumCapacityOfAnswer;
        this.questionType = surveyQuestion.questionType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVote_id() {
        return vote_id;
    }

    public void setVote_id(Long vote_id) {
        this.vote_id = vote_id;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public Boolean getMultipleChoice() {
        return multipleChoice;
    }

    public void setMultipleChoice(Boolean multipleChoice) {
        this.multipleChoice = multipleChoice;
    }

    public Boolean getMandatoryQuestion() {
        return mandatoryQuestion;
    }

    public void setMandatoryQuestion(Boolean mandatoryQuestion) {
        this.mandatoryQuestion = mandatoryQuestion;
    }

    public Integer getMaximumCapacityOfAnswer() {
        return maximumCapacityOfAnswer;
    }

    public void setMaximumCapacityOfAnswer(Integer maximumCapacityOfAnswer) {
        this.maximumCapacityOfAnswer = maximumCapacityOfAnswer;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public List<String> getPicklistValues() {
        return picklistValues;
    }

    public void setPicklistValues(List<String> picklistValues) {
        this.picklistValues = picklistValues;
    }

    @Override
    public String toString() {
        return "vote_id: " + vote_id + " mandatory: " + mandatoryQuestion + " content: " + questionContent;
    }
}
