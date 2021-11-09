/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package candidateeliminate;

/**
 *
 * @author Hamed Khashehchi
 */
public class Classes {

    private static String Possitive;
    private static String Negative;
    private boolean isPositive;

    public Classes(String s) {
        this.isPositive = isPossitive(s);
    }

    public boolean isIsPositive() {
        return isPositive;
    }

    public static void setNegative(String Negative) {
        Classes.Negative = Negative;
    }

    public static void setPossitive(String Possitive) {
        Classes.Possitive = Possitive;
    }

    public boolean isPossitive(String s) {
        return Classes.Possitive.equalsIgnoreCase(s);
    }

    public boolean isNegative(String s) {
        return Classes.Negative.equalsIgnoreCase(s);
    }

    @Override
    public String toString() {
        String s = "";
        if (this.isPositive) {
            s = Classes.Possitive;
        } else {
            s = Classes.Negative;
        }
        return s; //To change body of generated methods, choose Tools | Templates.
    }
}
