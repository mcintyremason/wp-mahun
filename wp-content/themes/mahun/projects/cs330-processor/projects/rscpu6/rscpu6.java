/* 
   Mason McIntyre
   03-24-2015
   rscpu5.java
   The purpose of this program is to represent a relatively simple CPU. This is done by using global variables to simulate registers.
   The newly implemented instructions: RR, RL, LSR, LSL.
*/

import java.util.*;
import java.io.*;
import java.lang.*;

public class rscpu6{
  static short AR, PC;
  static byte AC, R, DR, IR, TR, M[];
  static boolean Z, C, V, N;
  
  static void fetchCycle(){ /* the fetch cycle */
    int iDR, iPC, iAR, iIR; /* used to print the integer values of DR, PC, and AR */
    AR = PC; /* set Address Register to the Program Counter */
    iAR = (int)(AR & 0xffff); /* conversion made for printing output */
    iPC = (int)(PC & 0xffff); /* conversion made for printing output */
    System.out.printf("fetch 1: AR = %d and PC = %d\n", iAR, iPC);
    DR = M[iAR]; /* set the Data Register to the Memory allocation stored at index at AR */
    iDR = (int)(DR & 0xff); /* conversion made for printing output */
    PC++; /* increment the Program Counter */
    iPC = (int)(PC & 0xffff); /* conversion made for printing output */
    System.out.printf("fetch 2: DR = %d and PC = %d\n", iDR, iPC);
    IR = DR; /* set the Instruction Register to the value in the Data Register */
    AR = PC; /* set the Address Register to the value stored in the incremented Program Counter */
    iAR = (int)(AR & 0xffff); /* conversion made for printing output */
    iIR = (int)(IR & 0xff);   /* conversion made for printing output */
    System.out.printf("fetch 3: IR = %d and AR = %d\n", iIR, iAR);
  }
  
  static void NOP (){
    String flagbits = flagsToString(Z, C, V, N); /* convert flag booleans (ZCVN) into its binary string representation */
    int iDR, iPC, iAR; /* used to print the integer values of DR, PC, and AR */
    iAR = (int)(AR & 0xffff); /* conversion made for printing output */
    iDR = (int)(DR & 0xff); 
    iPC = (int)(PC & 0xffff); 
    System.out.println("NOP instruction");
    System.out.printf("Instruction Complete: AC = %d R = %d ZCVN = %s AR = %d PC = %d DR = %d\n", AC, R, flagbits, iAR, iPC, iDR);
  }

  static void LDAC (){
    String flagbits;
    int iPC, iAR;

    System.out.println("LDAC instruction");
        
    iAR = (int)(AR & 0xffff);
    DR = M[iAR];
    PC++;
    AR++;
    iAR = (int)(AR & 0xffff);
    iPC = (int)(PC & 0xffff); 
    System.out.printf("LDAC1 DR = %d PC = %d AR = %d\n", DR, iPC, iAR);
    
    TR = DR;
    DR = M[iAR];
    PC++;
    iAR = (int)(AR & 0xffff);
    iPC = (int)(PC & 0xffff); 
    System.out.printf("LDAC2 TR = %d DR = %d PC = %d AR = %d\n", TR, DR, iPC, iAR);

    AR = (short)((TR << 8) | DR);
    iAR = (int)(AR & 0xffff);
    System.out.printf("LDAC3 AR = %d\n", iAR);

    DR = M[iAR];
    System.out.printf("LDAC4 DR = %d\n", DR);

    AC = DR;
    System.out.printf("LDAC5 AC = %d\n", AC);

    iAR = (int)(AR & 0xffff);
    iPC = (int)(PC & 0xffff); 

    flagbits = flagsToString(Z, C, V, N); 
    System.out.printf("Instruction Complete: AC = %d R = %d ZCVN = %s AR = %d PC = %d DR = %d\n", AC, R, flagbits, iAR, iPC, DR);
  }

