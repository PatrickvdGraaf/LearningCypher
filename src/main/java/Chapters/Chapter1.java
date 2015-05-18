package Chapters;

import org.neo4j.cypher.javacompat.ExecutionResult;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used to summarise tha main points of interest in Chapter 1 of Learning Cypher
 * Created by Patrick van de Graaf
 */
public class Chapter1 extends Chapter{

    public Chapter1(){
        super();

        //For test purposes, this only needed to be executed once.
        //It was done 3 times, so there are duplicates in the database
        //Setup.DatabaseSetup.setup(graphDb);

        //Getting all Employees
        operation = "MATCH (e:Employee) RETURN e";
        executeOperation();

        //Alternative way of getting all Employees
        //ResourceIterable<Node> empl = GlobalGraphOperations.at(graphDb).getAllNodesWithLabel(HrLabels.Employee);

        /**
         * Simple first Cypher call
         */
        //Get all Employees and their CostCenter
        operation = "MATCH (n:Employee) --> (cc:CostCenter)" +
                "RETURN *";
        executeOperation();

        /**
         * Relationships and parameters
         */
        //Get all Employees in CC2
        //The --> shows the direction of the relationship
        //(cc:CostCenter { code: 'CC2' })  <-- (n:Employee) would give the same result
        //If we don't care about the preferred direction, we cna use --
        operation = "MATCH (n:Employee) -- (cc:CostCenter { code: 'CC2' })" +
                "RETURN n";
        executeOperation();

        /**
         * Specified relationships
         */
        //Get the existing relationship between the employees and cost centers
        //Relationships must be specified in square brackets
        //We can specify multiple relationships using the | operator
        operation = "MATCH (n:Employee) -[r:BELONGS_TO | MANAGER_OF]- (:CostCenter { code: 'CC2' })" +
                "RETURN n.surname, n.name, r";
        executeOperation();

        //We go a step further, this operation means; Node n belonging to the cost center having a manager m
        operation = "MATCH n -[BELONGS_TO]-> (cc:CostCenter) <-[:MANAGER_OF]- (m)" +
                "RETURN n.surname, m.surname, cc.code";
        executeOperation();

        /**
         * Neighborhood, the nodes that you can reach
         */
        //Get nodes reachable from one node with a certain number of depth
        //This is one of the strong points of graph databases
        //DISTINCT makes sure no value is shown twice
        operation = "MATCH (:Employee{surname: 'Smith'}) -[*2]- (neighborhood)" +
                "RETURN DISTINCT neighborhood";
        executeOperation();

        //By changing a little bit, we can ask for the relationships we visited
        //This might give you some insight in what this 'depth' really stands for
        operation = "MATCH (:Employee{surname: 'Davies'}) -[r*2]- (neighborhood)" +
                "RETURN neighborhood, r";
        executeOperation();

        //This can be used in many real-world applications, for example; a social media app that suggests people you might know
        //With a range of steps, you can easely make a list of people, and set teh boundaries by increasing the maximum numver of steps
        operation = "MATCH (:Employee{surname: 'Davies'}) -[r*0..2]- (neighborhood)" +
                "RETURN neighborhood, r";
        executeOperation();

        /**
         * Optional match
         */
        //An OPTIONAL MATCH is a keyqord that allows us to use any pattern expression that can be used in the MATCH clause.
        //but it describes only a pattern that could match. If the pattern does not match, the OPTIONAL MATCH clause sets any variable to null
        //The OPTIONAL MATCH is a similar type of relation as a OUTER JOIN in SQL
        operation = "MATCH (e:Employee)" +
                "OPTIONAL MATCH (e) <-[:REPORTS_TO]- (m:Employee)" +
                "RETURN e.surname, m.surname";
        executeOperation();

        /**
         * Paths
         */
        //This path assignment returns all possible paths between two nodes
        operation = "MATCH path = (a{surname:'Davies'}) -[*]- (b{surname:'Taylor'})" +
                "RETURN path";
        executeOperation();

        //But what if we want to find the shortest path, without first getting al results, then iterating them and finding the shortest one?
        //allShortestPaths is a standard Cypher function
        //In RETURN, we provide an alias, 'path' with the AS keyword. It specifies the name of the column returned
        operation = "MATCH (a{surname:'Davies'}), (b{surname:'Taylor'})" +
                "RETURN allShortestPaths((a) -[*]- (b)) AS path";
        executeOperation();

        /**
         * ID's as starting points
         */
        //We can let Cypher ind the starting points of a query on its own, but we can also specify them because we want to search a pattern
        //that starts with a specific node, or a specific relation resulting in an important improvement in performance of hte query
        operation = "START a=node(2), b=node(3)" +
                "RETURN allShortestPaths((a) -[*]- (b)) AS path";
        executeOperation();
        //PS: if you would compare the time elapsed in executing this query and the previous one, we can easely prove that the latter is
        //dramatically faster. The drawback is, of course, that we need to know the ID of the node

        /**
         * Parameters
         */
        //If we want to change the value of a parameter, we place them between curly brakets. This way, Cypher can assingn a value to it
        //In Java, Cypher wants us to pass all parameters in a Map
        String name = "Davies";
        Map<String, Object> params = new
                HashMap<>();
        params.put("inputSurname", name);
        operation = "MATCH (n:Employee {surname: {inputSurname} })" +
                "RETURN n";

        ExecutionResult result = engine.execute(operation, params);
        String dumped = result.dumpToString();
        System.out.println(operation);
        System.out.println(dumped);
    }
}
