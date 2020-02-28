package UTILS;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;

import AST.AST_DEC_CLASSDEC;
import TEMP.TEMP;

public class Context {
	// Provider for information used through the recursion (without passing it directly).
	public static String currentClassBuilder;
	public static String epilogueLabel;
	public static TEMP currentObject;
	// Each element in the list is the set of variables available in the current frame.
	public static LinkedList<LinkedHashSet<String>> varStack = new LinkedList<>(Arrays.asList(new LinkedHashSet<>(), new LinkedHashSet<>()));
	
	// Details of each class in the program:
	//
	// classMethods: The keys of the map are class names. Each entry contains
	// a sorted map where the keys are the methods (in order of declaration) and the
	// values are the names of the classes with the method implementation for the 
	// VTable construction. For example: for the program - 
	// class A {foo(){} bar(){}}; class B {foo(){} baz(){}};
	// will result in the map -
	// A: (foo: A, bar: A), B: (foo: B, bar: A, baz: B)
	public static HashMap<String, SortedMap<String, String>> classMethods = new HashMap<>();

	// classFields: The keys of the map are class names. Each entry contains
	// a set (sorted by insertion order) with the name of the fields in the class.
	// For example: for the program - 
	// class A {int foo; int bar;}; class B {int foo; int baz;};
	// will result in the map -
	// A: (foo, bar), B: (foo, bar, baz)
	public static HashMap<String, LinkedHashSet<String>> classFields = new HashMap<>();
	
	// Class to AST node mapping.
	public static HashMap<String, AST_DEC_CLASSDEC> classAST = new HashMap<>();
	
	// A mapping from the global function name to its label.
	public static HashMap<String, String> globalFunctions = new HashMap<>();
	
	// A mapping from the string to its label in the MIPS data section.
	public static HashMap<String, String> stringLabels = new HashMap<>();
	
	// The set of class names in the program.
	public static HashSet<String> classNames = new HashSet<>();
	
	// The set of global variables.
	public static LinkedHashSet<String> globals = varStack.getFirst();
	
	// Each element in the list is the set of variables available in the current frame.
	public static LinkedList<HashMap<String, Integer>> localFrameVarsList = new LinkedList<>(Arrays.asList(new HashMap<>()));
}
