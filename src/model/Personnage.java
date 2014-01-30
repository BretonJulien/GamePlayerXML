package model;

public class Personnage {

	private String nom;
	private int vieDefaut;
	private int vie;
	private int atk;
	private int def;
	private int initiative;
	private String type;
	
	public Personnage()
	{
		
	}
	
	public Personnage(String nom, int vie, int atk, int def, int initiative, String type)
	{
		this.nom = nom;
		this.vieDefaut = vie;
		this.vie = vie;
		this.atk = atk;
		this.def = def;
		this.initiative = initiative;
		this.type = type;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public int getVie() {
		return vie;
	}
	
	public void setVie(int vie) {
		this.vie = vie;
	}
	public int getVieDefaut() {
		return vieDefaut;
	}

	public void setVieDefaut(int vieDefaut) {
		this.vieDefaut = vieDefaut;
	}

	public int getAtk() {
		return atk;
	}
	public void setAtk(int atk) {
		this.atk = atk;
	}
	public int getDef() {
		return def;
	}
	public void setDef(int def) {
		this.def = def;
	}
	public int getInitiative() {
		return initiative;
	}
	public void setInitiative(int initiative) {
		this.initiative = initiative;
	}
}
