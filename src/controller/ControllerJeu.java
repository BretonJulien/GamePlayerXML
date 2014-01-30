package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import view.ViewSituationNarration;

import model.Jeu;
import model.LoadXmlFile;
import model.ModelJeu;
import model.Personnage;
import model.Reponse;
import model.ReponseCombat;
import model.ReponseNarration;
import model.Situation;
import model.SituationCombat;
import model.SituationNarration;

public class ControllerJeu{

	private ModelJeu modelJeu;
		
	//On attache le modèle au controller
	public ControllerJeu(ModelJeu modelJeu)
	{
		this.modelJeu = modelJeu;
	}
	
	public void actionJouer()
	{
		//---------------------------
		//CREATION DES PERSONNAGES
		//---------------------------
		LoadXmlFile lxf = new LoadXmlFile("C:/xampp/htdocs/gameGeneratorMVC/Content/xml/Members/julien.breton/SeigneurDesAnneaux/","SeigneurDesAnneauxCharacters.xml");
		
		NodeList personnage = lxf.getDoc().getElementsByTagName("personnage");
		
		HashMap<String, Personnage> mapPersos = new HashMap<String, Personnage>();
		//ArrayList<Personnage> persos = new ArrayList<Personnage>();
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
				//persos.add(perso);
				mapPersos.put(nomPerso, perso);
			}
		}
		
		//----------------------------
		//CREATION DES SITUATIONS
		//----------------------------
		LoadXmlFile lxf2 = new LoadXmlFile("C:/xampp/htdocs/gameGeneratorMVC/Content/xml/Members/julien.breton/SeigneurDesAnneaux/","fileGame_julien.breton_28122013.xml");
		
		System.out.println("Root element:"+lxf2.getDoc().getDocumentElement().getNodeName()+"\n");
		
		NodeList situation = lxf2.getDoc().getElementsByTagName("situation");
		
		HashMap<String, Situation> situationsMap = new HashMap<String, Situation>();
		
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
					//CREATION DES REPONSES
					//--------------------------------------
					ArrayList<Reponse> listReponses = new ArrayList<Reponse>();
					
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
					//sn.setReponses(listReponses);
					situationsMap.put(sn.getTitre(), sn);
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
						
//						System.out.println("Id réponse"+(j+1)+":"+idReponse);
//						System.out.println("Label réponse"+(j+1)+":"+labelReponse);
						
						
						//EN CAS D'ATTAQUE
						if(!elem.getElementsByTagName("rep").item(j).getAttributes().item(0).getNodeValue().equals("1"))
						{
							System.out.println("Id situation suivante victoire"+(j+1)+":"+idSituationNextVictoire);
							String idSituationNextDefaite = elem.getElementsByTagName("code").item(j+1).getTextContent();
							int nbPointsVictoire = Integer.parseInt(elem.getElementsByTagName("pointsVictoire").item(j).getTextContent());
							int nbPointsDefaite = Integer.parseInt(elem.getElementsByTagName("pointsDefaite").item(j).getTextContent());
							
//							System.out.println("Id situation suivante défaite"+(j+1)+":"+idSituationNextDefaite);
//							System.out.println("Nb points victoire"+(j+1)+":"+nbPointsVictoire);
//							System.out.println("Nb points défaite"+(j+1)+":"+nbPointsDefaite);
//							System.out.println("\n");
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
//							System.out.println("Id situation suivante fuite"+(j+1)+":"+idSituationNextFuite);
//							System.out.println("Nb points fuite"+(j+1)+":"+nbPointsFuite);
//							System.out.println("\n");
						}
						sc.setReponses(listReponses);
					}
					situationsMap.put(sc.getTitre(), sc);
				}
			}
		}
//		System.out.println("nb de situations:"+situationsMap.size());
//		Iterator it0 = situationsMap.entrySet().iterator();
//		int k=0;
//		while (it0.hasNext()) {
//		    Map.Entry entry = (Map.Entry) it0.next();
//		    String key = (String)entry.getKey();
//		    Situation val = (Situation)entry.getValue();
//		    System.out.println("Situation"+(k+1)+":"+val.getTitre());
//		    k++;
//		}
		
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
		//Jeu jeu = new Jeu("Seigneur des anneaux", 100,situationsMap, heros);
		this.modelJeu.creerJeu("Seigneur des anneaux", 0, situationsMap, heros);
			
		//ViewSituationNarration vsn = new ViewSituationNarration(this);
	}
	
	public String actionGetListGames()
	{
		String listeJeu = modelJeu.getListGames();
		return listeJeu;
	}
}
