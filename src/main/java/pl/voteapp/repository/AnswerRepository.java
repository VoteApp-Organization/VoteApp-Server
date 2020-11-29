package pl.voteapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.voteapp.model.Answer;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM Answer WHERE vote_id = :surveyId")
    public List<Answer> findSurveyAnswers(@Param("surveyId") Long surveyId);
}
