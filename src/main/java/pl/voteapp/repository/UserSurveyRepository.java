package pl.voteapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.voteapp.model.UserSurvey;

import java.util.List;

public interface UserSurveyRepository extends JpaRepository<UserSurvey, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM User_Survey WHERE user_id = :userId")
    public List<UserSurvey> findUserSurveys(@Param("userId") Long userId);
}
