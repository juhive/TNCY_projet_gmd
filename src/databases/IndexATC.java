package databases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class IndexATC {
	private IndexATC() {}

	/** Index all text files under a directory. */
	public static void main(String[] args) {

		String indexPath = "indexes/ATC";
		String dbPath = "resourcesFiles/ATC/br08303.keg";  //à mettre dans ressourcesFile  


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



	/** Indexes a single document */
	static void indexDoc(IndexWriter writer, File file) throws IOException {
		int eltCnt= 0;
		if (file.canRead() && !file.isDirectory())
			try  {
				InputStream ips = new FileInputStream(file);
				InputStreamReader ipsr = new InputStreamReader(ips);
				BufferedReader br = new BufferedReader(ipsr);
				String line;

				while((line =br.readLine())!=null){


					if (line.startsWith("E")){

						Document doc = new Document(); 
						//on transforme la ligne en chaîne de caractere
						System.out.println("code_ATC= "+line.substring(8,16)+" label= "+line.substring(17));
						doc.add(new TextField ("code_ATC", line.substring(8,16), Field.Store.YES));		//on prend les 7 premiers car (taille code ATC)


						doc.add(new TextField("label", line.substring(17), Field.Store.NO));			//on prends le reste de la chaîne
						line =br.readLine();

					}
				}
				br.close();  
			}catch(Exception e){
				System.out.println(e.toString());
			}
		System.out.println("ok");
	}

}


