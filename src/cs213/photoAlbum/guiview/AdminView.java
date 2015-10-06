package cs213.photoAlbum.guiview;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cs213.photoAlbum.control.Control;
import cs213.photoAlbum.model.User;


/**
 * the adminview class creates the admin window GUI and it 
 * allows the administrator to delete and add users and see the existing users
 * @author Silvia carbajal
 *
 */
@SuppressWarnings("serial")
public class AdminView extends JFrame 
{
	
	Control adminControl;
	JButton add ,del, logout, submit, cancel;
	DefaultComboBoxModel<String> userList;
	JTextField name, userID; 
	JComboBox<String> userComboBox ;
	JLabel name_label, userID_label;
	JPanel listAndTextPanel, buttonPanel,listPanel;

	/**
	 * constructor of the admin window class
	 * @param s title of admin window
	 * @param c admin controller 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public AdminView(String s,Control c) throws ClassNotFoundException, IOException 
	{
		super(s);
		//setName(s);
		adminControl= c;
		setUp();
		setUpWindow();
	}
	
	/**
	 * sets up the admin window
	 */
	public void setUp()
	{
		//buttons 
		add = new JButton("Add User");
		del = new JButton("Delete User");
		logout = new JButton("Log Out");
		cancel= new JButton("cancel");	
		submit= new JButton("submit");	

		userComboBox = new JComboBox<String>(); //add names
    	userList = new DefaultComboBoxModel<String>();
        userComboBox.setModel(userList);

		name = new JTextField(null,20);
		userID = new JTextField(null,20);
		name_label = new JLabel("Name:");
		userID_label = new JLabel("User ID:");
		
		//button panel and its components
		buttonPanel = new JPanel();	
		buttonPanel.setLayout(new GridLayout(7,2));
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(80,30,0,5));
		buttonPanel.add(add);
		buttonPanel.add(del);
		JLabel dummy = new JLabel(" ");
		JLabel dummy1 = new JLabel(" ");
		JLabel dummy2 = new JLabel(" ");
		buttonPanel.add(dummy);
		buttonPanel.add(dummy1);
		buttonPanel.add(dummy2);
		JLabel dummy3 = new JLabel(" ");
		JLabel dummy4 = new JLabel(" ");
		JLabel dummy5 = new JLabel(" ");
		JLabel dummy6 = new JLabel(" ");
		buttonPanel.add(dummy4);
		buttonPanel.add(dummy5);
		buttonPanel.add(dummy6);
		buttonPanel.add(logout);
		
		//list and text panel and its components
		listAndTextPanel = new JPanel();	
		listAndTextPanel.setLayout(new GridLayout(12,4));
		listAndTextPanel.setBorder(BorderFactory.createEmptyBorder(15,5,0,30));
		userComboBox.setBorder(BorderFactory.createEmptyBorder(0,0,10,5));
		listAndTextPanel.add(userComboBox);
		listAndTextPanel.add(dummy3);
		listAndTextPanel.add(dummy2);
		listAndTextPanel.add(dummy1);
		listAndTextPanel.add(dummy);
		listAndTextPanel.add(name_label);
		listAndTextPanel.add(name);
		listAndTextPanel.add(userID_label);
		listAndTextPanel.add(userID);
		listAndTextPanel.add(submit);
		listAndTextPanel.add(cancel);

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		addWindowListener( new WindowAdapter() {
		
            public void windowClosing(WindowEvent we) {
            	//bring back the album photos window
            	try {
					new LogInView("photo album",adminControl);
				} catch (ClassNotFoundException | IOException e) {	}
            }
        } );
		
		AddListener addListener = new AddListener();
		CancelListener cancelListener = new CancelListener();
		DelListener delListener = new DelListener();
		//LogoutListener logoutListener = new LogoutListener();
		SubmitListener submitListener = new SubmitListener();
		
