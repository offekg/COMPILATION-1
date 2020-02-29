/***********/
/* PACKAGE */
/***********/
package MIPS;

/*******************/
/* GENERAL IMPORTS */
/*******************/

import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;

import IR.IRcommand;
/*******************/
/* PROJECT IMPORTS */
/*******************/
import TEMP.*;
import UTILS.Context;

public class sir_MIPS_a_lot {
	private int WORD_SIZE = 4;
	/***********************/
	/* The file writer ... */
	/***********************/
	private PrintWriter fileWriter;

	/***********************/
	/* The file writer ... */
	/***********************/
	public void finalizeFile() {
		label("exit");
		fileWriter.print("\tli $v0,10\n");
		fileWriter.print("\tsyscall\n");
		fileWriter.close();
	}

	public void print_int(TEMP t) {
		int idx = t.getSerialNumber();
		// fileWriter.format("\taddi $a0,Temp_%d,0\n",idx);
		fileWriter.format("\tmove $a0,Temp_%d\n", idx);
		fileWriter.format("\tli $v0,1\n");
		fileWriter.format("\tsyscall\n");
		fileWriter.format("\tli $a0,32\n");
		fileWriter.format("\tli $v0,11\n");
		fileWriter.format("\tsyscall\n");
		// print space after int is printed
		fileWriter.format("\tla $a0,space\n");
		fileWriter.format("\tli $v0,4\n"); // print string syscall num is 4
		fileWriter.format("\tsyscall\n");
	}

	public void print_string(TEMP t) {
		int idx = t.getSerialNumber();
		fileWriter.format("\tmove $a0,Temp_%d\n", idx);
		fileWriter.format("\tli $v0,4\n"); // print string syscall num is 4
		fileWriter.format("\tsyscall\n");
	}

	// public TEMP addressLocalVar(int serialLocalVarNum)
	// {
	// TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
	// int idx = t.getSerialNumber();
	//
	// fileWriter.format("\taddi
	// Temp_%d,$fp,%d\n",idx,-serialLocalVarNum*WORD_SIZE);
	//
	// return t;
	// }
	public void allocate(String var_name) {
		fileWriter.format(".data\n");
		fileWriter.format("\tglobal_%s: .word 721\n", var_name);
	}

	public void load(TEMP dst, String var_name) {
		int idxdst = dst.getSerialNumber();
		fileWriter.format("\tlw Temp_%d,global_%s\n", idxdst, var_name);
	}

	public void loadLocalVar(TEMP dst, int var_offset) {
		int idxdst = dst.getSerialNumber();
		fileWriter.format("\tlw Temp_%d,%d($fp)\n", idxdst, -(var_offset * WORD_SIZE));
	}

	public void lw(TEMP dst, TEMP src, int offset) {
		int idxdst = dst.getSerialNumber();
		int idxsrc = src.getSerialNumber();
		fileWriter.format("\tlw Temp_%d,%d(Temp_%d)\n", idxdst, offset, idxsrc);
	}
	
	public void lw(TEMP dst, String label) {
		int idxdst = dst.getSerialNumber();
		fileWriter.format("\tlw Temp_%d,%s\n", idxdst, label);
	}

	public void store(String var_name, TEMP src) {
		int idxsrc = src.getSerialNumber();
		fileWriter.format("\tsw Temp_%d,global_%s\n", idxsrc, var_name);
	}

	public void storeLocalVar(int var_offset, TEMP src) {
		int idxsrc = src.getSerialNumber();
		fileWriter.format("\taddi $sp, $sp, %d\n", -WORD_SIZE); // move stack pointer down
		fileWriter.format("\tsw Temp_%d,%d($fp)\n", idxsrc, -(var_offset * WORD_SIZE));// save register value in stack
	}

	public void sw(TEMP src, TEMP dstAdd, int offset) {
		int idxdst = dstAdd.getSerialNumber();
		int idxsrc = src.getSerialNumber();
		fileWriter.format("\tsw Temp_%d,%d(Temp_%d)\n", idxsrc, offset, idxdst);
	}

	public void storeReturnValue(TEMP src) {
		int idxsrc = src.getSerialNumber();
		fileWriter.format("\tmove $v0, Temp_%d\n", idxsrc);
	}

	public void move(TEMP dst, TEMP src) {
		int idxsrc = src.getSerialNumber();
		int idxdst = dst.getSerialNumber();
		fileWriter.format("\tmove Temp_%d,Temp_%d\n", idxdst, idxsrc);
	}

