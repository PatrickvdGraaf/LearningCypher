package Setup;

import Enums.EmployeeRelationship;
import Enums.HrLabels;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import java.util.GregorianCalendar;

/**
 * This class is used to inject some test values into our Database
 * Created by Patrick van de Graaf
 */
public class DatabaseSetup {

    /**
     * Properties of a cost center
     * @see Enums.HrLabels
     */
    public static class CostCenter{
        public static final String CODE = "code";
    }

    /**
     * Properties of an employee
     * @see Enums.HrLabels
     */
    public static class Employee{
        public static final String NAME = "name";
        public static final String MIDDLE_NAME = "middleName";
        public static final String SURNAME = "surname";
    }

    public static void setup(GraphDatabaseService graphDb){
        try(Transaction tx = graphDb.beginTx()){
            Node cc1 = graphDb.createNode(HrLabels.CostCenter);
            cc1.setProperty(CostCenter.CODE, "CC1");

            Node cc2 = graphDb.createNode(HrLabels.CostCenter);
            cc1.setProperty(CostCenter.CODE, "CC2");

            Node davies = graphDb.createNode(HrLabels.Employee);
            davies.setProperty(Employee.NAME, "Nathan");
            davies.setProperty(Employee.SURNAME, "Davies");

            Node taylor = graphDb.createNode(HrLabels.Employee);
            taylor.setProperty(Employee.NAME, "Rose");
            taylor.setProperty(Employee.SURNAME, "Taylor");

            Node underwood = graphDb.createNode(HrLabels.Employee);
            underwood.setProperty(Employee.NAME, "Heather");
            underwood.setProperty(Employee.MIDDLE_NAME, "Mary");
            underwood.setProperty(Employee.SURNAME, "Underwood");

            Node smith = graphDb.createNode(HrLabels.Employee);
            smith.setProperty(Employee.NAME, "John");
            smith.setProperty(Employee.SURNAME, "Smith");

            //There is a vacant post in the company
            Node vacantPost = graphDb.createNode();

            //Davies belongs to CC1
            davies.createRelationshipTo(cc1, EmployeeRelationship.BELONGS_TO);

            //...and reports to Taylor
            davies.createRelationshipTo(taylor, EmployeeRelationship.REPORTS_TO);

            //Taylor is manager of CC1
            taylor.createRelationshipTo(cc1, EmployeeRelationship.MANAGER_OF)
                    .setProperty(EmployeeRelationship.FROM, new GregorianCalendar(2010, 2, 8).getTimeInMillis());

            //Smith belongs to CC2 from 2008
            smith.createRelationshipTo(cc2, EmployeeRelationship.BELONGS_TO)
                    .setProperty(EmployeeRelationship.FROM, new GregorianCalendar(2008,9,20).getTimeInMillis());

            //Smith reports to Underwood
            smith.createRelationshipTo(underwood, EmployeeRelationship.REPORTS_TO);

            //Underwood belongs to CC2
            underwood.createRelationshipTo(cc2, EmployeeRelationship.BELONGS_TO);

            //Underwood will report to an employee not yet hired
            underwood.createRelationshipTo(vacantPost, EmployeeRelationship.REPORTS_TO);

            //But the vacant post will belong to CC2
            vacantPost.createRelationshipTo(cc2, EmployeeRelationship.BELONGS_TO);

            tx.success();
        }
    }

}
