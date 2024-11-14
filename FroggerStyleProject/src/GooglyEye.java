import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class GooglyEye{
	private Image forward;// backward, left, right; 	
	private AffineTransform tx;
	
	int dir = 0; 					//0-forward, 1-backward, 2-left, 3-right
	int width, height;
	int x, y;						//position of the object
	int vx, vy;						//movement variables
	double scaleWidth = 1.0;		//change to scale image
	double scaleHeight = 1.0; //change to scale image
	boolean isCollected = false;
	String img = "googlyEye.png";
	
	public GooglyEye() {
		forward 	= getImage("/imgs/"+"googlyEye.png"); //load the image for Tree
		

		//alter these
		width = (int)(35*scaleWidth);
		height = (int)(35*scaleHeight);
		x = 10;
		y = 10;
		vx = 0;
		vy = 0;
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(x, y); 				//initialize the location of the image
									//use your variables
		
	}
	
	public GooglyEye(int x, int y) {
		
		//call the default constructor for all normal stuff
		this(); //invokes default constructor
		
		//do the specific task for this constructor
		this.x = x;
		this.y = y;
		
		
	}
	public boolean collided(Rock character) {
		
		//represent eachj object as a rectange
		//then check if they are interesecting
		Rectangle main = new Rectangle(character.getX(),character.getY(),character.getWidth(),character.getHeight());
		
		Rectangle thisObject = new Rectangle(x,y,width,height);
		if(!isCollected) {
			return main.intersects(thisObject);
		}
		return false;
		
		
	}

	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		
		x+=vx;
		y+=vy;	
		
		init(x,y);
		forward = getImage("/imgs/"+img);
		g2.drawImage(forward, tx, null);
		if(Frame.debugging) {
			//draw hitbox only if debugging
			g.setColor(Color.red);
			g.drawRect(x, y, width, height);
		}

	}
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = GooglyEye.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}
