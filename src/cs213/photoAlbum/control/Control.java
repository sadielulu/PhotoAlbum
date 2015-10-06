package cs213.photoAlbum.control;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import cs213.photoAlbum.model.Album;
import cs213.photoAlbum.model.Backend;
import cs213.photoAlbum.model.Photo;
import cs213.photoAlbum.model.User;

/**
 * 
 * this class implements the logic part of the project
 * checks if we are able to make such move , methods have to go through this
 * The control is not  tied with any one particular model implementation, it interact with the backend interface.

 * @author Oskar Bero
 * @author Silvia Carbajal
 * 
 */
public class Control implements ControlInterface{
	
	public Backend backend;
	public User usr;
	StringTokenizer tk;
	public Control() throws ClassNotFoundException, IOException
	{
		this.backend = new Backend();  //get ourselves a backend object to carry out the operations needed	
	}
	
	@Override
	public boolean logIn(String userId) throws ClassNotFoundException, IOException{
			
		usr = backend.userExists(userId);
		
		if(usr != null)
		{		
			return true;
		}
		else
		{
			////System.out.println("user " +userId +" does not exist ");
			return false; 
		}
	}


	@Override
	public boolean logOut() {
		
		//gonna be called from interactive mode 
		try {
			backend.writeUser(backend); //save the changes made during interactive mode session
			return true;
		} catch (IOException e) {
			
			return false;
		}

	}
	@Override
	public boolean addUser(String id, String name) throws ClassNotFoundException, IOException {
		
		if(id.isEmpty() || name.isEmpty())
		{
			return false;
		}
		
		usr = backend.userExists(id);
		
		if(usr != null)
		{
			////System.out.println("user " + id +" already exists with name " + name); //already exists
			return false;
		}
		else
		{
			backend.addUser(id, name); //use backend's adduser function to store it in the array , and save				
			backend.writeUser(backend); //store the change into the users.dat file
		//	//System.out.println("created user " + id + " with name " + name);  // display what we created
			return true;
		}
	
	}

	@Override
	public boolean deleteUser(String id) throws ClassNotFoundException, IOException {
		
		usr = backend.userExists(id);
		
		if(usr != null)
		{
			backend.deleteUser(usr); //delete user matching the object passed in
			backend.writeUser(backend);
	//		//System.out.println("Deleted user " + id);
			return true;
		}
		else
		{
		//	//System.out.println("user " + id + " does not exist");
			return false;
		}
	}
	
	@Override
	public void listUsers() {
		//not sure if this is working .. gotta try though
		if(backend.usersSize() > 0) //if we have any users
		{
			backend.listUsers(); //print them out
		}
		else
		{
			//System.out.println("no users exist");
		}
 	}


	@Override
	public boolean addAlbum(String albumName)
	{
		
		
		for(Album addAlbum:usr.getAlbums())
		{
			if(addAlbum.getAlbumName().equalsIgnoreCase(albumName))
			{
				////System.out.println("Album exists for user " + usr.getID() +":" );
				////System.out.println(albumName);
				return false;
			}
		}
		usr.addAlbum(albumName); 
	//	System.out.print("created album for user "+usr.getID()+":");
	//	//System.out.println( );
	//	//System.out.println(albumName);
		return true;
	}

	@Override
	public boolean deleteAlbum(String albumName) {
		for(Album delAlbum:usr.getAlbums())
		{
			if(delAlbum.getAlbumName().equalsIgnoreCase(albumName))
			{
				usr.deleteAlbum(albumName);
				////System.out.println("deleted album from user "+usr.getID()+":"); 
				////System.out.println(albumName);
				return true;
			}
		}
		////System.out.println("album does not exist for user "+usr.getID()+":");
		////System.out.println(albumName);
		return false;
	}
			

