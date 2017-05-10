package view;

import controller.Disease;
import controller.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
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
    
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public DisplayController() {
    }
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        diseaseColumn.setCellValueFactory(cellData -> cellData.getValue().diseaseProperty());
        fromColumn.setCellValueFactory(cellData -> cellData.getValue().fromProperty());
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        diseaseTable.setItems(mainApp.getPersonData());
    }
    
    public void handleButtonBack() {
    	mainApp.showHomePage();
    	
    }

}
