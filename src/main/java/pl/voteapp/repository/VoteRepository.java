package pl.voteapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.voteapp.model.Vote;

@Repository
public interface VoteRepository extends CrudRepository<Vote, Long> {
}
