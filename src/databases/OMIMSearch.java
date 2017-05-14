package databases;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

import controller.Couple;

public class OMIMSearch {

	
	public static void main(String[] args) throws IOException, ParseException{
		ArrayList<Couple> omims = OMIMSearchDisease("lack of visual");
		System.out.println(omims.size() + " total matching documents\n");
	}

	//private SearchATC() {}

	public static ArrayList<Couple> OMIMSearchDisease(String CStosearch) throws IOException, ParseException{
		ArrayList<Couple> disease2= new ArrayList<Couple>();
		ArrayList<String> disease= new ArrayList<String>();
		Pattern pattern = Pattern.compile("^#[0-9]|^%[0-9]");
		Pattern pattern2 = Pattern.compile("^[0-9]");
		
		String index = "indexes/omim";
		String field = "cs";

		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer();

		String in = CStosearch;
		
		if (in.equals("no match")){
			return disease2;
		}
		
		
		QueryParser parser = new QueryParser(field, analyzer);
		//System.out.println("Searching for '" + CStosearch + "'");
		
		//new
		//search for the exact sentence given
		parser.setDefaultOperator(QueryParser.Operator.AND);
		parser.setPhraseSlop(0);
		Query query = parser.createPhraseQuery(field, in);

		int hitsPerPage = 100;
		TopDocs results = searcher.search(query, 1000);
		ScoreDoc[] hits = results.scoreDocs;
		int numTotalHits = results.totalHits;
		//System.out.println(numTotalHits + " total matching documents\n");
		if (numTotalHits == 0) {
			return disease2;
		}

		hits = searcher.search(query, numTotalHits).scoreDocs;
		//System.out.println("Number of hits: " + hits.length);
		
		
		int start = 0;
		Integer end = Math.min(hits.length, start + hitsPerPage);
		for (int i = start; i < hits.length; i++) {
			Document doc = searcher.doc(hits[i].doc);
			String cs = doc.get("cs");
			//System.out.println(cs);
			if (cs != null) {
				//System.out.println((i+1) + ". " + name);
				String current = doc.get("ti");
				if (current != null) {
					if(!disease.contains(current)){
						//System.out.println((i+1)+"."+" = "+current);
						disease.add(current);
						
						Matcher matcher = pattern.matcher(current);
						if (matcher.find()) {
							current = current.substring(8);
							int length = current.length();
		 					int k = 0;
		 					while(current.charAt(k) != ';' && k < length-1) {
		 						k++;
		 						}
		 					current = current.substring(0, k);
							
						}
						Matcher matcher2 = pattern2.matcher(current);
						if (matcher2.find()) {
							current = current.substring(7);
							int length = current.length();
		 					int k = 0;
		 					while(current.charAt(k) != ';' && k < length-1) {
		 						k++;
		 						}
		 					current = current.substring(0, k);
						}
						current = current.toLowerCase();
						Couple couple = new Couple(current, "OMIM");
						
						//System.out.println((i+1)+"."+" = "+couple.getDisease());
						disease2.add(couple);
						}
				}
			} else {
				System.out.println((i+1) + ". " + "No doc for this name");
			}
		}
		return disease2;	
	}

}