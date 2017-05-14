 package main;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.queryparser.classic.ParseException;

import controller.Couple;
import databases.ATCSearch;
import databases.SiderConnexion;
import databases.StitchConnexion;

public class CStoMedicines {
	
	public static void main(String[] args) throws IOException, ParseException {

		System.out.println(ClinicalSignToGooodMedecines("pneumonia"));
		
	}
	
	/**
	 * clinical sign to medicines curing this clinical sign
	 * @param clinicalSign
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public static ArrayList<Couple> ClinicalSignToGooodMedecines(String clinicalSign) throws IOException, ParseException {
		
		ArrayList<Couple> first = meddralabelTOatclabel(clinicalSign);
		ArrayList<Couple> finalList = new ArrayList<Couple>();
		
		finalList.addAll(first);		
		
		return finalList;
	}
	
	/**
	 * Meddra Label to ATC Label
	 * @param clinicalSign
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public static ArrayList<Couple> meddralabelTOatclabel(String clinicalSign) throws IOException, ParseException {
		
		 ArrayList<Couple> finalList = new ArrayList<Couple>();
		
		String cui = SiderConnexion.SearchMeddraCUIFromLABEL(clinicalSign);
				
		if (cui != null) {
			
			ArrayList<String>  stitch_compound_id = SiderConnexion.SearchMeddraToMeddraAll(cui);
			
			if (stitch_compound_id != null) {
				
				  for (int i=0; i<stitch_compound_id.size(); i++) {
				  		String code_atc = StitchConnexion.stitch_CpdID_to_codeATC(stitch_compound_id.get(i));
				  		if (!code_atc.equals("no match")) {
				  		ArrayList<Couple> intermediaire = ATCSearch.ATCSearchLabel(code_atc);
				  		finalList.addAll(intermediaire);
				  		}
				  }
				 
			
			}
		}
		
		return finalList;
	}
	
	/**
	 * AND function 
	 * @param cs1
	 * @param cs2
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 * @throws org.json.simple.parser.ParseException
	 */
	public static ArrayList<Couple> ClinicalSignToGooodMedecinesET(String cs1, String cs2) throws IOException, ParseException, org.json.simple.parser.ParseException{
		ArrayList<Couple> list1 = ClinicalSignToGooodMedecines(cs1);
		ArrayList<Couple> list2 = ClinicalSignToGooodMedecines(cs2);
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
	public static ArrayList<Couple> ClinicalSignToGooodMedecinesOU(String cs1, String cs2) throws IOException, ParseException, org.json.simple.parser.ParseException {
		ArrayList<Couple> list1 = ClinicalSignToGooodMedecines(cs1);
		ArrayList<Couple> list2 = ClinicalSignToGooodMedecines(cs2);
		ArrayList<Couple> listFinal = new ArrayList<Couple>();
		
		for (int i=0; i<list1.size(); i++) {
			for (int j=0; j<list2.size(); j++) {
				
				if (list1.get(i).equalsDisease(list2.get(j))) {
					if (list1.get(i).equalsDataBase(list2.get(j))) {listFinal.add(list1.get(i));}
					else {
						Couple couple = new Couple(list1.get(i).getDisease(), list1.get(i).getDataBase() + " or " + list2.get(j).getDataBase());
						listFinal.add(couple);}
				}
			}
		}
		
		return listFinal;
	}
	
}
