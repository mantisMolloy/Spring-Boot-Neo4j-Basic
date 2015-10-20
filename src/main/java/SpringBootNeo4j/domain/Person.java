package SpringBootNeo4j.domain;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import java.util.HashSet;
import java.util.Set;

import static org.neo4j.graphdb.Direction.*;

/**
 * Created by tmolloy on 20/10/2015.
 */

@NodeEntity
public class Person {

    @GraphId Long id;
    public String name;

    public Person(){}
    public Person(String name){ this.name = name; }


    /*
    bi-directional relationship.
    Use @fetch to eagerly retrive teamates
     */
    @RelatedTo(type="TEAMAMTE", direction= BOTH)
    public @Fetch Set<Person> teammates;

    public void worksWith(Person person){
        if (teammates == null) teammates = new HashSet<Person>();
        teammates.add(person);
    }

    @Override
    public String toString() {
        String results = name + "'s teammates include\n";
        if (teammates != null) {
            for (Person person : teammates) {
                results += "\t- " + person.name + "\n";
            }
        }
        return results;
    }
}
