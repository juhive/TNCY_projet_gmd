package view;

import controller.Medecine;
import controller.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MedecineController {
	
	@FXML
    private TableView<Medecine> medecineTable;
    @FXML
    private TableColumn<Medecine, String> medecineColumn;
    @FXML
    private TableColumn<Medecine, String> fromColumn3;


    // Reference to the main application.
    private MainApp mainApp;
    
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public MedecineController() {
    }
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        medecineColumn.setCellValueFactory(cellData -> cellData.getValue().medecineProperty());
        fromColumn3.setCellValueFactory(cellData -> cellData.getValue().fromProperty());
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        medecineTable.setItems(mainApp.getMedecineData());
    }
    
    public void handleButtonBack3() {
    	mainApp.showHomePage();
    	
    }

}
