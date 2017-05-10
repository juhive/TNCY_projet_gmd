package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Disease {
	
	private final StringProperty disease;
    private final StringProperty from;

    /**
     * Default constructor.
     */
    public Disease() {
        this(null, null);
    }


	/**
     * Constructor with some initial data.
     * 
     * @param firstName
     * @param lastName
     */
    public Disease(String disease, String from) {
        this.disease = new SimpleStringProperty(disease);
        this.from = new SimpleStringProperty(from);
    }

    public String getDisease() {
        return disease.get();
    }

    public void setDisease(String disease) {
        this.disease.set(disease);
    }

    public StringProperty diseaseProperty() {
        return disease;
    }

    public String getFrom() {
        return from.get();
    }

    public void setFrom(String from) {
        this.from.set(from);
    }

    public StringProperty fromProperty() {
        return from;
    }
	
}
