class Keyboard
{  
  private int right;
  private int left;
  private int up;
  private int down;
  private int space;
  private int kz;    // Z key
  private int kx;
  private int kc;
  private int kq;
  private int kp;
  
  Keyboard()
  {
  }
  
  void Initialize()
  {
    right = 0;
    left = 0;
    up = 0;
    down = 0;
    space = 0;
    kz = 0;
    kx = 0;
    kc = 0;
    kq = 0;
    kp = 0;
  }
  
  void Update()
  {
    if (!keyPressed)  Initialize();
    
    if (keyPressed)
    {
      if (key == ' ') space++;
      if (key == 'z') kz++;
      if (key == 'x') kx++;
      if (key == 'c') kc++;
      if (key == 'q') kq++;
      if (key == 'p') kp++;
      if (keyCode == RIGHT) right++;
      if (keyCode == LEFT)  left++;
      if (keyCode == UP)    up++;
      if (keyCode == DOWN)  down++;
    }
  }
  
  int LEFT()
  {
    return left;
  }
  
  int RIGHT()
  {
    return right;
  }
  
  int UP()
  {
    return up;
  }
  
  int DOWN()
  {
    return down;
  }
  
  int SPACE()
  {
    return space;
  }
  
  int KZ()
  {
    return kz;
  }
  
  int KX()
  {
    return kx;
  }
  
  int KC()
  {
    return kc;
  }
  
  int KQ()
  {
    return kq;
  }
  
  int KP()
  {
    return kp;
  }
}