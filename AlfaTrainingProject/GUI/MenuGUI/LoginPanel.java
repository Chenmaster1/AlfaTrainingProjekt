package MenuGUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicBorders;

import Database.Database;
import Database.Queries;
import SoundThread.MP3Runnable;
import SoundThread.SoundController;
import alfatrainingprojekt.AlfaTrainingProjekt;
import resourceLoaders.ImageLoader;
import resourceLoaders.ImageName;

@SuppressWarnings("serial")
public class LoginPanel extends JPanel {

	private final boolean TESTVERSION = true;

	private boolean isInitiated = false;

	private final int BTN_WIDTH = 150;
	private final int BTN_HEIGHT = 30;

	private Image backgroundImage;
	private MyFrame frame;

	private JLabel lblHint;

	JLabel lblUser;
	JTextField txtUser;
	JLabel lblPw;
	JPasswordField txtPassword;

	JButton btnRegister;
	JButton btnLogin;
	JButton btnExit;
	
	private File file;
	private String path;
	
	public LoginPanel(MyFrame frame) {
		this.frame = frame;
		setLayout(null);
		frame.setContentPane(this);
//		backgroundImage = new ImageIcon(getClass().getClassLoader().getResource("Images/BackGround_FullScreenBlurred.png")).getImage();
		backgroundImage = ImageLoader.getInstance().getImage(ImageName.MENU_BACKGROUND_BLURRY);
    	boolean exists = false;
    	
    	try {
    	    Process p =  Runtime.getRuntime().exec("reg query \"HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\Shell Folders\" /v personal");
    	    p.waitFor();

    	    InputStream in = p.getInputStream();
    	    byte[] b = new byte[in.available()];
    	    in.read(b);
    	    in.close();

    	    path = new String(b);
    	    path = path.split("\\s\\s+")[4];
    	    path += "\\HeroesOfTheArena";
    	} catch(Throwable t) {
    	    t.printStackTrace();
    	}
    	ImageLoader.getInstance();
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, this);

		// erklaeren warum

