package main;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.queryparser.classic.ParseException;

import controller.Couple;
import databases.OMIMIndexing;
import databases.OMIMSearch;
import databases.OrphaDataBase;

public class CStoDisease {
	
	private static OrphaDataBase orphadb = new OrphaDataBase();
	private static OMIMIndexing omimindex = new OMIMIndexing();
	private static OMIMSearch omimdb = new OMIMSearch();
	
	
	
public static ArrayList<Couple> ClinicalSignTosDisease(String clinicalSign) throws IOException, ParseException, org.json.simple.parser.ParseException {
		
		ArrayList<Couple> orphaList = new ArrayList<Couple>();
		ArrayList<Couple> omimList = new ArrayList<Couple>();
		ArrayList<Couple> finalList = new ArrayList<Couple>();
		
		//ORPHADATA
		orphaList = orphadb.CStoDiseases(clinicalSign);
		//OMIM
		//omimindex.OMIMtxt();
		omimList = omimdb.OMIMSearchDisease(clinicalSign);
		
		finalList.addAll(orphaList);
		finalList.addAll(omimList);
		
		System.out.println(finalList);
		return finalList;
	}
	
	
	public static void main(String[] args) throws IOException, ParseException, org.json.simple.parser.ParseException {

		ClinicalSignTosDisease("lack of visual");
		}
	
}
