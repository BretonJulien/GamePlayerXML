package model;

import java.util.ArrayList;
import java.util.HashMap;

public class SituationNarration extends Situation {

	private ArrayList<ReponseNarration> reponses;
	
	public ArrayList<ReponseNarration> getReponses() {
		return reponses;
	}

	public void setReponses(ArrayList<ReponseNarration> reponses) {
		this.reponses = reponses;
	}

	public SituationNarration(String id, String titre, String exposition,
			String question, ArrayList<Reponse> reponses)
	{
		super(id, titre, exposition, question);
		// TODO Auto-generated constructor stub
	}
	
	
}
