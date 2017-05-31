package mySimulator;

import mySimulator.MyCache.MyBus;
import mySimulator.MyInstructions.BEQ;
import mySimulator.MyInstructions.HLT;
import mySimulator.MyInstructions.J;
import mySimulator.MyRegisters.IntegerRegisters;
import usageInstructions.ConfigurationSetup;
import usageInstructions.InstructionD;
import usageInstructions.InstructionInfoMaintain;
import usageInstructions.SourceRegisterValue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class MyScoreBoard {
	int pos_WB, pos_EX,pos_RD,pos_ISS,pos_IF;
	ArrayList<InstructionD> RD,IF,ISS,WB,EX;
	String OutputFilePath ="";
	String dataFilePath="";
	public MyScoreBoard(){
		clockCycle = new RclockTime();
		registers = myreg.new IntegerRegisters();
		cparser = myParsers.new ConfigurationParser().getInstance();
		iParser = myParsers.new InstructionParser().getInstance();
		programCountr = 0;
		pos_IF = 0;
		pos_ISS = 1;
		pos_RD = 2;
		pos_EX = 3;
		pos_WB = 4;
		
		
	}
	
	public void steupCache(ConfigurationSetup cm){
		MyCache myCache = new MyCache();
		MyBus bus =  myCache.new MyBus();
		iCache = mycache_.new ICache(cm.getICACHE_NUM_BLOCKS(), cm.getICACHE_BLOCK_SIZE(), clockCycle, bus);
		dCache = mycache_.new DCache(clockCycle, bus,dataFilePath);
	}
	String prettyFormatting(String instruction)
	{
		char res[] = new char[30];
        String str =instruction;
        char strArray[] = str.toCharArray();
        for(int i=0;i<res.length;i++){
            res[i]= ' ';
        }
        for(int i=0;i<strArray.length;i++){
             res[i]=strArray[i];
        }
        res[29]=' ';
        String result = new String(res);
        return result;
	}
	
	String header(){
		String instruction = "     Instruction             ";
		StringBuilder sb = new StringBuilder();
		sb.append(instruction);
		sb.append("Fetch");
		sb.append("     ");
		sb.append("Issue");
		sb.append("   ");
		sb.append("Read");
		sb.append("    ");
		sb.append("Exec");
		sb.append("    ");
		sb.append("Write");
		sb.append("   ");
		sb.append("RAW");
		sb.append("     ");
		sb.append("WAW");
		sb.append("    ");
		sb.append("Struct");
		sb.append("     ");
		return sb.toString();
		
		
	}
	MyParsers myParsers = new MyParsers();
	FuChec functionalUnit;
	MyParsers.ConfigurationParser cparser;
	MyCache mycache_ = new MyCache();
	MyCache.ICache iCache;
	MyCache.DCache dCache;
	RclockTime clockCycle;
	MyParsers.InstructionParser iParser;
	ConfigurationSetup cManager;
	InstructionInfoMaintain runManager;
	MyRegisters myreg = new MyRegisters();
	MyRegisters.IntegerRegisters registers;
	int programCountr;
	
	
	public void loadingConfigurationFile(String configFilePath){
		try {
			cparser.readConfigFile( configFilePath);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void loadingInstructionFile(String instructionFilePath){
		try{
			iParser.readInstFile(instructionFilePath);
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
	
	
	public void formattingInstructionsForOutput(ArrayList<InstructionD> totalInstructions) throws IOException{
		StringBuilder myOutput = new StringBuilder();
		class MyComparator implements Comparator<InstructionD>{

			@Override
			public int compare(InstructionD arg0, InstructionD arg1) {
				// TODO Auto-generated method stub
				if(arg0.instIssueNum < arg1.instIssueNum){
					return -1;
				}
				return 1;
			}
		}
		myOutput.append(header()+ System.lineSeparator());
		Collections.sort(totalInstructions, new MyComparator());
		for(int i = 0 ; i < totalInstructions.size(); i++){
			InstructionD instruction = totalInstructions.get(i);
			InstructionInfoMaintain rManager= InstructionInfoMaintain.getSingletonObj();
			if((i==totalInstructions.size()-1) && (instruction.iType==TypeOfInstruction.HALT)){
				instruction.endCycle[1]=0;
			}
		String label="";	
		if(instruction.iType== TypeOfInstruction.ConditionalBranch){
			  //label= instruction.branchlabellOut+": ";
		}
		myOutput.append(prettyFormatting(instruction.getLabelStr()+instruction.toString())+instruction.getoutputstr()+ System.lineSeparator());
		}
		myOutput.append(System.lineSeparator()+this.iCache.getMissHits()+ System.lineSeparator());
		myOutput.append(System.lineSeparator()+this.dCache.getMissHits());
		System.out.println(myOutput.toString());
		// output file location
		File file = new File( OutputFilePath);
		BufferedWriter writer = null;
		try {
		    writer = new BufferedWriter(new FileWriter(file));
		    writer.write(myOutput.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
		    if (writer != null) writer.close();
		   
		}
		
		
	}
	
	
	public void start(String instrutFile,String dataFile,String configFile,String outputfile) throws Exception {
		boolean isDone;
		boolean isStall = false;
		boolean isFlush = false;
		int instIssueNum = 0;
		boolean makeStall = false;
		loadingConfigurationFile(configFile);
		loadingInstructionFile(instrutFile);
		OutputFilePath = outputfile;
		dataFilePath = dataFile;
		cManager = ConfigurationSetup.getSingletonObj();
		runManager = InstructionInfoMaintain.getSingletonObj();
		ConfigurationSetup configMgr =  ConfigurationSetup.getSingletonObj();
		steupCache(ConfigurationSetup.getSingletonObj());
		functionalUnit = new FuChec(configMgr.getFPMULTIPLIER_Units(), configMgr.getFPDIVIDER_Units(), configMgr.getFPADDER_Units());
		EX = new ArrayList<InstructionD>();
		WB = new ArrayList<InstructionD>();
		ISS = new ArrayList<InstructionD>();
		IF = new ArrayList<InstructionD>();
		RD = new ArrayList<InstructionD>();		
		ArrayList<InstructionD> getword = new ArrayList<InstructionD>();
		ArrayList<InstructionD> instructionDone = new ArrayList<InstructionD>();
		while(true){
			isDone = true;
			ArrayList<InstructionD> noProblemInstructions = new ArrayList<InstructionD>();
			ArrayList<InstructionD> instructionBranch = new ArrayList<InstructionD>();
			ArrayList<InstructionD> stopInstructionBranch = new ArrayList<InstructionD>();
			ArrayList<InstructionD> flushInstruction = new ArrayList<InstructionD>();
			if(WB.size() != 0){
				isDone = false;
				for(int i = 0 ; i < WB.size(); i++){
					InstructionD inst = WB.get(i);
					if(inst.endCycle[this.pos_WB] < clockCycle.count() ){
						if(inst.getDestinationReg() != null && functionalUnit.checkWARHazard(inst)){
							//there is a hazard so please
							//inst.WARHZ = true;
						}else{
							noProblemInstructions.add(inst);
						}
					}
				}
			}
			if(EX.size() != 0){
				isDone = false;
				ArrayList<InstructionD> writeElgible = new ArrayList<InstructionD>();
				for(int i = 0 ; i < EX.size(); i++){
					InstructionD inst = EX.get(i);
					if(inst.endCycle[this.pos_EX] < clockCycle.count()){
						//we dont need to check any hazards
						writeElgible.add(inst);
					}
				}
				for(int i = 0 ; i < writeElgible.size(); i++){
					InstructionD inst = writeElgible.get(i);
					EX.remove(inst);
					inst.endCycle[this.pos_WB] = (int)(this.clockCycle.count()+1-1);
					inst.endCycle[this.pos_EX] = (int)(this.clockCycle.count()-1);
					WB.add(inst);
				}
			}
			
			ArrayList<InstructionD> executeLDSTElgible = new ArrayList<InstructionD>();
			if(RD.size() != 0){
				isDone = false;
				ArrayList<InstructionD> excuteElgible = new ArrayList<InstructionD>();
				for(int i = 0; i < RD.size(); i++){
					InstructionD inst = RD.get(i);
					if(inst.endCycle[this.pos_RD] < clockCycle.count()){
						List<SourceRegisterValue> sourceList = inst.getSourceReg();
						if(sourceList != null){
							boolean hasRAW = false;
							for(int j =0 ; j < sourceList.size(); j++){
								if( this.functionalUnit.checkRAWHazard(inst) == true){
									hasRAW = true;
								}
							}
							if(hasRAW == true){
								inst.RAWHZ = true;
							}else{
								excuteElgible.add(inst);	
							}
						}else{
							excuteElgible.add(inst);
						}
					}
				}
				for(int i =0; i < excuteElgible.size(); i++){
					InstructionD inst = excuteElgible.get(i);
					List<SourceRegisterValue> sourceReg = inst.getSourceReg();
					if(sourceReg != null){
						if(sourceReg.size() > 0){
							SourceRegisterValue reg = sourceReg.get(0);
							String regName = reg.getSource();
							reg.setValue(registers.readFrom(regName));
						}
						if(sourceReg.size() > 1){
							SourceRegisterValue reg = sourceReg.get(1);
							String regName = reg.getSource();
							reg.setValue(registers.readFrom(regName));
						}
					}
					
					FuSteup fStatus = inst.getFunctionalUnitStatus();
					
					fStatus.source1Ready = false;
					fStatus.source2Ready = false;
					inst.endCycle[this.pos_RD] = (int) (this.clockCycle.count()-1);
				
					
					inst.execInst();
					if(inst.iType == TypeOfInstruction.ConditionalBranch){
						makeStall = false;
						if(inst instanceof MyInstructions.BEQ){
							MyInstructions.BEQ bq = (MyInstructions.BEQ)inst;
							if(bq.getComparedResult()){
								isFlush = true;
								String label = bq.getBranchToLabel();
								programCountr = runManager.getAddressforLabel(label);
							}
						}else if(inst instanceof MyInstructions.BNE){
							MyInstructions.BNE bne = (MyInstructions.BNE)inst;
							if(bne.getComparedResult()){
								isFlush = true;
								String label = bne.getBranchToLabel();
								programCountr = runManager.getAddressforLabel(label);
							}
						}
						instructionBranch.add(inst);
					} 
					else if(inst.fType == TypeOfFunctionalUnit.FPADDUNIT){
						inst.endCycle[this.pos_EX] = (int) (cManager.getFPADDER_Cycles()+clockCycle.count()-1);
						EX.add(inst);
					}else if(inst.fType == TypeOfFunctionalUnit.FPDIVUNIT){
						inst.endCycle[this.pos_EX] = (int) (cManager.getFPDIVIDER_Cycles()+clockCycle.count()-1);
						EX.add(inst);
					}else if(inst.fType == TypeOfFunctionalUnit.FPMULTUNIT){
						inst.endCycle[this.pos_EX] = (int) (cManager.getFPMULTIPLIER_Cycles()+clockCycle.count()-1);
						EX.add(inst);
					}else if(inst.fType == TypeOfFunctionalUnit.INTEGERUNIT){
						inst.endCycle[this.pos_EX] = (int) (1 + clockCycle.count()-1);
						EX.add(inst);
					}else if(inst.fType == TypeOfFunctionalUnit.LSU){
						executeLDSTElgible.add(inst);
						EX.add(inst);
					}
					//
					RD.remove(inst);
					
				}
			} 
			
			if(ISS.size() != 0 && isStall == false){
				isDone = false;
				ArrayList<InstructionD> readElgible = new ArrayList<InstructionD>();
				for(int i = 0 ; i < ISS.size(); i++){
					InstructionD inst = ISS.get(i);
					
					if(isFlush == true){
											ISS.remove(inst);
						 					inst.endCycle[this.pos_ISS] = (int) (this.clockCycle.count()-1);
						 					flushInstruction.add(inst);
						 					continue;
						 				}
				      if(inst instanceof HLT){
						  				      stopInstructionBranch.add(inst);
						  						continue;
						  					}
				      if(inst instanceof J){
						 						J j = (J)inst;
						 				String label = j.getJumpTo();
						 					stopInstructionBranch.add(inst);
						 					programCountr = runManager.getAddressforLabel(label);
						 					isFlush = true;
						 					continue;
						 				}
					if(inst.entryCycle[this.pos_ISS] < clockCycle.count()){
						if( functionalUnit.checkAvailability(inst.fType) == false){
							inst.STRUCTHZ = true;
							continue;
						}
						if(inst.getDestinationReg() == null){
							readElgible.add(inst);
						}
						else if(functionalUnit.checkWAWHazard(inst.getDestinationReg().getDestination()) == false){
							readElgible.add(inst);
						}
						else{
							inst.WAWHZ = true;
						}
					}
				}
				for(int i = 0 ; i < readElgible.size(); i++){
					InstructionD inst = readElgible.get(i);
					if(inst.iType == TypeOfInstruction.ConditionalBranch){
						makeStall = true;
					}
					FuSteup st = functionalUnit.fuFetch(inst);
					inst.setFunctionalUnitStatus(st);
					inst.endCycle[this.pos_RD] = (int) (this.clockCycle.count()+1-1);
					inst.endCycle[this.pos_ISS] = (int) (this.clockCycle.count()-1);
					ISS.remove(inst);
					RD.add(inst);
				}
				for(int i = 0 ; i < stopInstructionBranch.size(); i++){
					InstructionD inst = stopInstructionBranch.get(i);
					ISS.remove(inst);
				}
			}
		
			
			if(IF.size() == 0 && isStall == false){
				
				
				InstructionD inst = runManager.getInstructioninAddress(programCountr);
				if(inst != null){
					int clockCycles = this.iCache.fettchThisInstruction(programCountr);
					isDone = false;
					inst.endCycle[this.pos_IF] = (int) (clockCycle.count()+clockCycles-1);
					programCountr = programCountr  + 1;
					inst.instIssueNum = instIssueNum;
					instIssueNum = instIssueNum +1;
					IF.add(inst);
				}
				
			}else if(isStall == false){
			
				isDone = false;
				InstructionD inst = IF.get(0);
				if(ISS.size() == 0 && inst.endCycle[this.pos_IF] < clockCycle.count()){
					IF.remove(inst); 
					inst.endCycle[this.pos_IF] = (int) (clockCycle.count()-1);
					if(isFlush == false){
						inst.endCycle[this.pos_ISS] = (int) (clockCycle.count()+1-1);
						ISS.add(inst);
					}else{
						isFlush = false;
						flushInstruction.add(inst);
					}
					InstructionD newinst = runManager.getInstructioninAddress(programCountr);
					
					if(newinst != null){
						int clockCycles = this.iCache.fettchThisInstruction(programCountr);
						isDone = false;
						newinst.endCycle[this.pos_IF] = (int) (clockCycle.count()+clockCycles-1);
						newinst.instIssueNum = instIssueNum;
						instIssueNum = instIssueNum +1;
						IF.add(newinst);
						programCountr = programCountr  + 1;
					}
					
				}
			}
	
			for(int i = 0 ; i < flushInstruction.size(); i++){
				InstructionD inst = flushInstruction.get(i);
				inst.endCycle[this.pos_IF] = (int) (clockCycle.count()-1);
				//System.out.println(inst.toString());
				instructionDone.add(inst);
			}
		
			for(int i = 0 ; i < instructionBranch.size(); i++){
				InstructionD inst = instructionBranch.get(i);
				inst.endCycle[this.pos_RD] = (int)(clockCycle.count()-1);
				functionalUnit.writeToFunctionalStatus(inst);
				//System.out.println(inst.toString());
				instructionDone.add(inst);
				
			}
	
			for(int i = 0 ; i < stopInstructionBranch.size(); i++){
				InstructionD inst = stopInstructionBranch.get(i);
				inst.endCycle[this.pos_ISS] = (int) (clockCycle.count()-1);
				ISS.remove(inst);
				//System.out.println(inst.toString());
				instructionDone.add(inst);
			} 
		
			
			getword = new ArrayList<InstructionD>();
			for(int i = 0 ; i < executeLDSTElgible.size(); i++){
				InstructionD inst = executeLDSTElgible.get(i);
				if(inst instanceof MyInstructions.LD){
					long addressvalue = inst.addressValue;
					MyCache.DCache.DCacheBundle info = dCache.getFetchData(addressvalue);
					inst.endCycle[this.pos_EX] = (int) (clockCycle.count()+info.cCycles -1);
					int value = Converting.fromStrToInt(info.data);
					getword.add(inst);
					inst.getDestinationReg().setValue(value);
				}else if(inst instanceof MyInstructions.SD){
					long addressvalue = inst.addressValue;
					int numCycles = dCache.updatingAtAddress(addressvalue,"10000000100000001000000010000000");
					inst.endCycle[this.pos_EX] = (int) (clockCycle.count()+numCycles -1);
					getword.add(inst);
				}else if(inst instanceof MyInstructions.LW){
					long addressvalue = inst.addressValue;
					MyCache.DCache.DCacheBundle info = dCache.getFetchData(addressvalue);
					inst.endCycle[this.pos_EX] = (int) (clockCycle.count()+info.cCycles -1);
					int value = Converting.fromStrToInt(info.data);
					//int value =Integer.parseInt("101001",2);
					inst.getDestinationReg().setValue(value);
				}else if(inst instanceof MyInstructions.SW){
					long addressvalue = inst.addressValue;
					List<SourceRegisterValue> srcReg = inst.getSourceReg();
					String defaultValue = "10000000";
					if(srcReg != null){
						int val = srcReg.get(0).getValue();
						defaultValue = Converting.fromIntToBinStr(val);
					}
					int numCycles = dCache.updatingAtAddress(addressvalue, defaultValue);
					inst.endCycle[this.pos_EX] = (int) (clockCycle.count()+numCycles -1);
				}
			}
		
			for(int i = 0 ; i < noProblemInstructions.size(); i++){
				InstructionD inst = noProblemInstructions.get(i);
				WB.remove(inst);
				if(inst.getDestinationReg() != null){
					String regName = inst.getDestinationReg().getDestination();
					int val = inst.getDestinationReg().getValue();
					registers.writeTo(regName, val);
				}
				//System.out.println(inst.instIssueNum);
				//System.out.println(inst.toString());
				instructionDone.add(inst);
				functionalUnit.writeToFunctionalStatus(inst);
			}
			if(isDone == true){
				try {
					dCache.writeToMem();
					dCache.writeMemToFile();
					formattingInstructionsForOutput(instructionDone);
					 //writeToDataFile(dataFilePath);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
			if(makeStall == true){
				isStall = true;
			}
			if(makeStall == false){
				isStall = false;
			}
			clockCycle.moveForward();
            for(int i = 0 ; i < getword.size();i++){
				InstructionD inst = getword.get(i);
				if(inst instanceof MyInstructions.LD){
					long addressvalue = inst.addressValue;
					MyCache.DCache.DCacheBundle info = dCache.getFetchData(addressvalue+4);
					inst.endCycle[this.pos_EX] = inst.endCycle[this.pos_EX] +info.cCycles;
					int value = Converting.fromStrToInt(info.data);
					inst.getDestinationReg().setValue(value);
				}else if(inst instanceof MyInstructions.SD){
					long addressvalue = inst.addressValue;
					int numCycles2 = dCache.updatingAtAddress(addressvalue+4,"10000000100000001000000010000000");
					inst.endCycle[this.pos_EX] = inst.endCycle[this.pos_EX] +numCycles2;
				}
			}
		}
	}

//	private void writeToDataFile(String dataFilePath2) throws IOException {
//		// TODO Auto-generated method stub
//		File file = new File(dataFilePath2);
//		BufferedWriter writer = null;
//		try {
//			MyMemory.DataWrite myMemory = new MyMemory.DataWrite();
//		    writer = new BufferedWriter(new FileWriter(file));
//		    writer.write(myMemory.totalFile());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//		    if (writer != null) writer.close();
//		   
//		}
//		
//	}

	
	
	
	
}