  static void STAC (){
    String flagbits; 
    int iPC, iAR;
    
    System.out.println("STAC instruction");
    
    iAR = (int)(AR & 0xffff);
    DR = M[iAR];
    PC++;
    AR++;
    iAR = (int)(AR & 0xffff);
    iPC = (int)(PC & 0xffff); 
    System.out.printf("STAC1 DR = %d PC = %d AR = %d\n", DR, iPC, iAR);
    
    TR = DR;
    DR = M[iAR];
    PC++;
    iAR = (int)(AR & 0xffff);
    iPC = (int)(PC & 0xffff); 
    System.out.printf("STAC2 TR = %d DR = %d PC = %d AR = %d\n", TR, DR, iPC, iAR);

    AR = (short)((TR << 8) | DR);
    iAR = (int)(AR & 0xffff);
    System.out.printf("STAC3 AR = %d\n", iAR);

    DR = AC;
    System.out.printf("STAC4 DR = %d\n", DR);

    M[iAR] = DR;
    System.out.printf("STAC5 M[AR] = %d\n", M[iAR]);

    iAR = (int)(AR & 0xffff);
    iPC = (int)(PC & 0xffff); 
    flagbits = flagsToString(Z, C, V, N); 
    System.out.printf("Instruction Complete: AC = %d R = %d ZCVN = %s AR = %d PC = %d DR = %d\n", AC, R, flagbits, iAR, iPC, DR);
  }

  static void MVAC (){
    String flagbits;
    int iDR, iPC, iAR;
    iAR = (int)(AR & 0xffff);
    iDR = (int)(DR & 0xff); 
    iPC = (int)(PC & 0xffff); 
    System.out.println("MVAC instruction");
    R = AC; /* set the general register to the value in the accumulator */

    flagbits = flagsToString(Z, C, V, N); 
    System.out.printf("MVAC1 R = %d\n", R);
    System.out.printf("Instruction Complete: AC = %d R = %d ZCVN = %s AR = %d PC = %d DR = %d\n", AC, R, flagbits, iAR, iPC, iDR);
  }

  static void MOVR (){
    String flagbits; 
    int iDR, iPC, iAR; 
    iAR = (int)(AR & 0xffff);
    iDR = (int)(DR & 0xff); 
    iPC = (int)(PC & 0xffff); 
    System.out.println("MOVR instruction");
    AC = R; /* set the accumulator to the value of the general register */

    flagbits = flagsToString(Z, C, V, N); 
    System.out.printf("MOVR1 AC = %d\n", AC);
    System.out.printf("Instruction Complete: AC = %d R = %d ZCVN = %s AR = %d PC = %d DR = %d\n", AC, R, flagbits, iAR, iPC, iDR);
  }

  static void JUMP (){
    String flagbits = flagsToString(Z, C, V, N); 
    int iDR, iPC, iAR; 
    iAR = (int)(AR & 0xffff); 
    iDR = (int)(DR & 0xff); 
    iPC = (int)(PC & 0xffff); 
    System.out.println("JUMP instruction");
    System.out.printf("Instruction Complete: AC = %d R = %d ZCVN = %s AR = %d PC = %d DR = %d\n", AC, R, flagbits, iAR, iPC, iDR);
  }

  static void JMPZ (){
    String flagbits = flagsToString(Z, C, V, N); 
    int iDR, iPC, iAR; 
    iAR = (int)(AR & 0xffff);
    iDR = (int)(DR & 0xff);  
    iPC = (int)(PC & 0xffff); 
    System.out.println("JMPZ instruction");
    System.out.printf("Instruction Complete: AC = %d R = %d ZCVN = %s AR = %d PC = %d DR = %d\n", AC, R, flagbits, iAR, iPC, iDR);
  }

  static void JPNZ (){
    String flagbits = flagsToString(Z, C, V, N); 
    int iDR, iPC, iAR; 
    iAR = (int)(AR & 0xffff); 
    iDR = (int)(DR & 0xff); 
    iPC = (int)(PC & 0xffff); 
    System.out.println("JPNZ instruction");
    System.out.printf("Instruction Complete: AC = %d R = %d ZCVN = %s AR = %d PC = %d DR = %d\n", AC, R, flagbits, iAR, iPC, iDR);
  }

  static void JMPC (){
    String flagbits = flagsToString(Z, C, V, N); 
    int iDR, iPC, iAR; 
    iAR = (int)(AR & 0xffff);
    iDR = (int)(DR & 0xff); 
    iPC = (int)(PC & 0xffff); 
    System.out.println("JMPC instruction");
    System.out.printf("Instruction Complete: AC = %d R = %d ZCVN = %s AR = %d PC = %d DR = %d\n", AC, R, flagbits, iAR, iPC, iDR);
  }

