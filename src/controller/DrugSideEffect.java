package controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DrugSideEffect {
	
	private final StringProperty drug;
    private final StringProperty from;

    /**
     * Default constructor.
     */
    public DrugSideEffect() {
        this(null, null);
    }


	/**
     * Constructor with some initial data.
     * 
     * @param drug
     * @param from
     */
    public DrugSideEffect(String drug, String from) {
        this.drug = new SimpleStringProperty(drug);
        this.from = new SimpleStringProperty(from);
    }

    public String getDrug() {
        return drug.get();
    }

    public void setDrug(String drug) {
        this.drug.set(drug);
    }

    public StringProperty drugProperty() {
        return drug;
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
