package controller;

public class Couple {
	
	private String disease;
	private String dataBase;
	
	
	public Couple() {
		this.disease = null;
		this.dataBase = null;
	}
	
	public Couple(String dis, String db) {
		this.disease = dis;
		this.dataBase = db;
	}
	
	public Couple(Couple couple) {
		this.disease = couple.disease;
		this.dataBase = couple.dataBase;
	}
	
	public String getDataBase() {
		return this.dataBase;
	}
	
	public String getDisease() {
		return this.disease;
	}
	
	public String toString() {
		if (this.disease != null & this.dataBase != null) {
			return (this.disease + " = " + this.dataBase);
		}
		else {return null;}
	}
	
	public boolean equals(Couple couple) {
		if(this.getDisease().equals(couple.getDisease()) && this.getDataBase().equals(couple.getDataBase())) {
			return true;
		}
		return false;
	}
	
	public boolean equalsDisease(Couple couple) {
		if(this.getDisease().equals(couple.getDisease())) {
			return true;
		}
		return false;
	}
	
	public boolean equalsDataBase(Couple couple) {
		if(this.getDataBase().equals(couple.getDataBase())) {
			return true;
		}
		return false;
	}
	


}
