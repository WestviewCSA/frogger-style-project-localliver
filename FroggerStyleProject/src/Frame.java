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
	
	public static boolean debugging = false;
	
	//Timer related variables
	int waveTimer = 5; //each wave of enemies is 20s
	long ellapseTime = 0;
	Font timeFont = new Font("Courier", Font.BOLD, 100);
	int level = 0;
	//score and lives variables 
	int score = 0;
	static int lives = 3;
	boolean didStart = false;
	boolean died = false;
	
	SimpleAudioPlayer overMusic = null;
	SimpleAudioPlayer victoryMusic = null;
	
	//making the rock object
	Rock rock2 = new Rock(350, 530);
	//background sprites
	Background cliff = new Background();
	Death death = new Death();
	Start start = new Start();
	Victory victory = new Victory();
	
	Font myFont = new Font("Courier", Font.BOLD, 20);
	Font bigFont = new Font("Courier", Font.BOLD, 50);
	//background_music
	SimpleAudioPlayer backgroundMusic = new SimpleAudioPlayer("background_music.wav", true);
	
	
	
	
//  Music soundBang = new Music("bang.wav", false);
//	Music soundHaha = new Music("haha.wav", false);
	
	// a row of bagelscrolling objects
	everythingBagelScroller[] row1  = new everythingBagelScroller[10];
	everythingBagelScroller[] row2  = new everythingBagelScroller[10];
	//a row of finger logs
	fingerScroller[] fingerRow = new fingerScroller[5];
	fingerScroller[] fingerRow2 = new fingerScroller[5];
	fingerScroller[] fingerRow3 = new fingerScroller[5];
	//a row of the googlyeyes (what you grab) and a row of googlyRocks (showing you collected them)
	ArrayList<GooglyEye> eyes = new ArrayList<GooglyEye>();
	ArrayList<googlyRock> googlyRock = new ArrayList<googlyRock>();
	//frame width/height
	static int width = 596;
	static int height = 620;	
	
	public static void subtractLives() {
		lives--;
	}

	
	public void paint(Graphics g) {
		super.paintComponent(g);
		//where to paint the objects
		
		
		//making text
		g.setFont(myFont);
		
		
		//paint the row1 objects
		//for each obj in row1 paint
		
		if(didStart) {
			cliff.paint(g);
			
		}
		for(everythingBagelScroller obj : row1) {
			obj.paint(g);
			
		}
		for(everythingBagelScroller obj : row2) {
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
		rock2.paint(g);
		g.setColor(Color.white);
		g.fillRect(10, 540, 80, 25);
		g.fillRect(500, 540, 75, 25);
		g.setColor(Color.black);
		g.drawString("Score: " + score, 10, 560);
		g.drawString("Lives: " + lives, 500, 560);
		//rock painted last so over everyhting
		//painting end/start screens
		g.setFont(bigFont);	
		if(lives <= 0) {
			death.paint(g);
			died=true;
			g.drawString("Sucked... into a bagel", 40, 100);
			g.drawString("Press space to restart", 35, 540);
			
			if(overMusic==null) {
				overMusic=new SimpleAudioPlayer("bagel.wav", false);
				overMusic.play();
				
			}
			
		}
		//victory screen
		if (score >= 8) {
			victory.paint(g);
			g.setColor(Color.black);
			g.drawString("In another life, I would", 10, 80);
			g.drawString("have really liked doing", 10, 130);
			g.drawString("laundry and taxes...", 10, 180);
			
			g.drawString("Press space to restart", 40, 510);
			g.setFont(myFont);
			g.drawString("(you won)", 50, 460);
			if(victoryMusic==null) {
				victoryMusic=new SimpleAudioPlayer("laundry.wav", false);
				victoryMusic.play();
			}
		}
		//start screen
		if(!didStart) {
			start.paint(g);
			g.setColor(Color.black);
			g.setFont(bigFont);
			g.drawString("Everything, Everywhere", 15, 80);
			g.drawString("All at Once Frogger", 45, 160);
			g.drawString("Press space to start", 50, 490);
		}
		
		//collision
		
		for(everythingBagelScroller obj : row1) {
			if(obj.collided(rock2) && obj.type !=0) {
				rock2.x = 350;
				rock2.y = 530;
				lives--;
				
				new SimpleAudioPlayer("bonk.wav",false).play();
				
			}
		}
		for(everythingBagelScroller obj : row2) {
			if(obj.collided(rock2) && obj.type !=0) {
				rock2.x = 350;
				rock2.y = 530;
				lives--;
				new SimpleAudioPlayer("bonk.wav",false).play();
				
			}
		}
		//when colliding with the googlyeyes, that specific googlyeye 
		//is removed and replaced with a googlyRock
		int tempx;
		int tempy;
		for(GooglyEye obj : eyes) {
			if(obj.collided(rock2)&&!obj.isCollected) {
				tempx = obj.x;
				tempy = obj.y;
				eyes.remove(obj);
				googlyRock.add(new googlyRock(tempx-5, tempy-7));
				rock2.x = 350;
				rock2.y = 530;
				score++;
				new SimpleAudioPlayer("victor.wav", false).play();
			}
			
		}
		//log collision/riding death things
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
		if(!riding&&(rock2.getY() > 85&&rock2.getY()<185) ) {
			riding = false;
			rock2.setVx(0);
			rock2.x =350;
			rock2.y=530;
			lives--;
			new SimpleAudioPlayer("clothes.wav", false).play();
			
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
		
		backgroundMusic.play();
		

		/*
		 * Setting up the arrays and arrayLists
		 */
		
		for(int i = 0; i < row1.length; i++) {
			row1[i] = new everythingBagelScroller(i*100-250, 370, (int) (Math.random()*5)-1, -2);
			
		}
		for(int i = 0; i < row2.length; i++) {
			row2[i] = new everythingBagelScroller(i*100-250, 430, (int) (Math.random()*5)-1, 3);
			
		}
		for(int i = 0; i < fingerRow.length; i++) {
			
			fingerRow[i] = new fingerScroller(i*330-180, 180, 4, "finger.png",(int) (Math.random()*5)-1, true);
		}
		for(int i = 0; i < fingerRow2.length; i++) {
			
			fingerRow2[i] = new fingerScroller(i*330-180, 80,3, "finger.png",(int) (Math.random()*5)-1, true);
		}
		for(int i = 0; i < fingerRow3.length; i++) {
			
			fingerRow3[i] = new fingerScroller(780-i*330, 130,-5, "fingerLeft.png", (int) (Math.random()*5)-1, false);
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
		
		//movement
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
		if(arg0.getKeyCode() == 32) {
		//if statements so the game doesn't always reset when pressing space
			if(!didStart) {
			//set everything back to normal in all if-statements
				score = 0;
				lives = 3;
				rock2.x =350;
				rock2.y=530;
				didStart = true;
			}
			if(died) {
				score = 0;
				lives=3;
				rock2.x =350;
				rock2.y=530;
				died=false;
				//making music only play once
				overMusic.pause();
				overMusic=null;
			}
			if(score>=8) {
				score = 0;
				lives = 3;
				rock2.x=350;
				rock2.y=530;
				//making music only play once
				victoryMusic.pause();
				victoryMusic=null;
			}
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
