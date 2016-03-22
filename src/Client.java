import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.File;


public class Client {
	Socket client_socket;
	
	InetAddress loopback;
	PrintWriter output;
	BufferedReader input;
	File input_file;
	File output_file;
	String option;
	int port_number;
	
	// try to connect to the server
	public Client(int port) throws IOException{
		port_number = port;
		loopback = InetAddress.getLocalHost();
		String host = loopback.getHostName();
		client_socket = new Socket(host, port_number);
		
		output = new PrintWriter(client_socket.getOutputStream(), true);
		input = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));
	}
	
	
	
	
	
	
	public void transmit(String message) throws IOException {
		
		output.println(message);
		message = input.readLine();
		System.out.println(message);
		
		
	}
	
	
}
