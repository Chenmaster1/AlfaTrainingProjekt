/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MenuGUI;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import resourceLoaders.ImageLoader;
import resourceLoaders.ImageName;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Heroes.Hero;
import Heroes.HeroBalthur;
import Heroes.HeroDahlia;
import Heroes.HeroFlint;
import Heroes.HeroTolpanLongbeard;
import Heroes.HeroWorok;
import InGameGUI.HeroPanelLarge;

/**
 *
 * @author Alfa
 */
@SuppressWarnings("serial")
public class InstructionPanel extends JPanel {

	private final int VIEW_STARTPOINT_X = 220;
	private final int VIEW_STARTPOINT_Y = 0;
	
	private boolean frameOpened = false;
	
	private MyFrame frame;
	MainFramePanel mainFramePanel;
	private Image backgroundImage;
	
	private JButton btnBack;
	private JScrollPane scrollPaneMainCategory;
	private JScrollPane scrollPaneSubCategory;
	
	DefaultListModel<String> model = new DefaultListModel<>();
	
	private JList<String> listMainCatagory;
	private JList<String> subCategory = new JList<String>(model);
	
	//private JPanel heroPanel;
	//private JPanel arenacardsPanel;
	
	private HeroPanelLarge heroPanel;
	
	private JFrame heroFrame;
	
	ArrayList<JLabel> lstHeroesOrArenacards = new ArrayList<JLabel>();
	
	//fuer size: 20 pro eintrag
	private String[] mainCatagories = new String[] {MyFrame.bundle.getString("instructionsAllHeroes"), 
			MyFrame.bundle.getString("instructionsArenacards"), 
			MyFrame.bundle.getString("instructionsGameBoard")};
	
	private String[] subCatagoryGameBoard = new String[] {MyFrame.bundle.getString("instructionsGameField"),
			MyFrame.bundle.getString("instructionsTower"),
			MyFrame.bundle.getString("instructionsHideField"),
			MyFrame.bundle.getString("instructionsArenacard"),
			MyFrame.bundle.getString("instructionsOverviewField"),
			MyFrame.bundle.getString("instructionsOwnHero"),
			MyFrame.bundle.getString("instructionsEnemies"),
			MyFrame.bundle.getString("instructionsHeroBoard"),
			MyFrame.bundle.getString("instructionsAttackDice"),
			MyFrame.bundle.getString("instructionsHideDice")};
	
