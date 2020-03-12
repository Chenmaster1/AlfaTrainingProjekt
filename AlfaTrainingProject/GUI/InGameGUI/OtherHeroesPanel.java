package InGameGUI;

import Heroes.Hero;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.JPanel;

/**
 * Ein Panel, dass mehrere HeroPanelSmall in einem horizontalen FlowLayout
 * gruppiert.
 *
 * TODO: Evtl. ein GridLayout benutzen und die Größe des HeroPanelSmall von der
 * Größe dieses Panels abhängig machen, statt konstant in der Klasse
 */
public class OtherHeroesPanel extends JPanel {

	public OtherHeroesPanel(ArrayList<Hero> heroes) {
		super(new FlowLayout(FlowLayout.CENTER, 10, 0));

		for (Hero h : heroes) {
			if (!h.isPlayerControlled()) {
				add(new HeroPanelSmall(h));
			}
		}

		setOpaque(false);
	}
}
