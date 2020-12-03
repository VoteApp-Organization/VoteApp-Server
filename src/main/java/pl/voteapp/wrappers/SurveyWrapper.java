package pl.voteapp.wrappers;

import java.sql.Date;
import java.util.List;

//Wrapper used during creating survey
public class SurveyWrapper {
    public Long author_Id;
    public Boolean isMandatory;
    public Boolean isPublicVote;
    public Integer numberOfQuestion;
    public Date startDate;
    // should be optionaly
    // public Date endDate;
    public String surveyPicture;
    public String voteTitle;
    public List<SurveyQuestion> questions;



}
