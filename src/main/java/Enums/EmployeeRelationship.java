package Enums;

import org.neo4j.graphdb.RelationshipType;

/**
 * This class is used to define the types of relationships possible between Employee, CostCenters, and possible other enums from the HrLabels enum class
 * Created by Patrick van de Graaf
 *
 * @see HrLabels
 */
public enum EmployeeRelationship implements RelationshipType {
    REPORTS_TO,
    BELONGS_TO,
    MANAGER_OF;
    public static final String FROM = "from";
}
