package usageInstructions;

public class ConfigurationSetup {
	
	private int fpAddUnits = 0;
	private int fpAddCycles = 0;

	private int fpMulUnits = 0;
	private int fpMulCycles = 0;
	
	private int fpDivUnits = 0;
	private int fpDivCycles = 0;
	
	private int icacheBlocks = 0;
	private int icacheBlockSize = 0;
	
	private static ConfigurationSetup cManager = null;
	
	public static ConfigurationSetup getSingletonObj() {
		if(cManager== null){
			cManager = new ConfigurationSetup();
		}
		return cManager;

	}
	
	public int getFPADDER_Units() {
		return fpAddUnits;
	}
	public void setFPADDER_Units(int fPADDER_Units) {
		fpAddUnits = fPADDER_Units;
	}
	public int getFPADDER_Cycles() {
		return fpAddCycles;
	}
	public void setFPADDER_Cycles(int fPADDER_Cycles) {
		fpAddCycles = fPADDER_Cycles;
	}
	public int getFPMULTIPLIER_Units() {
		return fpMulUnits;
	}
	public void setFPMULTIPLIER_Units(int fPMULTIPLIER_Units) {
		fpMulUnits = fPMULTIPLIER_Units;
	}
	public int getFPMULTIPLIER_Cycles() {
		return fpMulCycles;
	}
	public void setFPMULTIPLIER_Cycles(int fPMULTIPLIER_Cycles) {
		fpMulCycles = fPMULTIPLIER_Cycles;
	}
	public int getFPDIVIDER_Units() {
		return fpDivUnits;
	}
	public void setFPDIVIDER_Units(int fPDIVIDER_Units) {
		fpDivUnits = fPDIVIDER_Units;
	}
	public int getFPDIVIDER_Cycles() {
		return fpDivCycles;
	}
	public void setFPDIVIDER_Cycles(int fPDIVIDER_Cycles) {
		fpDivCycles = fPDIVIDER_Cycles;
	}
	public int getICACHE_NUM_BLOCKS() {
		return icacheBlocks;
	}
	public void setICACHE_NUM_BLOCKS(int iCACHE_NUM_BLOCKS) {
		icacheBlocks = iCACHE_NUM_BLOCKS;
	}
	public int getICACHE_BLOCK_SIZE() {
		return icacheBlockSize;
	}
	public void setICACHE_BLOCK_SIZE(int iCACHE_BLOCK_SIZE) {
		icacheBlockSize = iCACHE_BLOCK_SIZE;
	}
	
	
}
