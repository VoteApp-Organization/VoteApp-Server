package pl.voteapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.voteapp.model.Question;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM Question WHERE vote_id = :voteId")
    public List<Question> findBySurveyId(@Param("voteId") Long voteId);
}
