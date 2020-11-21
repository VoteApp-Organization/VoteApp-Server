package pl.voteapp;

import pl.voteapp.model.UserSurvey;
import pl.voteapp.model.Vote;

import java.sql.Date;

public class QuestionWrapper {

    private Long vote_Id;
    private String voteTitle;
    private Date createdDate;
    private Long author_id;
    private Date startDate;
    private Date endDate;
    private Boolean isPublicVote;
    private Boolean isAnonymousVote;
    private Boolean isMandatory;
    public Boolean answerHasBeenGiven;
    public Date voteDate;
    public Integer numberOfQuestions;

    public QuestionWrapper(Vote vote, UserSurvey userQuestion) {
        this.vote_Id = vote.getId();
        this.voteTitle = vote.getVoteTitle();
        this.createdDate = vote.getCreatedDate();
        this.author_id = vote.getAuthor_id();
        this.startDate = vote.getStartDate();
        this.endDate = vote.getEndDate();
        this.isPublicVote = vote.getPublicVote();
        this.isMandatory = vote.getMandatory();
        this.numberOfQuestions = vote.getNumberOfQuestions();
        this.answerHasBeenGiven = userQuestion.getAnswerHasBeenGiven();
        this.voteDate = userQuestion.getVoteDate();
    }

    public String getVoteTitle() {
        return voteTitle;
    }

    public void setVoteTitle(String voteTitle) {
        this.voteTitle = voteTitle;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Long author_id) {
        this.author_id = author_id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getPublicVote() {
        return isPublicVote;
    }

    public void setPublicVote(Boolean publicVote) {
        isPublicVote = publicVote;
    }

    public Boolean getAnonymousVote() {
        return isAnonymousVote;
    }

    public void setAnonymousVote(Boolean anonymousVote) {
        isAnonymousVote = anonymousVote;
    }

    public Boolean getMandatory() {
        return isMandatory;
    }

    public void setMandatory(Boolean mandatory) {
        isMandatory = mandatory;
    }

    public Date getVoteDate() {
        return voteDate;
    }

    public void setVoteDate(Date voteDate) {
        this.voteDate = voteDate;
    }

    public Boolean getAnswerHasBeenGiven() {
        return answerHasBeenGiven;
    }

    public void setAnswerHasBeenGiven(Boolean answerHasBeenGiven) {
        this.answerHasBeenGiven = answerHasBeenGiven;
    }

}