	@Override
	public boolean addPhoto(String path, String caption, String albumName) throws IOException
	{
		String photoName = "aisghadsipugh";
		if(path.equals(null))
		{
			//System.out.println("File "+path+ " does not exist");
			return false;
		}
		
		//tokenize to save photoName
		tk = new StringTokenizer(path);
		
		while(tk.hasMoreTokens())
		{
			photoName = tk.nextToken(File.separator); //tokenize based on the path
			
		}
		System.out.println(path);
		File file = new File(path);
		if(file.exists())
		{
			for(Album a:usr.getAlbums())
			{
				if(a.getAlbumName().equalsIgnoreCase(albumName))
				{
					for(Photo addPhoto:usr.getAlbum(albumName).getPhotos())
					{
						//Photo p=new Photo(fileName,caption); //dummy
						//fileName=p.getPhotoName();
						if(addPhoto.getPhotoName().equals(photoName))
						{
							//System.out.println("Photo "+path+" already exists in album "+albumName);
							return false;
						}
					}
					System.out.println(file.getCanonicalPath().toString());
					usr.getAlbum(albumName).addPhoto(file.getCanonicalPath().toString(), photoName, caption);
					//System.out.println("added photo "+path+":");
					//System.out.println(caption+" - Album: "+albumName );
					return true;
				}
			}
			//System.out.println("Album "+albumName+" does not exist");
			return false;
		}
		else
		{
			//System.out.println("file doesnt not exist");
			return false;
		}
	}


	@Override
	public boolean deletePhoto(String fileName, String albumName) throws ClassNotFoundException, IOException {
		
		//in case of a full path specified 
		tk = new StringTokenizer(fileName);
		while(tk.hasMoreTokens())
		{
			fileName = tk.nextToken(File.separator);		//tokenize based on the path
		}
		
		for(Album aa:usr.getAlbums())
		{ 
			if (aa.getAlbumName().equals(albumName))
			{
				for(Photo deletePhoto:usr.getAlbum(albumName).getPhotos())
				{
					/* commented out to see if delete finds photo by name!
					Photo p=new Photo(fileName,null); //dummy
					fileName=p.getPhotoName();*/
					if(deletePhoto.getPhotoName().equals(fileName))
					{
						
						usr.getAlbum(albumName).deletePhoto(fileName); //remove if only one photo too check?
						System.out.print("Removed photo:");
						//System.out.println(fileName +"- from album "+albumName);
						return true;
					}
				}
				//System.out.println("Photo "+ fileName +" is not in album "+albumName);
				return false;
			}
		}	
		//System.out.println("Album "+albumName +" does not exist");
		return false;
	}


	
	@Override
	public ArrayList<String> getPhotosByDate(String startDate, String endDate) throws IOException, ParseException {

		SimpleDateFormat format= new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss",Locale.ENGLISH);
		List<Photo> pArray =new ArrayList<Photo>();
		ArrayList<String> paths = new ArrayList<String>();
		Calendar s=Calendar.getInstance();
		Calendar e=Calendar.getInstance();
		s.setTime(format.parse(startDate));
		e.setTime(format.parse(endDate));
	//	//System.out.println("Photos for user "+ usr.getID()+ " in range " + startDate+ " to " +endDate+" :"); 

		if (s.compareTo(e) <= 0){
		for(Album a :usr.getAlbums()){
	 		for (Photo p: a.getPhotos()){
	 			Calendar date = p.getDateAndTime();
	 	 		if (date.compareTo(s) >= 0){
	 	 			if(date.compareTo(e) <= 0) {
	 	 				//pArray.add(p);
	 	 				//check for zero photos
	 	 				format.format(date.getTime());
	 	 				////System.out.println(p.getCaption() + "- Album: "+a.getAlbumName()+ "- Date: "+p.getDate());
	 	 				//getall albums
	 	 				paths.add(p.getPhotoPath());
	 	 			}
	 	 		}   
	 		}
	 	}
		}
		//usr.getPhotosByDate(pArray);
		return paths;
	}
	

	
	@Override
	public ArrayList<String> getPhotosByTag(List<String> tags) throws IOException {
		//first token in commands is the initial command and not a tag or value
	//	ArrayList<Photo> photos = new ArrayList<Photo>();
		ArrayList<String> paths = new ArrayList<String>();
		ArrayList<String> searchTags = new ArrayList<String>();
	//	System.out.print("Photos for user " + usr.getID() +" containing ");
		int i = 0;
		for(String str: tags)
		{			
			searchTags.add(str);
		
		}
		//System.out.println("\n");//move the display under the headder
		//we have all the tag values we wanna get , now for the photos that we want
		for(Album a:usr.getAlbums())
		{
			//check all albums
			for(Photo p:a.getPhotos())
			{
				//and check all photos in said albums
				for(i = 0; i < searchTags.size(); i++)
				{
					if(p.checkTagValue(searchTags.get(i)))//if this photo contains the tag value we want 
					{
						//get a path from the photo add to return array
						paths.add(p.getPhotoPath());
						
					}
				}
			}
		}
		return paths;
		/*
		Collections.sort(photos, new Comparator<Photo>() { //sort photos using an annonymous comparator class that sorts based on dates
			  public int compare(Photo p1, Photo p2) {
			      return p1.getDateAndTime().compareTo(p2.getDateAndTime());
			  }
			});
	
		for(Photo p:photos)
		{
			System.out.print(p.getPhotoName() + " - Album:");
	
			for(Album a: usr.getAlbums())
			{
				if(a.getPhotos().contains(p))
				{
					System.out.print(a.getAlbumName() + " - ");
				}
			}
			//System.out.println("Date: " + p.getDate());
		}*/
	}

