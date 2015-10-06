package cs213.photoAlbum.guiview;


import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.ListSelectionModel;

import cs213.photoAlbum.control.Control;

/**
 * This class extends JFrame
 * @author Oskar Bero
 */
@SuppressWarnings("serial")
public class AlbumPhotos extends JFrame {

	//non-component variables declaration
	Control ctrl;
	String newPhotoName, newPhotoCaption, thisAlbum;
	boolean makeAlbum;
	
	//Component variables declaration
    private JButton addPhotoButton;
    private JButton backButton;
    private JPanel bigPanel;
    private JPanel buttonsPanel;
    private JButton createAlbum;
    private JButton displayButton;
    private JButton editPhotoButton;
    private  JPanel listPanel;
    private JScrollPane listScrollPane;
    private JTextField photoCaptionText;
    public JList<JLabel> photoList;
    private JLabel photoListLabel;
    private JTextField photoNameText;
    private JLabel removeLabel;
    private JButton removePhotoButton;
    private ArrayList<String> userPhotos;
    private DefaultListModel<JLabel> listModel;
   
   
    /**
     * Creates new form AlbumPhotos frame, this frame has a list of all photos that are in the album selected in the album view
     * Allows for display, edit, and add / remove of photos. If the boolean create is true, it allows the creation of an album - however
     * this is only allowed from photos listed by search photo function in the album view. Otherwise create album is disabled since you 
     * are already looking at the album you'd be trying to create.
     */
    public AlbumPhotos(Control control, String albumName, boolean create) {
    	super("Album: " + albumName);
    	
    	//get the control so we can change things in the album
    	ctrl = control;
    	thisAlbum = albumName;
    	userPhotos = new ArrayList<String>();
    	makeAlbum = create;
   
	        for(int i  = 0; i < ctrl.usr.getAlbum(thisAlbum).getPhotos().size(); i++)
	        {
	        	String photoName = ctrl.usr.getAlbum(thisAlbum).getPhotos().get(i).getPhotoName();
	        	userPhotos.add(photoName);
	        }
 
    	//build the components of this gui
        initComponents();
        initPhotoList();
        if(listModel.size() > 0)
        {
        	photoList.setSelectedIndex(0);
        }
        else
        {
        	listModel.add(0,new JLabel("Ooops! No photos present in this album!"));
        	photoList.setSelectedIndex(0);
        }
        this.setVisible(true);
    }

