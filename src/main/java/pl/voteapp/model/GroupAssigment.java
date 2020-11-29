package pl.voteapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GroupAssigment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long group_Id;
    private Long vote_Id;
    private Long user_Id;

    public GroupAssigment() {
    }

    public GroupAssigment(Long group_Id, Long user_Id) {
        this.group_Id = group_Id;
        this.user_Id = user_Id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroup_Id() {
        return group_Id;
    }

    public void setGroup_Id(Long group_Id) {
        this.group_Id = group_Id;
    }

    public Long getVote_Id() {
        return vote_Id;
    }

    public void setVote_Id(Long vote_Id) {
        this.vote_Id = vote_Id;
    }

    public Long getUser_Id() {
        return user_Id;
    }

    public void setUser_Id(Long user_Id) {
        this.user_Id = user_Id;
    }

    @Override
    public String toString() {
        return "group_Id: " + group_Id + " vote_Id: " + vote_Id + " user_Id: " + user_Id;
    }
}
