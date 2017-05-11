package main;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;

import controller.Couple;
import databases.ATCSearch;
import databases.SiderConnexion;
import databases.StitchConnexion;

public class CStoMedicines {
	
	private static SiderConnexion sc = new SiderConnexion();
	private static StitchConnexion stc = new StitchConnexion();
	private static ATCSearch atc = new ATCSearch();
	
	
	public static Couple ClinicalSignToGooodMedecines(String clinicalSign) throws IOException, ParseException {
		
		String cui = sc.SearchMeddraCUIFromLABEL(clinicalSign);
		Couple medecine = new Couple();
				
		if (cui != null) {
			String stitch_compound_id = sc.SearchMeddraToMeddraAll(cui);
			
			if (stitch_compound_id != null) {
				String code_atc = stc.stitch_CpdID_to_codeATC(stitch_compound_id);
				
				if (!code_atc.equals("no match")) {
					medecine = atc.ATCSearchLabel(code_atc);
					
				} else {System.out.println("Sorry, no medicine matching your request !!!");}
			} else {System.out.println("Sorry, no medicine matching your request !!");}
		} else {System.out.println("Sorry, no medicine matching your request !");}
		
		return medecine;
	}
	
	
	public static void main(String[] args) throws IOException, ParseException {

		ClinicalSignToGooodMedecines("Acute abdominal");
		
	}
	
	
	
}
