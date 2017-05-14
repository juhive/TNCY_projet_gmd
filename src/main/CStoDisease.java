package main;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.queryparser.classic.ParseException;

import controller.Couple;
import databases.HPOConnexion;
import databases.HPOSearch;
import databases.OMIMIndexing;
import databases.OMIMSearch;
import databases.OrphaDataBase;

public class CStoDisease {
	
	private static OrphaDataBase orphadb = new OrphaDataBase();
	private static OMIMIndexing omimindex = new OMIMIndexing();
	private static OMIMSearch omimdb = new OMIMSearch();
	private static HPOConnexion hpocon = new HPOConnexion();
	private static HPOSearch hposearch = new HPOSearch();
	
	public CStoDisease() {}

	/**
	 * Return all disease associate to a clinical sign given
	 * @param clinicalSign
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 * @throws org.json.simple.parser.ParseException
	 */
public static ArrayList<Couple> ClinicalSignTosDisease(String clinicalSign) throws IOException, ParseException, org.json.simple.parser.ParseException {
		
		ArrayList<Couple> orphaList = new ArrayList<Couple>();
		ArrayList<Couple> omimList = new ArrayList<Couple>();
		ArrayList<Couple> hpoList = new ArrayList<Couple>();
		ArrayList<Couple> hpoList2 = new ArrayList<Couple>();
		ArrayList<Couple> finalList = new ArrayList<Couple>();
		
		//ORPHADATA
		orphaList = orphadb.CStoDiseases(clinicalSign);
		
		//OMIM
		File indexOmim = new File("indexes/omim/");
		if (!indexOmim.exists()) {
			omimindex.OMIMtxt();
		}
		omimList = omimdb.OMIMSearchDisease(clinicalSign);
		
		//HPO
		File indexHP = new File ("indexes/HPO/");
		if (!indexHP.exists()) {
			hpocon.HP_obo();
		}
		String idHP = hposearch.id_HPO_oboSearchid_HP(clinicalSign);
		String id_HP = hposearch.id_HPO_annotationSearchid_HP(clinicalSign);
		hpoList=hpocon.ToDiseaseLabel(idHP, 1);
		hpoList2=hpocon.ToDiseaseLabel(id_HP, 1);
		
		System.out.println(id_HP);
		finalList.addAll(orphaList);
		finalList.addAll(omimList);
		finalList.addAll(hpoList);
		finalList.addAll(hpoList2);
				
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
	public static ArrayList<Couple> ClinicalSignTosDiseaseET(String cs1, String cs2) throws IOException, ParseException, org.json.simple.parser.ParseException{
		ArrayList<Couple> list1 = ClinicalSignTosDisease(cs1);
		ArrayList<Couple> list2 = ClinicalSignTosDisease(cs2);
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
	public static ArrayList<Couple> ClinicalSignTosDiseaseOU(String cs1, String cs2) throws IOException, ParseException, org.json.simple.parser.ParseException {
		ArrayList<Couple> list1 = ClinicalSignTosDisease(cs1);
		ArrayList<Couple> list2 = ClinicalSignTosDisease(cs2);
		ArrayList<Couple> listFinal = new ArrayList<Couple>();
		listFinal.addAll(list1);
		listFinal.addAll(list2);
		//System.out.println(listFinal);
		return listFinal;
	}
	
	
	public static void main(String[] args) throws IOException, ParseException, org.json.simple.parser.ParseException {

		ClinicalSignTosDiseaseET("Abnormality of the clit", "skull/cranial anomalies");
		
		}
	
}
