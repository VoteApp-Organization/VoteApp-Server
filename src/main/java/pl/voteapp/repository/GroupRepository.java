package pl.voteapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.voteapp.model.Group__c;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group__c, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM Group__c WHERE is_public = true AND LOWER(NAME) LIKE LOWER(CONCAT('%',:name,'%'))")
    public List<Group__c> findGroupByName(@Param("name") String name);
}
