package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import model.ModelClassements;
import model.ModelMainMenu;
import model.ModelSelectGame2;
import model.User;
import view.ViewClassements;
import view.ViewMainMenu;
import view.ViewSelectGame;
import view.ViewSelectGame2;

public class ControllerSelectGame2 {

	private ViewSelectGame2 vsg;
	private ModelSelectGame2 msg;
	private String pathFile;
	
	public ControllerSelectGame2(ViewSelectGame2 vsg, ModelSelectGame2 msg)
	{
		this.vsg = vsg;
		this.msg = msg;
		this.vsg.addViewListener(new OkButtonListener(), new BackButtonListener());
		
		
		//On charge le fichier properties pour avoir la base du chemin absolu
				//A changer lorsqu'on change de poste
				Properties prop = new Properties();
				InputStream input = null;
				String pathFile = null;
				try {
			 		input = new FileInputStream("prop.properties");
			 		prop.load(input);
			 		this.pathFile=prop.getProperty("PATH");
			 
				} catch (IOException ex) {
					ex.printStackTrace();
				}
	}
	
	public class OkButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("Bouton ok cliqué");
			
			//récupérer le contenu du combobox
			String gameSelected = vsg.getSelectedComboBox();
			
			//récupérer les entetes et la data du table et la liste des jeux
			ModelMainMenu modelMM = new ModelMainMenu();
						
			String entetes[] = {"Joueur", "Score"};
						
			//par défaut, on récupère le premier jeu
			String pathToBestScores = modelMM.getPathFromGame(new File(pathFile+"gameGeneratorMVC/Content/xml/Members"), gameSelected);
			String[][] data = modelMM.getUserAndScoreFromBestScores(pathToBestScores);
			
						
			vsg.dispose();
			ViewClassements vc = new ViewClassements(entetes, data);
			ModelClassements mc = new ModelClassements();
			ControllerClassements cc = new ControllerClassements(vc, mc);
		}
		
	}
	
	public class BackButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			System.out.println("Bouton back cliqué");
			vsg.dispose();
			ViewMainMenu vmm = new ViewMainMenu();
			ModelMainMenu mmm = new ModelMainMenu();
			ControllerMainMenu cmm = new ControllerMainMenu(vmm, mmm);
		}
		
	}
}
