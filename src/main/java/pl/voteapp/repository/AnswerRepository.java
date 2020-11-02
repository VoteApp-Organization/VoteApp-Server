package pl.voteapp.repository;

import org.springframework.data.repository.CrudRepository;
import pl.voteapp.model.Answer;

public interface AnswerRepository extends CrudRepository<Answer, Long> {
}
