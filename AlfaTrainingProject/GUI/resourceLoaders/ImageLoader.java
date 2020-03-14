package resourceLoaders;

import java.awt.Image;
import java.util.ArrayList;

/**
 * TODO: Diese Klasse soll als Singleton alle benötigten Images initialisieren
 * und zur Abfrage bereithalten. Alle anderen Klassen sollen dann, wenn sie ein
 * Bild benötigen, die entsprechende Referenz hierher bekommen. So wird jedes
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
		images = new Image[IMAGEARRAY_SIZE];

		// TODO: Images ins Array laden, dabei für den jeweiligen index einen public
		// static final int definieren, damit man von anderen Klassen mit einem
		// sinnvollen Namen zugreifen kann und den tatsächlichen Index nicht wissen
		// muss.
	}

	public static ImageLoader getInstance() {
		return imageLoaderSingleton;
	}

	public Image getImage(int imageIndex) {

		return images[imageIndex];
	}
}
