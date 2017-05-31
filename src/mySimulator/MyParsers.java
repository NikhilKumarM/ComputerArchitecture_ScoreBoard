package mySimulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import usageInstructions.ConfigurationSetup;
import usageInstructions.InstructionD;
import usageInstructions.InstructionInfoMaintain;




public class MyParsers {
	public class InstructionParser {
       
		private InstructionParser iparser= null;

		public InstructionParser getInstance() {
             
			if(iparser== null){
				iparser = new InstructionParser();
			}
			return iparser;
		}

		public void readInstFile(String filepath) throws IOException {

			
			if(filepath!= null && !filepath.isEmpty()){
				BufferedReader br = new BufferedReader(new FileReader(filepath));
				try {
					String line;
					while ((line= br.readLine()) != null) {			    	
						if(!line.trim().isEmpty()){
							getInfoFromInst(line);
						}
					}
				}catch(Exception ex){
					System.out.println("Exception in reading the file "+ex.getMessage());
				}finally {
					br.close();
				}
			}
		}

		private void getInfoFromInst(String line) throws Exception  {
			
			String[] labelNInst = line.trim().split(":");
			String label=" ";
			String inst = "";
			String opcode = "";
			
			InstructionInfoMaintain infoManager = InstructionInfoMaintain.getSingletonObj();

			if(labelNInst.length==2){
				label = labelNInst[0].trim();
				inst = labelNInst[1].trim();
				InstructionInfoMaintain.getSingletonObj().addLabelToMap(label,InstructionInfoMaintain.getSingletonObj().getInstructionCount());
			}else{
				inst = labelNInst[0].trim();
			}

			String[] instparamArr = inst.trim().split(",|\\(|\\)|\\s+");
			List<String> instparamlist = new ArrayList<String>(Arrays.asList(instparamArr));
			instparamlist.removeAll(Collections.singleton(null));
			instparamlist.removeAll(Collections.singleton(""));

			if(instparamlist.size()>=1){

				opcode = instparamlist.get(0).trim().toUpperCase();
				infoManager.addInstructionToMap(infoManager.getInstructionCount(), line);
				switch(opcode){
				case "ADD.D":
					
					
					break;
				case "SUB.D":
					
					break;
				case "MUL.D":
					
					break;
				case "DIV.D":
				
					break;
				case "DADD":
					
					break;
				case "DSUB":
					
					break;
				case "AND":
					
					break;
				case "OR":
					
					break;
				case "DADDI":
				
					break;
				case "DSUBI":
					
					break;
				case "ANDI":
					
					break;
				case "ORI":
					
					break;
				case "LW":
					
					break;
				case "L.D":
				
					break;
				case "SW":
				
					break;
				case "LUI":
					break;
				case "S.D":
				
					break;
				case "LI":
				
					break;
				case "BNE":
					
					break;
				case "BEQ":
					
					break;
				case "J":
					
					break;
				case "HLT":
					//instruction = new MyInstructions.HLT();
					break;
				default:
					System.out.println("Unknown Instruction at line "+ (infoManager.getInstructionCount())+" "+line);
					throw new Exception();


				}
			
				//infoManager.addInstructionToMap(infoManager.getInstructionCount(), line);
			}
			
		}
		public  InstructionD getInstructionObj(String line) throws Exception {
			InstructionD instruction = null;
			String[] labelNInst = line.trim().split(":");
			String label=" ";
			String inst = "";
			String opcode = "";
			String destReg = "";
			String sourcereg1 ="";
			String sourcereg2 = "";
			String immediateVal = "";
			if(labelNInst.length==2){
				label =labelNInst[0].trim()+":";
				inst = labelNInst[1].trim();
				//RunManager.getInstance().addLabelToMap(label,RunManager.getInstance().getInstructionCount());
			}else{
				inst = labelNInst[0].trim();
			}
			InstructionInfoMaintain rManager = InstructionInfoMaintain.getSingletonObj();
			String[] instparamArr = inst.trim().split(",|\\(|\\)|\\s+");
			List<String> instparamlist = new ArrayList<String>(Arrays.asList(instparamArr));
			instparamlist.removeAll(Collections.singleton(null));
			instparamlist.removeAll(Collections.singleton(""));

			if(instparamlist.size()>=1){
                //currentInstnum++;
				opcode = instparamlist.get(0).trim().toUpperCase();
				switch(opcode){
				case "ADD.D":
					destReg = instparamlist.get(1).trim().toUpperCase();
					sourcereg1 = instparamlist.get(2).trim().toUpperCase();
					sourcereg2 = instparamlist.get(3).trim().toUpperCase();
					instruction = new MyInstructions.ADDD(destReg, sourcereg1, sourcereg2);
					break;
				case "SUB.D":
					destReg = instparamlist.get(1).trim().toUpperCase();
					sourcereg1 = instparamlist.get(2).trim().toUpperCase();
					sourcereg2 = instparamlist.get(3).trim().toUpperCase();
					instruction = new MyInstructions.SUBD(destReg, sourcereg1, sourcereg2);
					break;
				case "MUL.D":
					destReg = instparamlist.get(1).trim().toUpperCase();
					sourcereg1 = instparamlist.get(2).trim().toUpperCase();
					sourcereg2 = instparamlist.get(3).trim().toUpperCase();
					instruction = new MyInstructions.MULD(destReg, sourcereg1, sourcereg2);
					break;
				case "DIV.D":
					destReg = instparamlist.get(1).trim().toUpperCase();
					sourcereg1 = instparamlist.get(2).trim().toUpperCase();
					sourcereg2 = instparamlist.get(3).trim().toUpperCase();
					instruction = new MyInstructions.DIVD(destReg, sourcereg1, sourcereg2);
					break;
				case "DADD":
					destReg = instparamlist.get(1).trim().toUpperCase();
					sourcereg1 = instparamlist.get(2).trim().toUpperCase();
					sourcereg2 = instparamlist.get(3).trim().toUpperCase();
					instruction = new MyInstructions.DADD(destReg, sourcereg1, sourcereg2);
					break;
				case "DSUB":
					destReg = instparamlist.get(1).trim().toUpperCase();
					sourcereg1 = instparamlist.get(2).trim().toUpperCase();
					sourcereg2 = instparamlist.get(3).trim().toUpperCase();
					instruction = new MyInstructions.DSUB(destReg, sourcereg1, sourcereg2);
					break;
				case "AND":
					destReg = instparamlist.get(1).trim().toUpperCase();
					sourcereg1 = instparamlist.get(2).trim().toUpperCase();
					sourcereg2 = instparamlist.get(3).trim().toUpperCase();
					instruction = new MyInstructions.AND(destReg, sourcereg1, sourcereg2);
					break;
				case "OR":
					destReg = instparamlist.get(1).trim().toUpperCase();
					sourcereg1 = instparamlist.get(2).trim().toUpperCase();
					sourcereg2 = instparamlist.get(3).trim().toUpperCase();
					instruction = new MyInstructions.OR(destReg, sourcereg1, sourcereg2);
					break;
				case "DADDI":
					destReg = instparamlist.get(1).trim().toUpperCase();
					sourcereg1 = instparamlist.get(2).trim().toUpperCase();
					immediateVal = instparamlist.get(3).trim().toUpperCase();
					instruction = new MyInstructions.DADDI(destReg, sourcereg1, immediateVal);
					break;
				case "DSUBI":
					destReg = instparamlist.get(1).trim().toUpperCase();
					sourcereg1 = instparamlist.get(2).trim().toUpperCase();
					immediateVal = instparamlist.get(3).trim().toUpperCase();
					instruction = new MyInstructions.DSUBI(destReg, sourcereg1, immediateVal);
					break;
				case "ANDI":
					destReg = instparamlist.get(1).trim().toUpperCase();
					sourcereg1 = instparamlist.get(2).trim().toUpperCase();
					immediateVal = instparamlist.get(3).trim().toUpperCase();
					instruction = new MyInstructions.ANDI(destReg, sourcereg1, immediateVal);
					break;
				case "ORI":
					destReg = instparamlist.get(1).trim().toUpperCase();
					sourcereg1 = instparamlist.get(2).trim().toUpperCase();
					immediateVal = instparamlist.get(3).trim().toUpperCase();
					instruction = new MyInstructions.ORI(destReg, sourcereg1, immediateVal);
					break;
				case "LW":
					destReg = instparamlist.get(1).trim().toUpperCase();
					immediateVal = instparamlist.get(2).trim().toUpperCase();
					sourcereg1 = instparamlist.get(3).trim().toUpperCase();
					instruction = new MyInstructions.LW(destReg, sourcereg1, immediateVal);
					break;
				case "L.D":
					destReg = instparamlist.get(1).trim().toUpperCase();
					immediateVal = instparamlist.get(2).trim().toUpperCase();
					sourcereg1 = instparamlist.get(3).trim().toUpperCase();
					instruction = new MyInstructions.LD(destReg, sourcereg1, immediateVal);
					break;
				case "SW":
					sourcereg1 = instparamlist.get(1).trim().toUpperCase();
					immediateVal = instparamlist.get(2).trim().toUpperCase();
					sourcereg2 = instparamlist.get(3).trim().toUpperCase();
					instruction = new MyInstructions.SW(sourcereg1, sourcereg2, immediateVal);
					break;
				case "S.D":
					sourcereg1 = instparamlist.get(1).trim().toUpperCase();
					immediateVal = instparamlist.get(2).trim().toUpperCase();
					sourcereg2 = instparamlist.get(3).trim().toUpperCase();
					instruction = new MyInstructions.SD(sourcereg1, sourcereg2, immediateVal);
					break;
				case "LI":
					destReg = instparamlist.get(1).trim().toUpperCase();
					immediateVal = instparamlist.get(2).trim().toUpperCase();
					instruction = new MyInstructions.LI(destReg, immediateVal);
					break;
				case "BNE":
					sourcereg1 = instparamlist.get(1).trim().toUpperCase();
					sourcereg2 = instparamlist.get(2).trim().toUpperCase();
					destReg = instparamlist.get(3).trim().toUpperCase();
					instruction = new MyInstructions.BNE(sourcereg1, sourcereg2, destReg);
					break;
				case "BEQ":
					sourcereg1 = instparamlist.get(1).trim().toUpperCase();
					sourcereg2 = instparamlist.get(2).trim().toUpperCase();
					destReg = instparamlist.get(3).trim().toUpperCase();
					instruction = new MyInstructions.BEQ(sourcereg1, sourcereg2, destReg);
					break;
				case "J":
					destReg = instparamlist.get(1).trim().toUpperCase();
					instruction = new MyInstructions.J(destReg);
					break;
				case "HLT":
					instruction = new MyInstructions.HLT();
					break;
				case "LUI":
					destReg = instparamlist.get(1).trim().toUpperCase();
					immediateVal = instparamlist.get(2).trim().toUpperCase();
					instruction = new MyInstructions.LUI(destReg, immediateVal);
					break;
				default:
					


				}
				instruction.setInstOutptuStr(instruction.toString());
				instruction.setLabeltoOutputStr(label);
				return instruction;
			

			}
			
			return null;
		}

	}
	public class ConfigurationParser {

