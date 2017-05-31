package usageInstructions;

import java.util.ArrayList;
import java.util.List;

public abstract class STInstruction extends InstructionD{
	public SourceRegisterValue source1;
	public SourceRegisterValue source2;
	public int immediate;
	
	public STInstruction(String s1, String s2, String imm){
		this.source1 = new SourceRegisterValue(s1, 0);
		this.source2 = new SourceRegisterValue(s2, 0);
		this.immediate = Integer.valueOf(imm);
	}
	
	@Override
	public DestinationRegisterValue getDestinationReg() {
		return null;
		
	}
	
	@Override
	public List<SourceRegisterValue> getSourceReg() {
		List<SourceRegisterValue> sourcelist = new ArrayList<SourceRegisterValue>();
		sourcelist.add(this.source1);
		sourcelist.add(this.source2);
		return sourcelist;
	}

	

}