		if (!isInitiated) {
			initializeComponents();
			isInitiated = true;
		}else {
			if(!TESTVERSION){
				if(Files.exists(Paths.get(path + "\\hota.txt"))) {
					try {
						
						BufferedReader br = new BufferedReader(new FileReader(path + "\\hota.txt"));
						String name = br.readLine();
						String password = br.readLine();
						ResultSet rs = Database.getInstance().executeQuery(Queries.loginUser(name));
						rs.next();
						if (rs.getString(1).equals(password)) {
							switchToMainFramePanel();
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}		
		}
	}

	private void initializeComponents() {

		lblUser = new JLabel(MyFrame.bundle.getString("lblUser"), SwingConstants.CENTER);
		lblUser.setSize(BTN_WIDTH, BTN_HEIGHT);
		lblUser.setLocation(frame.getWidth() / 2 - BTN_WIDTH / 2, frame.getHeight() / 2 - 95);
		add(lblUser);

		txtUser = new JTextField(25);
		txtUser.setSize(BTN_WIDTH * 2, BTN_HEIGHT);
		txtUser.setLocation(frame.getWidth() / 2 - BTN_WIDTH, frame.getHeight() / 2 - 65);
		txtUser.setOpaque(true);
		add(txtUser);

		lblPw = new JLabel(MyFrame.bundle.getString("lblPassword"), SwingConstants.CENTER);
		lblPw.setSize(BTN_WIDTH, BTN_HEIGHT);
		lblPw.setLocation(frame.getWidth() / 2 - BTN_WIDTH / 2, frame.getHeight() / 2 - 25);
		add(lblPw);

		txtPassword = new JPasswordField(25);
		txtPassword.setSize(BTN_WIDTH * 2, BTN_HEIGHT);
		txtPassword.setLocation(frame.getWidth() / 2 - BTN_WIDTH, frame.getHeight() / 2 + 5);
		add(txtPassword);

		ImageIcon buttonImageIcon = new ImageIcon(ImageLoader.getInstance().getImage(ImageName.BUTTON));
//		ImageIcon buttonImageIcon = new ImageIcon(getClass().getClassLoader().getResource("Images/Button.png"));
				
		btnRegister = new MyButton(MyFrame.bundle.getString("btnRegister"), buttonImageIcon);
		addButton(btnRegister, frame.getWidth() / 2 - 200 - 5, frame.getHeight() / 2 + 45, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				onRegisterClicked();

			}
		});

		btnLogin = new MyButton(MyFrame.bundle.getString("btnLogin"), buttonImageIcon);
		addButton(btnLogin, frame.getWidth() / 2 + 5, frame.getHeight() / 2 + 45, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					onLoginClicked();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

			}
		});
		
		btnExit = new MyButton(MyFrame.bundle.getString("btnClose"), buttonImageIcon);
		addButton(btnExit, frame.getWidth() / 2 - 200 / 2, frame.getHeight() / 2 + 105, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
                SoundController.setBackgroundMusic(null);
				frame.dispose();
			}
		});

		if (TESTVERSION) {
			lblHint = new JLabel("TESTVERSION EINFACH LOGIN DRUECKEN", SwingConstants.CENTER);
			lblHint.setSize(frame.getWidth(), 100);
			lblHint.setLocation(0, 300);
			lblHint.setForeground(Color.RED);
			lblHint.setFont(lblHint.getFont().deriveFont(64.0f));
			add(lblHint);

			txtUser.setEditable(false);
			txtPassword.setEditable(false);
			btnRegister.setEnabled(false);
		}
	}

	private void addButton(JButton button,int posX,int posY, ActionListener action) {
		button.setHorizontalTextPosition(SwingConstants.CENTER);
		button.setBounds(posX, posY, 200, 50);
		button.addActionListener(action);
		add(button);
	}
	
	
	
	private void onLoginClicked() throws SQLException {
		if (!TESTVERSION) {

				boolean validInput = true;
	
				if (txtUser.getText().length() == 0) {
					txtUser.setBorder(BorderFactory.createLineBorder(Color.RED));
					validInput = false;
				} else {
					txtUser.setBorder(BorderFactory.createLineBorder(null));
				}
	
				if (txtPassword.getPassword().length == 0) {
					txtPassword.setBorder(BorderFactory.createLineBorder(Color.RED));
					validInput = false;
				} else {
					txtPassword.setBorder(BorderFactory.createLineBorder(null));
				}
	
				if (validInput) {
					ResultSet rs = Database.getInstance().executeQuery(Queries.loginUser(txtUser.getText()));
	
					if (rs.next()) {
						StringBuilder sb = new StringBuilder(txtPassword.getPassword().length);
						sb.append(txtPassword.getPassword());
						String userPassword = sb.toString();
						try {
							if (rs.getString(1).equals(userPassword)) {
								switchToMainFramePanel();
								
								if(!Files.isDirectory(Paths.get(path + "\\"))) {
					    	    	Files.createDirectory(Paths.get(path + "\\"));
					    	    	Files.createFile(Paths.get(path + "\\hota.txt"));
					    	    }else if(!Files.isDirectory(Paths.get(path + "\\hota.txt"))) {
					    	    	Files.createFile(Paths.get(path + "\\hota.txt"));
					    	    }
								
								FileWriter fw = new FileWriter(path + "\\hota.txt");
								
								fw.write(txtUser.getText() + "\n" + userPassword);
								fw.close();
								
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			
		} else {
			// instant bei login klick im main menu
			switchToMainFramePanel();
		}

	}

	private void onRegisterClicked() {

		boolean validInput = true;

		if (txtUser.getText().length() == 0) {
			txtUser.setBorder(BorderFactory.createLineBorder(Color.RED));
			validInput = false;
		} else {
			txtUser.setBorder(BorderFactory.createLineBorder(null));
		}

		if (txtPassword.getPassword().length == 0) {
			txtUser.setBorder(BorderFactory.createLineBorder(Color.RED));
			validInput = false;
		} else {
			txtPassword.setBorder(BorderFactory.createLineBorder(null));
		}
		if (validInput) {
			StringBuilder sb = new StringBuilder(txtPassword.getPassword().length);
			sb.append(txtPassword.getPassword());
			String userPassword = sb.toString();

			System.out.println(Queries.registerUser(txtUser.getText(), userPassword));
			if (Database.getInstance().execute(Queries.registerUser(txtUser.getText(), userPassword))) {
				switchToMainFramePanel();
			} else {
				txtUser.setBorder(BorderFactory.createLineBorder(Color.RED));
			}
		}
	}

	/**
	 * Wechselt zum MainFramePanel, aufzurufen beim erfolgreichen Login
	 */
	private void switchToMainFramePanel() {
		new MainFramePanel(frame);
		frame.repaint();
	}
}
