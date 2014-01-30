package view;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ViewLoadGame extends JFrame{

    private JLabel titleFrame;
    private JComboBox comboBoxSaves;
    private JButton backButton;
    private JButton loadButton;

    public ViewLoadGame(String[] arraySavedGames) {
        
    	//adjust size and set layout
    	setTitle("GamePlayerXML");
        setSize (428, 305);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout (null);
        setVisible(true);
        setLocationRelativeTo(null);
    	
    	//construct components
        titleFrame = new JLabel ("Charger une partie");
        comboBoxSaves = new JComboBox<String> (arraySavedGames);
        backButton = new JButton ("Précédent");
        loadButton = new JButton ("Charger");

        

        //add components
        add (titleFrame);
        add (comboBoxSaves);
        add (backButton);
        add (loadButton);

        //set component bounds (only needed by Absolute Positioning)
        titleFrame.setBounds (160, 35, 127, 25);
        comboBoxSaves.setBounds (95, 115, 250, 25);
        backButton.setBounds (65, 220, 100, 25);
        loadButton.setBounds (245, 220, 100, 25);
    }
    
    public void addViewLoadGameListener(ActionListener loadButtonListener, ActionListener backButtonListener)
    {
    	loadButton.addActionListener(loadButtonListener);
    	backButton.addActionListener(backButtonListener);
    }
    
    public String getComboBoxSelected()
    {
    	return comboBoxSaves.getSelectedItem().toString();
    }
}
