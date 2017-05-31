package usageInstructions;

import java.util.HashMap;
import java.util.Map;

import mySimulator.MyParsers;

public class InstructionInfoMaintain {
	
	public static InstructionInfoMaintain rManager = null;
	
	private int InstructionCount =0;
	private Map<String,Integer> labelMap = null;
	private Map<Integer, String> instructionlist = null;
	
	public static InstructionInfoMaintain getSingletonObj() {
		if(rManager == null){
			rManager = new InstructionInfoMaintain();
		}
		return rManager;
	}
	
	private InstructionInfoMaintain() {
		this.InstructionCount = 0;
		this.instructionlist = new HashMap<>();
		this.labelMap = new HashMap<>();
	}
	
	public InstructionD getInstructioninAddress(int address) throws Exception {
		if(instructionlist.containsKey(address)){
			String instStr= instructionlist.get(address);
			return new MyParsers().new InstructionParser().getInstructionObj(instStr);
		}else{
			return null;
		}		
	}
	
	
	public int getAddressforLabel(String lbl){
		if(labelMap.containsKey(lbl)){
			return labelMap.get(lbl);
		}else{
			return -1;
		}
	}

	public int getInstructionCount() {
		return InstructionCount;
	}

	public void setInstructionCount(int instructionCount) {
		InstructionCount = instructionCount;
	}

	public Map<String, Integer> getLabelMap() {
		return labelMap;
	}

	public void setLabelMap(Map<String, Integer> labelMap) {
		this.labelMap = labelMap;
	}

	public Map<Integer, String> getInstructionlist() {
		return instructionlist;
	}

	public void setInstructionlist(Map<Integer, String> instructionlist) {
		this.instructionlist = instructionlist;
	}

	
	public void addInstructionToMap(int add, String instStr){
		this.instructionlist.put(add, instStr);
		this.InstructionCount++;
		
	}
	
	public void addLabelToMap(String lbl, int add){
		this.labelMap.put(lbl, add);
	}
}
