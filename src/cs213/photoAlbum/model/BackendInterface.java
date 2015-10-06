package cs213.photoAlbum.model;

import java.io.IOException;
import java.util.ArrayList;



/**
 * This class is the implementation of the backend interface
 * @author Silvia Carbajal
 *
 */

public interface BackendInterface {

	//BACKEND 
		/**
		 * adds a user to user list
		 * @param id user's ID
		 * @param name name of the user
		 */
		void addUser(String id, String name);
		
		/**
		 * deletes user by id from list
		 * @param User which we are deleting 
		 */
		void deleteUser(User usr);
		
		/**
		 *displays all users 
		 */
		void listUsers ();
		
				
		/**
		 * write users , persistence from the backend object calling the mehtod
		 * Saves the users array in backend to a users.dat file 
		 * @throws IOException 
		 * @param b - version of the backend to be stored
		 */
		void writeUser(Backend b) throws IOException;
		
		/**
		 * reads users ,peristence
		 * @return an arrayList of all users
		 * @throws IOException 
		 * @throws ClassNotFoundException 
		 */
		ArrayList<User> readUser() throws ClassNotFoundException, IOException;
	//USER 
		
	
		 
}