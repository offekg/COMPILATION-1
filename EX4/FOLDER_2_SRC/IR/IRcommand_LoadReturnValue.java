package IR;

import TEMP.*;

public class IRcommand_LoadReturnValue extends IRcommand{
	
	public TEMP t;
	
	public IRcommand_LoadReturnValue(TEMP t)
	{
		this.t = t;
        
	}

	
	public void MIPSme() {
		

		//sir_MIPS_a_lot.getInstance().storeReturnValueONStack(t);

	}
	
	@Override
	public void printMe() {
		System.out.println(t.getSymbol() + " = v0");
	}

}