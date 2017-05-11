package main;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;

import controller.Couple;
import databases.ATCSearch;
import databases.SiderConnexion;
import databases.StitchConnexion;

public class CStoDrugs {
	
	private static SiderConnexion sc = new SiderConnexion();
	private static StitchConnexion stc = new StitchConnexion();
	private static ATCSearch atc = new ATCSearch();
	
	public static Couple ClinicalSignToBadMedecines(String clinicalSign) throws IOException, ParseException {
		
		String cui_meddra = sc.SearchMeddraCUIFromLABEL(clinicalSign);
		Couple medecine = new Couple();
		
		if (cui_meddra != null) {
			String stitch_compound_id1 = sc.SearchMeddraSE_cuiToCpdId1(cui_meddra);
			
			if (stitch_compound_id1 != null) {
				String code_atc = stc.stitch_CpdID_to_codeATC(stitch_compound_id1);
				
				if (!code_atc.equals("no match")) {
					medecine = atc.ATCSearchLabel(code_atc);
					
				} else {System.out.println("Sorry, no medicine matching your request !!!");}
			} else {System.out.println("Sorry, no medicine matching your request !!");}
		} else {System.out.println("Sorry, no medicine matching your request !");}
		
		return medecine;
	}
	
	
	public static void main(String[] args) throws IOException, ParseException {

		ClinicalSignToBadMedecines("Gut pain");
		
	}

}
