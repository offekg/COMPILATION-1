package IR;

import MIPS.sir_MIPS_a_lot;
import TEMP.*;

public class IRcommand_Binop_Concat_Strings extends IRcommand {
	public TEMP t1;
	public TEMP t2;
	public TEMP dst;
	
	public IRcommand_Binop_Concat_Strings(TEMP dst,TEMP t1,TEMP t2)
	{
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;
	}
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		//temps for going over the strings
		TEMP tChar = TEMP_FACTORY.getInstance().getFreshTEMP();
        TEMP srcPointer = TEMP_FACTORY.getInstance().getFreshTEMP();
        TEMP dstStrPointer = TEMP_FACTORY.getInstance().getFreshTEMP();
        TEMP tLength = TEMP_FACTORY.getInstance().getFreshTEMP();
		
        //labels for inner calculations
        String s1_len_calc_label = getFreshLabel("Length_String1_Calc");
        String s2_len_calc_label = getFreshLabel("Length_String2_Calc");
        String s1_concat_loop = getFreshLabel("String1_Concat");
        String s2_concat_loop = getFreshLabel("String2_Concat");
        
        //compute total strings lengths
        sir_MIPS_a_lot.getInstance().li(tLength, 1); 
        sir_MIPS_a_lot.getInstance().move(srcPointer, t1);
        sir_MIPS_a_lot.getInstance().add_str_length(tLength, tChar, srcPointer, s1_len_calc_label);
        sir_MIPS_a_lot.getInstance().move(srcPointer, t2);
        sir_MIPS_a_lot.getInstance().add_str_length(tLength, tChar, srcPointer, s2_len_calc_label);
        
        //malloc new concated string
        sir_MIPS_a_lot.getInstance().malloc(dst, tLength);
        
        //concat both strings into new space in dst
        sir_MIPS_a_lot.getInstance().move(dstStrPointer, dst);
        
        //string1
        sir_MIPS_a_lot.getInstance().move(srcPointer,t1);
        sir_MIPS_a_lot.getInstance().label(s1_concat_loop);
        sir_MIPS_a_lot.getInstance().lb(tChar,0,srcPointer);
        sir_MIPS_a_lot.getInstance().sb(tChar,0,dstStrPointer);
        sir_MIPS_a_lot.getInstance().addi(dstStrPointer, dstStrPointer, 1);
        sir_MIPS_a_lot.getInstance().addi(srcPointer, srcPointer, 1);
        sir_MIPS_a_lot.getInstance().bnez(tChar, s1_concat_loop);
        sir_MIPS_a_lot.getInstance().addi(dstStrPointer, dstStrPointer, -1); //so there won't be a space between
        
        //string2
        sir_MIPS_a_lot.getInstance().move(srcPointer,t2);
        sir_MIPS_a_lot.getInstance().label(s2_concat_loop);
        sir_MIPS_a_lot.getInstance().lb(tChar,0,srcPointer);
        sir_MIPS_a_lot.getInstance().sb(tChar,0,dstStrPointer);
        sir_MIPS_a_lot.getInstance().addi(dstStrPointer, dstStrPointer, 1);
        sir_MIPS_a_lot.getInstance().addi(srcPointer, srcPointer, 1);
        sir_MIPS_a_lot.getInstance().bnez(tChar, s2_concat_loop);
        sir_MIPS_a_lot.getInstance().addi(dstStrPointer, dstStrPointer, -1); //so there won't be a space between
            
	}
	
	@Override
	public void printMe() {
		System.out.println(dst.getSymbol() + " = concat " + t1.getSymbol()
							+ ", " + t2.getSymbol());
	}
}

