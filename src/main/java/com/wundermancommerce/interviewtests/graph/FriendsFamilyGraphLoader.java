package com.wundermancommerce.interviewtests.graph;

import com.wundermancommerce.interviewtests.graph.csv.Person;

/**
 * Friends and family graph loader.
 */
public interface FriendsFamilyGraphLoader {

    /**
     * Return a related number people for this <p>Person<p/>.
     *
     * @param person related person to be returned for
     * @return the number of related people friends or family
     */
    int countRelationship(Person person);

    /**
     * Return the number of extended family members for a given person.
     *
     * @param person given person for which extended family member should be returned
     * @return the number of extended family members for a given person.
     */
    int countExtendedFamily(Person person);

    /**
     * Return the number of members loaded in memory.
     *
     * @return the number of members
     */
    int countNumberofPeople();

    /**
     * Load a given list of members and their relationship from csv file.
     *
     * @param peopleCsvName csv file name corresponding to list of people
     * @param relationshipCsvName csv file name corresponding to relationship data
     */
    void importData(String peopleCsvName, String relationshipCsvName);

}
