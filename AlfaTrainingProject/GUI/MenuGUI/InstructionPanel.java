/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MenuGUI;

import java.awt.Color;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import resourceLoaders.ImageLoader;
import resourceLoaders.ImageName;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Alfa
 */
public class InstructionPanel extends JPanel {

private MainFramePanel parentPanel;
    private JFrame frame;
    private Image backgroundImage;

    private JLabel settingLbl, volumeLbl;
    private JSlider volumeSlider;
    private JLabel languageLbl;
    private JButton langGerBtn, langEngBtn;
    private JButton cancelBtn;
    private JButton saveBtn;

    private Boolean createFile = true;
    private JList themenAuswahl;
    private JList unterkategorienAuswahl;
    private JPanel listPanel;
    private String[] aktionenKategorien; 
    
    private String[] heldenKategorien;
    private String[] arenaKategorien; 
    private String[] heldenTafel; 
    private String[] heldenTafelKategorien;
     
    
    
    
    
    
    public InstructionPanel(MyFrame frame) {
		
        
        this.frame = frame;
	setLayout(new BorderLayout());
        frame.setContentPane(this);
        
        this.setPreferredSize(new Dimension(1920,1080));
                
        
        
                 
        MyBildPanel  detailsPanel = new MyBildPanel();  
        detailsPanel.setBounds(340,180,550,500);    
        detailsPanel.setBackground(Color.gray);
        
        listPanel = new JPanel();  
        listPanel.setBounds(40,180,300,500);    
        listPanel.setBackground(Color.yellow);
        
        
         String kategorien[] = {"Die Heldentafel","Die Arena" ,"Helden","Aktionen", "Verstecke", "Spielegeln"};
         aktionenKategorien = new String []{"angreifen","verstecken" ,"erforschen","verzögerug abbauen"};
         heldenKategorien = new String []{"Fähigkeiten"};
         heldenTafelKategorien = new String []{"Aktionpunkte","Ausdauer","Geschichte des helden","Spielstärke"};
         arenaKategorien = new String []{"Verstecke","Uralte Machine"};
         
          //JList mit Einträgen wird erstellt
        themenAuswahl = new JList(kategorien);
        unterkategorienAuswahl = new JList(aktionenKategorien);
        
        
        
 
        //JList wird Panel hinzugefügt
        listPanel.add(themenAuswahl);
        listPanel.add(unterkategorienAuswahl,BorderLayout.WEST);
        themenAuswahl.addListSelectionListener(new MyLiSeLi());
        
        this.add(detailsPanel,BorderLayout.CENTER);
        this.add(listPanel,BorderLayout.WEST);
		
	frame.pack();
	}
    
    
    private class MyLiSeLi implements ListSelectionListener
    {
        
        

        @Override
        public void valueChanged(ListSelectionEvent lse)
        {
            JList source = (JList)lse.getSource();
            String selected = source.getSelectedValue().toString();
            
            if (selected == "Aktionen")
            {
                unterkategorienAuswahl.setListData(aktionenKategorien);
                
            }
            
            if(selected == "Helden")
                unterkategorienAuswahl.setListData(heldenKategorien);
            
            if(selected == "Die Heldentafel")
                unterkategorienAuswahl.setListData(heldenTafelKategorien);
            
            if(selected == "Die Arena")
                unterkategorienAuswahl.setListData(arenaKategorien);
        }
        
        

       
    }
    
   
}    
