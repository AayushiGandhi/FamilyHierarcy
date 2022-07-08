//Interface with all the values that are constant through out the program
public interface Constant {
	//Credentials to make connection with SQL database
	String  user = "root";
	String password = "root123";
	String url = "jdbc:mysql://localhost/project";
	
	//Table names for SQL database
	String tableName = "FAMILYTREE";
	String occupationTable = "occupation";
	String refNotesTable = "REFERENCENOTES";
	String childTable = "CHILDTABLE";
	String partnerTable = "PARTNERTABLE";
	String mediaTable = "MEDIAARCHIVE";
	String tagTable = "TAGTABLE";
	String peopleInMediaTable = "PEOPLEINMEDIATABLE";
	String mediaDateTable = "mediaDateTable";
	String attributesTableForPerson = "attributesTableForPerson";
	String attributesTableForMedia = "attributesTableForMedia";
}
