import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;


public class FingerRightScroller{
	private Image forward, left, middle, right, blank;//, backward, left, right; 	
	private AffineTransform tx;
	
	int dir = 0; 					//0-forward, 1-backward, 2-left, 3-right
	int width, height;
	int x, y;						//position of the object
	int vx, vy;						//movement variables
	double scaleWidth = 0.8;		//change to scale image
	double scaleHeight = 0.8; 		//change to scale image
	int type;
	
	
	public FingerRightScroller() {
		right 	= getImage("/imgs/"+"endFingerRight.png");
		middle 	= getImage("/imgs/"+"midFingerRight.png");
		left 	= getImage("/imgs/"+"startFingerRight.png");
		blank 	= getImage("/imgs/"+"none.png");//load the image for Tree
		/*backward 	= getImage("/imgs/"+"backward.png"); //load the image for Tree
		left 		= getImage("/imgs/"+"left.png"); //load the image for Tree
		right 		= getImage("/imgs/"+"right.png"); //load the image for Tree
		*/
		//alter these
		width = (int)(76*scaleWidth);
		height = (int)(36*scaleHeight);
		
		//top-left lopation of your image
		x=-width; //off screen for now
		y = 450;
		
		vx = 5;
		vy = 0;
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(x, y); 				//initialize the location of the image
									//use your variables
		
	}
	
	//2nd constructor - allow setting x and y during construction
		public FingerRightScroller(int x, int y, int type, int sect) {
			
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
		if(x>750) {
			x=-250;
			
		}
		
		init(x,y);
		if(type == 0) {
			left= blank;
			right = blank;
			middle = blank;
		}else {
			right 	= getImage("/imgs/"+"endFingerRight.png");
			middle 	= getImage("/imgs/"+"midFingerRight.png");
			left 	= getImage("/imgs/"+"startFingerRight.png");
			if(Frame.debugging) {
				//draw hitbox only if debugging
				g.setColor(Color.red);
				g.drawRect(x, y, width, height);
			}
		}
		
			switch(type) {
			case 0:
				forward = blank;
				break;
			case 1:
				forward = right;
				break;
			case 2:
				forward = middle;
				break;
			case 3:
				forward = left;
				break;
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
			URL imageURL = FingerRightScroller.class.getResource(path);
	 		tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}
