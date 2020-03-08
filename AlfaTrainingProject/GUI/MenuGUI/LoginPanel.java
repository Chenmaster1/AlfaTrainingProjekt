package MenuGUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

@SuppressWarnings("serial")
public class LoginPanel extends JPanel {

	private final boolean TESTVERSION = true;

	private boolean isInitiated = false;

	private final int BTN_WIDTH = 100;
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

	public LoginPanel(MyFrame frame) {
		this.frame = frame;
		setLayout(null);
		frame.setContentPane(this);
		backgroundImage = new ImageIcon(
				getClass().getClassLoader().getResource("Images/BackGround_FullScreenBlurred.png")).getImage();

	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, this);

		// erklaeren warum

		if (!isInitiated) {
			initializeComponents();
			isInitiated = true;
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
		add(txtUser);

		lblPw = new JLabel(MyFrame.bundle.getString("lblPassword"), SwingConstants.CENTER);
		lblPw.setSize(BTN_WIDTH, BTN_HEIGHT);
		lblPw.setLocation(frame.getWidth() / 2 - BTN_WIDTH / 2, frame.getHeight() / 2 - 25);
		add(lblPw);

		txtPassword = new JPasswordField(25);
		txtPassword.setSize(BTN_WIDTH * 2, BTN_HEIGHT);
		txtPassword.setLocation(frame.getWidth() / 2 - BTN_WIDTH, frame.getHeight() / 2 + 5);
		add(txtPassword);

		btnRegister = new JButton(MyFrame.bundle.getString("btnRegister"));
		btnRegister.setSize(BTN_WIDTH, BTN_HEIGHT);
		btnRegister.setLocation(frame.getWidth() / 2 - BTN_WIDTH - 5, frame.getHeight() / 2 + 45);
		btnRegister.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				onRegisterClicked();

			}
		});
		add(btnRegister);

		btnLogin = new JButton(MyFrame.bundle.getString("btnLogin"));
		btnLogin.setSize(BTN_WIDTH, BTN_HEIGHT);
		btnLogin.setLocation(frame.getWidth() / 2 + 5, frame.getHeight() / 2 + 45);
		btnLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					onLoginClicked();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		add(btnLogin);

		btnExit = new JButton(MyFrame.bundle.getString("btnClose"));
		btnExit.setSize(BTN_WIDTH, BTN_HEIGHT);
		btnExit.setLocation(frame.getWidth() / 2 - BTN_WIDTH / 2, frame.getHeight() / 2 + 85);
		btnExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		add(btnExit);

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
//							switchToMainFramePanel();
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
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