		private ConfigurationParser cparser= null;

		public ConfigurationParser getInstance() {

			if(cparser== null){
				cparser = new ConfigurationParser();
			}
			return cparser;
		}

		public void readConfigFile(String filepath) throws IOException {

			
			if(filepath!= null && !filepath.isEmpty()){
				BufferedReader br = new BufferedReader(new FileReader(filepath));
				try {
					String line;
					while ((line= br.readLine()) != null) {			    	
						if(!line.trim().isEmpty()){
							updateConfigInfo(line);
						}
					}
				}catch(Exception ex){
					System.out.println("Exception in readConfigFile"+ex.getMessage());
				}finally {
					br.close();
				}
			}
		}

		private void updateConfigInfo(String line) {

			ConfigurationSetup cManager = ConfigurationSetup.getSingletonObj();
			String[] linearr = line.split(",|:");
			if(linearr.length==3){
				String key = linearr[0].trim();
				String units = linearr[1].trim();
				String cycles = linearr[2].trim();

				switch(key.toUpperCase().trim()){

				case "FP ADDER":
					cManager.setFPADDER_Units(Integer.valueOf(units));
					cManager.setFPADDER_Cycles(Integer.valueOf(cycles));
					break;
				case "FP MULTIPLIER":
					cManager.setFPMULTIPLIER_Units(Integer.valueOf(units));
					cManager.setFPMULTIPLIER_Cycles(Integer.valueOf(cycles));
					break;
				case "FP DIVIDER":
					cManager.setFPDIVIDER_Units(Integer.valueOf(units));
					cManager.setFPDIVIDER_Cycles(Integer.valueOf(cycles));
					break;
				case "I-CACHE":
					cManager.setICACHE_NUM_BLOCKS(Integer.valueOf(units));
					cManager.setICACHE_BLOCK_SIZE(Integer.valueOf(cycles));
					break;
				}
			}


		}
	}


}
