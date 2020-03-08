package InGameGUI;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

import Heroes.Hero;

public class HeroPanelSmall extends JPanel {
	private Hero displayedHero;

	public HeroPanelSmall(Hero hero) {
		displayedHero = hero;

		setLayout(null);

	}

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		// Avatar als Hintergrund
		g2d.drawImage(displayedHero.getAvatar().getImage(), 0, 0, getWidth(), getHeight(), this);

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
