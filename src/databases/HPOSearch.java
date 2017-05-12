package databases;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

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

public class HPOSearch {

	public static void main(String[] args) throws IOException, ParseException{
		String id_HPOs = id_HPO_oboSearchid_HP("abnormality of body height");		
		Couple id_HPOsynonym = id_HPO_annotationSearchid_HP("Abnormality of the clit");
		
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

		if (in.equals("no match")){
			return id_HP;
		}
		
		QueryParser parser = new QueryParser(field, analyzer);
		System.out.println("Searching for '" + nameSearch + "'");
		
		parser.setDefaultOperator(QueryParser.Operator.AND);
		parser.setPhraseSlop(0);
		Query query = parser.createPhraseQuery(field, in);

		int hitsPerPage = 1000;
		TopDocs results = searcher.search(query, 5 * hitsPerPage);
		ScoreDoc[] hits = results.scoreDocs;
		int numTotalHits = results.totalHits;
		System.out.println(numTotalHits + " total matching documents\n");
		if(numTotalHits==0){
			return id_HP;
		}

		hits = searcher.search(query, numTotalHits).scoreDocs;
		//System.out.println(hits.length);
		//System.out.println("Number of hits: " + hits.length);

		int start = 0;
		Integer end = Math.min(hits.length, start + hitsPerPage);
		for (int i = start; i < hits.length; i++) {
			Document doc = searcher.doc(hits[0].doc);
			String label= doc.get("name");
			if (label!= null) {
				//System.out.println((i+1) + ". " + name);
				id_HP = doc.get("id_HP");
				if (id_HP != null) {
					//System.out.println((i+1)+"."+id_HP+" = "+label);
				}
			} else {
				System.out.println((i+1) + ". " + "No doc for this name");
			}
		}
		
		return ("HP:" + id_HP);	
	}



	//find hp with synonym	

	public static Couple id_HPO_annotationSearchid_HP(String nameSearch) throws IOException, ParseException{
					
		for (int p= 0; p<10; p++){			//find every synonyms
			String id_HP=null;
			String index = "indexes/HPO";
			String field = "synonym".concat(Integer.toString(p));


			IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
			IndexSearcher searcher = new IndexSearcher(reader);
			Analyzer analyzer = new StandardAnalyzer();

			String in = nameSearch;

			if (in.equals("no match")){
				return new Couple();
			}

			QueryParser parser = new QueryParser(field, analyzer);
			System.out.println("Searching for '" + nameSearch + "'");

			parser.setDefaultOperator(QueryParser.Operator.AND);
			parser.setPhraseSlop(0);
			Query query = parser.createPhraseQuery(field, in);

			int hitsPerPage = 1000;
			TopDocs results = searcher.search(query, 5 * hitsPerPage);
			ScoreDoc[] hits = results.scoreDocs;
			int numTotalHits = results.totalHits;
			
			if (numTotalHits == 0){
				return new Couple();
			}
			//System.out.println(numTotalHits + " total matching documents\n");

			hits = searcher.search(query, numTotalHits).scoreDocs;
			//System.out.println(hits.length);
			//System.out.println("Number of hits: " + hits.length);

			int start = 0;
			Integer end = Math.min(hits.length, start + hitsPerPage);
			for (int i = start; i < hits.length; i++) {
				Document doc = searcher.doc(hits[i].doc);

				String syn = "synonym".concat(Integer.toString(p));		
				String synonym= doc.get(syn);
				if (synonym!= null) {
					//System.out.println((i+1) + ". " + name);
					id_HP = doc.get("id_HP");
					if (id_HP != null) {
						System.out.println((i+1)+"."+id_HP+" = "+synonym);
					}
				} else {
					System.out.println((i+1) + ". " + "No doc for this name");
				}

				
			}Couple couple = new Couple(id_HP,"HPO");
			return couple;

				
		}
			return new Couple();

	}
}
