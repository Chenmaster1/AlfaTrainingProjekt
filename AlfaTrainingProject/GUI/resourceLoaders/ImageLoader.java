package resourceLoaders;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import resourceLoaders.ImageName;

/**
 * Diese Klasse soll als Singleton alle benötigten Images initialisieren
 * und zur Abfrage bereithalten. Alle anderen Klassen sollen dann, wenn sie ein
 * Bild benötigen, die entsprechende Referenz aus der Singleton-Instanz
 * bekommen. So wird jedes Image nur einmal im Speicher gehalten.
 * 
 * @author Peter
 *
 */
public class ImageLoader {

	private static final ImageLoader imageLoaderSingleton = new ImageLoader();

	private Image[] images;

	private ImageLoader() {
		images = new Image[ImageName.values().length];

		// Images ins Array laden. 

		images[ImageName.MENU_BACKGROUND.ordinal()] = new ImageIcon(
				getClass().getClassLoader().getResource("Images/BackGround_FullScreen.png")).getImage();
		images[ImageName.ARENACARD_EMPTY.ordinal()] = new ImageIcon(
				getClass().getClassLoader().getResource("Arena_Cards/Arenacard_empty.jpg")).getImage();
		images[ImageName.TOWER
				.ordinal()] = new ImageIcon(getClass().getClassLoader().getResource("Gameboard/Tower.png")).getImage();
		images[ImageName.SPOT_LEFT.ordinal()] = new ImageIcon(
				getClass().getClassLoader().getResource("Gameboard/spot_left.png")).getImage();
		images[ImageName.SPOT_TOWER.ordinal()] = new ImageIcon(
				getClass().getClassLoader().getResource("Gameboard/spot_tower.png")).getImage();
		images[ImageName.GAMEBOARD_RIGHT.ordinal()] = new ImageIcon(
				getClass().getClassLoader().getResource("Gameboard/Gameboard_Right.png")).getImage();
		images[ImageName.HERO_FRONT_EMPTY_TALL.ordinal()] = new ImageIcon(
				getClass().getClassLoader().getResource("Hero_Card/Hero_Front_Empty_tall.jpg")).getImage();
		images[ImageName.HEART_ACTIVATED.ordinal()] = new ImageIcon(
				getClass().getClassLoader().getResource("Hero_Card/Heart_Activated.png")).getImage();
		images[ImageName.HEART_DEACTIVATED.ordinal()] = new ImageIcon(
				getClass().getClassLoader().getResource("Hero_Card/Heart_Deactivated.png")).getImage();
		images[ImageName.ACTION_ACTIVATED.ordinal()] = new ImageIcon(
				getClass().getClassLoader().getResource("Hero_Card/Action_Activated.png")).getImage();
		images[ImageName.ACTION_DEACTIVATED.ordinal()] = new ImageIcon(
				getClass().getClassLoader().getResource("Hero_Card/Action_Deactivated.png")).getImage();
		images[ImageName.DELAY
				.ordinal()] = new ImageIcon(getClass().getClassLoader().getResource("Hero_Card/Delay.png")).getImage();
		images[ImageName.HIDEOUT_CARD.ordinal()] = new ImageIcon(
				getClass().getClassLoader().getResource("Gameboard/Hideout_Card.jpg")).getImage();
		images[ImageName.SPOT_RIGHT.ordinal()] = new ImageIcon(
				getClass().getClassLoader().getResource("Gameboard/spot_right.png")).getImage();
		images[ImageName.SPOT_HIDEOUT.ordinal()] = new ImageIcon(
				getClass().getClassLoader().getResource("Gameboard/spot_hideout.png")).getImage();
		images[ImageName.GAMEBOARD_EMPTY.ordinal()] = new ImageIcon(
				getClass().getClassLoader().getResource("Gameboard/Gameboard_Empty.png")).getImage();
		images[ImageName.TOWER_AIM.ordinal()] = new ImageIcon(
				getClass().getClassLoader().getResource("Gameboard/Tower_Aim.png")).getImage();
		images[ImageName.MENU_BACKGROUND_BLURRY.ordinal()] = new ImageIcon(
				getClass().getClassLoader().getResource("Images/BackGround_FullScreenBlurred.png")).getImage();
		images[ImageName.BUTTON.ordinal()] =new ImageIcon(getClass().getClassLoader().getResource("Images/Button.png")).getImage();
	}

	public static ImageLoader getInstance() {
		return imageLoaderSingleton;
	}

	public Image getImage(ImageName name) {

		return images[name.ordinal()];
	}
}
