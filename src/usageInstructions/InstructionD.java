package usageInstructions;

import mySimulator.FuSteup;
import mySimulator.TypeOfFunctionalUnit;
import mySimulator.TypeOfInstruction;

public abstract class InstructionD implements InstructionInterface{

	public boolean RAWHZ;
	public boolean WAWHZ;
	public boolean STRUCTHZ;
	public int[] entryCycle;
	public int[] endCycle;
	public TypeOfFunctionalUnit fType;
	public TypeOfInstruction iType;
	public int instIssueNum;
	public long addressValue;
	public FuSteup fStatus;
	public String branchlabellOut;
	String labelStr,instStr;
	public  InstructionD(){
		this.RAWHZ = false;
		this.WAWHZ = false;
		this.STRUCTHZ = false;
		this.entryCycle = new int[5];
		this.endCycle = new int[5];
		
		this.fType = TypeOfFunctionalUnit.MISLEANEOUS;
		this.iType = TypeOfInstruction.UNKNOWN;
		
	}
	public String getLabelStr() {
		return labelStr;
	}
	public void setLabelStr(String labelStr) {
		this.labelStr = labelStr;
	}
	public String getInstStr() {
		return instStr;
	}
	public void setInstStr(String instStr) {
		this.instStr = instStr;
	}
	public void setFunctionalUnitStatus(FuSteup fStatus){
		this.fStatus = fStatus;
	}
	
	public void setLabeltoOutputStr(String str){
		this.labelStr   = str;
	}
	
	public void setInstOutptuStr(String str){
		this.instStr = str;
	}
	public FuSteup getFunctionalUnitStatus(){
		return this.fStatus;
	}
	public String getoutputstr(){
		StringBuilder sb = new StringBuilder();
		
		//writing the end cycle values
		
		for(int index=0; index<this.endCycle.length;index++){
			if(this.endCycle[index]!=0){
				sb.append(this.endCycle[index]+"\t");
			}else{
				sb.append(" \t");
			}
		}
		
		if(this.RAWHZ){
			sb.append("Y\t");
		}else{
			sb.append("N\t");
		}
		
		
		if(this.WAWHZ){
			sb.append("Y\t");
		}else{
			sb.append("N\t");
		}
		
		if(this.STRUCTHZ){
			sb.append("Y\t");
		}else{
			sb.append("N\t");
		}
		
		return sb.toString();
	}
	
}
