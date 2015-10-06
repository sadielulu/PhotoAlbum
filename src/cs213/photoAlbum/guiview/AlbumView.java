package cs213.photoAlbum.guiview;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cs213.photoAlbum.control.Control;
import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Photo;


/**
 * the album view class creates the album view window GUI of the user that logs in and it 
 * allows the user to delete, add albums, see the list of existing albums and to search among its photos
 * it brings up the photo window when an album is selected and open is pressed
 * @author Silvia Carbajal
 *
 */
@SuppressWarnings("serial")
public class AlbumView extends JFrame{
	
	JLabel startDateLabel, searchLabel,tagValueLabel,tagTypeLabel,endDateLabel;
	JTextField startDateField , endDateField, tagTypeField, tagValueField;
	JFrame searchFrame;
	String fullTag ;

	private JRadioButton[] buttons = {
            new JRadioButton("search by tag"),
            new JRadioButton("search by date"),
     }; 
	 DefaultComboBoxModel<String> tagsComboBoxModel = new DefaultComboBoxModel<String>();
	 JComboBox<String> tagsComboBox= new JComboBox<String>(tagsComboBoxModel) ;
	private JButton add ,del, logout, rename, search, open, delTagButton,addTagButton;
	private JPanel  buttonPanel,listPanel;
	boolean okSearch = false;
	Control userControl ;
	static JList<String> albumList;
	static DefaultListModel<String> albumListModel;
	JTextField searchField;
    List<String> tags = new ArrayList<String>();

	/**
	 * constructor of the album window class
	 * @param title name of the album window 
	 * @param c user's controller 
	 */
	public AlbumView(String title,Control c) 
	{
		super(title);
		userControl=c;
		setUp();
		setVisible(true);
	}

	/**
	 * sets up the album view window 
	 */
	public void setUp()
	{
		//buttons
		add = new JButton("Add album");
		del = new JButton("Delete album");
		logout = new JButton("Log Out");
		search= new JButton("Search");	
		rename= new JButton("Rename album");	
		open= new JButton("Open album");	
		
		//listeners
		AlbumAddListener albumAddListener = new AlbumAddListener();
		SearchListener searchListener = new SearchListener();
		AlbumDelListener albumDelListener = new AlbumDelListener();
		RenameAlbumListener renameAlbumListener = new RenameAlbumListener();
		ListListener ListListener = new ListListener();
		OpenAlbumListener openAlbumListener = new OpenAlbumListener();
		
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		addWindowListener( new WindowAdapter() {
             
             public void windowClosing(WindowEvent we) {
            	 if(!okSearch)
            	 {
            		 userControl.logOut();
            		 try {
         				new LogInView("login",userControl);
         				dispose();
         			} catch (ClassNotFoundException | IOException e1) {}
            	 }
             }
         } );
    
		listPanel = new JPanel();	
		albumListModel = new DefaultListModel<String>();
		albumList = new JList<String>(albumListModel);
		albumList.setLayoutOrientation(JList.VERTICAL);
		albumList.setVisibleRowCount(-1);
		
		GridBagLayout gridbag= new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();		

     	listPanel.setLayout(gridbag);
     	c.gridx = 0;
		c.gridy= 0;
		c.weightx = .2 ; 
		c.weighty= .5;
		c.gridheight=6;
		c.gridwidth = 5;
		c.fill=GridBagConstraints.BOTH;
		gridbag.setConstraints(albumList, c);
		listPanel.add(albumList,c);
		JScrollPane listScrollPane = new JScrollPane(albumList);
		listPanel.add(listScrollPane,c);
		c.gridx = 4;
		c.gridy= 8;
		c.weightx = .1 ; 
		c.weighty= 0;
		c.gridheight=1;
		c.gridwidth = 0;
		c.fill=GridBagConstraints.BOTH;
		gridbag.setConstraints(open, c);
		listPanel.add(open,c);		
		listPanel.setBorder(BorderFactory.createEmptyBorder(20,10,10,30));

		buttonPanel = new JPanel();	
		buttonPanel.setLayout(new GridLayout(10,2));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(40,40,20,50));
		buttonPanel.add(add);
		JLabel dummy = new JLabel(" ");
		buttonPanel.add(dummy);
		buttonPanel.add(del);
		JLabel dummy1 = new JLabel(" ");
		buttonPanel.add(dummy1);
		buttonPanel.add(rename);
		JLabel dummy2 = new JLabel(" ");
		buttonPanel.add(dummy2);
		buttonPanel.add(search);
		JLabel dummy3 = new JLabel(" ");
		buttonPanel.add(dummy3);
		buttonPanel.add(logout);
		
