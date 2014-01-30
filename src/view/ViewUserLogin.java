package view;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.ControllerJeu;
import controller.ControllerMainMenu;

import model.ModelJeu;
import model.ModelMainMenu;
import model.User;


public class ViewUserLogin extends JFrame{

	private JButton login,cancel;
	private JTextField uname;
	private JPasswordField pass;
	private JLabel u,p;
	
	
	
	 public ViewUserLogin()
	 {
		 setTitle("Login");
		 setLayout(new GridLayout(3,2));
		 setDefaultCloseOperation(EXIT_ON_CLOSE);
		 
		 setVisible(true);
	
		 u=new JLabel("Username");
		 p=new JLabel("Password");
	
		 uname=new JTextField(20);
		 pass=new JPasswordField(20);
	
		 login=new JButton("Login");
		 cancel=new JButton("Cancel");
	
		 add(u);
		 add(uname);
	
		 add(p);
		 add(pass);
	
		 add(login);
		 add(cancel);
	
		 uname.requestFocus();
	
		 cancel.addActionListener(new ActionListener(){
		  public void actionPerformed(ActionEvent ae)
		  {
			  System.exit(0);
		  }
		 });
	
		 login.addActionListener(new ActionListener(){
		  public void actionPerformed(ActionEvent ae)
		  {
		  String un=uname.getText();
		  String pa=new String(pass.getPassword());
		  
		   if((un.equals("juju"))&&(pa.equals("juju")))
		   {
			   //On récupère l'instance de la classe user et on lui définit le login du user 
			   //Qui se connecte
			   String login = "herbelin.benjamin";
			   User user = User.getInstance();
			   user.setLogin(login);
			   
			   dispose();
			   ViewMainMenu vmm = new ViewMainMenu();
			   ModelMainMenu mmm = new ModelMainMenu();
			   ControllerMainMenu cmm = new ControllerMainMenu(vmm, mmm);
		   }
		  }
		 });
	
		 KeyAdapter k=new KeyAdapter(){
		  public void keyPressed(KeyEvent ke)
		  {
		   if(ke.getKeyCode()==KeyEvent.VK_ENTER)
		   login.doClick();
		  }
		 };
	
		 pass.addKeyListener(k);
		 uname.addKeyListener(k);
	
		 pack();
		 setLocationRelativeTo(null);
	 }

}
