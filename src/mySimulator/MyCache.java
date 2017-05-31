package mySimulator;

import java.util.HashMap;

public class MyCache {
	

	public class ClockCycle {
		private long cycles;
		public ClockCycle(){
		cycles = 1;	
		}
		public void moveForward(){
			cycles++;
		}
		public long count(){
			return cycles;
		}
	}
	public class ICache {
		long bOffset;
		int offsetcount;
		int requests;
		int hits;
		private int numberOfBlocks;
		private int bSize;
		HashMap<Integer,Integer> cache;
		RclockTime c;
		MyBus bus;
		
		public ICache(int numBlocks, int blockSize, RclockTime clock, MyBus bus){
			this.numberOfBlocks = numBlocks;
			this.bSize = blockSize;
			this.c = clock;
			this.bus = bus;
			cache = new HashMap<Integer, Integer>();
			updateoffset();
			hits = 0;
			requests = 0;
		}
		
		 
		private void updateoffset(){
			int tBlockSize = this.bSize;
			this.bOffset = 0;
			this.offsetcount = 0;
			
			while(tBlockSize != 0){
				tBlockSize = tBlockSize/2;
				this.bOffset = this.bOffset << 1;
				this.bOffset = this.bOffset | 1;
				this.offsetcount++;
			}
			this.bOffset = this.bOffset >> 1;
			this.offsetcount = this.offsetcount -1;
		}
		public int fettchThisInstruction(long addr){
			int blockNumber = (int) ((addr >> offsetcount)%this.numberOfBlocks);
			int tag = (int) (addr >> offsetcount);
			this.requests++;
			if(cache.containsKey(blockNumber)){
				if(cache.get(blockNumber) == tag){
					this.hits++;
					return 1;
				}else{
					cache.put(blockNumber,tag);
					return numClockCyclesNeed(this.bSize*3 )+1;	
				}
			}else{
				cache.put(blockNumber,tag);
				return numClockCyclesNeed(this.bSize*3 )+1;
			}
		}
		public String getMissHits(){
			String result = "Total number of access requests for instruction cache: "+this.requests;
			String hits = System.lineSeparator()+"Number of instruction cache hits: "+this.hits;
			String finalResult = result+System.lineSeparator()+hits;
			return finalResult;
		}
		int numClockCyclesNeed(int clockcyclestake){
			if(bus.checkBusStatus(c.count()) == false){
				bus.busBusyFor(c.count()+clockcyclestake);
				return clockcyclestake;
			}else{
				int busyCount = (int)((bus.getBusfreeCycle() - c.count())+clockcyclestake);
				bus.busBusyFor(bus.getBusfreeCycle()+clockcyclestake);
				return busyCount;
			}
		}
		
		
	}
	public class MyBus {
		private long checkBusStatus;
		public MyBus(){
			checkBusStatus = 0;
		}
		
		public boolean checkBusStatus(long cCount){
			if(checkBusStatus > cCount){
				return true;
			}
			return false;
		}
		
		public void busBusyFor(long cCount){
			checkBusStatus = cCount;
		}
		public long getBusfreeCycle(){
			return checkBusStatus;
		}
	}
	public class DCache {
		
