package databases;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.jar.JarException;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;

import controller.Couple;

/**
 * 
 * @author celesteallardi
 *importer les packages suivnant :
 *json-simple.jar
 *org.apache.commons.io.jar
 *qui sont sur le drive
 */

	public class OrphaDataBase {

		public static void main(String[] args) throws JarException, MalformedURLException, IOException, ParseException {
			//CStoDiseases("skull/cranial anoamlies");
			//Skull/cranial anomalies
			//Conjunctival injection/chemosis/red eye
		}

	   
	     	
	     	
	    public static ArrayList<Couple> CStoDiseases (String clinicalSign) throws MalformedURLException, IOException, ParseException, JarException{
	    	ArrayList<Couple> finalListDisease= new ArrayList<Couple>();
	    	String urlString = "http://couchdb.telecomnancy.univ-lorraine.fr/orphadatabase/_design/clinicalsigns/_view/GetClinicalSignToDiseaseClassification";
	    	URL url = new URL(urlString);
	    	String genreJson = IOUtils.toString(url.openStream());
			JSONObject genreJsonObject = (JSONObject) JSONValue.parseWithException(genreJson);
			
			JSONArray genreArray = (JSONArray) genreJsonObject.get("rows");

			for(int i=0;i<genreArray.size();i++){
				JSONObject row= (JSONObject) genreArray.get(i);
				JSONObject value =  (JSONObject) row.get("value");
				
				//get Clinical Sign Name
				String CSname = (String) value.get("Name");
				
				
				//clinicalSign.toLowerCase().equals(CSname.toLowerCase())
				if(CSname.toLowerCase().contains(clinicalSign.toLowerCase())) {
					
					if (value.get("ClassificationDisease") instanceof JSONArray) {
						JSONArray classificationDisease = (JSONArray) value.get("ClassificationDisease");
						
						for(int j=0; j<classificationDisease.size() ; j++) {
							JSONObject rowDisease = (JSONObject) classificationDisease.get(j);
							String nameDisease = (String) rowDisease.get("Name");
							Couple couple = new Couple(nameDisease, "OrphaData");
							boolean flag =false;
							
							for (int k=0; k<finalListDisease.size(); k++ ) {
								if (finalListDisease.get(k).equals(couple)) {
									flag = true;
									break;
								}
							}
							if (!flag) {
								finalListDisease.add(couple);
								}
							
							
						}
						
					}
					else {
						JSONObject classificationDisease = (JSONObject) value.get("ClassificationDisease");
						JSONObject classif = (JSONObject) classificationDisease.get("Classif");
						String nameDisease = (String) classif.get("Name");
						Couple couple = new Couple(nameDisease, "OrphaData");
						finalListDisease.add(couple);
					}
				
				}
				
					
			}
			return finalListDisease;

	    }
	      
}