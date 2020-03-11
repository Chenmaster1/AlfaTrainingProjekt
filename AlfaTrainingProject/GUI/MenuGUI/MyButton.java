package MenuGUI;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JButton;


@SuppressWarnings("serial")
public class MyButton extends JButton implements MouseListener{

    private float opacity;
    
    public MyButton(String text, Icon icon){
        super(text, icon);
        opacity = (float) 0.8;
        addMouseListener(this);
        setFocusable(false);
    }
    
    @Override
    public void paint(Graphics g){       
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        super.paint(g2);
        g2.dispose();
    }
    
    public void setOpacity(float opacity){
        this.opacity = opacity;
    }

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {            
		getParent().repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		getParent().repaint();
	}
}
