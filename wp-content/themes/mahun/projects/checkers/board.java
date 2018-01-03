// create a game board to place pieces onto
// Developed by Mason McIntyre

import java.util.*;

class board
{
  static Map<Integer, Map<Integer, piece>> grid;
  //int[][] position = new int[8][8];
  
  public board()
  {
    grid = new HashMap<Integer, Map<Integer, piece>>();
  }

  static void fillboard(teams team)
  {
    Map<Integer, piece> row;
    int bcount = 0, rcount = 0;
    piece temp;
    for(int i = 0; i < 8; i++)
      {
	row = new HashMap<Integer, piece>();
	if(i % 2 == 0) // aligns pieces
	  {
	    
	    for(int j = 1; j < 8; j+=2)
	      {
		if(i <= 2) // true side
		  {
		    temp = (piece)team.bteam.get(bcount);
		    temp.x = j;
		    temp.y = i;
		    row.put(j, temp);
		    bcount++;
		  }
		
		if(i >= 5) // false side
		  {
		    //row.put(j, (piece)team.rteam.get(rcount)); 
		    temp = (piece)team.rteam.get(rcount);
		    temp.x = j;
		    temp.y = i;
		    row.put(j, temp);
		    rcount++;
		  }
		
	      }
	  }
	else
	  {
	    for(int j = 0; j < 8; j+=2)
	      {
		if(i <= 2) // true side
		  {
		    //row.put(j, (piece)team.bteam.get(bcount)); 
		    temp = (piece)team.bteam.get(bcount);
		    temp.x = j;
		    temp.y = i;
		    row.put(j, temp);
		    bcount++;
		  }
		
		if(i >= 5) // false side
		  {
		    //row.put(j, (piece)team.rteam.get(rcount)); 
		    temp = (piece)team.rteam.get(rcount);
		    temp.x = j;
		    temp.y = i;
		    row.put(j, temp);
		    rcount++;
		  }

	      }
	  }
	grid.put(i, row);
	
      }
  }

  public void dumpboard()
  {
    Map<Integer, piece> row;
    piece temp = null;

    System.out.println("|---|---|---|---|---|---|---|---|");

    for(int i = 0; i < 8; i++)
      {
	row = grid.get(i);
	for(int j = 0; j < 8; j++)
	  { 
	    if(row.get(j) != null)
	      {
		if(row.get(j).king == false)
		  System.out.print("| "+row.get(j).name+" ");
		else if(row.get(j).king == true)
		  System.out.print("|*"+row.get(j).name+"*");
	      }
	    else
	      System.out.print("|   ");
	    //else
	      //temp = row.get(j);
	      
	  }
	System.out.print("|\n");
	System.out.println("|---|---|---|---|---|---|---|---|");
      }
  }

}