package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Medecine {
	
	private final StringProperty medecine;
    private final StringProperty from;

    /**
     * Default constructor.
     */
    public Medecine() {
        this(null, null);
    }


	/**
     * Constructor with some initial data.
     * 
     * @param medecine
     * @param from
     */
    public Medecine(String medecine, String from) {
        this.medecine = new SimpleStringProperty(medecine);
        this.from = new SimpleStringProperty(from);
    }

    public String getMedecine() {
        return medecine.get();
    }

    public void setMedecine(String drug) {
        this.medecine.set(drug);
    }

    public StringProperty medecineProperty() {
        return medecine;
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
