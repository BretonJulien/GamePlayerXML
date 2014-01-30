package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import model.ModelLoadGame;
import model.ModelMainMenu;
import model.ModelSelectGame;
import model.ModelSituationNarration;
import view.ViewLoadGame;
import view.ViewMainMenu;
import view.ViewSituationNarration;

public class ControllerLoadGame {

	private ViewLoadGame viewLG;
	private ModelLoadGame modelLG;
	
	public ControllerLoadGame(ViewLoadGame viewLoadGame, ModelLoadGame modelLoadGame)
	{
		this.viewLG = viewLoadGame;
		this.modelLG = modelLoadGame;
		this.viewLG.addViewLoadGameListener(new LoadButtonListener(), new BackButtonListener());
	}
	
	public class LoadButtonListener implements ActionListener{

		@Override
		//appelé lorsque le bouton "Load" est cliqué
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String selectedCombobox = viewLG.getComboBoxSelected();
			
			//récupérer les champs score, vieheros et idsituationcourante
			ArrayList<String> listInfosFromGame = modelLG.getInfosFromSavedGame(selectedCombobox);
			
			//On récupère uniquement le nom du jeu
			String gameName = selectedCombobox.substring(0, selectedCombobox.indexOf(" "));
			
			ModelSelectGame msg = new ModelSelectGame();
			
			//On charge le jeu
			msg.initGame(gameName);
			
			//On lui met le scoreCourant de la sauvegarde
			msg.getJeu().setScoreCourant(Integer.parseInt(listInfosFromGame.get(1)));
			
			//On modifie la vie du héros pour remettre celle de la sauvegarde
			msg.getJeu().getHeros().setVie(Integer.parseInt(listInfosFromGame.get(2)));
			
			viewLG.dispose();
			ViewSituationNarration vsn = new ViewSituationNarration(msg.getJeu().getSituationById(listInfosFromGame.get(0)), msg.getJeu().getScoreCourant());
			ModelSituationNarration msn = new ModelSituationNarration(msg.getJeu());
			ControllerSituationNarration csn = new ControllerSituationNarration(msn, vsn);
			
		}
		
	}
	
	public class BackButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			viewLG.dispose();
			ViewMainMenu vmm = new ViewMainMenu();
			ModelMainMenu mmm = new ModelMainMenu();
			ControllerMainMenu cmm = new ControllerMainMenu(vmm, mmm);
		}
		
	}
}
