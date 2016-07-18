class Star
{
  final float GROUND = 364f;
  final float CEILING = 10f;
  final float RIGHT_END = 598f;
  final float LEFT_END = -6f;
  
  YImage img;
  Vector2D pos, vmove, vplayer, vforce;
  float rota;
  
  
  Star()
  {
    img = new YImage("starball.png");
    pos = new Vector2D(320f, 240f);
    vmove = new Vector2D(-1f, 1f);
    vplayer = new Vector2D(0f, 0f);
    vforce = new Vector2D(0.5f, -10f);
    Initialize();
  }
  
  
  void Initialize()
  {
  }
  
  
  void Update(Game game)
  {
    vplayer = game.GetPlayerPos();
    HitCheck(game);
    Move();
  }
  
  
  void Draw()
  {
    img.DrawRotaGraphF(pos.x, pos.y, 1f, rota);
    //ellipse(pos.x + 20, pos.y + 20, 30, 30);
  }
  
  
  void Move()
  {
    // moving
    pos = vmove.AddVec(pos);
    
    vmove.y += 0.2f;
    
    // reflection
    if (pos.x > RIGHT_END || pos.x < LEFT_END)  vmove.x *= -1f;
    if (pos.y < CEILING)  vmove.y *= -1f;
    if (pos.y > GROUND)  vmove.y = vforce.y;
    
    // rotation
    if (vmove.x >= 0){
      rota += 0.1f + abs(vmove.x) / 10;
    }else{
      rota -= 0.1f + abs(vmove.x) / 10;
    }
    
    if (pos.y < CEILING)  pos.y = CEILING;
    if (pos.x < LEFT_END - 20f) pos.x = RIGHT_END - 1f;
    if (pos.x > RIGHT_END + 20f) pos.x = LEFT_END + 1f;
  }
  
  void HitCheck(Game game)
  {
    Vector2D add = new Vector2D(20f, 20f);
    boolean isHit = vmove.CirclesCollision(30, 30, pos.AddVec(add), vplayer);
    
    if (isHit)
    {
      if (vplayer.x >= pos.x)
      {
        vmove.x += -0.5f;
        if (vmove.x > 0f)  vmove.x *= -1;
      }
      else
      {
        vmove.x += 0.5f;
        
        if (vmove.x < 0f)  vmove.x *= -1;
      }
      
      vmove.y -= abs(vmove.y) / 5f;
      
      game.AddScore(10);
      }
  }
}