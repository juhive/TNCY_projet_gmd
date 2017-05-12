package databases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import controller.Couple;

public class HPOConnexion {

	public static void main(String[] args) {

		//HPO_annotation();
		//HP_obo();
		System.out.println(ToDiseaseLabel("HP:0000056", 1));
	}

	
	
	public static void HPO_annotation(){
		/**
		 * First, you need to import sqlite-jdbc.jar from GoogleDrive
		 * Then you need to create a directory "HPO" 
		 * Finally add to the directory the file hpo_annotations.sqlite
		 */
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:resourcesFiles/HPO/hpo_annotations.sqlite");
			String myQuery = "SELECT sign_id, disease_label, disease_db_and_id FROM phenotype_annotation WHERE sign_id = 'HP:0000280' " ;
			Statement st = conn.createStatement();
			ResultSet res = st.executeQuery(myQuery);

			while (res.next()) {
				String disease_label= res.getString("disease_label");
				String sign_id= res.getString("sign_id");
				String disease_db_and_id= res.getString("disease_db_and_id");
				System.out.println("");
				System.out.println(disease_label + " ; " + sign_id + " ; " + disease_db_and_id  );
			}
			res.close();
			st.close();
			conn.close();
		}
		catch (ClassNotFoundException e){
			System.err.println("Could not load JDBC driver");
			System.out.println("Esception: " + e);
			e.printStackTrace();

		}
		catch (SQLException ex) {
			System.err.println("SQLException information");
			while(ex!=null){
				System.err.println("" + ex.getMessage());
				System.err.println("" + ex.getSQLState());
				System.err.println("" + ex.getErrorCode());
				ex.printStackTrace();
				ex = ex.getNextException();
			}
		}

	}

	public static void HP_obo() {

		/**
		 * You need to add to resourcesFiles hp.obo or in my case hp_extrait.obo (from GoogleDrive)
		 */

		String indexPath = "indexes/HPO/";
		String docsPath = "resourcesFiles/HPO/hp.obo";

		final File doc = new File(docsPath);

		if (!doc.exists()) {
			System.out.println("File doesn't exist or isn't readable, check the path");
			System.exit(1);
		}

		Date start = new Date();

		try {
			System.out.println("Indexation in directory '" + indexPath + "' ...");

			Directory dir = FSDirectory.open(Paths.get(indexPath));
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			iwc.setOpenMode(OpenMode.CREATE);

			IndexWriter writer = new IndexWriter(dir, iwc);
			
			indexDoc(writer, doc);
			writer.close();

			Date end = new Date();
			System.out.println("en " + (end.getTime() - start.getTime()) + " millisecondes" +
					"\nIndexation ended");
		} catch (IOException e) {
			System.out.println(" caught a " + e.getClass() +
					"\n with message: " + e.getMessage());
		}
	}

	static void indexDoc(IndexWriter writer, File file) throws IOException {
		int elementsAjoute = 0;
		if(file.canRead() && !file.isDirectory()){
			
			try{
				InputStream ips = new FileInputStream(file);
				InputStreamReader ipsr = new InputStreamReader(ips);
				BufferedReader br = new BufferedReader(ipsr);
				String ligne;
				
				//enable us to start parsing the file at the first [Term] line
				for(int i=1 ; i<29 ; i++){
					ligne=br.readLine();
				} 
				
				Document doc =null;

				while((ligne=br.readLine())!=null){
					if(ligne.startsWith("[Term]")){
						//new Doc is created because new medicine
						System.out.println("-NEW------");
						doc = new Document();
						ligne=br.readLine();
						elementsAjoute++;
					}
					if(ligne.startsWith("id:")){
						String id_HP = ligne.substring(7);
						System.out.println("IDHP ="+id_HP);
						ligne=br.readLine();
						doc.add(new TextField("id_HP", id_HP, Field.Store.YES)); //Analyse et Indexe
					}
					if(ligne.startsWith("name:")){
						String name = ligne.substring(6);
						System.out.println("name ="+name);
						ligne=br.readLine();
						doc.add(new TextField("name", name, Field.Store.YES)); //Analyse et Indexe
					}
					if(ligne.startsWith("synonym:")){
						String synonym = ligne.substring(9);
						int length = synonym.length();
						int j = synonym.length();
						for(int i=0; i<length; i++ ) {
							if (synonym.charAt(i) == '"') {
								j = i;
							}
						}
						synonym = synonym.substring(1, j);
						System.out.println("Syn = "+synonym);
						ligne=br.readLine();
						doc.add(new StringField("synonym", synonym, Field.Store.YES)); 
					}
					writer.addDocument(doc);
				}br.close();

			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		System.out.println(elementsAjoute+" elements add to the index");
	}

	
	/** 1 = sign_id
	 * 2 = disease_db_and_id 
	 **/
	 public static ArrayList<Couple> ToDiseaseLabel(String hpo_anno_sign_id, int what){
	 	
	 	String diseaseLabel = null;
	 	String diseaseDb = null;
	 	
		Pattern pattern = Pattern.compile("^#[0-9]|^%[0-9]");
		Pattern pattern2 = Pattern.compile("^[0-9]");
	 		
	 	ArrayList<Couple> ListDiseaseLabel = new ArrayList<Couple>();
	 	    
	 	    
	 	try {
	 		Class.forName("org.sqlite.JDBC");
	 		Connection conn = DriverManager.getConnection("jdbc:sqlite:resourcesFiles/HPO/hpo_annotations.sqlite");
	 			
	 		String aQuery= "";
	 		if(what == 1) {
	 		aQuery = "SELECT disease_label, disease_db FROM phenotype_annotation WHERE sign_id = ?";
	 		}
	 		else if(what == 2) {
	 			aQuery = "SELECT disease_label, disease_db FROM phenotype_annotation WHERE disease_db_and_id = ?";
	 			}
	 		PreparedStatement prep1 = conn.prepareStatement(aQuery);
	 		prep1.setString(1,hpo_anno_sign_id);
	 		
	 			
	 			//exécution de la requête
	 		ResultSet res = prep1.executeQuery();
	 		
	 		while(res.next()){
	 			diseaseLabel = res.getString("disease_label");
	 			diseaseDb = res.getString("disease_db");
	 			if (diseaseDb.equals("OMIM")) {
	 				
	 				Matcher matcher = pattern.matcher(diseaseLabel);
	 				if (matcher.find()) {
	 					diseaseLabel = diseaseLabel.substring(8);
	 					int length = diseaseLabel.length();
	 					int i = 0;
	 					while(diseaseLabel.charAt(i) != ';' && i < length-1) {
	 						i++;
	 						}
	 					diseaseLabel = diseaseLabel.substring(0, i);
	 				}
	 				
	 				
	 				Matcher matcher2 = pattern2.matcher(diseaseLabel);
	 				if (matcher2.find()) {
	 					diseaseLabel = diseaseLabel.substring(7);
	 					int length = diseaseLabel.length();
	 					int i = 0;
	 					while(diseaseLabel.charAt(i) != ';' && i < length-1) {
	 						i++;
	 						}
	 					diseaseLabel = diseaseLabel.substring(0, i);
	 				}
	 				diseaseLabel = diseaseLabel.toLowerCase();
	 				Couple couple = new Couple(diseaseLabel, "OMIM via HPO");
	 				ListDiseaseLabel.add(couple);
	 			}
	 			else if (diseaseDb.equals("ORPHA")) {
	 				diseaseLabel = diseaseLabel.toLowerCase();
	 				Couple couple = new Couple(diseaseLabel, "OrphaData via HPO");
	 				ListDiseaseLabel.add(couple);
	 			}
	 			else if (diseaseDb.equals("DECIPHER")) {
	 				diseaseLabel = diseaseLabel.toLowerCase();
	 				Couple couple = new Couple(diseaseLabel, "Decipher via HPO");
	 				ListDiseaseLabel.add(couple);
	 			}				
	 			
	 		}
	 		res.close();
	 		conn.close();
	 	}
	 	catch (ClassNotFoundException e){
	 		System.err.println("Could not load JDBC driver");
	 		System.out.println("Exception: " + e);
	 		e.printStackTrace();
	 	}
	 	catch (SQLException ex) {
	 		System.err.println("SQL Exception information");
	 		while(ex!=null){
	 			System.err.println("" + ex.getMessage());
	 			System.err.println("" + ex.getSQLState());
	 			System.err.println("" + ex.getErrorCode());
	 			ex.printStackTrace();
	 			ex = ex.getNextException();
	 			}
	 	}
	 	
	 	if(what == 2) {
	 		ArrayList<Couple> ListDiseaseLabel2 = new ArrayList<Couple>();
	 		Couple oneTime = ListDiseaseLabel.get(0);
	 		ListDiseaseLabel2.add(oneTime);
	 		ListDiseaseLabel = ListDiseaseLabel2;
	 	}
	 	
	 	System.out.println(ListDiseaseLabel.toString());
		return ListDiseaseLabel;
	 
	 }
	
}
