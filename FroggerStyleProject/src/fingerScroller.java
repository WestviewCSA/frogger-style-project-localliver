import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class fingerScroller{
	private Image forward;//, backward, left, right; 	
	private AffineTransform tx;
	
	int dir = 0; 					//0-forward, 1-backward, 2-left, 3-right
	int width, height;
	int x, y;						//position of the object
	int vx, vy;						//movement variables
	double scaleWidth = 1.00;		//change to scale image
	double scaleHeight = 1.00; 		//change to scale image
	int type;
	
	public fingerScroller() {
		forward 	= getImage("/imgs/"+"finger.png"); //load the image for Tree
		/*backward 	= getImage("/imgs/"+"backward.png"); //load the image for Tree
		left 		= getImage("/imgs/"+"left.png"); //load the image for Tree
		right 		= getImage("/imgs/"+"right.png"); //load the image for Tree
		*/
		//alter these
		width = (int)(120*scaleWidth);
		height = (int)(36*scaleHeight);
		
		//top-left lopation of your image
		x=-360; //off screen for now
		y = 300;
		
		vx = 5;
		vy = 0;
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(x, y); 				//initialize the location of the image
									//use your variables
		
	}
	
	/*
	 * Collision detection with main character class
	 */
	public boolean collided(Rock character) {
		
		//represent eachj object as a rectange
		//then check if they are interesecting
		Rectangle main = new Rectangle(character.getX(),character.getY(),character.getWidth(),character.getHeight());
		
		Rectangle thisObject = new Rectangle(x,y,width,height);
		
		return main.intersects(thisObject);
		
	}
	
	//2nd constructor - allow setting x and y during construction
		public fingerScroller(int x, int y, int type) {
			
			//call the default constructor for all normal stuff
			this(); //invokes default constructor
			
			//do the specific task for this constructor
			this.x = x;
			this.y = y;
			this.type = type;
			
			
		}

	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		
		x+=vx;
		y+=vy;	
		
		//for infinite scrolling tp to the other side once it leaves the other side\
		if(x>720) {
			x=-360;
			type = (int) (Math.random()*5)-1;
		}
		
		init(x,y);
		if(type == 0) {
			forward= getImage("/imgs/" + "none.png");
		}else {
			forward 	= getImage("/imgs/"+"finger.png");
			if(Frame.debugging) {
				//draw hitbox only if debugging
				g.setColor(Color.red);
				g.drawRect(x, y, width, height);
			}
		}
		g2.drawImage(forward, tx, null);
		
		
		
		
	}
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = fingerScroller.class.getResource(path);
	 		tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}