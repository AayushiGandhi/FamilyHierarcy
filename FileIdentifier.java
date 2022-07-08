import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FileIdentifier {
	String fileLocation;
	String dateOfPictureTaken;
	String locOfPictureTaken;

	Statement statement;
	
	/***
	 * Constructor of class FileIdentifier to make connection with SQL database
	 */
	FileIdentifier() {
		SqlConnection obj = new SqlConnection();
		statement = obj.makeConnection();		
	}
	
	/***
	 * This method records the media location in database
	 * @param fileLocation : File location of a media whose object needs to be returned
	 * @return : Returns the object of class FileIdentifier with all the information of a media with location "fileLocation"
	 */
	FileIdentifier addMediaFile(String fileLocation) {
		
		if(fileLocation == null || fileLocation == "") {
			return null;
		}
		
		FileIdentifier fileIdentifier = new FileIdentifier();
		fileIdentifier.fileLocation = fileLocation;
		
		try {			
			ResultSet resultSet = statement.executeQuery("SELECT * FROM " + Constant.mediaTable);
			
			ResultSetMetaData rsmd = resultSet.getMetaData();
			String insertQuery = "INSERT INTO " + Constant.mediaTable  + " ( " + rsmd.getColumnName(1) + " ) VALUES ('" + fileIdentifier.fileLocation +  "')";
			statement.executeUpdate(insertQuery);
			return fileIdentifier;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/***
	 * This method records various attributes for a media such as date of picture taken, location of picture taken
	 * @param fileIdentifier : Object of class FileIdentifier with a media's information whose attributes needs to be stored
	 * @param attributes : Various attributes of an individual
	 * @return : Returns true if attributes have been successfully recorded for a media and false otherwise
	 */
	boolean recordMediaAttributes( FileIdentifier fileIdentifier, Map<String, String> attributes ) {
		
		if(fileIdentifier == null || attributes.isEmpty()) {
			return false;
		}
		
		ResultSet resultSet;
		Map<String, Integer> columnName = new HashMap<>();
		List<String> notSavedAttributes = new ArrayList<>();
		List<String> date = new ArrayList<>();
		
		date.add("dateofpicturetaken");
		date.add("dateofpicture");
		date.add("dop");
		date.add("picturedate");
		date.add("date");
		date.add("year");
		
		//Converts all attribute's key to lower case and stores in a new map
		Map<String, String> attribute = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
		attribute.putAll(attributes);
		
		
		try {
			resultSet = statement.executeQuery("SELECT * FROM " + Constant.attributesTableForMedia);
			ResultSetMetaData rsmdForKey = resultSet.getMetaData();
			
			for(String key : attribute.keySet()) {
				String queryToGetColumnID = "SELECT * " + " FROM " + Constant.attributesTableForMedia 
						+ " WHERE " + rsmdForKey.getColumnName(1) + " = '" + key + "'";
				
				resultSet = statement.executeQuery(queryToGetColumnID);
				
				String column="";
				int ID=0;
				while(resultSet.next()) {
					column = key;
					ID = resultSet.getInt(2);
				}
				
				if(column.equals("")) {
					notSavedAttributes.add(key);	
				}
				columnName.put(column, ID);
			}
			
			resultSet = statement.executeQuery("SELECT * FROM " + Constant.mediaTable);
			ResultSetMetaData rsmd = resultSet.getMetaData();
			
			for(String column : columnName.keySet()) {
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
					
					if(date.contains(column.toLowerCase())) {
						if(putMapValue.length() <= 3 || putMapValue.length() == 5) {
							notSavedAttributes.add(column);
							continue;
						}
						
						
						String updateTable = "UPDATE " + Constant.mediaTable + " SET " + rsmd.getColumnName(2) + " = '" + putMapValue + "'"
								+ " where " + rsmd.getColumnName(1) + " = '" + fileIdentifier.fileLocation + "'";
						
						statement.executeUpdate(updateTable);
					}
					else {
						String concat="";
						resultSet = statement.executeQuery("SELECT " + rsmd.getColumnName(3) + " FROM " + Constant.mediaTable 
								+ " WHERE " + rsmd.getColumnName(1) + " = '" + fileIdentifier.fileLocation + "'");
						while(resultSet.next()) {
							concat = resultSet.getString(1);
						}
						
						if(concat != null) {
							putMapValue = concat +  "," + putMapValue;
							String updateTable = "UPDATE " + Constant.mediaTable + " SET " + rsmd.getColumnName(3) + " = '" + putMapValue + "'"
									+ " where " + rsmd.getColumnName(1) + " = '" + fileIdentifier.fileLocation + "'";
							
							statement.executeUpdate(updateTable);
						}
						else {
							String updateTable = "UPDATE " + Constant.mediaTable + " SET " + rsmd.getColumnName(3) + " = '" + putMapValue + "'"
									+ " where " + rsmd.getColumnName(1) + " = '" + fileIdentifier.fileLocation + "'";
							
							statement.executeUpdate(updateTable);
						}
					}
				}
			}

			return true;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}	
	}
	
	/***
	 * This method records set of people that appear in the given media file
	 * @param fileIdentifier : Media file object
	 * @param people : List of people in the media file
	 * @return : Returns true if people in the media have been successfully recorded and false otherwise
	 */
	boolean peopleInMedia( FileIdentifier fileIdentifier, List<PersonIdentity> people ) {
		
		if(fileIdentifier == null || people.isEmpty()) {
			return false;
		}
		
		String insertIntoTable;
		
		try {
			
			for(PersonIdentity person : people) {
				insertIntoTable = "INSERT INTO " + Constant.peopleInMediaTable + " VALUES ('" + fileIdentifier.fileLocation + "' , '" + person.id + "')";
				statement.executeUpdate(insertIntoTable);
			}
			return true;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/***
	 * This method records any tags for a media
	 * @param fileIdentifier : Object of class FileIdentifier with a media's information whose tag needs to be stored
	 * @param tag : A tag for a media
	 * @return : Returns true if tag has been successfully recorded for a media and false otherwise
	 */
	boolean tagMedia( FileIdentifier fileIdentifier, String tag ) {
		if(tag == null || tag=="") {
			return false;
		}
		
		String insertIntoTable;
		try {
			insertIntoTable = "INSERT INTO " + Constant.tagTable + " VALUES ('" + fileIdentifier.fileLocation + "' , '" + tag + "')";
			statement.executeUpdate(insertIntoTable);
			return true;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}	
}
