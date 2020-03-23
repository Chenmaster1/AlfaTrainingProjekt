package InGameGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import MenuGUI.MainFramePanel;
import MenuGUI.MyFrame;
import SoundThread.SoundController;
import resourceLoaders.ImageLoader;
import resourceLoaders.ImageName;

/**
 * Das Gesamtpanel, dass während des Spiels gezeigt wird. Beinhaltet das
 * eigentliche Spielfeld sowie die Sidebar mit Helden, Aktionen usw.
 *
 */
public class GamePanel extends JPanel {
    
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

	private MapPanel mapPanel;
	private GameSidePanel gameSidePanel;
	private final JFrame mainFrame;
	private MainFramePanel mainFramePanel;
        //neu Holger Screenaufbau
    private int MAPPANEL_STANDARD_SIZE = ((int) (dim.getWidth() * 0.5625));

        //alt Peter Screenaufbau
	private int GAMESIDEPANEL_STANDARD_SIZE_X = ((int) (dim.getWidth() * 0.4375));
	private int GAMESIDEPANEL_STANDARD_SIZE_Y = ((int) (dim.getHeight()));

        /**alt Peter Screenaufbau
	private static final int MAPPANEL_STANDARD_SIZE = 1080;

	private static final int GAMESIDEPANEL_STANDARD_SIZE_X = 840;
	private static final int GAMESIDEPANEL_STANDARD_SIZE_Y = 1080;
	*/
	
	//Standard 20, je höher, desto langsamer sind die Animationen
	public final static int ANIMATION_FRAME_PERIOD_MAPPANEL = 20;
	public final static int ANIMATION_FRAME_PERIOD_DICEPANELS = 10;

	public GamePanel(MapPanel mp, GameSidePanel gsp, JFrame frame, MainFramePanel mainFramePanel) {
		super();
		this.mainFrame = frame;
		this.mainFramePanel = mainFramePanel;
		// funktioniert noch nicht
//		String name = "Escape"; // I think the exact name doesn't matter
//		
//		getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
//                "showDialog");
//		getActionMap().put("showDialog", new AbstractAction() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				int input = JOptionPane.showOptionDialog(getParent(), 
//						"Beenden?", 
//						"", 
//						JOptionPane.OK_CANCEL_OPTION, 
//						JOptionPane.QUESTION_MESSAGE, 
//						null, 
//						new String[]{"Abbrechen","Beenden"}, 
//						"Beenden");
//				System.out.println(input);
//			}
//		});
//		isFocusable();
		// frame.setContentPane(this);
		mapPanel = mp;
		mapPanel.setPreferredSize(new Dimension(MAPPANEL_STANDARD_SIZE, MAPPANEL_STANDARD_SIZE));

		gameSidePanel = gsp;
		gameSidePanel.setPreferredSize(new Dimension(GAMESIDEPANEL_STANDARD_SIZE_X, GAMESIDEPANEL_STANDARD_SIZE_Y));

		setLayout(new BorderLayout());

		add(mapPanel, BorderLayout.LINE_START);
		add(gameSidePanel, BorderLayout.LINE_END);

		// Listener zum resizen
		addComponentListener(new ComponentAdapter() {
			
			
			@Override
			public void componentResized(ComponentEvent e) {
				resizeGamePanel();
			}
		});
		

		
		//Funktioniert so noch nicht
		
//		mainFrame.addWindowStateListener(new WindowStateListener() {
//			
//
//			@Override
//			public void windowStateChanged(WindowEvent e) {
//				if ((e.getNewState() & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH){
//				      resizeGamePanel();
//				}
//			}
//
//		});
	}

	@Override
	protected void paintComponent(Graphics grphcs) {

	}
	
	public void onExitClicked() {
		mainFrame.setContentPane(mainFramePanel);
		SoundController.setBackgroundMusic("Intro_Main.mp3");
		
	}
	
	private void resizeGamePanel() {
		// MapPanel quadratisch links hinein
		mapPanel.setPreferredSize(new Dimension(getHeight(), getHeight()));
//        System.out.println("MapPanel(W/H): " + mapPanel.getWidth() + " " + mapPanel.getHeight()); 

		// GameSidePanel füllt den Rest
		gameSidePanel.setPreferredSize(new Dimension(getWidth() - getHeight(), getHeight()));
//        System.out.println("GameSidePanel(W/H): " + gameSidePanel.getWidth() + " " + gameSidePanel.getHeight());

	}

	public MapPanel getMapPanel() {
		return mapPanel;
	}

	public GameSidePanel getGameSidePanel() {
		return gameSidePanel;
	}
}
