import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BiologicalRelation {
	int cousinship;
	int separation;
	Map<Integer, List<Integer>> relation = new HashMap<Integer, List<Integer>>();
	Statement statement;
	
	/***
	 * A constructor to make connection with SQL database
	 */
	BiologicalRelation() {
		SqlConnection obj = new SqlConnection();
		statement = obj.makeConnection();
	}
	
	/***
	 * A constructor to initiate value for cousinship and separation
	 * @param cousinship : Value of cousinship
	 * @param seperation : Value of separation
	 */
	BiologicalRelation(int cousinship, int seperation) {
		this.cousinship = cousinship;
		this.separation = seperation;
	}
	
	/***
	 * This method stores parent-child relation between individuals in a Map. The map contains Parent's ID as key and List of Childern's ID as value
	 * @return : Returns a Map with relations between individuals stored in it
	 */
	Map<Integer, List<Integer>> mapRelation() {
		try {
			ResultSet rs = statement.executeQuery("select * from " + Constant.childTable);
			
			while(rs.next()) {
				int i=1;
				
				//Gets the ID of a Parent
				int nextIndividual = rs.getInt(i);
					
				//If the parent is already stored in the Map, then it stores the current children by appending with all the previous children in the value of Map
				if(relation.containsKey(nextIndividual)) {
					List<Integer> currentChildren = relation.get(nextIndividual);
					currentChildren.add(rs.getInt(i+1));
					relation.put(nextIndividual, currentChildren);
				}
				//If the parent is encountered for the first time in the Map, then it stores the current children in the value of Map
				else {
					List<Integer> currentChildren = new ArrayList<Integer>();
					currentChildren.add(rs.getInt(i+1));
					relation.put(nextIndividual, currentChildren);
				}
			}
			return relation;
		} 
		catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/***
	 * This method reports how two individuals are related.
	 * @param person1 : object of class PersonIdentity whose relation needs to be found with person 2
	 * @param person2 : object of class PersonIdentity whose relation needs to be found with person 1
	 * @return : Returns object of class BiologicalRelation with degree of cousinship and degree of separation stored in it
	 */
	BiologicalRelation findRelation(PersonIdentity person1, PersonIdentity person2) {
		
		if(person1==null || person2==null) {
			return null;
		}
		
		mapRelation();
				
		//List to add ancestor for person 1
		List<Integer> listForPerson1 = new ArrayList<>();
		
		//List to add ancestor for person 2
		List<Integer> listForPerson2 = new ArrayList<>();
		
		List<List<Integer>> check1 = new ArrayList<>();
		List<List<Integer>> check2 = new ArrayList<>();
		
		int index1=0;
		int index2=0;
		int countsize = 1;
		int p1=person1.id;
		int p2=person2.id;
		
		int length1=0;
		int length2=0;
		boolean flag=false;
		boolean flag1=false;
		
		//Adding person 1 and person 2 in the list
		listForPerson1.add(p1);
		listForPerson2.add(p2);
		
		do {
			flag=false;
			length1 = listForPerson1.size();
			length2 = listForPerson2.size();
			
			//Loop to get all the relations one by one
			for(int person : relation.keySet()) {
				
				//Checks if the current person contains the other person 1 as a child, if yes the adds it in the list of person 1
				if(relation.get(person).contains(p1)) {
					listForPerson1.add(person);
				}
				
				//Checks if the current person contains the other person 2 as a child, if yes the adds it in the list of person 2
				if (relation.get(person).contains(p2)) {
					listForPerson2.add(person);
				}	
			}
			
			List<Integer> temp1 = new ArrayList<>();
			List<Integer> temp2 = new ArrayList<>();
			
			//Checks if more than 2 persons are added since the last time
			if(length1 + 2 <= listForPerson1.size() ) {
				temp1.add(listForPerson1.get(listForPerson1.size()-2));
				temp1.add(listForPerson1.get(listForPerson1.size()-1));
				check1.add(0,temp1);
				listForPerson1.remove(listForPerson1.size()-1);
			}
			if(length2 + 2 <= listForPerson2.size()) {
				temp2.add(listForPerson2.get(listForPerson2.size()-2));
				temp2.add(listForPerson2.get(listForPerson2.size()-1));
				check2.add(0,temp2);
				listForPerson2.remove(listForPerson2.size()-1);
			}
			
			//It turns the flag to true if one of the elements from both the lists of person 1 and person 2 is same. i.e. a common ancestor
			for(Integer l1 : listForPerson1) {
				if(listForPerson2.contains(l1)) {
					index1=listForPerson1.indexOf(l1);
					index2=listForPerson2.indexOf(l1);
					flag=true;
					flag1=true;
				}
			}
			
			//When a common ancestor is found it calculates degree of cousinship and level of separation
			if(flag1) {
				if(index1 > index2) {
					cousinship = index2 - 1;
					separation = index1 - index2;
				}
				else {
					cousinship = index1 - 1;
					separation = index2 - index1;
				}
			}
			
			//Checks if the size of both the lists is still same as it was in the previous run. i.e. no new persons are added
			if(listForPerson1.size()==length1 && listForPerson2.size()==length2) {
				if(!(check1.isEmpty())) {
					countsize = 1;
					int search =  check1.get(0).get(1);
					int delete = check1.get(0).get(0);
					int index = listForPerson1.indexOf(delete);
					listForPerson1.subList(index, listForPerson1.size()).clear();
					listForPerson1.add(search);
					check1.remove(0);
					flag = false;
				}
				if(!(check2.isEmpty())) {
					countsize = 1;
					int search =  check2.get(0).get(1);
					int delete = check2.get(0).get(0);
					int index = listForPerson2.indexOf(delete);
					listForPerson2.subList(index, listForPerson2.size()).clear();
					listForPerson2.add(search);
					check2.remove(0);
				}		
			}
			
			//Makes the last person from the list as the current individual for the next run.
			p1=listForPerson1.get(listForPerson1.size()-1);
			p2=listForPerson2.get(listForPerson2.size()-1);
			
			if((listForPerson1.size()==length1 && listForPerson2.size()==length2)) {
				countsize++;
			}
			
		
		} while(countsize < 3 && flag==false);
		
		BiologicalRelation br = new BiologicalRelation();
		br.cousinship = cousinship;
		br.separation = separation;

		return br;
	}
	
	/***
	 * Getter to get value of Counsinship
	 * @return : Returns the counsinship value
	 */
	int getCousinship() {
		return this.cousinship;
	}
	
	/***
	 * Getter to get value of separation
	 * @return : Returns the value of removal
	 */
	int getRemoval() {
		return this.separation;
	}
}
