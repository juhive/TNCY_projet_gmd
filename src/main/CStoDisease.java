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
		
		
		finalList.addAll(orphaList);
		finalList.addAll(omimList);
		finalList.addAll(hpoList);
		finalList.addAll(hpoList2);
				
		return finalList;
	}
	
	
	public static void main(String[] args) throws IOException, ParseException, org.json.simple.parser.ParseException {

		//ClinicalSignTosDisease("Abnormality of the clit");
		System.out.println(hposearch.id_HPO_annotationSearchid_HP("Abnormality of the clit"));
		}
	
}