  static void JV (){
    String flagbits = flagsToString(Z, C, V, N); 
    int iDR, iPC, iAR; 
    iAR = (int)(AR & 0xffff);
    iDR = (int)(DR & 0xff); 
    iPC = (int)(PC & 0xffff); 
    System.out.println("JV instruction");
    System.out.printf("Instruction Complete: AC = %d R = %d ZCVN = %s AR = %d PC = %d DR = %d\n", AC, R, flagbits, iAR, iPC, iDR);
  }

  static void JN (){
    String flagbits = flagsToString(Z, C, V, N);
    int iDR, iPC, iAR; 
    iAR = (int)(AR & 0xffff);
    iDR = (int)(DR & 0xff); 
    iPC = (int)(PC & 0xffff); 
    System.out.println("JN instruction");
    System.out.printf("Instruction Complete: AC = %d R = %d ZCVN = %s AR = %d PC = %d DR = %d\n", AC, R, flagbits, iAR, iPC, iDR);
  }

  static void ADD (){
    int ccheck = 0;  /* used to check for a carry in the operation */
    int vcheckb = 0; /* vcheckb: used to record the sign bit before manipulation of AC */ 
    int vchecka = 0; /* vchecka: used to record the sign bit after manipulation of AC */
    int zcheckb = 0, zchecka = 0;
    String flagbits;
    int iDR = (int)(DR & 0xff), iPC = (int)(PC & 0xffff), iAR = (int)(AR & 0xffff);
    System.out.println("ADD instruction");

    /* mask out all bits except for the sign bit */
    vcheckb = (int)(AC & 0x80); 

    if(AC == 0)
      zcheckb = 1;
 
    ccheck = ((int)((int)AC + (int)R) & 0x100);  

    AC += R;

    if(AC == 0)
      zchecka = 1;

    /* mask out all bits except for the sign bit */
    vchecka = (int)(AC & 0x80); 

    setZeroFlag();
    setCheckFlag(ccheck);
    setOverflowFlag(vcheckb, vchecka, zcheckb, zchecka);
    setNegativeFlag();

    flagbits = flagsToString(Z, C, V, N);
    System.out.printf("ADD1 AC = %d ZCVN = %s\n", AC ,flagbits);
    System.out.printf("Instruction Complete: AC = %d R = %d ZCVN = %s AR = %d PC = %d DR = %d\n", AC, R, flagbits, iAR, iPC, iDR);
  }

  static void SUB (){
    int ccheck = 0;  /* used to check for a carry in the operation */
    int vcheckb = 0; /* vcheckb: used to record the sign bit before manipulation of AC */ 
    int vchecka = 0; /* vchecka: used to record the sign bit after manipulation of AC */
    int zcheckb = 0, zchecka = 0;
    String flagbits;
    int iDR = (int)(DR & 0xff), iPC = (int)(PC & 0xffff), iAR = (int)(AR & 0xffff);
    System.out.println("SUB instruction");

    /* mask out all bits except for the sign bit */
    vcheckb = (int)(AC & 0x80); 

    if(AC == 0)
      zcheckb = 1;

    ccheck = ((int)((int)AC + (int)R) & 0x100);  

    TR = R;
    R = (byte)((R ^ 0xff) + 1);
    AC += R;
    R = TR;

    if(AC == 0)
      zchecka = 1;

    /* mask out all bits except for the sign bit */
    vchecka = (int)(AC & 0x80); 

    setZeroFlag();
    setCheckFlag(ccheck);
    setOverflowFlag(vcheckb, vchecka, zcheckb, zchecka);
    setNegativeFlag();

    flagbits = flagsToString(Z, C, V, N);
    System.out.printf("SUB1 AC = %d ZCVN = %s\n", AC ,flagbits);
    System.out.printf("Instruction Complete: AC = %d R = %d ZCVN = %s AR = %d PC = %d DR = %d\n", AC, R, flagbits, iAR, iPC, iDR);
  }

