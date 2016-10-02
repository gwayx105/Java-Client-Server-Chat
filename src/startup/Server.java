package startup;
//File Name GreetingServer.java
import java.net.*;
import java.util.ArrayList;
import java.io.*;
//Server extended to the thred 
public class Server extends Thread {
private ServerSocket serverSocket;
//Server constructor takes port number as parameter
public Server(int port) throws IOException {
   serverSocket = new ServerSocket(port);
   serverSocket.setSoTimeout(0);
}
public enum MenuOptions {
    CONNECT,LIST,TERMINATE,SEND,EXIT 
}
//ArrayList<String> connectionList = new ArrayList<String>();
ArrayList<Socket> socketList = new ArrayList<Socket>();
public void run() {
	String[] commands;
	//int id=1;
	//Server runs forever listening on assigned port
   while(true) {
      try {         
         Socket server = serverSocket.accept();
         //serverSocket.bind(server.getRemoteSocketAddress());
         socketList.add(server);
         DataInputStream in = new DataInputStream(server.getInputStream());
         DataOutputStream out = new DataOutputStream(server.getOutputStream());
         String cmd = in.readUTF();
         commands=cmd.split(" ");         
         MenuOptions firstCmd = MenuOptions.valueOf(commands[0].toUpperCase());			
			switch(firstCmd){
			case CONNECT:
				out = new DataOutputStream(server.getOutputStream());
				out.writeUTF("Server: Connection Succesful!\nTotal Connections : "+socketList.size());
				break;
			case LIST:
				System.out.println("Listed...Server");
				out.writeUTF("Listing");
				break;
			case TERMINATE:
				//String[] ccc;
				socketList.get(Integer.parseInt(commands[1])).close();;
				out.writeUTF("Terminated");
				socketList.remove(Integer.parseInt(commands[1]));
				break;
			case SEND:
				DataOutputStream outSend =new DataOutputStream(socketList.get(Integer.parseInt(commands[1])).getOutputStream());
				outSend.writeUTF(commands[2]);
				break;
			default:
				
				break;
			}
      }catch(SocketTimeoutException s) {
         System.out.println("Socket timed out!");
         
         break;
      }catch(IOException e) {
         e.printStackTrace();
      
         break;
      }finally{
    	  
    }
   }
}


}
