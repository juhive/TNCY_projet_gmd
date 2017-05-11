package databases;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class StitchConnexion {
	
/**
 * 
 * @param compound_id (from meddra_all_indication or meddra_all_se)
 * @return code ATC
 */
	public static String stitch_CpdID_to_codeATC(String compound_id) {

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
	                	System.out.println(uneLigne[0].toString()+"   "+compound_id);
	                	
	                	if (uneLigne[0].toString().equals(compound_id)){
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
