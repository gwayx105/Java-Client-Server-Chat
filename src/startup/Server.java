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
         socketList.add(server);
         DataInputStream in = new DataInputStream(server.getInputStream());
         String cmd = in.readUTF();
         commands=cmd.split(" ");         
         MenuOptions firstCmd = MenuOptions.valueOf(commands[0].toUpperCase());			
			switch(firstCmd){
			case CONNECT:
				DataOutputStream outC = new DataOutputStream(server.getOutputStream());
				outC.writeUTF("Server: Connection Succesful!\nTotal Connections : "+socketList.size());
				break;
			case LIST:		
				System.out.println("building");
				DataOutputStream outL = new DataOutputStream(server.getOutputStream());
				//System.out.println("building");
				StringBuilder sb=new StringBuilder();
				sb.append("ID :\tIP ADDRESS\tPort No.\n------------------------------\n");
				System.out.println("building");
				for(int i =0;i<socketList.size();i++){
					String[] cc=socketList.get(i).getRemoteSocketAddress().toString().replace("/", "").split(":");
					String s=i+"\t"+cc[0]+"\t"+cc[1]+"\n";
					sb.append(s);					
				}
				outL.writeUTF(sb.toString());
				break;
			case TERMINATE:
				//String[] ccc;
				socketList.get(Integer.parseInt(commands[1])).close();;
				break;
			case SEND:
				
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
