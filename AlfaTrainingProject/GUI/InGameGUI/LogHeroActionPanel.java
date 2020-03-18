package InGameGUI;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class LogHeroActionPanel extends JPanel
{
   private JLabel labelLogHeroAction;
    
    public LogHeroActionPanel()
    {
        setLayout(null);
        labelLogHeroAction = new JLabel("logoLog");
        labelLogHeroAction.setBounds(5, 5, 150, 50);
        add(labelLogHeroAction);
    }

}
