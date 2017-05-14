package main;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.queryparser.classic.ParseException;

import controller.Couple;
import databases.ATCSearch;
import databases.SiderConnexion;
import databases.StitchConnexion;

public class CStoDrugs {
	
	private static SiderConnexion sc = new SiderConnexion();
	private static StitchConnexion stc = new StitchConnexion();
	private static ATCSearch atc = new ATCSearch();
	
	public static void main(String[] args) throws IOException, ParseException {

		System.out.println(ClinicalSignToBadMedecines("warts"));
	}
	
	public static ArrayList<Couple> ClinicalSignToBadMedecines(String clinicalSign) throws IOException, ParseException {
		
		ArrayList<Couple> first = meddraLabelTOatcLabel(clinicalSign);
		ArrayList<Couple> second = meddrasideeffectTOatclabel(clinicalSign);
		ArrayList<Couple> finalList = new ArrayList<Couple>();
	
		finalList.addAll(first);
		finalList.addAll(second);
		
		return finalList;
		
	}
	
	
	public static ArrayList<Couple> meddraLabelTOatcLabel(String clinicalSign) throws IOException, ParseException {
		ArrayList<Couple> finalList = new ArrayList<Couple>();
		
		String cui_meddra = sc.SearchMeddraCUIFromLABEL(clinicalSign);
		
		if (cui_meddra != null) {
			String stitch_compound_id1 = sc.SearchMeddraSE_cuiToCpdId1(cui_meddra);
			
			if (stitch_compound_id1 != null) {
				String code_atc = stc.stitch_CpdID_to_codeATC(stitch_compound_id1);
				
				if (!code_atc.equals("no match")) {
					ArrayList<Couple> intermediaire = atc.ATCSearchLabel(code_atc);
					finalList.addAll(intermediaire);
					
				} else {System.out.println("Sorry, no medicine matching your request !!!");}
			} else {System.out.println("Sorry, no medicine matching your request !!");}
		} else {System.out.println("Sorry, no medicine matching your request !");}
		
		System.out.println("ICI 6 " + finalList);
		return finalList;
		
	}
	
	
	public static ArrayList<Couple> meddrasideeffectTOatclabel(String clinicalSign) throws IOException, ParseException {
		
		 ArrayList<Couple> finalList = new ArrayList<Couple>();
		
		String stitch_compound_id1 = sc.meddraSE_sideEffect_ToCpdId1(clinicalSign);
				
			if (stitch_compound_id1 != null) {
				String code_atc = stc.stitch_CpdID_to_codeATC(stitch_compound_id1);
				
				if (!code_atc.equals("no match")) {
					ArrayList<Couple> intermediaire = atc.ATCSearchLabel(code_atc);
					finalList.addAll(intermediaire);
					

				} else {System.out.println("Sorry, no medicine matching your request !!");}
			} else {System.out.println("Sorry, no medicine matching your request !");}
		
		return finalList;
	}
	
	/**
	 * AND fonction 
	 * @param cs1
	 * @param cs2
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 * @throws org.json.simple.parser.ParseException
	 */
	public static ArrayList<Couple> ClinicalSignToBadMedecinesET(String cs1, String cs2) throws IOException, ParseException, org.json.simple.parser.ParseException{
		ArrayList<Couple> list1 = ClinicalSignToBadMedecines(cs1);
		ArrayList<Couple> list2 = ClinicalSignToBadMedecines(cs2);
		ArrayList<Couple> listFinal = new ArrayList<Couple>();
		
		for (int i=0; i<list1.size(); i++) {
			for (int j=0; j<list2.size(); j++) {
				
				if (list1.get(i).equalsDisease(list2.get(j))) {
					if (list1.get(i).equalsDataBase(list2.get(j))) {
						listFinal.add(list1.get(i));
					}
					else {
						Couple couple = new Couple(list1.get(i).getDisease(), list1.get(i).getDataBase() + " and " + list2.get(j).getDataBase());
						listFinal.add(couple);
					}
				}
				
				
			}
		}
		System.out.println(listFinal);
		return listFinal;
	}
	
	/**
	 * OR function
	 * @param cs1
	 * @param cs2
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 * @throws org.json.simple.parser.ParseException
	 */
	public static ArrayList<Couple> ClinicalSignToBadMedecinesOU(String cs1, String cs2) throws IOException, ParseException, org.json.simple.parser.ParseException {
		ArrayList<Couple> list1 = ClinicalSignToBadMedecines(cs1);
		ArrayList<Couple> list2 = ClinicalSignToBadMedecines(cs2);
		ArrayList<Couple> listFinal = new ArrayList<Couple>();
		listFinal.addAll(list1);
		listFinal.addAll(list2);
		return listFinal;
	}

}
