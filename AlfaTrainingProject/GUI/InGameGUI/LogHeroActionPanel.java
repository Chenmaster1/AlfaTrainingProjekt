package InGameGUI;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LogHeroActionPanel extends JPanel {
	private JLabel labelLogHeroAction;
	private JTextArea textAreaLogHeroAction;
	private JScrollPane scrollTextAreaLogHeroAction;

	public LogHeroActionPanel() {
		setLayout(null);
		// labelLogHeroAction = new JLabel("logoLog");
		// labelLogHeroAction.setBounds(5, 5, 150, 50);

		textAreaLogHeroAction = new JTextArea();
		scrollTextAreaLogHeroAction = new JScrollPane(textAreaLogHeroAction);

//		scrollTextAreaLogHeroAction.setBounds(5, 5, 170, 385);

		// test filler
		String str = "the game begins \n";

		textAreaLogHeroAction.setText(str);

		// add(labelLogHeroAction);
		add(scrollTextAreaLogHeroAction);

		addComponentListener(new ComponentAdapter() {

			@Override
			public void componentResized(ComponentEvent e) {
				scrollTextAreaLogHeroAction.setBounds(5, 5, getWidth() - 10, getHeight() - 10);

			}

		});
	}

	public void setTextAreaLogHeroAction(String textAreaLog) {

		// textAreaLogHeroAction.setText(textAreaLog);
		textAreaLogHeroAction.append(textAreaLog + "\n");
		textAreaLogHeroAction.setCaretPosition(textAreaLogHeroAction.getDocument().getLength());
	}

}