	public void li(TEMP t, int value) {
		int idx = t.getSerialNumber();
		fileWriter.format("\tli Temp_%d,%d\n", idx, value);
	}

	public void lb(TEMP dst, int offset, TEMP addrs) {
		int idxdst = dst.getSerialNumber();
		int idxaddrs = addrs.getSerialNumber();
		fileWriter.format("\tlb Temp_%d, %d(Temp_%d)\n", idxdst, offset, idxaddrs);
	}

	public void la(TEMP dst, String addrLabel) {
		int idxdst = dst.getSerialNumber();
		fileWriter.format("\tla Temp_%d, %s\n", idxdst, addrLabel);
	}

	public void sb(TEMP src, int offset, TEMP addrs) {
		int idxdst = src.getSerialNumber();
		int idxaddrs = addrs.getSerialNumber();
		fileWriter.format("\tsb Temp_%d, %d(Temp_%d)\n", idxdst, offset, idxaddrs);
	}

	public void add(TEMP dst, TEMP oprnd1, TEMP oprnd2) {
		int i1 = oprnd1.getSerialNumber();
		int i2 = oprnd2.getSerialNumber();
		int dstidx = dst.getSerialNumber();

		fileWriter.format("\tadd Temp_%d,Temp_%d,Temp_%d\n", dstidx, i1, i2);
	}

	public void addi(TEMP dst, TEMP src, int immd) {
		int idxdst = dst.getSerialNumber();
		int idxsrc = src.getSerialNumber();

		fileWriter.format("\taddi Temp_%d,Temp_%d,%d\n", idxdst, idxsrc, immd);
	}

	public void sub(TEMP dst, TEMP oprnd1, TEMP oprnd2) {
		int i1 = oprnd1.getSerialNumber();
		int i2 = oprnd2.getSerialNumber();
		int dstidx = dst.getSerialNumber();

		fileWriter.format("\tsub Temp_%d,Temp_%d,Temp_%d\n", dstidx, i1, i2);
	}

	public void mul(TEMP dst, TEMP oprnd1, TEMP oprnd2) {
		int i1 = oprnd1.getSerialNumber();
		int i2 = oprnd2.getSerialNumber();
		int dstidx = dst.getSerialNumber();

		fileWriter.format("\tmul Temp_%d,Temp_%d,Temp_%d\n", dstidx, i1, i2);
	}

	public void div(TEMP dst, TEMP oprnd1, TEMP oprnd2) {
		int i1 = oprnd1.getSerialNumber();
		int i2 = oprnd2.getSerialNumber();
		int dstidx = dst.getSerialNumber();

		fileWriter.format("\tdiv Temp_%d,Temp_%d,Temp_%d\n", dstidx, i1, i2);
	}

	public void sll(TEMP dst, TEMP src, int i) {
		int idxdst = dst.getSerialNumber();
		int idxsrc = src.getSerialNumber();
		fileWriter.format("\tsll Temp_%d,Temp_%d,%d\n", idxdst, idxsrc, i);
	}

	public void srl(TEMP dst, TEMP src, int i) {
		int idxdst = dst.getSerialNumber();
		int idxsrc = src.getSerialNumber();
		fileWriter.format("\tsrl Temp_%d,Temp_%d,%d\n", idxdst, idxsrc, i);

	}

	public void label(String inlabel) {
		if (inlabel.equals("main")) {
			fileWriter.format(".text\n");
			fileWriter.format("%s:\n", inlabel);
		} else {
			fileWriter.format("%s:\n", inlabel);
		}
	}

	public void jump(String inlabel) {
		fileWriter.format("\tj %s\n", inlabel);
	}

	public void jal(String inlabel) {
		fileWriter.format("\tjal %s\n", inlabel);
	}
	
	public void jal(TEMP target_address) {
		int idxaddr = target_address.getSerialNumber();
		fileWriter.format("\tjal Temp_%d\n", idxaddr);
	}

	public void blt(TEMP oprnd1, TEMP oprnd2, String label) {
		int i1 = oprnd1.getSerialNumber();
		int i2 = oprnd2.getSerialNumber();

		fileWriter.format("\tblt Temp_%d,Temp_%d,%s\n", i1, i2, label);
	}

	public void bltz(TEMP oprnd1, String label) {
		int i1 = oprnd1.getSerialNumber();
		fileWriter.format("\tblt Temp_%d,$zero,%s\n", i1, label);
	}

	public void bge(TEMP oprnd1, TEMP oprnd2, String label) {
		int i1 = oprnd1.getSerialNumber();
		int i2 = oprnd2.getSerialNumber();

		fileWriter.format("\tbge Temp_%d,Temp_%d,%s\n", i1, i2, label);
	}

