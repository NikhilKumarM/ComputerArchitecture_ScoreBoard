package mySimulator;

import java.util.ArrayList;
import java.util.List;

import usageInstructions.Arg3Instructions;
import usageInstructions.ImmArg3;
import usageInstructions.InstructionD;
import usageInstructions.DestinationRegisterValue;
import usageInstructions.SourceRegisterValue;
import usageInstructions.STInstruction;


public class MyInstructions {
	public static class BEQ extends InstructionD{

		private SourceRegisterValue source1;
		private SourceRegisterValue source2;
		private String branchToLabel;
		
		public BEQ(String s1, String s2, String label) {
			this.source1 = new SourceRegisterValue(s1, 0);
			this.source2 = new SourceRegisterValue(s2, 0);
			this.branchToLabel = label;
			this.branchlabellOut =label;
			this.iType = TypeOfInstruction.ConditionalBranch;
			this.fType = TypeOfFunctionalUnit.BRANCHUNIT;
		}
		
		public boolean getComparedResult(){
			return (source1.getValue() == source2.getValue());
		}
		
		public String getBranchToLabel(){
			return this.branchToLabel;
		}
		
		@Override
		public void execInst() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public DestinationRegisterValue getDestinationReg() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<SourceRegisterValue> getSourceReg() {
			List<SourceRegisterValue> sourcelist = new ArrayList<SourceRegisterValue>();
			sourcelist.add(this.source1);
			sourcelist.add(this.source2);
			return sourcelist;
		}
		
		@Override
		public String toString() {
			String result = "BEQ " + " " + source1.getSource() + ", "+ source2.getSource() + ", " + branchToLabel;
			return result;
		}

	}
	

	public static class BNE extends InstructionD{

		private SourceRegisterValue source1;
		private SourceRegisterValue source2;
		private String branchToLabel;
		
		public BNE(String s1, String s2, String label) {
			this.source1 = new SourceRegisterValue(s1, 0);
			this.source2 = new SourceRegisterValue(s2, 0);
			this.branchToLabel = label;
			this.iType = TypeOfInstruction.ConditionalBranch;
			this.fType = TypeOfFunctionalUnit.BRANCHUNIT;
		}
		
		public boolean getComparedResult(){
			return (source1.getValue() != source2.getValue());
		}
		
		public String getBranchToLabel(){
			return this.branchToLabel;
		}
		
		@Override
		public void execInst() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public DestinationRegisterValue getDestinationReg() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<SourceRegisterValue> getSourceReg() {
			List<SourceRegisterValue> sourcelist = new ArrayList<SourceRegisterValue>();
			sourcelist.add(this.source1);
			sourcelist.add(this.source2);
			return sourcelist;
		}
		
		@Override
		public String toString() {
			String result = "BNE " + " " + source1.getSource() + ", "+ source2.getSource() + ", " + branchToLabel;
			return result;
		}

	}
	
	public static class LUI extends InstructionD{

		private DestinationRegisterValue dest;
		private int immediate;

		public LUI(String dest, String imm) {
			this.dest = new DestinationRegisterValue(dest, 0);
			this.immediate = Integer.valueOf(imm);
			this.fType = TypeOfFunctionalUnit.INTEGERUNIT;
		}
		
		@Override
		public void execInst() {
			this.dest.setValue((int)(Math.pow(2, 16)*this.immediate));
		}

		@Override
		public DestinationRegisterValue getDestinationReg() {
			return dest;
		}

		@Override
		public List<SourceRegisterValue> getSourceReg() {
			// TODO Auto-generated method stub
			return null;
		}
		
		@Override
		public String toString() {
			String result = "LUI "+dest.getDestination()+","+immediate;
			return result;
		}

	}
	public static class DADD extends Argument3{

		public DADD(String d, String s1, String s2) {
			super(d, s1, s2);
			this.iType = TypeOfInstruction.NArithmetic;
			this.fType = TypeOfFunctionalUnit.INTEGERUNIT;
		}

		@Override
		public void execInst() {
			dest.setValue(source1.getValue()+source2.getValue());
		}
		
		@Override
		public String toString() {
			String result = "DADD "+dest.getDestination()+","+source1.getSource()+","+source2.getSource();
			return result;
		}

	}
	public static class DADDI extends ImmArg3{

		public DADDI(String d, String s1, String imm) {
			super(d, s1, imm);
			this.iType = TypeOfInstruction.NArithmetic;
			this.fType = TypeOfFunctionalUnit.INTEGERUNIT;
		}

		@Override
		public void execInst() {
			dest.setValue(source1.getValue()+immediate);
		}
		
		@Override
		public String toString() {
			String result = "DADDI "+dest.getDestination()+","+source1.getSource()+","+immediate;
			return result;
		}

	}

	public static class DIVD extends Argument3{

		public DIVD(String d, String s1, String s2) {
			super(d, s1, s2);
			this.iType = TypeOfInstruction.FDIV;
			this.fType = TypeOfFunctionalUnit.FPDIVUNIT;
		}

