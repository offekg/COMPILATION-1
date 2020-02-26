package IR;

import MIPS.sir_MIPS_a_lot;
import TEMP.*;

public class IRcommand_StoreReturnValueOnStack extends IRcommand{
	
	public TEMP t;
	
	public IRcommand_StoreReturnValueOnStack(TEMP t)
	{
		this.t = t;
        
	}

	
	public void MIPSme() {
		

		//sir_MIPS_a_lot.getInstance().storeReturnValueONStack(t);

	}
	
	@Override
	public void printMe() {
		System.out.println("v0 = " + t.getSymbol());
	}
}