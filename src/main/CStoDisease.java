package main;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.queryparser.classic.ParseException;

import controller.Couple;
import databases.OrphaDataBase;

public class CStoDisease {
	
	private static OrphaDataBase odb = new OrphaDataBase();
	
	
	
public static ArrayList<Couple> ClinicalSignTosDisease(String clinicalSign) throws IOException, ParseException, org.json.simple.parser.ParseException {
		
		ArrayList<Couple> orphaList = new ArrayList<Couple>();
		
		ArrayList<Couple> finalList = new ArrayList<Couple>();
		
		//ORPHADATA
		orphaList = odb.CStoDiseases(clinicalSign);
		
		
		return finalList;
	}
	
	
	public static void main(String[] args) throws IOException, ParseException, org.json.simple.parser.ParseException {

		ClinicalSignTosDisease("Gut pain");
		}
	
}
