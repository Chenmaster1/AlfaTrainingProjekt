package InGameGUI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import Heroes.Hero;

public class HeroPanelLarge extends JPanel {
	private Hero displayedHero;
	private Image backgroundImage;

        //TODO: Passende Werte finden bzw. koordinieren
	private static final double AVATARPOSITION_RELATIVE_X = 0.7;
	private static final double AVATARPOSITION_RELATIVE_Y = 0.1;
	private static final double AVATARSIZE_RELATIVE_X = 0.4;
	private static final double AVATARSIZE_RELATIVE_Y = 0.7;

	public HeroPanelLarge(Hero hero) {
		displayedHero = hero;
		backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Hero_Front_Empty.png"))
				.getImage();

	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		// Hero-Dummypanel als Hintergrund
		g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

		// Overlay für den Heldenavatar
		g2d.drawImage(displayedHero.getAvatar().getImage(), (int) (AVATARPOSITION_RELATIVE_X * getWidth()),
				(int) (AVATARPOSITION_RELATIVE_Y * getHeight()), (int) (AVATARSIZE_RELATIVE_X * getWidth()),
				(int) (AVATARSIZE_RELATIVE_Y * getHeight()), this);

		// Overlays für Hitpoints
		drawHitPointIcons(g2d);

		// Overlays für Actionpoints
		drawActionPointIcons(g2d);

		// Overlays für Delaytokens
		drawDelayTokenIcons(g2d);
	}

	private void drawDelayTokenIcons(Graphics2D g2d) {
		// TODO DelayTokens zeichnen

	}

	private void drawActionPointIcons(Graphics2D g2d) {
		// TODO Hitpoints zeichnen

	}

	private void drawHitPointIcons(Graphics2D g2d) {
		// TODO Actionpoints zeichnen

	}
}