	@Override
	public boolean addTag(String fileName, String type, String value) throws ClassNotFoundException, IOException 
	{	
		//in case the input is a path, it is reduced to the name and the name is checked against other photots
		tk = new StringTokenizer(fileName);
		while(tk.hasMoreTokens())
		{
			fileName = tk.nextToken(File.separator); //tokenize based on the path
		}
		
		if(usr.getAlbums().isEmpty())
		{
			return false;
		}
		for(Album aa:usr.getAlbums())
		{ 
			
			for(Photo p:aa.getPhotos())
			{
				/*
				Photo ph =new Photo(fileName,null); //dummy
				fileName= ph.getPhotoName();*/
				if(p.getPhotoName().equals(fileName))
				{
					
					for(String[] t: p.getTags())
					{
						if((t[0].equals(type) && t[1].equals(value)))
						{
							//System.out.println("Tag already exist for "+fileName+ " "+type+":"+value);
							return false;
							//tag already exists
						}
					}
						p.addTag(type, value);
						return true;
				}
	
			}
		}
		//System.out.println("Photo "+fileName+ " does not exist");
		return false;
	}

	@Override
	public boolean deleteTag(String fileName, String type, String value) throws IOException
	{	
		
		//in case the input is a path 
		tk = new StringTokenizer(fileName);
		while(tk.hasMoreTokens())
		{
			fileName = tk.nextToken(File.separator); //tokenize based on the path
			
		}
		
		//System.out.println(fileName);
		for(Album aa:usr.getAlbums())
		{ 
			for(Photo p:aa.getPhotos())
			{
				if(fileName.equals(p.getPhotoName())) //found a photo containing the tag
				{

					for(String[] tag: p.getTags())
					{
						////System.out.println(tag[0]+ ":" + tag[1]);
						if(tag[0].equals(type) && tag[1].equals(value))
						{
								p.deleteTag(type, value);
								return true;
						}
					}

				}		
			}
		}
		//System.out.println("Photo "+fileName+ " does not exist");
		return false;
	}



