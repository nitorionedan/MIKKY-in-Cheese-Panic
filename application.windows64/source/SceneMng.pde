enum eStageScene
{
  Menu,
  Game,
  GameOver,
  
  None,
}

class SceneMng
{
  Menu menu;
  Game game;
  GameOver gameOver;
  eStageScene scene;
  eStageScene nextScene;
  int score;
  
  SceneMng()
  {
    menu = new Menu();
    game = new Game();
    gameOver = new GameOver();
    
    // default scene
    scene = eStageScene.Menu;
  }
  
  void Initialize()
  {
    switch(scene)
    {
    case Menu:  menu.Initialize();  break;
    case Game:  game.Initialize();  break;
    case GameOver: gameOver.Initialize();  break;
    default:  break;
    }
  }
  
  void Finalize()
  {
    switch(scene)
    {
    case Game:  game.Finalize();  break;
    case GameOver: gameOver.Finalize();  break;
    default:  break;
    }
  }
  
  void Update()
  {    
    switch(scene)
    {
    case Menu:  menu.Update(this);  break;
    case Game:  game.Update(this);  break;
    case GameOver:  gameOver.Update(this);  break;
    default:  break;
    } 
  }
  
  void Draw()
  {
    fill(0);
    switch(scene)
    {
    case Menu:
    menu.Draw();
    //text("NOW_MENU_SCENE", 20, 20);
    break;
    
    case Game:
    game.Draw();
    //text("NOW_GAME_SCENE", 20, 20);
    break;
    
    case GameOver:
    gameOver.Draw();
    //text("NOW_GAMEOVER_SCENE", 20, 20);
    break;
    
    default:  break;
    }
  }
  
  void ChangeScene(eStageScene nextScene)
  {
    Initialize();
    scene = nextScene;
    Initialize();
    
    if(nextScene != eStageScene.GameOver)  score = 0;
  }
  
  
  void SetScore(int score)
  {
    this.score = score;
  }
}