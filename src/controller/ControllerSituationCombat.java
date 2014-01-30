package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.ModelEndGame;
import model.ModelMainMenu;
import model.ModelSituationCombat;
import model.ModelSituationNarration;
import model.Personnage;
import model.ReponseCombat;
import model.ReponseNarration;
import model.Situation;
import model.SituationCombat;
import model.SituationNarration;
import view.ViewEndGame;
import view.ViewMainMenu;
import view.ViewSituationCombat;
import view.ViewSituationNarration;

public class ControllerSituationCombat {

	private ViewSituationCombat viewSC;
	private ModelSituationCombat modelSC;
	
	public ControllerSituationCombat(ViewSituationCombat viewSC, ModelSituationCombat modelSC)
	{
		this.viewSC = viewSC;
		this.modelSC = modelSC;
		this.viewSC.addViewSituationCombatListener(new OkButtonListener(), new QuitButtonListener(), new RestartButtonListener());
	}
	
	public class OkButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if(viewSC.getRadioButtonSelected().equals("Attaquer"))
			{
				SituationCombat situationCombat = viewSC.getSituationCombat();
				String idSituationSuivante = null;
				ReponseCombat reponseSituationCombat = null;
				
				modelSC.combat(modelSC.getJeu().getHeros(), viewSC.getSituationCombat().getEnnemi());
				
				//si la vie du héros atteint 0
				if(modelSC.getJeu().getHeros().getVie()<=0)
				{
					reponseSituationCombat = (ReponseCombat)situationCombat.getReponses().get(0);
					idSituationSuivante = reponseSituationCombat.getIdSituationDefaite();
					
					modelSC.getJeu().setScoreCourant(modelSC.getJeu().getScoreCourant()+reponseSituationCombat.getNbPointsDefaite());
					
					//on récupère la prochaine situation avec son id
					Situation nextSituation = modelSC.getJeu().getSituationById(idSituationSuivante);
					
					if(nextSituation!=null)
					{
						//On teste s'il s'agit d'une situation de combat ou de Narration
//						if(nextSituation.getClass().getSimpleName().equals("SituationNarration"))
//						{
//							viewSC.dispose();
//							ViewSituationNarration vsn = new ViewSituationNarration(nextSituation, modelSC.getJeu().getScoreCourant());
//							ModelSituationNarration msn = new ModelSituationNarration(modelSC.getJeu());
//							ControllerSituationNarration csn = new ControllerSituationNarration(msn, vsn);
//						}
//						else
//						{
//							viewSC.dispose();
//							ViewSituationCombat vsn = new ViewSituationCombat(nextSituation, modelSC.getJeu().getScoreCourant(), modelSC.getJeu().getHeros().getVie());
//							ModelSituationCombat msn = new ModelSituationCombat(modelSC.getJeu());
//							ControllerSituationCombat csn = new ControllerSituationCombat(vsn, msn);
//						}
					}
					else
					{
						viewSC.dispose();
						ViewEndGame veg = new ViewEndGame(modelSC.getJeu().getScoreCourant(), modelSC.getJeu().getHeros().getVie());
						ModelEndGame meg = new ModelEndGame(modelSC.getJeu());
						ControllerEndGame ceg = new ControllerEndGame(veg,meg);
					}
					
					
				}
				else if(viewSC.getSituationCombat().getEnnemi().getVie()<=0)
				{
					reponseSituationCombat = (ReponseCombat)situationCombat.getReponses().get(0);
					idSituationSuivante = reponseSituationCombat.getIdSituationVictoire();
					
					//On met à jour le score courant du jeu
					modelSC.getJeu().setScoreCourant(modelSC.getJeu().getScoreCourant()+reponseSituationCombat.getNbPointsVictoire());
					
					//on récupère la prochaine situation à l'aide de son id
					Situation nextSituation = modelSC.getJeu().getSituationById(idSituationSuivante);
					
					//On teste s'il s'agit d'une situation de combat ou de Narration
					if(nextSituation.getClass().getSimpleName().equals("SituationNarration"))
					{
						viewSC.dispose();
						ViewSituationNarration vsn = new ViewSituationNarration(nextSituation, modelSC.getJeu().getScoreCourant());
						ModelSituationNarration msn = new ModelSituationNarration(modelSC.getJeu());
						ControllerSituationNarration csn = new ControllerSituationNarration(msn, vsn);
					}
					else
					{
						viewSC.dispose();
						ViewSituationCombat vsn = new ViewSituationCombat(nextSituation, modelSC.getJeu().getScoreCourant(), modelSC.getJeu().getHeros().getVie());
						ModelSituationCombat msn = new ModelSituationCombat(modelSC.getJeu());
						ControllerSituationCombat csn = new ControllerSituationCombat(vsn, msn);
					}
				}
				