	@Override 
	public boolean movePhoto(String photoName, String albumName, String toAlbumName) throws ClassNotFoundException, IOException
	{	
		tk = new StringTokenizer(photoName);
		while(tk.hasMoreTokens())
		{
			photoName = tk.nextToken(File.separator); //tokenize based on the path
			
		}
		if(usr.getAlbum(albumName) != null && usr.getAlbum(albumName).getPhoto(photoName) != null) //album exists and photo exists
		{
			if(usr.getAlbum(toAlbumName) != null && (usr.getAlbum(toAlbumName).getPhoto(photoName)== null)) //dest album exists and doesnt contain photo
			{
				usr.getAlbum(albumName).movePhoto(usr.getAlbum(albumName).getPhoto(photoName), usr.getAlbum(toAlbumName));
			
				//success
				//System.out.println("Moved photo "+photoName +":");
				//System.out.println(photoName +"-From album "+albumName+" to album "+toAlbumName);
				return true;
			}
		}
		return false;
	}

	public boolean copyPhoto(String photoName, String albumName, String toAlbumName) throws IOException
	{	
		tk = new StringTokenizer(photoName);
		while(tk.hasMoreTokens())
		{
			photoName = tk.nextToken(File.separator); //tokenize based on the path
			
		}
		if(usr.getAlbum(albumName) != null && usr.getAlbum(albumName).getPhoto(photoName) != null) //album exists and photo exists
		{
			if(usr.getAlbum(toAlbumName) != null && (usr.getAlbum(toAlbumName).getPhoto(photoName)== null)) //dest album exists and doesnt contain photo
			{
				usr.getAlbum(albumName).copyPhoto(usr.getAlbum(albumName).getPhoto(photoName), usr.getAlbum(toAlbumName));
		
				return true;
			}
		}
		return false;
	}
	
	
			
	@Override
	public void listPhotos(String albumName) throws IOException
	{
		for(Album aa:usr.getAlbums())
		{ 
			if(aa.getAlbumName().equals(albumName))
			{
				usr.getAlbum(albumName).listPhotos();
				return;
			}		
		}
		//System.out.println("album "+ albumName+ " does not exist");
	}


	@Override
	public void listPhotoInfo(String fileName) throws ClassNotFoundException, IOException 
	{
		tk = new StringTokenizer(fileName);
		while(tk.hasMoreTokens())
		{
			fileName = tk.nextToken(File.separator); //tokenize based on the path
			
		}
		
		for(Album album:usr.getAlbums())
		{
			for(Photo photo:album.getPhotos())
			{
				/*
				Photo p=new Photo(fileName,null); //dummy
				fileName=p.getPhotoName();*/
				if(photo.getPhotoName().equals(fileName))
				{
					
					String albumN= album.getAlbumName();
					usr.getAlbum(albumN).listPhotoInfo(fileName,usr);
					return;
				}
			}
		}
			//System.out.println("Photo "+fileName+ " does not exist");
	}

	@Override
	public void listAlbums() 
	{
		if(usr.albumsSize()==0)
		{ 
			//System.out.println("No albums exist for user "+usr.getID()+ ":");
			return;
		}
		else
		{
			//System.out.println("Albums for user " + usr.getID()+":"); 
			usr.listAlbums();
			return;
		}
	}
	
	public boolean recaption(String fileName,String newCaption) throws IOException{
		for(Album a: usr.getAlbums()){
			for(Photo p:a.getPhotos()){
				if(p.getPhotoName().equals(fileName)){
					p.setCaption(newCaption);
					return true;
				}
			}
		}
		//System.out.println("Photo " +fileName+" for user "+usr.getID()+" does not exist");
		return false;
	}
	
	public boolean renameAlbum(String oldAlbumName ,String newAlbumName){
		

		for(Album a :usr.getAlbums())
		{
			if(a.getAlbumName().equalsIgnoreCase(newAlbumName))
			{
				//System.out.println("Album name exist" );
				return false;
			}
		}
		usr.getAlbum(oldAlbumName).setAlbumName(newAlbumName);  
		return true;
		
	}

}