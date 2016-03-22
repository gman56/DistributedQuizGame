import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.Socket;


public class ClientWorker implements Runnable {
	
	String fileIn;
	String gameNum;
	
	int id;
	
	
	
	private Socket client_socket;
	
	
	public ClientWorker(Socket client, String fileIn, String gameNum, int id)
	{
		client_socket = client;	
		this.fileIn = fileIn;
		this.gameNum = gameNum;
		this.id =id;
	}
	
	public void run()
	{
	
		

		
		
		
		
		String s;
		int answersRight = 0;
		
		long startTime=0;
		long endTime= 0;
		
		
		String line;
		String whole_file = "";
		String input;
		
		
		BufferedReader reader = null;
		PrintWriter output = null;

		try {
			reader = new BufferedReader(new InputStreamReader(client_socket.getInputStream()));		
			output = new PrintWriter(client_socket.getOutputStream(), true);
		} catch (IOException e5) {
			// TODO Auto-generated catch block
			e5.printStackTrace();
		}
		File file = new File(fileIn);
		BufferedReader read_file = null;
		try {
			read_file = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e4) {
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}
		
		File file2 = new File("Answers.txt");
		BufferedReader ansFile = null;
		try {
			ansFile = new BufferedReader(new FileReader(file2));
		} catch (FileNotFoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		File file3 = new File("HighScore.txt");
		BufferedReader scoreFile = null;
		try {
			scoreFile = new BufferedReader(new FileReader(file3));
		} catch (FileNotFoundException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		String source = null;
		try {
			source = scoreFile.readLine();
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		int highScore = Integer.parseInt(source.trim());
		try {
			scoreFile.close();
		} catch (IOException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
	

		String str1 = "";
		String str2 = "";
		String str3 = "";
		String str4 = "";
		String str5 = "";
		
		String[] ans = new String[15];
		int z =0;
		
		try {
			while ((line = ansFile.readLine()) != null && z <= 4) 
			{	
				if(line.contains("Game1"))
				{
					continue;
				}
				ans[z] = line;
				z++;
				
			}
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			while ((line = ansFile.readLine()) != null && z <= 9) 
			{	
				if(line.contains("Game2"))
				{
					continue;
				}
				ans[z] = line;
				z++;
				
			}
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			while ((line = ansFile.readLine()) != null && z<= 14) 
			{	
				if(line.contains("Game3"))
				{
					continue;
				}
				ans[z] = line;
				z++;
				
			}
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		
		
		try {
			while ((line = read_file.readLine()) != null) 
			{	
				whole_file += line + " ";
				int countLine =5;
				
				

				
				if(line.contains("!") && countLine > 0)
				{
					while ((line = read_file.readLine()) != null && countLine > 0) 
					{	
						str1 += line + " ";
						countLine--;
					}
				}
				
				else if(line.contains("$") && countLine > 0)
				{
					while ((line = read_file.readLine()) != null && countLine > 0) 
					{	
						str2 += line + " ";
						countLine--;
					}
				}
				else if(line.contains("^") && countLine > 0)
				{
					while ((line = read_file.readLine()) != null && countLine > 0) 
					{	
						str3 += line + " ";
						countLine--;
					}
				}
				else if(line.contains("*") && countLine > 0 )
				{
					while ((line = read_file.readLine()) != null && countLine > 0) 
					{	
						str4 += line + " ";
						countLine--;
					}
				}
				else if(line.contains("@") && countLine > 0)
				{
					while ((line = read_file.readLine()) != null && countLine > 0) 
					{	
						str5 += line + " ";
						countLine--;
					}
				}
				
				
				
				
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		//Garrett wrote from this point on
		int count =0;
		try {
			while ((s= reader.readLine()) != null) 
			{
				System.out.println("Client " + id +  " sent: " + s);
				

				//Questions, sent when user types r
				if(count == 0 && s.matches(".+"))
				{
					
					output.println(str1);
					startTime = System.currentTimeMillis();
					count++;		
					continue;
				}
				if(count == 2 && s.matches(".+"))
				{
					
					output.println(str2);
					startTime = System.currentTimeMillis();
					count++;
					continue;
				}
				if(count == 4 && s.matches(".+"))
				{
					output.println(str3);
					startTime = System.currentTimeMillis();
					count++;
					continue;

				}
				if(count == 6 && s.matches(".+"))
				{
					output.println(str4);
					startTime = System.currentTimeMillis();
					count++;
					continue;


				}
				if(count == 8 && s.matches(".+"))
				{
					output.println(str5);
					startTime = System.currentTimeMillis();
					count++;
					continue;


				}

				// game 1 answers		
				else if((s.equalsIgnoreCase(ans[0]) && gameNum.toLowerCase().contains("game1")) && count == 1)  
				{	
					endTime = System.currentTimeMillis();

					
					if(((endTime-startTime)/1000) >= 10)
					{
						output.println("Correct but time elapsed, therefore incorrect. Enter any character for next question");
						count++;
					}
					else
					{				
						output.println("Correct, Enter any character for next question");
						answersRight++;
						count++;
					}
				}
				else if((s.equalsIgnoreCase(ans[1]) && gameNum.toLowerCase().contains("game1")) && count == 3)  
				{	
					endTime = System.currentTimeMillis();

					
					if(((endTime-startTime)/1000) >= 10)
					{
						output.println("Correct but time elapsed, therefore incorrect. Enter any character for next question");
						count++;
					}
					else
					{				
						output.println("Correct, Enter any character for next question");
						answersRight++;
						count++;
					}
				}
				else if((s.equalsIgnoreCase(ans[2]) && gameNum.toLowerCase().contains("game1")) && count == 5)  
				{	
					endTime = System.currentTimeMillis();

					
					if(((endTime-startTime)/1000) >= 10)
					{
						output.println("Correct but time elapsed, therefore incorrect. Enter any character for next question");
						count++;
					}
					else
					{				
						output.println("Correct, Enter any character for next question");
						answersRight++;
						count++;
					}
				}
				else if((s.equalsIgnoreCase(ans[3]) && gameNum.toLowerCase().contains("game1")) && count == 7)  
				{	
					endTime = System.currentTimeMillis();

					
					if(((endTime-startTime)/1000) >= 10)
					{
						output.println("Correct but time elapsed, therefore incorrect. Enter any character for next question");
						count++;
					}
					else
					{				
						output.println("Correct, Enter any character for next question");
						answersRight++;
						count++;
					}
				}
				else if((s.equalsIgnoreCase(ans[4]) && gameNum.toLowerCase().contains("game1")) && count == 9)  
				{	
					endTime = System.currentTimeMillis();

					
					if(((endTime-startTime)/1000) >= 10)
					{
						output.println("Correct but time elapsed, therefore incorrect. Enter any character for results");
						count++;
					}
					else
					{				
						output.println("Correct, Enter any character for results");
						answersRight++;
						count++;
					}
				}
				// end game 1 answers
				
				
				
//			compare answers for game 2
				else if(s.equalsIgnoreCase(ans[5]) && gameNum.toLowerCase().contains("game2") && count == 1)  
				{	
					endTime = System.currentTimeMillis();

					
					if(((endTime-startTime)/1000) >= 10)
					{
						output.println("Correct but time elapsed, therefore incorrect. Enter any character for next question");
						count++;
					}
					else
					{				
						output.println("Correct, Enter any character for next question");
						answersRight++;
						count++;
					}
				}
				else if (s.equalsIgnoreCase(ans[6]) && gameNum.toLowerCase().contains("game2") && count == 3){
					
					endTime = System.currentTimeMillis();

					
					if(((endTime-startTime)/1000) >= 10)
					{
						output.println("Correct but time elapsed, therefore incorrect. Enter any character for next question");
						count++;
					}
					else
					{				
						output.println("Correct, Enter any character for next question");
						answersRight++;
						count++;
					}
				}
				else if(s.equalsIgnoreCase(ans[7]) && gameNum.toLowerCase().contains("game2") && count == 5){
					
					endTime = System.currentTimeMillis();

					
					if(((endTime-startTime)/1000) >= 10)
					{
						output.println("Correct but time elapsed, therefore incorrect. Enter any character for next question");
						count++;
					}
					else
					{				
						output.println("Correct, Enter any character for next question");
						answersRight++;
						count++;
					}
				}
				else if (s.equalsIgnoreCase(ans[8]) && gameNum.toLowerCase().contains("game2") && count == 7){
					
					endTime = System.currentTimeMillis();

					
					if(((endTime-startTime)/1000) >= 10)
					{
						output.println("Correct but time elapsed, therefore incorrect. Enter any character for next question");
						count++;
					}
					else
					{				
						output.println("Correct, Enter any character for next question");
						answersRight++;
						count++;
					}
				}
				else if (s.equalsIgnoreCase(ans[9]) && gameNum.toLowerCase().contains("game2") && count == 9){
					
					endTime = System.currentTimeMillis();

					
					if(((endTime-startTime)/1000) >= 10)
					{
						output.println("Correct but time elapsed, therefore incorrect. Enter any character for results");
						count++;
					}
					else
					{				
						output.println("Correct, Enter any character for results");
						answersRight++;
						count++;
					}
				}
				
//			end of game2 answers
				
				else if(s.equalsIgnoreCase(ans[10]) && gameNum.toLowerCase().contains("game3") && count == 1)  
				{	
					endTime = System.currentTimeMillis();

					
					if(((endTime-startTime)/1000) >= 10)
					{
						output.println("Correct but time elapsed, therefore incorrect. Enter any character for next question");
						count++;
					}
					else
					{				
						output.println("Correct, Enter any character for next question");
						answersRight++;
						count++;
					}
				}
				else if (s.equalsIgnoreCase(ans[11]) && gameNum.toLowerCase().contains("game3") && count == 3){
					
					endTime = System.currentTimeMillis();

					
					if(((endTime-startTime)/1000) >= 10)
					{
						output.println("Correct but time elapsed, therefore incorrect. Enter any character for next question");
						count++;
					}
					else
					{				
						output.println("Correct, Enter any character for next question");
						answersRight++;
						count++;
					}
				}
				else if(s.equalsIgnoreCase(ans[12]) && gameNum.toLowerCase().contains("game3") && count == 5){
					
					endTime = System.currentTimeMillis();

					
					if(((endTime-startTime)/1000) >= 10)
					{
						output.println("Correct but time elapsed, therefore incorrect. Enter any character for next question");
						count++;
					}
					else
					{				
						output.println("Correct, Enter any character for next question");
						answersRight++;
						count++;
					}
				}
				else if (s.equalsIgnoreCase(ans[13]) && gameNum.toLowerCase().contains("game3") && count == 7){
					
					endTime = System.currentTimeMillis();

					
					if(((endTime-startTime)/1000) >= 10)
					{
						output.println("Correct but time elapsed, therefore incorrect. Enter any character for next question");
						count++;
					}
					else
					{				
						output.println("Correct, Enter any character for next question");
						answersRight++;
						count++;
					}
				}
				else if (s.equalsIgnoreCase(ans[14]) && gameNum.toLowerCase().contains("game3") && count == 9){
					
					endTime = System.currentTimeMillis();

					
					if(((endTime-startTime)/1000) >= 10)
					{
						output.println("Correct but time elapsed, therefore incorrect. Enter any character for results");
						count++;
					}
					else
					{				
						output.println("Correct, Enter any character for results");
						answersRight++;
						count++;
					}
				}
			
			
				else if(count == 10)
				{
					if(answersRight > highScore)
					{
						output.println("NEW HIGH SCORE: " + answersRight + " out of 5" + " - Connection is now closed ");
						File myFoo = new File("HighScore.txt");
						FileWriter fooWriter = new FileWriter(myFoo, false); 
						String x = String.valueOf(answersRight);
						fooWriter.write(x);
						fooWriter.close();
						
					}
					else if(answersRight < highScore)
					{
						output.println("Answers right: " + answersRight + " out of 5" + " - Connection is now closed ");
					}

				}			
				else
				{
					if(count==9)
					{
						output.println("Incorrect, Enter any character for results");
						count++;
					}
					else
					{		
						output.println("Incorrect, Enter any character for next question");
						count++;
					}
					
					
				}
				
			
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			

	}


		
		
		
		
	}
	


