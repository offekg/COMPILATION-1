package IR;

import MIPS.sir_MIPS_a_lot;
import TEMP.*;

public class IRcommand_StoreReturnValue extends IRcommand{
	
	public TEMP tReturn;
	
	public IRcommand_StoreReturnValue(TEMP t)
	{
		this.tReturn = t;
        
	}

	
	public void MIPSme() {
		

		sir_MIPS_a_lot.getInstance().storeReturnValue(tReturn);

	}
	
	@Override
	public void printMe() {
		System.out.println("v0 = " + tReturn.getSymbol());
	}
}