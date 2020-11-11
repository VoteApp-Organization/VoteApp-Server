package pl.voteapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.voteapp.model.Question;
import pl.voteapp.model.UserQuestion;

import java.util.List;

public interface UserQuestionRepository extends JpaRepository<UserQuestion, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM User_Question WHERE user_id = :userId")
    public List<UserQuestion> findUserGivenAnswers(@Param("userId") Long userId);
}
