package databases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
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

import com.opencsv.CSVReader;


public class OMIMIndexing {

	private OMIMIndexing(){}
	
	/**
	 * You need to import opencsv-3.9.jar from GoogleDrive
	 * Then in resourcesFiles/omim check if it contains omim_onto.csv and omim.txt
	 * Each file have is own static method
	 */

	public static void main(String[] args){
		//OMIMtxt();
		OMIMcsv();
	}

	public static void OMIMcsv() {
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader("resourcesFiles/omim/omim_onto.csv"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String [] nextLine;
		try {
			while ((nextLine = reader.readNext()) != null) {
				// nextLine[] is an array of values from the line
				System.out.println("-NEW----------------");
				System.out.println("Preferred label : "+nextLine[1]+"\nSynonym : "+nextLine[2]+"\nCUI : "+nextLine[5]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


	public static void OMIMtxt() {
		String indexPath = "indexes/omim/";
		String docsPath = "resourcesFiles/omim/omim.txt";

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
					"\nIndexation done");
		} catch (IOException e) {
			System.out.println(" CAUGHT A " + e.getClass() +
					"\n WITH MESSAGE : " + e.getMessage());
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
					if(ligne.startsWith("*RECORD*")){
						//creation of a new Doc because it's a new disease
						doc = new Document();
						elementsAjoute++;
						System.out.println("-NEW----------------");
					}
					if(ligne.startsWith("*FIELD* NO")){
						String no=br.readLine();
						System.out.println("no : "+no);
						//creation of field "no" and adding it in the Doc
						doc.add(new TextField("no", no, Field.Store.YES)); //analyse and index
						//doc.add(new StringField("no", no, Field.Store.YES)); //only index
					}
					if(ligne.startsWith("*FIELD* TI")){
						String ti=br.readLine();
						System.out.println("ti : "+ti);
						//creation of field "ti" and adding it in the Doc
						doc.add(new StringField("ti", ti, Field.Store.YES));
					}
					if(ligne.startsWith("*FIELD* CS")){
						/*
						String cstemp="";
						System.out.println("cs : ");	
						while(!(cstemp=br.readLine()).startsWith("*FIELD* CN")){
						//CS is an enumeration of clinical sign so we stop at the next *FIELD* which is CN
							if(cstemp.startsWith("INHERITANCE")){
								String inheritance=br.readLine();
								doc.add(new StringField("inheritance", inheritance, Field.Store.YES));
								System.out.println(inheritance);	
							}
						};*/
						String cstemp="";
						String cs="";
						while(!(cstemp=br.readLine()).startsWith("*FIELD*")){
							cs=cs+cstemp;
						}
						System.out.println("cs : "+cs);
						//creation of field "cs" and adding it in the Doc
						doc.add(new TextField("cs", cs, Field.Store.YES)); //only index because StringField analyse and also produce error "Document contains at least one immense term in field"
					}
					writer.addDocument(doc);

				}

			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}
		System.out.println("\n"+elementsAjoute+" éléments ajoutés à l'index");
	}

}

