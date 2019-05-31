package hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class GetNodeController {

    private NodeRepository repository;

    public GetNodeController(NodeRepository repository) {
        this.repository = repository;
    }

    @RequestMapping("/greeting")
    public Node getNode(@RequestParam(value="id", defaultValue="0") long id) {
        Optional<Node> node = repository.findById(id);
        return node.get();
    }
}
