package pl.voteapp.wrappers;

import java.sql.Date;
import java.util.List;

//Wrapper used during creating survey
public class SurveyWrapper {
    public Long author_id;
    public Long group_id;
    public Boolean isMandatory;
    public Boolean isAnonymousVote;
    public Boolean isPublicVote;
    public Integer numberOfQuestions;
    public Date startDate;
    public Date endDate;
    public String surveyPicture;
    public String surveyDescription;
    public String voteTitle;
    public List<SurveyQuestion> questions;
}