				viewSC.setCurrentHeroLifeLabel(Integer.toString(modelSC.getJeu().getHeros().getVie()));
				viewSC.setCurrentEnemyLifeLabel(Integer.toString(viewSC.getSituationCombat().getEnnemi().getVie()));
			}
			//Cas de fuite
			else
			{
				SituationCombat situationCombat = viewSC.getSituationCombat();
				String idSituationSuivante = null;
				ReponseNarration situationNarrationFuite = null;
				
				for(int i=0; i<situationCombat.getReponses().size();i++)
				{
					if(situationCombat.getReponses().get(i).getLabel().equals("Prendre la fuite"))
					{
						//Par défaut situationCombat contient des réponses générales (narration ou combat)
						//il n'y a donc pas de méthode pour récupérer l'id de la réponse suivante
						//puisqu'on ne sait pas de quel type elle est
						//Dans le cas d'une situation de combat, on a 2 réponses possibles: Attaquer ou prendre la fuite.
						//"Attaquer" contient deux sous-éléments, une réponse de combat et une de narration
						//"Prendre la fuite" contient une réponse de narration, c'est elle qui nous intéresse ici
						situationNarrationFuite = (ReponseNarration)situationCombat.getReponses().get(1);
						idSituationSuivante = situationNarrationFuite.getIdSituationNext();
					}
				}
						//on met à jour le nombre de points du jeu
				System.out.println("A:"+modelSC.getJeu().getScoreCourant());
				System.out.println("B:"+situationNarrationFuite.getNbPoints());
						modelSC.getJeu().setScoreCourant(modelSC.getJeu().getScoreCourant()+situationNarrationFuite.getNbPoints());
						
						//on remet la vie du héros au max pour lui redonner une chance au prochain combat
						modelSC.getJeu().getHeros().setVie(modelSC.getJeu().getHeros().getVieDefaut());
						
						//On récupère la situation suivante à partir de son id
						Situation nextSituation = modelSC.getJeu().getSituationById(idSituationSuivante);
						
						//On teste s'il s'agit d'une situation de combat ou de Narration
						if(nextSituation.getClass().getSimpleName().equals("SituationNarration"))
						{
							//Si narration
							//On set les champs concernés (exposition, question, réponses, score)
							viewSC.dispose();
							System.out.println("Situation Narration");
							ViewSituationNarration vsn = new ViewSituationNarration(modelSC.getJeu().getFirstSituation(), modelSC.getJeu().getScoreCourant());
							ModelSituationNarration msn = new ModelSituationNarration(modelSC.getJeu());
							ControllerSituationNarration csn = new ControllerSituationNarration(msn, vsn);
						}
						else
						{
							//Si combat
							//on instancie la vue, le controller et le modèle
							System.out.println("Situation de combat new");
//							viewSN.dispose();
//							ViewSituationCombat vsc = new ViewSituationCombat(nextSituation, modelSN.getJeu().getScoreCourant(), modelSN.getJeu().getHeros().getVie());
//							ModelSituationCombat msc = new ModelSituationCombat(modelSN.getJeu());
//							ControllerSituationCombat csc = new ControllerSituationCombat(vsc, msc);
						}
					
				
			}
			
			
		}
		
		
	}
	
	public class QuitButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// appelé quand clic sur le bouton "Quit"
			int selectedOption = JOptionPane.showConfirmDialog(null, 
                    "Voulez-vous quitter le jeu?", 
                    "GamePlayerXML", 
                    JOptionPane.YES_NO_OPTION);
			if(selectedOption==0)
			{
				viewSC.dispose();
				ViewMainMenu vmm = new ViewMainMenu();
				ModelMainMenu mmm = new ModelMainMenu();
				ControllerMainMenu cmm = new ControllerMainMenu(vmm, mmm);
			}
		}
		
	}

	public class RestartButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// appelé quand clic sur le bouton "Restart"
			System.out.println("clic bouton Restart");
			int selectedOption = JOptionPane.showConfirmDialog(null, 
                    "Etes-vous sûr de vouloir recommencer le jeu?", 
                    "GamePlayerXML", 
                    JOptionPane.YES_NO_OPTION);
			if(selectedOption==0)
			{
				viewSC.getSituationCombat().getEnnemi().setVieDefaut(viewSC.getSituationCombat().getEnnemi().getVieDefaut());
				viewSC.dispose();
				modelSC.getJeu().restartGame();
				System.out.println(modelSC.getJeu().getScoreCourant());
				//Ne pas oubllier que la première situation du jeu est toujours de type narration
				ViewSituationNarration vsn = new ViewSituationNarration(modelSC.getJeu().getFirstSituation(), modelSC.getJeu().getScoreCourant());
				ModelSituationNarration msn = new ModelSituationNarration(modelSC.getJeu());
				ControllerSituationNarration csn = new ControllerSituationNarration(msn, vsn);
			}
		}
	
	}
}
