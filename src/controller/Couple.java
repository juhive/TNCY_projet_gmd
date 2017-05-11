package controller;

public class Couple {
	
	private String disease;
	private String dataBase;
	
	
	public Couple() {
		this.disease = "";
		this.dataBase = "";
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
		return (this.disease + " = " + this.dataBase);
	}

}
