package startup;
//File Name GreetingServer.java
import java.net.*;
import java.io.*;
//Server extended to the thred 
public class Server extends Thread {
private ServerSocket serverSocket;
//Server constructor takes port number as parameter
public Server(int port) throws IOException {
   serverSocket = new ServerSocket(port);
   serverSocket.setSoTimeout(2000);
}

public void run() {
   while(true) {
      try {
    	  
         System.out.println("Waiting for client on port " + 
            serverSocket.getLocalPort() + "...");
         Socket server = serverSocket.accept();
         
         System.out.println("Just connected to " + server.getRemoteSocketAddress().toString().replace("\\", ""));
         DataInputStream in = new DataInputStream(server.getInputStream());
         
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