	public void bne(TEMP oprnd1, TEMP oprnd2, String label) {
		int i1 = oprnd1.getSerialNumber();
		int i2 = oprnd2.getSerialNumber();

		fileWriter.format("\tbne Temp_%d,Temp_%d,%s\n", i1, i2, label);
	}

	public void beq(TEMP oprnd1, TEMP oprnd2, String label) {
		int i1 = oprnd1.getSerialNumber();
		int i2 = oprnd2.getSerialNumber();

		fileWriter.format("\tbeq Temp_%d,Temp_%d,%s\n", i1, i2, label);
	}

	public void beqz(TEMP oprnd1, String label) {
		int i1 = oprnd1.getSerialNumber();

		fileWriter.format("\tbeq Temp_%d,$zero,%s\n", i1, label);
	}

	public void bnez(TEMP oprnd1, String label) {
		int i1 = oprnd1.getSerialNumber();
		fileWriter.format("\tbne Temp_%d, $zero, %s\n", i1, label);
	}

	public void malloc(TEMP t, TEMP size) {
		int idxt = t.getSerialNumber();
		int idxSize = t.getSerialNumber();
		fileWriter.format("\tmove $a0,Temp_%d\n", idxSize);
		fileWriter.format("\tli $v0,9\n");
		fileWriter.format("\tsyscall\n");
		fileWriter.format("\tmove Temp_%d, $v0\n", idxt);
	}

	public void allocate_stack(int size) {
		fileWriter.format("\taddi $sp, $sp, %d\n", -4 * size);
	}

	public void function_prolog() {

		fileWriter.format("\taddi $sp, $sp, -4\n"); // make space for return address
		fileWriter.format("\tsw	$ra, 0($sp)\n"); // save return address
		fileWriter.format("\taddi $sp, $sp, -4\n"); // make space for prev fp
		fileWriter.format("\tsw	$fp, 0($sp)\n"); // save fp

		fileWriter.format("\tmove $fp, $sp\n"); // set new fp to be current sp

		// if we need to alocate space for the frame, then we will get the size and move
		// the sp:
		/*
		 * fileWriter.format("\taddi $sp, $sp, %d\n", -4 * (SizeFrame+1)); // allocate
		 * the stack frame + 1 for func name storeLocalVar(0,funcNameTemp); //the
		 * function name pointer is at offset 0 from fp
		 */
	}

	public void function_epilogue(String end_label, String funcName) {

		label(end_label);
		// fileWriter.format("\taddi $sp, $sp, %d\n", 4 * (SizeFrame+1)); if we do
		// decide to move sp by size
		fileWriter.format("\tmove $sp, $fp\n");
		fileWriter.format("\tlw	$ra, 4($fp)\n"); // bring back the relevant ra
		fileWriter.format("\tlw	$fp, 0($fp)\n");
		fileWriter.format("\taddi $sp, $sp, 8\n");
		if (funcName.equals("main")) {
			return;
		}
		fileWriter.format("\tjr $ra\n"); // set PC back to ra
	}

	public void cleanAlloactedMem(TEMP addr, TEMP size, String label) {
		TEMP offset = TEMP_FACTORY.getInstance().getFreshTEMP();

		int idxaddr = addr.getSerialNumber();
		int idxsize = size.getSerialNumber();
		int idxoffs = offset.getSerialNumber();

		fileWriter.format("\tadd Temp_%d, Temp_%d, Temp_%d\n", idxoffs, idxaddr, idxsize);
		label(label);
		fileWriter.format("\taddi Temp_%d, Temp_%d, -4\n", idxoffs, idxoffs);
		fileWriter.format("\tsw $zero, 0(Temp_%d)\n", idxoffs);
		fileWriter.format("\tbne Temp_%d, Temp_%d, %s\n", idxoffs, idxaddr, label);
	}

	public void add_str_length(TEMP len, TEMP char1, TEMP offset, String loopLabel) {
		label(loopLabel);
		lb(char1, 0, offset);
		addi(len, len, 1);
		addi(offset, offset, 1);
		bnez(char1, loopLabel);
		addi(len, len, -1);
	}

	public void push(TEMP t) {
		int idxt = t.getSerialNumber();
		fileWriter.format("\taddi $sp, $sp, %d\n", -WORD_SIZE); // move stack pointer down
		fileWriter.format("\tsw Temp_%d,0($sp)\n", idxt);// save register value in stack
	}

