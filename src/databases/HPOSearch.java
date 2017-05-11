package databases;

import java.io.IOException;
import java.nio.file.Paths;

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

public class HPOSearch {
	
	public static void main(String[] args) throws IOException, ParseException{
		String id_HPOs = id_HPO_oboSearchid_HP("Abnormality of body height");
		System.out.println("\n"+id_HPOs);
	}

	private HPOSearch() {}
	
	public static String id_HPO_oboSearchid_HP(String nameSearch) throws IOException, ParseException{
		String id_HP=null;
		String index = "indexes/HPO";
		String field = "name";
		
		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer();

		String in = nameSearch;
		QueryParser parser = new QueryParser(field, analyzer);
		System.out.println("Searching for '" + nameSearch + "'");
		Query query = parser.parse(in);
		
		int hitsPerPage = 10;
		TopDocs results = searcher.search(query, 5 * hitsPerPage);
		ScoreDoc[] hits = results.scoreDocs;
		
		int numTotalHits = results.totalHits;
		System.out.println(numTotalHits + " total matching documents\n");
		
		hits = searcher.search(query, numTotalHits).scoreDocs;
		System.out.println(hits.length);
		System.out.println("Number of hits: " + hits.length);
		
		int start = 0;
		Integer end = Math.min(hits.length, start + hitsPerPage);
		for (int i = start; i < end; i++) {
			Document doc = searcher.doc(hits[i].doc);
			String label= doc.get("name");
			if (id_HP!= null) {
				//System.out.println((i+1) + ". " + name);
				id_HP = doc.get("id_HP");
				if (label != null) {
					System.out.println((i+1)+"."+id_HP+" = "+label);
				}
			} else {
				System.out.println((i+1) + ". " + "No doc for this name");
			}
		}
		return id_HP;	
	}
	
	
	
	public static String id_HPO_annotationSearchid_HP(String nameSearch) throws IOException, ParseException{
		String id_HP=null;
		String index = "indexes/HPO";
		String field = "name";
		
		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer();

		String in = nameSearch;
		QueryParser parser = new QueryParser(field, analyzer);
		System.out.println("Searching for '" + nameSearch + "'");
		Query query = parser.parse(in);
		
		int hitsPerPage = 10;
		TopDocs results = searcher.search(query, 5 * hitsPerPage);
		ScoreDoc[] hits = results.scoreDocs;
		int numTotalHits = results.totalHits;
		System.out.println(numTotalHits + " total matching documents\n");
		
		hits = searcher.search(query, numTotalHits).scoreDocs;
		System.out.println("Number of hits: " + hits.length);
		
		int start = 0;
		Integer end = Math.min(hits.length, start + hitsPerPage);
		for (int i = start; i < end; i++) {
			Document doc = searcher.doc(hits[i].doc);
			String label= doc.get("name");
			if (id_HP!= null) {
				//System.out.println((i+1) + ". " + name);
				id_HP = doc.get("id_HP");
				if (label != null) {
					System.out.println((i+1)+"."+id_HP+" = "+label);
				}
			} else {
				System.out.println((i+1) + ". " + "No doc for this name");
			}
		}
		return id_HP;	
	}

}
