package mySimulator;

import java.io.IOException;

public class Run {

	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		if(args.length!=4){
			System.out.println("Usage: simulator inst.txt data.txt config.txt result.txt");
			System.exit(0);
		}
		MyScoreBoard myScoreBoard = new MyScoreBoard();
     	String instructionFilePath = args[0];
     	String dataFilePath = args[1];
    	String configurationFilePath = args[2];
    	String ouptutpathFile = args[3];
		myScoreBoard.start(args[0],args[1],args[2],args[3]);
	}
     //"C:\\Users\\Nikhil Kumar Mengani\\workspace\\ProjectCA\\Inst.txt"
	 //"C:\\Users\\Nikhil Kumar Mengani\\workspace\\ProjectCA\\Data.txt"
	 // "C:\\Users\\Nikhil Kumar Mengani\\workspace\\ProjectCA\\Config.txt",
	 //"C:\\Users\\Nikhil Kumar Mengani\\workspace\\ProjectCA\\output.txt");
}
