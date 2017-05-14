package databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.SQLException;

public class SiderConnexion {

	static String DB_SERVER = "jdbc:mysql://neptune.telecomnancy.univ-lorraine.fr:3306/";
	static String database = "gmd";
	static String DRIVER = "com.mysql.jdbc.Driver";
	static String USER_NAME = "gmd-read";
	static String PWD = "esial";


	public static void main(String[] args) {

		//SearchMeddraSE_cuiToCpdId1("C0000737");
		//ArrayList<String> ar = SearchMeddraToMeddraAll("C0032285");
		//System.out.println(ar.toString());
		//tablemeddraSe();
		//String f = SearchMeddraCUIFromLABEL("pneumonia");
		//System.out.println(f);
		//sidder_meddra_all_se_compound_id1();
		tableMeddraIndication();

	}

	/**
	 * 
	 * @param clinicalSign 
	 * @return associate CUI from meddra
	 */
	public static String SearchMeddraCUIFromLABEL(String clinicalSign){
		String CUI = null;
		try {
			Class.forName(DRIVER);
			Connection con = DriverManager.getConnection(DB_SERVER+database,USER_NAME, PWD);
			//parameted request to search CUI from the given CS
			String aQuery = "SELECT CUI FROM meddra WHERE label = ?";
			//String aQuery = "SELECT * FROM meddra";
			PreparedStatement prep1 = con.prepareStatement(aQuery);
			prep1.setString(1,clinicalSign);
			//exécution de la requête
			ResultSet res = prep1.executeQuery();
			/*
		String myQuery = "SELECT CUI " +				//requete
						 "FROM meddra " +
						 "WHERE label = 'Spontaneous ejaculation'";

		Statement st = con.createStatement();
		ResultSet res = st.executeQuery(myQuery)*/
			while(res.next()){
				CUI = res.getString("cui");
				//String concept_type = res.getString("concept_type");
				//int id = res.getInt("meddra_id");
				//String nom= res.getString("label");
				//System.out.println("");
				//System.out.println(CUI);
				//System.out.println(CUI+" ; "+concept_type+" ; "+id+" ; "+nom);
			}
			res.close();
			//st.close();
			con.close();
		}
		catch (ClassNotFoundException e){
			System.err.println("Could not load JDBC driver");
			System.out.println("Exception: " + e);
			e.printStackTrace();
		}
		catch (SQLException ex) {
			System.err.println("SQL Exception information");
			while(ex!=null){
				System.err.println("" + ex.getMessage());
				System.err.println("" + ex.getSQLState());
				System.err.println("" + ex.getErrorCode());
				ex.printStackTrace();
				ex = ex.getNextException();
			}
		}
		return CUI;

	}
	
	/**
	 * 
	 * @param CUI
	 * @return compound_id from meddra_all_indications
	 */
	public static ArrayList<String> SearchMeddraToMeddraAll(String CUI){
		ArrayList<String> compound_id = new ArrayList<String>();
		try {
			Class.forName(DRIVER);
			Connection con = DriverManager.getConnection(DB_SERVER+database,USER_NAME, PWD);
			//parameted request to search CUI from the given CS
			String aQuery = "SELECT stitch_compound_id FROM meddra_all_indications WHERE cui = ?";
			//String aQuery = "SELECT * FROM meddra";
			PreparedStatement prep1 = con.prepareStatement(aQuery);
			prep1.setString(1,CUI);
			//exécution de la requête
			ResultSet res = prep1.executeQuery();
			
			while(res.next()){
				
				if(!compound_id.contains(res.getString("stitch_compound_id"))){
					String ada = res.getString("stitch_compound_id");
					ada = ada.substring(0, 3).concat("m").concat(ada.substring(4));
					compound_id.add(ada);
					//System.out.print(ada);
				}
			}
			res.close();
			con.close();
		}
		catch (ClassNotFoundException e){
			System.err.println("Could not load JDBC driver");
			System.out.println("Exception: " + e);
			e.printStackTrace();
		}
		catch (SQLException ex) {
			System.err.println("SQL Exception information");
			while(ex!=null){
				System.err.println("" + ex.getMessage());
				System.err.println("" + ex.getSQLState());
				System.err.println("" + ex.getErrorCode());
				ex.printStackTrace();
				ex = ex.getNextException();
			}
		}
		return compound_id;

	}
	
	/**
	 * 
	 * @param CUI from meddra_all_se
	 * @return Stitch_compound_id1 from meddra_all_se
	 */
	public static String SearchMeddraSE_cuiToCpdId1(String CUI){
		String stitch_compound_id1 = null;
		try {
			Class.forName(DRIVER);
			Connection con = DriverManager.getConnection(DB_SERVER+database,USER_NAME, PWD);
			String aQuery = "SELECT stitch_compound_id1 FROM meddra_all_se WHERE cui = ?";;
			PreparedStatement prep1 = con.prepareStatement(aQuery);
			prep1.setString(1,CUI);
			ResultSet res = prep1.executeQuery();
			
			while(res.next()){
				String ada = res.getString("stitch_compound_id1");
				ada = ada.substring(0, 3).concat("m").concat(ada.substring(4));
				stitch_compound_id1 = ada;
				//System.out.println(ada);
			}
			res.close();
			con.close();
		}
		catch (ClassNotFoundException e){
			System.err.println("Could not load JDBC driver");
			System.out.println("Exception: " + e);
			e.printStackTrace();
		}
		catch (SQLException ex) {
			System.err.println("SQL Exception information");
			while(ex!=null){
				System.err.println("" + ex.getMessage());
				System.err.println("" + ex.getSQLState());
				System.err.println("" + ex.getErrorCode());
				ex.printStackTrace();
				ex = ex.getNextException();
			}
		}
		System.out.println(stitch_compound_id1);
		return stitch_compound_id1;

	}
	
