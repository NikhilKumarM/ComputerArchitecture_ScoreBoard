package usageInstructions;

import java.util.List;

public interface InstructionInterface {
	
	public void execInst();
	public DestinationRegisterValue getDestinationReg();
	public List<SourceRegisterValue> getSourceReg();

}
