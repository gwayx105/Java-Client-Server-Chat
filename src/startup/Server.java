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
   serverSocket.setSoTimeout(2000);
}
public enum MenuOptions {
    CONNECT,LIST,TERMINATE,SEND,EXIT 
}
ArrayList<String> connectionList = new ArrayList<String>();
public void run() {
	String[] commands;
	int id=1;
	//Server runs forever listening on assigned port
   while(true) {
      try {
         System.out.println("Waiting for client on port " + 
            serverSocket.getLocalPort() + "...");
         Socket server = serverSocket.accept();
         
         System.out.println("Just connected to " + server.getRemoteSocketAddress().toString().replace("\\", ""));
         DataInputStream in = new DataInputStream(server.getInputStream());
         String cmd = in.readUTF();
         commands=cmd.split(" ");
         MenuOptions firstCmd = MenuOptions.valueOf(commands[0].toUpperCase());			
			switch(firstCmd){
			case CONNECT:
				Socket ss = new Socket(commands[1],Integer.parseInt(commands[2]));
				serverSocket.bind(ss.getRemoteSocketAddress());
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
		        out.writeUTF("Connection successful!");
		        String conn = id+" "+commands[1]+" "+commands[2];
		        connectionList.add(conn);
		        id++;
				break;
			case LIST:
				System.out.println("ID :\tIP ADDRESS\tPort No.\n------------------------------");
				String[] cc;
				for(String ll:connectionList){
					cc=ll.split(" ");
					System.out.println(cc[0]+"\t"+cc[1]+"\t"+cc[2]);
				}
				break;
			case TERMINATE:
				String[] ccc;
				for(String ll:connectionList){
					ccc=ll.split(" ");
					if(ccc[0].equalsIgnoreCase(commands[1])){
						connectionList.remove(ll);
						if(serverSocket.getLocalSocketAddress().toString().replace("\\", "").equals(ccc[1])){
							
						}
					}
				}
				break;
			case SEND:
				
				break;
			default:
				
				break;
			}
         System.out.println(in.readUTF());
         
         DataOutputStream out = new DataOutputStream(server.getOutputStream());
         out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress()
            + "\nGoodbye!");
         
         
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
