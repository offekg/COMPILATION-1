package AST;

import TYPES.*;
import IR.*;
import UTILS.Context;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import SYMBOL_TABLE.*;
import TEMP.TEMP;
import TEMP.TEMP_FACTORY;

public class AST_DEC_CLASSDEC extends AST_DEC {
	public String name;
	public String father;
	public AST_CFIELD_LIST cFieldList;

	public AST_DEC_CLASSDEC(String name, String father, AST_CFIELD_LIST cFieldList) {
		this.name = name;
		this.father = father;
		this.cFieldList = cFieldList;
		SerialNumber = AST_Node_Serial_Number.getFresh();
		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		System.out.print("====================== Dec (CLASS) -> CLASS DEC\n");
	}

	public TYPE SemantMe() {
		/*************************/
		/* [0] Check if wanted class name is taken*/
		/*************************/
		if (SYMBOL_TABLE.getInstance().find(name) != null) {
			OutputFileWriter.writeError(this.lineNumber,
					String.format("CLass decleration name %s already exists\n", name));
		}

		/*************************/
		/* [1] Begin Class Scope */
		/*************************/
		TYPE_CLASS t = new TYPE_CLASS(name, null, null);
		// Enter the Class Type to the Symbol Table
		SYMBOL_TABLE.getInstance().enter(name, t);

		SYMBOL_TABLE.getInstance().beginScope(ScopeType.CLASS_SCOPE,name);
		
		// Check father is a class
		if (father != null) {
			TYPE fatherType = SYMBOL_TABLE.getInstance().find(father);
			if (fatherType == null || !(fatherType.isClass())) {
				OutputFileWriter.writeError(this.lineNumber, "Could not resolve father's class");
			}
			t.father = (TYPE_CLASS) fatherType;
		}
		for (AST_CFIELD_LIST field = this.cFieldList; field != null; field = field.tail) {
			if (t.father != null) {
				if (field.head instanceof AST_CFIELD_FUNCDEC) {
					// Comparing checking if the field is an override of a function
					AST_CFIELD_FUNCDEC currentFunc = (AST_CFIELD_FUNCDEC) field.head;
					TYPE_FUNCTION overriddenMethod = t.father.getOverriddenMethod(currentFunc.funcdec.funcName);
					if (overriddenMethod != null) {
						// Function overrides, comparing return types
						TYPE returnType = SYMBOL_TABLE.getInstance().find(currentFunc.funcdec.returnType);
						if (!returnType.equals(overriddenMethod.returnType))
							OutputFileWriter.writeError(currentFunc.lineNumber, "Wrong return type for overridden method");
						// Comparing function parameters
						AST_FUNC_INPUT_VARS_LIST methodParam = currentFunc.funcdec.params;
						if (overriddenMethod.paramTypes == null && methodParam != null) {
							// Method with parameters overriding one without
							OutputFileWriter.writeError(currentFunc.lineNumber, "Error in method params");
						}
						if (overriddenMethod.paramTypes != null && methodParam == null) {
							// Method without parameters overriding one with
							OutputFileWriter.writeError(currentFunc.lineNumber, "Error in method params");
						}
						TYPE_LIST overidedParam = overriddenMethod.paramTypes;
						if (methodParam != null) {							
							for (TYPE currentType = SYMBOL_TABLE.getInstance().find(methodParam.head.paramType); methodParam != null; methodParam = methodParam.tail) {
								if (overidedParam == null || !currentType.equals(overidedParam.head)) {
									OutputFileWriter.writeError(currentFunc.lineNumber, "Error in method params");
								}
								overidedParam = overidedParam.tail;
							}
						}
						if (overidedParam != null) {
							// The overridden method has more parameters
							OutputFileWriter.writeError(currentFunc.lineNumber, "Error in method params");
						}
					}
				} else {
					// Comparing overridden fields type;
					AST_CFIELD_VARDEC currentField = (AST_CFIELD_VARDEC) field.head;
					TYPE_CLASS_VAR_DEC overriddenDataMember = t.father
							.getOverriddenDataMemember(currentField.vardec.name);
					if (overriddenDataMember != null) {
						// Overrides a field, comparing types
						TYPE fieldType = SYMBOL_TABLE.getInstance().find(currentField.vardec.type);
						if (fieldType == null) {
							OutputFileWriter.writeError(currentField.lineNumber, "Could not resolve field type");
						}
						if (!fieldType.equalsOrSubclass(overriddenDataMember.t))
							OutputFileWriter.writeError(currentField.lineNumber, "Wrong field type for overridden field");
					}
				}
			}
			// The data member declaration is does not throw an error, adding the data member.
			if (field.head instanceof AST_CFIELD_FUNCDEC) {
				TYPE_FUNCTION newMethodType = new TYPE_FUNCTION(null, null, null);
				TYPE_CLASS_VAR_DEC shell = new TYPE_CLASS_VAR_DEC(newMethodType, null);
				t.addDataMember(shell);
				((AST_CFIELD_FUNCDEC)field.head).SemantMe(shell);
				//t.addDataMember(((AST_CFIELD_FUNCDEC)field.head).SemantMe(newMethodType));
			}
			else {
				t.addDataMember(field.head.SemantMe());
			}
		}
		SYMBOL_TABLE.getInstance().endScope();
		Context.classAST.put(name, this);
		Context.classNames.add(name);

		/*********************************************************/
		/* [5] Return value is irrelevant for class declarations */
		/*********************************************************/
		return null;
	}

