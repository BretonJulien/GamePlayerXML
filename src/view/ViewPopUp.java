package view;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ViewPopUp extends JFrame{

	private JLabel label;
    private JButton okButton;

    public ViewPopUp(String msg) {
        //construct components
        label = new JLabel (msg);
        okButton = new JButton ("OK");

        //add components
        add (label);
        add (okButton);

        //set component bounds (only needed by Absolute Positioning)
        label.setBounds (85, 35, 150, 25);
        okButton.setBounds (80, 85, 100, 25);
        
        setTitle("GamePlayerXML");
        setSize(268, 185);
        setLayout (null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public void addViewPopUpActionListener(ActionListener listenForOkButton)
    {
    	okButton.addActionListener(listenForOkButton);
    }
}
