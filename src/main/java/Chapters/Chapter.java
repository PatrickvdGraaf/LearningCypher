package Chapters;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

/**
 * This class is used to...
 * Created by Patrick van de Graaf
 */
public abstract class Chapter {
    public static String operation;
    public static ExecutionEngine engine;

    public Chapter(){
        GraphDatabaseFactory graphDbFactory = new GraphDatabaseFactory();

        GraphDatabaseService graphDb = graphDbFactory.newEmbeddedDatabaseBuilder("data/dbName")
                .loadPropertiesFromFile("neo4j.properties")
                .newGraphDatabase();

        engine = new ExecutionEngine(graphDb);
    }

    public void executeOperation(){
        ExecutionResult result = engine.execute(operation);
        String dumped = result.dumpToString();
        System.out.println(operation);
        System.out.println(dumped);
    }
}
