package cs213.photoAlbum.control;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public interface ControlInterface {
	
	/**
	 *interacts with command view to get implementation of log in 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @return true if you can login/login succesfull, false if user doesnt exist or other failure
	 */
	 boolean logIn(String userId) throws ClassNotFoundException, IOException;
	
	/**
	 *interacts with command view to get implementation of log out 
	 */
	 boolean logOut();
	
	/**
	 * adds a user to user list
	 * @param id user's ID
	 * @param name name of the user
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	 boolean addUser(String id, String name) throws ClassNotFoundException, IOException;
	
	/**
	 * deletes user by id from list
	 * @param id of the user we are deleting 
	 * @return true if succesful , false otherwise
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	 boolean deleteUser(String id) throws ClassNotFoundException, IOException;
 
	/**
	 * list users 
	 * @return 
	 */
	 void listUsers();
	
	/**
	 * add album to users album
	 * @param albumName, album's name that is added(created)
	 * @return true if succesful , false otherwise
	 */
	boolean addAlbum(String albumName);
	
	/**
	 * delete album from user
	 * @param albumName, album name that is being deleted
	 * @return true if succesful , false otherwise
	 */
	boolean deleteAlbum(String albumName);
	
	
	/**
	 * renames album
	 * @param albumName, name of the album we are renaming
	 * @param otherName, new name of the album
	 */
//	public void renameAlbum(String albumName, String otherName);
	
	/**
	 * 
	 * lists all the albums for the user that's logged in
	 */
	void listAlbums();

	/**
	 * add photo to album
	 * @param String fileName - name of photo to be added
	 * @param String caption - the caption for the photo
	 * @return true if succesful , false otherwise
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	boolean addPhoto(String fileName, String caption,String albumName) throws ClassNotFoundException, IOException;
	
	/**
	 * delete photo from album
	 * @param fileName- the photo we are deleting by name
	 * @return true if succesful , false otherwise
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	boolean deletePhoto(String fileName, String albumName) throws ClassNotFoundException, IOException;
	
	/**
	 * list photos of album
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	  void listPhotos(String albumName) throws ClassNotFoundException, IOException;
	 
	
	/**
	 * displays information of filename
	 * @param fileName, list information about this filename
	 * can be a call directly to photo
	 * @throws IOException 
	 * @throws ClassNotFoundException 
  	 */	
  	 	void listPhotoInfo(String fileName) throws ClassNotFoundException, IOException;
     

	/**
	 * displays photos organized by date in the given date range
	 * @param startDate, starting date of the photos
	 * @param endDate, ending date of the photos
	 * @throws IOException 
	 * @throws ParseException 
	*/
	 ArrayList<String> getPhotosByDate(String startDate, String endDate) throws IOException, ParseException;
	
	/**
	 * display photos by tag
	 * @param string containing the tag types/values to organize photos by
	 * @throws IOException 
	 *
	*/ 
	ArrayList<String> getPhotosByTag(List<String> tags) throws IOException;
	

 	/**
	 * add tag to photo
	 * @param fileName photos name
	 * @param type
	 * @param value
	 * @return true if succesful , false otherwise
 	 * @throws IOException 
 	 * @throws ClassNotFoundException 
	 */
	boolean addTag(String fileName,String type,String value) throws ClassNotFoundException, IOException;
	
	/**
	 * delete tag from photo
	 * @param fileName photo name
	 * @param type 
	 * @param value 
	 * @return true if succesful , false otherwise
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	boolean deleteTag(String fileName,String type,String value) throws ClassNotFoundException, IOException;
	
	 
	 /**
	  * moves photo from album to another album
	  * @param albumName , we get photo from here
	  * @param toAlbumName , and move it to here 
	  * @return true if succesful , false otherwise
	  * @throws IOException 
	  * @throws ClassNotFoundException 
	  */
	boolean movePhoto(String photoName, String albumName, String toAlbumName) throws ClassNotFoundException, IOException;

	/**
	 * 
	 * @param fileName - photo name 
	 * @param newCaption - new caption string 
	 * @return true if succesful , false otherwise
	 * @throws IOException
	 */
	public boolean recaption(String fileName,String newCaption) throws IOException;
}
