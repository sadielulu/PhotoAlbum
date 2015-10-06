/*package cs213.photoAlbum.simpleview;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cs213.photoAlbum.control.Control;

/**
 * implementation of log in and log out , includes main
 * 
 * @author Oskar Bero
 *
 *
public class CmdView {
		
	
		public static void main(String args[]) throws ClassNotFoundException, IOException, ParseException
		{
			
			Control ctrl = new Control();
	
			
			if(args.length >= 1)
			{
				switch(args[0]){
					
					case "listusers":
					{
						ctrl.listUsers();
						break;
					}
					case "adduser":
					{
						if(args.length == 3){
							//don't need to print this here but just for testing
							ctrl.addUser(args[1],args[2]);				
						}
						else if(args.length > 3)
						{
							System.out.println("Too many arguments for this command");
						}
						else{
							
							System.out.println("Not enough arguments");
						}
						break;
					}
						
					case "deleteuser":
					{
						if(args.length > 1){ //if we have an actual id
								
							ctrl.deleteUser(args[1]);
						}
						else{
							
							System.out.println("Not enough arguments for this command");
						}
						break;
					}
					
					case "login":
					{
						if(args.length == 2){
							String userId = args[1]; //save the id 
							
							if(ctrl.logIn(userId))
							{
								System.out.println("Loging in as: " + userId);
								//enter the interactive mode of the view
								interactiveMode(ctrl);
							}			
						}
						else if(args.length > 2){
							System.out.println("Too many arguments for this command");
						}
						else
						{
							System.out.println("Not enough arguments for this command");
						}
						break;
					}
					
					default:
						System.out.println("Unrecognized command");
				}//end switch
					
					
			}//endif
		}

		public static void interactiveMode(Control ctrl) throws ClassNotFoundException, IOException, ParseException
		{
			Scanner kb = new Scanner(System.in);
			Boolean logout = false; //for loging out
			String input, cmd = null, arg1 = null, arg2 = null, arg3 = null; //initialize the possible 3 args
			
			
			
			List<String> commands = new ArrayList<String>();
			
			while(!logout)
			{
				commands.clear();
				//reset the argument strings
				cmd = null;
				arg1 = null;
				arg2 = null;
				arg3 = null;
				
				System.out.print("Enter command: "); //prompt user for a command
				input = kb.nextLine(); //store said command in input
				
				if(!(input.isEmpty()))
				{	
					//(\\S*\\:) = any string ending in ':'
					//(([^\"]\\S*|\".+?\")\\s*) = any string not starting with " or starting and ending with " followed by any amount of spaces
					Matcher m = Pattern.compile("(\\S*\\:|([^\"]\\S*|\".+?\")\\s*)").matcher(input); //horrific but works
					
					while (m.find())
					{
						commands.add(m.group(1).replace("\"", "").trim()); // Add .replace("\"", "") to remove surrounding quotes.
					}
					//assign arguments based on the List of commands parsed by matcher
					if(commands.size() > 0) //no arg command
					{
						cmd = commands.get(0);
						if(commands.size() > 1)
						{
							arg1 = commands.get(1);
							
							if(commands.size() > 2)
							{
								arg2 = commands.get(2);
							
								if(commands.size() > 3)
								{
									arg3 = commands.get(3);
								}
							}
						}
					}
					
				}
				else //check if input was empty anything in there
				{
					cmd = "Not a command";
				}
				switch(cmd)
				{
					//Works fine
					case "createAlbum": //"<name>"
					{
						System.out.println("Create album function");
						if(arg1 != null)
						{
							System.out.println("create album");
							ctrl.addAlbum(arg1);
							break;
						}
						else
							//not enough args error
							break;
						
					}
					case "deleteAlbum": //"<name>"
					{
						if(arg1 != null)
						{
							ctrl.deleteAlbum(arg1);
							break;
						}
						break;
					}
					case "listAlbums": //no args
					{
						ctrl.listAlbums();
						break;
					}
					case "listPhotos": //"<name>"
					{
						if(arg1 != null)
						{
							ctrl.listPhotos(arg1);							
							break;
						}
						break;
					}
					
					//DOESNT ADD A PHOTO
					case "addPhoto": //"<filename>" "<caption>" "<albumName>"
					{
						System.out.println("Add photo function"); //takes 1 arg
						if(arg1 != null)
						{
							if(arg2 != null)
							{
								if(arg3 != null)
								{
									ctrl.addPhoto(arg1, arg2, arg3);
									break;
								}
								break;
							}
							//error
							break;
						}
						//not enough args error
						break;
					}
					
					//CAUSES THE THROWN EXCEPTIONS FROM UP TOP - NEEDS CHANGE
					case "movePhoto": //"<filename>" "<oldAlbumName>" "<newAlbumName>"
					{
						System.out.println("Move photo function"); //takes 1 arg
						if(arg1 != null)
						{
							if(arg2 != null)
							{
								
								if(arg3 != null)
								{
									ctrl.movePhoto(arg1, arg2, arg3);
									break;
								}
								//error
								break;
							}
							//error
							break;
						}
						//not enough args error
						break;
					}
					//NO IDEA IF IT WORKS - due to add photo
					case "removePhoto": //"<filename>" "<albumName>" 
					{
						System.out.println("remove photo function"); //takes 1 arg
						if(arg1 != null)
						{
							if(arg2 != null)
							{
								ctrl.deletePhoto(arg1, arg2);
								break;
							}
							//error
							break;
						}
						//not enough args error
						break;
					}
					
					case "addTag": //"<filename>" <tagType>:"<tagValue>"
					{
						
						if(arg1 != null)
						{
							if(arg2 != null)
							{
								if(arg3 != null)
								{
									System.out.println("AddTag to photo function"); //takes 3 args
									ctrl.addTag(arg1, arg2, arg3);
									break;
								}
								//error
								break;
							}
							//error
							break;
						}
						//not enough args error
						break;
					}
					
					case "deleteTag": //"<filename>" <tagType>:"<tagValue>"
					{
						
						if(arg1 != null)
						{
							if(arg2 != null)
							{
								if(arg3 != null)
								{
									System.out.println("deleteTag to photo function"); //takes 3 args
									ctrl.deleteTag(arg1, arg2, arg3);
									break;
								}
								//error
								break;
							}
							//error
							break;
						}
						//not enough args error
						break;
					}
					
					case "listPhotoInfo": //"<fileName>"
					{
						System.out.println("listPhotoInfo function"); //takes 1 arg
						if(arg1 != null)
						{
							ctrl.listPhotoInfo(arg1);
							break;
						}
						else
							//not enough args error
							break;
					}
					
					case "getPhotosByDate": //"<startDate>" "<endDate>" 
					{
											
						if(arg1 != null)
						{
							if(arg2 != null)
							{
								
								StringTokenizer tk = new StringTokenizer(input);
		                        tk.nextToken(); //consume command 
		                        String startDate = tk.nextToken();
		                        String endDate = tk.nextToken();
		                        System.out.println("StartDate:" + startDate + "\nEndDate:" + endDate); 
								ctrl.getPhotosByDate(startDate, endDate);
								break;
							}
							//error
							break;
						}
						//not enough args error
						break;
					}
					case "getPhotosByTag": //[tagType:]"tagValue"
					{
						commands.remove(0); //the first one is "getPhotosByTag"
						if(commands.isEmpty())
						{
							System.out.println("No tags specified");
						}
						else
						{
							ctrl.getPhotosByTag(commands); //pass the rest of the tokens down to getPh
						}
						break;
					}
					case "logout":
					{
						logout = ctrl.logOut();
						break;
						
					}		
					default:
						System.out.println("Invalid command");
						break;
				}
				
			}
			commands.clear();
			kb.close();
			
		}
}
*/
