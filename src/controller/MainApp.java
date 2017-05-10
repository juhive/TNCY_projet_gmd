package controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.DisplayController;
import view.InHomePageController;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane homePage;
    
    
    /**
     * The data as an observable list of Persons.
     */
    private ObservableList<Disease> personData = FXCollections.observableArrayList();

    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
        personData.add(new Disease("Hans", "Muster"));
        personData.add(new Disease("Ruth", "Mueller"));
        personData.add(new Disease("Heinz", "Kurz"));
        personData.add(new Disease("Cornelia", "Meier"));
        personData.add(new Disease("Werner", "Meyer"));
        personData.add(new Disease("Lydia", "Kunz"));
        personData.add(new Disease("Anna", "Best"));
        personData.add(new Disease("Stefan", "Meier"));
        personData.add(new Disease("Martin", "Mueller"));
    }

    /**
     * Returns the data as an observable list of Persons. 
     * @return
     */
    public ObservableList<Disease> getPersonData() {
        return personData;
    }
    
    
    

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("TAMALOO");

        initRootLayout();

        showHomePage();
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/Homepage.fxml"));
            homePage = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(homePage);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
   

    /**
     * Shows the inHomePage overview inside the root layout HomePage.
     */
    
    public void showHomePage() {
        try {
            // Load inHomePage overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/InHomePage.fxml"));
            AnchorPane inHomePage = (AnchorPane) loader.load();
           
         // Give the controller access to the main app.
            InHomePageController controller = loader.getController();
            controller.setMainApp(this);
		
            // Set inHomePage overview into the center of root layout homePage.
            homePage.setCenter(inHomePage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
    /**
     * Shows the display view inside the root layout.
     */
    public void showDisplayPage() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("../view/Display.fxml"));
            AnchorPane display = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            homePage.setCenter(display);

            // Give the controller access to the main app.
            DisplayController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}