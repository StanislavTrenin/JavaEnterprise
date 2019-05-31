package hello;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface NodeRepository extends CrudRepository<Node, Long> {

    //List<Node> findById(long id);
}
