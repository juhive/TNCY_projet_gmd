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

public class OMIMSearch {

	
	public static void main(String[] args) throws IOException, ParseException{
		String omims = OMIMSearchDisease("visual AND head AND lack"); //Attention ici on doit récupérer une liste de diseases
		System.out.println(""+omims);
	}

	//private SearchATC() {}

	public static String OMIMSearchDisease(String CStosearch) throws IOException, ParseException{
		String disease=null;
		String index = "indexes/omim";
		String field = "cs";

		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer();

		String in = CStosearch;
		QueryParser parser = new QueryParser(field, analyzer);
		System.out.println("Searching for '" + CStosearch + "'");
		Query query = parser.parse(in);

		int hitsPerPage = 100;
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
			String cs = doc.get("cs");
			if (cs != null) {
				//System.out.println((i+1) + ". " + name);
				disease = doc.get("ti");
				if (disease != null) {
					//System.out.println((i+1)+"."+cs+" = "+disease);
				}
			} else {
				System.out.println((i+1) + ". " + "No doc for this name");
			}
		}
		return disease;	
	}

}