    /**
     * builds all the components for the frame, sets up layouts and groups
     *
     */
    private void initComponents() {
    	
        bigPanel = new JPanel();
        buttonsPanel = new JPanel();
        photoNameText = new JTextField();
        photoCaptionText = new JTextField();
        addPhotoButton = new JButton();
        removePhotoButton = new JButton();
        editPhotoButton = new JButton();
        displayButton = new JButton();
        removeLabel = new JLabel();
        backButton = new JButton();
        createAlbum = new JButton();
        listPanel = new JPanel();
        listScrollPane = new JScrollPane();
        photoList = new JList<JLabel>();
        photoListLabel = new JLabel();
        listModel = new DefaultListModel<JLabel>();
      
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        //window listener for closing- bring back album view
        addWindowListener( new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
            	//bring back the album photos window
           
            	new AlbumView(thisAlbum,ctrl);
            }
        } );
        
        setResizable(false);
        
        buttonsPanel.setBorder(BorderFactory.createEtchedBorder());

        photoNameText.setText("New Photo Name...");
      
        photoNameText.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                photoNameTextFocusGained(evt);
            }
        });
        
        //Photo Caption Initialized
        photoCaptionText.setText("Photo Caption...");
        photoCaptionText.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                photoCaptionTextFocusGained(evt);
            }
        });

        addPhotoButton.setText("Add Photo");
        addPhotoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                addPhotoButtonActionPerformed(evt);
            }
        });
        
        removePhotoButton.setText("Remove Photo");
        removePhotoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
            	removePhotoButtonActionPerformed(evt);
            }
        });

        editPhotoButton.setText("Edit Photo");
        editPhotoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                editPhotoButtonActionPerformed(evt);
            }
        });

        displayButton.setText("Display Photo");
        displayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                displayButtonActionPerformed(evt);
            }
        });

        removeLabel.setText("Choose photo from the list");

        backButton.setText("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        createAlbum.setText("Create Album");
        if(makeAlbum)
        {
        	createAlbum.setEnabled(true);
        	 createAlbum.addActionListener(new ActionListener() {
                 public void actionPerformed(ActionEvent evt) {
                    createAlbumButtonActionPerformed(evt);
                 }
             });
        }
        else
	    {
        	createAlbum.setEnabled(false);
	    }

        GroupLayout buttonsPanelLayout = new GroupLayout(buttonsPanel);
        
        //massive set layout 
        buttonsPanel.setLayout(buttonsPanelLayout);
        buttonsPanelLayout.setHorizontalGroup(
            buttonsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, buttonsPanelLayout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(buttonsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(backButton, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                    .addGroup(GroupLayout.Alignment.TRAILING, buttonsPanelLayout.createSequentialGroup()
                        .addComponent(photoNameText, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(buttonsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                            .addComponent(removeLabel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                            .addComponent(createAlbum, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addPhotoButton, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(photoCaptionText, GroupLayout.Alignment.TRAILING)
                            .addComponent(removePhotoButton, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(editPhotoButton, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(displayButton, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(80, 80, 80))
        );
        
        buttonsPanelLayout.setVerticalGroup(
            buttonsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(buttonsPanelLayout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(buttonsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(photoNameText, GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(photoCaptionText))
                .addGap(18, 18, 18)
                .addComponent(addPhotoButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(removeLabel, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(removePhotoButton, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editPhotoButton, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(displayButton, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(createAlbum, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(backButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        listPanel.setBorder(BorderFactory.createEtchedBorder());
        
 
        photoList.setModel(listModel);
     
        
        photoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        photoList.setSelectedIndex(0);
        photoList.setToolTipText("");
        
        listScrollPane.setViewportView(photoList);
        photoListLabel.setText("Photos");

        
        GroupLayout listPanelLayout = new GroupLayout(listPanel);
        listPanel.setLayout(listPanelLayout);
        listPanelLayout.setHorizontalGroup(
            listPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(listPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(listPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(listScrollPane, GroupLayout.DEFAULT_SIZE, 453, Short.MAX_VALUE)
                    .addGroup(listPanelLayout.createSequentialGroup()
                        .addComponent(photoListLabel)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        listPanelLayout.setVerticalGroup(
            listPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, listPanelLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(photoListLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listScrollPane, GroupLayout.PREFERRED_SIZE, 439, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        GroupLayout bigPanelLayout = new GroupLayout(bigPanel);
        bigPanel.setLayout(bigPanelLayout);
        bigPanelLayout.setHorizontalGroup(
            bigPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, bigPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(listPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonsPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        bigPanelLayout.setVerticalGroup(
            bigPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(buttonsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(listPanel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bigPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(bigPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }
    
    /**
     * Attempts to add photo to user's album , adds the photo and its thumbnail to the JList allows the photo to be selected edited or displayed
     * @param evt - add photo button pressed
     */
    private void addPhotoButtonActionPerformed(ActionEvent evt){
    	
    	if(photoNameText.getText().equals("")||photoCaptionText.getText().equals("")
    			|| photoNameText.getText().equals("New Photo Name...")
    				|| photoCaptionText.getText().equals("Photo Caption...")){

    					JOptionPane.showMessageDialog(this, "The photo name or caption field was unchanged or left empty!"
    							,"Error: Empty fields!", JOptionPane.ERROR_MESSAGE);
    	}
    	else
    	{
    		//make the default folder data!
    		String inputName = photoNameText.getText();
    		if(!(photoNameText.getText().contains(File.separator)))
    		{
    			
    			newPhotoName = "data"+File.separator + inputName;
    		}
    		else
    		{
    			newPhotoName = photoNameText.getText();
    		}
    			newPhotoCaption = photoCaptionText.getText();
    		
    		photoNameText.setText("New Photo Name...");
    		photoCaptionText.setText("Photo Caption...");
    		
    		StringTokenizer tk = new StringTokenizer(inputName);
    		while(tk.hasMoreTokens())
    		{
    			inputName = tk.nextToken(File.separator);
    		}
    		try{
    			if(ctrl.addPhoto(newPhotoName, newPhotoCaption, thisAlbum))
    			{
    				
    				initPhotoList();
    	    		//success
    				JOptionPane.showMessageDialog(this, "Successfully added \nPhoto: " + inputName
    												+ "\nCaption: " + newPhotoCaption,"Success!", JOptionPane.PLAIN_MESSAGE);
    			}
    			else
    			{
 
    				JOptionPane.showMessageDialog(this, "The photo doesn't exist!"
							,"Error: No such file!", JOptionPane.ERROR_MESSAGE);
    			
    			}
    						
    		}catch(IOException e){};

    		photoNameText.setText("New Photo Name...");
    		photoCaptionText.setText("Photo Caption...");    		
    	}
    	
    }
    
    /**
     * Disposes curent frame , spawns the edit photo frame for changing tags, caption or moving albums
     * @param evt - edit photo button pressed
     */
    private void editPhotoButtonActionPerformed(ActionEvent evt) {
    	
    	if(listModel.getElementAt(0).getText().equals("oops no photos here"))
         {
         	return;
         }
    	String photoName = getNameFromList();
    	
    	EditPhoto editPhotoWindow = new EditPhoto(ctrl, thisAlbum, photoName);
    	editPhotoWindow.setVisible(true);
    	//
    	this.dispose();
    }

   /**
    * Removes photo from the JList, the and the user's currently open album!
    * @param evt - remove photo button pressed
    */
    private void removePhotoButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_editPhotoButtonActionPerformed
    
    	photoList.setSelectedIndex(0);
    	
    	//get the selected index, delete the photo from the user's album 
    	int index = photoList.getSelectedIndex();
    	
    	//System.out.println(listModel.size());
    	if(userPhotos.size() == 0)
    	{
    		return;
    	}
    	String photoName = userPhotos.get(index);
    
    	
    	try {
			ctrl.deletePhoto(photoName, thisAlbum);
			
			listModel.remove(index);
			userPhotos.remove(index);
			photoList.setSelectedIndex(0); //reset the selection
		} catch (ClassNotFoundException | IOException e){};
		
		if(listModel.size() == 0)
    	{
    		listModel.add(0, new JLabel("oops no photos here"));
    		
    	}
		photoList.setSelectedIndex(0);
				return;
    }
    
    /**
     * dispose current frame, WindowClose handler spawns an album frame aka move bak in  our program flow
     * @param evt - back button pressed
     */
    private void backButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
			//cause the window closing event on press of X
    	this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    	
    }
    
    /**
     * Only available when photo list is created from photos found by the search function.
     * Allows the user to create an album from the found photos, internally just changes the name
     * of a temporary album used to instantiate the photoList 
     * @param evt - create album pressed
     */
    private void createAlbumButtonActionPerformed(ActionEvent evt){

    	String newName = (String) JOptionPane.showInputDialog(this, "Input new album name:","Album Name",
				JOptionPane.PLAIN_MESSAGE,null, null, null);
    	if(newName != null && !newName.isEmpty())
    	{
    		ctrl.renameAlbum("tempAlbum", newName);
		
	    	dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    	}
    	else
    	{
    		return;
    	}
    	
    }

    /**
     * Display button action handler, creates a new display photo frame
     * @param evt - display button pressed
     */
    private void displayButtonActionPerformed(ActionEvent evt) {
    	//passing in ctrl , selected photo name , and the albumName
        DisplayPhoto display;
	
    	if(listModel.getElementAt(0).getText().equals("oops no photos here"))
        {
        	return;
        }
        try {
			display = new DisplayPhoto(ctrl , getNameFromList(), thisAlbum);
			//try the wait for display to close and call notify
			display.setVisible(true);
			
		
			this.dispose();
		 } catch (IOException e){}
        	//catch(InterruptedException e) {/* maybe need somethign here */};
       
    }

    /**
     * On photoName field focused, clear the text field
     * @param evt - photo name text field focused event
     */
    private void photoNameTextFocusGained(FocusEvent evt) {                                          
        photoNameText.setText("");
    }

    /**
     * On photoCaption field focused, clear the text field
     * @param evt - photo caption text field focused event
     */
    private void photoCaptionTextFocusGained(FocusEvent evt) {                                          
        photoCaptionText.setText("");
        
    } 
       
    /**
     * Fills the photoList JList object with thumbnails of photos and their name and caption
     * 
     */
	private void initPhotoList()
    {
    	listModel.removeAllElements();
    	
    	String path, name, caption;
    	//list of labels the size of the photos array in the album
    	JLabel labels[] = new JLabel[ctrl.usr.getAlbum(thisAlbum).getPhotos().size()];
    	
    	/**
    	 * This is an override of default cell renderer to have the List display in a neat Thumbnail + name + caption format
    	 */
    	DefaultListCellRenderer renderer = new DefaultListCellRenderer(){
    	    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
    	    	JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus); 	      
    	        if(value instanceof JLabel)
    	        {
    	        	label.setIcon(((JLabel) value).getIcon() );
    	        	label.setText(((JLabel) value).getText());
    	        }
    	        //Add a border to each cell
    	        label.setBorder(BorderFactory.createEtchedBorder());
    	        return label;
    	    }    	  
    	};
    	
    	photoList.setCellRenderer(renderer);
    	
        for(int i  = 0; i < ctrl.usr.getAlbum(thisAlbum).getPhotos().size(); i++)
        {
        	String photoName = ctrl.usr.getAlbum(thisAlbum).getPhotos().get(i).getPhotoName();
        	userPhotos.add(photoName);
        }
    	for(int i = 0; i < labels.length; i++)
    	{
    		path = ctrl.usr.getAlbum(thisAlbum).getPhotos().get(i).getPhotoPath();
    		name = ctrl.usr.getAlbum(thisAlbum).getPhotos().get(i).getPhotoName() + "\n";
    		caption = ctrl.usr.getAlbum(thisAlbum).getPhotos().get(i).getCaption();
    		
    		//add a JLabel returned from getImageLabel function
    		listModel.add(i,getImageLabel(path,name, caption));
    		
    	}
    	if(listModel.size() == 0)
    	{
    		listModel.add(0, new JLabel("oops no photos here"));
    		
    	}
    	photoList.setSelectedIndex(0);
    }
    
    /**
     * Creates a JLabel that contains a photo thumbnail as well as the name and the caption of the photo wraped with width
     * 200 as to avoid horizontal scroll bars, as well as Nam and Caption being labeled on separate lines of text
     * 
     * @param path - photo Path 
     * @param name - name of the photo (aka last part of the path ./photoName)
     * @param caption - caption given to the photo
     * @return - JLabel with a thumbnail size 100x100 of the photo, and name + caption
     */
    private JLabel getImageLabel(String path, String name, String caption)
    {
    	name = "Name: " + name;
    	caption = "Caption: " + caption;
    	
    	//html for text wraping in the JLabel
    	String html1 = "<html><body style='width: ";
        String html2 = "px'>";

 
    	//make a new image icon
    	ImageIcon image = new ImageIcon(path);
    	//new label for return
    	JLabel photoLabel = new JLabel(html1+ "200" + html2 + name+"<br/> <b>"+ caption + "</html>");
    	//resize the image icon to be a certain size for the list
        ImageIcon thumbnailIcon = new ImageIcon(getScaledImage(image.getImage(), 100, 100));
        //set the icon for the jlabel to the new thumbnail
    	photoLabel.setIcon(thumbnailIcon);
    	
    	//return the JLabel with icon and message
    	return photoLabel ;
    			
    }
    /**
     * Gets the actual photo name out of the entire description stored in JList (cuts out whitespace and html code as well as the caption)
     * @return photoName of the selected index in the JList photoList
     */
    private String getNameFromList()
    {
    	//Get a label from the JList
    	JLabel name = (JLabel) listModel.get(photoList.getSelectedIndex());

    	//Save text in photoName
    	String photoName = name.getText();

    	//Start tokenizing past html code in place to wrap lines in the list + length of name (this value is hardcoded to be 39 characters long as it doesnt change);
    	photoName = photoName.substring(39); //The 6 here is from the length of "Name: "
    	//new tokenizer for photoName
    	StringTokenizer tk = new StringTokenizer(photoName);
    	photoName = tk.nextToken("\n"); //Tokenize to where I drop down a line for a caption
    	photoName.trim(); //trim any whitespace
    	
    	return photoName;
    }
    
    /**
     * Got mostly from the oracle ImageIcon tutorials , resizes an image to be thumbnail size specified by
     * w and h.
     * @param srcImg - imge to be rescaled 
     * @param w - new width
     * @param h - new height
     * @return - resized Image type object
     */
    private Image getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
      
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        
        return resizedImg;
    }
  
}
 