		@Override
		public void execInst() {
			dest.setValue(source1.getValue()/source2.getValue());
			
		}
		
		@Override
		public String toString() {
			String result = "DIV.D "+dest.getDestination()+","+source1.getSource()+","+source2.getSource();
			return result;
		}

	}
	
	public static class DSUB extends Argument3{

		public DSUB(String d, String s1, String s2) {
			super(d, s1, s2);
			this.iType = TypeOfInstruction.NArithmetic;
			this.fType = TypeOfFunctionalUnit.INTEGERUNIT;
		}

		@Override
		public void execInst() {
			dest.setValue(source1.getValue()-source2.getValue());
			
		}
		
		@Override
		public String toString() {
			String result = "DSUB "+dest.getDestination()+","+source1.getSource()+","+source2.getSource();
			return result;
		}

	}

	public static class DSUBI extends ImmArg3{

		public DSUBI(String d, String s1, String imm) {
			super(d, s1, imm);
			this.fType = TypeOfFunctionalUnit.INTEGERUNIT;
			this.iType = TypeOfInstruction.NArithmetic;
		}

		@Override
		public void execInst() {
			dest.setValue(source1.getValue()-immediate);
		}

		@Override
		public String toString() {
			String result = "DSUBI "+dest.getDestination()+","+source1.getSource()+","+immediate;
			return result;
		}
		
	}
	public static class HLT extends InstructionD{
		
		public HLT() {
			this.iType = TypeOfInstruction.HALT;
			this.fType = TypeOfFunctionalUnit.MISLEANEOUS; 
		}

