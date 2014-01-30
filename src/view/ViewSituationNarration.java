package view;


import java.awt.event.*;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.*;
import model.Reponse;
import model.ReponseNarration;
import model.Situation;
import model.SituationNarration;
import model.User;


public class ViewSituationNarration extends JFrame {
    
	private JLabel gameTitle;
    private JTextArea expositionArea;
    private JLabel questionLabel;
    private JButton saveButton;
    private JButton restartButton;
    private JButton quitButton;
    private JButton okButton;
    private JLabel score;
    private JLabel currentScore;
    private ButtonGroup group;
    private SituationNarration situationNarration;
        
	public ViewSituationNarration(Situation situation, int scoreCourant) {
	    
		 setTitle("GamePlayerXml");
		 setSize(486,441);
		 setDefaultCloseOperation(EXIT_ON_CLOSE);
		 setLayout(null);
		 setVisible(true);
		
		//On caste la situation pour récupérer la classe d'origine de la situation courante
		situationNarration = (SituationNarration)situation;
		
		gameTitle = new JLabel (situationNarration.getTitre());
	    expositionArea = new JTextArea (5, 5);
	    expositionArea.append(situationNarration.getExposition());
	    questionLabel = new JLabel (situationNarration.getQuestion());
	    saveButton = new JButton ("Sauver le jeu");
	    restartButton = new JButton ("Recommencer");
	    quitButton = new JButton ("Quitter");
	    okButton = new JButton ("OK");
	    score = new JLabel ("Score :");
	    currentScore = new JLabel (Integer.toString(scoreCourant));
	    
	    
	    group = new ButtonGroup();
	    
	    ArrayList<ReponseNarration> reponses = situationNarration.getReponses();
	    int y=175;
	    for(int i=0;i<reponses.size();i++)
	    {
	    	JRadioButton jb = new JRadioButton(reponses.get(i).getLabel());
	    	if(i==0)
	    	{
	    		jb.setSelected(true);
	    	}
	    	group.add(jb);
	    	add(jb);
	    	jb.setBounds(85, y, 200, 25);
	    	y+=35;
	    }

	
	    //add components
	    add (gameTitle);
	    add (expositionArea);
	    add (questionLabel);
	    add (saveButton);
	    add (restartButton);
	    add (quitButton);
	    add (okButton);
	    add (score);
	    add (currentScore);
	    
	   	
	    gameTitle.setBounds (215, 15, 200, 25);
	    expositionArea.setBounds (85, 50, 300, 75);
	    questionLabel.setBounds (90, 140, 100, 25);
	    saveButton.setBounds (45, 355, 130, 25);
	    restartButton.setBounds (190, 355, 120, 25);
	    quitButton.setBounds (325, 355, 100, 25);
	    okButton.setBounds (330, 185, 100, 25);
	    score.setBounds (330, 145, 50, 25);
	    currentScore.setBounds (380, 145, 100, 25);
	    
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

	public void addViewSituationNarrationListener(ActionListener listenForOkButton, ActionListener listenForQuitButton, ActionListener listenForRestartButton, ActionListener listenForSaveButton)
	 {
	    	okButton.addActionListener(listenForOkButton);
	    	quitButton.addActionListener(listenForQuitButton);
	    	restartButton.addActionListener(listenForRestartButton);
	    	saveButton.addActionListener(listenForSaveButton);
	 }
	
	 public ButtonGroup getGroup() {
			return group;
		}

	public void setGroup(ButtonGroup group) {
		this.group = group;
	}

	public SituationNarration getSituationNarration() {
		return situationNarration;
	}

	public void setSituationNarration(SituationNarration situationNarration) {
		this.situationNarration = situationNarration;
	}

}
