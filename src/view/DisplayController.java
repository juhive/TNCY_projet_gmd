package view;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.jar.JarException;

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
    	ArrayList<Couple> listDiseaseData = CStoDisease.ClinicalSignTosDisease(clinicalsign);    	
    	for (int i=0; i<listDiseaseData.size(); i++ ) {
    		diseaseData.add(new Disease(listDiseaseData.get(i).getDisease(), listDiseaseData.get(i).getDataBase()));
    	}
    	if (listDiseaseData.isEmpty()) {
    		diseaseData.add(new Disease("No result", "No result"));
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
