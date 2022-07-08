import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

class TestCases {
	Map<String, String> attributes = new HashMap<String, String>();
	Map<String, String> attributesFI = new HashMap<String, String>();

	@Test
	void test() {
		List<PersonIdentity> l = new ArrayList<>();
		List<PersonIdentity> l1 = new ArrayList<>();
		
		PersonIdentity pI = new PersonIdentity();
		
		
		//Add person and record attributes
		PersonIdentity p1 = pI.addPerson("a");
		attributes = new HashMap<String, String>();	
		attributes.put("lod", "sydney");
		attributes.put("lob", "halifax");
		attributes.put("dob", "2000-12-28");
		attributes.put("dod", "2080-03-04");
		attributes.put("gender", "f");
		attributes.put("occupation", "student");
		assertTrue(pI.recordAttributes(p1, attributes));
		l.add(p1);
		l1.add(p1);
		
		PersonIdentity p2 = pI.addPerson("b");
		attributes = new HashMap<String, String>();	
		attributes.put("locOfbirth", "mumbai");
		attributes.put("lod", "surat");
		attributes.put("dod", "2075-02-20");
		attributes.put("dateOfbirth", "1998-01-14");
		attributes.put("gender", "m");
		attributes.put("occupation", "businessman");
		assertTrue(pI.recordAttributes(p2, attributes));
		
		attributes = new HashMap<String, String>();
		attributes.put("occupation", "Cashier");
		assertTrue(pI.recordAttributes(p2, attributes));
		
		attributes = new HashMap<String, String>();
		attributes.put("occupation", "Freelancer");
		assertTrue(pI.recordAttributes(p2, attributes));
		l.add(p2);
		l1.add(p2);
		
		PersonIdentity p3 = pI.addPerson("c");
		attributes = new HashMap<String, String>();	
		attributes.put("dob", "1999-05-01");
		attributes.put("lob", "delhi");
		attributes.put("dod", "2035-10-18");
		attributes.put("locOfDeath", "toronto");
		attributes.put("gender", "f");
		attributes.put("occupation", "teacher");
		assertTrue(pI.recordAttributes(p3, attributes));
		
		
		PersonIdentity p4 = pI.addPerson("d");
		attributes = new HashMap<String, String>();	
		attributes.put("dob", "2015-08-01");
		attributes.put("lob", "calgary");
		attributes.put("gender", "m");
		attributes.put("occupation", "engineer");
		assertTrue(pI.recordAttributes(p4, attributes));
		
		PersonIdentity p5 = pI.addPerson("e");
		attributes = new HashMap<String, String>();	
		attributes.put("dod", "2015-08-01");
		attributes.put("lod", "calgary");
		attributes.put("gender", "m");
		attributes.put("occupation", "engineer");
		assertTrue(pI.recordAttributes(p4, attributes));
		
		PersonIdentity p6 = pI.addPerson("f");
		attributes = new HashMap<String, String>();	
		attributes.put("d", "2015-08-01");
		assertFalse(pI.recordAttributes(p6, attributes));
		
		PersonIdentity p7 = pI.addPerson("g");
		l1.add(p7);
		
		PersonIdentity p8 = pI.addPerson("h");
		PersonIdentity p9 = pI.addPerson("i");
		l.add(p9);
		
		PersonIdentity p10 = pI.addPerson("j");
		l1.add(p10);
		PersonIdentity p11 = pI.addPerson("k");
		PersonIdentity p12 = pI.addPerson("l");
		PersonIdentity p13 = pI.addPerson("m");
		l.add(p13);
		
		
		//Record child
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
		assertFalse(pI.recordChild(p12, p13));
		
		PersonIdentity p14 = pI.addPerson("n");
		assertFalse(pI.recordChild(p14, p9));
		assertFalse(pI.recordChild(p12, p13));
		
		//Record notes and references
		assertTrue(pI.recordReference(p1, "reference1"));
		assertTrue(pI.recordNote(p1, "note1"));
		assertTrue(pI.recordNote(p1, "note2"));
		assertTrue(pI.recordReference(p1, "reference2"));
		
		assertTrue(pI.recordNote(p2, "note5"));
		assertTrue(pI.recordReference(p2, "reference3"));
		
		assertTrue(pI.recordNote(p3, "note6"));
		assertTrue(pI.recordReference(p3, "reference7"));
		assertTrue(pI.recordReference(p3, "reference8"));
		assertTrue(pI.recordNote(p3, "note7"));
		assertTrue(pI.recordNote(p3, "note8"));
		assertTrue(pI.recordReference(p3, "reference9"));
		
		assertFalse(pI.recordReference(p4, ""));
		assertFalse(pI.recordReference(null, "reference45"));
		
		
		//Record partnering and dissolution
		assertTrue(pI.recordPartnering(p1, p2));
		assertTrue(pI.recordDissolution(p2, p1));
		assertTrue(pI.recordPartnering(p3, p4));
		assertTrue(pI.recordPartnering(p6, p8));
		assertTrue(pI.recordPartnering(p10, p13));
		assertTrue(pI.recordDissolution(p10, p13));
		assertTrue(pI.recordPartnering(p1, p7));
		
		assertFalse(pI.recordPartnering(p1, p2));
		assertFalse(pI.recordPartnering(p2, p1));
		assertFalse(pI.recordDissolution(p2, p1));
		assertFalse(pI.recordDissolution(p1, p2));
		assertFalse(pI.recordDissolution(p4, p5));
		assertFalse(pI.recordPartnering(p3, p11));
		
		
		
		//FileIdentifier
		FileIdentifier fI = new FileIdentifier();
		
		attributesFI = new HashMap<String, String>();
		FileIdentifier fI1 = fI.addMediaFile("abc1.jpg");
		attributesFI.put("dateOfPictureTaken", "2021-11-30");
		attributesFI.put("locOfPictureTaken", "l1");
		attributesFI.put("city", "halifax");
		attributesFI.put("province", "ns");
		assertTrue(fI.recordMediaAttributes(fI1, attributesFI));
		assertTrue(fI.peopleInMedia(fI1, l));
		assertTrue(fI.tagMedia(fI1, "tag1"));
		assertTrue(fI.tagMedia(fI1, "tag2"));
		assertTrue(fI.tagMedia(fI1, "tag3"));
		
		attributesFI = new HashMap<String, String>();
		FileIdentifier fI2 = fI.addMediaFile("ebc2.jpg");
		attributesFI.put("dateOfPicture", "2018-01");
		attributesFI.put("locOfPicture", "l2");
		attributesFI.put("city", "halifax");
		assertTrue(fI.recordMediaAttributes(fI2, attributesFI));
		assertTrue(fI.peopleInMedia(fI2, l1));
		assertTrue(fI.tagMedia(fI2, "tag1"));
		
		attributesFI = new HashMap<String, String>();
		FileIdentifier fI3 = fI.addMediaFile("fbc3.jpg");
		attributesFI.put("dop", "2019");
		attributesFI.put("lop", "l1");
		assertTrue(fI.recordMediaAttributes(fI3, attributesFI));
		
		attributesFI = new HashMap<String, String>();
		FileIdentifier fI4 = fI.addMediaFile("nbc4.jpg");
		assertFalse(fI.recordMediaAttributes(fI4, attributesFI));
		
		FileIdentifier fI5 = fI.addMediaFile("gbc5.jpg");
		assertTrue(fI.tagMedia(fI5, "tag4"));
		
		FileIdentifier fI6 = fI.addMediaFile("obc6.jpg");
		assertTrue(fI.tagMedia(fI6, "tag4"));
		
		FileIdentifier fI7 = fI.addMediaFile("obc7.jpg");
		List<PersonIdentity> l2 = new ArrayList<>();
		assertFalse(fI.peopleInMedia(fI7, l2));
		
		FileIdentifier fI8 = fI.addMediaFile("qbc8.jpg");
		FileIdentifier fI9 = fI.addMediaFile("sbc9.jpg");
		
		Genealogy g = new Genealogy();
		System.out.println(g.findPerson("Aayushi"));
		System.out.println(g.findMediaFile("abc1.jpg"));
		System.out.println(g.findName(p1));
		System.out.println(g.findMediaFile(fI1));
		
		System.out.println(g.findRelation(p6, p13));
		System.out.println(g.findRelation(p2, p3));
		System.out.println(g.findRelation(p1, p9));
		System.out.println(g.findRelation(p10, p13));
		System.out.println(g.findRelation(p10, p10));
		
		Set<PersonIdentity> d = new LinkedHashSet<>();
		List<String> name = new ArrayList<>();
		d = g.descendents(p13, 2);
		List<String> nameManually = Arrays.asList("i","l","g","h","k");
		for(PersonIdentity p : d) {
			name.add( g.findName(p));
		}
		assertEquals(nameManually , name);
		
		
		d = new LinkedHashSet<>();
		name = new ArrayList<>();
		d = g.descendents(p10, 4);
		nameManually = Arrays.asList("d","i","a","b","c","g","h","e","f");
		for(PersonIdentity p : d) {
			name.add( g.findName(p));
		}
		assertEquals(nameManually , name);
		
		d = new LinkedHashSet<>();
		name = new ArrayList<>();
		d = g.descendents(p2, 2);
		nameManually = new ArrayList<>();
		for(PersonIdentity p : d) {
			name.add( g.findName(p));
		}
		assertEquals(nameManually , name);
		
		
		d = new LinkedHashSet<>();
		name = new ArrayList<>();
		d = g.descendents(p2, 0);
		nameManually = new ArrayList<>();
		for(PersonIdentity p : d) {
			name.add( g.findName(p));
		}
		assertEquals(nameManually , name);
		
		
		
		Set<PersonIdentity> a = new LinkedHashSet<>();
		name = new ArrayList<>();
		a = g.ancestores(p5, 5);
		nameManually = Arrays.asList("g","i","j","m");
		for(PersonIdentity p : a) {
			name.add( g.findName(p));
		}
		assertEquals(nameManually , name);
				
		
		a = new LinkedHashSet<>();
		name = new ArrayList<>();
		a = g.ancestores(p11, 1);
		nameManually = Arrays.asList("l");
		for(PersonIdentity p : a) {
			name.add( g.findName(p));
		}
		assertEquals(nameManually , name);
		
		
		a = new LinkedHashSet<>();
		name = new ArrayList<>();
		a = g.ancestores(p13, 2);
		nameManually = new ArrayList<>();
		for(PersonIdentity p : a) {
			name.add( g.findName(p));
		}
		assertEquals(nameManually , name);
		
		//notes and references
		List<String> nr = Arrays.asList("reference1","note1","note2","reference2");
		assertEquals(nr, g.notesAndReferences(p1));
		
		assertEquals(null, g.notesAndReferences(null));
		
		//findMediaByTag		
		Set<FileIdentifier> m = new LinkedHashSet<>();
		name = new ArrayList<>();
		Set<FileIdentifier> t = g.findMediaByTag("tag1", "2019-11-30", "2021-12-30");
		nameManually = Arrays.asList("abc1.jpg");
		for(FileIdentifier p : t) {
			name.add( g.findMediaFile(p));
		}
		assertEquals(nameManually, name);
		
		
		name = new ArrayList<>();
		assertEquals(null, g.findMediaByTag("", "2019-11-30", "2021-12-30"));
		
		//findMediaByLocation
		t = new LinkedHashSet<>();
		name = new ArrayList<>();
		t = g.findMediaByLocation("l1", "", "");
		nameManually = Arrays.asList("abc1.jpg","fbc3.jpg");
		for(FileIdentifier p : t) {
			name.add( g.findMediaFile(p));
		}
		assertEquals(nameManually, name);
		
		
		//findIndividualsMedia
		Set<PersonIdentity> people = new LinkedHashSet<>();
		people.add(p1);
		people.add(p13);
		
		name = new ArrayList<>();
		List<FileIdentifier> pm = g.findIndividualsMedia(people, "", "");
		nameManually = Arrays.asList("ebc2.jpg","abc1.jpg");
		for(FileIdentifier p : pm) {
			name.add( g.findMediaFile(p));
		}
		assertEquals(nameManually, name);
		
		//findBiologicalFamilyMedia
		name = new ArrayList<>();
		List<FileIdentifier> im = g.findBiologicalFamilyMedia(p4);
		nameManually = Arrays.asList("ebc2.jpg","abc1.jpg");
		for(FileIdentifier p : im) {
			name.add( g.findMediaFile(p));
		}
		assertEquals(nameManually, name);
		
		assertEquals(null, g.findBiologicalFamilyMedia(null));
		
	}
}
