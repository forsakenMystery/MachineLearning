/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package candidateeliminate;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Hamed Khashehchi
 */
public class Entries {

    private ArrayList<Attributes> attributes;
    private Classes classes;
    private ArrayList<String> values;

    public Entries(ArrayList<Attributes> attributes, Classes classes, String... values) {
        this.attributes = attributes;
        this.classes = classes;
        this.values = new ArrayList<>();
        this.values.addAll(Arrays.asList(values));
    }

    public Classes getClasses() {
        return classes;
    }

    public ArrayList<Attributes> getAttributes() {
        return attributes;
    }

    public ArrayList<String> getValues() {
        return values;
    }

    @Override
    public String toString() {
        String s = "{";
        for (int i = 0; i < this.attributes.size(); i++) {
            s+=this.attributes.get(i).getName()+"->";
            s+=this.values.get(i)+", ";
        }
        s+=" classes->"+this.classes+"}";
        return s; //To change body of generated methods, choose Tools | Templates.
    }

}
