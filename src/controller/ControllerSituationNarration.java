package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JOptionPane;



import view.ViewPopUp;
import view.ViewEndGame;
import view.ViewMainMenu;
import view.ViewSituationNarration;
import view.ViewSituationCombat;
import model.ModelEndGame;
import model.ModelMainMenu;
import model.ModelSituationCombat;
import model.ModelSituationNarration;
import model.Situation;
import model.SituationNarration;

public class ControllerSituationNarration {

	private ModelSituationNarration modelSN;
	private ViewSituationNarration viewSN;
	
	public ControllerSituationNarration(ModelSituationNarration modelSN, ViewSituationNarration viewSN)
	{
		this.modelSN = modelSN;
		this.viewSN = viewSN;
		this.viewSN.addViewSituationNarrationListener(new OkButtonListener(), new QuitButtonListener(), new RestartButtonListener(), new SaveButtonListener());
	}
	
	public class OkButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			//appelé quand clic sur le bouton "OK"
			String radioSelectedLabel = viewSN.getRadioButtonSelected();
			System.out.println("Radio sélectionné:"+radioSelectedLabel);
			
						
			SituationNarration situNarration = viewSN.getSituationNarration();
			String idSituationSuivante = null;
			int nbPointsSituationSuivante = 0;
			
			for(int i=0; i<situNarration.getReponses().size();i++)
			{
				//pour chaque réponse de la situation, on vérifie si l'une d'elle vaut le label sélectionné par user
				if(situNarration.getReponses().get(i).getLabel().equals(radioSelectedLabel))
				{
					//on récupère l'id de la situation suivante
					idSituationSuivante = situNarration.getReponses().get(i).getIdSituationNext();
					nbPointsSituationSuivante = situNarration.getReponses().get(i).getNbPoints();
					
					modelSN.getJeu().setScoreCourant(modelSN.getJeu().getScoreCourant()+nbPointsSituationSuivante);
				}
			}
			
			//On récupère la situation suivante à partir de son id
			Situation nextSituation = modelSN.getJeu().getSituationById(idSituationSuivante);
			
			//Si on a une situation suivante
			if(nextSituation!=null)
			{
				//On teste s'il s'agit d'une situation de combat ou de Narration
				if(nextSituation.getClass().getSimpleName().equals("SituationNarration"))
				{
					//Si narration
					//On set les champs concernés (exposition, question, réponses, score)
					System.out.println("Situation Narration");
					viewSN.dispose();
					ViewSituationNarration vsn = new ViewSituationNarration(nextSituation, modelSN.getJeu().getScoreCourant());
					ModelSituationNarration msn = new ModelSituationNarration(modelSN.getJeu());
					ControllerSituationNarration csn = new ControllerSituationNarration(msn, vsn);
				}
				else
				{
					//Si combat
					//on instancie la vue, le controller et le modèle
					System.out.println("Situation de combat");
					viewSN.dispose();
					System.out.println(modelSN.getJeu().getScoreCourant());
					ViewSituationCombat vsc = new ViewSituationCombat(nextSituation, modelSN.getJeu().getScoreCourant(), modelSN.getJeu().getHeros().getVie());
					ModelSituationCombat msc = new ModelSituationCombat(modelSN.getJeu());
					ControllerSituationCombat csc = new ControllerSituationCombat(vsc, msc);
				}
			}
			else
			{
				System.out.println("Fin du jeu.");
				viewSN.dispose();
				ViewEndGame veg = new ViewEndGame(modelSN.getJeu().getScoreCourant(), modelSN.getJeu().getHeros().getVie());
				ModelEndGame meg = new ModelEndGame(modelSN.getJeu());
				ControllerEndGame ceg = new ControllerEndGame(veg,meg);
			}				
		}
	}
	
	public class QuitButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			int selectedOption = JOptionPane.showConfirmDialog(null, 
                    "Voulez-vous quitter le jeu?", 
                    "GamePlayerXML", 
                    JOptionPane.YES_NO_OPTION);
			if(selectedOption==0)
			{
				// appelé quand clic sur le bouton "Quit"
				viewSN.dispose();
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
                    "Etes-vous sûr de vouloir recommencer la partie?", 
                    "GamePlayerXML", 
                    JOptionPane.YES_NO_OPTION);
			if(selectedOption==0)
			{
				viewSN.dispose();
				modelSN.getJeu().restartGame();
				ViewSituationNarration vsn = new ViewSituationNarration(modelSN.getJeu().getFirstSituation(), modelSN.getJeu().getScoreCourant());
				ModelSituationNarration msn = new ModelSituationNarration(modelSN.getJeu());
				ControllerSituationNarration csn = new ControllerSituationNarration(msn, vsn);
			}
		}
	}

	public class SaveButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// appelé quand clic sur le bouton "Save"
			viewSN.dispose();
			
			JOptionPane.showMessageDialog( null, "Partie sauvegardée!", 
			      "GamePlayerXML", JOptionPane.INFORMATION_MESSAGE);
			
			modelSN.saveGame(viewSN.getSituationNarration());
			
			ViewMainMenu vmm = new ViewMainMenu();
			ModelMainMenu mmm = new ModelMainMenu();
			ControllerMainMenu cmm = new ControllerMainMenu(vmm, mmm);
		}
	}
}
