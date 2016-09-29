package startup;
import java.net.*;
import java.io.*;
import java.util.Scanner;
//Authors    : Sylvester Zowonu   			 
//			 :   
//School    : California State University Los Angeles
//Course    : Networking 
//Reference : http://www.tutorialspoint.com/java/java_networking.htm
public class Start {
	// Server Port Number
	public static int ServerPort = 55056;
	//Server IP address
	public static String ServerName = "192.168.1.112";
	//Menu options enumeration
	public enum MenuOptions {
	    HELP, MYIP,MYPORT,CONNECT,LIST,TERMINATE,SEND,EXIT 
	}
	//Function to display program options menu
	public static void displayHelp(){
		System.out.println("\n\tPROGRAM MENU\n-----------------------------------");
		System.out.println("help       -> Displays Program Menu Options");
		System.out.println("myip       -> Displays your IP Address");
		System.out.println("myport     -> Displays your Port number");
		System.out.println("connect    -> Connects you to specified IP address <IP Address> <Port>");
		System.out.println("list 	   -> Displays all ip addresses and ports connected to ");
		System.out.println("terminate  -> Terminates connection <terminate> <id>");
		System.out.println("send   	   -> Sends message <connection id> <message>");
		System.out.println("....................................................");
	}
	
	
	public static void main(String args[]) throws UnknownHostException, IOException {
		try {
		      Thread t = new Server(ServerPort);
		      t.start();
		}catch(IOException e) {
		      e.printStackTrace();
		}
		String cmd="";
		String[] commands;
		Socket client = new Socket(ServerName, ServerPort);
		@SuppressWarnings("resource")
		Scanner scanInput = new Scanner(System.in);
		do {
			System.out.print("Enter Command:");
			cmd= scanInput.nextLine();
			//splits user response into an array 
			commands=cmd.split(" ");
			MenuOptions currentDay = MenuOptions.valueOf(commands[0].toUpperCase());			
			switch(currentDay){
			case HELP:
				//displays program menu options to the user
				displayHelp();
				break;
			case MYIP:				
				System.out.println("Your IP ADDRESS: " + Inet4Address.getLocalHost().getHostAddress().toString().replace("/", ""));			
				break;
			case MYPORT:
				System.out.println("Your PORT NUMBER: "+ client.getLocalPort());
				break;
			case CONNECT:
				System.out.println("Connecting...");
				System.out.println("Connecting to " + ServerName + " on port " + ServerPort);
		        
		        System.out.println("Just connected to " + client.getRemoteSocketAddress());
		        OutputStream outToServer = client.getOutputStream();
		        DataOutputStream out = new DataOutputStream(outToServer);
		        //Sending to client
		        out.writeUTF("Hello from " + client.getLocalSocketAddress());
		        InputStream inFromServer = client.getInputStream();
		        DataInputStream in = new DataInputStream(inFromServer);
		         
		        System.out.println("Server says " + in.readUTF());
		         client.close();
				break;
			case SEND:
				break;
			case LIST:
				System.out.println("List...");
				break;
			case TERMINATE:
				System.out.println("Terminating...");
				break;
			case EXIT:
				client.close();
				System.out.println("Closing connections...");
				break;
			default:
				break;
			}
		} while (!cmd.equalsIgnoreCase("EXIT"));
		
		
	}

}
