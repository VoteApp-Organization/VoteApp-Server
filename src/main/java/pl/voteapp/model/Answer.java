package pl.voteapp.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long vote_id;
    public Long question_id;
    public Long user_id;
    @ElementCollection
    public List<String> answerContent;

    public Answer() {
    }

    public Answer(Long vote_id, Long question_id, List<String> answerContent) {
        this.vote_id = vote_id;
        this.question_id = question_id;
        this.answerContent = answerContent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVote_id() {
        return vote_id;
    }

    public void setVote_id(Long vote_id) {
        this.vote_id = vote_id;
    }

    public Long getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(Long question_id) {
        this.question_id = question_id;
    }

    public List<String> getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(List<String> answerContent) {
        this.answerContent = answerContent;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "vote_id " + vote_id + " question_id: " + question_id + " answerContent: " + answerContent;
    }
}
