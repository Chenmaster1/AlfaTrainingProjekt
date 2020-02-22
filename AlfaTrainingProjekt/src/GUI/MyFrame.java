package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MyFrame extends JFrame{

	private JButton btnClose;
	private JPanel pnl;
	
	
	public MyFrame () {
		btnClose = new JButton("Beenden");
		pnl = new JPanel();
		pack();
	}
	
	
	public void start() {
		//Jframe auf fullscreen
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//this.setUndecorated(true);
		
		this.add(pnl);

		btnClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				onCloseClicked();
				
			}
		});
		pnl.add(btnClose, BorderLayout.PAGE_END);
		
		
		this.setVisible(true);
	}
	
	private void onCloseClicked() {
		this.dispose();
	}
}
