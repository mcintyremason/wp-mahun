// Mason McIntyre
// 02-16-2015
// rscpu1.java
// The purpose of this program is to represent a relatively simple CPU. This is done by using global variables to simulate registers.

import java.util.*;
import java.io.*;
import java.lang.*;

public class rscpu1{
  static short AR, AC, R, PC;
  static byte DR, IR, TR, M[];
  static boolean Z, C, V, N;
  
  static void fetchCycle(){  /* the fetch cycle */
    int val;
    AR = PC; /* set Address Register to the Program Counter */
    System.out.printf("fetch 1: AR = %d and PC = %d\n", AR, PC);
    DR = M[AR]; /* set the Data Register to the Memory allocation stored at index at AR */
    PC++; /* increment the Program Counter */
    val = (int)(DR & 0xff); /* conversion made for printing output */
    System.out.printf("fetch 2: DR = %d and PC = %d\n", val, PC);
    IR = DR; /* set the Instruction Register to the value in the Data Register */
    AR = PC; /* set the Address Register to the value stored in the incremented Program Counter */
    System.out.printf("fetch 3: IR = %d and AR = %d\n", val, AR);
  }
  
  static void NOP (){
    int val = (int)(DR & 0xff); /* conversion made for printing output */
    String flagbits = flagsToString(Z, C, V, N); /* convert flag booleans (ZCVN) into its binary string representation */
    System.out.println("NOP instruction");
    System.out.printf("Instruction Complete: AC = %d R = %d ZCVN = %s AR = %d PC = %d DR = %d\n", AC, R, flagbits, AR, PC, val);
    fetchCycle(); /* call fetch cycle instruction */
 }

  static void HALT (){
    int val = (int)(DR & 0xff); /* conversion made for printing output */
    String flagbits = flagsToString(Z, C, V, N); /* convert flag booleans (ZCVN) into its binary string representation */
    System.out.println("HALT instruction");
    System.out.printf("Instruction Complete: AC = %d R = %d ZCVN = %s AR = %d PC = %d DR = %d\n", AC, R, flagbits, AR, PC, val);
  }

  static void findOpCode (byte op){
    switch(op){
    case (byte)0:   /* 0000 0000 */
      NOP();
      return;
    case (byte)255: /* 1111 1111 */
      HALT();
      return;
    default:
      return;
    }
  }

  static String flagsToString (boolean z, boolean c, boolean v, boolean n){
    StringBuilder stringBuilder = new StringBuilder();
    if(z == true)
      stringBuilder.append("1");
    else 
      stringBuilder.append("0");
    if(c == true)
      stringBuilder.append("1");
    else 
      stringBuilder.append("0");
    if(v == true)
      stringBuilder.append("1");
    else 
      stringBuilder.append("0");
    if(n == true)
      stringBuilder.append("1");
    else 
      stringBuilder.append("0");

    return stringBuilder.toString();
  }

  public static void main(String[] args){
    String filename, line;
    int count = 0, memSize = 65536;
    byte byteVal;
    File fd;
    Scanner S = new Scanner(System.in);

    AR = 0;
    AC = 0;
    R = 0;
    PC = 0;
    DR = 0;
    IR = 0;
    TR = 0;

    M = new byte[memSize]; /* allocate array able to hold 2^8 simulated memory allocations */

    for(int i = 0; i < memSize; i++){ /* initialize all memory allocations to zero */
      M[i] = 0;
    }
    
    System.out.print("rscpu written by Mason McIntyre\n");
    System.out.print("Enter the name of the file containing the program: \n");
    filename = S.next(); /* get filename */
    
    fd = new File(filename);
    System.out.println("You've opened the file " + filename);

    try{ /* check that the file is falid. */
      S = new Scanner(fd); 
    }catch (IOException e){
      System.err.println(e);
      System.exit(1);
    }

    while(S.hasNextLine()) { /* parse through file line by line */
      line = S.nextLine();
      M[count] = (byte)Integer.parseInt(line, 16); /* convert line into integer by parsing the string as a base 16 number and then cast to a byte */
      count++;
    }

    System.out.print("End of file reached.\n");
    fetchCycle(); /* initiate the first fetch instruction */
    
    for(int i = 0; i < count; i++){
      findOpCode(M[i]); /* find the proper opcode for each byte read in */
    }
  }
}
