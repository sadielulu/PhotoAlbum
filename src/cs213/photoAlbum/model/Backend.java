package cs213.photoAlbum.model;


import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;


/**
 * 
 * @author Oskar Bero
 *
 */
public class Backend implements BackendInterface, Serializable{
	
	//Serializable variables
	private static final long serialVersionUID = 351575227508756640L;
	private static final String storeDir = "data";
	private static final String storeFile = "users.dat";

	//FileOutputStream out;
	
	/**
	 * List of all users in memory
	 */
	public static ArrayList<User> users;
	
	public Backend() throws ClassNotFoundException, IOException
	{
		users = new ArrayList<User>();
		
		try
		{	
			users = readUser();
		}
		catch(EOFException e){ //if we get EOFException this means that we don't have an array yet - create a new array to later be stored
			
			users = new ArrayList<User>();
			System.out.println("Created a new array list since readusers had an empty file " + usersSize());
		}
		catch(FileNotFoundException ex)
		{
			//make a new file object
			File f = new File(storeDir);
			f.mkdir(); //make the directory since it doesnt exist
			users = new ArrayList<User>(); //make a new array list 
			
		}
		
	}
	
	@Override
	public void addUser(String id, String name) {
		//Control should have already checked for validity of this data
		users.add(new User(id, name));  //add user to the list	
		sortUsers(); //sort the array list in alphabetical order
		
	}

	public static ArrayList<User>  getUsers()
	{
		return users;
	}
	public static User getUser(String userID, String name){
		
		for(User user:users){
			if (userID.equalsIgnoreCase(user.ID));
			{
				return user;
			}
		
		}
		return null;
	}
	
	@Override
	public void deleteUser(User usr) {
		//Control should have already made all the checks for validity of this 
		users.remove(usr);
	}

	@Override
	public void listUsers() {
		for(User usr : users) 
		{
			System.out.println(usr.getID()); //print out all users
		}
	}

	@Override
	public void writeUser(Backend b) throws IOException {
		
		// new ObjectOutput stream
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		
		oos.writeObject(b.users); //Changed this to only save the array list since there are no other fields
		
	}

	@Override
	public ArrayList<User> readUser() throws ClassNotFoundException, IOException {
		
		
		//New object input stream
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		
		return (ArrayList<User>)ois.readObject();
	}
	
	/**
	 * 
	 * @param ID - user Id that you're checking for existence
	 * @return true if user is in the list of users, false otherwise
	 */
	public User userExists(String ID)
	{
				
		for(int i = 0; i < users.size(); i++)
		{
			if(users.get(i).getID().equals(ID))
			{
				return users.get(i);
			}
		}
		return null;
	}
	
	/**
	 * @return the size of users array list
	 */
	public int usersSize()
	{
		if(!(users.isEmpty()))
		{
			return users.size();
		}
		else
			return 0;
	}
	
	/**
	 * sort the users in alphabetical order
	 */
	public void sortUsers()
	{
		Collections.sort(users);
		
	}
	
	
}
