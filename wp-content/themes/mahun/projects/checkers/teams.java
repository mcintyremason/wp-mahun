// teams.java
// make team of pieces
// Mason McIntyre

import java.util.*;

class teams extends piece
{
  ArrayList<piece> bteam;
  ArrayList<piece> rteam;
  static piece p;

  public teams()
  {
    bteam = new ArrayList<piece>(12);
    rteam = new ArrayList<piece>(12);
  }

  public teams(int size)
  {
    bteam = new ArrayList<piece>(size);
    rteam = new ArrayList<piece>(size);
  }
  
  public void buildteam(boolean teamname)
  {
    int piecename;
    if(teamname == true) //black
      {
	piecename = 65;
	for(int i = 0; i < 12; i++)
	  {
	    p = new piece((char)piecename);
	    p.team = true;
	    bteam.add(p);
	    piecename++;
	  }
      }
    if(teamname == false) //red
      {
	piecename = 97;
	for(int i = 0; i < 12; i++)
	  {
	    p = new piece((char)piecename);
	    p.team = false;
	    rteam.add(p);
	    piecename++;
	  }
      }
  }
  
  public void remove(piece value) //Gives Error
  {
    if(value.team == true)
      bteam.remove(value);
    if(value.team == false)
      rteam.remove(value);
  }

  public void dumpteam(boolean teamname)
  {
    piece temp;

    if(teamname == true)
      {
	for(int i = 0; i < 12; i++)
	  {
	    temp = (piece)bteam.get(i);
	    System.out.print(temp.name);
	  }
	System.out.println();
      }

    if(teamname == false)
      {
	for(int i = 0; i < 12; i++)
	  {
	    temp = (piece)rteam.get(i);
	    System.out.print(temp.name);
	  }
	System.out.println();
      }
  }

  void act()
  {
    
  }
}