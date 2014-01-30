package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class ViewClassements{

	private String[] header;
	private Object[][] data;
	private String[] items;
	private JButton okButton;
	private JFrame frame;
	
	public ViewClassements(String[] entetes, Object[][]datas)
	{
		this.header = entetes;
		this.data = datas;
			       
	    JTable table = new JTable(data,header);
	    frame = new JFrame();
	    okButton = new JButton("OK");
	    //JPanel panel = new JPanel(new GridLayout(1, 0, 1, 0));
	    
	    //frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(table), BorderLayout.CENTER);
        frame.add(okButton,BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
        frame.setSize(600, 200);
        
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);
	}

	public void addViewClassementsListener(ActionListener listenForOkButton)
	{
		okButton.addActionListener(listenForOkButton);
	}
	
	public JFrame getFrameFromView()
	{
		return frame;
	}
	
}
