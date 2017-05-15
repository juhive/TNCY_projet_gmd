package view;



import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.parser.ParseException;

import controller.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class InHomePageController {
	
		@FXML
	  	private TextField CS;
		@FXML
	  	private Text textMignon;
		private static String clinicalSign;
		
	    private MainApp mainApp;
	    
	    Pattern pattern = Pattern.compile("[ ]+");
	    
	    		
	    /**
	     * The constructor.
	     * The constructor is called before the initialize() method.
	     */
	    public InHomePageController() {	    	
	    }

	    /**
	     * Initializes the controller class. This method is automatically called
	     * after the fxml file has been loaded.
	     */
	    @FXML
	    private void initialize() {
	        }

	    /**
	     * Is called by the main application to give a reference back to itself.
	     * 
	     * @param mainApp
	     */
	    public void setMainApp(MainApp mainApp) {
	        this.mainApp = mainApp;
	    }
	    
	    public void handleButtonSearch() throws ParseException {
	    	clinicalSign = CS.getText();
	    	Matcher matcher = pattern.matcher(clinicalSign);
	    	//System.out.println(clinicalSign);
	    	if (clinicalSign.isEmpty() || matcher.find()) {
	    		mainApp.showHomePage();
	    	}
	    	else {mainApp.showDisplayPage();}	    	
	    }
	    		    
	    public void handleButtonDrugSideEffect() {
	    	clinicalSign = CS.getText();
	    	//System.out.println(clinicalSign);
	    	Matcher matcher2 = pattern.matcher(clinicalSign);
	    	if (clinicalSign.isEmpty() || matcher2.find()) {
	    		mainApp.showHomePage();
	    	}
	    	else {mainApp.showDisplaySideEffectPage();}
	    	
	    }
	    
	    public void handleButtonMedecine() {
	    	clinicalSign = CS.getText();
	    	//System.out.println(clinicalSign);
	    	Matcher matcher3 = pattern.matcher(clinicalSign);
	    	if (clinicalSign.isEmpty() || matcher3.find()) {
	    		mainApp.showHomePage();
	    	}
	    	else {mainApp.showDisplayMedecinePage();}
	    	
	    }
	    
	    public String getCS() {
	    	return clinicalSign;
	    }
	    
}