	/******************************************************/
	/* The printing message for a statement list AST node */
	/******************************************************/
	public void PrintMe() {
		/**************************************/
		/* AST NODE TYPE = AST DEC CLASS DEC */
		/**************************************/
		if (this.father == null)
			System.out.printf("AST NODE CLASS DEC:\n Class %s\n", this.name);
		else
			System.out.printf("AST NODE CLASS DEC:\n Class %s Extends %s\n", this.name, this.father);

		/*************************************/
		/* RECURSIVELY PRINT HEAD + TAIL ... */
		/*************************************/
		if (cFieldList != null)
			cFieldList.PrintMe();

		/**********************************/
		/* PRINT to AST GRAPHVIZ DOT file */
		/**********************************/
		if (father == null) {
			AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("CLASS DEC\n Class %s\n", this.name));
		} else {
			AST_GRAPHVIZ.getInstance().logNode(SerialNumber,
					String.format("CLASS DEC\n Class %s Extends %s\n", this.name, this.father));
		}

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		if (cFieldList != null)
			AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cFieldList.SerialNumber);
	}
	
	public TEMP IRme() {
		Context.varStack.addLast(new LinkedHashSet<>());
		LinkedHashMap<String, String> methods = new LinkedHashMap<>();
		LinkedHashSet<String> fields = new LinkedHashSet<>();
		Context.classMethods.put(name, methods);
		Context.classFields.put(name, fields);
		if (father != null) {
			methods.putAll(Context.classMethods.get(father));
			fields.addAll(Context.classFields.get(father));
		}
		Context.currentClassBuilder = name;
		cFieldList.IRme();
		createClassConstructorIR();
		Context.currentClassBuilder = null;
		Context.varStack.removeLast();
		return null;
	}
	
	public void createClassConstructorIR() {
		String label = name + "_constructor";
		Context.globalFunctions.put(label, label);
		Context.epilogueLabel = label + "_epilogue";
		IR.getInstance().Add_IRcommand(new IRcommand_Label(label));
		IR.getInstance().Add_IRcommand(new IRcommand_Function_Prologue(0));
		int sizeToAllocate = Context.classFields.get(Context.currentClassBuilder).size() + 1;
		TEMP t = TEMP_FACTORY.getInstance().getFreshTEMP();
		IR.getInstance().Add_IRcommand(new IRcommand_Malloc(t, sizeToAllocate));
		//check if there are functions, and set VT if there are:
		if (Context.classMethods.get(name).size() > 0)
			IR.getInstance().Add_IRcommand(new IRcommand_Set_Virtual_Table(t, name));
        setDefaultValues(this, t);
		IR.getInstance().Add_IRcommand(new IRcommand_StoreReturnValue(t));
        IR.getInstance().Add_IRcommand(new IRcommand_Function_Epilogue("constructor", 0));
	}
	
	public void setDefaultValues(AST_DEC_CLASSDEC currentClass, TEMP instanceAddr) {
        if (currentClass.father != null) {
            setDefaultValues(Context.classAST.get(currentClass.father), instanceAddr);
        }
        currentClass.cFieldList.setDefaultValues(instanceAddr);
	}
}