		setUpWindow();	

		albumList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		albumList.setLayoutOrientation(JList.VERTICAL_WRAP);
		albumList.addListSelectionListener(ListListener);
		add.addActionListener(albumAddListener);
		del.addActionListener(albumDelListener);
		search.addActionListener(searchListener);
		rename.addActionListener(renameAlbumListener);
		open.addActionListener(openAlbumListener);
		logout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });
		initList();
		setVisible(true);

	}
	
	/**
	 * initiates the album list 
	 */
	public void initList()
	{
		if(albumListModel.getSize() > 0)
    	{
			albumListModel.removeAllElements();
    	}
		
    	for(Album album: userControl.usr.getAlbums())
        {        	
    		albumListModel.addElement(album.getAlbumName());
        }
        
    }
		
	/**
	 * album add button listener
	 * @author Sadielulu
	 *
	 */
	public class AlbumAddListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{	
			String  albumName = JOptionPane.showInputDialog("Album Name:");	
			//when cancel is pressed
			if (albumName==null)
			{
				return;
			}
			//if nothing is in field
			if (albumName.isEmpty())
			{
				JOptionPane.showMessageDialog(null, "album needs a name", "Error", 
														JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//ADDS ALBUM
			if (userControl.addAlbum(albumName))
			{
				//UPDATES LIST
				initList();
			}
			else 
			{
				JOptionPane.showMessageDialog(null, "album cannot be added", "Error", 
                       JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * search button listener 
	 * brings up another window that can lead to another photo window if the search has more than 0 photos
	 * sets up the search window 
	 * @author Sadielulu
	 *
	 */
	public class SearchListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			//DISABLE X BUTTON
			setVisible(false);
			ButtonGroup bg =new ButtonGroup();
			
			searchFrame =new JFrame("Search");
			JPanel searchPanel =new JPanel ();
			JPanel labelPanel =new JPanel ();
			JPanel textPanel =new JPanel ();

			labelPanel.setLayout(new GridLayout(1,2));
			textPanel.setLayout(new GridLayout(5,3));
			searchPanel.setLayout(new GridLayout(4,2));
			labelPanel.setBorder(BorderFactory.createEmptyBorder(40,5,0,30));

			searchFrame.setVisible(true);
			searchFrame.setSize(400, 350);
			searchFrame.setResizable(false);
			searchFrame.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
	        searchFrame.addWindowListener( new WindowAdapter() {

	       public void windowClosing(WindowEvent we) {
	            	
	            	setVisible(true);
	            }
	        } );
			searchLabel= new JLabel("Search by date or tags : ");
			JLabel dummy = new JLabel (" ");
			JLabel dummy1 = new JLabel (" ");
			JLabel dummy3 = new JLabel (" ");
			JLabel dummy5 = new JLabel (" ");
			JLabel dummy6 = new JLabel (" ");

			tagValueField=new JTextField();
			tagTypeField=new JTextField();
			startDateField=new JTextField();
			endDateField=new JTextField();
			JButton okSearch, cancelSearch;
			okSearch =new JButton("search");
			cancelSearch=new JButton("cancel");
			addTagButton= new JButton("add tag ");
			delTagButton= new JButton("remove tag");
			
			bg.add(buttons[0]);
			bg.add(buttons[1]);
			buttons[0].setBorder(BorderFactory.createEmptyBorder(0,40,0,0));

			labelPanel.add(buttons[0]);
			labelPanel.add(buttons[1]);
	        searchPanel.add(tagsComboBox);
	        searchPanel.add(addTagButton);
	        searchPanel.add(dummy6);	        
	        searchPanel.add(delTagButton);       
	        searchPanel.add(dummy3);	 

	        textPanel.add(dummy);	        
	        textPanel.add(dummy1);
			startDateLabel= new JLabel("Start Date:");
			textPanel.add(startDateField);
			endDateLabel= new JLabel("End Date:");
			textPanel.add(startDateLabel);
			textPanel.add(startDateField);
			textPanel.add(endDateLabel);
			textPanel.add(endDateField);
			
			tagValueLabel= new JLabel("Tag Type: ");
			textPanel.add(tagValueLabel);
			textPanel.add(tagTypeField);
			tagTypeLabel= new JLabel("Tag Value");
			textPanel.add(tagTypeLabel);
			textPanel.add(tagValueField);
			
 	        searchPanel.add(dummy5);

            searchPanel.add(okSearch);
            searchPanel.add(cancelSearch);
            
            searchFrame.add(labelPanel, BorderLayout.NORTH);
             searchFrame.add(textPanel, BorderLayout.CENTER);
    		searchFrame.add(searchPanel, BorderLayout.SOUTH);

    		CancelSearchListener cl= new CancelSearchListener();
    		OkSearchListener ol = new OkSearchListener();
    		AddTagListener atl =new AddTagListener();
    		DelTagListener dtl =new DelTagListener();
    		
    		okSearch.addActionListener(ol);
    		cancelSearch.addActionListener(cl);
    		addTagButton.addActionListener(atl);
    		delTagButton.addActionListener(dtl);
    		
    		TagListener tl= new TagListener();
    		DateListener dl = new DateListener();
    		
    		buttons[0].addChangeListener(tl);
    		buttons[1].addChangeListener(dl);
    		buttons[0].setSelected(true);
    		
		}
	}

	/**
	 * tag listener in the search window , if tag is pressed 
	 * @author Sadielulu
	 *
	 */
	public class TagListener implements ChangeListener
	{
		public void stateChanged(ChangeEvent e) {

			startDateField.setEnabled(false);
			startDateLabel.setEnabled(false);
			 endDateLabel.setEnabled(false);
			 endDateField.setEnabled(false);
			 tagsComboBox.setEnabled(true);
			 addTagButton.setEnabled(true);
			 delTagButton.setEnabled(true);
			 tagTypeLabel.setEnabled(true);
			 tagValueLabel.setEnabled(true);
			 tagTypeField.setEnabled(true);
			 tagValueField.setEnabled(true);
		}
	}	
	
	/**
	 * date listener int he search window,  if date is pressed
	 * @author Sadielulu
	 *
	 */
	public class DateListener implements ChangeListener
	{
		@Override
		public void stateChanged(ChangeEvent e)
		{
			 tagTypeLabel.setEnabled(false);
			 tagValueLabel.setEnabled(false);
			 tagTypeField.setEnabled(false);
			 tagsComboBox.setEnabled(false);
			 addTagButton.setEnabled(false);
			 delTagButton.setEnabled(false);
			 tagValueField.setEnabled(false);
			 startDateField.setEnabled(true);
			 startDateLabel.setEnabled(true);
			 endDateLabel.setEnabled(true);
			 endDateField.setEnabled(true);
			 
		}
	}	
	
	/**
	 * cancel button in the search window, leads back ot album window
	 * @author Silvia Carbajal
	 *
	 */
	public class CancelSearchListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			//dispose the frame
			searchFrame.dispose();
			setVisible(true);
		}
	}	
	
	/**
	 * ok button listener in search window , can lead to a photo window
	 * @author Silvia Carbajal
	 *
	 */
	public class OkSearchListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			if(buttons[1].isSelected()){
				
				if (!endDateField.getText().isEmpty()&& !startDateField.getText().isEmpty())
				{
					try
					{
						//searches for photos from the list i got from get photos by date and creates an album from it 
						ArrayList<String> searchByDateAlbumList = userControl.getPhotosByDate(startDateField.getText(), endDateField.getText());
					//	Album searchByDateAlbum= new Album("search by dates album");
				//String fileName = null, caption = null;
						ArrayList<Album> albums = userControl.usr.getAlbums();
						userControl.addAlbum("tempAlbum");
						for (int i =0 ; i < searchByDateAlbumList.size(); i++)
						{	
							for(Album album :albums)
							{
								for(Photo photo :album.getPhotos())
								{
									if (searchByDateAlbumList.get(i).equals(photo.getPhotoPath()))
									{
										//String fileName=photo.getPhotoName();
									//	String caption=photo.getCaption();
										
										userControl.copyPhoto(photo.getPhotoPath(), album.getAlbumName(), "tempAlbum");
										//searchByDateAlbum.addPhoto(searchByDateAlbumList.get(i), fileName,caption );
										continue;
									}
								}
							}
						}
						//CHECKS IF ALBUM HAS NO PHOTOS
						if(userControl.usr.getAlbum("tempAlbum").getPhotos().size()==0){
							JOptionPane.showMessageDialog(null, "there are no existing photos with your search", "Error", 
									JOptionPane.ERROR_MESSAGE);
							userControl.deleteAlbum("tempAlbum");
							return;
						}
						else{
							searchFrame.dispose();
							okSearch = true;
							dispose();
							new AlbumPhotos(userControl,"tempAlbum",true);
						}
						

					} 
					catch (IOException e1) {
						e1.printStackTrace();
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Search for dates needs a start date and an end date ", "Error", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
			else if (buttons[0].isSelected())
			{
				if(tagsComboBoxModel.getSize()!=0)
				{
					for(int i =0; i<tagsComboBoxModel.getSize();i++)
					{	
						tags.add(i,tagsComboBoxModel.getElementAt(i));
					}
					try
					{
						ArrayList<String> tagList=userControl.getPhotosByTag(tags);
						//Album searchByTagAlbum= new Album("search by tag album");
				//	String fileName = null, caption = null;
						//ArrayList<Album> albums = userControl.usr.getAlbums();
						userControl.addAlbum("tempAlbum");
						for (int i =0 ; i < tagList.size(); i++)
						{	
							for(Album album :userControl.usr.getAlbums())
							{
								for(Photo photo : album.getPhotos())
								{
									if (tagList.get(i).equals(photo.getPhotoPath()))
									{
							//		fileName=photo.getPhotoName();
								//		caption=photo.getCaption();
										
										userControl.copyPhoto(photo.getPhotoPath(), album.getAlbumName(), "tempAlbum");
										continue;
									}
								}
							}
						}
						setVisible(false);
						if(userControl.usr.getAlbum("tempAlbum").getPhotos().size()==0){
							JOptionPane.showMessageDialog(null, "theres are no existing photos with your search", "Error", 
									JOptionPane.ERROR_MESSAGE);
							userControl.deleteAlbum("tempAlbum");
							return;
						}
						else{
							searchFrame.dispose();
							okSearch = true;
							dispose();
							new AlbumPhotos(userControl,"tempAlbum",true);
						}
					}	
					 catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null, "You have to add at least one tag", "Error", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
		}
	}	
	/**
	 * delete button listener in search window 
	 * @author Silvia Carbajal
	 *
	 */
	public class DelTagListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{		
			tagsComboBoxModel.removeElement(tagsComboBox.getSelectedItem());
		}
	}
	
	/**
	 * add tag button listener in search window
	 * @author Silvia Carbajal
	 */
	public class AddTagListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{		
			if (buttons[0].isSelected())
			{
				if (!tagTypeField.getText().isEmpty()&& !tagValueField.getText().isEmpty())
				{
					 fullTag =  tagTypeField.getText() + ":" + tagValueField.getText();		        		
					for(int i =0; i< tagsComboBoxModel.getSize();i++)
					{
						if(tagsComboBoxModel.getElementAt(i).equals(fullTag))
						{
							JOptionPane.showMessageDialog(null, "you cannot add the same tag twice ", "Error", 
										JOptionPane.ERROR_MESSAGE);
							return;
						}
					}
			        tagsComboBoxModel.addElement(fullTag);
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Search for tags needs a tag type and a tag value ", "Error", 
							JOptionPane.ERROR_MESSAGE);
				}			
			}
		}
	}	
	/**
	 * album delete button listener in album window
	 * @author Silvia Carbajal
	 *
	 */
	public class AlbumDelListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{		
			
			if(albumListModel.getSize()!=0)
			{
			//CREATES COMBO BOX
			String[] albumComboBox = new String [userControl.usr.albumsSize()];
			for(int i =0; i<albumComboBox.length; i++)
			{
				albumComboBox[i]=userControl.usr.getAlbums().get(i).getAlbumName();
			}	
			//DELETE DIALOG
			String deleteAlbum = (String)JOptionPane.showInputDialog(
                                             null,
                                             "Select an album to delete",
                                             "Album List",
                                             JOptionPane.QUESTION_MESSAGE,
                                             null,albumComboBox,null
                                             );
			//DELETES ALBUM
			if (userControl.deleteAlbum(deleteAlbum))
			{
				albumListModel.removeElement(deleteAlbum);
				//updates list
				initList();
				return;
			}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "no albums to delete", "Error", 
						JOptionPane.ERROR_MESSAGE);	
				return;
			}
			
		}
	}
	/**
	 * list listener
	 * not used
	 * @author Silvia Carbajal
	 *
	 */
	public class ListListener implements ListSelectionListener
	{
		@Override
		public void valueChanged(ListSelectionEvent e) 
		{			
			
		}
	}
	
	/**
	 * when exit window, app logs you out 
	 * @param e action
	 */
	public void logoutActionPerformed(ActionEvent e) 
		{	
			userControl.logOut();
			try {
				new LogInView("login",userControl);
				this.dispose();
			} catch (ClassNotFoundException | IOException e1) {}
			
		}
	
	

	/**
	 * rename button listener in album window
	 * @author Silvia Carbajal
	 *
	 */
	public class RenameAlbumListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent e) 
		{
			if(albumList.getSelectedValue()==null)
			{
				if(userControl.usr.albumsSize()==0)
				{
					JOptionPane.showMessageDialog(null, "no albums to rename", "Error", 
		                       JOptionPane.ERROR_MESSAGE);
					return;
				}
				JOptionPane.showMessageDialog(null, "select an album to rename from the list ", "Error", 
	                       JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			String oldAlbumName=albumList.getSelectedValue();
			String  newAlbumName = JOptionPane.showInputDialog("New album Name:");
			if(newAlbumName==null)
			{
				return;
			}
			if(newAlbumName.isEmpty())
			{
				JOptionPane.showMessageDialog(null, "need a new name to rename", "Error", 
	                       JOptionPane.ERROR_MESSAGE);
			}
			userControl.renameAlbum(oldAlbumName, newAlbumName);
			initList();
		}
	}

	/**
	 * open album button listener in album window
	 * @author Silvia Carbajal
	 *
	 */
	public class OpenAlbumListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent e) 
		{	
			//error checks
			if(albumList.getSelectedValue()==null)
			{		
				if(userControl.usr.albumsSize()==0){
					JOptionPane.showMessageDialog(null, "no albums to open", "Error", 
		                       JOptionPane.ERROR_MESSAGE);
					return;
				}
				JOptionPane.showMessageDialog(null, "select an album to open", "Error", 
	                       JOptionPane.ERROR_MESSAGE);
				return;
			}
			//opens album photos
			String albumName=albumList.getSelectedValue();
			setVisible(false);
			new AlbumPhotos(userControl,albumName, false);
		}
	}
	
		
	/**
	 * sets up album window 
	 */
	public void setUpWindow()
	{
		//DISABLE X BUTTON and for admin too
		add(listPanel);
		add(buttonPanel, BorderLayout.WEST);
		setVisible(true);
		setSize(500,550);
		setResizable(false);
	}
	
}
