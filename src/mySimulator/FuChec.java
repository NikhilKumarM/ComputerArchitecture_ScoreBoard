package mySimulator;

import java.util.ArrayList;
import java.util.List;

import usageInstructions.*;
public class FuChec {
	
	public FuSteup fuFetch(InstructionD instruction){
		
		String destRegister = "";
		String srcRegister1  = "";
		String srcRegister2 = "";
		boolean src1Ready = true;
		boolean src2Ready = true;
		if(instruction.getDestinationReg() != null){
			destRegister = instruction.getDestinationReg().getDestination();
		}
		List<SourceRegisterValue> srcReg = instruction.getSourceReg();
		if(srcReg != null && srcReg.size() > 0){
			srcRegister1 = instruction.getSourceReg().get(0).getSource();
			src1Ready = checkReady(srcRegister1);
		}
		if(srcReg != null &&srcReg.size() > 1){
			srcRegister2 = instruction.getSourceReg().get(1).getSource();
			src2Ready = checkReady(srcRegister2);
		}
		for(int i = 0; i < fp.size(); i++){
			if(fp.get(i).status == false && instruction.fType == fp.get(i).fType){
				FuSteup status = fp.get(i);
				status.fType = instruction.fType;
				status.source1Ready = src1Ready;
				status.source2Ready = src2Ready;
				status.sourceRegister1 = srcRegister1;
				status.sourceRegister2 = srcRegister2;
				status.destinationRegister = destRegister;
				status.status = true;
				return status;
			}
		}
		return null;
	}
	
	public void writeToFunctionalStatus(InstructionD inst){
		FuSteup fStatus = inst.getFunctionalUnitStatus();
		if(inst.getDestinationReg() == null){	
			fStatus.clear();
			return;
		}
		String destRegister = inst.getDestinationReg().getDestination();
		for(int i = 0 ; i < fp.size(); i++){
			FuSteup myStatus = fp.get(i);
			if(myStatus.sourceRegister1.equals(destRegister)){
				myStatus.source1Ready = true;
			}
			if(myStatus.sourceRegister2.equals(destRegister)){
				myStatus.source2Ready = true;
			}
		}
		fStatus.clear();
	}
	ArrayList<FuSteup> fp;
	int fpDivUnits;
	int fpAddUnits;
	int fpMulUnits;
	public FuChec(int multipliers, int dividers, int adders){
		fp = new ArrayList<FuSteup>();
		this.fpAddUnits = adders;
		this.fpDivUnits = dividers;
		this.fpMulUnits = multipliers;
		for(int i = 0 ; i < this.fpMulUnits; i++){
			fp.add(new FuSteup(TypeOfFunctionalUnit.FPMULTUNIT));
		}
		for(int i = 0; i < this.fpAddUnits; i++){
			fp.add(new FuSteup(TypeOfFunctionalUnit.FPADDUNIT));
		}
		for(int i = 0; i < this.fpDivUnits; i++){
			fp.add(new FuSteup(TypeOfFunctionalUnit.FPDIVUNIT));
		}
		fp.add(new FuSteup(TypeOfFunctionalUnit.INTEGERUNIT));
		fp.add(new FuSteup(TypeOfFunctionalUnit.LSU));
		fp.add(new FuSteup(TypeOfFunctionalUnit.BRANCHUNIT));
	}
	
	public boolean checkAvailability(TypeOfFunctionalUnit fType){
		for(int i = 0 ; i < fp.size(); i++){
			if(fp.get(i).fType == fType && fp.get(i).status == false){
				return true;
			}
		}
		return false;
	}

	public boolean checkRAWHazard(InstructionD inst){
		FuSteup fStatus = inst.getFunctionalUnitStatus();
		if(fStatus.source1Ready == true && fStatus.source2Ready == true){
			return false;
		}
		return true;
	}
	public boolean checkWAWHazard(String register){
		for(int i = 0 ; i < fp.size(); i++){
			if(fp.get(i).status == true && fp.get(i).destinationRegister.equals(register)){
				return true;
			}
		}
		return false;
	}
	
	
	
	public boolean checkWARHazard(InstructionD inst){
	    if( inst.getDestinationReg() == null){
	    	return false;
	    }
	    String destReg = inst.getDestinationReg().getDestination();
		for(int i = 0 ; i < fp.size(); i++){
			FuSteup fStatus = inst.getFunctionalUnitStatus();
			if(fStatus.status){
				if(destReg.equals(fStatus.sourceRegister1) && fStatus.source1Ready == true){
					return true;
				}
				if(destReg.equals(fStatus.sourceRegister2) && fStatus.source2Ready == true){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean checkReady(String register){
		for(int i = 0; i < fp.size(); i++){
			FuSteup fStatus = fp.get(i);
			if(fStatus.status == true && fStatus.destinationRegister.equals(register)){
				return false;
			}
		}
		return true;
	}
	
	
	
}