package view;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.jar.JarException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.parser.ParseException;

import controller.Couple;
import controller.DrugSideEffect;
import controller.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import main.CStoDisease;
import main.CStoDrugs;

public class DisplayDrugSideEffectController {
	@FXML
	private TableView<DrugSideEffect> drugTable;
	@FXML
	private TableColumn<DrugSideEffect, String> drugColumn;
	@FXML
	private TableColumn<DrugSideEffect, String> fromColumn2;
	@FXML
	private Text searchingText3;

	private String clinicalsign;
	private ObservableList<DrugSideEffect> drugSideEffectData = FXCollections.observableArrayList();
	private InHomePageController hp = new InHomePageController();
	private MainApp mainApp;

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public DisplayDrugSideEffectController() {
	}

	/**
	 * 
	 * @param medecineData
	 * @throws JarException
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ParseException
	 * @throws org.apache.lucene.queryparser.classic.ParseException
	 */
	private void showDrugTable(ObservableList<DrugSideEffect> drugData ) throws JarException, MalformedURLException, IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {
		if (drugData !=  null) {
			fillDrugData(clinicalsign);
			drugTable.setItems(drugData);
		}

	}

	/**
	 * 
	 * @param clinicalsign
	 * @throws JarException
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws ParseException
	 * @throws org.apache.lucene.queryparser.classic.ParseException
	 */
	public void fillDrugData(String clinicalsign) throws JarException, MalformedURLException, IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException{
		String cs1;
		String cs2;
		Pattern pattern = Pattern.compile(".*AND.*");
		Matcher matcher = pattern.matcher(clinicalsign);
		Pattern pattern2 = Pattern.compile(".*OR.*");
		Matcher matcher2 = pattern2.matcher(clinicalsign);

		boolean listVide = false;


		if (matcher.find()) {
			int length = clinicalsign.length();
			int i = 1;
			while(clinicalsign.charAt(i+1) != 'N' && clinicalsign.charAt(i) != 'A' && i < length-1) {
				i++;
			}
			cs1 = clinicalsign.substring(0, i-1);
			cs2 = null;
			if (clinicalsign.substring(i).length() > 4) {
				cs2 = clinicalsign.substring(i+4);
			}
			else {listVide = true;}

			ArrayList<Couple> listDrugData;    
			if (!listVide) {listDrugData = CStoDrugs.ClinicalSignToBadMedecinesET(cs1, cs2);
			}
			else {listDrugData = CStoDrugs.ClinicalSignToBadMedecines(cs1);}
			for (int l=0; l<listDrugData.size(); l++ ) {
				drugSideEffectData.add(new DrugSideEffect(listDrugData.get(l).getDisease(), listDrugData.get(l).getDataBase()));
			}
			if (listDrugData.isEmpty()) {listVide = true;}
		}


		else if (matcher2.find()) {
			int length = clinicalsign.length();
			int i = 1;
			boolean flag = false;
			while(clinicalsign.charAt(i+1) != 'R' && clinicalsign.charAt(i) != 'O' && i < length-1) {
				i++;
			}
			cs1 = clinicalsign.substring(0, i-1);
			cs2 = null;
			if (clinicalsign.substring(i).length() > 3) {
				cs2 = clinicalsign.substring(i+3);
			}
			else {flag = true;}

			ArrayList<Couple> listDrugData;     	
			if (!flag) {
				listDrugData = CStoDrugs.ClinicalSignToBadMedecinesOU(cs1, cs2);    	
			}
			else {listDrugData = CStoDrugs.ClinicalSignToBadMedecines(cs1);  }

			for (int j=0; j<listDrugData.size(); j++ ) {
				drugSideEffectData.add(new DrugSideEffect(listDrugData.get(j).getDisease(), listDrugData.get(j).getDataBase()));
			}
			if (listDrugData.isEmpty()) {listVide = true;}
		}

		else {
			ArrayList<Couple> listDrugData = CStoDrugs.ClinicalSignToBadMedecines(clinicalsign);    	

			for (int i=0; i<listDrugData.size(); i++ ) {
				drugSideEffectData.add(new DrugSideEffect(listDrugData.get(i).getDisease(), listDrugData.get(i).getDataBase()));
			}
			if (listDrugData.isEmpty()) {listVide = true;}
		}

		if (listVide) {drugSideEffectData.add(new DrugSideEffect("No result", "No result"));}
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 * @throws org.apache.lucene.queryparser.classic.ParseException 
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws MalformedURLException 
	 * @throws JarException 
	 */
	@FXML
	private void initialize() throws JarException, MalformedURLException, IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {
		clinicalsign = hp.getCS();

		// Initialize the person table with the two columns.
		drugColumn.setCellValueFactory(cellData -> cellData.getValue().drugProperty());
		fromColumn2.setCellValueFactory(cellData -> cellData.getValue().fromProperty());

		searchingText3.setText("Searching for : " + clinicalsign);

		showDrugTable(null);
		showDrugTable(drugSideEffectData);

	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
	}

	public void handleButtonBack2() {
		mainApp.showHomePage();

	}


}
