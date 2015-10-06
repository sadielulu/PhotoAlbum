
package cs213.photoAlbum.guiview;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

import cs213.photoAlbum.control.Control;
/**
 *
 * @author Oskar Bero
 */
@SuppressWarnings("serial")
public class EditPhoto extends JFrame {

	//non-component variables declaration
	Control ctrl;
	String currentAlbum, currentPhoto, caption;
	ArrayList<Integer> inUseAlbumIndexes;
	ArrayList<String[]> tags;


    //Component variables declaration
    private JButton acceptCaptiontButton;
    private JButton addTagButton;
    private JComboBox<String> currentAlbumsCombo;
    private JTextField currentCaptionText;
    private JButton editCaptionButton;
    private JLabel editCaptionLabel;
    private JPanel editCaptionPanel;
    private JLabel editTagInfo;
    private JLabel editTagsLabel;
    private JPanel editTagsPanel;
    private JButton movePhotoButton;
    private JLabel movePhotoInfo;
    private JLabel movePhotoLabel;
    private JPanel movePhotoPanel;
    private JButton removeTagButton;
    private JComboBox<String> tagListComboBox;
    private JTextField tagType;
    private JTextField tagValue;
    private DefaultComboBoxModel<String> tagsComboBoxModel;
    private DefaultComboBoxModel<String> albumsComboBoxModel;
    
    /**
     * Creates new form EditPhoto
     */
    public EditPhoto(Control control, String curAlbum, String curPhoto) {
    	super("Edit: " + curPhoto);
    	
    	ctrl = control;
    	currentAlbum = curAlbum;
    	currentPhoto = curPhoto;
    	inUseAlbumIndexes = new ArrayList<Integer>();
    	
    	/*
    	 * get tags from the usr
    	 */
    	try {
			tags = ctrl.usr.getAlbum(currentAlbum).getPhoto(currentPhoto).getTags();
			
		} catch (IOException e) { /* excpetion */		};
		

        initComponents(); //build components
        
        //components were built time to add the necessary data to them
       
        //add all of photo's tags to the comboBox
        initTagsComboBox();
        initCaptionTextField();
        initAlbumsComboBox();
        
        caption = currentCaptionText.getText();
        
        this.setVisible(true);
    }
    

