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
import model.ModelLoadGame;
import model.ModelMainMenu;
import model.ModelSelectGame;
import model.ModelSelectGame2;
import model.User;
import view.ViewClassements;
import view.ViewLoadGame;
import view.ViewMainMenu;
import view.ViewSelectGame;
import view.ViewSelectGame2;

public class ControllerMainMenu {

	private ViewMainMenu viewMM;
	private ModelMainMenu modelMM;
	private String pathFile;
	
	public ControllerMainMenu(ViewMainMenu viewMM, ModelMainMenu modelMM)
	{
		this.viewMM = viewMM;
		this.modelMM = modelMM;
		
		this.viewMM.addViewMainMenuListener(new PlayListener(), new LoadListener(), new ClassementListener());
		
		
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
	
	public class PlayListener implements ActionListener{

		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			viewMM.dispose();
			
			//On récupère la liste des jeux existants
			ArrayList<String> gamesList = modelMM.getGamesList(new File(pathFile+"gameGeneratorMVC/Content/xml/Members"));
			
			//on convertit l'arrayList en Array simple
			String[] gamesArray = gamesList.toArray(new String[gamesList.size()]);
			
			ViewSelectGame vsg = new ViewSelectGame(gamesArray);
			ModelSelectGame msg = new ModelSelectGame();
			ControllerSelectGame csg = new ControllerSelectGame(msg, vsg);
		}
		
	}
	
	public class LoadListener implements ActionListener{

		@Override
		//Méthode appelée lorsqu'on clique sur "reprendre une partie"
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
						
			User user = User.getInstance();
			ArrayList<String> listSavedGames = modelMM.getAllSavedGamesNames(pathFile+"gameGeneratorMVC/Content/xml/Members/"+user.getLogin()+"/");
			
			String[] arraySavedGames = listSavedGames.toArray(new String[listSavedGames.size()]);
			viewMM.dispose();
			ViewLoadGame vlg = new ViewLoadGame(arraySavedGames);
			ModelLoadGame mlg = new ModelLoadGame();
			ControllerLoadGame clg = new ControllerLoadGame(vlg, mlg);
		}
		
	}
	
	public class ClassementListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.out.println("Clic bouton classements");
			
			ArrayList<String> gamesList = modelMM.getGamesList(new File(pathFile+"gameGeneratorMVC/Content/xml/Members"));
			String[] gamesArray = gamesList.toArray(new String[gamesList.size()]);
			
			viewMM.dispose();
			ViewSelectGame2 vsg2 = new ViewSelectGame2(gamesArray);
			ModelSelectGame2 msg2 = new ModelSelectGame2();
			ControllerSelectGame2 cc = new ControllerSelectGame2(vsg2, msg2);
			
			
		}
		
	}
}
