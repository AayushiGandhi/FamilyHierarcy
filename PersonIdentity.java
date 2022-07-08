import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class PersonIdentity {
	int id;
	String name;
	String dateOfBirth;
	String locOfBirth;
	String dateOfDeath;
	String locOfDeath;
	String gender;
	String occupation;
	Statement statement;
	
	/***
	 * Constructor for PersonIdentity class. It is used to make connection with the database.
	 */
	PersonIdentity() {
		SqlConnection obj = new SqlConnection();
		statement = obj.makeConnection();
	}

	/***
	 * This method records name of an individual in the database
	 * @param name : Name of a person whose object needs to be returned
	 * @return : Returns the object of class PersonIdentity with all the information of person with name "name"
	 */
	PersonIdentity addPerson(String name) {
		
		if(name == null || name == "") {
			return null;
		}
		
		PersonIdentity person = new PersonIdentity();
		
		try {
			int rowCount=0;
			ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM " + Constant.tableName);
			while(resultSet.next()) {
				rowCount = resultSet.getInt(1);
			}
			
			person.id = rowCount+1;	
			person.name = name;			
			
			resultSet = statement.executeQuery("SELECT * FROM " + Constant.tableName);
			ResultSetMetaData rsmd = resultSet.getMetaData();
			
			String insertQuery = "INSERT INTO " + Constant.tableName  + "(" + rsmd.getColumnName(1) + ", " + rsmd.getColumnName(2) + " ) VALUES (" + person.id +  ", '" + person.name + "')";			
			statement.executeUpdate(insertQuery);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return person;
	}
	
	/***
	 * This method records various attributes for an individual such as date of birth, date of death, gender, occupation
	 * @param person : Object of class PersonIdentity with a person's information whose attributes needs to be stored
	 * @param attributes : Various attributes of an individual
	 * @return : Returns true if attributes have been successfully recorded for a person and false otherwise
	 */
	Boolean recordAttributes( PersonIdentity person, Map<String, String> attributes ) {
		
		if(person == null || attributes.isEmpty()) {
			return false;
		}
		
		ResultSet resultSet;
		Map<String, Integer> columnName = new HashMap<>();
		List<String> notSavedAttributes = new ArrayList<>();
		List<String> DOB = new ArrayList<>();
		List<String> DOD = new ArrayList<>();
		
		DOB.add("dob");
		DOB.add("dateofbirth");
		DOB.add("birthdate");
		DOB.add("date_of_birth");
		
		DOD.add("dod");
		DOD.add("dateofdeath");
		DOD.add("deathdate");
		DOD.add("date_of_death");
		
		//Converts all attribute's key to lower case and stores in a new map
		Map<String, String> attribute = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		attribute.putAll(attributes);

		try {
			resultSet = statement.executeQuery("SELECT * FROM " + Constant.attributesTableForPerson);
			ResultSetMetaData rsmdForKey = resultSet.getMetaData();

			
			for(String key : attribute.keySet()) {
				
				String queryToGetColumnID = "SELECT * " + " FROM " + Constant.attributesTableForPerson 
						+ " WHERE " + rsmdForKey.getColumnName(1) + " = '" + key + "'";
				
				resultSet = statement.executeQuery(queryToGetColumnID);
				
				String column="";
				if(key.equalsIgnoreCase("occupation")) {
					column="occupation";
				}
				int ID=0;
				while(resultSet.next()) {
					column = key;
					ID = resultSet.getInt(2);
				}
				
				if(column.equals("")) {
					if(!key.equalsIgnoreCase("occupation")) {
						notSavedAttributes.add(key);
					}
				}
				
				columnName.put(column, ID);
			}
			
			resultSet = statement.executeQuery("SELECT * FROM " + Constant.tableName);
			
			ResultSetMetaData rsmd = resultSet.getMetaData();
					
			for(String column : columnName.keySet()) {
				if(attribute.containsKey(column)) {
		
					if(column.equalsIgnoreCase("occupation")) {
						String insertIntoOccupationTable = "INSERT INTO " + Constant.occupationTable + " VALUES ( " + person.id + " , '" + attribute.get(column) + "')";
						statement.executeUpdate(insertIntoOccupationTable);
					}
					else {
						String putMapValue = attribute.get(column);
						
						if(putMapValue != null) {
							//If only year is mentioned in date of birth of date of death then it appends zero to month and date
							if(putMapValue.matches("[0-9]+")) {
								if(putMapValue.length()==4) {
									putMapValue = putMapValue.concat("-00-00");
								}
							}
							
							//If only year and month are mentioned in date of birth of date of death then it appends zero to date
							if(putMapValue.contains("-")) {
								if(putMapValue.length()==7) {
									putMapValue = putMapValue.concat("-00");
								}
							}
							
							if(DOB.contains(column.toLowerCase()) || DOD.contains(column.toLowerCase())) {
								if(putMapValue.length() <= 3 || putMapValue.length() == 5) {
									notSavedAttributes.add(column);
									continue;
								}
							}
							
							//Inserts attributes in the family tree table
							String updateTable = "UPDATE " + Constant.tableName + " SET " + rsmd.getColumnName(columnName.get(column)) + " = '" + putMapValue + "' "
									+ " where " + rsmd.getColumnName(1) + " = " + person.id ;
							
							statement.executeUpdate(updateTable);
						}
					}
				}
			}
			
			if(!notSavedAttributes.isEmpty()) {
				System.out.println("Following attributes were not recorded: " + notSavedAttributes);
			}
			
			
			//If all the attributes are not recorded then the method returns false 
			if(notSavedAttributes.size() == attribute.size()) {
				return false;
			}
			
			//Otherwise even if one attribute is successfully recorded then the method returns true
			else {
				return true;	
			}
		} 
		catch (SQLException e) {
			//e.printStackTrace();
			return false;
		}		
	}
	
	/***
	 * This method records any reference to source material for an individual
	 * @param person : Object of class PersonIdentity with a person's information whose reference needs to be stored
	 * @param reference : A reference for an individual
	 * @return : Returns true if reference has been successfully recorded for a person and false otherwise
	 */
	Boolean recordReference( PersonIdentity person, String reference ) {
		if(reference == null || reference == "" || person==null) {
			return false;
		}
		
		String insertIntoTable;
		try {
			int decide=0;
			insertIntoTable = "INSERT INTO " + Constant.refNotesTable + " VALUES (" + person.id + " , '" + reference + "', " + decide + ")";
			statement.executeUpdate(insertIntoTable);
			return true;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/***
	 * This method records any note for an individual
	 * @param person : Object of class PersonIdentity with a person's information whose note needs to be stored
	 * @param note : A note for an individual
	 * @return : Returns true if note has been successfully recorded for a person and false otherwise
	 */
	Boolean recordNote( PersonIdentity person, String note ) {
		
		if(note == null || note == "" || person ==null) {
			return false;
		}
		
		String insertIntoTable;
		try {
			int decide=1;
			insertIntoTable = "INSERT INTO " + Constant.refNotesTable + " VALUES (" + person.id + " , '" + note + "', " + decide +  "  )";
			statement.executeUpdate(insertIntoTable);
			return true;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/***
	 * This method records relation between a parent and a child
	 * @param parent : Object of class PersonIdentity with parent information
	 * @param child : Object of class PersonIdentity with child information
	 * @return : Returns true if relation between parent and child have been successfully recorded and false otherwise
	 */
	Boolean recordChild( PersonIdentity parent, PersonIdentity child ) {
		if(parent == null || child == null) {
			return false;
		}
		
		String insertIntoTable;
		boolean flag1=false;
		boolean flag2=false;
		boolean flag3=false;
		boolean flag4=false;
		try {
			
			ResultSet resultSet = statement.executeQuery("SELECT * FROM " + Constant.tableName);
			
			while(resultSet.next()) {
				if(resultSet.getInt("id") == parent.id) {
					flag1=true;
				}
				if(resultSet.getInt("id") == child.id) {
					flag2=true;
				}
			}
			
			//Check if more than 2 parents are been added for same child
			resultSet = statement.executeQuery("SELECT * FROM " + Constant.childTable);
			ResultSetMetaData rsmdForParent = resultSet.getMetaData();
			int parentCount=1;
			String countParents = "SELECT " + rsmdForParent.getColumnName(1) + " FROM " + Constant.childTable 
									+ " WHERE " + rsmdForParent.getColumnName(2) + " = " + child.id;
			
			resultSet = statement.executeQuery(countParents);
			while(resultSet.next()) {
				parentCount++;
			}
			
			if(parentCount < 3) {
				flag3=true;
			}
			
			
			//Checks if the child is already a parent of "parent"
			int tempParent=0;
			String checkRelation = "SELECT " + rsmdForParent.getColumnName(1) + " FROM " + Constant.childTable 
					+ " WHERE " + rsmdForParent.getColumnName(2) + " = " + parent.id;
			resultSet = statement.executeQuery(checkRelation);
			while(resultSet.next()) {
				tempParent = resultSet.getInt(1);
			}
			
			if(tempParent!=child.id) {
				flag4=true;
			}
			
			if(flag1 && flag2 && flag3 && flag4) {
				insertIntoTable = "INSERT INTO " + Constant.childTable + " VALUES (" + parent.id + " , " + child.id + ")";
				statement.executeUpdate(insertIntoTable);
				
				return true;
			}
			else {
				return false;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/***
	 * This method records partnering relation between partner 1 and partner 2
	 * @param partner1 : Object of class PersonIdentity with partner 1
	 * @param partner2 : Object of class PersonIdentity with partner 2
	 * @return : Returns true if partnering between partner 1 and partner 2 have been successfully recorded and false otherwise
	 */
	Boolean recordPartnering( PersonIdentity partner1, PersonIdentity partner2 ) {
		
		if(partner1 == null || partner2 == null) {
			return false;
		}
		
		try {
			ResultSet resultSetForPartner = statement.executeQuery("SELECT * FROM " + Constant.partnerTable);
			ResultSetMetaData rsmdForPartner = resultSetForPartner.getMetaData();
			
			//To check if partnering has already been performed or not
			String checkPartnering = "SELECT " + rsmdForPartner.getColumnName(1) + " FROM " + Constant.partnerTable 
					+ " WHERE " + rsmdForPartner.getColumnName(2) + " = " + partner1.id + " AND " + rsmdForPartner.getColumnName(3) + " = " + 0;
			resultSetForPartner = statement.executeQuery(checkPartnering);
			
			if(resultSetForPartner.next()) {
				if(partner2.id==resultSetForPartner.getInt(1)) {
					return false;
				}
			}
			
			checkPartnering = "SELECT " + rsmdForPartner.getColumnName(2) + " FROM " + Constant.partnerTable 
					+ " WHERE " + rsmdForPartner.getColumnName(1) + " = " + partner1.id + " AND " + rsmdForPartner.getColumnName(3) + " = " + 0;
			resultSetForPartner = statement.executeQuery(checkPartnering);
			
			if(resultSetForPartner.next()) {
				if(partner1.id == resultSetForPartner.getInt(1)) {
					return false;
				}
			}
			
			checkPartnering = "SELECT " + rsmdForPartner.getColumnName(2) + " FROM " + Constant.partnerTable 
					+ " WHERE " + rsmdForPartner.getColumnName(1) + " in (" + partner1.id + " , " + partner2.id + ") AND " + rsmdForPartner.getColumnName(3) + " = " + 0;
			resultSetForPartner = statement.executeQuery(checkPartnering);
			
			if(resultSetForPartner.next()) {
				return false;
			}
			
			String insertIntoTable = "INSERT INTO " + Constant.partnerTable + " VALUES (" + partner1.id + " , " + partner2.id +  " , " + 0 +  ")";
			statement.executeUpdate(insertIntoTable);
			return true;
			
		} 
		catch (SQLException e) {
			//e.printStackTrace();
			return false;
		}
	}
	
	/***
	 * This method records dissolution relation between partner 1 and partner 2
	 * @param partner1 : Object of class PersonIdentity with partner 1
	 * @param partner2 : Object of class PersonIdentity with partner 2
	 * @return : Returns true if dissolution between partner 1 and partner 2 have been successfully recorded and false otherwise
	 */
	Boolean recordDissolution( PersonIdentity partner1, PersonIdentity partner2 ) {
		
		if(partner1 == null || partner2 == null) {
			return false;
		}
		
		try {
			
			ResultSet resultSetForPartner = statement.executeQuery("SELECT * FROM " + Constant.partnerTable);
			ResultSetMetaData rsmdForPartner = resultSetForPartner.getMetaData();
			
			//To check is dissolution has already been performed between partner 1 and partner 2
			String checkDissolution = "SELECT " + rsmdForPartner.getColumnName(3) + " FROM " + Constant.partnerTable 
					+ " WHERE " + rsmdForPartner.getColumnName(1) + " = " + partner1.id + " AND " + rsmdForPartner.getColumnName(2) + " = " + partner2.id;
			resultSetForPartner = statement.executeQuery(checkDissolution);
			
			if(resultSetForPartner.next()) {
				int dissolution = resultSetForPartner.getInt(1);
				if(dissolution == 1) {
					return false;
				}	
			}
			
			checkDissolution = "SELECT " + rsmdForPartner.getColumnName(3) + " FROM " + Constant.partnerTable 
					+ " WHERE " + rsmdForPartner.getColumnName(1) + " = " + partner2.id + " AND " + rsmdForPartner.getColumnName(2) + " = " + partner1.id;
			resultSetForPartner = statement.executeQuery(checkDissolution);
			
			if(resultSetForPartner.next()) {
				int dissolution = resultSetForPartner.getInt(1);
				if(dissolution == 1) {
					return false;
				}	
			}
			
			
			ResultSet resultSetForDissolution = statement.executeQuery("SELECT * FROM " + Constant.partnerTable);
			ResultSetMetaData rsmdForDissolution = resultSetForDissolution.getMetaData();
			
			String updatePartnerTable = "UPDATE " + Constant.partnerTable + " SET " + rsmdForDissolution.getColumnName(3) + " = " + 1 
					+ " WHERE " + rsmdForDissolution.getColumnName(1) + " IN (" + partner1.id + "," + partner2.id + ") AND " + rsmdForDissolution.getColumnName(2) + " IN (" + partner1.id + "," + partner2.id + ")";

			int rows = statement.executeUpdate(updatePartnerTable);
			if(rows==0) {
				return false;
			}
			else {
				return true;
			}
		} 
		catch (SQLException e) {
			//e.printStackTrace();
			return false;
		}
	}
}