    /**
     * builds all the components for the frame, sets up layouts and such
     * @SuppressWarnings("unchecked")
     */
    private void initComponents() {
    	tagsComboBoxModel = new DefaultComboBoxModel<String>();
    	albumsComboBoxModel = new DefaultComboBoxModel<String>();
        editCaptionPanel = new JPanel();
        editCaptionButton = new JButton();
        acceptCaptiontButton = new JButton();
        currentCaptionText = new JTextField();
        editCaptionLabel = new JLabel();
        editTagsPanel = new JPanel();
        addTagButton = new JButton();
        tagValue = new JTextField();
        tagType = new JTextField();
        tagListComboBox = new JComboBox<String>();
        removeTagButton = new JButton();
        editTagsLabel = new JLabel();
        editTagInfo = new JLabel();
        movePhotoPanel = new JPanel();
        currentAlbumsCombo = new JComboBox<String>();
        movePhotoLabel = new JLabel();
        movePhotoButton = new JButton();
        movePhotoInfo = new JLabel();
        
    
        
     
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
        setResizable(false);
        addWindowListener( new WindowAdapter() {

            public void windowClosing(WindowEvent we) {
            	//bring back the album photos window
            	new AlbumPhotos(ctrl, currentAlbum,false);
            }
        } );
        
        
        //edit captions init
        editCaptionLabel.setText("Edit Caption");
        
        editCaptionPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        editCaptionButton.setText("Edit");
        editCaptionButton.setToolTipText("");
        editCaptionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editCaptionButtonActionPerformed(evt);
            }
        });

        //accept captions button init
        acceptCaptiontButton.setText("Accept");
        acceptCaptiontButton.setToolTipText("");
        acceptCaptiontButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptCaptiontButtonActionPerformed(evt);
            }
        });

        currentCaptionText.setEditable(false);
        

   

        //layout for edit caption panel
        GroupLayout editCaptionPanelLayout = new GroupLayout(editCaptionPanel);
        editCaptionPanel.setLayout(editCaptionPanelLayout);
        editCaptionPanelLayout.setHorizontalGroup(
            editCaptionPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(editCaptionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(editCaptionPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(editCaptionPanelLayout.createSequentialGroup()
                        .addComponent(currentCaptionText)
                        .addGap(61, 61, 61)
                        .addComponent(editCaptionButton, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(acceptCaptiontButton, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
                        .addGap(51, 51, 51))
                    .addGroup(editCaptionPanelLayout.createSequentialGroup()
                        .addComponent(editCaptionLabel, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        
        editCaptionPanelLayout.setVerticalGroup(
            editCaptionPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(editCaptionPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(editCaptionLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editCaptionPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(editCaptionButton, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                    .addComponent(acceptCaptiontButton, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                    .addComponent(currentCaptionText, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        editTagsPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        //add tag button
        addTagButton.setText("Add Tag");
        addTagButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addTagButtonActionPerformed(evt);
            }
        });

        //tag value
        tagValue.setText("Tag Value");
        tagValue.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                tagValueTextFocusGained(evt);
            }
        });
        
        tagType.setText("Tag Type");
        tagType.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                tagTypeTextFocusGained(evt);
            }
        });
       
        //LIST OF TAGS COMBO BOX
        tagListComboBox.setModel(tagsComboBoxModel);
        
     
        //remove tag button
        removeTagButton.setText("Remove Tag");
        removeTagButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeTagButtonActionPerformed(evt);
            }
        });

        editTagsLabel.setText("Edit Tags");

        editTagInfo.setText("Choose a tag from the dropdown menu");

      //Edit tags PANEL Layout and group set
        GroupLayout editTagsPanelLayout = new GroupLayout(editTagsPanel);
        editTagsPanel.setLayout(editTagsPanelLayout);
        editTagsPanelLayout.setHorizontalGroup(
            editTagsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(editTagsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(editTagsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(editTagsPanelLayout.createSequentialGroup()
                        .addGroup(editTagsPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(editTagsPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(editTagInfo))
                            .addGroup(editTagsPanelLayout.createSequentialGroup()
                                .addGroup(editTagsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addGroup(editTagsPanelLayout.createSequentialGroup()
                                        .addComponent(tagType, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(tagValue, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE))
                                    .addGroup(GroupLayout.Alignment.TRAILING, editTagsPanelLayout.createSequentialGroup()
                                        .addComponent(tagListComboBox, GroupLayout.PREFERRED_SIZE, 393, GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(editTagsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(removeTagButton, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                                    .addComponent(addTagButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(51, 51, 51))
                    .addGroup(editTagsPanelLayout.createSequentialGroup()
                        .addComponent(editTagsLabel, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        
        
        editTagsPanelLayout.setVerticalGroup(
            editTagsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(editTagsPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(editTagsLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(editTagsPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(tagValue, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(tagType, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                    .addComponent(addTagButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
                .addGroup(editTagsPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(editTagsPanelLayout.createSequentialGroup()
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(editTagInfo)
                        .addGap(2, 2, 2)
                        .addComponent(removeTagButton))
                    .addGroup(editTagsPanelLayout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(tagListComboBox, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        //MOVE PHOTO PANEL
        movePhotoPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        currentAlbumsCombo.setModel(albumsComboBoxModel);

        movePhotoLabel.setText("Move Photo");

        movePhotoButton.setText("Move Photo");
        movePhotoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                movePhotoButtonActionPerformed(evt);
            }
        });

        movePhotoInfo.setText("Choose album from the dropdown menu");

        GroupLayout movePhotoPanelLayout = new GroupLayout(movePhotoPanel);
        movePhotoPanel.setLayout(movePhotoPanelLayout);
        movePhotoPanelLayout.setHorizontalGroup(
            movePhotoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(movePhotoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(movePhotoPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                    .addGroup(movePhotoPanelLayout.createSequentialGroup()
                        .addComponent(movePhotoLabel, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(movePhotoInfo))
                    .addGroup(movePhotoPanelLayout.createSequentialGroup()
                        .addComponent(currentAlbumsCombo, GroupLayout.PREFERRED_SIZE, 521, GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(movePhotoButton, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        movePhotoPanelLayout.setVerticalGroup(
            movePhotoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, movePhotoPanelLayout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addGroup(movePhotoPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(movePhotoLabel)
                    .addComponent(movePhotoInfo))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(movePhotoPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(movePhotoButton, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
                    .addComponent(currentAlbumsCombo))
                .addGap(66, 66, 66))
        );

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                    .addComponent(editTagsPanel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(editCaptionPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(movePhotoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(editCaptionPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(editTagsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(movePhotoPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        pack();
    }


    /**
     * This method takes all the tags for the current photo and dumps them in the combo box for tags 
     */
    private void initTagsComboBox()
    {
    	//clear list to let the list update after adding or removing a tag !
    	if(tagsComboBoxModel.getSize() > 0)
    	{
    		tagsComboBoxModel.removeAllElements();
    	}
    	
    
    	//Fill the TAGS combo box!
        for(String[] tag: tags)
        {
        		String fullTag = tag[0] + ":" + tag[1];
        		
        		//System.out.println("Full Tag is: " + fullTag);
        		tagsComboBoxModel.addElement(fullTag);
        }
        
        //if combo box is empty after adding in all the tags for a photo
        if(tagsComboBoxModel.getSize() == 0)
        {
        	tagsComboBoxModel.addElement("No tags for this photo!");
        	removeTagButton.setEnabled(false);
        }
        tagListComboBox.setSelectedIndex(0);
    }
   
    /**
     * sets the caption text field to the photo's current caption
     */
    private void initCaptionTextField()
    {
    	//Set tthe current caption to the Photo's caption
    	try {
			currentCaptionText.setText(ctrl.usr.getAlbum(currentAlbum).getPhoto(currentPhoto).getCaption());
		} catch (IOException e) {/* Something went wrong*/	}
    }

    /**
     * gets the albums from the user and lists all the albums in the albums combo box
     */
    private void initAlbumsComboBox()
    {
    	//This will clear the combo box, and let you call it as a refresh when moving the photo
    	if(albumsComboBoxModel.getSize() > 0)
    	{
    		albumsComboBoxModel.removeAllElements();
    		
    	}
    	//for every album
    	for(int i = 0; i < ctrl.usr.getAlbums().size(); i++)
    	{	
    		for(int j = 0; j < ctrl.usr.getAlbums().get(i).getPhotos().size(); j++)
    		{
    				//if the photo belongs to one of the albums, tag it as already in there, display as already in 
					if(ctrl.usr.getAlbums().get(i).getPhotos().get(j).getPhotoName().equals(currentPhoto))
					{
						//System.out.println("Alredy in: " + ctrl.usr.getAlbums().get(i).getAlbumName());
						inUseAlbumIndexes.add(i);
					}
    		}
    		
    		// the current index i has the photo were editing add the Already in: tag
    		if(inUseAlbumIndexes.contains(i))
    		{
    			albumsComboBoxModel.addElement("Already in: "+ ctrl.usr.getAlbums().get(i).getAlbumName());
    		}
    		else
    		{
    			albumsComboBoxModel.addElement( ctrl.usr.getAlbums().get(i).getAlbumName());
    		}
    		
    	}
    	
    }
    
    /**
     * Attempts to move photo to the album selected in the albums combo box
     * @param evt - even that movePhoto button is pressed 
     */
    private void movePhotoButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	String selected;
    	if((selected = (String) albumsComboBoxModel.getSelectedItem()) == null)
    	{
			JOptionPane.showMessageDialog(this, "You have not selected a destination album"
					,"Error: no destination selected", JOptionPane.ERROR_MESSAGE);
    	}
    	else if(selected.startsWith("Already in:"))
    	{
    		JOptionPane.showMessageDialog(this, "The photo is already in the destination you chose"
					,"Error", JOptionPane.ERROR_MESSAGE);
    	}
    	else
    	{
    		//tokenize out the "Already in"
    		StringTokenizer tk = new StringTokenizer(selected);
    		selected = tk.nextToken(":");
    		//System.out.println("Selected:"+selected);
    		
    		try {
    			//preform the move 
				ctrl.movePhoto(currentPhoto, currentAlbum, selected);
				//remove the old version of selected, and add Already in: part to it
				albumsComboBoxModel.removeElement(selected);
				albumsComboBoxModel.addElement("Already in: "+ selected);
				
				//remove the "Already in: " part of the origin album aka the current album 
				albumsComboBoxModel.removeElement("Already in: "+ currentAlbum);
				albumsComboBoxModel.addElement(currentAlbum);
				
				//change the current album aspect of this photo
				currentAlbum = selected;
				ctrl.listPhotoInfo(currentPhoto);
			} catch (ClassNotFoundException | IOException e) {}
    	}
    }

    /**
     * Attempts to remove the tag selected in the tags combo box
     * @param evt - remove button pressed event
     */
    private void removeTagButtonActionPerformed(java.awt.event.ActionEvent evt) {
       
    	String type = "", value;
    	String fullTag = (String)tagsComboBoxModel.getSelectedItem();

    	// tokenize the tag into value and string, done because of how they are displayed in the combo box
    	if(fullTag.contains(":"))
    	{
    		type = fullTag.substring(0,fullTag.indexOf(":"));
        	value = fullTag.substring(fullTag.indexOf(":") + 1);
    	}
    	else
    	{
    		value = fullTag;
    	}
    	try{
			if(ctrl.deleteTag(currentPhoto, type, value))
			{
					
					//gone from the actual photo , take it out from the list??
			
					tagsComboBoxModel.removeElement(fullTag);
					tagsComboBoxModel.setSelectedItem(fullTag);
				
				if(tagsComboBoxModel.getSize() == 0)
			    {		
						tagsComboBoxModel.removeElement(fullTag);
			        	tagsComboBoxModel.insertElementAt("No tags for this photo!",0);
			        	tagsComboBoxModel.setSelectedItem("No tags for this photo!");
			        	removeTagButton.setEnabled(false);
			    }
			}
		}catch(IOException e){};
    	
    	tagListComboBox.setSelectedIndex(0); //set selected index to be 0 
    }

    /**
     * Attempts to add the tag described in the type and value fields. Also does error checking on input
     * @param evt - add tag button pressed event
     */
    private void addTagButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	String newTagType, newTagValue;
    	
    	if(tagValue.getText().equals("")	|| tagType.getText().equals("Tag Type")
    				|| tagValue.getText().equals("Tag Value")){

    					JOptionPane.showMessageDialog(this, "Tag type or Tag value invalid"
    							,"Error: Incomplete fields!", JOptionPane.ERROR_MESSAGE);
    					
    					
    		    		tagType.setText("Tag Type");
    		    		tagValue.setText("Tag Value");
    	}
    	else
    	{
    		//store the strings from type and value 
    		newTagType = tagType.getText();
    		newTagValue = tagValue.getText();
    		
    		
    		//check if type is more than 1 word
    		StringTokenizer tk = new StringTokenizer(newTagType);
    		
    		if(tk.countTokens() > 1)
    		{
    			JOptionPane.showMessageDialog(this, "Tag Type should be one word!"
						,"Error", JOptionPane.ERROR_MESSAGE);
    			//reset the field values 
    			tagType.setText("Tag Type");
	    		tagValue.setText("Tag Value");
    			return;
    		}
    		//trim whitespaces because this has to be one word (aka tokenzed as 1 word + space "type ")
    		newTagType = newTagType.trim();
    		
    		//reset the text fields to default
    		tagType.setText("Tag Type");
    		tagValue.setText("Tag Value");
    		

    		try{ //try to add tag 
    			if(ctrl.addTag(currentPhoto, newTagType, newTagValue))
    			{
    				//if we have the empty list indicator here, just remove it
    				if(tagsComboBoxModel.getElementAt(0).equals("No tags for this photo!"))
    				{
    					tagsComboBoxModel.removeElementAt(0);
    					removeTagButton.setEnabled(true);
    				}
    				if(newTagType.equals(""))//if there wasnt a type
    				{
    					tagsComboBoxModel.addElement(newTagValue); //no ' : ' needed .. just to make this cleaner
    				}
    				else
    				{
    					tagsComboBoxModel.addElement(newTagType + ":" + newTagValue); //include the ' : 
    				}
    			}
    			else
    			{
    				JOptionPane.showMessageDialog(this, "Failed at adding the tag. Try again."
							,"Error", JOptionPane.ERROR_MESSAGE);
    			}

    		}catch(ClassNotFoundException e){}
    		  catch(IOException e){};

    		
    		//success
			JOptionPane.showMessageDialog(this, "Successfully added Tag\nType: " + newTagType + "\nValue:" + newTagValue
																,"Success!", JOptionPane.PLAIN_MESSAGE);
    		
    	}
    	tagListComboBox.setSelectedIndex(0); //just in case set selected item to be the first thing in the list 
    }
    
    /**
     * clears the default "Tag Type" text(or any text there) when the text field is focused 
     * @param evt - tagType text field focused
     */
    private void tagTypeTextFocusGained(FocusEvent evt) {                                          
        tagType.setText("");
    }

    /**
     * clears the default "Tag Value" text(or any text there) when the text field is focused 
     * @param evt - tagValue text field focused
     */
    private void tagValueTextFocusGained(FocusEvent evt) {                                          
       tagValue.setText("");
        
    } 
    
    /**
     * makes the caption text field editable so you can alter the current caption
     * @param evt - edit caption pressed
     */
    private void editCaptionButtonActionPerformed(java.awt.event.ActionEvent evt) {
    	//make the caption editable 
    	currentCaptionText.setEditable(true);
    }

    /**
     * Preforms error checking on input, and then attempts to change the caption of the currently edited
     * photo to the content of caption text field.
     * @param evt - accept caption button pressed
     */
    private void acceptCaptiontButtonActionPerformed(java.awt.event.ActionEvent evt) {
        
    		//ge the text from text field
    		String newCaption = currentCaptionText.getText();
    		//error check , if caption wasn't changed or left empty 
    		if(newCaption.equals(caption))
    		{
    			JOptionPane.showMessageDialog(this, "You did not change the caption at all"
						,"Error: Incomplete fields!", JOptionPane.ERROR_MESSAGE);
    			return;
    		}
    		try {
    			//change the caption 
    			if(ctrl.recaption(currentPhoto, newCaption))
    			{
    				//success
    				JOptionPane.showMessageDialog(this, "Successfully changed caption to: \"" + newCaption + "\""
    																	,"Success!", JOptionPane.PLAIN_MESSAGE);
    				
    				//Edited and accepted so you cant mess with it right now
    				currentCaptionText.setEditable(false);
    			}
    			else
    			{
    				JOptionPane.showMessageDialog(this, "Something went wrong, caption not changed"
							,"Error: Incomplete fields!", JOptionPane.ERROR_MESSAGE);
    			}
			} catch (IOException e) {	}
    }
    
}
