package SpringBootNeo4j;

import SpringBootNeo4j.domain.Person;
import SpringBootNeo4j.repository.PersonRepository;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.core.GraphDatabase;

import java.io.File;
import java.util.ArrayList;
import java.util.stream.Stream;

import static java.lang.System.out;


/**
 * Created by tmolloy on 20/10/2015.
 */

@SpringBootApplication
public class App implements CommandLineRunner {

    @Configuration
    @EnableNeo4jRepositories(basePackages = "SpringBootNeo4j")
    static class ApplicationConfig extends Neo4jConfiguration {

        public ApplicationConfig() {
            setBasePackage("SpringBootNeo4j");
        }

        @Bean
        GraphDatabaseService graphDatabaseService() {
            return new GraphDatabaseFactory().newEmbeddedDatabase("accessingdataneo4j.db");
        }
    }

    @Autowired PersonRepository personRepo;

    @Autowired GraphDatabase graphDatabase;

    public void run(String[] args) throws Exception {

        ArrayList<Person> members = new ArrayList<>();
        members.add(new Person("John"));
        members.add(new Person("Paul"));
        members.add(new Person("Ringo"));
        members.add(new Person("George"));

        Transaction tx = graphDatabase.beginTx();
        try {
            members.stream().forEach(b -> personRepo.save(b));

            //add relationships
            for(Person p1 : members) {
                members.stream()
                        .filter(b -> !(b.name.equals(p1.name)))
                        .forEach(b -> {
                            Person p = personRepo.findByName(p1.name);
                            p.worksWith(b);
                            personRepo.save(p);
                        });
            }

            //get info from neo4j
            out.println("Lookup each person by name...");
            members.stream()
                    .forEach(b -> out.println(personRepo.findByName(b.name)));


            out.println("Looking up who works with Paul...");
            personRepo.findByTeammatesName("Paul").forEach(out::println);

            tx.success();
        } finally {
            tx.close();
        }
    }
    public static void main(String[] args) throws Exception {
        FileUtils.deleteRecursively(new File("accessingdataneo4j.db"));
        SpringApplication.run(App.class, args);
    }
}
