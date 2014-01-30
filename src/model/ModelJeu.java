package model;

import java.util.HashMap;

public class ModelJeu {

	private Jeu jeu;
	
	public void creerJeu(String nomJeu, int scoreCourant, HashMap<String, Situation> situations, Personnage heros)
	{
		System.out.println("Création du jeu.");
		//this.jeu =new Jeu(nomJeu, scoreCourant, situations, heros);
	}
	
	public void sauverJeu()
	{
		
	}
	
	public void sauverAutoJeu()
	{
		
	}
	
	public void getClassementJeu()
	{
		
	}
	
	public void chargerJeu()
	{
		
	}
	
	public String getListGames()
	{
		return "Le seigneur des anneaux";
	}
	
	public Jeu getJeu() {
		return jeu;
	}

	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
	}
}
