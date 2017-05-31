package mySimulator;

import java.util.HashMap;

public class MyRegisters {

	public class FloatingPointRegisters {
		HashMap<String, Float> registers;
		public FloatingPointRegisters(){
			registers = new HashMap<String, Float>();
		}
		public void writeTo(String register, Float value){
			registers.put(register, value);
		}
		public Float readFrom(String register){
			if(registers.containsKey(register)){
				return registers.get(register);
			}
			return null;
		}
	}
	
	public class IntegerRegisters {
		HashMap<String, Integer> registers;
		public IntegerRegisters(){
			registers = new HashMap<String, Integer>();
			for(int i = 0 ; i < 32; i++){
				registers.put("F"+i, 0);
			}
		}
		public void writeTo(String register, Integer value){
			registers.put(register, value);
		}
		
		public Integer readFrom(String register){
			if(registers.containsKey(register)){
				return registers.get(register);
			}
			return null;
		}
	}

}
