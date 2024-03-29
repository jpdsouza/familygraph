package com.wundermancommerce.interviewtests.graph.csv;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;

public class Person implements Comparable<Person>{

    private String name;
    private String email;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Person(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                Objects.equal(name, person.name) &&
                Objects.equal(email, person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, email, age);
    }

    public int compareTo(Person that) {
        return ComparisonChain.start()
                .compare(this.name, that.name)
                .compare(this.email, that.email)
                .compare(this.age, that.age)
                .result();
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}
