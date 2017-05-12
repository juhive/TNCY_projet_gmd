package view;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.jar.JarException;

import org.json.simple.parser.ParseException;

import controller.Couple;
import controller.DrugSideEffect;
import controller.MainApp;
import controller.Medecine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import main.CStoDrugs;
import main.CStoMedicines;

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
    private CStoDrugs csTOdrug = new CStoDrugs();
    // Reference to the main application.
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
    	//search drug matching this clinicalSign
    	ArrayList<Couple> listDrugData = csTOdrug.ClinicalSignToBadMedecines(clinicalsign);    	
    	
    	for (int i=0; i<listDrugData.size(); i++ ) {
    		drugSideEffectData.add(new DrugSideEffect(listDrugData.get(i).getDisease(), listDrugData.get(i).getDataBase()));
    	}
    	if (listDrugData.isEmpty()) {
    		drugSideEffectData.add(new DrugSideEffect("No result", "No result"));
    	}
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
    
    /**
     * Returns the data as an observable list of DrugSideEffect. 
     * @return
     */
    public ObservableList<DrugSideEffect> getDrugSideEffectData() {
        return drugSideEffectData;
    } 
  
}
