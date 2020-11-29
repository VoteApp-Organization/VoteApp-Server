package pl.voteapp.wrappers;

import pl.voteapp.model.Answer;

import java.util.List;

public class QuestionAnswersWrapper {
    public Long questionId;
    public String questionContent;
    public List<Answer> answers;

    public QuestionAnswersWrapper(Long questionId, String questionContent, List<Answer> answers) {
        this.questionId = questionId;
        this.questionContent = questionContent;
        this.answers = answers;
    }
}
