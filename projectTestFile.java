import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

class projectTestFile {

	Map<String, String> attributes = new HashMap<String, String>();
	Map<String, String> attributesFI = new HashMap<String, String>();
	List<PersonIdentity> personList = new ArrayList<>();
	
	
	@Test
	void testAddPerson() {
	
		PersonIdentity pI = new PersonIdentity();
		
		PersonIdentity p1 = pI.addPerson("Dhruv");
		
		
		
		attributes.put("dateOfBirth", "199");
		attributes.put("birthLocation", "Surat");
		attributes.put("dateOfDeath", "2080-03-04");
		attributes.put("LocationOfDeath", "Surat");
		attributes.put("gender", "m");
		attributes.put("occupation", "engineer");
		
		assertTrue(pI.recordAttributes(p1, attributes));
		
				
		PersonIdentity p2 = pI.addPerson("Krushn");
		
		attributes = new HashMap<>();
		
		attributes.put("dob", "1994-07-08");
		attributes.put("dobL", "Bharuch");
		attributes.put("dod", "2080-03-04");
		attributes.put("dodL", "Bharuch");
		attributes.put("gender", "m");
		attributes.put("occupation", "manager");
	
		pI.recordAttributes(p2, attributes);
		
		pI.recordReference(p1, "reference1");
		pI.recordNote(p1, "note1");
		pI.recordReference(p1, "reference2");
		pI.recordReference(p2, "ref1");
		
		pI.recordNote(p2, "note2");
		

		pI.recordPartnering(p1, p2);
		
		pI.recordDissolution(p1, p2);
		
		FileIdentifier fI = new FileIdentifier();
		
		FileIdentifier fI1 = fI.addMediaFile("abc1.jpg");
		FileIdentifier fI2 = fI.addMediaFile("ebc2.jpg");
		FileIdentifier fI3 = fI.addMediaFile("fbc3.jpg");
		FileIdentifier fI4 = fI.addMediaFile("nbc4.jpg");
		FileIdentifier fI5 = fI.addMediaFile("gbc5.jpg");
		FileIdentifier fI6 = fI.addMediaFile("obc6.jpg");
		FileIdentifier fI7 = fI.addMediaFile("obc7.jpg");
		FileIdentifier fI8 = fI.addMediaFile("qbc8.jpg");
		FileIdentifier fI9 = fI.addMediaFile("sbc9.jpg");
		
		
		attributesFI.put("dateOfPicture", "2021-11-30");
		attributesFI.put("Location", "l1");
		
		fI.recordMediaAttributes(fI1, attributesFI);
		
		fI.tagMedia(fI1, "t1");
		
		attributesFI.put("dateOfPicture", "2021-12-1");
		attributesFI.put("Location", "l2");
		
		fI.recordMediaAttributes(fI2, attributesFI);
		
		fI.tagMedia(fI2, "t2");
		
		attributesFI.put("dateOfPicture", null);
		attributesFI.put("Location", "l3");
		
		fI.recordMediaAttributes(fI3, attributesFI);
		
		fI.tagMedia(fI3, "t3");
		
		attributesFI.put("dateOfPicture", "2020-01-30");
		attributesFI.put("Location", "l2");
		attributesFI.put("city", "Halifax");
		attributesFI.put("country", "Canada");
		
		
		fI.recordMediaAttributes(fI4, attributesFI);
		
		fI.tagMedia(fI4, "t4");
		
		attributesFI = new HashMap<>();
		attributesFI.put("dateOfPicture", "2019-09-24");
		attributesFI.put("Location", "l5");
		
		fI.recordMediaAttributes(fI5, attributesFI);
		
		fI.tagMedia(fI5, "t5");

		attributesFI = new HashMap<>();
		attributesFI.put("dateOfPicture", "2019-03-09");
		attributesFI.put("Location", "l2");
		
		fI.recordMediaAttributes(fI6, attributesFI);
		
		fI.tagMedia(fI6, "t6");
		attributesFI = new HashMap<>();
		attributesFI.put("dateOfPicture", "2018-11-10");
		attributesFI.put("Location", "l7");
		
		fI.recordMediaAttributes(fI7, attributesFI);
		
		fI.tagMedia(fI7, "t3");
		attributesFI = new HashMap<>();
		attributesFI.put("dateOfPicture", "2019-09-24");
		attributesFI.put("Location", "l2");
		
		fI.recordMediaAttributes(fI8, attributesFI);
		
		fI.tagMedia(fI8, "t8");
		attributesFI = new HashMap<>();
		attributesFI.put("dateOfPicture", "2017-04-07");
		attributesFI.put("Location", "l1");
		
		fI.recordMediaAttributes(fI9, attributesFI);
		
		fI.tagMedia(fI9, "t3");
		
		
		personList.add(p1);
		
		personList.add(p2);
		
		fI.peopleInMedia(fI1, personList);
		
		PersonIdentity p3 = pI.addPerson("a");
		
		attributes = new HashMap<>();
		
		attributes.put("dob", "2000");
		attributes.put("dod", "1997-01-01");
		attributes.put("occupation", "o1");
		
		assertTrue(pI.recordAttributes(p3, attributes));
		
		PersonIdentity p4 = pI.addPerson("b");
		
		attributes = new HashMap<>();
		attributes.put("dob", "1997");
		
		
		assertTrue(pI.recordAttributes(p4, attributes));
		
		PersonIdentity p5 = pI.addPerson("c");
		PersonIdentity p6 = pI.addPerson("d");
		PersonIdentity p7 = pI.addPerson("e");
		PersonIdentity p8 = pI.addPerson("f");
		PersonIdentity p9 = pI.addPerson("g");
		PersonIdentity p10 = pI.addPerson("h");
		PersonIdentity p11 = pI.addPerson("i");
		PersonIdentity p12 = pI.addPerson("j");
		PersonIdentity p13 = pI.addPerson("k");
		PersonIdentity p14 = pI.addPerson("l");
		PersonIdentity p15 = pI.addPerson("m");
		PersonIdentity p16 = pI.addPerson("n");
		PersonIdentity p17 = pI.addPerson("o");
		
		pI.recordChild(p4, p1);
		
//		pI.recordChild(p1, p4);
//		pI.recordChild(p5, p1);
//		pI.recordChild(p6, p1);
		
		
		
		pI.recordChild(p4, p2);
		pI.recordChild(p4, p3);
		pI.recordChild(p7, p5);
		pI.recordChild(p7, p6);
		pI.recordChild(p9, p7);
		pI.recordChild(p9, p8);
		pI.recordChild(p10, p4);
		pI.recordChild(p10, p9);
		pI.recordChild(p12, p11);
		pI.recordChild(p13, p9);
		pI.recordChild(p13, p12);
		pI.recordChild(p16, p15);
		pI.recordChild(p17, p15);
		pI.recordChild(p15, p10);
		pI.recordChild(p14, p10);
	
		
		//Map<Integer, List<Integer>> fTree = fM.createMap();
		
		BiologicalRelation bR = new BiologicalRelation();
		
		
		
		Genealogy g = new Genealogy();
		
		
		System.out.println(p1);
		System.out.println(g.findPerson("Dhruv"));
		
		System.out.println(fI1);
		System.out.println(g.findMediaFile("abc.jpg"));
		
		System.out.println(g.findName(p1));
		
		System.out.println(g.findMediaFile(fI1));
		
		
		bR = g.findRelation( p1, p2);
		System.out.println("For " + p1.id + " "+ p2.id + "; CousinShip: " + bR.getCousinship() + ", Removal: " + bR.getRemoval());
			
		bR = g.findRelation( p1, p5);
		System.out.println("For " + p1.id  + " "+ p5.id +"; CousinShip: " + bR.getCousinship() + ", Removal: " + bR.getRemoval());
//		
		bR = g.findRelation( p5, p13);
		System.out.println("For " + p5.id  + " "+ p13.id +"; CousinShip: " + bR.getCousinship() + ", Removal: " + bR.getRemoval());
//		
		bR = g.findRelation( p6, p8);
		System.out.println("For " + p6.id  + " " + p8.id + "; CousinShip: " + bR.getCousinship() + ", Removal: " + bR.getRemoval());
//		
		bR = g.findRelation( p3, p11);
//		System.out.println("For " + p3.id  + " " + p11.id + "; CousinShip: " + bR.getCousinShip() + ", Removal: " + bR.getRemoval());
		
		g.descendents(p16, 4);
		
		g.ancestores(p9, 1);	
		
		
		System.out.println("NoteAndReference: " + g.notesAndReferences(p1).toString());
		
		System.out.println("MediabyTag " + g.findMediaByTag("t3", "2017-01-01", "2021-12-01"));
		
		System.out.println("MediabyLocation " + g.findMediaByLocation("Halifax", "2017-01-01", "2021-12-01"));
		
		
		
		personList.clear();
		personList.add(p3);
		personList.add(p2);
		personList.add(p1);
		personList.add(p4);
		
		fI.peopleInMedia(fI2, personList);
		
		
		personList.clear();
		personList.add(p5);
		personList.add(p3);
		
		fI.peopleInMedia(fI4, personList);
		
		personList.clear();
		personList.add(p1);
		personList.add(p2);
		
		fI.peopleInMedia(fI9, personList);
		
		personList.clear();
		personList.add(p11);
		personList.add(p14);
		personList.add(p15);
		
		fI.peopleInMedia(fI8, personList);
		
		personList.clear();
		personList.add(p10);
		
		fI.peopleInMedia(fI3, personList);
		
		Set<PersonIdentity> people = new HashSet<>();
		
//		people.add(p10);
		people.add(p10);
		people.add(p3);
		
		
		System.out.println("PeopleMedia: " + g.findIndividualsMedia( people, "2017-01-01", "2021-12-01"));
		
		
		System.out.println("ChildMedia: " + g.findBiologicalFamilyMedia(p10));
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Test
	void test1() {

		PersonIdentity pI = new PersonIdentity();
		
		PersonIdentity p1 = pI.addPerson("Dhruv");
		attributes.put("d", "1997-03-04");
		attributes.put("locOfBirth", "Surat");
		attributes.put("dateOfDeath", "2080-03-04");
		attributes.put("ld", "Surat");
		attributes.put("gender", "m");
		attributes.put("occupation", "engineer");
		assertTrue(pI.recordAttributes(p1, attributes));
		
		attributes = new HashMap<String, String>();		
		PersonIdentity p2 = pI.addPerson("Krushn");
		attributes.put("dateOfBirth", "2020");
		attributes.put("locOfBirth", "Bharuch");
		attributes.put("dateOfDeath", "1998-10-10");
		attributes.put("locOfDeath", "Bharuch");
		attributes.put("gender", "m");
		attributes.put("occupation", "manager");
		assertTrue(pI.recordAttributes(p2, attributes));
		
		attributes = new HashMap<String, String>();	
		attributes.put("occupation", "student");
		assertTrue(pI.recordAttributes(p2, attributes));
		
		attributes = new HashMap<String, String>();	
		attributes.put("occupation", "freelancer");
		assertTrue(pI.recordAttributes(p2, attributes));
		
		assertTrue(pI.recordReference(p1, "reference1"));
		assertTrue(pI.recordNote(p1, "note1"));
		assertTrue(pI.recordReference(p1, "reference2"));
		assertTrue(pI.recordReference(p2, "ref1"));
		assertTrue(pI.recordNote(p2, "note2"));
	
		
		FileIdentifier fI = new FileIdentifier();
		
		FileIdentifier fI1 = fI.addMediaFile("abc1.jpg");
		FileIdentifier fI2 = fI.addMediaFile("ebc2.jpg");
		FileIdentifier fI3 = fI.addMediaFile("fbc3.jpg");
		FileIdentifier fI4 = fI.addMediaFile("nbc4.jpg");
		FileIdentifier fI5 = fI.addMediaFile("gbc5.jpg");
		FileIdentifier fI6 = fI.addMediaFile("obc6.jpg");
		FileIdentifier fI7 = fI.addMediaFile("obc7.jpg");
		FileIdentifier fI8 = fI.addMediaFile("qbc8.jpg");
		FileIdentifier fI9 = fI.addMediaFile("sbc9.jpg");
		
		
		attributesFI.put("dateOfPictureTaken", "2021-11-30");
		attributesFI.put("locOfPictureTaken", "l1");
		attributesFI.put("city", "halifax");
		attributesFI.put("province", "ns");
		
		assertTrue(fI.recordMediaAttributes(fI1, attributesFI));
		
		assertTrue(fI.tagMedia(fI1, "t1"));
		
		attributesFI = new HashMap<String, String>();
		attributesFI.put("dateOfPictureTaken", "2021-12-1");
		attributesFI.put("locOfPictureTaken", "l2");
		assertTrue(fI.recordMediaAttributes(fI2, attributesFI));
		
		assertTrue(fI.tagMedia(fI2, "t2"));
		
		attributesFI = new HashMap<String, String>();
		attributesFI.put("dateOfPictureTaken", "2020-05-25");
		attributesFI.put("locOfPictureTaken", "l3");
		
		assertTrue(fI.recordMediaAttributes(fI3, attributesFI));
		
		assertTrue(fI.tagMedia(fI3, "t3"));
		
		attributesFI = new HashMap<String, String>();
		attributesFI.put("dateOfPictureTaken", "2020-01-30");
		attributesFI.put("locOfPictureTaken", "l2");
		
		assertTrue(fI.recordMediaAttributes(fI4, attributesFI));
		
		assertTrue(fI.tagMedia(fI4, "t4"));
		
		attributesFI = new HashMap<String, String>();
		attributesFI.put("dateOfPictureTaken", "2019-09-24");
		attributesFI.put("locOfPictureTaken", "l5");
		
		assertTrue(fI.recordMediaAttributes(fI5, attributesFI));
		
		assertTrue(fI.tagMedia(fI5, "t5"));
		
		attributesFI = new HashMap<String, String>();
		attributesFI.put("dateOfPictureTaken", "2019-03-09");
		attributesFI.put("locOfPictureTaken", "l2");
		
		assertTrue(fI.recordMediaAttributes(fI6, attributesFI));
		
		fI.tagMedia(fI6, "t6");
		
		attributesFI = new HashMap<String, String>();
		attributesFI.put("dateOfPictureTaken", "2018-11-10");
		attributesFI.put("locOfPictureTaken", "l7");
		
		fI.recordMediaAttributes(fI7, attributesFI);
		
		fI.tagMedia(fI7, "t3");
		
		attributesFI = new HashMap<String, String>();
		attributesFI.put("dateOfPictureTaken", "2019-09-24");
		attributesFI.put("locOfPictureTaken", "l2");
		
		fI.recordMediaAttributes(fI8, attributesFI);
		
		fI.tagMedia(fI8, "t8");
		
		attributesFI = new HashMap<String, String>();
		attributesFI.put("dateOfPictureTaken", "2017-04-07");
		attributesFI.put("locOfPictureTaken", "l1");
		
		fI.recordMediaAttributes(fI9, attributesFI);
		
		fI.tagMedia(fI9, "t3");
		
		
		personList.add(p1);
		
		personList.add(p2);
		
		fI.peopleInMedia(fI1, personList);
		

		PersonIdentity p3 = pI.addPerson("a");
		attributes = new HashMap<String, String>();	
		attributes.put("locOfDeath", "Sydney");
		attributes.put("LOB", "Halifax");
		attributes.put("dob", null);
		attributes.put("dateOfDeath", "2080-03-04");
		attributes.put("gender", "f");
		attributes.put("occupation", "student");
		attributes.put("occupation", "freelancer");
		attributes.put("occupation", "cashier");
		assertTrue(pI.recordAttributes(p3, attributes));
		
		PersonIdentity p4 = pI.addPerson("b");
		PersonIdentity p5 = pI.addPerson("c");
		PersonIdentity p6 = pI.addPerson("d");
		PersonIdentity p7 = pI.addPerson("e");
		PersonIdentity p8 = pI.addPerson("f");
		PersonIdentity p9 = pI.addPerson("g");
		PersonIdentity p10 = pI.addPerson("h");
		PersonIdentity p11 = pI.addPerson("i");
		PersonIdentity p12 = pI.addPerson("j");
		PersonIdentity p13 = pI.addPerson("k");
		PersonIdentity p14 = pI.addPerson("l");
		PersonIdentity p15 = pI.addPerson("m");
		PersonIdentity p16 = pI.addPerson("n");
		PersonIdentity p17 = pI.addPerson("o");

		
		assertTrue(pI.recordChild(p4, p1));
		assertTrue(pI.recordChild(p4, p2));
		assertTrue(pI.recordChild(p4, p3));
		assertTrue(pI.recordChild(p7, p5));
		assertTrue(pI.recordChild(p7, p6));
		assertTrue(pI.recordChild(p9, p7));
		assertTrue(pI.recordChild(p9, p8));
		assertTrue(pI.recordChild(p10, p4));
		assertTrue(pI.recordChild(p10, p9));
		assertTrue(pI.recordChild(p12, p11));
		assertTrue(pI.recordChild(p13, p9));
		assertTrue(pI.recordChild(p13, p12));
		assertTrue(pI.recordChild(p16, p15));
		assertTrue(pI.recordChild(p17, p15));
		assertTrue(pI.recordChild(p15, p10));
		assertTrue(pI.recordChild(p14, p10));
		assertFalse(pI.recordChild(p17, p10));
	
		
		assertTrue(pI.recordPartnering(p1, p2));
		assertTrue(pI.recordDissolution(p2, p1));
		
		assertFalse(pI.recordPartnering(p1, p2));
		assertFalse(pI.recordPartnering(p2, p1));
		
		assertFalse(pI.recordDissolution(p2, p1));
		assertFalse(pI.recordDissolution(p1, p2));
		
		assertFalse(pI.recordDissolution(p4, p5));
		assertFalse(pI.recordPartnering(p1, p5));
		assertFalse(pI.recordPartnering(p2, p5));
		assertFalse(pI.recordPartnering(p5, p2));
		assertTrue(pI.recordPartnering(p6, p8));
		
		
		BiologicalRelation bR = new BiologicalRelation();
		
		bR = bR.findRelation( p1, p2);
		System.out.println("For " + p1.id + " "+ p2.id + "; CousinShip: " + bR.getCousinship() + ", Removal: " + bR.getRemoval());
			
		bR = bR.findRelation( p1, p5);
		System.out.println("For " + p1.id  + " "+ p5.id +"; CousinShip: " + bR.getCousinship() + ", Removal: " + bR.getRemoval());
		
		bR = bR.findRelation( p5, p10);
		System.out.println("For " + p5.id  + " "+ p10.id +"; CousinShip: " + bR.getCousinship() + ", Removal: " + bR.getRemoval());
		
		bR = bR.findRelation( p6, p8);
		System.out.println("For " + p6.id  + " " + p8.id + "; CousinShip: " + bR.getCousinship() + ", Removal: " + bR.getRemoval());
		
		bR = bR.findRelation( p3, p11);
		//System.out.println("For " + p3.id  + " " + p11.id + "; CousinShip: " + bR.getCousinShip() + ", Removal: " + bR.getRemoval());
		
		Genealogy g = new Genealogy();
		
		
		System.out.println(p1);
		System.out.println(g.findPerson("Dhruv"));
		
		System.out.println(fI1);
		System.out.println(g.findMediaFile("abc.jpg"));
		
		System.out.println(g.findName(p1));
		
		System.out.println(g.findMediaFile(fI1));
		
		g.descendents(p16, 6);
		
		g.ancestores(p9, 1);	
		
		
		System.out.println("NoteAndReference: " + g.notesAndReferences(p1).toString());
		
		System.out.println("MediabyTag " + g.findMediaByTag("t3", "2017-01-01", "2021-12-01"));
		
		System.out.println("MediabyLocation " + g.findMediaByLocation("l2", "2017-01-01", "2021-12-01"));
		System.out.println("MediabyLocation " + g.findMediaByLocation("halifax", "2017-01-01", "2021-12-01"));
		
		personList.clear();
		personList.add(p3);
		personList.add(p2);
		personList.add(p1);
		personList.add(p4);
		
		fI.peopleInMedia(fI2, personList);
		
		
		personList.clear();
		personList.add(p5);
		personList.add(p3);
		
		fI.peopleInMedia(fI4, personList);
		
		personList.clear();
		personList.add(p1);
		personList.add(p2);
		
		fI.peopleInMedia(fI9, personList);
		
		personList.clear();
		personList.add(p11);
		personList.add(p14);
		personList.add(p15);
		
		fI.peopleInMedia(fI8, personList);
		
		personList.clear();
		personList.add(p10);
		
		fI.peopleInMedia(fI5, personList);
		
		Set<PersonIdentity> people = new HashSet<>();
		
		people.add(p10);
		people.add(p11);
		people.add(p1);
		
		
		System.out.println("PeopleMedia: " + g.findIndividualsMedia( people, "2017-01-01", "2021-12-01"));
		
		
		System.out.println("ChildMedia: " + g.findBiologicalFamilyMedia(p10));
	}


	@Test
	void test3() {
		List<PersonIdentity> l = new ArrayList<>();
		List<PersonIdentity> l1 = new ArrayList<>();
		
		PersonIdentity pI = new PersonIdentity();
		
		PersonIdentity p1 = pI.addPerson("a");
		Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("dateOfBirth", "2021-12-28");
		attributes.put("locOfBirth", "Surat");
		attributes.put("dateOfDeath", "2080-12-28");
		attributes.put("locOfDeath", "Surat");
		attributes.put("gender", "f");
		attributes.put("occupation", "engineer");
		pI.recordAttributes(p1, attributes);
		pI.recordReference(p1, "reference1");
		pI.recordReference(p1, "reference3");
		pI.recordReference(p1, "reference4");
		pI.recordNote(p1, "note1");
		pI.recordNote(p1, "note2");
		l.add(p1);
		
		PersonIdentity p2 = pI.addPerson("b");
		attributes = new HashMap<String, String>();
		attributes.put("dateOfBirth", "1998-11-12");
		attributes.put("locOfBirth", "Mumbai");
		attributes.put("dateOfDeath", "2085-12-12");
		attributes.put("locOfDeath", "Halifax");
		attributes.put("gender", "f");
		attributes.put("occupation", "student");
		pI.recordAttributes(p2, attributes);
		pI.recordReference(p2, "reference3");
		pI.recordNote(p2, "note2");
		l.add(p2);
		
		PersonIdentity p3 = pI.addPerson("c");
		PersonIdentity p4 = pI.addPerson("d");
		l1.add(p4);
		
		PersonIdentity p5 = pI.addPerson("e");
		attributes = new HashMap<String, String>();
		attributes.put("dateOfBirth", "2000-10-13");
		attributes.put("locOfBirth", "Mumbai");
		attributes.put("dateOfDeath", "2085-11-22");
		attributes.put("locOfDeath", "Halifax");
		attributes.put("gender", "m");
		attributes.put("occupation", "doctor");
		pI.recordAttributes(p5, attributes);
		pI.recordReference(p5, "reference5");
		pI.recordNote(p5, "note3");
		l.add(p5);
		l1.add(p5);
		
		PersonIdentity p6 = pI.addPerson("f");
		PersonIdentity p7 = pI.addPerson("g");
		PersonIdentity p8 = pI.addPerson("h");
		l1.add(p8);
		PersonIdentity p9 = pI.addPerson("i");
		PersonIdentity p10 = pI.addPerson("j");
		PersonIdentity p11 = pI.addPerson("k");
		PersonIdentity p12 = pI.addPerson("l");
		l1.add(p12);
		l.add(p12);
		PersonIdentity p13 = pI.addPerson("m");
		PersonIdentity p14 = pI.addPerson("n");
		PersonIdentity p15 = pI.addPerson("o");
		PersonIdentity p16 = pI.addPerson("p");
		PersonIdentity p17 = pI.addPerson("q");
		
		pI.recordChild(p4, p1);
		pI.recordChild(p4, p2);
		pI.recordChild(p4, p3);
		pI.recordChild(p7, p5);
		pI.recordChild(p7, p6);
		pI.recordChild(p9, p7);
		pI.recordChild(p9, p8);
		pI.recordChild(p10, p4);
		pI.recordChild(p10, p9);
		pI.recordChild(p12, p11);
		pI.recordChild(p13, p9);
		pI.recordChild(p13, p12);
		
		pI.recordChild(p16, p15);
		pI.recordChild(p17, p15);
		pI.recordChild(p15, p10);
		pI.recordChild(p14, p10);
		
		BiologicalRelation BRobj = new BiologicalRelation();
		BRobj.mapRelation();
		BRobj.findRelation(p1, p2);
		BRobj.findRelation(p1, p5);
		BRobj.findRelation(p5, p10);
		BRobj.findRelation(p6, p8);
		BRobj.findRelation(p3, p11);
		
		
		Genealogy g = new Genealogy();
		g.descendents(p7, 1);
		
		g.ancestores(p7,  3);
		g.notesAndReferences(p1);
		
//		g.findPerson("k");
//		g.findPerson("a");
//		g.findPerson("m");
//		
//		g.findName(p10);
//		g.findName(p2);
		
		FileIdentifier FIobj = new FileIdentifier();
		
		FileIdentifier fileIdentifier = FIobj.addMediaFile("location1");
		Map<String, String> mediaAttributes = new HashMap<String, String>();
		mediaAttributes.put("dateOfPictureTaken", "2021-12-28");
		mediaAttributes.put("locOfPictureTaken", "dalplex");
		FIobj.recordMediaAttributes(fileIdentifier, mediaAttributes);
		FIobj.tagMedia(fileIdentifier, "tag1");
		FIobj.tagMedia(fileIdentifier, "tag2");
		FIobj.peopleInMedia(fileIdentifier, l);
		
		FileIdentifier fileIdentifier1 = FIobj.addMediaFile("location2");
		mediaAttributes = new HashMap<String, String>();
		mediaAttributes.put("dateOfPictureTaken", "2001-10-23");
		mediaAttributes.put("locOfPictureTaken", "surat");
		FIobj.recordMediaAttributes(fileIdentifier1, mediaAttributes);
		FIobj.tagMedia(fileIdentifier1, "tag3");
		FIobj.tagMedia(fileIdentifier1, "tag1");
		FIobj.peopleInMedia(fileIdentifier1, l1);
		
		FileIdentifier fileIdentifier2 = FIobj.addMediaFile("location3");
		mediaAttributes = new HashMap<String, String>();
		mediaAttributes.put("dateOfPictureTaken", "2011-04-01");
		mediaAttributes.put("locOfPictureTaken", "dalplex");
		FIobj.recordMediaAttributes(fileIdentifier2, mediaAttributes);
		FIobj.tagMedia(fileIdentifier2, "tag3");
		FIobj.tagMedia(fileIdentifier2, "tag6");
		
		FileIdentifier fileIdentifier3 = FIobj.addMediaFile("location4");
		mediaAttributes = new HashMap<String, String>();
		mediaAttributes.put("dateOfPictureTaken", "2001-10-23");
		mediaAttributes.put("locOfPictureTaken", "mackeen");
		FIobj.recordMediaAttributes(fileIdentifier3, mediaAttributes);
		FIobj.tagMedia(fileIdentifier3, "tag1");
		FIobj.tagMedia(fileIdentifier3, "tag4");
		FIobj.peopleInMedia(fileIdentifier3, l1);
		
//		FileIdentifier fileIdentifier4 = FIobj.addMediaFile("location5");
//		mediaAttributes = new HashMap<String, String>();
//		mediaAttributes.put("locOfPictureTaken", "mackeen");
//		FIobj.recordMediaAttributes(fileIdentifier3, mediaAttributes);
//		FIobj.peopleInMedia(fileIdentifier4, l1);
		
		g.findMediaByTag("tag1", "2001-10-23", "2011-04-01");
		g.findMediaByLocation("dalplex", "2001-10-23", "2011-04-01");

		Set<PersonIdentity> people = new HashSet<>();
		people.add(p1);
		people.add(p5);
		people.add(p12);
		g.findIndividualsMedia(people, "2001-10-23", "2011-04-01");
		g.findBiologicalFamilyMedia(p4);
	}
	
}
