/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package candidateeliminate;

import java.util.ArrayList;

/**
 *
 * @author Hamed Khashehchi
 */
public class CandidateEliminate {

    private ArrayList<Entries> dataTrain;
    private ArrayList<ArrayList<String>> specificHistory;
    private ArrayList<ArrayList<ArrayList<String>>> generalHistory;
    private ReadCSV rcv;

    public ArrayList<ArrayList<ArrayList<String>>> getGeneralHistory() {
        return generalHistory;
    }

    public ArrayList<ArrayList<String>> getSpecificHistory() {
        return specificHistory;
    }

    public ArrayList<Entries> getDataTrain() {
        return dataTrain;
    }

    public CandidateEliminate(String path, String Positive, String Negative) {
        this.rcv = new ReadCSV(path, Positive, Negative);
        this.dataTrain = rcv.getEntries();
        this.specificHistory = new ArrayList<>();
        this.generalHistory = new ArrayList<>();
        ArrayList<String> s = new ArrayList<>();
        ArrayList<String> g = new ArrayList<>();
        rcv.getAttributes().stream().map((_item) -> {
            s.add("0");
            return _item;
        }).forEachOrdered((_item) -> {
            g.add("?");
        });
        ArrayList<ArrayList<String>> g0 = new ArrayList<>();
        g0.add(g);
        this.specificHistory.add(s);
        this.generalHistory.add(g0);
        System.out.println("General = " + generalHistory);
        System.out.println("Specific = " + specificHistory);
    }

    public void train() {
        System.out.println("********start training********\n");
        for (int i = 0; i < dataTrain.size(); i++) {
            Entries get = dataTrain.get(i);
            System.out.println("get = " + get);
            ArrayList<String> latestSpecificSet = specificHistory.get(specificHistory.size() - 1);
            System.out.println("latestSpecificSet = " + latestSpecificSet);
            ArrayList<ArrayList<String>> latestGeneralSet = generalHistory.get(generalHistory.size() - 1);
            System.out.println("latestGenetalSet = " + latestGeneralSet);
            if (get.getClasses().isIsPositive()) {
                System.out.println("positive example");
                System.out.println("hypoThesis = " + latestSpecificSet);
                for (int j = 0; j < latestGeneralSet.size(); j++) {
                    if(!consistency(latestGeneralSet.get(j), get)){
                        System.out.println("remove!");
                        latestGeneralSet.remove(j);
                        j--;
                    }
                }
                ArrayList<String> moreGeneral = changeMyHypothesis(latestSpecificSet, get);
                System.out.println("moreGeneral = " + moreGeneral);
                specificHistory.add(moreGeneral);
                System.out.println("specificHistory = " + specificHistory);
            } else {
                System.out.println("negative example");
                for (int j = 0; j < latestGeneralSet.size(); j++) {
                    ArrayList<String> hypoThesis = latestGeneralSet.get(j);
                    System.out.println("hypoThesis = " + hypoThesis);
                    ArrayList<ArrayList<String>> moreSpecific = IdontWanna(hypoThesis, get);
                    
                    System.out.println("moreSpecific = " + moreSpecific);
                }
                //should add specificset
                generalHistory.add(latestGeneralSet);
                System.out.println("generalHistory = " + generalHistory);
            }
            System.out.println("========================\n======================\n");
        }
    }

    @Override
    public String toString() {
        return this.dataTrain.toString(); //To change body of generated methods, choose Tools | Templates.
    }

    private ArrayList<String> changeMyHypothesis(ArrayList<String> hypoThesis, Entries example) {
        System.out.println("example = " + example);
        System.out.println("hypoThesis = " + hypoThesis);
        ArrayList<String> moreGeneral = new ArrayList<>();
        for (int i = 0; i < hypoThesis.size(); i++) {
            System.out.println("hypothesis on attributes = " + hypoThesis.get(i));
            if (hypoThesis.get(i).equals("?")) {
                moreGeneral.add("?");
            } else if (hypoThesis.get(i).equals("0")) {
                moreGeneral.add(example.getValues().get(i));
            } else if (hypoThesis.get(i).equals(example.getValues().get(i))) {
                moreGeneral.add(example.getValues().get(i));
            } else {
                moreGeneral.add("?");
            }
        }
        return moreGeneral;
    }

    private ArrayList<ArrayList<String>> IdontWanna(ArrayList<String> hypoThesis, Entries example) {
        System.out.println("example = " + example);
        System.out.println("hypoThesis = " + hypoThesis);
        ArrayList<ArrayList<String>> nextSet = new ArrayList<>();
        for (int i = 0; i < hypoThesis.size(); i++) {
            System.out.println("hypothesis on attributes = " + hypoThesis.get(i));
            if (hypoThesis.get(i).equals("?")) {
                for (String s : example.getAttributes().get(i).getDomain()) {
                    System.out.println("s = " + s);
                    ArrayList<String> makeOne = new ArrayList<>();
                    makeOne.addAll(hypoThesis);
                    if (!example.getValues().get(i).equals(s)) {
                        makeOne.set(i, s);
                        System.out.println("makeOne = " + makeOne);
                        nextSet.add(makeOne);
                    }
                }
            } else if (hypoThesis.get(i).equals(example.getValues().get(i))) {
                //do nothing!
            } else {
                nextSet.add(hypoThesis);
            }
        }
        System.out.println("nextSet = " + nextSet);
        return nextSet;
    }

    private boolean consistency(ArrayList<String> hypoThesis, Entries example) {
        int i=0;
        for(String s:hypoThesis){
            if(s.equals(example.getValues().get(i++))||s.equals("?")){
                //do nothing
            }else{
                return false;
            }
        }
        return true;
    }

}
