package startup;
//Authors    : Sylvester Zowonu
//			 :
//School    : California State University Los Angeles
//Course    : Networking
//Reference : http://www.tutorialspoint.com/java/java_networking.htm
import java.net.*;
import java.io.*;

public class Client {
private String ServerName;
private int ServerPort;
public static void main(String [] args) {
   String serverName = args[0];
   int port = Integer.parseInt(args[1]);
   try {
      System.out.println("Connecting to " + serverName + " on port " + port);
      Socket client = new Socket(serverName, port);
      
      System.out.println("Just connected to " + client.getRemoteSocketAddress());
      //Message to server
      OutputStream outToServer = client.getOutputStream();
      DataOutputStream out = new DataOutputStream(outToServer);
      
      out.writeUTF("Hello from " + client.getLocalSocketAddress());
      //Message from server
      InputStream inFromServer = client.getInputStream();
      DataInputStream in = new DataInputStream(inFromServer);
      
      System.out.println("Server says " + in.readUTF());
      client.close();
   }catch(IOException e) {
      e.printStackTrace();
   }
}
public Client(String server,int port){
	ServerName=server;
	ServerPort=port;
}
}