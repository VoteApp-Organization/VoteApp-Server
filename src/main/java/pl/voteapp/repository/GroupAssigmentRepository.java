package pl.voteapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.voteapp.model.GroupAssigment;

import java.util.List;

public interface GroupAssigmentRepository extends JpaRepository<GroupAssigment, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM Group_Assigment WHERE user_id = :userId")
    public List<GroupAssigment> findAssigmentUserToGroups(@Param("userId") Long userId);
}
