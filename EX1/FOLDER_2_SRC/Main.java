import java.io.*;
import java.io.PrintWriter;

import java_cup.runtime.Symbol;
   
public class Main
{
	static public void main(String argv[])
	{
		Lexer l;
		Symbol s = null;
		FileReader file_reader;
		PrintWriter file_writer;
		String inputFilename = argv[0];
		String outputFilename = argv[1];
		boolean int_out_of_range = false;
		int int_value;
		
		try
		{
			/********************************/
			/* [1] Initialize a file reader */
			/********************************/
			file_reader = new FileReader(inputFilename);

			/********************************/
			/* [2] Initialize a file writer */
			/********************************/
			file_writer = new PrintWriter(outputFilename);
			
			/******************************/
			/* [3] Initialize a new lexer */
			/******************************/
			l = new Lexer(file_reader);

			/***********************/
			/* [4] Read next token */
			/***********************/
		
			s = l.next_token();
	
			/********************************/
			/* [5] Main reading tokens loop */
			/********************************/
			StringBuilder file_contnet = new StringBuilder();
			while (!int_out_of_range && s.sym != TokenNames.EOF && s.sym != TokenNames.ERROR)
			{
				System.out.println("inside loop");
				try {
					if (s.sym == TokenNames.INT) {
						int_value = Integer.parseInt((s.value).toString()); 
						if (int_value > (Math.pow(2,15) -1) || int_value < -(Math.pow(2,15))) {
							int_out_of_range = true;
							break;
						}
					}
				} catch(Exception e){
					int_out_of_range = true;
					System.out.println("inside catch");
					break;
				}
			
				/************************/
				/* [6] Print to console */
				/************************/
				System.out.print("[");
				System.out.print(l.getLine());
				System.out.print(",");
				System.out.print(l.getTokenStartPosition());
				System.out.print("]:");
				System.out.print(s.value);
				System.out.print("\n");
				
				/*********************/
				/* [7] Print to file */
				/*********************/
				/*file_writer.print(l.getLine());
				file_writer.print(": ");
				file_writer.print(s.value);
				file_writer.print("\n");*/
				file_contnet.append(TokenNames.getTokenName(s.sym));
				if (s.value != null) {
					file_contnet.append("(");
					file_contnet.append(s.value);
					file_contnet.append(")");
				}
				file_contnet.append("[");
				file_contnet.append(l.getLine());
				file_contnet.append(",");
				file_contnet.append(l.getTokenStartPosition());
				file_contnet.append("]");
				file_contnet.append("\n");

				
				/***********************/
				/* [8] Read next token */
				/***********************/
				s = l.next_token();
			}
			if (int_out_of_range || s.sym == TokenNames.ERROR)
				file_writer.print("ERROR");
			else
				file_writer.print(file_contnet);
			
			/******************************/
			/* [9] Close lexer input file */
			/******************************/
			l.yyclose();

			/**************************/
			/* [10] Close output file */
			/**************************/
			file_writer.close();
    	}
			     
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}