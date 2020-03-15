package MenuGUI;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import Heroes.Hero;

public class HeroChoicePanel extends JPanel {

	private ArrayList<Hero> heroList;

	private ArrayList<HeroChoicePanelSingle> heroChoicePanelSingleList;

	public HeroChoicePanel(ArrayList<Hero> heroList) {
		this.heroList = heroList;

		setLayout(new GridLayout(1, heroList.size(), 100, 0));

		heroChoicePanelSingleList = new ArrayList<>();
		
		HeroChoicePanelSingle hCPS;
		for (Hero h : heroList) {
			h.setCurrentActionPoints(h.getMaxActionPoints());
			
			hCPS = new HeroChoicePanelSingle(h);
			heroChoicePanelSingleList.add(hCPS);
			this.add(hCPS);
		}
	}

	public ArrayList<Hero> getHeroList() {
		return heroList;
	}

	public ArrayList<HeroChoicePanelSingle> getHeroChoicePanelSingleList() {
		return heroChoicePanelSingleList;
	}
	
}
