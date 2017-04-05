package databases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer; //il faut importer dans referenced librairies les bonnes lib
import org.analysis.common.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


/* Il est important que chacun créé chez lui en local au même niveau que src un dossier
 * resourcesFiles. Dans ce dossier on créé également le dossier omim dans lequel se trouve omim.txt
 * et omim_onto.csv
 */
public class OMIMIndexing {

	private OMIMIndexing(){}

	public static void main(String[] args) {
		String indexPath = "index/";
		String docsPath = "src/Files/drugbank.txt";

		final File doc = new File(docsPath);
		
		if (!doc.exists()) {
			System.out.println("Le fichier n'existe pas ou n'est pas lisible, veuillez vérifier le chemin d'accès");
			System.exit(1);
		}

		Date start = new Date();

		try {
			System.out.println("Indexation dans le répertoire '" + indexPath + "' ...");

			Directory dir = FSDirectory.open(Paths.get(indexPath));
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);

			iwc.setOpenMode(OpenMode.CREATE);

			IndexWriter writer = new IndexWriter(dir, iwc);
			indexDoc(writer, doc);

			writer.close();

			Date end = new Date();
			System.out.println("en " + (end.getTime() - start.getTime()) + " millisecondes" +
					"\nIndexation terminée");
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
					if(ligne.startsWith("#BEGIN_DRUGCARD")){
						//creation d'un nouveau doc car nouveau médicament
						doc = new Document();
					}
					if(ligne.startsWith("# Generic_Name")){
						String name=br.readLine();
						//System.out.println(name+"\n");
						//creation d'un champ et ajout dans le doc
						doc.add(new TextField("name", name, Field.Store.YES)); //Analyse et Indexe
					}
					if(ligne.startsWith("# Synonyms")){
						String synonym=br.readLine();
						//System.out.println(name+"\n");
						//creation d'un champ et ajout dans le doc
						doc.add(new StringField("synonym", synonym, Field.Store.YES)); //Indexe
					}
					if(ligne.startsWith("# Brand_Names")){
						String Brand_Names=br.readLine();
						//System.out.println(name+"\n");
						//creation d'un champ et ajout dans le doc
						doc.add(new StringField("brand_names", Brand_Names, Field.Store.YES));
					}
					if(ligne.startsWith("# Description")){
						String Description=br.readLine();
						//System.out.println(name+"\n");
						//creation d'un champ et ajout dans le doc
						doc.add(new StringField("description", Description, Field.Store.YES));
					}
					if(ligne.startsWith("# Indication")){
						String Indication=br.readLine();
						//System.out.println(name+"\n");
						//creation d'un champ et ajout dans le doc
						doc.add(new StringField("indication", Indication, Field.Store.YES));
					}
					if(ligne.startsWith("# Pharmacology")){
						String Pharmacology=br.readLine();
						//System.out.println(name+"\n");
						//creation d'un champ et ajout dans le doc
						doc.add(new StringField("pharmacology", Pharmacology, Field.Store.YES));
					}
					if(ligne.startsWith("# Drug_Interactions")){
						String Drug_Interactions=br.readLine();
						//System.out.println(name+"\n");
						//creation d'un champ et ajout dans le doc
						doc.add(new StringField("drug_interactions", Drug_Interactions, Field.Store.YES));
					}
					if(ligne.startsWith("#END_DRUGCARD")){
						writer.addDocument(doc);
						elementsAjoute++;
					}
				}
				
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		System.out.println(elementsAjoute+" éléments ajoutés à l'index");
	}

}

