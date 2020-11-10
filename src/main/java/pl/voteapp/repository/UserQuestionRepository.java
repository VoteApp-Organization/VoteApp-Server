package pl.voteapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import pl.voteapp.model.UserQuestion;

public interface UserQuestionRepository extends JpaRepository<UserQuestion, Long> {
}
