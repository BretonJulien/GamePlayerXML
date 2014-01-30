package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import model.ModelMainMenu;
import model.ModelSelectGame;
import model.ModelSituationNarration;
import view.ViewMainMenu;
import view.ViewSelectGame;
import view.ViewSituationNarration;

public class ControllerSelectGame {

	private ModelSelectGame modelSG;
	private ViewSelectGame viewSG;
	
	public ControllerSelectGame(ModelSelectGame modelSG, ViewSelectGame viewSG)
	{
		this.modelSG = modelSG;
		this.viewSG = viewSG;
		
		this.viewSG.addViewListener(new okButtonListener(), new PreviousButtonListener());
	}
	
	public class okButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			//action lorsque le bouton OK sera cliqué
			String selectedComboBoxItem = viewSG.getSelectedComboBox();
			
			viewSG.dispose();
			//On charge le jeu qui correspond au jeu sélectionné dans la combo box
			modelSG.initGame(selectedComboBoxItem);
			ViewSituationNarration vsn = new ViewSituationNarration(modelSG.getJeu().getFirstSituation(), modelSG.getJeu().getScoreCourant());
			ModelSituationNarration msn = new ModelSituationNarration(modelSG.getJeu());
			ControllerSituationNarration csn = new ControllerSituationNarration(msn, vsn);
		}
		
	}
	
	public class PreviousButtonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e) {
			//action lorsque le bouton OK sera cliqué
			
			viewSG.dispose();
			ViewMainMenu vmm = new ViewMainMenu();
			ModelMainMenu mmm = new ModelMainMenu();
			ControllerMainMenu cmm = new ControllerMainMenu(vmm, mmm);
		}
		
	}
}
