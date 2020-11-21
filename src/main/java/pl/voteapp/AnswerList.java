package pl.voteapp;

import pl.voteapp.model.Answer;

import java.util.List;

public class AnswerList {
    public List<Answer> answers;

    public AnswerList() {
    }

    public AnswerList(List<Answer> answers) {
        this.answers = answers;
    }

    public List<Answer> getCosTam() {
        return answers;
    }

    public void setCosTam(List<Answer> answers) {
        this.answers = answers;
    }
}
