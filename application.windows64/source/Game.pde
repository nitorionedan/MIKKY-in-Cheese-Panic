class Game
{
  Keyboard keyboard;
  Player player;
  Star star;
  Field field;
  MyTwitter twi;
  Drops drops;
  int timer, second, score;
  boolean isPause;
  
  
  Game()
  {
    keyboard = new Keyboard();
    player = new Player();
    star = new Star();
    field = new Field();
    twi = new MyTwitter();
    drops = new Drops();
    Initialize();
  }
  
  
  void Initialize()
  {
    timer = 0;
    second = 50;
    score = 0;
    drops.Initialize();
    star.Initialize();
    player.Initialize();
    isPause = false;
  }
  
  
  void Finalize()
  {
  }
  
  
  void Update(SceneMng secenMng)
  {
    if(timer % 60 == 0)  second--;
    if(second == 0)  GameOver();
    
    keyboard.Update();
    
    if (keyboard.KQ() == 1)  isPause = !isPause;
    
    if (isPause)  return;
    
    timer++;

    player.Update(this);
    star.Update(this);
    drops.Update(this);
    
    player.HitCheck(this);
    
    // TEST
    if (keyboard.KP() == 1)
    {
      
    }
  }
  
  
  void Draw()
  {
    field.Draw();
    player.Draw();
    drops.Draw();
    star.Draw();
    
    if(second <= 10)  fill(255, 0, 0);
    text("TIMER:" + second + "sec", 20, 20);
    fill(0);
    text("SCORE:" + score, 20, 50);
    
    if (isPause)  text("NOW PAUSING", 320, 240);
  }
  
  
  void GameOver()
  {
    sceneMng.SetScore(score);
    sceneMng.ChangeScene(eStageScene.GameOver);
  }
  
  
  Vector2D GetPlayerPos(){
    return player.pos;
  }
  
  
  Vector2D GetStarPos(){
    return star.pos;
  }
  
  Vector2D GetWarmPos(){
    return drops.GetWarmPos();
  }
  
  
  void AddScore(int score)
  {
    this.score += score;
  }
}