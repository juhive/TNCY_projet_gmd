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

public class DisplayController {
	
	@FXML
    private TableView<Disease> diseaseTable;
    @FXML
    private TableColumn<Disease, String> diseaseColumn;
    @FXML
    private TableColumn<Disease, String> fromColumn;
 
    // Reference to the main application.
    private MainApp mainApp;
    
    private String clinicalsign;
    
    private ObservableList<Disease> diseaseData = FXCollections.observableArrayList();
    
    private InHomePageController hp = new InHomePageController();
    
    private OrphaDataBase orpha = new OrphaDataBase();
    
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
     */
    private void showDiseaseTable(ObservableList<Disease> diseaseData ) throws JarException, MalformedURLException, IOException, ParseException {
    	if (diseaseData !=  null) {
    		fillDiseaseData(clinicalsign);
    		diseaseTable.setItems(diseaseData);
    	}
    	else {}
    }
    
    /**POUR L'INSTANT "BRANCHÉ" AVEC ORPHADATA DIRECTEMENT
     * @throws ParseException 
     * @throws IOException 
     * @throws MalformedURLException 
     * @throws JarException 
     * 
     */
    public void fillDiseaseData(String clinicalsign) throws JarException, MalformedURLException, IOException, ParseException{
    	ArrayList<Couple> listDiseaseData = orpha.CStoDiseases(clinicalsign);    	
    	for (int i=0; i<listDiseaseData.size(); i++ ) {
    		diseaseData.add(new Disease(listDiseaseData.get(i).getDisease(), listDiseaseData.get(i).getDataBase()));
    	}
    }
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     * @throws ParseException 
     * @throws IOException 
     * @throws MalformedURLException 
     * @throws JarException 
     */
    @FXML
    private void initialize() throws JarException, MalformedURLException, IOException, ParseException {
    	clinicalsign = hp.getCS();
    	
        // Initialize the disease table with the two columns.
        diseaseColumn.setCellValueFactory(cellData -> cellData.getValue().diseaseProperty());
        fromColumn.setCellValueFactory(cellData -> cellData.getValue().fromProperty()); 
        
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
