package view;

import controller.Medecine;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.jar.JarException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.parser.ParseException;

import controller.Couple;
import controller.MainApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import main.CStoMedicines;

public class MedecineController {
	
	@FXML
    private TableView<Medecine> medecineTable;
    @FXML
    private TableColumn<Medecine, String> medecineColumn;
    @FXML
    private TableColumn<Medecine, String> fromColumn3;
    @FXML
    private Text searchingText2;

    private MainApp mainApp;
    private String clinicalsign;
    private ObservableList<Medecine> medecineData = FXCollections.observableArrayList();
    private InHomePageController hp = new InHomePageController();
    
    
    
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public MedecineController() {
    }
    
    /**
     * 
     * @param diseaseData
     * @throws JarException
     * @throws MalformedURLException
     * @throws IOException
     * @throws ParseException
     * @throws org.apache.lucene.queryparser.classic.ParseException
     */
    private void showMedecineTable(ObservableList<Medecine> medecineData ) throws JarException, MalformedURLException, IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {
    	if (medecineData !=  null) {
    		fillMedecineData(clinicalsign);
    		medecineTable.setItems(medecineData);
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
    public void fillMedecineData(String clinicalsign) throws JarException, MalformedURLException, IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException{
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
			while(clinicalsign.charAt(i-1) != ' ' && clinicalsign.charAt(i) != 'A' && i < length-1) {
					i++;
				}
			cs1 = clinicalsign.substring(0, i-1);
			cs2 = null;
			if (clinicalsign.substring(i).length() > 3) {
				cs2 = clinicalsign.substring(i+4);
			}
			else {listVide = true;}
			
			ArrayList<Couple> listMedecineData;    
			if(!listVide) {listMedecineData = CStoMedicines.ClinicalSignToGooodMedecinesET(cs1, cs2);
				
			}
			else {
				listMedecineData = CStoMedicines.ClinicalSignToGooodMedecines(cs1);
			}
	    	for (int l=0; l<listMedecineData.size(); l++ ) {
	    		medecineData.add(new Medecine(listMedecineData.get(l).getDisease(), listMedecineData.get(l).getDataBase()));
	    	}
	    	if (listMedecineData.isEmpty()) {listVide = true;}
		}
    	
    	
    	else if (matcher2.find()) {
    		int length = clinicalsign.length();
			int i = 1;
			boolean flag = false;
			while(clinicalsign.charAt(i-1) != ' ' && clinicalsign.charAt(i) != 'O' && i < length-1) {
					i++;
				}
			cs1 = clinicalsign.substring(0, i-1);
			cs2 = null;
			if (clinicalsign.substring(i).length() > 2) {
				cs2 = clinicalsign.substring(i+3);
			}
			else {flag = true;}
			
			ArrayList<Couple> listMedecineData;    	
			if (!flag) {
				listMedecineData = CStoMedicines.ClinicalSignToGooodMedecinesOU(cs1, cs2);    	
			}
			else {listMedecineData = CStoMedicines.ClinicalSignToGooodMedecines(cs1);  }
			for (int j=0; j<listMedecineData.size(); j++ ) {
	    		medecineData.add(new Medecine(listMedecineData.get(j).getDisease(), listMedecineData.get(j).getDataBase()));
	    	}
	    	if (listMedecineData.isEmpty()) {listVide = true;}
		}
    	
	    else {
	    	ArrayList<Couple> listMedecineData = CStoMedicines.ClinicalSignToGooodMedecines(clinicalsign);
	    	for (int i=0; i<listMedecineData.size(); i++ ) {
	    		medecineData.add(new Medecine(listMedecineData.get(i).getDisease(), listMedecineData.get(i).getDataBase()));
	    	}
	    	if (listMedecineData.isEmpty()) {listVide = true;}
	    }
    	
    	if (listVide) {medecineData.add(new Medecine("No result", "No result"));}
    	
    	
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
        medecineColumn.setCellValueFactory(cellData -> cellData.getValue().medecineProperty());
        fromColumn3.setCellValueFactory(cellData -> cellData.getValue().fromProperty());
        
        searchingText2.setText("Searching for : " + clinicalsign);
        
        showMedecineTable(null);
        showMedecineTable(medecineData);
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    public void handleButtonBack3() {
    	mainApp.showHomePage();
    	
    }
    
    /**
     * Returns the data as an observable list of Medecine. 
     * @return
     */
    public ObservableList<Medecine> getMedecineData() {
        return medecineData;
    }

}
