package pl.voteapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.voteapp.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = "select * from User where phoneNumber = :phoneNumber LIMIT 1")
    public User findByPhoneNumber(@Param("phoneNumber") String phoneNumber);
}
