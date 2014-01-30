package model;

public class ReponseOld {

	String code;
	String label;
	String idSituationSuivante;
	
	public ReponseOld(String code, String label, String idSituationSuivante)
	{
		this.code = code;
		this.label = label;
		this.idSituationSuivante = idSituationSuivante;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getIdSituationSuivante() {
		return idSituationSuivante;
	}

	public void setIdSituationSuivante(String idSituationSuivante) {
		this.idSituationSuivante = idSituationSuivante;
	}
}
