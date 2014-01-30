package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import model.ModelEndGame;
import model.ModelMainMenu;
import model.ModelSituationNarration;

import view.ViewEndGame;
import view.ViewMainMenu;
import view.ViewSituationNarration;

public class ControllerEndGame {

	private ViewEndGame viewEG;
	private ModelEndGame modelEG;
	
	public ControllerEndGame(ViewEndGame veg, ModelEndGame meg)
	{
		this.viewEG = veg;
		this.modelEG = meg;
		this.viewEG.addViewEndGameActionListener(new OkButtonListener(), new RestartButtonListener());
	}
	
	public class OkButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("Bouton OK cliqué");
			modelEG.getJeu().saveScoreGame();
			
			viewEG.dispose();
			ViewMainMenu vmm = new ViewMainMenu();
			ModelMainMenu mmm = new ModelMainMenu();
			ControllerMainMenu cmm = new ControllerMainMenu(vmm, mmm);
		}
		
	}
	
	public class RestartButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int selectedOption = JOptionPane.showConfirmDialog(null, 
                    "Etes-vous sûr de vouloir recommencer le jeu?", 
                    "GamePlayerXML", 
                    JOptionPane.YES_NO_OPTION);
			if(selectedOption==0)
			{
				viewEG.dispose();
				modelEG.getJeu().restartGame();
				//Ne pas oubllier que la première situation du jeu est toujours de type narration
				ViewSituationNarration vsn = new ViewSituationNarration(modelEG.getJeu().getFirstSituation(), modelEG.getJeu().getScoreCourant());
				ModelSituationNarration msn = new ModelSituationNarration(modelEG.getJeu());
				ControllerSituationNarration csn = new ControllerSituationNarration(msn, vsn);
			}
			
		}
		
	}
}