		int hits;
		int requests;
		String cache_array[][];
		MyMemory mem;
		int cSize;
		int bSize;
		int way ;
		int v_bits[];
		int countLru[];
		int tag[];	
		int d_bit[];
		RclockTime c;
		MyBus bus;
		public class DCacheBundle{
			public String data;
			public int cCycles;
			DCacheBundle(String data, int clockCycles){
				this.data = data;
				this.cCycles = clockCycles;
			}
		}
		public DCache(RclockTime clock, MyBus bus, String dataFilePath){
			this.c = clock;
			this.bus = bus;
			way = 2;
			cSize = 4;
			bSize = 4;
			v_bits = new int[cSize];
			countLru = new int[cSize];
			d_bit = new int[cSize];
			tag = new int[cSize];		
			cache_array = new String[cSize][];
			
			for(int i =0 ; i < cSize; i++){
			  cache_array[i] = new String[bSize];
			}
			mem = new MyMemory(dataFilePath);
			requests = 0;
			hits = 0;
		}
		public DCacheBundle getFetchData(long address){
			long o_address = address;
			address = address >> 2;
			long blockOffsetMask = 3;
			int blockOffset = (int)(address & blockOffsetMask);
			long addr = address >> 2;
			int setNum = (int)(addr %2);
			this.requests++;
			int position = setNum * this.way;
			int t_countLru[] = new int[4];
			t_countLru[position] = this.countLru[position];
			t_countLru[position+1] = this.countLru[position+1];
			this.countLru[position] = 0;
			this.countLru[position+1] = 0;
			if(this.v_bits[position] == 1 && this.tag[position] == addr){
				this.countLru[position] = 1;
				this.hits++;
				return new DCacheBundle(cache_array[position][blockOffset],1);
			}
			if(this.v_bits[position+1] == 1 && this.tag[position+1] == addr){
				this.countLru[position+1] = 1;
				this.hits++;
				return new DCacheBundle(cache_array[position+1][blockOffset],1);
			}
			
			String data[] = mem.getDataFromAddress(o_address);
			
			if(this.v_bits[position] == 0){
				this.countLru[position] = 1;
				this.d_bit[position] = 0;
				this.v_bits[position] = 1;
				
				this.tag[position] = (int)addr;
				for(int i = 0 ; i < data.length; i++){
					cache_array[position][i] = data[i];
				}
				return new DCacheBundle(cache_array[position][blockOffset],clockCyclesRequired(12)+1);
			}
			if(this.v_bits[position+1] == 0){
				this.v_bits[position+1] = 1;
				this.d_bit[position+1] = 0;
				this.countLru[position+1] = 1;
				this.tag[position+1] = (int)addr;
				for(int i = 0 ; i < data.length; i++){
					cache_array[position+1][i] = data[i];
				}
				return new DCacheBundle(cache_array[position+1][blockOffset],clockCyclesRequired(12)+1);
			}
			
			
			if(t_countLru[position] == 0){
				this.v_bits[position] = 1;
				this.countLru[position] = 1;
				int extraCycles = 0;
				if(this.d_bit[position] == 1){
					extraCycles = 12;
					mem.updateData(o_address, cache_array[position]);
				}
				this.d_bit[position] = 0;
				this.tag[position] = (int)addr;
				for(int i = 0 ; i < data.length; i++){
					cache_array[position][i] = data[i];
				}
				return new DCacheBundle(cache_array[position][blockOffset],clockCyclesRequired(12+extraCycles)+1);
			}
			if(t_countLru[position+1] == 0){
				this.v_bits[position+1] = 1;
				this.countLru[position+1] = 1;
				this.tag[position+1] = (int)addr;
				int extraCycles = 0;
				if(this.d_bit[position+1] == 1){
					extraCycles = 12;
					mem.updateData(o_address, cache_array[position]);
				}
				this.d_bit[position+1] = 0;
				for(int i = 0 ; i < data.length; i++){
					cache_array[position+1][i] = data[i];
				}
				return new DCacheBundle(cache_array[position+1][blockOffset],clockCyclesRequired(12+extraCycles)+1);
			}
			return null;
		}
		int clockCyclesRequired(int clockCycles){
			if(bus.checkBusStatus(c.count()) == false){
				bus.busBusyFor(c.count()+clockCycles);
				return clockCycles;
			}else{
				int busyCount = (int)((bus.getBusfreeCycle() - c.count())+clockCycles);
			    bus.busBusyFor(bus.getBusfreeCycle()+clockCycles);
				return busyCount;
			}
		}
		public String getMissHits(){
			String requests = "Total number of access requests for data cache: "+this.requests;
			String hits = System.lineSeparator()+"Number of data cache hits: "+this.hits;
			String myResult = requests+System.lineSeparator()+hits;
			return myResult;
		}
		public int updatingAtAddress(long address, String data){
			long oldaddress = address;
			address = address >> 2;
			long blockOffsetMask = 3;
			int blockOffset = (int)(address & blockOffsetMask);
			long addr = address >> 2;
			int setNum = (int)(addr %2);
			this.requests++;
			//checking the hit or not
			int position = setNum * this.way;
			int templrucounter[] = new int[4];
			templrucounter[position] = this.countLru[position];
			templrucounter[position+1] = this.countLru[position+1];
			this.countLru[position] = 0;
			this.countLru[position+1] = 0;
			//checking whether it is existed in the cache or not
			if(this.v_bits[position] == 1 && this.tag[position] == addr){
				this.countLru[position] = 1;
				this.d_bit[position] = 1;
				this.hits++;
				cache_array[position][blockOffset] = data;
				return 1;
			}
			if(this.v_bits[position+1] == 1 && this.tag[position+1] == addr){
				this.countLru[position+1] = 1;
				this.d_bit[position+1] = 1;
				cache_array[position+1][blockOffset] = data;
				this.hits++;
				return 1;
			}
	
			String newdata[] = mem.updateAtAddress(oldaddress, blockOffset,data);
			if(this.v_bits[position] == 0){
				this.v_bits[position] = 1;
				this.countLru[position] = 1;
				this.d_bit[position] = 0;
				this.tag[position] = (int)addr;
				for(int i = 0 ; i < newdata.length; i++){
					cache_array[position][i] = newdata[i];
				}
				return clockCyclesRequired(12)+1;			
			}
			if(this.v_bits[position+1] == 0){
				this.v_bits[position+1] = 1;
				this.countLru[position+1] = 1;
				this.d_bit[position+1] = 0;
				this.tag[position+1] = (int)addr;
				for(int i = 0 ; i < newdata.length; i++){
					cache_array[position+1][i] = newdata[i];
				}
				return clockCyclesRequired(12)+1;	
			}
			if(templrucounter[position] == 0){
				this.v_bits[position] = 1;
				this.countLru[position] = 1;
				this.tag[position] = (int)addr;
				int extraCycles = 0;
				if(this.d_bit[position] == 1){
					extraCycles = 12;
					mem.updateData(oldaddress, cache_array[position]);
				}
				this.d_bit[position] = 0;
				for(int i = 0 ; i < newdata.length; i++){
					cache_array[position][i] = newdata[i];
				}
				return clockCyclesRequired(12+extraCycles)+1;
			}
			if(templrucounter[position+1] == 0){
				this.v_bits[position+1] = 1;
				this.countLru[position+1] = 1;
				this.tag[position+1] = (int)addr;
				int extraCycles = 0;
				if(this.d_bit[position+1] == 1){
					mem.updateData(oldaddress, cache_array[position+1]);
					extraCycles =12;
				}
				this.d_bit[position+1] = 0;
				for(int i = 0 ; i < newdata.length; i++){
					cache_array[position+1][i] = newdata[i];
				}
				return clockCyclesRequired(12+extraCycles)+1;
			}
			return -1;
		}	
		public void writeToMem(){
			 	for(int i = 0 ; i < 4; i++){
						if(d_bit[i] == 1){
			 			int tag_val = tag[i];
			 			int address = tag_val << 4;
			 			mem.updateData(address,cache_array[i]);
			 		}
			 	}
			 	}
			 	public void writeMemToFile(){
			 		mem.writingToFile();
			 	}
	
		
	}


}