  static void INAC (){
    int ccheck = 0;  /* used to check for a carry in the operation */
    int vcheckb = 0; /* vcheckb: used to record the sign bit before manipulation of AC */ 
    int vchecka = 0; /* vchecka: used to record the sign bit after manipulation of AC */
    int zcheckb = 0, zchecka = 0;
    String flagbits; 
    int iDR, iPC, iAR;
    iAR = (int)(AR & 0xffff);
    iDR = (int)(DR & 0xff); 
    iPC = (int)(PC & 0xffff); 
    System.out.println("INAC instruction");

    /* mask out all bits except for the sign bit */
    vcheckb = (int)(AC & 0x80); 
    
    if(AC == 0)
      zcheckb = 1;

    if(AC == -1)
      ccheck = 1;

    AC++;

    if(AC == 0)
      zchecka = 1;

    /* mask out all bits except for the sign bit */
    vchecka = (int)(AC & 0x80); 

    setZeroFlag();
    setCheckFlag(ccheck);
    setOverflowFlag(vcheckb, vchecka, zcheckb, zchecka);
    setNegativeFlag();


    flagbits = flagsToString(Z, C, V, N); 
    System.out.printf("INAC1 AC = %d ZCVN = %s\n", AC, flagbits);
    System.out.printf("Instruction Complete: AC = %d R = %d ZCVN = %s AR = %d PC = %d DR = %d\n", AC, R, flagbits, iAR, iPC, iDR);
  }

  static void CLAC (){
    String flagbits;
    int iDR, iPC, iAR;
    iAR = (int)(AR & 0xffff);
    iDR = (int)(DR & 0xff); 
    iPC = (int)(PC & 0xffff); 
    System.out.println("CLAC instruction");
    /* reset accumulator */
    AC = 0; 
    /* set zero flag */
    Z = true; 
    /* set carry flag */
    C = false;
    /* set overflow flag */ 
    V = false;
    /* set negative flag */
    N = false; 
    flagbits = flagsToString(Z, C, V, N); 
    System.out.printf("CLAC1 AC = %d ZCVN = %s\n", AC, flagbits);
    System.out.printf("Instruction Complete: AC = %d R = %d ZCVN = %s AR = %d PC = %d DR = %d\n", AC, R, flagbits, iAR, iPC, iDR);
  }

  static void AND (){
    String flagbits;
    int iDR, iPC, iAR;
    iAR = (int)(AR & 0xffff);
    iDR = (int)(DR & 0xff); 
    iPC = (int)(PC & 0xffff); 
    System.out.println("AND instruction");

    AC = (byte)(AC & R);

    setZeroFlag();
    setNegativeFlag();

    flagbits = flagsToString(Z, C, V, N);
    System.out.printf("ADD1 AC = %d, R = %d ZCVN = %s\n", AC, R, flagbits);
    System.out.printf("Instruction Complete: AC = %d R = %d ZCVN = %s AR = %d PC = %d DR = %d\n", AC, R, flagbits, iAR, iPC, iDR);
  }

  static void OR (){
    String flagbits;
    int iDR, iPC, iAR;
    iAR = (int)(AR & 0xffff);
    iDR = (int)(DR & 0xff); 
    iPC = (int)(PC & 0xffff); 
    System.out.println("OR instruction");

    AC = (byte)(AC | R);

    setZeroFlag();
    setNegativeFlag();

    flagbits = flagsToString(Z, C, V, N);
    System.out.printf("OR1 AC = %d, R = %d ZCVN = %s\n", AC, R, flagbits);
    System.out.printf("Instruction Complete: AC = %d R = %d ZCVN = %s AR = %d PC = %d DR = %d\n", AC, R, flagbits, iAR, iPC, iDR);
  }

  static void XOR (){
    String flagbits;
    int iDR, iPC, iAR;
    iAR = (int)(AR & 0xffff);
    iDR = (int)(DR & 0xff); 
    iPC = (int)(PC & 0xffff); 
    System.out.println("XOR instruction");

    AC = (byte)(AC ^ R);

    setZeroFlag();
    setNegativeFlag();

    flagbits = flagsToString(Z, C, V, N);
    System.out.printf("XOR1 AC = %d, R = %d ZCVN = %s\n", AC, R, flagbits);
    System.out.printf("Instruction Complete: AC = %d R = %d ZCVN = %s AR = %d PC = %d DR = %d\n", AC, R, flagbits, iAR, iPC, iDR);
  }

