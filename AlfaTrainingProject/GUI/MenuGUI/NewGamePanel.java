
package MenuGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JCheckBox;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import GameLogic.SingleplayerGame;
import GameLogic.SingleplayerGameCreator;
import Heroes.Hero;
import Heroes.HeroBalthur;
import Heroes.HeroDahlia;
import Heroes.HeroFlint;
import Heroes.HeroTolpanLongbeard;
import Heroes.HeroWorok;
import InGameGUI.GamePanel;
import InGameGUI.GameSidePanel;
import InGameGUI.HeroPanelLarge;
import InGameGUI.MapPanel;
import SoundThread.MP3Runnable;
import resourceLoaders.ImageLoader;
import resourceLoaders.ImageName;

public class NewGamePanel extends JPanel {

	private MainFramePanel mainFramePanel;
	private JFrame mainFrame;
	private Image backgroundImage;
	private Image BackgroundNewGame;
	private Image PlayerImageBackground;
	private Image ArenaCardSwitch;
	private JButton cancelBtn;
	private JButton newGameBtn;
	private Image AvatarImage1;
	private Image AvatarImage2;
	private Image AvatarImage3;
	private Image AvatarImage4;
	private Image AvatarImage5;
	private Image Deactived_AvatarImage1;
	private Image Deactived_AvatarImage2;
	private Image Deactived_AvatarImage3;
	private Image Deactived_AvatarImage4;
	private Image Deactived_AvatarImage5;

	private ArrayList<Hero> heroList;
	private HeroChoicePanel heroChoicePanel;
	private HeroPanelLarge heroPanelLarge;

	public NewGamePanel(MainFramePanel parentPanel, JFrame frame) {
		backgroundImage = ImageLoader.getInstance().getImage(ImageName.MENU_BACKGROUND_BLURRY);
		this.mainFramePanel = parentPanel;
		this.mainFrame = frame;

		setLayout(null);

		// Liste auswählbarer Helden erstellen
		heroList = new ArrayList<Hero>();
		heroList.add(new HeroBalthur());
		heroList.add(new HeroDahlia());
		heroList.add(new HeroFlint());
		heroList.add(new HeroTolpanLongbeard());
		heroList.add(new HeroWorok());

		// Kleine Avatarbilder am unteren Rand
		heroChoicePanel = new HeroChoicePanel(heroList);
		heroChoicePanel.setBounds(350, 665, 1220, 200);
		this.add(heroChoicePanel);

		// Zentrale Heldenvorschau
		heroPanelLarge = new HeroPanelLarge(heroList.get(0));
		heroPanelLarge.setBounds(644, 219, 593, 420);
		this.add(heroPanelLarge);

		// Listener für die kleinen Bilder erstellen
		for (int i = 0; i < heroChoicePanel.getHeroChoicePanelSingleList().size(); i++) {
			final Hero connectedHero = heroList.get(i);
			final int index = i;
			heroChoicePanel.getHeroChoicePanelSingleList().get(i).getPicturePanel()
					.addMouseListener(new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							heroPanelLarge.setDisplayedHero(connectedHero);
							heroChoicePanel.select(index);
						}
					});
		}

		// Heldenauswahl initialisieren (erster Held in der Liste)
		heroChoicePanel.select(0);

		cancelBtn = new JButton(MyFrame.bundle.getString("btnCancel"));
		newGameBtn = new JButton(MyFrame.bundle.getString("btnNew"));
		BackgroundNewGame = new ImageIcon(getClass().getClassLoader().getResource("Gameboard/2x2px_white.jpg"))
				.getImage();

//		PlayerImageBackground = new ImageIcon(
//				getClass().getClassLoader().getResource("Hero_Card/Hero_Front_Empty_tall.jpg")).getImage()
//						.getScaledInstance(593, 420, java.awt.Image.SCALE_SMOOTH);
//		ArenaCardSwitch = new ImageIcon(getClass().getClassLoader().getResource("Gameboard/Arena_Card.jpg")).getImage()
//				.getScaledInstance(176, 293, java.awt.Image.SCALE_SMOOTH);
//		AvatarImage1 = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Avatar_Balthur.jpg")).getImage()
//				.getScaledInstance(195, 204, java.awt.Image.SCALE_SMOOTH);
//		Deactived_AvatarImage1 = new ImageIcon(
//				getClass().getClassLoader().getResource("Hero_Card/Deactivated_Avatar_Balthur.jpg")).getImage()
//						.getScaledInstance(195, 204, java.awt.Image.SCALE_SMOOTH);
//		AvatarImage2 = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Avatar_Dahlia.jpg")).getImage()
//				.getScaledInstance(195, 204, java.awt.Image.SCALE_SMOOTH);
//		AvatarImage3 = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Avatar_Flint.jpg")).getImage()
//				.getScaledInstance(195, 204, java.awt.Image.SCALE_SMOOTH);
//		AvatarImage4 = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Avatar_Talpan.jpg")).getImage()
//				.getScaledInstance(195, 204, java.awt.Image.SCALE_SMOOTH);
//		AvatarImage5 = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Avatar_Worok.jpg")).getImage()
//				.getScaledInstance(195, 204, java.awt.Image.SCALE_SMOOTH);
//		
		/**
		 * Deactived_AvatarImage1 = new
		 * ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Deactived_Avatar_Balthur.jpg")).getImage();
		 * Deactived_AvatarImage2 = new
		 * ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Deactived_Avatar_Dahlia.jpg")).getImage();
		 * Deactived_AvatarImage3 = new
		 * ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Deactived_Avatar_Flint.jpg")).getImage();
		 * Deactived_AvatarImage4 = new
		 * ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Deactived_Avatar_Talpan.jpg")).getImage();
		 * Deactived_AvatarImage5 = new
		 * ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Deactived_Avatar_Worok.jpg")).getImage();
		 */

		fillPanel();

		this.setPreferredSize(new Dimension(1920, 1080));
		frame.setContentPane(this);
		frame.pack();
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, this);
		g.drawImage(BackgroundNewGame, 200, 125, 1520, 830, this);
