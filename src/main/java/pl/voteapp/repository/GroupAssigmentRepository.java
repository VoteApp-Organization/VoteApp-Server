package pl.voteapp.repository;

import org.springframework.data.repository.CrudRepository;
import pl.voteapp.model.GroupAssigment;

public interface GroupAssigmentRepository extends CrudRepository<GroupAssigment, Long> {
}
