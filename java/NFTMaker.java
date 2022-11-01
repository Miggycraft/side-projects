import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;

public class NFTMaker {
	/*
	 * how to make this shit work:
	 * so basically when you run this program once it will cause an error and then creates foldiers (NFTMaker\*sub folders*)
	 * in the sub folders, they will be named based on the strings of the layers array (so background, bodies, etc. folders will be made)
	 * just put images inside the folders, the rest of the layers (aside from background) should be transparent
	 */
	
	/*
	 * This is X and Y attributes of the basic image, they should be same with the background
	 * resolution so that the rest of the images will merge together
	 * The String Image_Name will be used as the name of the finalized image
	 * The String Layers will be used to get which images will be merged together order WILL matter.
	 */
	final int IMAGE_X = 1980;
	final int IMAGE_Y = 1080;
	public String IMAGE_NAME = "output";
	final  String[] LAYERS = { // these should be created as a folder
			"backgrounds", "bodies", "faces", "hat"
	};

	public  ArrayList<Color> uniqueValues = new ArrayList<>();
	public  ArrayList<Color> rngValues = new ArrayList<>();
	
	public  void create(int numbers) {
		for (int i = 1; i <= numbers; i++) {
			create(i+"");
		}
	}
	
	public  void create() {
		/*
		 * All of the individual images will be merged IN ORDER therefore background 
		 * (which doesn't contain any transperancy) should be placed first 
		 * and the rest for the following.
		 */
		String[] output = new String[LAYERS.length];
		
		try {
			File dir = new File("NFTmaker");
			if (!dir.exists())
				dir.mkdir();
			else
				createDirectories(dir, LAYERS);
			
			for (int i = 0; i < output.length; i++) {
				output[i] = "NFTMaker\\" +LAYERS[i] + "\\" + getImage(LAYERS[i]);
			}
			
			combine(output); // combines all the images into one
		} 
		catch (IIOException e){
			System.out.println("One of the selected files is not found!");
			e.printStackTrace();
		}
		catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Images contain within the folder/s is empty!");
		}

