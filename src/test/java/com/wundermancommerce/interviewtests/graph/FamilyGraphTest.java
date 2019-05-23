package com.wundermancommerce.interviewtests.graph;

import com.wundermancommerce.interviewtests.graph.csv.Person;
import com.wundermancommerce.interviewtests.graph.csv.RelationshipData;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class FamilyGraphTest {


    private FamilyGraphLoader familyGraphLoader = new FamilyGraphLoader();

    private static String PEOPLE_CSV_NAME;
    private static String RELATIONSHIP_CSV_NAME;

    @Before
    public void setup() {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        PEOPLE_CSV_NAME = classLoader.getResource("people.csv").getFile();
        RELATIONSHIP_CSV_NAME = classLoader.getResource("relationships.csv").getFile();
        familyGraphLoader.importData(PEOPLE_CSV_NAME, RELATIONSHIP_CSV_NAME);
    }

    @Test
    public void testRelationshipMatches() {

        Person bob = new Person("Bob", "bob@bob.com",31);
        int actual = familyGraphLoader.countRelationship(bob);
        assertEquals("Bob's relationship count doesnt match", 4, actual);

        Person jenny = new Person("Jenny", "jenny@toys.com" ,52);
        actual = familyGraphLoader.countRelationship(jenny);
        assertEquals("Jenny's relationship count doesnt match", 3, actual);

        Person nigel = new Person("Nigel", "nigel@marketing.com",40);
        actual = familyGraphLoader.countRelationship(nigel);
        assertEquals("Nigel's relationship count doesnt match", 2, actual);

        Person alan = new Person("Alan", "alan@lonely.org",23);
        actual = familyGraphLoader.countRelationship(alan);
        assertEquals("Alan's relationship count doesnt match", 0, actual);
    }

    @Test
    public void testExtendedFamilyRelationship() {
        Person jenny = new Person("Jenny", "jenny@toys.com",52);
        int actual = familyGraphLoader.countExtendedRelationship(jenny);
        assertEquals("Jenny's relationship count doesnt match", 4, actual);

        Person bob = new Person("Bob", "bob@bob.com",31);
        actual = familyGraphLoader.countExtendedRelationship(bob);
        assertEquals("Bob's relationship count doesnt match", 4, actual);

    }

    @Test
    public void testProcessPeopleCSVInputFile() {
        List<Person> actual = familyGraphLoader.processInputFile(PEOPLE_CSV_NAME, FamilyGraphLoader.mapToItem);
        assertEquals(actual.size(), 12);
        List<RelationshipData> actualRelation = familyGraphLoader.processInputFile(RELATIONSHIP_CSV_NAME, FamilyGraphLoader.mapToRelationShip);
        assertEquals(actualRelation.size(), 16);
    }

    @Test
    public void testProcessRelationshipCSVInputFile() {
        List<RelationshipData> actualRelation = familyGraphLoader.processInputFile(RELATIONSHIP_CSV_NAME, FamilyGraphLoader.mapToRelationShip);
        assertEquals(actualRelation.size(), 16);
    }

}
