 package main;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.queryparser.classic.ParseException;

import controller.Couple;
import databases.ATCSearch;
import databases.SiderConnexion;
import databases.StitchConnexion;

public class CStoMedicines {
	
	private static SiderConnexion sc = new SiderConnexion();
	private static StitchConnexion stc = new StitchConnexion();
	private static ATCSearch atc = new ATCSearch();
	
	
	public static ArrayList<Couple> ClinicalSignToGooodMedecines(String clinicalSign) throws IOException, ParseException {
		
		ArrayList<Couple> first = meddralabelTOatclabel(clinicalSign);
		//ArrayList<Couple> second = meddrasideeffectTOatclabel(clinicalSign);
		ArrayList<Couple> finalList = new ArrayList<Couple>();
		
		finalList.addAll(first);
		//finalList.addAll(second);
		
		
		return finalList;
	}
	
	public static ArrayList<Couple> meddralabelTOatclabel(String clinicalSign) throws IOException, ParseException {
		
		 ArrayList<Couple> finalList = new ArrayList<Couple>();
		
		String cui = sc.SearchMeddraCUIFromLABEL(clinicalSign);
				
		if (cui != null) {
			
			ArrayList<String>  stitch_compound_id = sc.SearchMeddraToMeddraAll(cui);
			
			if (stitch_compound_id != null) {
				
				  for (int i=0; i<stitch_compound_id.size(); i++) {
				  		String code_atc = stc.stitch_CpdID_to_codeATC(stitch_compound_id.get(i));
				  		if (!code_atc.equals("no match")) {
				  		finalList = atc.ATCSearchLabel(code_atc);
				  		}
				  }
				 
			
			} else {System.out.println("Sorry, no medicine matching your request !!");}
		} else {System.out.println("Sorry, no medicine matching your request !");}
		
		return finalList;
	}
	
	
	
	public static void main(String[] args) throws IOException, ParseException {

		//ClinicalSignToGooodMedecines("Gut pain");
		System.out.println(meddralabelTOatclabel("Pneumonia"));
		
	}
	
	
	
}
