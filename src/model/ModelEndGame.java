package model;

public class ModelEndGame {

	private Jeu jeu;
	
	public ModelEndGame(Jeu jeu)
	{
		this.jeu = jeu;
	}
	
	public void saveScoreGame()
	{
		this.jeu.saveScoreGame();
	}
	
	public Jeu getJeu()
	{
		return this.jeu;
	}
}
