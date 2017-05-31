package usageInstructions;

import java.util.ArrayList;
import java.util.List;

public abstract class ImmArg3 extends InstructionD{
	
	public SourceRegisterValue source1 = null;
	public int immediate ;
	public DestinationRegisterValue dest = null;

	public ImmArg3(String d, String s1, String imm) {
		this.dest = new DestinationRegisterValue(d, 0);
		this.source1 = new SourceRegisterValue(s1, 0);
		this.immediate = Integer.valueOf(imm);
	}
	
	@Override
	public DestinationRegisterValue getDestinationReg() {
		return this.dest;
	}

	@Override
	public List<SourceRegisterValue> getSourceReg() {
		List<SourceRegisterValue> sourcelist = new ArrayList<SourceRegisterValue>();
		sourcelist.add(this.source1);
		return sourcelist;
	}

}
