class Menu
{
  Keyboard keyb;
  YImage g_star;
  YImage g_title;
  float rota;
  boolean isRota;
  
  
  Menu()
  {
    keyb = new Keyboard();
    g_star = new YImage("starball.png");
    g_title = new YImage("title.png");
    
    Initialize();
  }
  
  
  void Initialize()
  {
    int rota = 0;
    isRota = false;
  }
  
  void Update(SceneMng sceneMng)
  {
    keyb.Update();
    
    if(keyb.KZ() == 1)
    {
      sceneMng.ChangeScene(eStageScene.Game);
      keyb.kz = 0;
    }
    
    if(isRota)  rota += 0.5f;
    
    if(mousePressed)  isRota = !isRota;
  }
  
  void Draw()
  {
    textSize(80);
    text("Hoshi no Mikky!", 0, 240);
 
    g_title.DrawGraph(0, 0);
    
    textSize(20);
    text("Z KEY START", 260, 290);
    
    g_star.DrawRotaGraphF(mouseX - 20, mouseY - 20, 1f, rota);
  }
}