package Enums;

import org.neo4j.graphdb.Label;

/**
 * Created by Patrick van de Graaf
 *
 * Human resource labels, used in the Cypher example
 * Employees can have 3 kind of relationships:
 *
 *  Emps that belong to cost centers
 *  Emps that report to other employees
 *  Emps that can be managers of a cost center
 *
 *  @see EmployeeRelationship
 */
public enum HrLabels implements Label{
    Employee,
    CostCenter
}
