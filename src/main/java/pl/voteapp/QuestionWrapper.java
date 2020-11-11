package pl.voteapp;

import pl.voteapp.model.Question;
import pl.voteapp.model.UserQuestion;

import java.sql.Date;

public class QuestionWrapper {

    public Long vote_id;
    public String questionContent;
    public Boolean multipleChoice;
    public Boolean mandatoryQuestion;
    public Integer maximumCapacityOfAnswer;
    public Boolean answerHasBeenGiven;
    public Date voteDate;

    public QuestionWrapper(Question question, UserQuestion userQuestion) {
        this.vote_id = question.getVote_id();
        this.questionContent = question.getQuestionContent();
        this.multipleChoice = question.getMultipleChoice();
        this.mandatoryQuestion = question.getMandatoryQuestion();
        this.maximumCapacityOfAnswer = question.getMaximumCapacityOfAnswer();
        this.answerHasBeenGiven = userQuestion.getAnswerHasBeenGiven();
        this.voteDate = userQuestion.getVoteDate();
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

    public Boolean getAnswerHasBeenGiven() {
        return answerHasBeenGiven;
    }

    public void setAnswerHasBeenGiven(Boolean answerHasBeenGiven) {
        this.answerHasBeenGiven = answerHasBeenGiven;
    }

}
