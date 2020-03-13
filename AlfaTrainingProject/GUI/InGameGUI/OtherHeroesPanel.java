package InGameGUI;

import Heroes.Hero;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * Ein Panel, dass mehrere HeroPanelSmall in einem horizontalen FlowLayout
 * gruppiert.
 *
 */
public class OtherHeroesPanel extends JPanel {

	public OtherHeroesPanel(ArrayList<Hero> heroes) {
		setLayout(new GridLayout(1,4,10,0));

		for (Hero h : heroes) {
			if (!h.isPlayerControlled()) {
				add(new HeroPanelSmall(h));
			}
		}

		setOpaque(false);
	}
}
