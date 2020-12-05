package pl.voteapp.model;

import org.hibernate.annotations.CreationTimestamp;
import pl.voteapp.wrappers.SurveyWrapper;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.util.List;

@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String voteTitle;
    private String surveyDescription;
    @CreationTimestamp
    private Date createdDate;
    private Long author_id;
    private Date startDate;
    private Date endDate;
    private Boolean isPublicVote;
    private Boolean isAnonymousVote;
    private Boolean isMandatory;
    private String votePassword;
    private Integer numberOfQuestions;
    private String picture_name;

    public Vote() {
    }

    public Vote(SurveyWrapper surveyWrapper){
        this.voteTitle = surveyWrapper.voteTitle;
        this.author_id = surveyWrapper.author_id;
        this.isMandatory = surveyWrapper.isMandatory;
        this.isPublicVote = surveyWrapper.isPublicVote;
        this.numberOfQuestions = surveyWrapper.numberOfQuestions;
        this.isAnonymousVote = surveyWrapper.isAnonymousVote;
        this.startDate = surveyWrapper.startDate;
        this.endDate = surveyWrapper.endDate;
        this.picture_name = surveyWrapper.surveyPicture;
        this.surveyDescription = surveyWrapper.surveyDescription;
        this.voteTitle = surveyWrapper.voteTitle;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVoteTitle() {
        return voteTitle;
    }

    public void setVoteTitle(String voteTitle) {
        this.voteTitle = voteTitle;
    }

    public String getSurveyDescription() {
        return surveyDescription;
    }

    public void setSurveyDescription(String surveyDescription) {
        this.surveyDescription = surveyDescription;
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

    public String getVotePassword() {
        return votePassword;
    }

    public void setVotePassword(String votePassword) {
        this.votePassword = votePassword;
    }

    public Integer getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(Integer numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public String getPicture_name() {
        return picture_name;
    }

    public void setPicture_name(String picture_name) {
        this.picture_name = picture_name;
    }

    @Override
    public String toString() {
        return voteTitle + " from: " + startDate + " to: " + endDate;
    }
}
