package com.wundermancommerce.interviewtests.graph.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wundermancommerce.interviewtests.graph.FriendsFamilyGraphLoader;
import com.wundermancommerce.interviewtests.graph.csv.Person;
import com.wundermancommerce.interviewtests.graph.csv.Relationship;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Friends and family graph loader.
 */
public class FriendsFamilyGraphLoaderImpl implements FriendsFamilyGraphLoader {

    private static final String COMMA = ",";

    public static final Function<String, Person> MAP_TO_PERSON = (line) -> {
        String[] p = line.split(COMMA);
        return new Person(p[0], p[1], Integer.valueOf(p[2]));
    };

    public static final Function<String, Relationship> MAP_TO_RELATION_SHIP = (line) -> {
        String[] p = line.split(COMMA);
        return new Relationship(p[0], p[2], p[1]);
    };

    private static final Function<Person, String> email = Person::getEmail;
    private static final String FAMILY = "FAMILY";
    private static final String FRIEND = "FRIEND";
    private List<Person> people = Lists.newArrayList();
    private final Map<Person, LinkedList<Person>> familyRelationship = Maps.newHashMap();
    private final Map<Person, LinkedList<Person>> friendsRelationship = Maps.newHashMap();

    @Override
    public int countRelationship(Person person) {
        return familyRelationship.getOrDefault(person, Lists.newLinkedList()).size()
                + friendsRelationship.getOrDefault(person, Lists.newLinkedList()).size();
    }

    @Override
    public int countExtendedFamily(Person person) {
        Map<Person, Boolean> visited = Maps.newHashMap();
        search(person, visited);
        return visited.size();
    }

    @Override
    public int countNumberofPeople() {
        return people.size();
    }

    @Override
    public void importData(String peopleCsvName, String relationshipCsvName) {
        people = importCsv(peopleCsvName, MAP_TO_PERSON);
        Map<String, Person> mapOfPerson =
                people.stream().collect(Collectors.toMap(email, Function.identity()));
        List<Relationship> relationshipList = importCsv(relationshipCsvName, MAP_TO_RELATION_SHIP);

        // construct undirected graph for friends or family
        relationshipList.forEach(
                r -> addRelationShipData(mapOfPerson.get(r.getPerson1()),
                        mapOfPerson.get(r.getPerson2()), r.getRelatedBy()));

    }

    private void addRelationShipData(Person source, Person destination, String relatedBy) {
        if (FAMILY.equalsIgnoreCase(relatedBy)) {
            familyRelationship.putIfAbsent(source, Lists.newLinkedList());
            familyRelationship.putIfAbsent(destination, Lists.newLinkedList());
            familyRelationship.get(source).addFirst(destination);
            familyRelationship.get(destination).addFirst(source);

        }
        if (FRIEND.equalsIgnoreCase(relatedBy)) {
            friendsRelationship.putIfAbsent(source, Lists.newLinkedList());
            friendsRelationship.putIfAbsent(destination, Lists.newLinkedList());
            friendsRelationship.get(source).addFirst(destination);
            friendsRelationship.get(destination).addFirst(source);
        }

    }


    // Perform depth first search
    private void search(Person person, Map<Person, Boolean> visited) {
        visited.put(person, true);
        LinkedList<Person> edges = familyRelationship.getOrDefault(person, Lists.newLinkedList());

        for (Person edge : edges) {
            if (isNotVisited(visited, edge)) {
                search(edge, visited);
            }
        }
    }

    private boolean isNotVisited(Map<Person, Boolean> visited, Person person) {
        return visited.get(person) == null || !visited.get(person);
    }


    // Import the csv file into corresponding list of type
    private  <K> List<K> importCsv(String inputFilePath, Function<String, K> mappingTo) {
        List<K> inputList = Lists.newArrayList();
        try {

            File inputF = new File(inputFilePath);
            InputStream inputFS = new FileInputStream(inputF);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));

            inputList = br.lines()
                    .filter(line -> !line.isEmpty())  // ignore empty lines
                    .map(mappingTo).collect(Collectors.toList());
            br.close();
        } catch (IOException e) {
            System.out.println("Error loading file : cause " + e.getCause());

        }
        return inputList;
    }

}