		add.addActionListener(addListener);
		del.addActionListener(delListener);
		submit.addActionListener(submitListener);		
		cancel.addActionListener(cancelListener);
	     logout.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent evt) {
	                logoutActionPerformed(evt);
	            }
	        });
		
		name.setEnabled(false);
		userID.setEnabled(false);
		submit.setEnabled(false);
		cancel.setEnabled(false);
		name_label.setEnabled(false);
		userID_label.setEnabled(false);

		initList();
	}

	/**
	 * initiates the user list 
	 */
	@SuppressWarnings("static-access")
	public void initList(){
		if(userList.getSize() > 0)
    	{
			userList.removeAllElements();
    	}
		
    	//Fill the Users combo box!
    	userList.insertElementAt("Users", 0);
       
    	
    	for(User user: adminControl.backend.users)
        {        	
        	 userList.addElement(user.getID());
        }
        
        //if combo box is empty after adding in all the users 
        if(userList.getSize() == 0)
        {
        	userList.addElement("No users");
         }
        userComboBox.setSelectedIndex(0);
    }
	
	/**
	 *  add button listener for the admin window 
	 *  adds the user to userlist 
	 * @author Silvia Carbajal
	 *
	 */
	public class AddListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			//texfields and submit and cancel  button are visible 
			add.setEnabled(false);
			logout.setEnabled(false);
			del.setEnabled(false);
			userComboBox.setEnabled(false);
			name.setEnabled(true);
			userID.setEnabled(true);
			submit.setEnabled(true);
			cancel.setEnabled(true);
			name_label.setEnabled(true);
			userID_label.setEnabled(true);
		}
	}
	
	/**
	 * delete button listener in admin window
	 * when button is pressed , it deletes the selected field in the combo box of users 
	 * @author Silvia Carbajal
	 *
	 */
	public class DelListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent e)
		{		
			//CHECK
			if(userList.getSize()!=0)
			{
				String deleteUser= (String)userComboBox.getSelectedItem();
				try {
					if (adminControl.deleteUser(deleteUser))
					{
						userList.removeElement(deleteUser);
					}
					else
					{
						JOptionPane.showMessageDialog(null, "user not selected", "Error", 
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				initList();
			}
			else
			{
				JOptionPane.showMessageDialog(null, "no users to delete", "Error", 
						JOptionPane.ERROR_MESSAGE);	
				return;
			}
				//deletes from list 
				//if combo box nothing selected then error display 
		}
	}
	
	/**
	 * cancel button listener in the admin window 
	 * @author Silvia Carbajal
	 *
	 */
	public class CancelListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent e)
		{
			//submit is pressed make the texfields and buttons nt visible ,
			//adn empty the texfiekdls
			add.setEnabled(true);
			logout.setEnabled(true);
			del.setEnabled(true);
			userComboBox.setEnabled(true);
		
		 
			name.setEnabled(false);
			userID.setEnabled(false);
			submit.setEnabled(false);
			cancel.setEnabled(false);
			name_label.setEnabled(false);
			userID_label.setEnabled(false);
			
			name.setText("");
			userID.setText("");
		}
	}
	
	/**
	 * submit button listener in the admin window
	 * when button is pressed ,takes the textfield input and adds that to the list of albums
	 * @author Silvia Carbajal
	 *
	 */
	public class SubmitListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
		
			//submit is pressed make the texfields and buttons nt visible ,
			add.setEnabled(true);
			logout.setEnabled(true);
			del.setEnabled(true);
			userComboBox.setEnabled(true);
			
			name.setEnabled(false);
			userID.setEnabled(false);
			submit.setEnabled(false);
			cancel.setEnabled(false);
			name_label.setEnabled(false);
			userID_label.setEnabled(false);
			
			//add user
			
			if(name.getText().equals(null) || userID.getText().equals(null)){
				JOptionPane.showMessageDialog(null, "need userID and name", "Error", 
                        JOptionPane.ERROR_MESSAGE);
			}
			 
			String n=name.getText();
			String u=userID.getText();
		
			//cheeck if its is null or spaces 
			if(n.equals(" ") || u.equals(" "))
			{
				JOptionPane.showMessageDialog(null, "need userID and name", "Error", 
                        JOptionPane.ERROR_MESSAGE);
			}
			
			try {
				if (adminControl.addUser(u, n))
				{
					userList.addElement(u);
					initList();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "cannot add users", "Error", 
                            JOptionPane.ERROR_MESSAGE);
				}
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			name.setText("");
			userID.setText("");
		
		}
	}
	
	/**
	 * saves everything after you logout 
	 * @param e event of button logout being pressed
	 */
	public void logoutActionPerformed(ActionEvent e) 
	{		
		adminControl.logOut();
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	
	/**
	 * sets up the window for admin window 
	 */
	public void setUpWindow()
	{
		add(buttonPanel, BorderLayout.WEST);
		add(listAndTextPanel, BorderLayout.EAST);
		setVisible(true);
		setSize(500,450);
		setResizable(false);
	}
}
