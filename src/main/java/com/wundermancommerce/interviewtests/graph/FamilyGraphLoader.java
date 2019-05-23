package com.wundermancommerce.interviewtests.graph;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wundermancommerce.interviewtests.graph.csv.Person;
import com.wundermancommerce.interviewtests.graph.csv.RelationshipData;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class FamilyGraphLoader {

    private static final String COMMA = ",";

    public static Function<String, Person> mapToItem = (line) -> {
        String[] p = line.split(COMMA);
        return new Person(p[0], p[1], Integer.valueOf(p[2]));
    };

    public static Function<String, RelationshipData> mapToRelationShip = (line) -> {
        String[] p = line.split(COMMA);
        return new RelationshipData(p[0], p[2], p[1]);
    };

    private static Function<Person, String> email = Person::getEmail;

    private Map<Person, LinkedList<Person>> familyRelationship = Maps.newHashMap();
    private Map<Person, LinkedList<Person>> friendsRelatiionship = Maps.newHashMap();

    public void addEgde(Person source, Person destination, String relatedBy) {
        if (relatedBy.equalsIgnoreCase("FAMILY")) {
            familyRelationship.putIfAbsent(source, Lists.newLinkedList());
            familyRelationship.putIfAbsent(destination, Lists.newLinkedList());

            familyRelationship.get(source).push(destination);
            familyRelationship.get(destination).push(source);

        }
        if (relatedBy.equalsIgnoreCase("FRIEND")) {
            friendsRelatiionship.putIfAbsent(source, Lists.newLinkedList());
            friendsRelatiionship.putIfAbsent(destination, Lists.newLinkedList());

            friendsRelatiionship.get(source).push(destination);
            friendsRelatiionship.get(destination).push(source);

        }

    }

    public int countRelationship(Person person) {
        return familyRelationship.getOrDefault(person, Lists.newLinkedList()).size()
                + friendsRelatiionship.getOrDefault(person, Lists.newLinkedList()).size();
    }

    // A function used by DFS
    void search(Person person, Map<Person, Boolean> visited) {
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

    public int countExtendedRelationship(Person person) {
        Map<Person, Boolean> visited = Maps.newHashMap();
        search(person, visited);
        return visited.size();
    }

    public void importData(String people_csv_name, String relationship_csv_name) {
        Map<String, Person> mapOfPerson =
                processInputFile(people_csv_name, mapToItem).stream().collect(Collectors.toMap(email, Function.identity()));
        List<RelationshipData> relationshipDataList = processInputFile(relationship_csv_name, mapToRelationShip);
        constructRelationshipTree(mapOfPerson, relationshipDataList);
    }

    private void constructRelationshipTree(Map<String, Person> mapOfPerson, List<RelationshipData> relationshipDataList) {
        for (RelationshipData r : relationshipDataList) {
            addEgde(mapOfPerson.get(r.getPerson1()), mapOfPerson.get(r.getPerson2()), r.getRelatedBy());
        }
    }

    protected <K> List<K> processInputFile(String inputFilePath, Function<String, K> function) {
        List<K> inputList = Lists.newArrayList();
        try {

            File inputF = new File(inputFilePath);
            InputStream inputFS = new FileInputStream(inputF);
            BufferedReader br = new BufferedReader(new InputStreamReader(inputFS));
            inputList = br.lines().filter(line -> !line.isEmpty()).map(function).collect(Collectors.toList());
            br.close();
        } catch (IOException e) {

        }
        return inputList;
    }

    public Map<Person, LinkedList<Person>> getFamilyRelationship() {
        return familyRelationship;
    }

    public Map<Person, LinkedList<Person>> getFriendsRelatiionship() {
        return friendsRelatiionship;
    }




}