	/**
	 * 
	 * @param side effect term from meddra_all_se
	 * @return Stitch_compound_id1 from meddra_all_se
	 */
	public static String meddraSE_sideEffect_ToCpdId1(String sideEffect){
		String stitch_compound_id1 = null;
		try {
			Class.forName(DRIVER);
			Connection con = DriverManager.getConnection(DB_SERVER+database,USER_NAME, PWD);
			String aQuery = "SELECT stitch_compound_id1 FROM meddra_all_se WHERE side_effect_name = ?";;
			PreparedStatement prep1 = con.prepareStatement(aQuery);
			prep1.setString(1,sideEffect);
			ResultSet res = prep1.executeQuery();
			
			while(res.next()){
				String ada = res.getString("stitch_compound_id1");
				ada = ada.substring(0, 3).concat("m").concat(ada.substring(4));
				stitch_compound_id1 = ada;
				//System.out.print(ada);
			}
			res.close();
			con.close();
		}
		catch (ClassNotFoundException e){
			System.err.println("Could not load JDBC driver");
			System.out.println("Exception: " + e);
			e.printStackTrace();
		}
		catch (SQLException ex) {
			System.err.println("SQL Exception information");
			while(ex!=null){
				System.err.println("" + ex.getMessage());
				System.err.println("" + ex.getSQLState());
				System.err.println("" + ex.getErrorCode());
				ex.printStackTrace();
				ex = ex.getNextException();
			}
		}
		//System.out.println(stitch_compound_id1);
		return stitch_compound_id1;

	}
	
	/**
	 * 
	 * @return ArrayList<String> of all stitch_compound_id1 from sidder meddra all se
	 */
	public static ArrayList<String> sidder_meddra_all_se_compound_id1(){
		ArrayList<String> stitch_compound_id1_list = new ArrayList<String>();
		String stitch_compound_id1 = null;
		try {
			Class.forName(DRIVER);
			Connection con = DriverManager.getConnection(DB_SERVER+database,USER_NAME, PWD);
			String aQuery = "SELECT stitch_compound_id1 FROM meddra_all_se";
			PreparedStatement prep1 = con.prepareStatement(aQuery);
			ResultSet res = prep1.executeQuery();
			
			while(res.next()){
				stitch_compound_id1 = res.getString("stitch_compound_id1");
				System.out.println(stitch_compound_id1);
				stitch_compound_id1_list.add(stitch_compound_id1);
			}
			res.close();
			con.close();
		}
		catch (ClassNotFoundException e){
			System.err.println("Could not load JDBC driver");
			System.out.println("Exception: " + e);
			e.printStackTrace();
		}
		catch (SQLException ex) {
			System.err.println("SQL Exception information");
			while(ex!=null){
				System.err.println("" + ex.getMessage());
				System.err.println("" + ex.getSQLState());
				System.err.println("" + ex.getErrorCode());
				ex.printStackTrace();
				ex = ex.getNextException();
			}
		}
		return stitch_compound_id1_list;

	}

	public static void tableMeddraIndication(){
		try {
			Class.forName(DRIVER);
			Connection con = DriverManager.getConnection(DB_SERVER+database,USER_NAME, PWD);
			String myQuery = "SELECT stitch_compound_id " +				//requete
					"FROM meddra_all_indications " ;
			Statement st = con.createStatement();
			ResultSet res = st.executeQuery(myQuery);

			while(res.next()){
				String stitch_id= res.getString("stitch_compound_id");
				
				System.out.println(stitch_id);
				//System.out.println(stitch_id+" ; "+CUI+" ; "+detection+" ; "+concept_name+" ; "+concept_type+" ; "+cui_of_meddra_term+" ; "+meddra_concept_name);
			}
			res.close();
			st.close();
			con.close();
		}
		catch (ClassNotFoundException e){
			System.err.println("Could not load JDBC driver");
			System.out.println("Exception: " + e);
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
			String myQuery = "SELECT stitch_compound_id1 " +				//requete
					"FROM meddra_all_se" ;
			Statement st = con.createStatement();
			ResultSet res = st.executeQuery(myQuery);

			while(res.next()){
				String stitch_id1= res.getString("stitch_compound_id1");
				//String stitch_id2= res.getString("stitch_compound_id2");
				//String CUI = res.getString("cui");
				//String concept_type= res.getString("meddra_concept_type");
				//String cui_of_meddra_term= res.getString("cui_of_meddra_term");
				//String side_effect= res.getString("side_effect_name");
				//System.out.println("");
				//System.out.println(stitch_id1); //+" ; "+stitch_id2+" ; "+CUI+" ; "+concept_type+" ; "+cui_of_meddra_term+" ; "+side_effect);
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

