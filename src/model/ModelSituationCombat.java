package model;

import java.util.Random;

public class ModelSituationCombat {

	private Jeu jeu;
	
	public ModelSituationCombat(Jeu jeu)
	{
		this.jeu = jeu;
	}

	public Jeu getJeu() {
		return jeu;
	}

	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
	}
	
	public void combat(Personnage heros, Personnage ennemi)
	{
		Random rand = new Random();
		int randomDefensePercentage = rand.nextInt(50)+1;
		int defense = heros.getDef() - (heros.getDef()*randomDefensePercentage)/100;
		int degats = ennemi.getAtk();
		
		int dommage = degats-defense;
		if(dommage>0)
		{
			heros.setVie(heros.getVie()-degats);
		}
		
		
		randomDefensePercentage = rand.nextInt(50)+1;
		defense = ennemi.getDef() - (ennemi.getDef()*randomDefensePercentage)/100;
		degats = heros.getAtk();
		dommage = degats - defense;
		
		if(dommage>0)
		{
			ennemi.setVie(ennemi.getVie()-dommage);
		}	
		
	}
}
