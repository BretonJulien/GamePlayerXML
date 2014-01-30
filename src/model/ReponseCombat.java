package model;

public class ReponseCombat extends Reponse{

	private int nbPointsVictoire;
	private int nbPointsDefaite;
	private String idSituationVictoire;
	private String idSituationDefaite;
	
	public ReponseCombat(String code, String label, int nbPointsVictoire, int nbPointsDefaite, String idSituationVictoire, String idSituationDefaite) {
		super(code, label);
		this.nbPointsVictoire = nbPointsVictoire;
		this.nbPointsDefaite = nbPointsDefaite;
		this.idSituationVictoire = idSituationVictoire;
		this.idSituationDefaite = idSituationDefaite;
	}

	public int getNbPointsVictoire() {
		return nbPointsVictoire;
	}

	public void setNbPointsVictoire(int nbPointsVictoire) {
		this.nbPointsVictoire = nbPointsVictoire;
	}

	public int getNbPointsDefaite() {
		return nbPointsDefaite;
	}

	public void setNbPointsDefaite(int nbPointsDefaite) {
		this.nbPointsDefaite = nbPointsDefaite;
	}

	public String getIdSituationVictoire() {
		return idSituationVictoire;
	}

	public void setIdSituationVictoire(String idSituationVictoire) {
		this.idSituationVictoire = idSituationVictoire;
	}

	public String getIdSituationDefaite() {
		return idSituationDefaite;
	}

	public void setIdSituationDefaite(String idSituationDefaite) {
		this.idSituationDefaite = idSituationDefaite;
	}

}
