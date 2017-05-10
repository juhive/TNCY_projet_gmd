package view;

import controller.DrugSideEffect;
import controller.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class DisplayDrugSideEffectController {
	@FXML
    private TableView<DrugSideEffect> drugTable;
    @FXML
    private TableColumn<DrugSideEffect, String> drugColumn;
    @FXML
    private TableColumn<DrugSideEffect, String> fromColumn2;


    // Reference to the main application.
    private MainApp mainApp;
    
    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public DisplayDrugSideEffectController() {
    }
    
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        drugColumn.setCellValueFactory(cellData -> cellData.getValue().drugProperty());
        fromColumn2.setCellValueFactory(cellData -> cellData.getValue().fromProperty());
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        drugTable.setItems(mainApp.getDrugSideEffectData());
    }
    
    public void handleButtonBack2() {
    	mainApp.showHomePage();
    	
    }
}
