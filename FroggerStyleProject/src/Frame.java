import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {
	
	public static boolean debugging = true;
	
	//Timer related variables
	int waveTimer = 5; //each wave of enemies is 20s
	long ellapseTime = 0;
	Font timeFont = new Font("Courier", Font.BOLD, 70);
	int level = 0;
	//making the objects
	
	everythingBagel bagel = new everythingBagel();
	googlyRock gRock = new googlyRock(265, 25);
	Rock rock2 = new Rock(150, 25);
	GooglyEye eye = new GooglyEye();
	
	Font myFont = new Font("Courier", Font.BOLD, 40);
	SimpleAudioPlayer backgroundMusic = new SimpleAudioPlayer("scifi.wav", false);
//	Music soundBang = new Music("bang.wav", false);
//	Music soundHaha = new Music("haha.wav", false);
	
	// a row of bagelscrolling objects
	everythingBagelScroller[] row1  = new everythingBagelScroller[10];
	//a row of finger logs
	FingerRightScroller[] rowRight = new FingerRightScroller[9];
	
	
	
	//frame width/height
	static int width = 596;
	static int height = 600;	
	

	public void paint(Graphics g) {
		super.paintComponent(g);
		//where to paint the objects
		bagel.paint(g);
		rock2.paint(g);
		gRock.paint(g);
		eye.paint(g);
		
		
		//paint the row1 objects
		//for each obj in row1 paint
		/*for(everythingBagelScroller obj : row1) {
			obj.paint(g);
			
		}*/
		for(int i = 0; i < rowRight.length; i++) {
			if(rowRight[i].x >= 600) {
				FingerRightScroller j;
				if(i == 0) {
					j = rowRight[8];
				}else {
					j = rowRight[i-1];
				}
				
				switch(j.type) {
				case 0:
					rowRight[i].type = 1;
					
				case 1:
					rowRight[i].type = 2;
					
				case 2: 
					rowRight[i].type = 3;
					break;
					
				case 3: 
					rowRight[i].type = 0;
					
				}
				
				
			}
		}
		
	}
	
	public static void main(String[] arg) {
		Frame f = new Frame();
		
	}
	
	public Frame() {
		JFrame f = new JFrame("Duck Hunt");
		f.setSize(new Dimension(width, height));
		f.setBackground(Color.white);
		f.add(this);
		f.setResizable(false);
 		f.addMouseListener(this);
		f.addKeyListener(this);
		
		//backgroundMusic.play();

		/*
		 * Setup any 1D array here! - create the objects that go in there
		 */
		
		for(int i = 0; i < row1.length; i++) {
			row1[i] = new everythingBagelScroller(i*100-250, 150, (int) (Math.random()*5)-1);
			
		}
		for(int i = 0; i < rowRight.length; i+=3) {
			int random = (int) (Math.random()*5)-1;
			rowRight[i] = new FingerRightScroller(i*100-250, 300, random, 1);
			rowRight[i+1] = new FingerRightScroller(i*100-320, 300, random, 2);
			rowRight[i+2] = new FingerRightScroller(i*100-390, 300, random, 3);
		}
		
		
		
		//the cursor image must be outside of the src folder
		//you will need to import a couple of classes to make it fully 
		//functional! use eclipse quick-fixes
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("torch.png").getImage(),
				new Point(0,0),"custom cursor"));	
		
		
		Timer t = new Timer(16, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent m) {
		
	
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0.getKeyCode());
		if(arg0.getKeyCode() == 87) {
			rock2.vy=-10;
			rock2.vx=0;
		}
		if(arg0.getKeyCode() == 83) {
			rock2.vy=10;
			rock2.vx=0;
		}
		if(arg0.getKeyCode() == 68) {
			rock2.vx=10;
			rock2.vy=0;
		}
		if(arg0.getKeyCode() == 65) {
			rock2.vx=-10;
			rock2.vy=0;
		}
		
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode() == 87) {
			rock2.vy=0;
			
		}
		if(arg0.getKeyCode() == 83) {
			rock2.vy=0;
			
		}
		if(arg0.getKeyCode() == 68) {
			rock2.vx=0;
			
		}
		if(arg0.getKeyCode() == 65) {
			rock2.vx=0;
			
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
