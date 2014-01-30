package view;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;


public class ViewMainMenu extends JFrame{

	JButton jouer,reprendre,classements,meta;
	String[] gamesList;
	
	//On attache le controller à la vue
	 public ViewMainMenu()
	 {
		 setTitle("GamePlayerXml");
		 setSize(400,400);
		 setDefaultCloseOperation(EXIT_ON_CLOSE);
		 setLayout(null);
		 setVisible(true);
	
		 jouer = new JButton("Jouer");
		 reprendre = new JButton("Reprendre une partie");
		 classements = new JButton("Classements");
		 meta = new JButton("Données de jeu");
		 		 
		 add(jouer);
		 add(reprendre);
		 add(classements);
		 add(meta);
		 
		 jouer.setBounds (110, 31, 160, 30);
	     reprendre.setBounds (110, 75, 160, 30);
	     classements.setBounds (110, 124, 160, 30);
	     meta.setBounds (110, 180, 160, 30);
		
		 setLocationRelativeTo(null);
		 setResizable(false);
		 
	 }
	 
	 public void addViewMainMenuListener(ActionListener listenForPlayButton, ActionListener listenForLoadButton, ActionListener listenForClassementButton)
	 {
		 jouer.addActionListener(listenForPlayButton);
		 reprendre.addActionListener(listenForLoadButton);
		 classements.addActionListener(listenForClassementButton);
	 }
	 
}
