/**
 * A simple example of handling JSON in Java using java-json and gson. This code does not necessarily follow best
 * practices as it has been structures for maximum simplicity.
 *
 * The goal of this program is to create an array list of People objects from some JSON and print out each person's
 * toString().
 */
package com.jmdarling;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// These require java-json (http://www.json.org/java/).
import org.json.JSONArray;
import org.json.JSONObject;

// This requires json (https://code.google.com/p/google-gson/).
import com.google.gson.*;

public class Main {

    // The location of the json file we will be importing.
    /*
    Take note of the structure of the JSON file as this is quite important. You can see the the outermost element of the
    file is an array. You can tell this because it is surrounded by curly braces ('{' and '}'). Inside of that object,
    we have a key ("people") that is pointing to an array. You can tell it is an array as it is surrounded by square
    brackets ('[' and ']'). Inside this array we have more objects each with two name-value pairs for "name" and "age".
    This structure will be quite important to understand when we start parsing it out.
     */
    private static String mJsonFileLoc = "file.txt";

    public static void main(String[] args) {
        List<Person> people = new ArrayList<>();

        // Read in the file containing JSON.
        String json = readFile(mJsonFileLoc);
        System.out.println("The file contained: " + json);

        // Convert the JSON to a JSONObject.
        /*
        Notice that we are converting the JSON to a JSONObject, not a JSONArray. We are doing this because, as mentioned
        earlier, the outermost element of the JSON in the file is an object.
         */
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(json);
        } catch (Exception e) {
            System.err.println("Failed to convert the JSON to a JSONObject.");
            e.printStackTrace();
            jsonObject = new JSONObject();
        }

        // Get the nested array in the JSONObject.
        /*
        At this point, we have a JSONObject that we parsed from the JSON. The data that we really need is in the array
        that is nested in this object. Looking at the JSON in the file, we can see that the array is the value to the
        key "people". To fetch this array, we need to get the array in the object with the key "people".
         */
        JSONArray jsonArray;
        try {
            jsonArray = jsonObject.getJSONArray("people");
        } catch (Exception e) {
            System.err.println("Failed to get the JSONArray with key \"users\" from the JSONObject");
            e.printStackTrace();
            jsonArray = new JSONArray();
        }

        // Convert each "user" in the JSONArray to a Person object and put them in our people list.
        /*
        We now have our JSONArray that contains two objects, each with a person's information. We want to create new
        Person objects out of each of these sets of information. To do that, we will iterate through the JSONArray and
        use gson to magically convert the raw data to an object.
         */
        Gson gson = new Gson();
        for (int i = 0; i < jsonArray.length(); i++) {
            Person person;

            // Convert each JSONObject in the JSONArray to a string as this is what gson acts on.
            /*
            Step by step, gson is going to be creating an object fromJson(). The JSON it will be converting from is
            inside the object at the current index (i). Gson acts upon Strings, not JSONObjects. Because of this, we
            need to convert the JSONObject to a string using its toString() method. The second parameter that
            gson.fromJson takes is the Class the object we are creating is derived from.
             */
            try {
                person = gson.fromJson(jsonArray.getJSONObject(i).toString(), Person.class);
            } catch (Exception e) {
                System.err.println("Failed to convert the JSON at index " + i + " to a Person.");
                e.printStackTrace();
                person = new Person();
            }

            // Add the newly created person to our people list.
            people.add(person);
        }

        // Print the data for each person in our People list.
        for (int i = 0; i < people.size(); i++) {
            System.out.println(people.get(i));
        }
    }

    /**
     * Returns a string containing the contents of the specified file.
     *
     * @param fileLoc The location of the file that will have its contents returned.
     *
     * @return A string containing the contents of the specified file.
     */
    private static String readFile(String fileLoc) {
        try {
            return new Scanner(new File(fileLoc)).useDelimiter("\\Z").next();
        } catch (Exception e) {
            System.err.println("Failed to open the file. Make sure that mJsonFileLoc is pointing to the right location!.");
            e.printStackTrace();
            return "";
        }
    }
}
