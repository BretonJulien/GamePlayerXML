package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.ModelClassements;
import model.ModelMainMenu;
import view.ViewClassements;
import view.ViewMainMenu;
import view.ViewSelectGame2;

public class ControllerClassements {

	private ViewClassements viewC;
	private ModelClassements modelC;
	
	public ControllerClassements(ViewClassements viewC, ModelClassements modelC)
	{
		this.viewC = viewC;
		this.modelC = modelC;
		this.viewC.addViewClassementsListener(new OkButtonListener());
	}
	
	public class OkButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			viewC.getFrameFromView().dispose();
			ViewMainMenu vmm = new ViewMainMenu();
			ModelMainMenu mmm = new ModelMainMenu();
			ControllerMainMenu cmm = new ControllerMainMenu(vmm, mmm);
		}
		
	}
	
}
