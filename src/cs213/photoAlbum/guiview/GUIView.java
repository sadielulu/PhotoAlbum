package cs213.photoAlbum.guiview;


import java.io.IOException;

import cs213.photoAlbum.control.Control;
/**
 * @author Silvia Carbajal
 * @author Oskar Bero
 */

public class GUIView {


    public static void main(String[] args) throws ClassNotFoundException, IOException {
    	
    	//New control object to be utilized throughout the GUI
    	Control ctrl = new Control();
    	//Begin new session 
    	new LogInView("login", ctrl);
 	
    }
    
}
