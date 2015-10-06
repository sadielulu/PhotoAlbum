package cs213.photoAlbum.model;


import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;


/**
 * 
 * @author Silvia Carbajal
 *
 */

public class Album implements Serializable,Comparable<Album>{

	
	private static final long serialVersionUID = 351575227508756640L;
	
	private String albumName;
	private ArrayList<Photo> photos;
	
	/**
	 * constructor of the album
	 * @param albumName
	 */
	public Album(String albumName){
		photos = new ArrayList<Photo>();
		this.albumName=albumName;
	}

	/**
	 * 
	 * @param fileName
	 * @param caption
	 * @throws IOException 
	 */
	public void addPhoto(String path, String fileName, String caption) throws IOException {
		
		Photo photo=new Photo(path, fileName, caption); 		
		photos.add(photo);

	}

	/**
	 * 
	 * @param fileName
	 * @throws IOException 
	 */
	public void deletePhoto(String fileName) throws IOException {
		for(int i = 0; i < photos.size();i++){
			if(photos.get(i).getPhotoName().equals(fileName)){
				photos.remove(photos.get(i));
			}
		}		
	}

	/**
	 * Takes a photo object and removes it from current album then adds it to destination album
	 * @param Photo p , what were trying to move
	 * @param Album destination , the album to which we wnat to move the photo p
	 */
	public void movePhoto(Photo p, Album destination)
	{
		if(destination.getPhotos().contains(p))
		{
			return; //return silently and not do anything
		}
		else
		{
			this.photos.remove(p); //remove from current album	
			destination.photos.add(p); //add to the new album destination
			Collections.sort(destination.photos); //sort 
		
		}
	}
	
	public void copyPhoto(Photo p, Album destination)
	{
		if(destination.getPhotos().contains(p))
		{
			return; //return silently and not do anything
		}
		else
		{
			destination.photos.add(p); //add to the new album destination
			Collections.sort(destination.photos); //sort 
		
		}
	}
	/**
	 * Prints all the photos that have been added to the program
	 * @throws IOException
	 */
	public void listPhotos() throws IOException { 
		String n,date;
		System.out.println("Photos for album "+ getAlbumName()+":");  
		for(int x=0;x<photos.size();x++){ 
			 n=photos.get(x).getPhotoName();
			 date = photos.get(x).getDate();
			String c = n+"-"+ date;
			System.out.println(c);
		}
	}

	/**
	 * get the list(object)  of photos
	 * @return ArrayList<Photo> of photos 
	 */
	public ArrayList<Photo> getPhotos() {
		return photos;
	}

	/**
	 * Displays all the photo's info: Path, Album, Date, Caption , and Tags
	 * @param fileName - String with photo path
	 * @throws IOException
	 */
	public void listPhotoInfo(String fileName,User u) throws IOException { 
		for(Photo photo:photos){
			if(photo.getPhotoName().equals(fileName)){
				System.out.println("Photo file name: "+ fileName);
				System.out.print("Album:");
				for(Album a:u.getAlbums()){
					for(Photo p:a.getPhotos()){
						if(p.getPhotoName().equals(fileName)){
							System.out.print(a.getAlbumName() +","); //problems if a photo cna be in more albums
				}
			}
				}
				
				
				System.out.println();
				System.out.println("Date: "+photo.getDate() );
				System.out.println("Caption: "+photo.getCaption()  );
				
				System.out.println("Tags:");
				for(String[]t:photo.getTags())
				{
					if(t[0] != null)
					{
						System.out.println(t[0]+ t[1]);
					}
					else
					{
						System.out.println(t[1]);
					}
				}
				
		}	
		}	
		}
	


	/**
	 * sets the name of the album
	 * @param name, name of the album
	 **/
	public void setAlbumName(String name) {
		albumName=name;
	}
	
	/**
	 * gets the name of the album
	 * @return name of the album
	 */
	public String getAlbumName() {
		return albumName;
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException 
	 */
	public Photo getPhoto(String fileName) throws IOException{
		for(Photo p:photos){
			//Photo pp=new Photo(fileName,null); //dummy
			//fileName=pp.getPhotoName();
			if(p.getPhotoName().equals(fileName)){
				return p;
			} 
		}
		return null;  
	}
	
	public String photoStartEndDate(){
		
		Calendar startDate = null,endDate = null;
		SimpleDateFormat format= new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");
	
	
		for (Photo p: photos) {
			Calendar date = p.getDateAndTime();
	 		if (startDate == null || date.compareTo(startDate) < 0) {
				startDate = date;
			}
			
			if (endDate == null || date.compareTo(endDate) > 0) {
				endDate = date;
			}
		}  
		
		String output = albumName + " number of photos: " + photos.size();
		if (startDate != null) {
			output += ", " + format.format(startDate.getTime()) + " - " + format.format(endDate.getTime());
		}
		return output;

	}
	/**
	 *  
	 * @param otherAlbum
	 * @return 
	 */
	public boolean equals(Album otherAlbum)
	 {
		//change it later when everything is working, instead of the one i use
		return albumName.equals(otherAlbum.getAlbumName());
	 }

	@Override
	public int compareTo(Album o) {
		
			return albumName.compareTo(o.albumName);
		}	

	/**
	 * Get the # of photos in ArrayList<Photo> photos
	 * @return # of elements in array list
	 */
	public int photoSize()
	{
		if(!(photos.isEmpty()))
		{
			return photos.size();
		}
		else
			return 0;
	}
	
}
