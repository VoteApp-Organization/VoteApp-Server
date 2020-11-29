package pl.voteapp;

public class GroupAssigmentWrapper {
    private Long group_Id;
    private Long vote_Id;
    private Long user_Id;
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
