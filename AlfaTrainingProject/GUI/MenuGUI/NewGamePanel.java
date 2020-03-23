
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
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
import SoundThread.SoundController;
import java.awt.Toolkit;
import resourceLoaders.ImageLoader;
import resourceLoaders.ImageName;

public class NewGamePanel extends JPanel {

	private MainFramePanel mainFramePanel;
	private JFrame mainFrame;
	private Image backgroundImage;
	private Image BackgroundNewGame;
	private Image ArenaCardSwitch;
	private JButton cancelBtn;
	private JButton newGameBtn;
	private JLabel disabledHideoutsLabel;
	private JComboBox disabledHideoutsComboBox;

	private ArrayList<Hero> heroList;
	private HeroChoicePanel heroChoicePanel;
	private HeroPanelLarge heroPanelLarge;
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

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
		heroChoicePanel.setBounds((int) (dim.getWidth() * 0.1822916), (int) (dim.getHeight() * 0.61574074), (int) (dim.getWidth() * 0.63541666666), (int) (dim.getHeight() * 0.18518518));
		this.add(heroChoicePanel);

		// Zentrale Heldenvorschau
		heroPanelLarge = new HeroPanelLarge(heroList.get(0));
                System.out.println(getWidth());
		heroPanelLarge.setBounds((int) (dim.getWidth() * 0.33541666), (int) (dim.getHeight() * 0.202777), (int) (dim.getWidth() * 0.30885), (int) (dim.getHeight() * 0.38888888));
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

		disabledHideoutsLabel = new JLabel(MyFrame.bundle.getString("lblDisabledHideouts"));
		
		String[] disabledHideoutsChoices = {"0","1","2","3","4","5","6","7","8","9","10"};
		disabledHideoutsComboBox = new JComboBox(disabledHideoutsChoices);

		fillPanel();

		this.setPreferredSize(new Dimension(1920, 1080));
		frame.setContentPane(this);
		frame.pack();
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, this);
		g.drawImage(BackgroundNewGame, (int) (dim.getWidth() * 0.10416666666), (int) (dim.getHeight() * 0.11574074), (int) (dim.getWidth() * 0.7916666), (int) (dim.getHeight() * 0.768518), this);
//		g.drawImage(ArenaCardSwitch, 346, 247, this);
	}

	private void fillPanel() {

//		JCheckBox arenacardcheckBox = new JCheckBox(MyFrame.bundle.getString("Arena-Cards"), false);
//		arenacardcheckBox.setBounds(346, 570, 176, 30);

//		JCheckBox Playcardmode_BasiccheckBox = new JCheckBox(MyFrame.bundle.getString("Playcardmode_Basic"), true);
//		Playcardmode_BasiccheckBox.setBounds(848, 532, 150, 30);
//		JCheckBox Playcardmode_AdvancedcheckBox = new JCheckBox(MyFrame.bundle.getString("Playcardmode_Advanced"),
//				false);
//		Playcardmode_AdvancedcheckBox.setBounds(848, 566, 150, 30);

		disabledHideoutsLabel.setBounds((int) (dim.getWidth() * 0.6953125), (int) (dim.getHeight() * 0.3472222222),(int) (dim.getWidth() * 0.111458333), (int) (dim.getHeight() * 0.0333333333));

		disabledHideoutsComboBox.setBounds((int) (dim.getWidth() * 0.6953125), (int) (dim.getHeight() * 0.388888888), (int) (dim.getWidth() * 0.111458333), (int) (dim.getHeight() * 0.0333333333));

		newGameBtn.setBounds((int) (dim.getWidth() * 0.6953125), (int) (dim.getHeight() * 0.449074074), (int) (dim.getWidth() * 0.111458333), (int) (dim.getHeight() * 0.0333333333));
		newGameBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				newGameClicked();
			}
		});

		cancelBtn.setBounds((int) (dim.getWidth() * 0.6953125), (int) (dim.getHeight() * 0.49074074), (int) (dim.getWidth() * 0.111458333), (int) (dim.getHeight() * 0.0333333333));
		cancelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cancelClicked();
			}
		});

		add(disabledHideoutsLabel);
		add(disabledHideoutsComboBox);
		add(newGameBtn);
		add(cancelBtn);

//		add(arenacardcheckBox);
//		add(Playcardmode_BasiccheckBox);
//		add(Playcardmode_AdvancedcheckBox);
	}

	private void cancelClicked() {
		mainFrame.setContentPane(mainFramePanel);
		mainFrame.repaint();
	}

	private void newGameClicked() {

		// Werte auslesen und daraus Singleplayergame erstellen
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

		int disabledHideoutsNumber = disabledHideoutsComboBox.getSelectedIndex();
		SingleplayerGame singlePlayerGame = SingleplayerGameCreator.createSingleplayerGame(gameHeroes, playerHero,
				disabledHideoutsNumber, mainFrame, mainFramePanel);

		SoundController.setBackgroundMusic("Fight_Theme.mp3");
		singlePlayerGame.startGame();

	}
}
