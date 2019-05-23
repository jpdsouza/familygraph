package com.wundermancommerce.interviewtests.graph;

import com.wundermancommerce.interviewtests.graph.csv.Person;
import com.wundermancommerce.interviewtests.graph.impl.FriendsFamilyGraphLoaderImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FriendsFamilyGraphLoaderTest {


    private final FriendsFamilyGraphLoader friendsFamilyGraphLoader = new FriendsFamilyGraphLoaderImpl();

    @Before
    public void setup() {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String PEOPLE_CSV_NAME = classLoader.getResource("people.csv").getFile();
        String RELATIONSHIP_CSV_NAME = classLoader.getResource("relationships.csv").getFile();
        friendsFamilyGraphLoader.importData(PEOPLE_CSV_NAME, RELATIONSHIP_CSV_NAME);
    }

    @Test
    public void testRelationshipMatches() {

        Person bob = new Person("Bob", "bob@bob.com",31);
        int actual = friendsFamilyGraphLoader.countRelationship(bob);
        assertEquals("Bob's relationship count doesnt match", 4, actual);

        Person jenny = new Person("Jenny", "jenny@toys.com" ,52);
        actual = friendsFamilyGraphLoader.countRelationship(jenny);
        assertEquals("Jenny's relationship count doesnt match", 3, actual);

        Person nigel = new Person("Nigel", "nigel@marketing.com",40);
        actual = friendsFamilyGraphLoader.countRelationship(nigel);
        assertEquals("Nigel's relationship count doesnt match", 2, actual);

        Person alan = new Person("Alan", "alan@lonely.org",23);
        actual = friendsFamilyGraphLoader.countRelationship(alan);
        assertEquals("Alan's relationship count doesnt match", 0, actual);
    }

    @Test
    public void testExtendedFamilyRelationship() {
        Person jenny = new Person("Jenny", "jenny@toys.com",52);
        int actual = friendsFamilyGraphLoader.countExtendedFamily(jenny);
        assertEquals("Jenny's relationship count doesnt match", 4, actual);

        Person bob = new Person("Bob", "bob@bob.com",31);
        actual = friendsFamilyGraphLoader.countExtendedFamily(bob);
        assertEquals("Bob's relationship count doesnt match", 4, actual);

    }

    @Test
    public void testPeopleLoaded() {
        assertEquals(friendsFamilyGraphLoader.countNumberofPeople(), 12);

    }

}