  static void NOT (){
    String flagbits;
    int iDR, iPC, iAR;
    iAR = (int)(AR & 0xffff);
    iDR = (int)(DR & 0xff); 
    iPC = (int)(PC & 0xffff); 
    System.out.println("NOT instruction");

    AC = (byte)(AC ^ 0xff);

    setZeroFlag();
    setNegativeFlag();

    flagbits = flagsToString(Z, C, V, N);
    System.out.printf("NOT1 AC = %d, ZCVN = %s\n", AC, flagbits);
    System.out.printf("Instruction Complete: AC = %d R = %d ZCVN = %s AR = %d PC = %d DR = %d\n", AC, R, flagbits, iAR, iPC, iDR);
  }

  static void RL (){
    int ccheck = 0;  /* used to check for a carry in the operation */
    int vcheckb = 0; /* vcheckb: used to record the sign bit before manipulation of AC */ 
    int vchecka = 0; /* vchecka: used to record the sign bit after manipulation of AC */
    int zcheckb = 0, zchecka = 0;
    String flagbits; 
    int iDR, iPC, iAR;
    iAR = (int)(AR & 0xffff);
    iDR = (int)(DR & 0xff); 
    iPC = (int)(PC & 0xffff); 
    System.out.println("RL instruction");

    /* mask out all bits except for the sign bit */
    vcheckb = (int)(AC & 0x80); 
    
    if(AC == 0)
      zcheckb = 1;

    ccheck = (AC & 0x100);

    if((AC & 0x100) != 0)
      AC = (byte)((AC << 1) + 1);
    else
      AC = (byte)(AC << 1);

    if(AC == 0)
      zchecka = 1;

    /* mask out all bits except for the sign bit */
    vchecka = (int)(AC & 0x80); 

    setZeroFlag();
    setCheckFlag(ccheck);
    setOverflowFlag(vcheckb, vchecka, zcheckb, zchecka);
    setNegativeFlag();

    flagbits = flagsToString(Z, C, V, N);
    System.out.printf("RL1 AC = %d, ZCVN = %s\n", AC, flagbits);
    System.out.printf("Instruction Complete: AC = %d R = %d ZCVN = %s AR = %d PC = %d DR = %d\n", AC, R, flagbits, iAR, iPC, iDR);
  }

  static void RR (){
    int ccheck = 0;  /* used to check for a carry in the operation */
    int vcheckb = 0; /* vcheckb: used to record the sign bit before manipulation of AC */ 
    int vchecka = 0; /* vchecka: used to record the sign bit after manipulation of AC */
    int zcheckb = 0, zchecka = 0;
    int rcarry = 0;
    String flagbits; 
    int iDR, iPC, iAR;
    iAR = (int)(AR & 0xffff);
    iDR = (int)(DR & 0xff); 
    iPC = (int)(PC & 0xffff); 
    System.out.println("RR instruction");

    /* mask out all bits except for the sign bit */
    vcheckb = (int)(AC & 0x80); 
    
    if(AC == 0)
      zcheckb = 1;

    ccheck = (AC & 0x100);

    if((AC & 0x01) != 0)
      AC = (byte)((byte)(AC >>> 1) ^ 0x80);
    else
      AC = (byte)(AC >>> 1);

    if(AC == 0)
      zchecka = 1;

    /* mask out all bits except for the sign bit */
    vchecka = (int)(AC & 0x80); 

    setZeroFlag();
    setCheckFlag(ccheck);
    setOverflowFlag(vcheckb, vchecka, zcheckb, zchecka);
    setNegativeFlag();

    flagbits = flagsToString(Z, C, V, N);
    System.out.printf("RR1 AC = %d, ZCVN = %s\n", AC, flagbits);
    System.out.printf("Instruction Complete: AC = %d R = %d ZCVN = %s AR = %d PC = %d DR = %d\n", AC, R, flagbits, iAR, iPC, iDR);
  }

