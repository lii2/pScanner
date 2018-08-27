package com.jeff.application.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jeff.clients.email.model.PhoneNumberList;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class Converter {

    public static String getProperty(String propertyName) {

        Properties prop = new Properties();
        InputStream input = null;
        String propertyValue = "";

        try {

            input = new FileInputStream("configuration.properties");

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            propertyValue = prop.getProperty(propertyName);

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return propertyValue;
        }

    }

    public static String getJson(String path) {

        FileReader fr = null;
        BufferedReader br = null;

        StringBuilder json = new StringBuilder();

        try {

            fr = new FileReader(path);
            br = new BufferedReader(fr);

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                json.append(sCurrentLine);
            }


        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {

            try {

                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

            return json.toString();
        }
    }

    public static PhoneNumberList getPhoneNumberList() {
        String phoneNumberJson = getJson("phonenumbers.json");

        ObjectMapper mapper = new ObjectMapper();

        PhoneNumberList phoneNumberList = null;

        try {
            phoneNumberList = mapper.readValue(phoneNumberJson, PhoneNumberList.class);
        } catch (IOException e) {
            System.out.println("IOException thrown while mapping");
            e.printStackTrace();
        }

        return phoneNumberList;
    }

    public static List<String> getOldPosts() {

        FileReader fr = null;
        BufferedReader br = null;

        List<String> oldPosts = new LinkedList<>();

        try {

            fr = new FileReader("oldPosts.txt");
            br = new BufferedReader(fr);

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                oldPosts.add(sCurrentLine);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {

            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return oldPosts;
        }
    }

    public static void addOldPost(String postTitle) {
        try {
            postTitle = postTitle + "\n";
            Files.write(Paths.get("oldPosts.txt"), postTitle.getBytes(), StandardOpenOption.APPEND);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }

}
