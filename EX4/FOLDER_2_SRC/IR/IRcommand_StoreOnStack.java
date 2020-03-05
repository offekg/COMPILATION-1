package IR;

import MIPS.sir_MIPS_a_lot;
import TEMP.*;

public class IRcommand_StoreOnStack extends IRcommand{
	public TEMP t;
	public int offset;
	
	public IRcommand_StoreOnStack(TEMP t, int offset)
	{
		this.t = t;
		this.offset = offset;
	}

	
	public void MIPSme() 
	{
		sir_MIPS_a_lot.getInstance().storeOnStack(t, offset);
	}
	
	@Override
	public void printMe() {
		System.out.println("store on stack " + t.getSymbol() + " offset " + offset);
	}
}