  static void LSL (){
    int ccheck = 0;  /* used to check for a carry in the operation */
    int vcheckb = 0; /* vcheckb: used to record the sign bit before manipulation of AC */ 
    int vchecka = 0; /* vchecka: used to record the sign bit after manipulation of AC */
    int zcheckb = 0, zchecka = 0;
    String flagbits; 
    int iDR, iPC, iAR;
    iAR = (int)(AR & 0xffff);
    iDR = (int)(DR & 0xff); 
    iPC = (int)(PC & 0xffff); 
    System.out.println("LSL instruction");

    /* mask out all bits except for the sign bit */
    vcheckb = (int)(AC & 0x80); 
    
    if(AC == 0)
      zcheckb = 1;

    ccheck = (AC & 0x100);

    AC = (byte)(AC << 1);

    if(AC == 0)
      zchecka = 1;

    /* mask out all bits except for the sign bit */
    vchecka = (int)(AC & 0x80); 

    setZeroFlag();
    setCheckFlag(ccheck);
    setOverflowFlag(vcheckb, vchecka, zcheckb, zchecka);
    setNegativeFlag();

    flagbits = flagsToString(Z, C, V, N);
    System.out.printf("LSL1 AC = %d, ZCVN = %s\n", AC, flagbits);
    System.out.printf("Instruction Complete: AC = %d R = %d ZCVN = %s AR = %d PC = %d DR = %d\n", AC, R, flagbits, iAR, iPC, iDR);
  }

  static void LSR (){
    int ccheck = 0;  /* used to check for a carry in the operation */
    int vcheckb = 0; /* vcheckb: used to record the sign bit before manipulation of AC */ 
    int vchecka = 0; /* vchecka: used to record the sign bit after manipulation of AC */
    int zcheckb = 0, zchecka = 0;
    String flagbits; 
    int iDR, iPC, iAR;
    iAR = (int)(AR & 0xffff);
    iDR = (int)(DR & 0xff); 
    iPC = (int)(PC & 0xffff); 
    System.out.println("LSR instruction");

    /* mask out all bits except for the sign bit */
    vcheckb = (int)(AC & 0x80); 
    
    if(AC == 0)
      zcheckb = 1;

    ccheck = (AC & 0x100);

    if(AC < 0)
      AC = (byte)((AC >>> 1) ^ 0x80);
    else
      AC = (byte)(AC >>> 1);

    if(AC == 0)
      zchecka = 1;

    /* mask out all bits except for the sign bit */
    vchecka = (int)(AC & 0x80); 

    setZeroFlag();
    setCheckFlag(ccheck);
    setOverflowFlag(vcheckb, vchecka, zcheckb, zchecka);
    setNegativeFlag();

    flagbits = flagsToString(Z, C, V, N);
    System.out.printf("LSR1 AC = %d, ZCVN = %s\n", AC, flagbits);
    System.out.printf("Instruction Complete: AC = %d R = %d ZCVN = %s AR = %d PC = %d DR = %d\n", AC, R, flagbits, iAR, iPC, iDR);
  }

  static void MVI (){
    String flagbits;
    int iPC, iAR;
    iAR = (int)(AR & 0xffff); 
    iPC = (int)(PC & 0xffff);
    System.out.println("MVI instruction");

    DR = M[iAR];
    PC++;
    AR++;
    iAR = (int)(AR & 0xffff); 
    iPC = (int)(PC & 0xffff);
    System.out.printf("MVI1 DR = %d PC = %d AR = %d\n", DR, iPC, iAR);

    AC = DR;
    System.out.printf("MVI2 AC = %d\n", AC);

    iAR = (int)(AR & 0xffff); 
    iPC = (int)(PC & 0xffff);
    flagbits = flagsToString(Z, C, V, N); 
    System.out.printf("Instruction Complete: AC = %d R = %d ZCVN = %s AR = %d PC = %d DR = %d\n", AC, R, flagbits, iAR, iPC, DR);
  }

  static void HALT (){
    String flagbits;
    int iDR, iPC, iAR; 

    iAR = (int)(AR & 0xffff);
    iDR = (int)(DR & 0xff); 
    iPC = (int)(PC & 0xffff); 
    flagbits = flagsToString(Z, C, V, N); 
    System.out.println("HALT instruction");
    System.out.printf("Instruction Complete: AC = %d R = %d ZCVN = %s AR = %d PC = %d DR = %d\n", AC, R, flagbits, iAR, iPC, iDR);
  }

  static void setZeroFlag(){
    /* flag set if accumulator is zero */
    if(AC == 0) 
      Z = true;
    else
      Z = false;
  }

  static void setCheckFlag(int ccheck){
    /* if ccheck is a positive integer greater than or equal to 256 set the carry bit */
    if(ccheck != 0) 
      C = true;
    else 
      C = false;
}

