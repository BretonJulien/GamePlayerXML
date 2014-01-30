package model;

public class ReponseNarration extends Reponse{

	private int nbPoints;
	private String idSituationNext;
	
	public ReponseNarration(String code, int nbPoints, String label, String idSituationNext) {
		super(code, label);
		this.nbPoints = nbPoints;
		this.idSituationNext = idSituationNext;
	}

	public int getNbPoints() {
		return nbPoints;
	}

	public void setNbPoints(int nbPoints) {
		this.nbPoints = nbPoints;
	}

	public String getIdSituationNext() {
		return idSituationNext;
	}

	public void setIdSituationNext(String idSituationNext) {
		this.idSituationNext = idSituationNext;
	}
}
