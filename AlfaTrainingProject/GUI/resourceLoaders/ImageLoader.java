package resourceLoaders;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import resourceLoaders.ImageName;

/**
 * TODO: Diese Klasse soll als Singleton alle benötigten Images initialisieren
 * und zur Abfrage bereithalten. Alle anderen Klassen sollen dann, wenn sie ein
 * Bild benötigen, die entsprechende Referenz aus der Singleton-Instanz bekommen. So wird jedes
 * Image nur einmal im Speicher gehalten.
 * 
 * @author Peter
 *
 */
public class ImageLoader {

	private static final ImageLoader imageLoaderSingleton = new ImageLoader();

	private static final int IMAGEARRAY_SIZE = 50; // je nach Bedarf erhöhen

	private Image[] images;

	private ImageLoader() {
		images = new Image[ImageName.values().length];

		// TODO: Images ins Array laden. Dafür das ImageName enum erweitern, damit man
		// von anderen Klassen mit einem sinnvollen Namen zugreifen kann und den
		// tatsächlichen Index nicht wissen muss.

		images[ImageName.MENU_BACKGROUND.ordinal()] = new ImageIcon(
				getClass().getClassLoader().getResource("Images/BackGround_FullScreen.png")).getImage();
	}

	public static ImageLoader getInstance() {
		return imageLoaderSingleton;
	}

	public Image getImage(ImageName name) {

		return images[name.ordinal()];
	}
}
