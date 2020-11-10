package pl.voteapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    @Override
    public String toString() {
        return "vote_id: " + vote_id + " mandatory: " + mandatoryQuestion + " content: " + questionContent;
    }
}
