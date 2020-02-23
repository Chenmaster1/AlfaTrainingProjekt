package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Database.Database;

public class MyFrame extends JFrame{

	private JButton btnClose;
	private JPanel pnl;
	
	
	public MyFrame () {
		
		btnClose = new JButton("Beenden");
		pnl = new JPanel();
		initializeFrame();
		pack();
	}
	
	
	public void start() {
		//Panel adden
		this.add(pnl);

		//Buttons
		initializeButtons();
		
		//Datenbankverbindung
		connectDatabase();
		
		this.setVisible(true);		
	}
	
	private void onCloseClicked() {
		this.dispose();
	}
	
	private void initializeButtons() {
		btnClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				onCloseClicked();
				
			}
		});
		pnl.add(btnClose, BorderLayout.PAGE_END);
	}
	
	private void connectDatabase() {
		try {
			Database.getInstance().createDatabase();
			Database.getInstance().createTables();
		}catch(Exception ex) {
			System.out.println("Keine Datenbank verbindung");
		}
	}
	
	private void initializeFrame(){
		this.setUndecorated(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
}
