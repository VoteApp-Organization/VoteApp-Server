package pl.voteapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.voteapp.model.UserSurvey;

import java.util.List;

public interface UserSurveyRepository extends JpaRepository<UserSurvey, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM User_Survey WHERE user_id = :userId")
    public List<UserSurvey> findUserSurveys(@Param("userId") Long userId);

    @Query(nativeQuery = true, value = "SELECT * FROM User_Survey WHERE survey_id = :surveyId")
    public List<UserSurvey> findVoteUsers(@Param("surveyId") Long surveyId);

    @Query(nativeQuery = true, value = "SELECT * FROM User_Survey WHERE survey_id = :surveyId AND user_id = :userId LIMIT 1")
    public UserSurvey findUsersVote(@Param("surveyId") Long surveyId, @Param("userId") Long userId);
}
