// Pieces to be mapped to the board
// in this case checkers
// Developed by Mason McIntyre

class piece extends Object
{
  char name;
  boolean team, king, alive; //false == red : true == black
  int x, y;

  public piece()
  {
    name = '?';
    king = false;
    alive = true;
  }

  public piece(char n)
  {
    name = n;
    king = false;
    alive = true;
  }
  
  void act()
  {

  }
}