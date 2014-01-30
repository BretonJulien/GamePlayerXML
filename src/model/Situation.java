package model;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Situation {

	protected String id;
	protected String titre;
	protected String exposition;
	protected String question;
	
	//protected HashMap<Integer, String> reponses;
	//A déplacer dans situationNarration et situationCombat
	//protected ArrayList<Reponse> reponses;
	
	
	
	public Situation(String id, String titre, String exposition,
			String question) {
		super();
		this.id = id;
		this.titre = titre;
		this.exposition = exposition;
		this.question = question;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getExposition() {
		return exposition;
	}
	public void setExposition(String exposition) {
		this.exposition = exposition;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
}
