package usageInstructions;

public class DestinationRegisterValue {
	
	private String destination = "";
	private int value = 0;
	
	public DestinationRegisterValue(String des, int val){
		this.destination = des;
		this.value = val;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	

}
