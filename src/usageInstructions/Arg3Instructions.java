package usageInstructions;

import java.util.ArrayList;
import java.util.List;

public abstract class Arg3Instructions extends InstructionD{
	
	public SourceRegisterValue source1 = null;
	public SourceRegisterValue source2 = null;
	public DestinationRegisterValue dest = null;
	
	public Arg3Instructions (String d, String s1, String s2){
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
