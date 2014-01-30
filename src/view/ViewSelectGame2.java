package view;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ViewSelectGame2 extends JFrame{

	private JLabel labelSelectGame;
    private JComboBox<String> comboBoxGames;
    private JButton goPreviousButton;
    private JButton okButton;

    public ViewSelectGame2(String[] gamesList) {
        
    	 setTitle("GamePlayerXml");
		 setSize(400,400);
		 setDefaultCloseOperation(EXIT_ON_CLOSE);
		 setLayout(null);
		 setVisible(true);
    	
    	String[] comboBoxGamesItems = gamesList;

        labelSelectGame = new JLabel ("Sélectionner un jeu :");
        comboBoxGames = new JComboBox<String> (comboBoxGamesItems);
        goPreviousButton = new JButton ("Précédent");
        okButton = new JButton ("OK");
        
        //add components
        add (labelSelectGame);
        add (comboBoxGames);
        add (goPreviousButton);
        add (okButton);

        //set component bounds (only needed by Absolute Positioning)
        labelSelectGame.setBounds (110, 35, 140, 25);
        comboBoxGames.setBounds (110, 80, 181, 25);
        goPreviousButton.setBounds (100, 250, 100, 25);
        okButton.setBounds (205, 250, 100, 25);
        
        setLocationRelativeTo(null);
    }


    public void addViewListener(ActionListener listenForOkButton, ActionListener listenForPreviousButton)
    {
    	okButton.addActionListener(listenForOkButton);
    	goPreviousButton.addActionListener(listenForPreviousButton);
    }
    
    public String getSelectedComboBox()
    {
    	return this.comboBoxGames.getSelectedItem().toString();
    }

}

