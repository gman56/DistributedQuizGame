import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class QuizGame {
	
	public static void main(String[] args) throws IOException {
		
		int port_number = Integer.parseInt(args[0]);
		String gameToPlay;

		
		if(args.length == 2 && args[1].equalsIgnoreCase("-l") ){
			
			Server server = new Server(port_number);
			BufferedReader reader= new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Choose Game");
			String fileName = "GameList.txt";
			FileReader inputFile = new FileReader(fileName);
			BufferedReader fileReader= new BufferedReader(inputFile);
			String message ="";
			while ((message = fileReader.readLine()) != null) {
				System.out.println(message);
			}
			
			gameToPlay = reader.readLine();
			System.out.println("Waiting for Client ");
			
			if (gameToPlay.equals("Game1"))
			{
				server.playGame(gameToPlay+".txt", "game1");
				
			}
			else if (gameToPlay.equals("Game2"))
			{
				server.playGame(gameToPlay+".txt", "game2");
			}
			else if (gameToPlay.equals("Game3"))
			{
				server.playGame(gameToPlay+".txt", "game3");
			}
			
		}
	
		
	else {
		
		try
		{
		Client client = new Client(port_number);
		BufferedReader reader= new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Connected ");
		String msg = " ";
		
		System.out.println("Type & send any character if ready for questions: ");
		System.out.println("If question is not answered in 10 seconds, it is counted wrong");
		while ((msg = reader.readLine()) != null) 
		{


			if(msg.isEmpty())
			{
				break;
			}
		
		
			
			
		
			System.out.println("Sending: " + msg);
			client.transmit(msg);
			System.out.print("Enter your message: ");

		}
		}
		catch (Exception e)
		{
			System.out.println("Error: " + e.getMessage());
		}
		
	}

	

	}

}




