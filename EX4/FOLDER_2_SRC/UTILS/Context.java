package UTILS;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

import AST.AST_DEC_CLASSDEC;
import TEMP.TEMP;

public class Context {
	public static String currentClassBuilder;
	public static String epilogueLabel;
	public static TEMP currentObject;
	public static HashMap<String, LinkedList<String>> classMethodList = new HashMap<>();
	public static HashMap<String, LinkedList<String>> classFieldList = new HashMap<>();
	public static HashMap<String, AST_DEC_CLASSDEC> classAST = new HashMap<>();
	public static HashMap<String, String> globalFunctions = new HashMap<>();
	public static HashMap<String, String> stringLabels = new HashMap<>();
	public static HashSet<String> classNames = new HashSet<>();
	
}
