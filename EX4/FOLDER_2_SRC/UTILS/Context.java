package UTILS;

import java.util.HashMap;
import java.util.LinkedList;

import TEMP.TEMP;

public class Context {
	public static String currentClassBuilder;
	public static String epilogueLabel;
	public static TEMP currentObject;
	public static HashMap<String, LinkedList<String>> classMethodList = new HashMap<>();
	public static HashMap<String, LinkedList<String>> classFieldList = new HashMap<>();
}
