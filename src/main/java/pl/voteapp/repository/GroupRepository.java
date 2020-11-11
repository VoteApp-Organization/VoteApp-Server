package pl.voteapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.voteapp.model.Group__c;

public interface GroupRepository extends JpaRepository<Group__c, Long> {
}
