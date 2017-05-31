package mySimulator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MyMemory {
	ArrayList<String []> data;
	long startAddress;
	static String dataPath="";
	public MyMemory(String dataFilePath){
		data = new ArrayList<String[]>();
		startAddress = 256;
		try {
			dataPath = dataFilePath;
			File file = new File(dataFilePath);
			FileReader fileReader = null;
			fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			int index = 0;
			String blockData[]  = new String[4];
			while ((line = bufferedReader.readLine()) != null) {
				if((index+1)%4 == 0){
					blockData[index%4] = line;
					data.add(blockData);
					blockData = new String[4];
				}
				blockData[index%4] = line;
				index++;
			}
			fileReader.close();
		}
        catch(FileNotFoundException e){
        	e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	public String[] getDataFromAddress(long address){
		int index = (int)(address - startAddress)/4;
		index = index/4;
		return data.get(index);
	}
	
	public String[] updateAtAddress(long address, int offset, String givendata){
		int index = (int)(address - startAddress)/4;
		index = index / 4;
		String mydata[] = this.data.get(index);
		mydata[offset] = givendata;
		return mydata;
	}
	public void updateData(long address, String givendata[]){
		int index = (int)(address-startAddress)/4;
		index = index/4;
		String newData[] = new String[4];
		for(int i =0; i < givendata.length; i++){
			newData[i] = givendata[i];
		}
		data.set(index, newData);
	}
	
	public void writingToFile(){
		 		try{
		 		BufferedWriter bw = new BufferedWriter(new FileWriter(dataPath));
		            for(int i = 0 ; i < data.size(); i++){
		            	for(int j = 0; j < data.get(i).length; j++){
		            		bw.write(data.get(i)[j]);
		            		bw.write("\r\n");
		            	}
		          }
		            bw.close();
				}catch(IOException e ){
					System.out.println("Exception writing to output file...");
		 		}
		 	}
}
