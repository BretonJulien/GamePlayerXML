package model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ModelMainMenu {

	public ModelMainMenu()
	{
		
	}
	
	//retourne la liste des jeux en parcourant l'arborescence des membres inscrits
	public ArrayList<String> getGamesList ( File repertoire ) {
        
		//HashMap<String, String> mapFileGameGamePath = new HashMap<String, String>();
		ArrayList<String> gamesList = new ArrayList<String>();
		
        if ( repertoire.isDirectory ( ) ) {
                File[] list = repertoire.listFiles();
                if (list != null){
                	//parcourt la liste des members
	                for ( int i = 0; i < list.length; i++)
	                {
	                	//Si l'élément du répertoire members est un répertoire
	                	if(list[i].isDirectory())
	                	{
	                		//liste des répertoires utilisateurs
	                		File[] underList = list[i].listFiles();
	                		if(underList!=null)
	                		{
	                			//parcourt la liste des éléments du répertoire courant de l'utilisateur
	                			for(int j=0;j<underList.length;j++)
	                			{
	                				if(!underList[j].getName().contains("xml"))
	                				{
	                					gamesList.add(underList[j].getName());
//	                					System.out.println(underList[j].getName());
//	                					System.out.println(underList[j].getPath());
	                					//mapFileGameGamePath.put(underList[j].getName(), underList[j].getPath());
	                					//System.out.println(underList[j].getPath().replace(underList[j].getName(), ""));
	                				}
	                			}
	                		}
	                	}
	                } 
                } else {
                	System.err.println(repertoire + " : Erreur de lecture.");
                }
        } 
        return gamesList;
	}
	
	//Retourne la liste des jeux concaténée avec la date de la sauvegarde
	public ArrayList<String> getAllSavedGamesNames(String path)
	{
		ArrayList<String> savedGamesNamesList = new ArrayList<String>();
		
		LoadXmlFile lxf = new LoadXmlFile(path, "savedGames.xml");
		//on récupère la liste des noeuds de save
		NodeList saveNodes = lxf.getDoc().getElementsByTagName("save");
		
		for(int i=0; i<saveNodes.getLength();i++)
		{
			Node node = (Element)saveNodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element elem = (Element) node;
				String saveName = elem.getElementsByTagName("jeu").item(0).getTextContent()+" "+elem.getElementsByTagName("date").item(0).getTextContent();
				savedGamesNamesList.add(saveName);
			}
		}
		
		return savedGamesNamesList;
	}
	
	//retourne l'ensemble des scores (joueur+score) pour un jeu en paramètres
	public String getPathFromGame(File repertoire, String game)
	{
		String pathToBestScores = null;
		
		 if ( repertoire.isDirectory ( ) ) {
             File[] list = repertoire.listFiles();
             if (list != null){
             	//parcourt la liste des members
	                for ( int i = 0; i < list.length; i++)
	                {
	                	//Si le member est un répertoire
	                	if(list[i].isDirectory())
	                	{
	                		//liste des fichiers du répertoire members 
	                		File[] underList = list[i].listFiles();
	                		if(underList!=null)
	                		{
	                			for(int j=0;j<underList.length;j++)
	                			{
	                				if(underList[j].getName().equals(game))
	                				{
	                					pathToBestScores = underList[j].getAbsolutePath()+"/";
	                				}
	                			}
	                		}
	                	}
	                } 
             } else {
             	System.err.println(repertoire + " : Erreur de lecture.");
             }
		 }
		 return pathToBestScores;
	}
	
	public String[][] getUserAndScoreFromBestScores(String path)
	{
		LoadXmlFile lxf = new LoadXmlFile(path, "bestScores.xml");
		NodeList score = lxf.getDoc().getElementsByTagName("score");
				
		//On sait que le noeud score ne contient que deux enfants
		String[][] tab = new String[score.getLength()][2];
		
		for(int i=0; i<score.getLength();i++)
		{
			Node node = score.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element elem = (Element) node;
				String joueur = elem.getElementsByTagName("joueur").item(0).getTextContent();
				String nbPoints = elem.getElementsByTagName("points").item(0).getTextContent();
				tab[i][0] = joueur;
				tab[i][1] = nbPoints;
			}
		}
		System.out.println("ok");
		return tab;
	}
}
