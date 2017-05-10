package databases;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class StitchConnexion {
	
	public static void main(String[] args) {
		stitch();
	}
	
	public static void stitch() {

	        String csvFile = "resourcesFiles/chemical.sources.v5.0.EXTRAIT.tsv";
	        String line = "";
	        String cvsSplitBy = "\t";

	        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	        	
	        	line = br.readLine();
	            while ((line = br.readLine()) != null) {
	            	if (!line.startsWith("#")) {
	                	String[] uneLigne = line.split(cvsSplitBy);
	                	System.out.println(uneLigne[0] + " " + uneLigne[1] + " " + uneLigne[2] + " " + uneLigne[3]);
	            	}

	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }

	    }
	
	
	

}
