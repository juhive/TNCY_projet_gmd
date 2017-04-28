package databases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Date;

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

public class HPOConnexion {
	
	public static void main(String[] args) {
		
		//HPO_annotation();
		HP_obo();
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
		
		String indexPath = "indexes/HPO";
		String docsPath = "resourcesFiles/HPO/hp_extrait.obo";

		final File doc = new File(docsPath);
		
		if (!doc.exists()) {
			System.out.println("File doesn't exist or isn't readable, check the path");
			System.exit(1);
		}

		Date start = new Date();

		try {
			System.out.println("Indexation in directory" + indexPath + "' ...");

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
				Document doc =null;
				
				while((ligne=br.readLine())!=null){
					if(ligne.startsWith("[Term]")){
						//new doc is created beacause new medicine
						doc = new Document();
					}
					if(ligne.startsWith("id:")){
							String id_HP = ligne.substring(7);
							System.out.println(id_HP+"\n");
							ligne=br.readLine();
						doc.add(new TextField("id_HP : ", id_HP, Field.Store.YES)); //Analyse et Indexe
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
							System.out.println(synonym+"\n");
							ligne=br.readLine();
						doc.add(new StringField("synonym", synonym, Field.Store.YES)); 
					}
					
				}
				
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		System.out.println(elementsAjoute+" éléments ajoutés à l'index");
	}
	
}
