package cs213.photoAlbum.model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.TimeZone;

/**
 * This class is the implementation of the photo interface
 * @author Silvia Carbajal
 *
 */

public class Photo implements Serializable,Comparable<Photo>{

	private static final long serialVersionUID = 351575227508756640L;
	private ArrayList<String[]> tags;
	private String tagDescriptions[];
	private String photoName,caption, photoPath;
	private Calendar date;
	private SimpleDateFormat format;
	File file;
	String s;
	/**
	 * constructor of the photo class
	 * @param photoName, name of the photo we create
	 * @param caption, caption of the photo
	 * @throws IOException 
	 */
	public Photo(String pathName, String photoName, String caption) throws IOException{
		tags = new ArrayList<String[]>();
		this.caption=caption;
		this.photoName = photoName;
 		file = new File(pathName);
 		tagDescriptions = new String[2];
		String actualName;
	
		if(file.exists()){
 			this.photoPath=file.getCanonicalPath();
 			date = Calendar.getInstance(); //returns default time zone		
 			date.setLenient(false);
 			date.setTimeInMillis(file.lastModified()); //sets the current time that file was last modified 
 			date.set(Calendar.MILLISECOND, 0); 
 			format = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss"); 
 			
 			try {//should throw error if invalid date
 				Date d= date.getTime();
 	 			s=format.format(d);
 			}
 			catch (Exception e) {
 			
 			}
 			
		}
		else{
		date =Calendar.getInstance();
		TimeZone tz = TimeZone.getTimeZone("PST");
		date.setTimeZone(tz);
		format = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss"); 
	     s=format.format(date.getTime());
		
		
		}
		
	}
	/**
	 * Get caption
	 * @return caption for a photo
	 */
	public String getCaption() {
		return caption;
	}
	/**
	 * Sets caption for a photo
	 * @param c - the caption to set
	 * @return the caption we just set?
	 */
	public String setCaption(String c) {
		caption=c;
		return caption;
	}
	/**
	 * Get the date of the photo's last modification
	 * @return the string containing date and time information 
	 */
	public String getDate(){
		return s;

	}
	/**
	 *  just like getDatre just in a calendar object not string
	 * @return a calanedar object with both date and time info
	 */
	public Calendar getDateAndTime(){
		return date;
	}
	
	/**
	 * sets a name to a photo 
	 * @param fileName, name of the photo we are setting
	 */
	public void setPhotoName(String fileName){
		photoName=fileName;
	}
	
	/**
	 * gets photo name 
	 * @return photo Name
	 * @throws IOException 
	 */
	public String getPhotoName(){
	
		return photoName; 
	}
	
	public String getPhotoPath()
	{
		return photoPath;
	}
	/**
	 * Add a tag to the ArrayList<Tags>
	 * @param type - tag type
	 * @param value - tag value
	 * @return String array of size 2 with type and value at index 0 and 1 
	 */
	public String[] addTag(String type, String value)
	{
		tagDescriptions = new String[2];
		tagDescriptions[0] = type;
		tagDescriptions[1] = value;	
		tags.add(tagDescriptions);
		return tagDescriptions;
	}

	/**
	 * Deletes tag with given information from the photo
	 * @param type - tag type 
	 * @param value - tag value
	 */
	public void deleteTag(String type, String value) 
	{
		for(int i = 0; i < tags.size(); i++)
		{
			if(tags.get(i)[0].equals(type) && tags.get(i)[1].equals(value))
			{
				tags.remove(i);
			}
		}
	}


	/**
	 * Check if a given tag value belongs to the photo
	 * @param value - tag's value to check for
	 * @return boolean true if the photo contains a tag with such value , false otherwise
	 */
	public boolean checkTagValue(String value) { //check a photo for containing a tag valy
		
		if(value.contains(":"))
		{
			StringTokenizer tk = new StringTokenizer(value);
			String type = tk.nextToken(":");
			value = tk.nextToken();
		
			for(int i = 0; i < tags.size(); i++)
			{
				if(tags.get(i)[0].equals(type) && tags.get(i)[1].equals(value)) //if the first subscript of the ith element of tags equals the value
				{
					
					return true;
				}
			}
		}
		else
		{
			for(int i = 0; i < tags.size(); i++)
			{
				if(tags.get(i)[1].equals(value)) //if the first subscript of the ith element of tags equals the value
				{
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * Gets the array list of tags from a user
	 * @return ArrayList<Tag> 
	 */
	public ArrayList<String[]> getTags() 
	{	
			return tags;
	}
	

	@Override
	public int compareTo(Photo o) {

			return photoName.compareTo(o.getPhotoName());

	}
	/**
	 *	 
	 * @param otherPhoto
	 * @return true if equals, false otherwise
	 * @throws IOException 
	 */
	public boolean equals(Photo otherPhoto) throws IOException
	{
		return photoName.equals(otherPhoto.getPhotoName());
	}

	
}// end of class







