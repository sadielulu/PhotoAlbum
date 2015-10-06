package cs213.photoAlbum.model;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 *user should have an array list of albums for a given user or tree? or hash
 * implementation of the user interface
 * @author Silvia Carbajal
 *
 */


public class User implements Serializable, Comparable<User>{
	
	private static final long serialVersionUID = 351575227508756640L;
	
	String ID, name;
	ArrayList<Album> albums;
	/**
	 * constructor of the user class
	 * @param ID, chosen ID of the user
	 * @param name, full name of the user
	 * 
	 */
	
	public User(String ID, String name){
		 this.ID = ID;
		 this.name = name;
		 albums = new ArrayList<Album>();
	}
	
	/**
	 * set name of user 
	 * @param newName, new name of the user
	 */
	public void setName(String newName){
		this.name = newName;
 	}
	
	/**
	 * get User's name
	 * @return user's name
	 */
	public String getName(){
 		return name;
	}
	
	/**
	 * set the user's ID
	 * @param newID, what we want to change it to
	 */
	public void setID(String newID){
		this.ID = newID;
	}
	
	/**
	 * get user's ID
	 * @return user's ID
	 */
	public String getID(){
		return ID;
	}

	/**
	 * 
	 * @param albumName
	 */
	public void addAlbum(String albumName)
	{
		//check if it already exists
		Album newAlbum= new Album(albumName);
		albums.add(newAlbum);
		Collections.sort(albums);
	}

	/**
	 * 
	 * @param albumName
	 */
	public void deleteAlbum(String albumName) 
	{	
		for(int i=0;i<albums.size();i++)
		{
			if(albums.get(i).getAlbumName().equalsIgnoreCase(albumName))
			{
				albums.remove(i);
			}
		}
	}  

	/**
	 * 
	 */
	public void listAlbums() 
	{
		Collections.sort(albums);
		for(int i =0;i<albums.size();i++)
		{
			String s= albums.get(i).photoStartEndDate();
			System.out.println(s);
		}
		 
	} 
			
	/**
	 * 
	 * @return
	 */
	public ArrayList<Album> getAlbums() {

		return albums;
	}	
	
	
	/**
	 * 
	 * @param albumName
	 * @return
	 */
	 public Album getAlbum(String albumName){
		 for(int i =0;i<albums.size();i++){
			 if(albums.get(i).getAlbumName().equalsIgnoreCase(albumName)){
				 return albums.get(i);
			 }
		 }
		 return null;
	 }
	 
	 /**
	  * 
	  * @param otherUser
	  * @return
	  */
	 public boolean equals(User otherUser)
	 {
		return ID.equals(otherUser.getID());
	 }

	@Override
	public int compareTo(User usr) {
		
		return ID.compareTo(usr.ID);
	}
 
	/**
	 * 
	 * @return
	 */
	public int albumsSize()
	{
		if(!(albums.isEmpty()))
		{
			return albums.size();
		}
		else
			return 0;
	}
	
	public void getPhotosByDate(List p){
		
		//PhotoDateComparator pp =new PhotoDateComparator();
		//Collections.sort(p,pp);
		
		//for(int i =0;i<p.size();i++){
			//if()
			//System.out.println(p.get(i).get(i).getCaption() + "- Album: "+a.getAlbumName()+ "- Date: "+p.getDate());

	//	}
		
		//format.format(date.getTime());
			//have to be sorted?
		// check for sixe 0 in photos 
	}
	
	
	
	public class PhotoDateComparator implements Comparator<Photo> {

		@Override
			public int compare(Photo p1, Photo p2) {
				return p1.getDateAndTime().compareTo(p2.getDateAndTime());
			}
		}
	//public void renameAlbum(String albumName, String otherName) {
		
	//}
	
}