//		g.drawImage(PlayerImageBackground, 644, 219, this);
//		g.drawImage(ArenaCardSwitch, 346, 247, this);
//		g.drawImage(AvatarImage1, 1016, 243, this);
//		g.drawImage(Deactived_AvatarImage1, 250, 665, this);
//		g.drawImage(AvatarImage2, 546, 665, this);
//		g.drawImage(AvatarImage3, 834, 665, this);
//		g.drawImage(AvatarImage4, 1126, 665, this);
//		g.drawImage(AvatarImage5, 1416, 665, this);
	}

	private void fillPanel() {
//		setSize(frame.getSize());

//		JCheckBox checkBox1 = new JCheckBox(MyFrame.bundle.getString("PC_Player"), false);
//		JCheckBox checkBox2 = new JCheckBox(MyFrame.bundle.getString("PC_Player"), true);
//		JCheckBox checkBox3 = new JCheckBox(MyFrame.bundle.getString("PC_Player"), true);
//		JCheckBox checkBox4 = new JCheckBox(MyFrame.bundle.getString("PC_Player"), true);
//		JCheckBox checkBox5 = new JCheckBox(MyFrame.bundle.getString("PC_Player"), true);

//		JCheckBox arenacardcheckBox = new JCheckBox(MyFrame.bundle.getString("Arena-Cards"), false);
//		arenacardcheckBox.setBounds(346, 570, 176, 30);

//		JCheckBox Playcardmode_BasiccheckBox = new JCheckBox(MyFrame.bundle.getString("Playcardmode_Basic"), true);
//		Playcardmode_BasiccheckBox.setBounds(848, 532, 150, 30);
//		JCheckBox Playcardmode_AdvancedcheckBox = new JCheckBox(MyFrame.bundle.getString("Playcardmode_Advanced"),
//				false);
//		Playcardmode_AdvancedcheckBox.setBounds(848, 566, 150, 30);

//		checkBox1.setBounds(270, 885, 170, 40);
//		checkBox2.setBounds(560, 885, 170, 40);
//		checkBox3.setBounds(846, 885, 170, 40);
//		checkBox4.setBounds(1146, 885, 170, 40);
//		checkBox5.setBounds(1430, 885, 170, 40);

		newGameBtn.setBounds(1335, 485, 214, 36);
		newGameBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newGameClicked();
			}
		});

		cancelBtn.setBounds(1335, 530, 214, 36);
		cancelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cancelClicked();
			}
		});
		add(newGameBtn);
		add(cancelBtn);
//		add(checkBox1);
//		add(checkBox2);
//		add(checkBox3);
//		add(checkBox4);
//		add(checkBox5);
//		add(arenacardcheckBox);
//		add(Playcardmode_BasiccheckBox);
//		add(Playcardmode_AdvancedcheckBox);
	}

	private void cancelClicked() {
		mainFrame.setContentPane(mainFramePanel);
		mainFrame.repaint();
	}

	private void newGameClicked() {
		// MUKKE

		new Thread(new MP3Runnable("Fight_Theme.mp3", true,
				alfatrainingprojekt.AlfaTrainingProjekt.musicTitle.getVolume())).start();
		
		alfatrainingprojekt.AlfaTrainingProjekt.musicTitle.stopPlayer();

		// TODO Werte auslesen und daraus Singleplayergame erstellen
		ArrayList<Hero> gameHeroes = new ArrayList<>();
		Hero playerHero = null;
		for (int i = 0; i < heroChoicePanel.getHeroChoicePanelSingleList().size(); i++) {
			if (heroChoicePanel.getHeroChoicePanelSingleList().get(i).isHeroSelected()) {
				playerHero = heroList.get(i);
			} else {
				if (heroChoicePanel.getHeroChoicePanelSingleList().get(i).getCheckbox().isSelected()) {
					heroList.get(i).setCurrentActionPoints(0);
					gameHeroes.add(heroList.get(i));
				}
			}
		}
		// Spielerheld zuletzt, damit die Reihenfolge stimmt
		playerHero.setPlayerControlled(true);
		playerHero.setCurrentActionPoints(0);
		gameHeroes.add(playerHero);

		SingleplayerGame singlePlayerGame = SingleplayerGameCreator.createSingleplayerGame(mainFrame, gameHeroes,
				playerHero, mainFramePanel);

		singlePlayerGame.startGame();
	}
}
