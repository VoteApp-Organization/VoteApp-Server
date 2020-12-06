package pl.voteapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.voteapp.model.Vote;

import java.util.List;
import java.util.Set;


public interface VoteRepository extends JpaRepository<Vote, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM Vote WHERE id = :voteId")
    public List<Vote> findGroupSurveys(@Param("voteId") Long voteId);
}
