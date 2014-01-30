package model;

import java.util.ArrayList;
import java.util.HashMap;

public class SituationCombat extends Situation{

	private Personnage ennemi;
	private ArrayList<Reponse> reponses;
	
	public SituationCombat(String id, String titre, String exposition,
			String question, ArrayList<Reponse> reponses, Personnage perso)
	{
		super(id, titre, exposition, question);
		this.reponses = reponses;
		this.ennemi = perso;
	}

	public Personnage getEnnemi() {
		return ennemi;
	}

	public void setEnnemi(Personnage ennemi) {
		this.ennemi = ennemi;
	}

	public ArrayList<Reponse> getReponses() {
		return reponses;
	}

	public void setReponses(ArrayList<Reponse> reponses) {
		this.reponses = reponses;
	}

}
