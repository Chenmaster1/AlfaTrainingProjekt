/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MenuGUI;

import java.awt.Color;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.SwingConstants;

import resourceLoaders.ImageLoader;
import resourceLoaders.ImageName;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Alfa
 */
@SuppressWarnings("serial")
public class InstructionPanel extends JPanel {

	private MyFrame frame;
	MainFramePanel mainFramePanel;
	private Image backgroundImage;
	
	private JButton btnBack;
	private JScrollPane scrollPaneMainCategory;
	private JScrollPane scrollPaneSubCategory;
	
	DefaultListModel<String> resultList = new DefaultListModel<String>();
	
	private JList<String> listMainCatagory;
	private JList<String> subCategory = new JList<String>(resultList);
	
	
	//fuer size 20 pro eintrag
	private String[] mainCatagories = new String[] {"test1","test2","test3","test4","test5","test6"};
	
	public InstructionPanel(MyFrame frame, MainFramePanel mainFramePanel) {
		backgroundImage = ImageLoader.getInstance().getImage(ImageName.MENU_BACKGROUND_BLURRY);
		this.frame = frame;
		this.mainFramePanel = mainFramePanel;
		frame.setContentPane(this);
		setLayout(null);
		setPreferredSize(frame.getSize());
		
		//btnBack = new MyButton(MyFrame.bundle.getString("btnCancel"), new ImageIcon(ImageLoader.getInstance().getImage(ImageName.BUTTON)));
		btnBack = new JButton(MyFrame.bundle.getString("btnCancel"));
		
		initializeButtons();
		initializeMainList();
		initializeScrollPanes();
		frame.pack();
	}
	
	@Override
    public void paintComponent(Graphics g)
    {
        g.drawImage(backgroundImage, 0, 0, this);
    }
	
	private void initializeButtons() {

		addButton(btnBack, 0,0, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				onBackClicked();
				
			}
		});
	}
	
	private void addButton(JButton button, int posX, int posY, ActionListener action) {
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setBounds(posX, posY, 100, 30);
		button.addActionListener(action);
		add(button);
	}
	
	@SuppressWarnings("unchecked")
	private void initializeMainList() {
		listMainCatagory = new JList<String>(mainCatagories);
		listMainCatagory.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				DefaultListModel<String> resultList = new DefaultListModel<String>();
				switch(((JList<String>) e.getSource()).getSelectedIndex()) {
				case 0:
					resultList.addElement("test1");
					resultList.addElement("test1");
					resultList.addElement("test1");
					resultList.addElement("test1");
					resultList.addElement("test1");
					scrollPaneSubCategory.setSize(100, 100);
					scrollPaneSubCategory.setVisible(true);
					break;
				case 1:
					resultList.addElement("test2");
					resultList.addElement("test2");
					resultList.addElement("test2");
					resultList.addElement("test2");
					resultList.addElement("test2");
					scrollPaneSubCategory.setSize(100, 100);
					scrollPaneSubCategory.setVisible(true);
					break;
				case 2:
					resultList.addElement("test3");
					resultList.addElement("test3");
					resultList.addElement("test3");
					resultList.addElement("test3");
					resultList.addElement("test3");
					scrollPaneSubCategory.setSize(100, 100);
					scrollPaneSubCategory.setVisible(true);
					break;
				case 3:
					resultList.addElement("test4");
					resultList.addElement("test4");
					resultList.addElement("test4");
					resultList.addElement("test4");
					resultList.addElement("test4");
					scrollPaneSubCategory.setSize(100, 100);
					scrollPaneSubCategory.setVisible(true);
					break;
				case 4:
					resultList.addElement("test5");
					resultList.addElement("test5");
					resultList.addElement("test5");
					resultList.addElement("test5");
					resultList.addElement("test5");
					scrollPaneSubCategory.setSize(100, 100);
					scrollPaneSubCategory.setVisible(true);
					break;
				case 5:
					resultList.addElement("test6");
					resultList.addElement("test6");
					resultList.addElement("test6");
					resultList.addElement("test6");
					resultList.addElement("test6");
					scrollPaneSubCategory.setSize(100, 100);
					scrollPaneSubCategory.setVisible(true);
					break;
					
				}
			}
		});
		//listMainCatagory.setLocation(0,40);		
		//add(listMainCatagory);
	}
	
	private void initializeScrollPanes() {
		scrollPaneMainCategory = new JScrollPane(listMainCatagory);
		scrollPaneMainCategory.setSize(100,120);
		scrollPaneMainCategory.setLocation(0, 40);
		add(scrollPaneMainCategory);
		
		scrollPaneSubCategory = new JScrollPane(subCategory);
		scrollPaneSubCategory.setLocation(110, 40);
		scrollPaneSubCategory.setVisible(false);
		add(scrollPaneSubCategory);
	}
	
	private void onBackClicked() {
		frame.setContentPane(mainFramePanel);
		frame.repaint();
	}
