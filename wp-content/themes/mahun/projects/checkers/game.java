// game.java
// the driver to run the game
// Mason McIntyre

import java.util.*;

class game 
{
  static boolean turn = false;
  static teams team = new teams();
  static board B = new board();
  static int bteamcount = 12, rteamcount = 12;
  public static void main(String[] args)
  {
    
    
    team.buildteam(true);
    team.buildteam(false);

    B.fillboard(team);
    B.dumpboard();

    while(bteamcount > 0 && rteamcount >0)
      {
	
	System.out.println("BLACK: pieces left: "+bteamcount);
	System.out.println();

	System.out.println("red: pieces left: "+rteamcount);
	System.out.println();


	if(turn == false)
	  System.out.println("RED'S TURN");
	else
	  System.out.println("BLACK'S TURN");

	move(select());
	B.dumpboard();
	turn = !turn;	
      }
    if(bteamcount > 0)
      System.out.print("\n\nCongratulations! \n\t Black Team WINS!");
    else if(rteamcount > 0)
      System.out.print("\n\nCongratulations! \n\t Black Team WINS!");
      
  }

  static piece select()
  { 
    piece temp = null;
    System.out.println("Select a piece to move:");
    Scanner sc = new Scanner(System.in);
    char input = sc.next().charAt(0);
    
    if(turn == false) //red team
      {
	for(int i = 0; i < 12; i++)
	  {
	    temp = team.rteam.get(i);
	    if(temp.alive == true)
	      {
		if(input == temp.name)
		  {
		    System.out.println("You've selected to move piece: "+temp.name);
		    break;
		  }
		else if(temp == null)
		  {
		    System.out.println("Sorry, that piece no longer exists!");
		    temp = select();
		    break;
		  }
		if(i == 11)
		  {
		    System.out.println("Invalid Selection");
		    temp = select();
		  }
	      }
	  }
      }

    if(turn == true) // black's turn
      {
	for(int i = 0; i < 12; i++)
	  {
	    temp = team.bteam.get(i);
	    if(temp.alive == true)
	      {
		if(input == temp.name)
		  {
		    System.out.println("You've selected to move piece: "+temp.name);
		    break;
		  }
		else if(temp == null)
		  {
		    System.out.println("Sorry, that piece no longer exists!");
		    temp = select();
		    break;
		  }
		
		if(i == 11)
		  {
		    System.out.println("Invalid Selection");
		    temp = select();
		  }
	      }
	  }
      }
    return temp;
  }

