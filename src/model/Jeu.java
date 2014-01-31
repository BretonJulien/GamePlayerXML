package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.Set;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Jeu {

	private String nomJeu;
	private int scoreCourant;
	//private HashMap<String, Situation> situations;
	private LinkedHashMap<String, Situation> situations;
	private Personnage heros;
	private String pathToGame;
	private String pathFile;

	public Jeu(String nomJeu, int scoreCourant,  LinkedHashMap<String, Situation> situations, Personnage heros, String pathToGame)
	{
		this.nomJeu = nomJeu;
		this.scoreCourant = scoreCourant;
		this.situations = situations;
		this.heros = heros;
		this.pathToGame = pathToGame;
		
		//Chargement du fichier properties pour récupérer le chemin qui pointe vers gameGeneratorMVC
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
	
	public String getNomJeu() {
		return nomJeu;
	}

	public void setNomJeu(String nomJeu) {
		this.nomJeu = nomJeu;
	}

	public int getScoreCourant() {
		return scoreCourant;
	}

	public void setScoreCourant(int scoreCourant) {
		this.scoreCourant = scoreCourant;
	}

	public LinkedHashMap<String, Situation> getSituations() {
		return situations;
	}

	public void setSituations(LinkedHashMap<String, Situation> situations) {
		this.situations = situations;
	}

	public Personnage getHeros() {
		return heros;
	}

	public void setHeros(Personnage heros) {
		this.heros = heros;
	}
	
	public String getPathToGame() {
		return pathToGame;
	}

	public void setPathToGame(String pathToGame) {
		this.pathToGame = pathToGame;
	}

	public Situation getFirstSituation()
	{
		String firstKey = this.situations.keySet().iterator().next();
		return this.situations.get(firstKey);
		
	}
	
	public Situation getSituationById(String id)
	{
		return this.situations.get(id);
	}
	
	public void restartGame()
	{
		this.heros.setVie(this.heros.getVieDefaut());
		this.setScoreCourant(0);
	}
	
	public void saveGame(SituationNarration situationCourante)
	{
		//On récupère les infos courantes sur le jeu
		int scoreCourant = this.getScoreCourant();
		String idSituationCourante = situationCourante.getId();
		int vieHeros = this.getHeros().getVie();
		String nomJeu = this.getNomJeu();
		
		String login = User.getInstance().getLogin();
		
		String filePath = pathFile+"gameGeneratorMVC/Content/xml/Members/"+login+"/savedGames.xml";
		
		LoadXmlFile lxf = new LoadXmlFile(pathFile+"gameGeneratorMVC/Content/xml/Members/"+login+"/", "savedGames.xml");
		
		//On récupère le noeud root
		Node saves = lxf.getDoc().getElementsByTagName("saves").item(0);
		
		//Création des éléments
		Element saveElem = lxf.getDoc().createElement("save");
		Element titleGameElem = lxf.getDoc().createElement("jeu");
		Element dateElem = lxf.getDoc().createElement("date");
		Element idSituationCouranteElem = lxf.getDoc().createElement("idSituationCourante");
		Element scoreCourantElem = lxf.getDoc().createElement("score");
		Element vieHerosElem = lxf.getDoc().createElement("vieHeros");
		
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Date date = new Date();
		
		//insertion des valeurs dans les éléments
		titleGameElem.appendChild(lxf.getDoc().createTextNode(nomJeu));
		dateElem.appendChild(lxf.getDoc().createTextNode(dateFormat.format(date)));
		idSituationCouranteElem.appendChild(lxf.getDoc().createTextNode(idSituationCourante));
		scoreCourantElem.appendChild(lxf.getDoc().createTextNode(Integer.toString(scoreCourant)));
		vieHerosElem.appendChild(lxf.getDoc().createTextNode(Integer.toString(vieHeros)));
		
		//ajout des éléments enfants au noeud "save"
		saveElem.appendChild(titleGameElem);
		saveElem.appendChild(dateElem);
		saveElem.appendChild(idSituationCouranteElem);
		saveElem.appendChild(scoreCourantElem);
		saveElem.appendChild(vieHerosElem);
				
		//Ajout du noeud "save" au noeud root "saves"
		saves.appendChild(saveElem);
				
		//Conversion du document vers le fichier de sortie xml
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		try {
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(lxf.getDoc());
			StreamResult streamResult = new StreamResult(new File(filePath));
			
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
			
			transformer.transform(domSource, streamResult);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void saveScoreGame()
	{
		//charger le fichier bestScore du jeu 
		LoadXmlFile lxf = new LoadXmlFile(this.getPathToGame(), "bestScores.xml");
		
		//Infos à sauvegarder
		String joueur = User.getInstance().getLogin();
		int scorePoints = this.getScoreCourant();
		
		//parcourir le fichier bestscores et comparer les scores avec le scoreCourant
		NodeList score = lxf.getDoc().getElementsByTagName("score");
		
		//Test si on a 5 scores déjà existants au plus
		if(score.getLength()==5)
		{
			int tmpPoints = 9999;
			int index = 0;
			for(int i=0; i<score.getLength();i++)
			{
				Node node = score.item(i);
				System.out.println("current element:"+node.getNodeName());
				
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					
					Element elem = (Element) node;
					
					int nbPoints = Integer.parseInt(elem.getElementsByTagName("points").item(0).getTextContent());
					
					if(nbPoints<tmpPoints)
					{
						tmpPoints = nbPoints;
						index = i;
					}
				}
			}
			System.out.println(tmpPoints);
			System.out.println(index);
			
			if(scorePoints>tmpPoints)
			{
				//On récupère le noeud "score" concerné par la modif
				//Node node = score.item(index);
				Node node = lxf.getDoc().getElementsByTagName("score").item(index);
				
							
				NodeList list = node.getChildNodes();
				System.out.println(list.getLength());
							
				for(int j=0; j<list.getLength();j++)
				{
					Node node2 = list.item(j);
					System.out.println(node2.getNodeName());
					System.out.println(node2.getTextContent());
									
					if ("joueur".equals(node2.getNodeName()))
					{
						node2.setTextContent(joueur);
					}
					else if("points".equals(node2.getNodeName()))
					{
						node2.setTextContent(Integer.toString(scorePoints));
					}
				}
				
				
			}
		}
		else
		{
			Element scoreElem = lxf.getDoc().createElement("score");
			Element joueurElem = lxf.getDoc().createElement("joueur");
			Element pointsElem = lxf.getDoc().createElement("points");
			
			joueurElem.appendChild(lxf.getDoc().createTextNode(joueur));
			pointsElem.appendChild(lxf.getDoc().createTextNode(Integer.toString(scorePoints)));
			
			scoreElem.appendChild(joueurElem);
			scoreElem.appendChild(pointsElem);
			
			lxf.getDoc().getDocumentElement().appendChild(scoreElem);
		}
		
		
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer;
		try {
			transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(lxf.getDoc());
			StreamResult result = new StreamResult(new File(this.getPathToGame()+"bestScores.xml"));
			transformer.transform(source, result);
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