  static void setOverflowFlag(int vcheckb, int vchecka, int zcheckb, int zchecka){
    /* check for sign change in AC, if so setflag set for overflow */
    if((vcheckb != vchecka) && (zcheckb == 0) && (zchecka == 0)) 
      V = true;
    else
      V = false;

  }

  static void setNegativeFlag(){
    /* flag set if the accumulator is negative */
    if(AC < 0) 
      N = true;
    else
      N = false;
  }

  static byte findOpCode (byte op){
    switch(op){
    case (byte)0:   /* 0000 0000 */
      fetchCycle();
      NOP();
      return op;
    case (byte)1:   /* 0000 0001 */
      fetchCycle();
      LDAC();
      return op;
    case (byte)2:   /* 0000 0010 */
      fetchCycle();
      STAC();
      return op;
    case (byte)3:   /* 0000 0011 */
      fetchCycle();
      MVAC();
      return op;
    case (byte)4:   /* 0000 0100 */
      fetchCycle();
      MOVR();
      return op;
    case (byte)5:   /* 0000 0101 */
      fetchCycle();
      JUMP();
      return op;
    case (byte)6:   /* 0000 0110 */
      fetchCycle();
      JMPZ();
      return op;
    case (byte)7:   /* 0000 0111 */
      fetchCycle();
      JPNZ();
      return op;
    case (byte)8:   /* 0000 1000 */
      fetchCycle();
      ADD();
      return op;
    case (byte)9:   /* 0000 1001 */
      fetchCycle();
      SUB();
      return op;
    case (byte)10:   /* 0000 1010 */
      fetchCycle();
      INAC();
      return op;
    case (byte)11:   /* 0000 1011 */
      fetchCycle();
      CLAC();
      return op;
    case (byte)12:   /* 0000 1100 */
      fetchCycle();
      AND();
      return op;
    case (byte)13:   /* 0000 1101 */
      fetchCycle();
      OR();
      return op;
    case (byte)14:   /* 0000 1110 */
      fetchCycle();
      XOR();
      return op;
    case (byte)15:   /* 0000 1111 */
      fetchCycle();
      NOT();
      return op;
    case (byte)16:   /* 0001 0000 */
      fetchCycle();
      JMPC();
      return op;
    case (byte)17:   /* 0001 0001 */
      fetchCycle();
      JV();
      return op;
    case (byte)18:   /* 0001 0010 */
      fetchCycle();
      RL();
      return op;
    case (byte)19:   /* 0001 0011 */
      fetchCycle();
      RR();
      return op;
    case (byte)20:   /* 0001 0100 */
      fetchCycle();
      LSL();
      return op;
    case (byte)21:   /* 0001 0101 */
      fetchCycle();
      LSR();
      return op;
    case (byte)22:   /* 0001 0110 */
      fetchCycle();
      MVI();
      return op;
    case (byte)23:   /* 0001 0111 */
      fetchCycle();
      JN();
      return op;
    case (byte)255:  /* 1111 1111 */
      fetchCycle();
      HALT();
      return op;
    default:
      fetchCycle();
      System.out.println("Invalid Intruction!");
      return op;
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

    /* allocate array able to hold 2^8 simulated memory allocations */
    M = new byte[memSize]; 

    /* initialize all memory allocations to zero */
    for(int i = 0; i < memSize; i++){ 
      M[i] = 0;
    }
    
    System.out.print("rscpu written by Mason McIntyre\n");
    System.out.print("Enter the name of the file containing the program: \n");
    /* get filename */
    filename = S.next(); 
    
    fd = new File(filename);
    System.out.println("You've opened the file " + filename);

    /* check that the file is valid, if not print error */
    try{ 
      S = new Scanner(fd); 
    }catch (IOException e){
      System.err.println(e);
      System.exit(1);
    }

    /* parse through file line by line */
    while(S.hasNextLine()) { 
      line = S.nextLine();
      /* convert line into integer by parsing the string as a base 16 number and then cast to a byte */
      if(line != null && !line.isEmpty()){
	M[count] = (byte)Integer.parseInt(line, 16); 
	count++;
      }
    }

    /* initiate the first fetch instruction */
    System.out.print("End of file reached.\n");

    while(findOpCode(M[PC]) != (byte)255);
  }
}
