package SpringBootNeo4j.repository;

import SpringBootNeo4j.domain.Person;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by tmolloy on 20/10/2015.
 */
public interface PersonRepository extends CrudRepository <Person, String> {
    Person findByName(String name);
    Iterable<Person> findByTeammatesName(String name);
}
