import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("E:\\Code\\Java\\JavaApplication8\\src\\Data\\trainingData.txt"));
		String s;
		ArrayList<String[]> trainingData = new ArrayList<>(); 
		while((s=br.readLine())!=null){
			StringTokenizer st = new StringTokenizer(s,",");
			int n = st.countTokens();
			String[] trainingInstance = new String[n];
			for(int i = 0; i < n; i++){
				trainingInstance[i] = st.nextToken();
			}
			trainingData.add(trainingInstance);
		}
		br.close();
		
		int noOfAttributes=trainingData.get(0).length-1;//last is class no
		String[][] totalAttributes = new String[noOfAttributes][];
		for(int i = 0; i < totalAttributes.length; i++){
			if(i==12){//legs has 6 values 0,2,4,5,6,8
				totalAttributes[i] = new String[6];
				totalAttributes[i][0] = "0";
				totalAttributes[i][1] = "2";
				totalAttributes[i][2] = "4";
				totalAttributes[i][3] = "5";
				totalAttributes[i][4] = "6";
				totalAttributes[i][5] = "8";
			}else{
				totalAttributes[i] = new String[2];
				totalAttributes[i][0] = "0";
				totalAttributes[i][1] = "1";
			}
		}
		
		//Find concept hypothesis for class=1 to class=7
		//NOTE: if you have only yes/no in classification than change i<=7 to i<=1 and replace yes by 1 and no by 0
		for(int i = 1; i <= 7; i++){
			System.out.println("\n*****For Class = "+i+"*****");
			Collections.sort(trainingData, new Comp(i)); // sort such that first all positive examples come then all negative
			
			TargetConcept tc = new TargetConcept(i, totalAttributes);
			boolean conceptCanBeLearned = true;
			for(String[] trainingInstance : trainingData){
                            System.out.println("trainingInstance = " + Arrays.toString(trainingInstance));
				conceptCanBeLearned = tc.addTrainingInstance(trainingInstance);
				if(!conceptCanBeLearned){
					System.out.println("Concept cannot be learned");
					break;
				}
			}
			if(conceptCanBeLearned){
				tc.printSpecificBoundary();
				tc.printGenericBoundary();
			}
		}
	}
}

class Comp implements Comparator<String[]>{
	int currentPositiveClassification;
	public Comp(int i){currentPositiveClassification = i;}
	public int compare(String[] s1, String[] s2){
		if(Integer.parseInt(s1[s1.length-1])==currentPositiveClassification)	return Integer.compare(1, 2);
		if(Integer.parseInt(s2[s2.length-1])==currentPositiveClassification)	return Integer.compare(2, 1);
		return Integer.compare(1, 1);
	}
}