	public InstructionPanel(MyFrame frame, MainFramePanel mainFramePanel) {
		backgroundImage = ImageLoader.getInstance().getImage(ImageName.MENU_BACKGROUND_BLURRY);
		this.frame = frame;
		this.mainFramePanel = mainFramePanel;
		frame.setContentPane(this);
		setLayout(null);
		
		
		//btnBack = new MyButton(MyFrame.bundle.getString("btnCancel"), new ImageIcon(ImageLoader.getInstance().getImage(ImageName.BUTTON)));
		btnBack = new JButton(MyFrame.bundle.getString("btnCancel"));
		
		initializeButtons();
		initializeMainList();
		initializeScrollPanes();
		setPreferredSize(frame.getSize());
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

				
				switch(((JList<String>) e.getSource()).getSelectedIndex()) {

					case 0:
						
						//TODO Hier namen aller Helden anzeigen und in der 2. liste anzeigen
						model.removeAllElements();
						lstHeroesOrArenacards.removeAll(lstHeroesOrArenacards);
						//Hier alle Helden einfügen
						showAllHeroes();
						
						scrollPaneSubCategory.setVisible(false);
						//scrollPaneSubCategory.setSize(100, subCatagoryGameBoard.length * 20);
						//scrollPaneSubCategory.setVisible(true);
						break;
					case 1:
						//TODO Hier namen aller Arenakarten anzeigen und in der 2. liste anzeigen
						if(lstHeroesOrArenacards.size() >0) {
//							for(JLabel label : lstHeroesOrArenacards)
//								label.setVisible(false);;
							Component[] components = getComponents();
							int componentsCount = components.length;
							for(int i = 0; i < componentsCount; i++) {
								if(components[i] instanceof JLabel) {
									remove(i);
									remove(components[i]);
									i--;
									componentsCount--;
								}
							}
							frame.repaint();
							lstHeroesOrArenacards.removeAll(lstHeroesOrArenacards);
						}
						model.removeAllElements();
						
						scrollPaneSubCategory.setVisible(false);
						//scrollPaneSubCategory.setSize(100, subCatagoryGameBoard.length * 20);
						//scrollPaneSubCategory.setVisible(true);
						break;
					case 2:
						//TODO Spielbrett anzeigen und entsprechende Felder zu auswahl stellen
						model.removeAllElements();
						for(int i = 0; i < subCatagoryGameBoard.length; i++)
							model.addElement(subCatagoryGameBoard[i]);
						scrollPaneSubCategory.setSize(100, subCatagoryGameBoard.length * 20);
						scrollPaneSubCategory.setVisible(true);
						break;
					}
				
				revalidate();
			}
		});
		//listMainCatagory.setLocation(0,40);		
		//add(listMainCatagory);
	}
	
	private void initializeScrollPanes() {
		scrollPaneMainCategory = new JScrollPane(listMainCatagory);
		scrollPaneMainCategory.setSize(100,60);
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
	
	private void showAllHeroes() {
		ArrayList<Hero> allHeroes = new ArrayList<Hero>();
		allHeroes.add(new HeroBalthur());
		allHeroes.add(new HeroDahlia());
		allHeroes.add(new HeroFlint());
		allHeroes.add(new HeroTolpanLongbeard());
		allHeroes.add(new HeroWorok());
		
		for(Hero hero : allHeroes) {
			JLabel label = new JLabel(hero.getName());
			lstHeroesOrArenacards.add(label);
			ImageIcon ii = new ImageIcon(hero.getAvatar());
			label.setIcon(ii);
			label.setSize(ii.getIconWidth(), ii.getIconHeight());
			label.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					onLabelHeroClicked(((JLabel) e.getSource()).getText(), allHeroes);
				}
			});
			//label.setFocusable(true);
			
		}
		int multiplierY = 0;
		int multiplierX = 0;
		
		for(JLabel label : lstHeroesOrArenacards) {
			if(!(VIEW_STARTPOINT_X + (label.getWidth() * (multiplierX+1)) > frame.getWidth())) {

				label.setLocation(VIEW_STARTPOINT_X + label.getWidth() * multiplierX, VIEW_STARTPOINT_Y + label.getHeight() *  multiplierY);
				add(label);
				multiplierX++;
			}else {
				multiplierY++;
				multiplierX = 0;
				label.setLocation(VIEW_STARTPOINT_X + label.getWidth() * multiplierX, VIEW_STARTPOINT_Y + label.getHeight() *  multiplierY);
				add(label);
				
			}

		}
		repaint();
	}
	
	private void onLabelHeroClicked(String heroName, ArrayList<Hero> heroes) {
		
		if(!frameOpened) {
			Hero selectedHero = null;
			for(Hero hero : heroes) {
				if(hero.getName().equals(heroName)) {
					selectedHero = hero;
				}
			}
			
			heroPanel = new HeroPanelLarge(selectedHero);
			
			heroPanel.setSize(558, 393);
			
			heroFrame = new JFrame();
			heroFrame.setSize(558, 393);
			heroFrame.setLocationRelativeTo(frame);
//			heroFrame.setLocation(frame.getWidth()/2 - heroFrame.getWidth()/2, 
//					frame.getHeight()/2 - heroFrame.getHeight()/2);
			heroFrame.setContentPane(heroPanel);
			heroFrame.setUndecorated(true);
			heroFrame.setVisible(true);
			
			frame.revalidate();
			frame.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					heroFrame.dispose();
					frameOpened = false;
					frame.removeMouseListener(frame.getMouseListeners()[0]);
				}
			});
			frameOpened = true;
		}
		
	}
	
}