	public void pop(TEMP t) {
		int idxt = t.getSerialNumber();
		fileWriter.format("\tlw Temp_%d,0($sp)\n", idxt);// load stack value to t
		fileWriter.format("\taddi $sp, $sp, %d\n", WORD_SIZE); // move stack pointer up

	}

	public void abort() {
		jump("abort");
	}
	
	public void createAbort() {
		label("abort");
		fileWriter.format("\tli $v0, 10\n");
		fileWriter.format("\tsyscall\n");
	}

	public void zeroDivisionCheck(TEMP t){
		String zeroDivLabel = IRcommand.getFreshLabel("zero_division");
		sir_MIPS_a_lot.getInstance().beqz(t, zeroDivLabel);
		// add label to handel error
		sir_MIPS_a_lot.getInstance().label(zeroDivLabel);
		TEMP tAbort_msg = TEMP_FACTORY.getInstance().getFreshTEMP();
		sir_MIPS_a_lot.getInstance().la(tAbort_msg, "string_illegal_div_by_0");
		sir_MIPS_a_lot.getInstance().print_string(tAbort_msg);
		abort();

	}

	public void objectInitializedCheck(TEMP t){
		String objectInitLabel = IRcommand.getFreshLabel("object_initialized");
		sir_MIPS_a_lot.getInstance().beqz(t, objectInitLabel);
		// add label to handel error
		sir_MIPS_a_lot.getInstance().label(objectInitLabel);
		TEMP tAbort_msg = TEMP_FACTORY.getInstance().getFreshTEMP();
		sir_MIPS_a_lot.getInstance().la(tAbort_msg, "string_invalid_ptr_dref");
		sir_MIPS_a_lot.getInstance().print_string(tAbort_msg);
		abort();

	}


	/**************************************/
	/* Global data */
	/**************************************/
	static List<String> dataList = null;

	public static void add_to_global_data_list(String string_label, String type, String data) {
		if (dataList == null)
			dataList = new LinkedList<String>();
		dataList.add(String.format("%s: %s %s\n", string_label, type, data));
	}

	public static void add_VTs_to_data_list() { // need to handle inherited met hods
		String methods = "";
		for (String class_name : Context.classMethods.keySet()) {
			methods = "";
			for (String method : Context.classMethods.get(class_name).keySet()) {
				methods += Context.classMethods.get(class_name).get(method) + "_" + method + ",";
			}
			methods += "\n ";
			add_to_global_data_list("VT_" + class_name, ".word", methods);
		}
	}

	// Adding all the global variables as labels in the data section.
	public static void add_global_variables_to_data_list() {
		for (String global : Context.globals) {
			add_to_global_data_list(global, ".word", "0");
		}
	}

	public void writeGlobalData() {
		if(dataList != null) {
			for (String data : dataList) {
				instance.fileWriter.print(data);
			}
		}
	}

	/**************************************/
	/* USUAL SINGLETON IMPLEMENTATION ... */
	/**************************************/
	private static sir_MIPS_a_lot instance = null;

	/*****************************/
	/* PREVENT INSTANTIATION ... */
	/*****************************/
	protected sir_MIPS_a_lot() {
	}

	/******************************/
	/* GET SINGLETON INSTANCE ... */
	/******************************/
	public static sir_MIPS_a_lot getInstance() {
		if (instance == null) {
			/*******************************/
			/* [0] The instance itself ... */
			/*******************************/
			instance = new sir_MIPS_a_lot();

			try {
				/*********************************************************************************/
				/*
				 * [1] Open the MIPS text file and write data section with error message strings
				 */
				/*********************************************************************************/
				String dirname = "./FOLDER_5_OUTPUT/";
				String filename = String.format("MIPS.txt");

				/***************************************/
				/* [2] Open MIPS text file for writing */
				/***************************************/
				instance.fileWriter = new PrintWriter(dirname + filename);
			} catch (Exception e) {
				e.printStackTrace();
			}

			/*****************************************************/
			/* [3] Print data section with error message strings */
			/*****************************************************/
			instance.fileWriter.print(".data\n");
			instance.fileWriter.print("string_access_violation: .asciiz \"Access Violation\"\n");
			instance.fileWriter.print("string_illegal_div_by_0: .asciiz \"Illegal Division By Zero\"\n");
			instance.fileWriter.print("string_invalid_ptr_dref: .asciiz \"Invalid Pointer Dereference\"\n");
			instance.fileWriter.print("space: .asciiz \" \"\n");

			add_VTs_to_data_list();
			add_global_variables_to_data_list();
			instance.writeGlobalData();
			instance.label("main");
		}
		return instance;
	}

}
