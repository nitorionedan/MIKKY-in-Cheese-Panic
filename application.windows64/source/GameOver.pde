class GameOver
{
  final int MAX_NUM = 10000;
  
  MyTwitter twi;
  Keyboard keyb;
  int score;
  Vector2D[] pos = new Vector2D[MAX_NUM];
  Vector2D vMouse;
  YImage g_cheese;
  
  
  GameOver()
  {    
    twi = new MyTwitter();
    keyb = new Keyboard();
    g_cheese = new YImage("cheese.png");
    vMouse = new Vector2D(0f, 0f);
    
    Initialize();
  }
  
  
  void Initialize()
  {
    for(int i = 0; i < MAX_NUM; i++)
    {
      float rnd_x = random(10, 630);
      float rnd_y = random(10, 470);
      pos[i] = new Vector2D(rnd_x, rnd_y);
    }
  }
  
  
  void Finalize()
  {
  }
  
  
  void Update(SceneMng sceneMng)
  {
    keyb.Update();
    
    if(keyb.KP() == 1)
    {
      twi.Tweet("Score:" + sceneMng.score + "\n");
      keyb.Initialize();
      sceneMng.ChangeScene(eStageScene.Menu);
    }
    
    if(keyb.KQ() == 1)
    {
      keyb.Initialize();
      sceneMng.ChangeScene(eStageScene.Menu);
    }
    
    if(mousePressed)  vMouse.SetVector(mouseX, mouseY);
    
    score = sceneMng.score;
  }
  
  
  void mousePressed()
  {
    if(mouseButton == LEFT)  vMouse.SetVector(mouseX, mouseY);
  }
  
  
  void Draw()
  {    
    for(int i = 0; i < (score / 100); i++)
    {
      g_cheese.DrawExRateGraphF(pos[i].x - random(mouseX - vMouse.x), pos[i].y - random(mouseY - vMouse.y), 1f);
    }
    
    text("Score:" + score, 20, 50);
    
    textSize(80);
    fill(255, 0, 0);
    text("CHEESE OVER", 50, 240);
    fill(0);
    
    textSize(20);
    text("Q KEY RETURN TITLE", 250, 300);
    text("P KEY TWEETING", 250, 330);
  }
}