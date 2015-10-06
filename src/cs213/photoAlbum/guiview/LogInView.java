package cs213.photoAlbum.guiview;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cs213.photoAlbum.control.Control;

/**
 * the login window class logs in the user or admin to their respected accounts and GUI 
 * @author Silvia Carbajal
 *
 */
@SuppressWarnings("serial")
public class LogInView extends JFrame {
	private GridBagConstraints c;
	private JPanel mainPanel;
	private JTextField input;
	private Control control;
	
	/**
	 * log in window class constructor
	 * @param s title of window
	 * @param ctrl controller we use for the rest of the application , until user or admin log out
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public LogInView(String s, Control ctrl) throws ClassNotFoundException, IOException
	{
		super(s);
		control = ctrl;
		mainPanel = new JPanel(new GridBagLayout());
		c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		addComponents();
		setUpWindow();
		pack();

	}
	/**
	 * sets up components in login window
	 */
	public void addComponents()
	{
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	    ImageIcon mainPic  = new ImageIcon("2187501.jpg");
	    
	    JLabel picture = new JLabel("",mainPic,JLabel.CENTER);
	    c.insets = new Insets(10,20, 0, 0); //topleftbottomright  
	    c.gridx = 1;
	    c.gridy = 2;
	    c.weightx = .35;                                   
	    c.weighty = .75;
	    c.gridheight=2;
	    c.gridwidth = 2;	
	    mainPanel.add(picture,c);
	
		JButton login = new JButton("Log in");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 7;
    	c.weightx = .7;
    	c.gridwidth = 2;
    	c.gridheight=1;
    	c.weighty = .05;
    	c.insets = new Insets(0, 200,10, 200);
    	mainPanel.add(login, c);

    	JLabel login_userID_label = new JLabel("user ID:");
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 4;
		c.weightx = .9;
		c.gridwidth = 2;
		c.gridheight=1;
		c.weighty = 0;
		c.insets = new Insets(0, 100,0, 80);
		mainPanel.add(login_userID_label,c); 
		
		input= new JTextField();
		input.setEditable(true);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;	
		c.gridy = 5;	
		c.weightx = .7;
		c.gridwidth = 2;
		c.gridheight=1;
		c.weighty = .05;
		c.insets = new Insets(0, 100,0, 80);
		mainPanel.add(input,c);	
		input.setVisible(true);

		loginListener loginListener = new loginListener();
		login.addActionListener(loginListener);

	}
	/**
	 * login button listener in login window 
	 * @author Silvia Carbajal
	 *
	 */
	public class loginListener implements ActionListener 
	{
		public void actionPerformed(ActionEvent e)
		{
			String s =input.getText();
			if(s.equalsIgnoreCase("admin"))
			{
				try {
					new AdminView("Administrator",control);
					setVisible(false);

				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
			else {
				
					try {

						if(control.logIn(s))
						{

							new AlbumView(s + "'s albums",control);
							setVisible(false);
						}
						else {
							 JOptionPane.showMessageDialog(null, "userID does not exist", "Error", 
                                     JOptionPane.ERROR_MESSAGE);
						}
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		}
	}

	/**
	 * sets up log in window 
	 */
	public void setUpWindow()
	{
		add(mainPanel,BorderLayout.CENTER);
		setVisible(true);
		setSize(500,450);
		setResizable(false);
	}
	
	
}
