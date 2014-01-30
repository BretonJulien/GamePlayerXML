package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ViewEndGame extends JFrame{

	private JLabel titleEndGame;
    private JLabel heroLifeLabel;
    private JLabel scoreLabel;
    private JLabel currentHeroLifeLabel;
    private JLabel currentScoreLabel;
    private JButton restartButton;
    private JButton okButton;

    public ViewEndGame(int scoreJeu, int vieHeros) {
        
    	setTitle("GamePlayerXml");
		setSize(356,305);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true);    	
		
		setLocationRelativeTo(null);
    	
    	//construct components
        titleEndGame = new JLabel ("Fin de la partie");
        heroLifeLabel = new JLabel ("Vie du héros :");
        scoreLabel = new JLabel ("Score :");
        currentHeroLifeLabel = new JLabel (Integer.toString(vieHeros));
        currentScoreLabel = new JLabel (Integer.toString(scoreJeu));
        restartButton = new JButton ("Recommencer");
        okButton = new JButton ("OK");

        
        //add components
        add (titleEndGame);
        add (heroLifeLabel);
        add (scoreLabel);
        add (currentHeroLifeLabel);
        add (currentScoreLabel);
        add (restartButton);
        add (okButton);

        //set component bounds (only needed by Absolute Positioning)
        titleEndGame.setBounds (130, 20, 100, 25);
        heroLifeLabel.setBounds (110, 75, 80, 25);
        scoreLabel.setBounds (145, 115, 47, 25);
        currentHeroLifeLabel.setBounds (190, 75, 100, 25);
        currentScoreLabel.setBounds (190, 115, 100, 25);
        restartButton.setBounds (46, 190, 123, 25);
        okButton.setBounds (205, 190, 100, 25);
        
       
    }
    
    public void addViewEndGameActionListener(ActionListener listenForOkButton, ActionListener listenForRestartButton)
    {
    	okButton.addActionListener(listenForOkButton);
    	restartButton.addActionListener(listenForRestartButton);
    }
}
