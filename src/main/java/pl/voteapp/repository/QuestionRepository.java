package pl.voteapp.repository;

import org.springframework.data.repository.CrudRepository;
import pl.voteapp.model.Question;

public interface QuestionRepository extends CrudRepository<Question, Long> {
}
