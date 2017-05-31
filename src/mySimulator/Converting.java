package mySimulator;

public class Converting {
	public static int fromStrToInt(String data){
		int val = 0;
		for(int i = 1; i < data.length(); i++){
			val = val * 2 + (data.charAt(i)-'0');
		}
		if(data.charAt(0) == '1'){
			val = val + Integer.MIN_VALUE;
		}
		return val;
	}
	public static String fromIntToBinStr(int data){
		char strBInput[] = new char[32];
		for(int i = 0 ; i < strBInput.length; i++){
			strBInput[i] = '0';
		}
		long nVal;
		if(data < 0){
			nVal = data + Integer.MIN_VALUE;
			strBInput[0] = '1';
		}else{
			nVal = data;
			strBInput[0] = '0';
		}
		int index = 31;
		while(nVal != 0){
			int mod = (int) (nVal%2);
			if(mod == 0){
				strBInput[index] = '0';
			}else{
				strBInput[index] = '1';
			}
			index--;
			nVal = nVal/2;
		}
		return new String(strBInput);
	}
	
	
}
