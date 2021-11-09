/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package candidateeliminate;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Hamed Khashehchi
 */
public class ReadCSV {

    private ArrayList<Attributes> attributes;
    private ArrayList<Classes> classes;
    private ArrayList<Entries> entries;

    public ReadCSV(String path, String positive, String negative) {
        ArrayList<String[]> readCSV = readCSV(path);
//        System.out.println("readCSV = " + readCSV);
        this.attributes = new ArrayList<>();
        this.classes = new ArrayList<>();
        this.entries = new ArrayList<>();
        Classes.setPossitive(positive);
        Classes.setNegative(negative);
//        System.out.println("WTF");
        String[] get = readCSV.get(0);

        for (int i = 0; i < get.length - 1; i++) {
            this.attributes.add(new Attributes(get[i]));
//            System.out.println("s = " + s);
//            System.out.println(new Attributes(s));
        }
//        System.out.println("attributes = " + this.attributes);
        for (int i = 1; i < readCSV.size(); i++) {
            get = readCSV.get(i);
            for (int j = 0; j < get.length - 1; j++) {
                this.attributes.get(j).setDomain(get[j]);
            }
            this.classes.add(new Classes(get[get.length - 1]));
            this.entries.add(new Entries(attributes, classes.get(i - 1), get));
        }
//        System.out.println("attributes = " + attributes);
//        System.out.println("classes = " + classes);
//        System.out.println("entries = " + entries);
    }

    private ArrayList<String[]> readCSV(String pathname) {
        Path pathToFile = Paths.get(pathname);
        ArrayList<String[]> entries = new ArrayList<>();
//        System.out.println("pathToFile = " + pathToFile);
//        System.out.println("pathToFile = " + pathToFile.getParent());
        try (BufferedReader br = Files.newBufferedReader(pathToFile)) {
            String line = br.readLine();
            while (line != null) {

                String[] attributes = line.split(",");
//                System.out.println("attributes = " + Arrays.toString(attributes));
                entries.add(attributes);
                line = br.readLine();
            }

        } catch (IOException ex) {
        }
        return entries;
    }

    public ArrayList<Attributes> getAttributes() {
        return attributes;
    }

    public ArrayList<Classes> getClasses() {
        return classes;
    }

    public ArrayList<Entries> getEntries() {
        return entries;
    }

}
