package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ModelSelectGame {

	private Jeu jeu;
	
	public ModelSelectGame()
	{

	}
	
	//Charge le jeu correspondant à son nom
	public void initGame(String gameName)
	{
		//Chargement du fichier properties pour récupérer le chemin qui pointe vers gameGeneratorMVC
		Properties prop = new Properties();
		InputStream input = null;
		String pathFile = null;
				
		try {
	 		input = new FileInputStream("prop.properties");
	 		prop.load(input);
	 		pathFile=prop.getProperty("PATH");
				 
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		//on récupère la liste avec le nom du fichier de jeu, le nom du fichier des persos à partir du nom de jeu récupéré en paramètre
		//la liste sera toujours composée de 4 index :
		// 0 : nom du fichier de jeu xml
		// 1 : chemin vers le fichier de jeu xml
		// 2 : nom du fichier des personnages xml
		// 3 : chemin vers le fichier des personnages xml
		//Etant donné que les valeurs des index sont toujours insérées dans le même ordre
		//on utilise les valeurs en dur pour récupérer les index
		HashMap<String, String> mapCharactersAndGameFile = getCharactersFileNameAndGameFileNameFromGameName(new File(pathFile+"gameGeneratorMVC/Content/xml/Members"), gameName);
		
		//Chargement du fichier xml des personnages
		LoadXmlFile lxf = new LoadXmlFile(mapCharactersAndGameFile.get("path"),mapCharactersAndGameFile.get("charactersFile"));
		NodeList personnage = lxf.getDoc().getElementsByTagName("personnage");
		HashMap<String, Personnage> mapPersos = new HashMap<String, Personnage>();
		for(int i=0;i<personnage.getLength();i++)
		{
			Node node = personnage.item(i);
			System.out.println("current element:"+node.getNodeName());
			
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				 
				Element elem = (Element) node;
				String typePerso = elem.getAttribute("type");
				String nomPerso = elem.getElementsByTagName("nom").item(0).getTextContent();
				int viePerso = Integer.parseInt(elem.getElementsByTagName("vie").item(0).getTextContent());
				int defPerso = Integer.parseInt(elem.getElementsByTagName("defense").item(0).getTextContent());
				int atkPerso = Integer.parseInt(elem.getElementsByTagName("attaque").item(0).getTextContent());
				int initiativePerso = Integer.parseInt(elem.getElementsByTagName("initiative").item(0).getTextContent());
				
				Personnage perso = new Personnage(nomPerso, viePerso, atkPerso, defPerso, initiativePerso, typePerso);
				mapPersos.put(nomPerso, perso);
			}
		}
		
		//----------------------------
		//CREATION DES SITUATIONS
		//----------------------------
		LoadXmlFile lxf2 = new LoadXmlFile(mapCharactersAndGameFile.get("path"),mapCharactersAndGameFile.get("gameFile"));
		
		//System.out.println("Root element:"+lxf2.getDoc().getDocumentElement().getNodeName()+"\n");
		
		String titreJeu = lxf2.getDoc().getElementsByTagName("titre").item(0).getTextContent();
		
		NodeList situation = lxf2.getDoc().getElementsByTagName("situation");
		
		LinkedHashMap<String, Situation> situationsMap = new LinkedHashMap<String, Situation>();
		
		for(int i=0;i<situation.getLength();i++)
		{
			Node node = situation.item(i);
			//System.out.println("current element:"+node.getNodeName());
			
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				
				
				Element elem = (Element) node;
				String typeSituation = elem.getAttribute("type");
				
				if(typeSituation.equals("Narration"))
				{
					String idSituation = elem.getElementsByTagName("situationCode").item(0).getTextContent();
					String titreSituation = elem.getElementsByTagName("situationTitle").item(0).getTextContent();
					String situationExposition = elem.getElementsByTagName("situationExposition").item(0).getTextContent();
					String questionSituation = elem.getElementsByTagName("label").item(0).getTextContent();
					
					SituationNarration sn = new SituationNarration(idSituation, titreSituation, situationExposition, questionSituation, null);
					//--------------------------------------
					//CREATION DES REPONSES DE NARRATION
					//--------------------------------------
					ArrayList<ReponseNarration> listReponses = new ArrayList<ReponseNarration>();
					
					for(int j=0;j<elem.getElementsByTagName("rep").getLength();j++)
					{
						String idReponse = elem.getElementsByTagName("rep").item(j).getAttributes().item(0).getNodeValue();
						String labelReponse = elem.getElementsByTagName("rep").item(j).getTextContent();
						int nbPoints = Integer.parseInt(elem.getElementsByTagName("points").item(j).getTextContent());
						String idSituationNext = elem.getElementsByTagName("code").item(j).getTextContent();
						
						/*System.out.println("Id réponse"+(j+1)+":"+idReponse);
						System.out.println("Label réponse"+(j+1)+":"+labelReponse);
						System.out.println("Nb points"+(j+1)+":"+nbPoints);
						System.out.println("Id situation suivante"+(j+1)+":"+idSituationNext);
						System.out.println("\n");*/
						ReponseNarration rn = new ReponseNarration(idReponse, nbPoints, labelReponse, idSituationNext);
						listReponses.add(rn);
					}
					sn.setReponses(listReponses);
					situationsMap.put(sn.getId(), sn);
				}
				else
				{
					String idSituation = elem.getElementsByTagName("situationCode").item(0).getTextContent();
					String titreSituation = elem.getElementsByTagName("situationTitle").item(0).getTextContent();
					String situationExposition = elem.getElementsByTagName("situationExposition").item(0).getTextContent();
					String questionSituation = elem.getElementsByTagName("label").item(0).getTextContent();
					String ennemi = elem.getElementsByTagName("ennemi").item(0).getTextContent();
					
					Personnage perso = mapPersos.get(ennemi);
					
					SituationCombat sc = new SituationCombat(idSituation, titreSituation, situationExposition, questionSituation, null, perso);
					//--------------------------------------
					//CREATION DES REPONSES
					//--------------------------------------
					ArrayList<Reponse> listReponses = new ArrayList<Reponse>();
					
					for(int j=0;j<elem.getElementsByTagName("rep").getLength();j++)
					{
						String idReponse = elem.getElementsByTagName("rep").item(j).getAttributes().item(0).getNodeValue();
						String labelReponse = elem.getElementsByTagName("rep").item(j).getTextContent();
						String idSituationNextVictoire = elem.getElementsByTagName("code").item(j).getTextContent();
						
//								System.out.println("Id réponse"+(j+1)+":"+idReponse);
//								System.out.println("Label réponse"+(j+1)+":"+labelReponse);
						
						
						//EN CAS D'ATTAQUE
						if(!elem.getElementsByTagName("rep").item(j).getAttributes().item(0).getNodeValue().equals("1"))
						{
							System.out.println("Id situation suivante victoire"+(j+1)+":"+idSituationNextVictoire);
							String idSituationNextDefaite = elem.getElementsByTagName("code").item(j+1).getTextContent();
							int nbPointsVictoire = Integer.parseInt(elem.getElementsByTagName("pointsVictoire").item(j).getTextContent());
							int nbPointsDefaite = Integer.parseInt(elem.getElementsByTagName("pointsDefaite").item(j).getTextContent());
							
//									System.out.println("Id situation suivante défaite"+(j+1)+":"+idSituationNextDefaite);
//									System.out.println("Nb points victoire"+(j+1)+":"+nbPointsVictoire);
//									System.out.println("Nb points défaite"+(j+1)+":"+nbPointsDefaite);
//									System.out.println("\n");
							ReponseCombat rc = new ReponseCombat(idReponse, labelReponse, nbPointsVictoire, nbPointsDefaite, idSituationNextVictoire, idSituationNextDefaite);
							listReponses.add(rc);
						}
						//EN CAS DE FUITE
						else
						{
							String idSituationNextFuite = elem.getElementsByTagName("code").item(2).getTextContent();
							int nbPointsFuite = Integer.parseInt(elem.getElementsByTagName("points").item(0).getTextContent());
							
							ReponseNarration rn = new ReponseNarration(idReponse, nbPointsFuite, labelReponse, idSituationNextFuite);
							listReponses.add(rn);
//									System.out.println("Id situation suivante fuite"+(j+1)+":"+idSituationNextFuite);
//									System.out.println("Nb points fuite"+(j+1)+":"+nbPointsFuite);
//									System.out.println("\n");
						}
						sc.setReponses(listReponses);
					}
					situationsMap.put(sc.getId(), sc);
					//situationsList.add(sc);
				}
			}
		}
//				System.out.println("nb de situations:"+situationsMap.size());
//				Iterator it0 = situationsMap.entrySet().iterator();
//				int k=0;
//				while (it0.hasNext()) {
//				    Map.Entry entry = (Map.Entry) it0.next();
//				    String key = (String)entry.getKey();
//				    Situation val = (Situation)entry.getValue();
//				    System.out.println("Situation"+(k+1)+":"+val.getTitre());
//				    k++;
//				}
		
		//------------------------------
		//CREATION DU JEU
		//------------------------------
		Personnage heros = new Personnage();
		Iterator it = mapPersos.entrySet().iterator();
		while (it.hasNext()) {
		    Map.Entry entry = (Map.Entry) it.next();
		    String key = (String)entry.getKey();
		    Personnage val = (Personnage)entry.getValue();
		    if(val.getType().equals("H"))
		    {
		    	heros = mapPersos.get(key);
		    }
		}
		String pathToGame = mapCharactersAndGameFile.get("path");
		
		Jeu newGame = new Jeu(titreJeu, 0, situationsMap, heros, pathToGame);
		this.jeu = newGame;
	}

	public Jeu getJeu() {
		return jeu;
	}

	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
	}
	
	//retourne les noms du fichier des personnages et du fichier de jeu à partir du répertoire spécifié et du nom de jeu fourni
	public HashMap<String, String> getCharactersFileNameAndGameFileNameFromGameName(File repertoire, String gameName)
	{
	//ArrayList<String> listCharactersGameFile = new ArrayList<String>();
	HashMap<String, String> mapCharactersGameFile = new HashMap<String, String>();
			
	        if ( repertoire.isDirectory ( ) ) {
	                File[] list = repertoire.listFiles();
	                if (list != null){
	                	//parcourt la liste des members
		                for ( int i = 0; i < list.length; i++)
		                {
		                	//Si l'élément du répertoire members est un répertoire
		                	if(list[i].isDirectory())
		                	{
		                		//liste des répertoires 
		                		File[] underList = list[i].listFiles();
		                		if(underList!=null)
		                		{
		                			for(int j=0;j<underList.length;j++)
		                			{
		                				//Si un des éléments du répertoire courant à l'extension xml, typiquement userGames.xml
		                				if(!underList[j].getName().contains("xml"))
		                				{
		                					//System.out.println(underList[j].getName());
		                					//System.out.println(underList[j].getPath());
		                					String path = underList[j].getPath()+"/";
		                					if(underList[j].getName().equals(gameName))
		                					{
			                					if(underList[j].isDirectory())
			                					{
			                						File[] listCharactersAndFileGame = underList[j].listFiles();
			                						//Permet de toujours positionner le fichier de jeu en première position
			                						//pour le retour de l'arrayList
			                						//listCharactersGameFile.add(path.replace("\\","/"));
			                						mapCharactersGameFile.put("path", path.replace("\\", "/"));
			                						for(int l=0; l<listCharactersAndFileGame.length; l++)
			                						{
			                							if(!listCharactersAndFileGame[l].getName().startsWith("bestScores"))
			                							{
			                								if(listCharactersAndFileGame[l].getName().startsWith("fileGame"))
			                								{
			                									mapCharactersGameFile.put("gameFile", listCharactersAndFileGame[l].getName());
			                								}
			                								else
			                								{
			                									mapCharactersGameFile.put("charactersFile", listCharactersAndFileGame[l].getName());
			                								}
			                							}
			                						}
			                					}
		                					}
		                				}
		                			}
		                		}
		                	}
		                } 
	                } else {
	                	System.err.println(repertoire + " : Erreur de lecture.");
	                }
	        } 
	        return mapCharactersGameFile;
		}
}
