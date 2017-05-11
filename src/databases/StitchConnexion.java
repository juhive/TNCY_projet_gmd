package databases;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class StitchConnexion {
	
	public static void main(String[] args) {
		String ATC = stitch("CIDm00010517");
		System.out.println(ATC);
	}
	
	public static String stitch(String coumpound_id) {

	        String csvFile = "resourcesFiles/Stitch/chemical.sources.v5.0.EXTRAIT.tsv";
	        String line = "";
	        String cvsSplitBy = "\t";
	        String codeATC = "no match";

	        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	        	
	        	line = br.readLine();
	            while ((line = br.readLine()) != null) {
	            	if (!line.startsWith("#")) {
	                	String[] uneLigne = line.split(cvsSplitBy);
	                	//System.out.println(uneLigne[0] + " " + uneLigne[1] + " " + uneLigne[2] + " " +uneLigne[3]);
	                	System.out.println(uneLigne[0].toString()+"   "+coumpound_id);
	                	
	                	if (uneLigne[0].toString().equals(coumpound_id)){
	                		codeATC =uneLigne[3];
	                	}
	            	}

	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return codeATC;

	    }
	
	
	

}
