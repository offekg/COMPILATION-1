package IR;

import MIPS.sir_MIPS_a_lot;
import TEMP.*;

public class IRcommand_LoadReturnValue extends IRcommand{
	
	public TEMP dst;
	
	public IRcommand_LoadReturnValue(TEMP t)
	{
		this.dst = t;
        
	}

	
	public void MIPSme() {
		

		sir_MIPS_a_lot.getInstance().loadReturnValue(dst);

	}
	
	@Override
	public void printMe() {
		System.out.println(dst.getSymbol() + " = v0");
	}

}