//	private MainFramePanel parentPanel;
//	private JFrame frame;
//	private Image backgroundImage;
//
//	private JLabel settingLbl, volumeLbl;
//	private JSlider volumeSlider;
//	private JLabel languageLbl;
//	private JButton langGerBtn, langEngBtn;
//	private JButton cancelBtn;
//	private JButton saveBtn;
//
//	private Boolean createFile = true;
//	private JList themenAuswahl;
//	private JList unterkategorienAuswahl;
//	private JPanel listPanel;
//	private MyBildPanel detailsPanel;
//	private String[] aktionenKategorien;
//
//	private String[] heldenKategorien;
//	private String[] arenaKategorien;
//	private String[] heldenTafel;
//	private String[] heldenTafelKategorien;
//
//	public InstructionPanel(MyFrame frame, MainFramePanel mainFramePanel) {
//
//		this.frame = frame;
//		setLayout(new BorderLayout());
//		frame.setContentPane(this);
//
//		this.setPreferredSize(new Dimension(1920, 1080));
//
//		detailsPanel = new MyBildPanel();
//		detailsPanel.setBounds(340, 180, 550, 500);
//		detailsPanel.setBackground(Color.gray);
//
//		listPanel = new JPanel();
//		listPanel.setBounds(40, 180, 300, 500);
//		listPanel.setBackground(Color.yellow);
//
//		String kategorien[] = { "Die Heldentafel", "Die Arena", "Helden", "Aktionen", "Verstecke", "Spielegeln" };
//		aktionenKategorien = new String[] { "angreifen", "verstecken", "erforschen", "verzögerug abbauen" };
//		heldenKategorien = new String[] { "Fähigkeiten" };
//		heldenTafelKategorien = new String[] { "Aktionpunkte", "Ausdauer", "Geschichte des helden", "Spielstärke" };
//		arenaKategorien = new String[] { "Verstecke", "Uralte Machine" };
//
//		// JList mit Einträgen wird erstellt
//		themenAuswahl = new JList(kategorien);
//		unterkategorienAuswahl = new JList(aktionenKategorien);
//
//		// JList wird Panel hinzugefügt
//		listPanel.add(themenAuswahl);
//		listPanel.add(unterkategorienAuswahl, BorderLayout.WEST);
//		themenAuswahl.addListSelectionListener(new MyLiSeLi());
//
//		this.add(detailsPanel, BorderLayout.CENTER);
//		this.add(listPanel, BorderLayout.WEST);
//
//		frame.pack();
//	}
//
//	private class MyLiSeLi implements ListSelectionListener {
//
//		@Override
//		public void valueChanged(ListSelectionEvent lse) {
//			JList source = (JList) lse.getSource();
//			String selected = source.getSelectedValue().toString();
//
//			if (selected == "Aktionen") {
//				unterkategorienAuswahl.setListData(aktionenKategorien);
//
//			}
//
//			if (selected.equals("Helden")) {
//
//				detailsPanel.displayHeroLargePanel();
//				detailsPanel.repaint();
//				unterkategorienAuswahl.setListData(heldenKategorien);
//			}
//
//			if (selected == "Die Heldentafel")
//				unterkategorienAuswahl.setListData(heldenTafelKategorien);
//
//			if (selected == "Die Arena")
//				unterkategorienAuswahl.setListData(arenaKategorien);
//		}
//
//	}

}
