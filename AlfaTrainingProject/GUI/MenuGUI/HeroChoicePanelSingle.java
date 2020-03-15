package MenuGUI;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import Heroes.Hero;
import InGameGUI.HeroPanelSmall;

public class HeroChoicePanelSingle extends JPanel {

	private Hero displayedHero;
	private HeroPanelSmall picturePanel;
	private JCheckBox checkbox;
	
	private boolean heroSelected;

	public HeroChoicePanelSingle(Hero displayedHero) {
		this.displayedHero = displayedHero;
		this.picturePanel = new HeroPanelSmall(displayedHero);
		
		this.checkbox = new JCheckBox(MyFrame.bundle.getString("PC_Player"), true);
			
		setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		
		add(picturePanel);
		add(checkbox);
	}

	public Hero getDisplayedHero() {
		return displayedHero;
	}

	public HeroPanelSmall getPicturePanel() {
		return picturePanel;
	}

	public JCheckBox getCheckbox() {
		return checkbox;
	}

	public boolean isHeroSelected() {
		return heroSelected;
	}

	public void setHeroSelected(boolean heroSelected) {
		this.heroSelected = heroSelected;
		
		if(heroSelected)
		{
			checkbox.setEnabled(false);
		}
		else {
			checkbox.setEnabled(true);
		}
	}
	
	
	
	
}
