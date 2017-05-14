package databases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


public class StitchConnexion {
	
	public static void main(String[] args) throws IOException, ParseException {
		StitchIndex();
		//meddraAllSeCompoundId1_in_StitchCodeAtc("CIDm00031477");
		//String res = stitch_CpdID_to_codeATC("CIDm00060871");
		//System.out.println(res);
	}
	
	public static String stitch_CpdID_to_codeATC(String nameSearch) throws IOException, ParseException{
		String res="no match";
		String index = "indexes/Stitch";
		String field = "CID";

		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer();

		String in = nameSearch;

		if (in.equals("no match")){
			return res;
		}
		
		QueryParser parser = new QueryParser(field, analyzer);
		//System.out.println("Searching for '" + nameSearch + "'");
		
		parser.setDefaultOperator(QueryParser.Operator.AND);
		parser.setPhraseSlop(0);
		Query query = parser.createPhraseQuery(field, in);

		int hitsPerPage = 1000;
		TopDocs results = searcher.search(query, 5 * hitsPerPage);
		ScoreDoc[] hits = results.scoreDocs;
		int numTotalHits = results.totalHits;
		//System.out.println(numTotalHits + " total matching documents\n");
		if(numTotalHits==0){
			return res;
		}

		hits = searcher.search(query, numTotalHits).scoreDocs;
		//System.out.println(hits.length);
		//System.out.println("Number of hits: " + hits.length);

		int start = 0;
		Integer end = Math.min(hits.length, start + hitsPerPage);
		for (int i = start; i < hits.length; i++) {
			Document doc = searcher.doc(hits[0].doc);
			String label= doc.get("CID");
			if (label!= null) {
				String atc= doc.get("ATC");
				if(atc!=null){
					res =atc;
				}
			} else {
				System.out.println((i+1) + ". " + "No doc for this name");
			}
		}
		
		return res;	
	}
	
	
	public static void StitchIndex() {

		String indexPath = "indexes/Stitch";
		String dbPath = "resourcesFiles/Stitch/chemical.sources.v5.0.tsv";  


		final File dbFile = new File(dbPath);
		if (!dbFile.exists()) {
			System.out.println("the file '" +dbFile+ "' does not exist or is not readable, please check the path");
			System.exit(1);
		}

		Date start = new Date();
		try {
			System.out.println("Indexing to directory '" + indexPath + "'...");

			Directory dir = FSDirectory.open(Paths.get(indexPath));
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);


			iwc.setOpenMode(OpenMode.CREATE);


			IndexWriter writer = new IndexWriter(dir, iwc);
			indexDoc(writer, dbFile);

			writer.close();

			Date end = new Date();
			System.out.println(end.getTime() - start.getTime() + " total milliseconds");

		} catch (IOException e) {
			System.out.println(" caught a " + e.getClass() +
					"\n with message: " + e.getMessage());
		}
	}
	
	static void indexDoc(IndexWriter writer, File file) throws IOException {
		int eltCnt= 0;
		if (file.canRead() && !file.isDirectory())
			try  {
				InputStream ips = new FileInputStream(file);
				InputStreamReader ipsr = new InputStreamReader(ips);
				BufferedReader br = new BufferedReader(ipsr);
				String line;
				
				for(int i =0;i<10;i++){
					line=br.readLine();
				}

				while((line=br.readLine())!=null){
					if (line.startsWith("CID") && line.contains("ATC")){
						Document doc = new Document(); 
						doc.add(new TextField ("CID", line.substring(0, 12), Field.Store.YES));
						doc.add(new TextField ("ATC", line.substring(line.length()-7, line.length()), Field.Store.YES));
						writer.addDocument(doc);

					}
				}
				br.close();  
			}catch(Exception e){
				System.out.println(e.toString());
			}
	}
	
	
/**
 * 
 * @param compound_id (from meddra_all_indication or meddra_all_se)
 * @return code ATC
 */
	public static String stitch_CpdID_to_codeATC2(String compound_id) {

	        String csvFile = "resourcesFiles/Stitch/chemical.sources.v5.0.tsv";
	        String line = "";
	        String cvsSplitBy = "\t";
	        String codeATC = "no match";

	        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	        	
	        	line = br.readLine();
	            while ((line = br.readLine()) != null) {
	            	if (!line.startsWith("#")) {
	                	String[] uneLigne = line.split(cvsSplitBy);
	                	//System.out.println(uneLigne[0] + " " + uneLigne[1] + " " + uneLigne[2] + " " +uneLigne[3]);
	                	//System.out.println(uneLigne[0].toString()+"   "+compound_id);
	                	
	                	if (uneLigne[0].toString().equals(compound_id)){
	                		codeATC =uneLigne[3];
	                	}
	            	}

	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return codeATC;

	    }
	
	/**
	 * Return true if compound_id present in bdd stitch
	 * @param compound_id
	 * @return
	 */
	
	public static boolean meddraAllSeCompoundId1_in_StitchCodeAtc(String compound_id) {

        String csvFile = "resourcesFiles/Stitch/chemical.sources.v5.0.tsv";
        String line = "";
        String cvsSplitBy = "\t";
        boolean flag = false;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        	
        	line = br.readLine();
            while ((line = br.readLine()) != null) {
            	if (!line.startsWith("#")) {
                	String[] uneLigne = line.split(cvsSplitBy);
                	//System.out.println(uneLigne[0] + " " + uneLigne[1] + " " + uneLigne[2] + " " +uneLigne[3]);
                	if(uneLigne[0].equals(compound_id)){
                		flag = true;
                	}
            	}
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;

    }
	
	
	

}
