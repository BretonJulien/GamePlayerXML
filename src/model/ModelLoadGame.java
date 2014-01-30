package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ModelLoadGame {

	private String path;
	
	public ModelLoadGame()
	{
		//Chargement du fichier properties pour récupérer le chemin qui pointe vers gameGeneratorMVC
				Properties prop = new Properties();
				InputStream input = null;
										
				try {
			 		input = new FileInputStream("prop.properties");
			 		prop.load(input);
			 		this.path=prop.getProperty("PATH");
						 
				} catch (IOException ex) {
					ex.printStackTrace();
				}
	}
	
	//retourne une liste avec l'idsituationcourante, le score
	//et la vie du héros en fonction du nom de la sauvegarde
	public ArrayList<String> getInfosFromSavedGame(String gameName)
	{
		ArrayList<String> listInfosFromGame = new ArrayList<String>();
		
		User user = User.getInstance();
		String login = user.getLogin();
		
		LoadXmlFile lxf = new LoadXmlFile(path+"gameGeneratorMVC/Content/xml/Members/"+login+"/", "savedGames.xml");
		//on récupère la liste des noeuds de save
		NodeList saveNodes = lxf.getDoc().getElementsByTagName("save");
		
		for(int i=0; i<saveNodes.getLength();i++)
		{
			Node node = (Element)saveNodes.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE)
			{
				Element elem = (Element) node;
				String saveName = elem.getElementsByTagName("jeu").item(0).getTextContent()+" "+elem.getElementsByTagName("date").item(0).getTextContent();
				if(saveName.equals(gameName))
				{
					listInfosFromGame.add(elem.getElementsByTagName("idSituationCourante").item(0).getTextContent());
					listInfosFromGame.add(elem.getElementsByTagName("score").item(0).getTextContent());
					listInfosFromGame.add(elem.getElementsByTagName("vieHeros").item(0).getTextContent());
				}
				
			}
		}
		
		return listInfosFromGame;
	}
}
