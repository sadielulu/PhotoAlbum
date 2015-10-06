package cs213.photoAlbum.guiview;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

import cs213.photoAlbum.control.Control;

/**
 * Display photo class object is a JFrame that displays a photo passed by name, as well as all its info and tags.
 * It also alows the user to go through the album sequentially and throws an error dialoge if a user tries to go past
 * the last or first image in the album.
 * @author Oskar Bero
 */
@SuppressWarnings("serial") //dont need the serial handling as the data save and manipulation is separate from the gui
public class DisplayPhoto extends JFrame {
		
	//non- component declarations
		public String currentPhoto, currentAlbum, photoPath, newCaption, newDate;
		Control ctrl;
	    ArrayList<String[]>	tags;
	    String[] photos; //stores photo names
	    int photoIndex = 0;
    
    //Component variables
	    private JButton leftButton;
	    private JTextField captionText;
	    private JTextField dateText;
	    private JPanel displayPhotoPanel;
	    private JPanel photoInfoPanel;
	    private JButton rightButton;
	    private JComboBox<String> tagsComboBox;
	    private DefaultComboBoxModel<String> tagsComboBoxModel;
	    private JPanel tempPhotoSpace;
	    
	public DisplayPhoto(Control control, String photoName, String albumName) throws IOException {
    	super(photoName);
		currentPhoto = photoName;
    	currentAlbum = albumName;
    	ctrl = control;
       	
    	//get stuff from control to use later!

			int len = ctrl.usr.getAlbum(currentAlbum).getPhotos().size();
			String name;
    		photoPath = ctrl.usr.getAlbum(currentAlbum).getPhoto(currentPhoto).getPhotoPath();
    		photos = new String[len];
    		//fill with photo paths , save the current location in album
    		for(int i = 0; i < len; i++)
    		{
    			name =  ctrl.usr.getAlbum(currentAlbum).getPhotos().get(i).getPhotoName();
    			photos[i] = name;
    			//System.out.println("Photo name: " + name);
    			if(ctrl.usr.getAlbum(currentAlbum).getPhotos().get(i).getPhotoName().equals(currentPhoto))
    			{
    				photoIndex = i;
    			}
    		}
		
    
		//build GUI
    	initComponents();
    	
    	//initialize the info fields
    	initTagsComboBox();
    	initCaptionTextField();
    	initDateTextField();
    	
    
    	//System.out.println("Path:  " + photoPath);
    	tempPhotoSpace.add(new ImageMaker(photoPath));
    	
    }
     /**
      * builds all the components for DisplayPhoto Frame
      */
	 private void initComponents() {
		   	
		   	tagsComboBoxModel = new DefaultComboBoxModel<String>();
	        displayPhotoPanel = new JPanel();
	        leftButton = new JButton();
	        rightButton = new JButton();
	        tempPhotoSpace = new JPanel();
	        photoInfoPanel = new JPanel();
	        captionText = new JTextField();
	        dateText = new JTextField();
	        tagsComboBox = new JComboBox<String>();

	        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
	        addWindowListener( new WindowAdapter() {
              
                public void windowClosing(WindowEvent we) {
                	//bring back the album photos window
                	new AlbumPhotos(ctrl, currentAlbum, false);
                }
            } );
	        //set frame title to the current photo name
	        setTitle(currentPhoto);

	        //for now dont resize.. if we get to that we get to that 
	        setResizable(false);
	    
	        
	        
	        //Set borders for panels
	        displayPhotoPanel.setBorder(BorderFactory.createEtchedBorder());
	        tempPhotoSpace.setBorder(BorderFactory.createEtchedBorder());
	        
	        //left button
	        leftButton.setText("<");
	        leftButton.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	            	leftButtonActionPerformed(evt);
	            }
	        });

	        //right button
	        rightButton.setText(">");
	        rightButton.setToolTipText("");
	        rightButton.addActionListener(new java.awt.event.ActionListener() {
	            public void actionPerformed(java.awt.event.ActionEvent evt) {
	                rightButtonActionPerformed(evt);
	            }
	        });

	        //display photo panel layout (i.e the big panel in the background)
	        GroupLayout displayPhotoPanelLayout = new GroupLayout(displayPhotoPanel);
	        displayPhotoPanel.setLayout(displayPhotoPanelLayout);
	        displayPhotoPanelLayout.setHorizontalGroup(
	            displayPhotoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addGroup(displayPhotoPanelLayout.createSequentialGroup()
	                .addComponent(leftButton, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(tempPhotoSpace, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(rightButton, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))
	        );
	        displayPhotoPanelLayout.setVerticalGroup(
	            displayPhotoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addComponent(tempPhotoSpace, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	            .addGroup(GroupLayout.Alignment.TRAILING, displayPhotoPanelLayout.createSequentialGroup()
	                .addContainerGap(266, Short.MAX_VALUE)
	                .addGroup(displayPhotoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
	                    .addComponent(rightButton, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
	                    .addComponent(leftButton, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE))
	                .addGap(152, 152, 152))
	        );

	        //Info panel border set
	        photoInfoPanel.setBorder(BorderFactory.createEtchedBorder());

	        //have the caption date and tags set up
	        captionText.setEditable(false);
	        dateText.setEditable(false);

	        tagsComboBox.setModel(tagsComboBoxModel);

	        //photo info panel layout for the 3 types of info
	        GroupLayout photoInfoPanelLayout = new GroupLayout(photoInfoPanel);
	        photoInfoPanel.setLayout(photoInfoPanelLayout);
	        photoInfoPanelLayout.setHorizontalGroup(
	            photoInfoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addGroup(photoInfoPanelLayout.createSequentialGroup()
	                .addContainerGap()
	                .addComponent(captionText, GroupLayout.PREFERRED_SIZE, 237, GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(dateText, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(tagsComboBox, GroupLayout.PREFERRED_SIZE, 288, GroupLayout.PREFERRED_SIZE)
	                .addContainerGap(64, Short.MAX_VALUE))
	        );
	        photoInfoPanelLayout.setVerticalGroup(
	            photoInfoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addGroup(photoInfoPanelLayout.createSequentialGroup()
	                .addContainerGap()
	                .addGroup(photoInfoPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
	                    .addComponent(tagsComboBox, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)
	                    .addComponent(dateText)
	                    .addComponent(captionText, GroupLayout.Alignment.LEADING))
	                .addContainerGap(43, Short.MAX_VALUE))
	        );

	        GroupLayout layout = new GroupLayout(getContentPane());
	        getContentPane().setLayout(layout);
	        layout.setHorizontalGroup(
	            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addComponent(displayPhotoPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	            .addComponent(photoInfoPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	        );
	        layout.setVerticalGroup(
	            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addComponent(displayPhotoPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(photoInfoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
	        );

	        pack();
	    }                       
	    
	 /**
	  * Moves one photo forward in the album or displays an error if at the last photo in the album
	  * @param evt - right button pressed
	  */
	    private void rightButtonActionPerformed(java.awt.event.ActionEvent evt){
	    			    	
	    	if(photoIndex == photos.length - 1) //adjust for the 0 index
	    	{
	    		JOptionPane.showMessageDialog(this, "You are at the last photo in the album!"
						,"Error!", JOptionPane.ERROR_MESSAGE);
	    		return;
	    	}
	    	else
	    	{
	    		photoIndex++; //move forward
	    		
	    	}
	    	
	    	//get the path of the next photo in the album
	    	try {
				photoPath = ctrl.usr.getAlbum(currentAlbum).getPhoto(photos[photoIndex]).getPhotoPath();
				currentPhoto = ctrl.usr.getAlbum(currentAlbum).getPhoto(photos[photoIndex]).getPhotoName();
			} catch (IOException e) {/*  error */}
	    
	    	
	    	//System.out.println("Path:  " + photoPath);
	    	initTagsComboBox();
	    	initCaptionTextField();
	    	initDateTextField();
	    	tempPhotoSpace.removeAll();
	    	tempPhotoSpace.add(new ImageMaker(photoPath));
	    }
	    /**
		  * Moves one photo backwards in the album or displays an error if at the first photo in the album
		  * @param evt - left button pressed
		  */
	    private void leftButtonActionPerformed(java.awt.event.ActionEvent evt){
	    	if(photoIndex == 0)
	    	{
	    		JOptionPane.showMessageDialog(this, "You are at the first photo in the album!"
						,"Error!", JOptionPane.ERROR_MESSAGE);
	    		return;
	    	}
	    	else
	    	{
	    		photoIndex--; //move back
	    	}
	    	
	    	//get the path of the next photo in the album
	    	try {
	    		photoPath = ctrl.usr.getAlbum(currentAlbum).getPhoto(photos[photoIndex]).getPhotoPath();
				currentPhoto = ctrl.usr.getAlbum(currentAlbum).getPhoto(photos[photoIndex]).getPhotoName();
			} catch (IOException e) {/*  error */}
	    
	    	initTagsComboBox();
	    	initCaptionTextField();
	    	initDateTextField();
	    	
	    
	    	//System.out.println("Path:  " + photoPath);
	    	//clean and add new image
	    	tempPhotoSpace.removeAll();
	    	tempPhotoSpace.add(new ImageMaker(photoPath));
	    }

	/**
	 * Builds tags combo box, takes tags for current photo from the control, displays them in combo box
	 */
    private void initTagsComboBox()
    {
    	tagsComboBoxModel.removeAllElements();
    	try {
			tags = ctrl.usr.getAlbum(currentAlbum).getPhoto(currentPhoto).getTags();
		} catch (IOException e) { /**/	}
    	//Fill the TAGS combo box!
        for(String[] tag: tags)
        {
        		String fullTag = tag[0] + ":" + tag[1];
        		
        		////System.out.println("Full Tag is: " + fullTag);
        		tagsComboBoxModel.addElement(fullTag);
        }
        
        //if combo box is empty after adding in all the tags for a photo
        if(tagsComboBoxModel.getSize() == 0)
        {
        	tagsComboBoxModel.addElement("No tags for this photo!");
        }
        tagsComboBox.setSelectedIndex(0);
    }
    
   /**
    * takes date from current photo , displays in the text field for the user
    */
    private void initDateTextField()
    {
    	
    	try {
			newDate = ctrl.usr.getAlbum(currentAlbum).getPhoto(currentPhoto).getDate();
		} catch (IOException e) { /*   */}

			dateText.setText(newDate);
    }
    
    /**
     * takes current photo caption from the user and puts it in an uneditable text field
     */
    private void initCaptionTextField()
    {
    	try {
    		newCaption = ctrl.usr.getAlbum(currentAlbum).getPhoto(currentPhoto).getCaption();
		} catch (IOException e) { /*   */}
		captionText.setText(newCaption);
    }
    
   
    /**
     * This Class creates an image component so that it can be added to a panel directly
     * It contains a resize class that resizes the photo to a right now hard-coded size of the photoSpacePanel
     * @author Oskar Bero
     *
     */
	class ImageMaker extends Component {
    	BufferedImage img;

        public void paint(Graphics g) {
            g.drawImage(img, 0, 0, null);
        }
        
        /**
         * Creates an image component you can add to a frame or panel
         * @param path - path to an image
         */
        public ImageMaker(String path) {
           try {
        	   //get the actual image
               img = ImageIO.read(new File(path));

           } catch (IOException e) { }
           
           //resize to match the panel size for it 
           img = resize(img, tempPhotoSpace.getWidth(), tempPhotoSpace.getHeight());
        }
        
        public Dimension getPreferredSize() {
            if (img == null) {
                 return new Dimension(tempPhotoSpace.getWidth(),tempPhotoSpace.getHeight());
            } else {
               return new Dimension(img.getWidth(null), img.getHeight(null));
           }
        }

       /**
        * inner class for imageMaker , resizes an BufferedImage object and returns to ImagMaker
        * @param img
        * @param newWidth
        * @param newHeight
        * @return
        */
        public  BufferedImage resize(BufferedImage img, int newWidth, int newHeight) { 
            Image tmp = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
           
            BufferedImage dimg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = dimg.createGraphics();
            g2d.drawImage(tmp, 0, 0, null);
            
            g2d.dispose();

            return dimg;
        }  
    }


}
