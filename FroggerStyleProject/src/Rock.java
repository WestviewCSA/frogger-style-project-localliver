import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Rock{
	private Image forward; //, backward, left, right; 	
	private AffineTransform tx;
	
	int dir = 0; 					//0-forward, 1-backward, 2-left, 3-right
	int width, height;
	int x, y;						//position of the object
	int vx, vy;						//movement variables
	double scaleWidth = 0.0375;		//change to scale image
	double scaleHeight = 0.0375; 		//change to scale image

	public Rock() {
		forward 	= getImage("/imgs/"+"rock.png"); //load the image for Tree
		/*backward 	= getImage("/imgs/"+"backward.png"); //load the image for Tree
		left 		= getImage("/imgs/"+"left.png"); //load the image for Tree
		right 		= getImage("/imgs/"+"right.png"); //load the image for Tree
		*/
		//alter these
		width = (int) (1320*scaleWidth);
		height = (int) (1200*scaleHeight);
		x = 350;
		y = 550;
		vx = 0;
		vy = 0;
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(x, y); 				//initialize the location of the image
									//use your variables
		
	}
	
	//2nd constructor - allow setting x and y during construction
	public Rock(int x, int y) {
		
		//call the default constructor for all normal stuff
		this(); //invokes default constructor
		
		//do the specific task for this constructor
		this.x = x;
		this.y = y;
		
	}
	

	//getters!!!
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	
	//setters!!!
	public void setImg(String newImgUrl) {
		forward = getImage("/imgs/"+newImgUrl);
	}
	public void setVx(int vx) {
		this.vx = vx;
	}
	
	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		
		x+=vx;
		y+=vy;	
		if(x>586) {
			x=350;
			y=530;
			Frame.subtractLives();
		}
		if(x<-50) {
			x=350;
			y=530;
			Frame.subtractLives();
		}
		if(y<0) {
			x=350;
			y=530;
		}
		if(y>600) {
			x=350;
			y=530;
		}
		init(x,y);
		g2.drawImage(forward, tx, null);
		
		//draw hitbox based on x, y, width, height for collision detection
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
			URL imageURL = Rock.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}
