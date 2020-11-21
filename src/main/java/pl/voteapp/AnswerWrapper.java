package pl.voteapp;

public class AnswerWrapper {
    public Long question_id;
    public Long vote_id;
    public String answerContent;

    public AnswerWrapper() {
    }

    public AnswerWrapper(Long question_id, Long vote_id, String answerContent) {
        this.question_id = question_id;
        this.vote_id = vote_id;
        this.answerContent = answerContent;
    }
}
