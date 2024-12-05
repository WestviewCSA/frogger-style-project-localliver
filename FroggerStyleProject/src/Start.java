import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class Start{
	private Image forward;//, backward, left, right; 	
	private AffineTransform tx;
	
						//0-forward, 1-backward, 2-left, 3-right
	int width, height;
	double scaleWidth = 4.00;		//change to scale image
	double scaleHeight = 4.00; 		//change to scale image

	public Start() {
		forward 	= getImage("/imgs/"+"background.png"); //load the image for Tree
		/*backward 	= getImage("/imgs/"+"backward.png"); //load the image for Tree
		left 		= getImage("/imgs/"+"left.png"); //load the image for Tree
		right 		= getImage("/imgs/"+"right.png"); //load the image for Tree
		*/
		//alter these
		width = 100;
		height = 100;
		
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(0, 0); 				//initialize the location of the image
									//use your variables
		
	}

	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		
		
		
		init(0,0);
		
		g2.drawImage(forward, tx, null);

	}
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = Start.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}
