/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package candidateeliminate;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Hamed Khashehchi
 */
public class Attributes {

    private String name;
    private int domainLength;
    private HashSet<String> domain;

    public Attributes(String name) {
        this.name = name;
        this.domainLength = 0;
        domain = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public HashSet<String> getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain.add(domain);
        this.domainLength = this.domain.size();
    }

    @Override
    public String toString() {
        String s="{";
        s+=this.name+"->";
        s+=this.domain+"}";
        return s; //To change body of generated methods, choose Tools | Templates.
    }
    
}
