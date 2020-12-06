package pl.voteapp.wrappers;

import pl.voteapp.model.UserSurvey;
import pl.voteapp.model.Vote;

import java.sql.Date;

public class QuestionWrapper {

    public Long vote_Id;
    public String voteTitle;
    public String surveyDescription;
    public Date createdDate;
    public Long author_id;
    public Date startDate;
    public Date endDate;
    public Boolean isPublicVote;
    public Boolean isAnonymousVote;
    public Boolean isMandatory;
    public Boolean answerHasBeenGiven;
    public Date voteDate;
    public Integer numberOfQuestions;
    public String surveyPicture;

    public QuestionWrapper(Vote vote, UserSurvey userQuestion) {
        this.vote_Id = vote.getId();
        this.voteTitle = vote.getVoteTitle();
        this.surveyDescription = vote.getSurveyDescription();
        this.createdDate = vote.getCreatedDate();
        this.author_id = vote.getAuthor_id();
        this.startDate = vote.getStartDate();
        this.endDate = vote.getEndDate();
        this.isPublicVote = vote.getPublicVote();
        this.isMandatory = vote.getMandatory();
        this.isAnonymousVote = vote.getAnonymousVote();
        this.numberOfQuestions = vote.getNumberOfQuestions();
        this.surveyPicture = vote.getPicture_name();
        this.answerHasBeenGiven = userQuestion.getAnswerHasBeenGiven();
        this.voteDate = userQuestion.getVoteDate();
    }

    public QuestionWrapper(Vote vote) {
        this.vote_Id = vote.getId();
        this.voteTitle = vote.getVoteTitle();
        this.surveyDescription = vote.getSurveyDescription();
        this.createdDate = vote.getCreatedDate();
        this.author_id = vote.getAuthor_id();
        this.startDate = vote.getStartDate();
        this.endDate = vote.getEndDate();
        this.isPublicVote = vote.getPublicVote();
        this.isMandatory = vote.getMandatory();
        this.isAnonymousVote = vote.getAnonymousVote();
        this.numberOfQuestions = vote.getNumberOfQuestions();
        this.surveyPicture = vote.getPicture_name();
    }
}
