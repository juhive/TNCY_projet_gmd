package databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class SiderConnexion {
	
	static String DB_SERVER = "jdbc:mysql://neptune.telecomnancy.univ-lorraine.fr:3306/";
	static String database = "gmd";
	static String DRIVER = "com.mysql.jdbc.Driver";
	static String USER_NAME = "gmd-read";
	static String PWD = "esial";
	
	
public static void main(String[] args) {
	
	//tableMeddra();
	//tableMeddraIndication();
	tablemeddraSe();
}

public static void tableMeddra(){
	try {
		Class.forName(DRIVER);
		Connection con = DriverManager.getConnection(DB_SERVER+database,USER_NAME, PWD);
		String myQuery = "SELECT * " +				//requete
						 "FROM meddra " ;
		Statement st = con.createStatement();
		ResultSet res = st.executeQuery(myQuery);
		
		while(res.next()){
			String CUI = res.getString("cui");
			String concept_type = res.getString("concept_type");
			int id = res.getInt("meddra_id");
			String nom= res.getString("label");
			System.out.println("");
			System.out.println(CUI+" ; "+concept_type+" ; "+id+" ; "+nom);
		}
		res.close();
		st.close();
		con.close();
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


	public static void tableMeddraIndication(){
		try {
			Class.forName(DRIVER);
			Connection con = DriverManager.getConnection(DB_SERVER+database,USER_NAME, PWD);
			String myQuery = "SELECT * " +				//requete
							 "FROM meddra_all_indications " ;
			Statement st = con.createStatement();
			ResultSet res = st.executeQuery(myQuery);
			
			while(res.next()){
				String stitch_id= res.getString("stitch_compound_id");
				String CUI = res.getString("cui");
				String detection = res.getString("method_of_detection");
				String concept_name= res.getString("concept_name");
				String concept_type= res.getString("meddra_concept_type");
				String cui_of_meddra_term= res.getString("cui_of_meddra_term");
				String meddra_concept_name= res.getString("meddra_concept_name");
				System.out.println("");
				System.out.println(stitch_id+" ; "+CUI+" ; "+detection+" ; "+concept_name+" ; "+concept_type+" ; "+cui_of_meddra_term+" ; "+meddra_concept_name);
			}
			res.close();
			st.close();
			con.close();
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
	
	
	public static void tablemeddraSe(){
		try {
			Class.forName(DRIVER);
			Connection con = DriverManager.getConnection(DB_SERVER+database,USER_NAME, PWD);
			String myQuery = "SELECT * " +				//requete
							 "FROM meddra_all_se" ;
			Statement st = con.createStatement();
			ResultSet res = st.executeQuery(myQuery);
			
			while(res.next()){
			String stitch_id1= res.getString("stitch_compound_id1");
			String stitch_id2= res.getString("stitch_compound_id2");
			String CUI = res.getString("cui");
			String concept_type= res.getString("meddra_concept_type");
			String cui_of_meddra_term= res.getString("cui_of_meddra_term");
			String side_effect= res.getString("side_effect_name");
			System.out.println("");
			System.out.println(stitch_id1+" ; "+stitch_id2+" ; "+CUI+" ; "+concept_type+" ; "+cui_of_meddra_term+" ; "+side_effect);
			}
			res.close();
			st.close();
			con.close();
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

}
