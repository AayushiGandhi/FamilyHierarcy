CREATE SCHEMA project;

USE project;

# familyTree
CREATE TABLE familyTree (
	id INTEGER NOT NULL,
	name VARCHAR(50),
	date_of_birth DATE  DEFAULT NULL,
	loc_of_birth VARCHAR(50),
	date_of_death DATE  DEFAULT NULL,
	loc_of_death VARCHAR(50),
	gender VARCHAR(50),
    CONSTRAINT id_pk PRIMARY KEY (id));
    
# occupationTable
CREATE TABLE occupation (
	id INTEGER NOT NULL,
	occupation VARCHAR(50),
    CONSTRAINT id_fk FOREIGN KEY (id)
        REFERENCES familytree (id));

# referenceNotes
CREATE TABLE referenceNotes (
	refNo INTEGER NOT NULL,
	referenceNote VARCHAR(100),
	decide INTEGER,
    CONSTRAINT refNo_fk FOREIGN KEY (refNo)
        REFERENCES familytree (id));
 
# childTable
CREATE TABLE childTable (
	parentID INTEGER NOT NULL,
	childID INTEGER NOT NULL,
    CONSTRAINT childID_fk
	FOREIGN KEY (childID) REFERENCES familytree (id),
    CONSTRAINT parentID_fk
	FOREIGN KEY (parentID) REFERENCES familytree (id));

# partnerTable
CREATE TABLE partnerTable (
	partner1 INTEGER NOT NULL,
	partner2 INTEGER NOT NULL,
    dissolution INTEGER,
    CONSTRAINT partner1_fk FOREIGN KEY (partner1)
        REFERENCES familytree (id),
    CONSTRAINT partner2_fk FOREIGN KEY (partner2)
        REFERENCES familytree (id));

# mediaArchive
CREATE TABLE mediaArchive (
	fileLocation VARCHAR(50) NOT NULL,
	dateOfPictureTaken DATE DEFAULT NULL,
	locOfPictureTaken VARCHAR(50),
    CONSTRAINT fileLocation_pk PRIMARY KEY (fileLocation));
 
# peopleInMediaTable
CREATE TABLE peopleInMediaTable (
	fileLocation VARCHAR(50) NOT NULL,
	peopleID INTEGER NOT NULL,
    CONSTRAINT fileLocation_fk FOREIGN KEY (fileLocation)
        REFERENCES mediaArchive (fileLocation),
	CONSTRAINT peopleID_fk FOREIGN KEY (peopleID) 
		REFERENCES familytree (id));
    
# tagTable
CREATE TABLE tagTable (
	fileLocation VARCHAR(50) NOT NULL,
	tagString VARCHAR(100),
    CONSTRAINT fileLocationTag_fk FOREIGN KEY (fileLocation)
        REFERENCES mediaArchive (fileLocation));

# mediaDateTable
CREATE TABLE mediaDateTable (
	fileLocation VARCHAR(50) NOT NULL,
    dateOfPictureTaken DATE DEFAULT NULL,
    CONSTRAINT fileLocationMedia_fk FOREIGN KEY (fileLocation)
        REFERENCES mediaArchive (fileLocation));

# attributesTable
CREATE TABLE attributesTableForPerson (
	attribute VARCHAR(50),
    columnID INTEGER
);

INSERT INTO attributesTableForPerson
VALUES 
	("dob" , 3),
    ("dateofbirth", 3),
    ("birthdate", 3),
    ("date_of_birth", 3),
    
    ("lob", 4),
    ("locofbirth", 4),
    ("locationofbirth", 4),
    ("birthlocation", 6),
    ("location_of_birth", 4),
    
    ("dod", 5),
    ("dateofdeath", 5),
    ("deathdate", 5),
    ("date_of_death", 5),
    
    ("lod", 6),
    ("locationofdeath", 6),
    ("locofdeath", 6),
    ("deathlocation", 6),
    ("location_of_death", 6),
    
    ("gender", 7);
    
    
# attributesTableForMedia
CREATE TABLE attributesTableForMedia (
	attribute VARCHAR(50),
    columnID INTEGER
);

INSERT INTO attributesTableForMedia
VALUES 
    ("dateofpicturetaken" , 2),
    ("dateofpicture" , 2),
    ("dop" , 2),
    ("picturedate" , 2),
    ("date" , 2),
    ("year" , 2),
    ("locofpicturetaken" , 3),
    ("location" , 3),
    ("lop" , 3),
    ("city" , 3),
    ("province" , 3),
    ("country" , 3),
    ("picturelocation" , 3),
    ("locationofpicture" , 3);
    
