package view;

	import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;

import javax.swing.*;
import javax.swing.event.*;

import model.Situation;
import model.SituationCombat;
import model.SituationNarration;

public class ViewSituationCombat extends JFrame {
    private JLabel gameTitleLabel;
    private JTextArea expositionAreaLabel;
    private JLabel questionLabel;
    private JButton restartButton;
    private JButton quitButton;
    private JButton okButton;
    private JLabel scoreLabel;
    private JLabel currentScoreLabel;
    private ButtonGroup group;
    private JLabel vieHerosLabel;
    private JLabel vieEnnemiLabel;
    private JLabel currentHeroLifeLabel;
    private JLabel currentEnemyLifeLabel;
    private SituationCombat situationCombat;
    
    public ViewSituationCombat(Situation situation, int scoreCourant, int vieHeros) {
    	
    	 setTitle("GamePlayerXml");
		 setSize(486,441);
		 setDefaultCloseOperation(EXIT_ON_CLOSE);
		 setLayout(null);
		 setVisible(true);
		 
		 situationCombat = (SituationCombat) situation;
    	
		 
		 
        //construct components
	    gameTitleLabel = new JLabel (situationCombat.getTitre());
	    expositionAreaLabel = new JTextArea (5, 5);
	    expositionAreaLabel.append(situationCombat.getExposition());
	    questionLabel = new JLabel ("Que voulez-vous faire?");
	    restartButton = new JButton ("Recommencer");
	    quitButton = new JButton ("Quitter");
	    okButton = new JButton ("OK");
	    scoreLabel = new JLabel ("Score :");
	    currentScoreLabel = new JLabel (Integer.toString(scoreCourant));
	    vieHerosLabel = new JLabel ("Vie heros :");
	    vieEnnemiLabel = new JLabel ("Vie ennemi :");
	    currentHeroLifeLabel = new JLabel (Integer.toString(vieHeros));
	    currentEnemyLifeLabel = new JLabel (Integer.toString(situationCombat.getEnnemi().getVieDefaut()));
	
	    group = new ButtonGroup();
	    JRadioButton attackButton =  new JRadioButton("Attaquer");
	    attackButton.setSelected(true);
	    JRadioButton fuiteButton =  new JRadioButton("Prendre la fuite");
	    group.add(attackButton);
	    group.add(fuiteButton);
	    
	    //add components
	    add (gameTitleLabel);
	    add (expositionAreaLabel);
	    add (questionLabel);
	    add (restartButton);
	    add (quitButton);
	    add (okButton);
	    add (scoreLabel);
	    add (currentScoreLabel);
	    add(attackButton);
	    add(fuiteButton);
	    add (vieHerosLabel);
	    add (vieEnnemiLabel);
	    add (currentHeroLifeLabel);
	    add (currentEnemyLifeLabel);
	
	    //set component bounds (only needed by Absolute Positioning)
	    gameTitleLabel.setBounds (215, 15, 100, 25);
	    expositionAreaLabel.setBounds (85, 50, 300, 75);
	    questionLabel.setBounds (90, 140, 150, 25);
	    restartButton.setBounds (190, 355, 120, 25);
	    quitButton.setBounds (325, 355, 100, 25);
	    okButton.setBounds (330, 254, 100, 25);
	    scoreLabel.setBounds (330, 140, 44, 25);
	    currentScoreLabel.setBounds (380, 140, 40, 25);
	    attackButton.setBounds (85, 175, 200, 25);
	    fuiteButton.setBounds (85, 210, 200, 25);
	    vieHerosLabel.setBounds (330, 175, 63, 25);
	    vieEnnemiLabel.setBounds (330, 210, 73, 25);
	    currentHeroLifeLabel.setBounds (395, 175, 40, 25);
	    currentEnemyLifeLabel.setBounds (405, 210, 40, 25);
	    
	    setLocationRelativeTo(null);
    }
    
    public String getRadioButtonSelected()
	{
		String returnVal = null;
		Enumeration<AbstractButton> allRadioButton=group.getElements(); 
		while(allRadioButton.hasMoreElements())  
		{  
			JRadioButton temp=(JRadioButton)allRadioButton.nextElement();  
			if(temp.isSelected())  
			{  
				returnVal = temp.getText();
			}  
		}
		return returnVal;
	}
    
    public void addViewSituationCombatListener(ActionListener listenForOkButton, ActionListener listenForQuitButton, ActionListener listenForRestartButton)
    {
    	okButton.addActionListener(listenForOkButton);
    	restartButton.addActionListener(listenForRestartButton);
    	quitButton.addActionListener(listenForQuitButton);
    }
    
    public void setCurrentHeroLifeLabel(String vieHeros)
    {
    	currentHeroLifeLabel.setText(vieHeros);
    }
    
    public void setCurrentEnemyLifeLabel(String vieEnemy)
    {
    	currentEnemyLifeLabel.setText(vieEnemy);
    }
    
    public SituationCombat getSituationCombat() {
		return situationCombat;
	}

	public void setSituationCombat(SituationCombat situationCombat) {
		this.situationCombat = situationCombat;
	}
}
