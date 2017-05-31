package mySimulator;

public class RclockTime {
	private long cCycles;
	public RclockTime(){
	cCycles = 1;	
	}
	public void moveForward(){
		cCycles++;
	}
	public long count(){
		return cCycles;
	}
}
