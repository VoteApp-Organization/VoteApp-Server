package pl.voteapp.repository;

import org.springframework.data.repository.CrudRepository;
import pl.voteapp.model.Group;

public interface GroupRepository extends CrudRepository<Group, Long> {
}
