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
import java.util.ArrayList;
public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {
	
	public static boolean debugging = true;
	
	//Timer related variables
	int waveTimer = 5; //each wave of enemies is 20s
	long ellapseTime = 0;
	Font timeFont = new Font("Courier", Font.BOLD, 70);
	int level = 0;
	//making the objects
	
	
	
	Rock rock2 = new Rock(350, 530);
	
	Background cliff = new Background();
	Font myFont = new Font("Courier", Font.BOLD, 40);
	SimpleAudioPlayer backgroundMusic = new SimpleAudioPlayer("scifi.wav", false);
//	Music soundBang = new Music("bang.wav", false);
//	Music soundHaha = new Music("haha.wav", false);
	
	// a row of bagelscrolling objects
	everythingBagelScroller[] row1  = new everythingBagelScroller[10];
	//a row of finger logs
	fingerScroller[] fingerRow = new fingerScroller[5];
	fingerScroller[] fingerRow2 = new fingerScroller[5];
	fingerScroller[] fingerRow3 = new fingerScroller[5];
	ArrayList<GooglyEye> eyes = new ArrayList<GooglyEye>();
	ArrayList<googlyRock> googlyRock = new ArrayList<googlyRock>();
	//frame width/height
	static int width = 596;
	static int height = 620;	
	

	public void paint(Graphics g) {
		super.paintComponent(g);
		//where to paint the objects
		cliff.paint(g);
		
		
		
		
		//paint the row1 objects
		//for each obj in row1 paint
		for(everythingBagelScroller obj : row1) {
			obj.paint(g);
			
		}
		for(fingerScroller obj: fingerRow) {
				obj.paint(g);
				
			}
		
		for(fingerScroller obj: fingerRow2) {
			obj.paint(g);
			
		}
		for(fingerScroller obj: fingerRow3) {
			
			obj.paint(g);
			
		}
		for(GooglyEye obj: eyes) {
			
			obj.paint(g);
		
		}
		for(googlyRock obj: googlyRock) {
			
			obj.paint(g);
		
		}
		//collision
		rock2.paint(g);
		for(everythingBagelScroller obj : row1) {
			if(obj.collided(rock2) && obj.type !=0) {
				rock2.x = 350;
				rock2.y = 530;
			}
		}
		int tempx;
		int tempy;
		for(GooglyEye obj : eyes) {
			if(obj.collided(rock2)&&!obj.isCollected) {
				tempx = obj.x;
				tempy = obj.y;
				eyes.remove(obj);
				googlyRock.add(new googlyRock(tempx-5, tempy));
				rock2.x = 350;
				rock2.y = 530;
			}
			
		}
		boolean riding = false;
		for(fingerScroller obj : fingerRow) {
			if(obj.collided(rock2)&&obj.type!=0) {
				rock2.setVx(obj.getVx());
				riding = true;
				break;
			}
		}
		for(fingerScroller obj : fingerRow2) {
			if(obj.collided(rock2)&&obj.type!=0) {
				rock2.setVx(obj.getVx());
				riding = true;
				break;
			}
		}
		for(fingerScroller obj : fingerRow3) {
			if(obj.collided(rock2)&&obj.type!=0) {
				rock2.setVx(obj.getVx());
				riding = true;
				break;
			}
		}
		if(!riding&&(rock2.getY() > 110&&rock2.getY()<210) ) {
			riding = false;
			rock2.setVx(0);
			rock2.x =350;
			rock2.y=550;
		}else if (!riding) {
			rock2.setVx(0);
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
			row1[i] = new everythingBagelScroller(i*100-250, 400, (int) (Math.random()*5)-1);
			
		}
		for(int i = 0; i < fingerRow.length; i++) {
			
			fingerRow[i] = new fingerScroller(i*330-180, 210, 4, "finger.png",(int) (Math.random()*5)-1, true);
		}
		for(int i = 0; i < fingerRow2.length; i++) {
			
			fingerRow2[i] = new fingerScroller(i*330-180, 110,3, "finger.png",(int) (Math.random()*5)-1, true);
		}
		for(int i = 0; i < fingerRow3.length; i++) {
			
			fingerRow3[i] = new fingerScroller(780-i*330, 160,-5, "fingerLeft.png", (int) (Math.random()*5)-1, false);
		}
		while(eyes.size()<8) {
			eyes.add(new GooglyEye(eyes.size()*75+10,25));
			
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
			rock2.vy=-5;
			
			
		}
		if(arg0.getKeyCode() == 83) {
			rock2.vy=5;
			
		
		}
		if(arg0.getKeyCode() == 68) {
			rock2.vx=5;
			
		}
		if(arg0.getKeyCode() == 65) {
			rock2.vx=-5;
			
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