    static void move(piece temp)
    {
      boolean upperright = false, upperleft = false, lowerright = false, lowerleft = false, jumpupright = false, jumpupleft = false, jumplowerright = false, jumplowerleft = false; // triggers for movement
      Scanner sc;
      String input = new String();
      char c;

      System.out.println("Which direction?");

      Map<Integer, piece> row = B.grid.get(temp.y);
      Map<Integer, piece> prevrow = B.grid.get(temp.y-1);
      Map<Integer, piece> postrow = B.grid.get(temp.y+1);
      Map<Integer, piece> pprerow = B.grid.get(temp.y-2); 
      Map<Integer, piece> ppostrow = B.grid.get(temp.y+2);

      piece check = null;

      // CHECK UPPER RIGHT
      if(temp.x+1 < 8 && temp.y-1 > -1)
	{
	  if(prevrow.get(temp.x+1) != null)
	    check = prevrow.get(temp.x+1);
      
	  if(check != null)
	    {
	      if(temp.team == false || temp.king == true)
		{
		  //upperright = false;
		  if(check.team != temp.team)
		    {
		      if(temp.y-2 > -1)
			{
			  if(pprerow.get(temp.x+2) == null)
			    {
			      if(temp.x+2 < 8 && temp.y-2 > -1)
				{
				  System.out.println("jump upper right? (jur)");
				  jumpupright = true;
				}		   
			    } 
			}
		    }
		}
	    }
	  else
	    {
	      if(temp.team == false || temp.king == true)
		{
		  System.out.println("upper right? (ur)");
		  upperright = true;
		  jumpupright = false;
		}
	    }
	  check = null;
	}
    

      // CHECK UPPER LEFT	    
      if(temp.x-1 > -1 && temp.y-1 > -1)
	{
	  if(prevrow.get(temp.x-1) != null)
	    check = prevrow.get(temp.x-1);
      
	  if(check != null) 
	    {
	      if(temp.team == false || temp.king == true)
		{
		  if(check.team != temp.team)
		    {
		      if(temp.y-2 > -1)
			{
			  if(pprerow.get(temp.x-2) == null)
			    {
			      if(temp.x-2 > -1 && temp.y-2 > -1)
				{
				  System.out.println("jump upper left? (jul)");
				  jumpupleft = true;
				}
			    }
			}
		    }
		}
	    }
	  else
	    {
	      if(temp.team == false || temp.king == true)
		{
		  System.out.println("upper left? (ul)");
		  upperleft = true;
		  jumpupleft = false;
		}
	    }
	  check = null; // reset piece to check
	}
    
    

      // CHECK LOWER RIGHT	
      if(temp.x+1 < 8 && temp.y+1 < 8)
	{
	  if(postrow.get(temp.x+1) != null)
	    check = postrow.get(temp.x+1);
	
	  if(check != null)
	    {
	      if(temp.team == true || temp.king == true)
		{
		  if(check.team != temp.team)
		    {
		      if(temp.y+2 < 8)
			{
			  if(ppostrow.get(temp.x+2) == null)
			    {
			      if(temp.x+2 < 8 && temp.y+2 < 8)
				{
				  System.out.println("jump lower right? (jlr)");
				  jumplowerright = true;
				}
			    }
			}
		    }
		}
	    }
      
	  else
	    {
	      if(temp.team == true || temp.king == true)
		{
		  System.out.println("lower right? (lr)");
		  lowerright = true;
		}
	      jumplowerright = false;
	    }
	  check = null; // reset the piece to check
	}

      // CHECK LOWER LEFT	
      if(temp.x-1 > -1 && temp.y+1 < 8)
	{
	  if(postrow.get(temp.x-1) != null)
	    check = postrow.get(temp.x-1);
    
	  if(check != null)
	    {
	      if(temp.team == true || temp.king == true)
		{
		  if(check.team != temp.team)
		    {
		      if(temp.y+2 < 8)
			{
			  if(ppostrow.get(temp.x-2) == null)
			    {
			      if(temp.x-2 > -1 && temp.y+2 < 8)
				{
				  System.out.println("jump lower left? (jll)");
				  jumplowerleft = true;
				}
			    }
			}
		    } 
		}
	    }
	  else
	    {
	      if(temp.team == true || temp.king == true)
		{
		  System.out.println("lower left? (ll)");
		  lowerleft = true;
		}
	      jumplowerleft = false;
	    }

	  check = null;
	}

      //CHECK IF NO WHERE TO MOVE
      if(upperright == false && upperleft == false && lowerright == false && lowerleft == false && jumpupright == false && jumpupleft == false && jumplowerright == false && jumplowerleft == false)
	{
	  System.out.println("Piece "+temp.name+" cannot move anywhere!");
	  temp = select();
	  move(temp);
	}
      else
	{
	  sc = new Scanner(System.in);
	  input = sc.next();
	
	  switch(input) 
	    {
	    case "ur":
	      if(temp.king == true || temp.team == false)
		{
		  if(upperright == true)
		    {
		    
		      row.remove(temp.x);  // remove piece from row
		      B.grid.remove(temp.y); // remove row from grid
		      row.put(temp.x, null); // fill piece spot in row with null
		      B.grid.put(temp.y, row); //replace row
		      temp.x++; // increase x position of piece
		      temp.y--; // increase y position of piece
		      row = B.grid.get(temp.y); // get row at proper y position
		      row.put(temp.x, temp); // place piece in row
		      B.grid.put(temp.y, row); // place row back on grid
		    
		      if(temp.y == 0) // in top row
			temp.king = true;
		      break;
		    }
		}
	      else
		{
		  System.out.println("Cannot move there!");
		  temp = select();
		  move(temp);
		  break;
		}
	    
	    case "ul":
	      if(temp.king == true || temp.team == false)
		{
		  if(upperleft == true)
		    {
		      row.remove(temp.x);
		      B.grid.remove(temp.y);
		      row.put(temp.x, null);
		      B.grid.put(temp.y, null);
		      B.grid.put(temp.y, row);
		      temp.x--;
		      temp.y--;
		      row = B.grid.get(temp.y);
		      row.put(temp.x, temp);
		      B.grid.put(temp.y, row);
		    
		      if(temp.y == 0)
			temp.king = true;
		      break;
		    }
		}
	      else
		{
		  System.out.println("Cannot move there!");
		  temp = select();
		  move(temp);
		  break;
		}
	    
	    case "lr":
	      if(temp.king == true || temp.team == true)
		{
		  if(lowerright == true)
		    {
		      row.remove(temp.x);
		      B.grid.remove(temp.y);
		      row.put(temp.x, null);
		      B.grid.put(temp.y, null);
		      B.grid.put(temp.y, row);
		      temp.x++;
		      temp.y++;
		      row = B.grid.get(temp.y);
		      row.put(temp.x, temp);
		      B.grid.put(temp.y, row);
		    
		      if(temp.y == 7)
			temp.king = true;
		      break;
		    }
		}
	      else
		{
		  System.out.println("Cannot move there!");
		  temp = select();
		  move(temp);
		  break;
		}
	    
	    case "ll":
	      if(temp.king == true || temp.team == true)
		{
		  if(lowerleft == true)
		    {
		      row.remove(temp.x);
		      //B.grid.remove(temp.y);
		      row.put(temp.x, null);
		      B.grid.put(temp.y, null);
		      B.grid.put(temp.y, row);
		      temp.x--;
		      temp.y++;
		      row = B.grid.get(temp.y);
		      row.put(temp.x, temp);
		      B.grid.put(temp.y, row);
		    
		      if(temp.y == 7)
			temp.king = true;
		      break;
		    }
		}
	      else
		{
		  System.out.println("Cannot move there!");
		  temp = select();
		  move(temp);
		  break;
		}
	    
	    case "jur":
	      if(temp.team == false || temp.king == true)
		{
		  if(jumpupright == true)
		    {
		      
		      row.remove(temp.x);
		      
		      if(prevrow.get(temp.x+1).team == true)
			bteamcount--;
		      else
			rteamcount--;
		      
		      prevrow.get(temp.x+1).alive = false;
		      prevrow.remove(temp.x+1);		
		      row.put(temp.x, null);
		      prevrow.put(temp.x+1, null);
		      B.grid.put(temp.y-1, prevrow);
		      B.grid.put(temp.y, row);
		      temp.x +=2;
		      temp.y -=2;
		      row = B.grid.get(temp.y);
		      row.put(temp.x, temp);
		      B.grid.put(temp.y, row);
		      if(temp.y == 0) // in top row
			temp.king = true;
		      break;
		    }
		  else
		    {
		      System.out.println("Cannot move there!");
		      temp = select();
		      move(temp);
		      break;
		    }
		}

	    case "jul":
	      if(temp.team == false || temp.king == true)
		{
		  if(jumpupleft == true)
		    {
		      row.remove(temp.x);
		      if(prevrow.get(temp.x-1).team == true)
			bteamcount--;
		      else
			rteamcount--;
		      prevrow.get(temp.x-1).alive = false;
		      prevrow.remove(temp.x+1);		
		      row.put(temp.x, null);
		      prevrow.put(temp.x-1, null);
		      B.grid.put(temp.y-1, prevrow);
		      B.grid.put(temp.y, row);
		      temp.x -=2;
		      temp.y -=2;
		      row = B.grid.get(temp.y);
		      row.put(temp.x, temp);
		      B.grid.put(temp.y, row);
		      if(temp.y == 0) // in top row
			temp.king = true;
		      break;
		    }
		  else
		    {
		      System.out.println("Cannot move there!");
		      temp = select();
		      move(temp);
		      break;
		    }
		}

	    case "jlr":
	      if(temp.team == true || temp.king == true)
		{
		  if(jumplowerright == true)
		    {
		      row.remove(temp.x);
		      if(postrow.get(temp.x+1).team == true)
			bteamcount--;
		      else
			rteamcount--;
		      postrow.get(temp.x+1).alive = false;
		      postrow.remove(temp.x+1);
		      row.put(temp.x, null);
		      postrow.put(temp.x+1, null);
		      B.grid.put(temp.y+1, postrow);
		      B.grid.put(temp.y, row);
		      temp.x +=2;
		      temp.y +=2;
		      row = B.grid.get(temp.y);
		      row.put(temp.x, temp);
		      B.grid.put(temp.y, row);
		      
		      if(temp.y == 7) // in bottom row
			temp.king = true;
		      break;
		    }
		  else
		    {
		      System.out.println("Cannot move there!");
		      temp = select();
		      move(temp);
		      break;
		    }
		}

	    case "jll":
	      if(temp.team == true || temp.king == true)
		{
		  if(jumplowerleft == true)
		    {
		      row.remove(temp.x);
		      
		      if(postrow.get(temp.x-1).team == true)
			bteamcount--;
		      else
			rteamcount--;
		      
		      postrow.get(temp.x-1).alive = false;
		      postrow.remove(temp.x+1);
		      row.put(temp.x, null);
		      postrow.put(temp.x-1, null);
		      B.grid.put(temp.y+1, postrow);
		      B.grid.put(temp.y, row);
		      temp.x -=2;
		      temp.y +=2;
		      row = B.grid.get(temp.y);
		      row.put(temp.x, temp);
		      B.grid.put(temp.y, row);
		      
		      if(temp.y == 7) // in bottom row
			temp.king = true;
		      break;
		    }
		  else
		    {
		      System.out.println("Cannot move there!");
		      temp = select();
		      move(temp);
		      break;
		    }
		}
	    default:
	      System.out.println("Invalid Input");
	      temp = select();
	      move(temp);
	      break;
	    }
	}
    }
}