package com.jmdarling;

public class Person {
    /*
    The naming of these variables is very important. For gson to work, the variable names must match the key names in
    the JSON object the Person objects will be created from.
     */
    private String name;
    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Hi! My name is " + name + " and I am " + age + " years old. It is nice to meet you!";
    }
}
