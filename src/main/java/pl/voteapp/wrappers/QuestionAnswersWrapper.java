package pl.voteapp.wrappers;

import pl.voteapp.model.Answer;
import pl.voteapp.model.Question;

import java.util.List;

public class QuestionAnswersWrapper {
    public Long questionId;
    public String questionContent;
    public List<Answer> answers;
    public Boolean multipleChoice;
    public Boolean mandatoryQuestion;
    public Integer maximumCapacityOfAnswer;
    public String questionType;

    public QuestionAnswersWrapper(Question question, List<Answer> answers) {
        this.questionId = question.getId();
        this.answers = answers;
        this.multipleChoice = question.getMultipleChoice();
        this.mandatoryQuestion = question.getMandatoryQuestion();
        this.maximumCapacityOfAnswer = question.getMaximumCapacityOfAnswer();
        this.questionType = question.getQuestionType();
        this.questionContent = question.getQuestionContent();
    }
}
