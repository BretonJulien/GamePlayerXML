package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.ModelMainMenu;

import view.ViewMainMenu;
import view.ViewPopUp;

public class ControllerPopUp {

	private ViewPopUp viewPopUp;
	
	public ControllerPopUp(ViewPopUp viewPopUp)
	{
		this.viewPopUp = viewPopUp;
		this.viewPopUp.addViewPopUpActionListener(new OkButtonListener());
	}
	
	public class OkButtonListener implements ActionListener{

		@Override
		//appelé lorsqu'on clique sur "OK"
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			viewPopUp.dispose();
			ViewMainMenu vmm = new ViewMainMenu();
			ModelMainMenu mmm = new ModelMainMenu();
			ControllerMainMenu cmm = new ControllerMainMenu(vmm, mmm);
		}
		
	}
}
