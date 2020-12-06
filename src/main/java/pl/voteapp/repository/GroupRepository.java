package pl.voteapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.voteapp.model.Group__c;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GroupRepository extends JpaRepository<Group__c, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM Group__c WHERE is_public = true AND LOWER(NAME) LIKE LOWER(CONCAT('%',:name,'%'))")
    public List<Group__c> findGroupByName(@Param("name") String name);

    @Query(nativeQuery = true, value = "SELECT * FROM Group__c WHERE is_public = true AND LOWER(NAME) LIKE LOWER(CONCAT('%',:name,'%')) AND owner_id != :userId NOT IN :idList")
    public List<Group__c> exploreGroupByName(@Param("name") String name, @Param("idList") Set<Long> idList, @Param("userId") Long userId);

    @Query(nativeQuery = true, value = "SELECT Id FROM Group__c WHERE owner_id = :userId")
    public List<Long> findGroupByAuthorId(@Param("userId") Long userId);

    @Query(nativeQuery = true, value = "SELECT * FROM Group__c WHERE group_password = :password LIMIT 1")
    public Optional<Group__c> findGroupByPassword(@Param("password") String password);

    @Query(nativeQuery = true, value = "SELECT group_password FROM Group__c WHERE is_public = TRUE")
    public Set<Long> getAllPasswords();
}
