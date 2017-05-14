package view;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.jar.JarException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.parser.ParseException;

import controller.Couple;
import controller.Disease;
import controller.MainApp;
import databases.OrphaDataBase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import main.CStoDisease;

public class DisplayController {
	
	@FXML
    private TableView<Disease> diseaseTable;
    @FXML
    private TableColumn<Disease, String> diseaseColumn;
    @FXML
    private TableColumn<Disease, String> fromColumn;
    @FXML
    private Text searchingText;
 
    // Reference to the main application.
    private MainApp mainApp;
    
    private String clinicalsign;
    
    private ObservableList<Disease> diseaseData = FXCollections.observableArrayList();
    
    private InHomePageController hp = new InHomePageController();
    
    private CStoDisease csTOdis = new CStoDisease();
    
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public DisplayController() {
    }
   
    /**
     * fill dieaseTAble with the datas in diseaseData
     * @param diseaseData
     * @throws ParseException 
     * @throws IOException 
     * @throws MalformedURLException 
     * @throws JarException 
     * @throws org.apache.lucene.queryparser.classic.ParseException 
     */
    private void showDiseaseTable(ObservableList<Disease> diseaseData ) throws JarException, MalformedURLException, IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {
    	if (diseaseData !=  null) {
    		fillDiseaseData(clinicalsign);
    		diseaseTable.setItems(diseaseData);
    	}
    	
    }
    
    /**
     * @throws ParseException 
     * @throws IOException 
     * @throws MalformedURLException 
     * @throws JarException 
     * @throws org.apache.lucene.queryparser.classic.ParseException 
     * 
     */
    public void fillDiseaseData(String clinicalsign) throws JarException, MalformedURLException, IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException{
    	String cs1;
    	String cs2;
    	Pattern pattern = Pattern.compile(".*AND.*");
    	Matcher matcher = pattern.matcher(clinicalsign);
    	Pattern pattern2 = Pattern.compile(".*OR.*");
    	Matcher matcher2 = pattern2.matcher(clinicalsign);
    	
    	
    	if (matcher.find()) {
    		int length = clinicalsign.length();
			int i = 1;
			while(clinicalsign.charAt(i-1) != ' ' && clinicalsign.charAt(i) != 'A' && i < length-1) {
					i++;
				}
			cs1 = clinicalsign.substring(0, i-1);
			cs2 = clinicalsign.substring(i+2);
			
			ArrayList<Couple> listDiseaseData = CStoDisease.ClinicalSignTosDiseaseET(cs1, cs2);    	
	    	for (int l=0; l<listDiseaseData.size(); l++ ) {
	    		diseaseData.add(new Disease(listDiseaseData.get(l).getDisease(), listDiseaseData.get(l).getDataBase()));
	    	}
	    	if (listDiseaseData.isEmpty()) {
	    		diseaseData.add(new Disease("No result", "No result"));
	    	}
		}
    	
    	
    	if (matcher2.find()) {
    		int length = clinicalsign.length();
			int i = 1;
			while(clinicalsign.charAt(i-1) != ' ' && clinicalsign.charAt(i) != 'O' && i < length-1) {
					i++;
				}
			cs1 = clinicalsign.substring(0, i-1);
			cs2 = clinicalsign.substring(i+2);
			
			ArrayList<Couple> listDiseaseData = CStoDisease.ClinicalSignTosDiseaseOU(cs1, cs2);    	
	    	for (int j=0; j<listDiseaseData.size(); j++ ) {
	    		diseaseData.add(new Disease(listDiseaseData.get(j).getDisease(), listDiseaseData.get(j).getDataBase()));
	    	}
	    	if (listDiseaseData.isEmpty()) {
	    		diseaseData.add(new Disease("No result", "No result"));
	    	}
		}
    	
    	
    	if (!matcher.find() && !matcher2.find()) {
    		ArrayList<Couple> listDiseaseData = CStoDisease.ClinicalSignTosDisease(clinicalsign);    	
	    	for (int k=0; k<listDiseaseData.size(); k++ ) {
	    		diseaseData.add(new Disease(listDiseaseData.get(k).getDisease(), listDiseaseData.get(k).getDataBase()));
	    	}
	    	if (listDiseaseData.isEmpty()) {
	    		diseaseData.add(new Disease("No result", "No result"));
	    	}
    	}
    	
    }
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @throws ParseException 
     * @throws IOException 
     * @throws MalformedURLException 
     * @throws JarException 
     * @throws org.apache.lucene.queryparser.classic.ParseException 
     */
    @FXML
    private void initialize() throws JarException, MalformedURLException, IOException, ParseException, org.apache.lucene.queryparser.classic.ParseException {
    	clinicalsign = hp.getCS();
    	
    	
        // Initialize the disease table with the two columns.
        diseaseColumn.setCellValueFactory(cellData -> cellData.getValue().diseaseProperty());
        fromColumn.setCellValueFactory(cellData -> cellData.getValue().fromProperty()); 
        
        searchingText.setText("Searching for : " + clinicalsign);
        
        showDiseaseTable(null);
        showDiseaseTable(diseaseData);
        
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     * @throws ParseException 
     * @throws IOException 
     * @throws MalformedURLException 
     * @throws JarException 
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    
    public void handleButtonBack() {
    	mainApp.showHomePage();
    	
    }
    

    /**
     * Returns the data as an observable list of Disease. 
     * @return
     */
    public ObservableList<Disease> getDiseaseData() {
        return diseaseData;
    }
    
 

}