		catch (NullPointerException e) {
			System.out.println("One of the folders required is/are missing! (make sure to check they're not empty too!)");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  void create(String extra) {
		/*
		 * All of the individual images will be merged IN ORDER therefore background 
		 * (which doesn't contain any transperancy) should be placed first 
		 * and the rest for the following.
		 */
		String[] output = new String[LAYERS.length];
		
		try {
			File dir = new File("NFTmaker");
			if (!dir.exists())
				dir.mkdir();
			else
				createDirectories(dir, LAYERS);
			
			for (int i = 0; i < output.length; i++) {
				output[i] = "NFTMaker\\" +LAYERS[i] + "\\" + getImage(LAYERS[i]);
			}
			
			
			
			
			IMAGE_NAME = "output" + extra;
			combine(output); // combines all the images into one
		} 
		catch (IIOException e){
			System.out.println("One of the selected files is not found!");
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (NullPointerException e) {
			System.out.println("One of the folders required is/are missing! (make sure to check they're not empty too!)");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public  String getImage(String location) {
		String[] imageList = new File("NFTmaker\\" + location).list();
		int r = (int)(Math.random() * imageList.length); // chooses a randomized image on that folder
		return imageList[r];
	}
	
	
	private  boolean createDirectories(File main, String...sub) {
		main.mkdirs();
	    
	    if (!main.exists() || !main.isDirectory()) {
		      return false;
		      }
	    
	    for (String su : sub) {
	    	File subFile = new File(main, su);
	    	subFile.mkdir();
	    	if (!subFile.exists() || !subFile.isDirectory()) {
	    		return false;
	    		}
	    	}
	    
	    return true;
	    }
	
	public  void combine(String[] layers) throws IOException {
		BufferedImage output = new BufferedImage(IMAGE_X, IMAGE_Y, BufferedImage.TYPE_INT_ARGB);
		Graphics g = output.getGraphics();
		for (int i = 0; i < layers.length; i++) {
			BufferedImage layer = ImageIO.read(new File(layers[i]));
//			if (i == 3) { // remove
				changeHue(layer);
//			}
			g.drawImage(layer, 0, 0, null);
		}
		
		ImageIO.write(output, "PNG", new File(IMAGE_NAME+".png"));
	}
	
    public  BufferedImage changeHue(BufferedImage image) {
        Color current, rnd = null;
        boolean isVanilla = false;
        int roll = (int)(Math.random() * 10); // this means that theres a 10% chance to get the basic color
        if (roll == 0) {
        	isVanilla = true;
        }
        for (int y = 0; y < image.getHeight(); y++) {
        	for (int x = 0; x < image.getWidth(); x++) {
        		current = new Color(image.getRGB(x, y));
        		int pixel = image.getRGB(x, y);
        		
        		if (pixel < 0 && !blackOrClose(current,10) && !isVanilla) {
                	/*
                	 * checks for the following conditions:
                	 * 1. the selected pixel must NOT be transparent
                	 * 2. the selected pixel must NOT be black or close to black (hence 3 is the threshhold)
                	 * 3. the roll is NOT vanilla
                	 * (add another condition, alpha, checks whether how transparent the pixel is)
                	 * since di ko to ma fix, best solution is to use an image editor na 100% transparent, for some reason
                	 * photoshop saves with compression YAWA so either clean the images again or something
                	 * (update for my previous comment, as long as the image's rgb is positive it works idk how)
                	 * PSA. I think dapat instead of current (COLOR) I should swap out to pixel (INT) for some reason if kunin ko ang
                	 * RGB directly  through color may bug siya pero if kunin ko ang RGB directly through color, mag work na siya lol
                	 */
                	rnd = getRNGValue(current, 1);
                    image.setRGB(x, y, rnd.getRGB());
        		}
        	}
        }
        
        
        flushColor();
        return image;
    }
    
    public  boolean blackOrClose(Color c, int threshold) {
    	return (Math.abs(c.getRed() - c.getBlue()) < threshold) 
    			&& (Math.abs(c.getRed() - c.getGreen()) < threshold);
    }

    public  boolean isUnique(Color c, int threshold) {
    	for (int i = 0; i < uniqueValues.size(); i++) {
    		int r_abs = Math.abs(c.getRed() - uniqueValues.get(i).getRed());
    		int b_abs = Math.abs(c.getBlue() - uniqueValues.get(i).getBlue());
    		int g_abs = Math.abs(c.getGreen() - uniqueValues.get(i).getGreen());
    		
    		if (r_abs < threshold && b_abs < threshold && g_abs < threshold)
    			return false;
    	}
    	
    	return true;
    }
    
    public  Color getRNGValue(Color c, int threshold) {
    	/*
    	 * the c (which is the selected pixel's color) should be unique or close to itself to change it's value to an RGB value
    	 * if it has another color (example grass (green) below and theres a tree (brown) they both should have different value 
    	 * 
    	 * BUGGED TO SO FAR MAY PROBLEM DITO IDK WHERE HELP PLZ BITCH
    	 */
    	Color returnValue = new Color(0,0,0);
    	
    	if (isUnique(c, threshold)) {
    		addUniqueValue(c);
    	}
    	
    	for (int i = 0; i < uniqueValues.size(); i++) {
    		if (colorEquals(uniqueValues.get(i), c)) {
//    			System.out.println("correct at" + i);
    			return rngValues.get(i);
    		}
    	}
    	
    	return returnValue;
    }
    
    public  boolean colorEquals(Color x, Color y) {
    	if (x.getRed() != y.getRed())
    		return false;
    	if (x.getBlue() != y.getBlue())
    		return false;
    	if (x.getGreen() != y.getGreen())
    		return false;
    	return true;
    }
    
    public  void flushColor() {
    	// this should reset the uniqueValues Color Array, should be called everytime a new layer is being used
    	uniqueValues.removeAll(uniqueValues);
    	rngValues.removeAll(rngValues);
    }
    
    public  void addUniqueValue(Color c) {
    	uniqueValues.add(c);
    	
    	int r = (int)(Math.random() * 255);
    	int g = (int)(Math.random() * 255);
    	int b = (int)(Math.random() * 255);
    	Color randomValue = new Color(r,g,b);
    	
    	rngValues.add(randomValue);
    }
}
