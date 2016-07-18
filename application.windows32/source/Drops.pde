class Drops
{
  final float CEILING = 10f;
  final float GROUND = 480f;
  final int MAX_NUM = 200;
  Vector2D[] pos = new Vector2D[MAX_NUM];
  Vector2D[] vSmash = new Vector2D[MAX_NUM];
  Vector2D vStar, vPlayer;
  boolean[] isExist = new boolean[MAX_NUM];
  boolean[] isWarm = new boolean[MAX_NUM];
  boolean[] isHit = new boolean[MAX_NUM];
  YImage g_warm, g_cheese;
  int time, rank;
  
  
  Drops()
  {
    g_warm = new YImage("warm.png");
    g_cheese = new YImage("cheese.png");
    
    for(int i = 0; i < MAX_NUM; i++)
    {
      pos[i] = new Vector2D(0f, 0f);
      vSmash[i] = new Vector2D(0f, 0f);
    }
    
    Initialize();
  }
  
  
  void Initialize()
  { 
    for(int i = 0; i < MAX_NUM; i++)
    {
      pos[i].SetVector(0f, CEILING);
      vSmash[i].SetVector(0f, 0f);
      isExist[i] = false;
      isHit[i] = false;
    }
    
    rank = 0;
    time = 0;
  }
  
  
  void Update(Game game)
  {
    time++;
    if(time <= 600)
    {
      if(time % 120 == 0)  Create();
    }
    else if(time <= 900)
    {
      if(time % 60 == 0) Create();
    }
    else if(time <= 1500)
    {
      if(time % 30 == 0) Create();
    }
    else if(time <= 1800)
    {
      if(time % 10 == 0) Create();
    }
    else
    {
      Create();
    }
    
    vStar = game.GetStarPos();
    vPlayer = game.GetPlayerPos();
    
    for(int i = 0; i < MAX_NUM; i++)
    {
      if(!isExist[i])  continue;
      
      Move(i);
      HitCheck(i, game);
    }    
  }
  
  
  void Draw()
  {
    for(int i = 0; i < MAX_NUM; i++)
    {
      if(!isExist[i])  continue;
      
      if(isWarm[i])
      {
        g_warm.DrawExRateGraphF(pos[i].x, pos[i].y, 1f);
      }
      else
      {
        g_cheese.DrawExRateGraphF(pos[i].x, pos[i].y, 1f);
      }
    }
  }
 
  
  void Move(int id)
  {
    if(time <= 1600)  pos[id].y += 1f;
    else  pos[id].y += 2f;
    
    if(isWarm[id])  pos[id].y += 0.5f;
    
    if(isHit[id])
    {
      //pos[id] = pos[id].AddVec(vSmash[id]);
      pos[id].x += vSmash[id].x;
      pos[id].y += vSmash[id].y;
    }
    
    // reset
    if(pos[id].y > GROUND)  isExist[id] = false;
    if(pos[id].x < -10f || pos[id].x > 650f)
    {
      isExist[id] = false;
      isHit[id] = false;
      vSmash[id].SetVector(0f, 0f);
    }
    
    if(isHit[id] && pos[id].y < -15f)
    {
      isExist[id] = false;
      isHit[id] = false;
      vSmash[id].SetVector(0f, 0f);
    }
  }
  
  
  void Create()
  {
    for(int i = 0; i < MAX_NUM; i++)
    {
      if(isExist[i])  continue;
      
      isExist[i] = true;
      
      pos[i].x = random(10, 630);
      pos[i].y = -15f;
      
      float rnd = random(10);
      
      if(rnd >= 0 && rnd < 2)
      {
        isWarm[i] = true;
      }
      else
      {
        isWarm[i] = false;
      }
      
      break;
    }
  }
  
  
  void HitCheck(int id, Game game)
  {
    boolean isHit1 = pos[id].CirclesCollision(30, 30, pos[id], vStar);
    boolean isHit2 = pos[id].CirclesCollision(30, 30, pos[id], vPlayer);
    
    if(isHit1)
    {
      //if(!isWarm[id])isExist[id] = false;
      
      game.AddScore(15);
      
      if(true)
      {
        isHit[id] = true;
        float rnd_x = random(-6, 6);
        vSmash[id].SetVector(rnd_x, -6f);
      }
    }
    
    if(isHit2)
    {
      if(!isWarm[id])  isExist[id] = false;
      if(!isWarm[id])  game.AddScore(100);
    }
  }
  
  Vector2D GetWarmPos()
  {
    for(int i = 0; i < MAX_NUM; i++)
    {
      if(!isExist[i])  continue;
      
      if(isWarm[i])  return pos[i];
    }
    
    Vector2D fake = new Vector2D(-10f, -10f);
    return fake;
  }
  
  boolean IsHitWarm(Vector2D col)
  {
    for(int i = 0; i < MAX_NUM; i++)
    {
      if(!isExist[i])  continue;
      if(!isWarm[i])  continue;
      if(isHit[i])  continue;
         
      boolean isHit = pos[i].CirclesCollision(20, 20, pos[i], col);
 
      if(isHit)  return isHit;
    }
     
    return false;
  }
}