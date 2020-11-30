package pl.voteapp;

import pl.voteapp.model.Vote;
import pl.voteapp.wrappers.QuestionAnswersWrapper;

import java.sql.Date;
import java.util.List;

public class SurveyResultWrapper {
    public List<QuestionAnswersWrapper> questions;
    public String surveyTitle;
    public String surveyDescription;
    public Double voteTurnout;
    public Date createdDate;
    public Long author_id;
    public Date startDate;
    public Date endDate;
    public Boolean isPublicVote;
    public Boolean isAnonymousVote;
    public Boolean isMandatory;
    public Integer numberOfQuestions;
    public Integer enabledToVote;
    public Integer peopleWhoHasVote;

    public SurveyResultWrapper() {
    }

    public SurveyResultWrapper(Vote vote) {
        this.surveyTitle = vote.getVoteTitle();
        this.surveyDescription = vote.getSurveyDescription();
        this.createdDate = vote.getCreatedDate();
        this.author_id = vote.getAuthor_id();
        this.startDate = vote.getStartDate();
        this.endDate = vote.getEndDate();
        this.isPublicVote = vote.getPublicVote();
        this.isAnonymousVote = vote.getAnonymousVote();
        this.isMandatory = vote.getMandatory();
        this.numberOfQuestions = vote.getNumberOfQuestions();
    }
}
