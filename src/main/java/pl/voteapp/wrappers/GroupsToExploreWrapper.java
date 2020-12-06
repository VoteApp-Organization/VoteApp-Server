package pl.voteapp.wrappers;

import pl.voteapp.model.Group__c;

public class GroupsToExploreWrapper {
    public Group__c group;
    public Long numberOfUsers;
    public Long numberOfSurveys;

    public GroupsToExploreWrapper(Group__c group, Long numberOfUsers, Long numberOfSurveys) {
        this.group = group;
        this.numberOfUsers = numberOfUsers;
        this.numberOfSurveys = numberOfSurveys;
    }
}
