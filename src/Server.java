import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TimerTask;

public class Server {
	ServerSocket server_socket;
	Socket client_socket;
	int port;
	String option;
	File fileGiven;
	
	
	

	
			
	
	
	public Server(int port) throws IOException 
	{
		this.port = port;	
		server_socket= new ServerSocket(port);
		System.out.println( "Listening on port " + Integer.toString( port ) );
	
	}
	
	public void playGame(String fileIn, String gameNum) throws IOException
	{
		
		
		int id =1;
		while(true)
		{
			
			ClientWorker w;
			try
			{
				w = new ClientWorker(server_socket.accept(), fileIn, gameNum, id);
				Thread t = new Thread(w);
				t.start();
				id++;
				
				
			}
			catch(IOException e)
			{
				System.out.println("Accept failed: 4444");
				System.exit(-1);
			}
			
			
		}
		

	}
}

	
	
	
	
	
	
	