		@Override
		public void execInst() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public DestinationRegisterValue getDestinationReg() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<SourceRegisterValue> getSourceReg() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "HLT";
		}
	}

	public static class J extends InstructionD{
		
		String jumpTo;

		public J(String destLabel) {
			this.jumpTo = destLabel;
		}
		
		
		public String getJumpTo() {
			return jumpTo;
		}


		@Override
		public String toString() {
			return "J "+this.jumpTo;
		}
		@Override
		public void execInst() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public DestinationRegisterValue getDestinationReg() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<SourceRegisterValue> getSourceReg() {
			// TODO Auto-generated method stub
			return null;
		}

	}

	public static class LD extends ImmArg3{

		public LD(String d, String s1, String imm) {
			super(d, s1, imm);
			this.iType = TypeOfInstruction.LSD;
			this.fType = TypeOfFunctionalUnit.LSU;
		}

		@Override
		public void execInst() {
			this.addressValue = source1.getValue()+immediate;
		}

		@Override
		public String toString() {
			String result = "LD "+dest.getDestination()+","+immediate+"("+source1.getSource()+")";
			return result;
		}
	}
	public static class LI extends InstructionD{
		
		private DestinationRegisterValue dest;
		private int immediate;

		public LI(String dest, String imm) {
			this.dest = new DestinationRegisterValue(dest, 0);
			this.immediate = Integer.valueOf(imm);
			this.fType = TypeOfFunctionalUnit.INTEGERUNIT;
		}
		
		@Override
		public void execInst() {
			this.dest.setValue(this.immediate);
			
		}

		@Override
		public String toString() {
			String result = "LI "+dest.getDestination()+","+immediate;
			return result;
		}
		
		@Override
		public DestinationRegisterValue getDestinationReg() {
			// TODO Auto-generated method stub
			return dest;
		}

		@Override
		public List<SourceRegisterValue> getSourceReg() {
			// TODO Auto-generated method stub
			return null;
		}

	}

	public static class LW extends ImmArg3{

		public LW(String d, String s1, String imm) {
			super(d, s1, imm);
			this.iType = TypeOfInstruction.LSW;
			this.fType = TypeOfFunctionalUnit.LSU;
		}

		@Override
		public void execInst() {
			this.addressValue = immediate+source1.getValue();
		}

		@Override
		public String toString() {
			String result = "LW "+dest.getDestination()+","+immediate+"("+source1.getSource()+")";
			return result;
		}
	}

	public static class MULD extends Argument3{

		public MULD(String d, String s1, String s2) {
			super(d, s1, s2);
			this.iType = TypeOfInstruction.FMUL;
			this.fType = TypeOfFunctionalUnit.FPMULTUNIT;
		}

		@Override
		public void execInst() {
			dest.setValue(source1.getValue()*source2.getValue());
		}
		
		@Override
		public String toString() {
			String result = "MUL.D "+dest.getDestination()+","+source1.getSource()+","+source2.getSource();
			return result;
		}

	}

	public static class OR extends Argument3{

		public OR(String d, String s1, String s2) {
			super(d, s1, s2);
			this.iType = TypeOfInstruction.NArithmetic;
			this.fType = TypeOfFunctionalUnit.INTEGERUNIT;
		}

		@Override
		public void execInst() {
			dest.setValue(source1.getValue() | source2.getValue());		
		}
		
		@Override
		public String toString() {
			String result = "OR "+dest.getDestination()+","+source1.getSource()+","+source2.getSource();
			return result;
		}

	}

	public static class ORI extends ImmArg3{

		public ORI(String d, String s1, String imm) {
			super(d, s1, imm);
			this.iType = TypeOfInstruction.NArithmetic;
			this.fType = TypeOfFunctionalUnit.INTEGERUNIT;
		}

		@Override
		public void execInst() {
			dest.setValue(source1.getValue() | immediate);
		}

		@Override
		public String toString() {
			String result = "ORI "+dest.getDestination()+","+source1.getSource()+","+immediate;
			return result;
		}
	}

	public static class SD extends STInstruction{

		public SD(String s1, String s2, String imm) {
			super(s1, s2, imm);
			this.iType = TypeOfInstruction.LSD;
			this.fType = TypeOfFunctionalUnit.LSU;
		}

		@Override
		public void execInst() {
			this.addressValue = this.immediate+ this.source2.getValue();
		}
		
		public SourceRegisterValue getValuetowriteToMemory(){
			return this.source1;
		}

		@Override
		public String toString() {
			String result = "SD "+source1.getSource()+","+immediate+"("+source2.getSource()+")";
			return result;
		}
	}

	public static class SUBD extends Argument3{

		public SUBD(String des, String source1, String source2) {
			super(des, source1, source2);
			this.fType = TypeOfFunctionalUnit.FPADDUNIT;
			this.iType = TypeOfInstruction.FADDNSUB;
		}

		@Override
		public void execInst() {
			dest.setValue(source1.getValue()-source2.getValue());
			
		}
		
		@Override
		public String toString() {
			String result = "SUB.D "+dest.getDestination()+","+source1.getSource()+","+source2.getSource();
			return result;
		}

	}

	public static class SW extends STInstruction{

		public SW(String s1, String s2, String imm) {
			super(s1, s2, imm);
			this.iType = TypeOfInstruction.LSW;
			this.fType = TypeOfFunctionalUnit.LSU;
		}
		
		@Override
		public void execInst() {
			this.addressValue = this.immediate+ this.source2.getValue();
		}
		
		public SourceRegisterValue getValuetowriteToMemory(){
			return this.source1;
		}

		@Override
		public String toString() {
			String result = "SW "+source1.getSource()+","+immediate+"("+source2.getSource()+")";
			return result;
		}
	}
	public static class ANDI extends ImmArg3{

		public ANDI(String d, String s1, String imm) {
			super(d, s1, imm);
			this.iType = TypeOfInstruction.NArithmetic;
			this.fType = TypeOfFunctionalUnit.INTEGERUNIT;
		}

		@Override
		public void execInst() {
			dest.setValue(source1.getValue() & immediate);
		}
		
		@Override
		public String toString() {
			String result = "ANDI "+dest.getDestination()+","+source1.getSource()+","+immediate;
			return result;
		}

	}
	public static abstract class Argument3 extends InstructionD{
		
		public SourceRegisterValue source1 = null;
		public SourceRegisterValue source2 = null;
		public DestinationRegisterValue dest = null;
		
		public Argument3 (String d, String s1, String s2){
			this.source1 = new SourceRegisterValue(s1,0);
			this.source2 = new SourceRegisterValue(s2,0);
			this.dest = new DestinationRegisterValue(d,0);
		}

		@Override
		public DestinationRegisterValue getDestinationReg() {
			return this.dest;
			
		}

		@Override
		public List<SourceRegisterValue> getSourceReg() {
			List<SourceRegisterValue> sourcelist = new ArrayList<SourceRegisterValue>();
			sourcelist.add(this.source1);
			sourcelist.add(this.source2);
			return sourcelist;
		}

	}

	public static class ADDD extends Argument3{
		
		public ADDD(String destination, String source1, String source2){
			super(destination,source1,source2);
			this.fType = TypeOfFunctionalUnit.FPADDUNIT;
			this.iType = TypeOfInstruction.FADDNSUB;
			
		}

		@Override
		public void execInst() {
			dest.setValue(source1.getValue()+source2.getValue());
			
		}
		
		@Override
		public String toString() {
			String result = "ADD.D "+dest.getDestination()+","+source1.getSource()+","+source2.getSource();
			return result;
		}

	}
	public static class AND extends Argument3{

		public AND(String d, String s1, String s2) {
			super(d, s1, s2);
			this.iType = TypeOfInstruction.NArithmetic;
			this.fType = TypeOfFunctionalUnit.INTEGERUNIT;
		}

		@Override
		public void execInst() {
			dest.setValue(source1.getValue() & source2.getValue());
		}
		
		@Override
		public String toString() {
			String result = "AND "+dest.getDestination()+","+source1.getSource()+","+source2.getSource();
			return result;
		}

}
}
