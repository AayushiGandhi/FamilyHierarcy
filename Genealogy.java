import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class Genealogy {
	
	Statement statement;
	
	/***
	 * Constructor to make connection with SQL database
	 */
	public Genealogy() {		
		SqlConnection obj = new SqlConnection();
		statement = obj.makeConnection();
	}
	
	/***
	 * 
	 * @param name : Name of person whose object of class PersonIdentity needs to be returned
	 * @return : Returns object of class PersonIdentity with name of person and ID stored in it
	 */
	PersonIdentity findPerson( String name ) {
		
		if(name==null || name=="") {
			return null;
		}
		
		ResultSet resultSet=null;
		PersonIdentity person=new PersonIdentity();
		try {
			ResultSet resultSetForFamilyTable = statement.executeQuery("SELECT * FROM " + Constant.tableName);
			ResultSetMetaData rsmdForFamilyTable = resultSetForFamilyTable.getMetaData();
			
			//Gets ID of the person whose name is given
			resultSet = statement.executeQuery("SELECT " + rsmdForFamilyTable.getColumnName(1) +  " FROM " + Constant.tableName + " where " + rsmdForFamilyTable.getColumnName(2) + "= '" + name + "'");
			ResultSetMetaData rsmd = resultSet.getMetaData();
			
			while(resultSet.next()) {
				person.id = resultSet.getInt(rsmd.getColumnName(1));
				person.name = name;
			}
			return person;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/***
	 * @param name : Name of media whose object of class FileIdentifier needs to be returned
	 * @return : Returns object of class FileIdentifier 
	 */
	FileIdentifier findMediaFile( String name ) {
		if(name=="" || name==null) {
			return null;
		}
		
		FileIdentifier FIobj = new FileIdentifier();
		FIobj.fileLocation = name;
		return FIobj;
	}
	
	/***
	 * @param id : Object of class PersonIdentity
	 * @return : Returns the name of an individual by using object of class PersonIdentity.
	 */
	String findName( PersonIdentity id ) {
		if(id == null) {
			return null;
		}
		
		return id.name;
	}
	
	/***
	 * @param fileId : Object of class FileIdentifier
	 * @return : This method returns the file name of the media file associated with the file identifier.
	 */
	String findMediaFile( FileIdentifier fileId ) {
		if(fileId == null) {
			return null;
		}
		
		return fileId.fileLocation;
	}
	
	/***
	 * This method reports how two individuals are related.
	 * @param person1 : object of class PersonIdentity whose relation needs to be found with person 2
	 * @param person2 : object of class PersonIdentity whose relation needs to be found with person 1
	 * @return : Returns object of class BiologicalRelation with degree of cousinship and degree of separation stored in it
	 */
	BiologicalRelation findRelation(PersonIdentity person1, PersonIdentity person2) {
		BiologicalRelation BRobj = new BiologicalRelation();
		BiologicalRelation br = BRobj.findRelation(person1, person2);
		return br;
	}
	
	
	/***
	 * @param person : Object of class PersonIdentity whose descendents needs to be reported
	 * @param generations : “generations” generations of the person
	 * @return : Returns descendents in the family tree who are within “generations” generations of the person
	 */
	Set<PersonIdentity> descendents ( PersonIdentity person, Integer generations ) {
		
		if(generations==null || person==null || generations < 0) {
			return null;
		}
		
		Set<PersonIdentity> descendentsSet = new LinkedHashSet<PersonIdentity>(); 
		PersonIdentity pi = new PersonIdentity();
		List<Integer> descendentsList = new ArrayList<>();
		List<Integer> tempParent = new ArrayList<>();
		int generationCount=1;
		int tempCount1=0;
		int tempCount2=0;
		int currentIndividual = person.id;
		
		//Runs loop for "generations" 
		while(generations>=generationCount) {
			ResultSet resultSet = null;
			
			try {
				resultSet = statement.executeQuery("SELECT * FROM " + Constant.childTable);
				ResultSetMetaData rsmd = resultSet.getMetaData();
				
				//Query to get child from parent
				String query = "select " + rsmd.getColumnName(2) +  " from " + Constant.childTable + " where " + rsmd.getColumnName(1) + "=" + currentIndividual;
				ResultSet rs = statement.executeQuery(query);
				
				//Adding the child in the descendent list
				while(rs.next()) {
					descendentsList.add(rs.getInt(rsmd.getColumnName(2)));
				}
				
				//Temporary parent list to store all children of a parent
				if(tempParent.isEmpty()) {
					tempCount2 = descendentsList.size();
					tempParent.addAll(descendentsList.subList(tempCount1, tempCount2));
					tempCount1 = descendentsList.size();
					
					//Increasing generation count when one breadth level is traversed
					generationCount++;
				}
				
				//Taking first value from the temporary list and making it as a current individual
				if(!tempParent.isEmpty()) {
					currentIndividual = tempParent.get(0);
					tempParent.remove(0);
				}
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
				
		ResultSet resultSet;
		try {
			resultSet = statement.executeQuery("SELECT * FROM " + Constant.tableName);
			ResultSetMetaData rsmdForFamilyTable = resultSet.getMetaData();
			
			
			//To store objects of class PersonIdentity for all the individuals from descendents list into a descendents set list
			for(int i : descendentsList) {
				
				//Query to get name of an individual from ID
				String getNameFromIDQuery = "SELECT " + rsmdForFamilyTable.getColumnName(2) +  " from " + Constant.tableName + " where " + rsmdForFamilyTable.getColumnName(1) + "=" + i;
				ResultSet rsForFamilyTable = statement.executeQuery(getNameFromIDQuery);
				pi = new PersonIdentity();
				pi.id = i;
				
				while(rsForFamilyTable.next()) {
					pi.name = rsForFamilyTable.getString(rsmdForFamilyTable.getColumnName(2));
				}
				descendentsSet.add(pi);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
				
		return descendentsSet;
	}
	
	/***
	 * @param person : Object of class PersonIdentity whose ancestores needs to be reported
	 * @param generations : “generations” generations of the person
	 * @return : Returns ancestores in the family tree who are within “generations” generations of the person
	 */
	Set<PersonIdentity> ancestores( PersonIdentity person, Integer generations ) {
		
		if(generations==null || person==null || generations < 0) {
			return null;
		}
		
		Set<PersonIdentity> ancestoresSet = new LinkedHashSet<PersonIdentity>(); 
		PersonIdentity pi = new PersonIdentity();
		List<Integer> ancestoresList = new ArrayList<>();
		List<Integer> tempParent = new ArrayList<>();
		int generationCount=1;
		int tempCount1=0;
		int tempCount2=0;
		int currentIndividual = person.id;
		
		//Runs loop for "generations" 
		while(generations>=generationCount) {
			ResultSet resultSet = null;
			
			try {
				resultSet = statement.executeQuery("SELECT * FROM " + Constant.childTable);
				ResultSetMetaData rsmd = resultSet.getMetaData();
				
				//Query to get parent from child
				String query = "select " + rsmd.getColumnName(1) +  " from " + Constant.childTable + " where " + rsmd.getColumnName(2) + "=" + currentIndividual;
				ResultSet rs = statement.executeQuery(query);
				
				//Adding the child in the ancestores list
				while(rs.next()) {
					ancestoresList.add(rs.getInt(rsmd.getColumnName(1)));
				}
				
				//Temperory parent list to store parent of the current individual
				if(tempParent.isEmpty()) {
					tempCount2 = ancestoresList.size();
					tempParent.addAll(ancestoresList.subList(tempCount1, tempCount2));
					tempCount1 = ancestoresList.size();
					
					//Increasing generation count when one breadth level is traversed
					generationCount++;
				}
				
				//Taking first value from the temperory list and making it as a current individual
				if(!tempParent.isEmpty()) {
					currentIndividual = tempParent.get(0);
					tempParent.remove(0);
				}
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
				
		ResultSet resultSet;
		try {
			resultSet = statement.executeQuery("SELECT * FROM " + Constant.tableName);
			ResultSetMetaData rsmdForFamilyTable = resultSet.getMetaData();
			
			//To store objects of class PersonIdentity for all the individuals from ancestores list into a ancestores set list
			for(int i : ancestoresList) {
				
				//Query to get name of an individual from ID
				String getNameFromIDQuery = "SELECT " + rsmdForFamilyTable.getColumnName(2) +  " from " + Constant.tableName + " where " + rsmdForFamilyTable.getColumnName(1) + "=" + i;
				ResultSet rsForFamilyTable = statement.executeQuery(getNameFromIDQuery);
				pi = new PersonIdentity();
				pi.id = i;
				
				while(rsForFamilyTable.next()) {
					pi.name = rsForFamilyTable.getString(rsmdForFamilyTable.getColumnName(2));
				}
				ancestoresSet.add(pi);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}		
		
		return ancestoresSet;
	}
	
	/***
	 * @param person : Object of class PersonIdentity whose notes and references needs to be returned
	 * @return : Returns all the notes and references on the individual. It returns in the same order in which they were added to the family tree
	 */
	List<String> notesAndReferences( PersonIdentity person ) {
		
		if(person==null) {
			return null;
		}
		
		ResultSet resultSetForReferenceNotes = null;
		
		List<String> notesAndReferences = new ArrayList<String>();
		try {
			//refNotesTable : Database table to store references and notes of an individual
			resultSetForReferenceNotes = statement.executeQuery("SELECT * FROM " + Constant.refNotesTable);
			ResultSetMetaData rsmdForReferenceNotes = resultSetForReferenceNotes.getMetaData();
			
			//Query to get all the references and notes of an individual in the same order as they were added to the family tree
			String query = "select " + rsmdForReferenceNotes.getColumnName(2) + " from " + Constant.refNotesTable + " where " + rsmdForReferenceNotes.getColumnName(1) + " = " + person.id;
			ResultSet rs = statement.executeQuery(query);
			
			while(rs.next()) {
				notesAndReferences.add(rs.getString(rsmdForReferenceNotes.getColumnName(2)));
			}
						
			return notesAndReferences;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/***
	 * @param tag : Media files for a particular tag
	 * @param startDate : starting date for the media
	 * @param endDate : ending date till the media needs to be found
	 * @return : Returns the set of media files linked to the given tag whose dates fall within the date range
	 */
	Set<FileIdentifier> findMediaByTag( String tag , String startDate, String endDate) {
		
		if(tag==null || tag=="") {
			return null;
		}
		
		Set<FileIdentifier> listOfMedia = new LinkedHashSet<FileIdentifier>();
		FileIdentifier FIobj = new FileIdentifier();
		
		
		//If start date is null then by default 0000-00-00 will be added. And if end date is null then by default 9999-12-31 will be added
		if(startDate.equals(null) || startDate.equals("") || endDate.equals(null) || endDate.equals("")) {
			if(startDate.equals(null) || startDate.equals("")) {
				startDate="0000-00-00";
			}
			if(endDate.equals(null) || endDate.equals("")) {
				endDate="9999-12-31";
			}
		}
		
		try {
			ResultSet resultSetForTag = statement.executeQuery("SELECT * FROM " + Constant.tagTable);
			ResultSetMetaData rsmdForTag = resultSetForTag.getMetaData();
			
			ResultSet resultSetForMedia = statement.executeQuery("SELECT * FROM " + Constant.mediaTable);
			ResultSetMetaData rsmdForMedia = resultSetForMedia.getMetaData();
			
			//Query to get media files for a particular tag under a given date range
			String query = "select m." + rsmdForTag.getColumnName(1) + " from " + Constant.tagTable + " t JOIN " + Constant.mediaTable + " m "
					+ "on t." + rsmdForTag.getColumnName(1) + " = m." + rsmdForMedia.getColumnName(1)
					+ " where " + rsmdForTag.getColumnName(2) + " = '" + tag  
					+ "' AND (( " + rsmdForMedia.getColumnName(2) + " BETWEEN '" + startDate + "' AND '" + endDate + "') OR (" + rsmdForMedia.getColumnName(2) + " IS null))";
			
			resultSetForTag = statement.executeQuery(query);
			while(resultSetForTag.next()) {
				FIobj = new FileIdentifier();
				FIobj.fileLocation = resultSetForTag.getString(rsmdForTag.getColumnName(1));
				System.out.println("findMediaByTag " + FIobj.fileLocation);
				listOfMedia.add(FIobj);
			}
			
			return listOfMedia;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	/***
	 * @param location : media file for a particular location
	 * @param startDate : starting date for the media
	 * @param endDate : ending date till the media needs to be found
	 * @return : Returns the set of media files linked to the given location whose dates fall within the date range
	 */
	Set<FileIdentifier> findMediaByLocation( String location, String startDate, String endDate) {
		
		if(location==null || location=="") {
			return null;
		}
		
		ResultSet resultSet=null;
		Set<FileIdentifier> setOfMedia = new LinkedHashSet<FileIdentifier>();
		FileIdentifier FIobj = new FileIdentifier();
				
		//If start date is null then by default 0000-00-00 will be added. And if end date is null then by default 9999-12-31 will be added
		if(startDate.equals(null)|| startDate.equals("") || endDate.equals(null) ||  endDate.equals("")) {
			if(startDate.equals(null) || startDate.equals("")) {
				startDate="0000-00-00";
			}
			if(endDate.equals(null) ||  endDate.equals("")) {
				endDate="9999-12-31";
			}
		}
		
		try {
			resultSet = statement.executeQuery("SELECT * FROM " + Constant.mediaTable);
			ResultSetMetaData rsmd = resultSet.getMetaData();
			
			//Query to get media files for a particular location under a given date range
			String query = "SELECT * FROM " + Constant.mediaTable + " where " + rsmd.getColumnName(3) + " LIKE '%" + location + "%'"
					+ " AND (( " + rsmd.getColumnName(2) + " BETWEEN '" + startDate + "' AND '" + endDate + "') OR " + rsmd.getColumnName(2) + " IS null)";
			
			resultSet = statement.executeQuery(query);
			while(resultSet.next()) {
				FIobj = new FileIdentifier();
				FIobj.fileLocation = resultSet.getString(rsmd.getColumnName(1));
				System.out.println("findMediaByLocation " + FIobj.fileLocation);
				setOfMedia.add(FIobj);
			}
			
			return setOfMedia;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/***
	 * @param people : List of people whose media needs to be found (list of object of class PersonIdentity)
	 * @param startDate : starting date for the media
	 * @param endDate : ending date till the media needs to be found
	 * @return : Returns the set of media files that include any of individuals given in the list of people whose dates fall within the date range
	 */
	List<FileIdentifier> findIndividualsMedia( Set<PersonIdentity> people, String startDate, String endDate) {
		
		if(people == null) {
			return null;
		}
		
		ResultSet resultSetForMedia;
		ResultSet resultSetForPeople;
		FileIdentifier FIobj = new FileIdentifier();
		List<FileIdentifier> listOfMedia = new ArrayList<>();
		List<String> fileLocationList = new ArrayList<>();
		List<String> dateList = new ArrayList<>();
		
		//If start date is null then by default 0000-00-00 will be added. And if end date is null then by default 9999-12-31 will be added
		if(startDate.equals(null)|| startDate.equals("") || endDate.equals(null) || endDate.equals("")) {
			if(startDate.equals(null) || startDate.equals("")) {
				startDate="0000-00-00";
			}
			if(endDate.equals(null) ||  endDate.equals("")) {
				endDate="9999-12-31";
			}
		}
		
		try {
			resultSetForMedia = statement.executeQuery("SELECT * FROM " + Constant.mediaTable);
			ResultSetMetaData rsmdForMedia = resultSetForMedia.getMetaData();
			
			resultSetForPeople = statement.executeQuery("SELECT * FROM " + Constant.peopleInMediaTable);
			ResultSetMetaData rsmdForPeople = resultSetForPeople.getMetaData();
			
			//Query to get media files for particular individuals under a date range
			for(PersonIdentity person : people) {
				String query = "select DISTINCT m." + rsmdForMedia.getColumnName(1) + " , m." + rsmdForMedia.getColumnName(2)
				+ " from " + Constant.mediaTable + "  m JOIN " + Constant.peopleInMediaTable + " p "
				+ " on m." + rsmdForMedia.getColumnName(1) + " = p." + rsmdForPeople.getColumnName(1)
				+ " where " + rsmdForPeople.getColumnName(2) + " = " + person.id
				+ " AND (( " + rsmdForMedia.getColumnName(2) + " BETWEEN '" + startDate + "' AND '" + endDate + "') OR " + rsmdForMedia.getColumnName(2) + " IS null)";
				
				resultSetForPeople = statement.executeQuery(query);
				while(resultSetForPeople.next()) {
					fileLocationList.add(resultSetForPeople.getString(1));
					dateList.add(resultSetForPeople.getString(2));
				}
			}
			
			//Inserting the above media files with their respective dates in a table named "mediaDateTable"
			for(int i=0;i<fileLocationList.size();i++) {
				String insertIntoMediaDateTable ="";
				if(dateList.get(i) != null) {
					insertIntoMediaDateTable = "INSERT INTO " + Constant.mediaDateTable + " VALUES ( '" + fileLocationList.get(i) + "' , '" + dateList.get(i) + "'  )";	
				}
				else {
					insertIntoMediaDateTable = "INSERT INTO " + Constant.mediaDateTable + " (fileLocation) VALUES ( '" + fileLocationList.get(i) + "'  )";
				}
				
				statement.executeUpdate(insertIntoMediaDateTable);	
			}
			
			ResultSet resultSetForMediaDate = statement.executeQuery("SELECT * FROM " + Constant.mediaDateTable);
			ResultSetMetaData rsmdForMediaDate = resultSetForMediaDate.getMetaData();
			
			//Sorting the table "mediaDateTable" and getting the media file in ascending chronological order when date is not null
			String getMediaByDate = "SELECT DISTINCT " + rsmdForMediaDate.getColumnName(1) + " FROM " + Constant.mediaDateTable + " WHERE " + rsmdForMediaDate.getColumnName(2) + " IS NOT NULL ORDER BY " + rsmdForMediaDate.getColumnName(2);
			resultSetForMediaDate = statement.executeQuery(getMediaByDate);
						
			while(resultSetForMediaDate.next()) {
				FIobj = new FileIdentifier();
				FIobj.fileLocation = resultSetForMediaDate.getString(1);
				System.out.println(FIobj.fileLocation);
				listOfMedia.add(FIobj);
			}
			
			//Sorting the table "mediaDateTable" and getting the media file in ascending chronological order when date is null
			getMediaByDate = "SELECT DISTINCT " + rsmdForMediaDate.getColumnName(1) + " FROM " + Constant.mediaDateTable + " WHERE " + rsmdForMediaDate.getColumnName(2) + " IS NULL ORDER BY " + rsmdForMediaDate.getColumnName(1);
			resultSetForMediaDate = statement.executeQuery(getMediaByDate);
						
			while(resultSetForMediaDate.next()) {
				FIobj = new FileIdentifier();
				FIobj.fileLocation = resultSetForMediaDate.getString(1);
				System.out.println(FIobj.fileLocation);
				listOfMedia.add(FIobj);
			}			
			return listOfMedia;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	/***
	 * @param person : The person with object of class PersonIdentity whose immediate children's media needs to be found
	 * @return : Returns the set of media files that include the specified person’s immediate children
	 */
	List<FileIdentifier> findBiologicalFamilyMedia(PersonIdentity person) {
		
		if(person == null) {
			return null;
		}
		
		List<FileIdentifier> listOfMedia = new ArrayList<>();
		String query;
		Set<Integer> children = new HashSet<>();
		Set<PersonIdentity> people = new HashSet<>();
		
		try {
			ResultSet resultSetForChild = statement.executeQuery("SELECT * FROM " + Constant.childTable);
			ResultSetMetaData rsmdForChild = resultSetForChild.getMetaData();
			
			//Query to fetch immediate children of person
			query = "select " + rsmdForChild.getColumnName(2) +  " from " + Constant.childTable + " where " + rsmdForChild.getColumnName(1) + "=" + person.id;
			ResultSet rs = statement.executeQuery(query);
			
			//Storing immediate children in children list
			while(rs.next()) {
				children.add(rs.getInt(1));
			}
			
			ResultSet resultSet = statement.executeQuery("SELECT * FROM " + Constant.tableName);
			ResultSetMetaData rsmdForFamilyTable = resultSet.getMetaData();
			PersonIdentity pi = new PersonIdentity();
			
			//Storing objects of class PersonIdentity in list
			for(int i : children) {
				String getNameFromIDQuery = "SELECT " + rsmdForFamilyTable.getColumnName(2) +  " from " + Constant.tableName + " where " + rsmdForFamilyTable.getColumnName(1) + "=" + i;
				ResultSet rsForFamilyTable = statement.executeQuery(getNameFromIDQuery);
				pi = new PersonIdentity();
				pi.id = i;
				
				while(rsForFamilyTable.next()) {
					pi.name = rsForFamilyTable.getString(rsmdForFamilyTable.getColumnName(2));
				}
				people.add(pi);
			}
			
			statement.executeUpdate("TRUNCATE table " + Constant.mediaDateTable);
			
			//All the children are passed as a list to call findIndividualsMedia()
			listOfMedia.addAll(findIndividualsMedia(people, "0000-00-00", "9999-12-31"));
			
			System.out.println();
			return listOfMedia;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}	
	}
}
