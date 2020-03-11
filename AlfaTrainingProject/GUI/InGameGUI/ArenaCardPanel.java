package InGameGUI;

import Arenacards.Arenacards;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 */
public class ArenaCardPanel extends JPanel {

    private Arenacards displayedCard;
    private Image backgroundImage;
    private Image artworkImage;

    private JLabel nameLabel;
    private JTextArea descriptionField;

    private static final double ARTWORK_POSITION_RELATIVE_X = 0.633;
    private static final double ARTWORK_POSITION_RELATIVE_Y = 0.06;
    private static final double ARTWORK_SIZE_RELATIVE_X = 0.325;
    private static final double ARTWORK_SIZE_RELATIVE_Y = 0.5;

    private static final double NAMELABEL_POSITION_RELATIVE_X = 0.633;
    private static final double NAMELABEL_POSITION_RELATIVE_Y = 0.06;
    private static final double NAMELABEL_SIZE_RELATIVE_X = 0.325;
    private static final double NAMELABEL_SIZE_RELATIVE_Y = 0.5;

    private static final double DESCRIPTIONFIELD_POSITION_RELATIVE_X = 0.633;
    private static final double DESCRIPTIONFIELD_POSITION_RELATIVE_Y = 0.06;
    private static final double DESCRIPTIONFIELD_SIZE_RELATIVE_X = 0.325;
    private static final double DESCRIPTIONFIELD_SIZE_RELATIVE_Y = 0.5;

    /**
     * Panel zur Darstellung einer ArenaCard. 
     *
     * @param displayedCard Die darzustellende ArenaCard
     */
    public ArenaCardPanel(Arenacards displayedCard) {
        this.displayedCard = displayedCard;

        artworkImage = displayedCard.getImage();
        
        backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("Arena_Cards/Arenacard_empty.jpg"))
                .getImage();

        setLayout(null);

        initializeTextFields();
    }
    
    /**
     * Zeichnet das Panel. Alle Elemente werden relativ zur aktuellen Panelgr��e
     * angepasst (siehe die entsprechend definierten Konstanten), sodass ein
     * Vergr��ern bzw. Verkleinern problemlos m�glich ist.
     * 
     * @param g 
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        //Hintergrund
        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        
        //Artwork
        g2d.drawImage(artworkImage, (int)(ARTWORK_POSITION_RELATIVE_X * getWidth()),
               (int)(ARTWORK_POSITION_RELATIVE_Y * getHeight()),
               (int)(ARTWORK_SIZE_RELATIVE_X * getWidth()), 
               (int)(ARTWORK_SIZE_RELATIVE_Y * getHeight()), this);
        
        //Namensfeld Bounds setzen
        nameLabel.setBounds((int) (NAMELABEL_POSITION_RELATIVE_X * getWidth()),
                (int) (NAMELABEL_POSITION_RELATIVE_Y * getHeight()),
                (int) (NAMELABEL_SIZE_RELATIVE_X * getWidth()),
                (int) (NAMELABEL_SIZE_RELATIVE_Y * getHeight()));
        
        
        
        //Descriptionfeld Bounds setzen
        descriptionField.setBounds((int) (DESCRIPTIONFIELD_POSITION_RELATIVE_X * getWidth()),
                (int) (DESCRIPTIONFIELD_POSITION_RELATIVE_Y * getHeight()),
                (int) (DESCRIPTIONFIELD_SIZE_RELATIVE_X * getWidth()),
                (int) (DESCRIPTIONFIELD_SIZE_RELATIVE_Y * getHeight()));
    }

    /**
     * Initialisiert die Textfelder, also sowohl den Kartennamen als auch die
     * Beschreibung, welche aus der ArenaCard ausgelesen werden.
     */
    private void initializeTextFields() {
        nameLabel = new JLabel(displayedCard.getName());
        this.add(nameLabel);

        descriptionField = new JTextArea(displayedCard.getDescription());
        descriptionField.setOpaque(false);
        descriptionField.setEditable(false);
        descriptionField.setLineWrap(true);
        descriptionField.setWrapStyleWord(true);
        this.add(descriptionField);
    }

    

}
