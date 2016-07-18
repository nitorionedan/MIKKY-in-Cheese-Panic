import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 
import twitter4j.*; 
import ddf.minim.*; 

import twitter4j.api.*; 
import twitter4j.*; 
import twitter4j.auth.*; 
import twitter4j.conf.*; 
import twitter4j.json.*; 
import twitter4j.management.*; 
import twitter4j.util.*; 
import twitter4j.util.function.*; 
import twitter4j.examples.account.*; 
import twitter4j.examples.async.*; 
import twitter4j.examples.block.*; 
import twitter4j.examples.directmessage.*; 
import twitter4j.examples.*; 
import twitter4j.examples.favorite.*; 
import twitter4j.examples.friendsandfollowers.*; 
import twitter4j.examples.friendship.*; 
import twitter4j.examples.geo.*; 
import twitter4j.examples.help.*; 
import twitter4j.examples.json.*; 
import twitter4j.examples.lambda.*; 
import twitter4j.examples.list.*; 
import twitter4j.examples.media.*; 
import twitter4j.examples.oauth.*; 
import twitter4j.examples.savedsearches.*; 
import twitter4j.examples.search.*; 
import twitter4j.examples.spamreporting.*; 
import twitter4j.examples.stream.*; 
import twitter4j.examples.suggestedusers.*; 
import twitter4j.examples.timeline.*; 
import twitter4j.examples.trends.*; 
import twitter4j.examples.tweets.*; 
import twitter4j.examples.user.*; 
import twitter4j.media.*; 
import twitter4j.http.*; 
import twitter4j.internal.async.*; 
import twitter4j.internal.http.*; 
import twitter4j.internal.logging.*; 
import twitter4j.internal.org.json.*; 
import twitter4j.internal.util.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Main extends PApplet {



Minim minim;
AudioPlayer player;

SceneMng sceneMng;


public void setup()
{
  
  frameRate(60);
  textSize(20);
  fill(0);

  Initialize();
}


public void Initialize()
{
  sceneMng = new SceneMng();
  minim = new Minim(this);
  player = minim.loadFile("inst.mp3");
  player.loop();
}


public void dispose()
{
  sceneMng.Finalize();
  player.close();
  minim.stop();
  super.stop();
  println("exited");
}


public void draw()
{
  background(255);

  Update();
  Draw();
}


public void Update()
{
  sceneMng.Update();
}


public void Draw()
{
  sceneMng.Draw();
}


// EOF
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
  
  
  public void Initialize()
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
  
  
  public void Update(Game game)
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
  
  
  public void Draw()
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
 
  
  public void Move(int id)
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
  
  
  public void Create()
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
  
  
  public void HitCheck(int id, Game game)
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
  
  public Vector2D GetWarmPos()
  {
    for(int i = 0; i < MAX_NUM; i++)
    {
      if(!isExist[i])  continue;
      
      if(isWarm[i])  return pos[i];
    }
    
    Vector2D fake = new Vector2D(-10f, -10f);
    return fake;
  }
  
  public boolean IsHitWarm(Vector2D col)
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
class Field
{
  YImage img;
  
  
  Field()
  {
    img = new YImage("background.png");
  }
  
  
  public void Initialize()
  {
  }
  
  
  public void Update()
  {
  }
  
  
  public void Draw()
  {
    img.DrawGraph(0, 0);
  }
}
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
  
  
  public void Initialize()
  {
    timer = 0;
    second = 50;
    score = 0;
    drops.Initialize();
    star.Initialize();
    player.Initialize();
    isPause = false;
  }
  
  
  public void Finalize()
  {
  }
  
  
  public void Update(SceneMng secenMng)
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
  
  
  public void Draw()
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
  
  
  public void GameOver()
  {
    sceneMng.SetScore(score);
    sceneMng.ChangeScene(eStageScene.GameOver);
  }
  
  
  public Vector2D GetPlayerPos(){
    return player.pos;
  }
  
  
  public Vector2D GetStarPos(){
    return star.pos;
  }
  
  public Vector2D GetWarmPos(){
    return drops.GetWarmPos();
  }
  
  
  public void AddScore(int score)
  {
    this.score += score;
  }
}
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
  
  
  public void Initialize()
  {
    for(int i = 0; i < MAX_NUM; i++)
    {
      float rnd_x = random(10, 630);
      float rnd_y = random(10, 470);
      pos[i] = new Vector2D(rnd_x, rnd_y);
    }
  }
  
  
  public void Finalize()
  {
  }
  
  
  public void Update(SceneMng sceneMng)
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
  
  
  public void mousePressed()
  {
    if(mouseButton == LEFT)  vMouse.SetVector(mouseX, mouseY);
  }
  
  
  public void Draw()
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
  
  public void Initialize()
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
  
  public void Update()
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
  
  public int LEFT()
  {
    return left;
  }
  
  public int RIGHT()
  {
    return right;
  }
  
  public int UP()
  {
    return up;
  }
  
  public int DOWN()
  {
    return down;
  }
  
  public int SPACE()
  {
    return space;
  }
  
  public int KZ()
  {
    return kz;
  }
  
  public int KX()
  {
    return kx;
  }
  
  public int KC()
  {
    return kc;
  }
  
  public int KQ()
  {
    return kq;
  }
  
  public int KP()
  {
    return kp;
  }
}
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
  
  
  public void Initialize()
  {
    int rota = 0;
    isRota = false;
  }
  
  public void Update(SceneMng sceneMng)
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
  
  public void Draw()
  {
    textSize(80);
    text("Hoshi no Mikky!", 0, 240);
 
    g_title.DrawGraph(0, 0);
    
    textSize(20);
    text("Z KEY START", 260, 290);
    
    g_star.DrawRotaGraphF(mouseX - 20, mouseY - 20, 1f, rota);
  }
}
/*
  @brief    Twitter class
  @sanko    https://t.co/0NSg6hYQ17(2016/6/25 Access)
  @sanko    https://t.co/KNK1bYiZ9R(2016/6/25 Access)
  @warning  \u3053\u308c\u3089\u306f\u4e0a\u8a18\u306e\u30b5\u30a4\u30c8\u3092\u53c2\u8003\u306b\u3057\u305f\u3082\u306e\u3067\u3059\u3002\u50d5\u306e\u30aa\u30ea\u30b8\u30ca\u30eb\u3067\u306f\u3042\u308a\u307e\u305b\u3093\u3002
*/


 

class MyTwitter
{ 
  String consumerKey = "8rLwPJQM14h4J3sXOY01FOEAa";
  String consumerSecret = "XG6FZmXETtFIXdPXUrKRIVLoL9FYlwgTDQGmnatJl3KBgDNUGB";
  String accessToken = "449447843-CH7FmWBuuR3iFHnLJnBSbzn3cq3f9Pg48TBRToD1";
  String accessSecret = "ciHwgzQGVK7qlenaJB9RbRDc8cR0fd4pNxRFBUEBcTTcc";
  Twitter myTwitter;
 
  int ms;
  
  
  MyTwitter()
  {
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setOAuthConsumerKey(consumerKey);
    cb.setOAuthConsumerSecret(consumerSecret);
    cb.setOAuthAccessToken(accessToken);
    cb.setOAuthAccessTokenSecret(accessSecret);
    myTwitter = new TwitterFactory(cb.build()).getInstance();
  }
  
  
  public void Tweet(String msg)
  {
    try
    {
      myTwitter.updateStatus(msg + ", " + getDate() + "\n#\u661f\u306e\u30df\u30c3\u30ad\u30a3");
      //Status st = myTwitter.updateStatus(msg + ", " + getDate());
      println("\"" + msg + "\"->Success!");
    }
    catch (TwitterException e)
    {
      println(e.getStatusCode());
    }
  }
  
  
  private String getDate()
  {
    String monthStr, dayStr, hourStr, minuteStr, secondStr;
 
    if (month() < 10)  monthStr = "0" + month();
    else  monthStr = "" + month();
    
    if (day() < 10)  dayStr = "0" + day();
    else  dayStr = "" + day();
    
    if (hour() < 10)  hourStr = "0" + hour();
    else  hourStr = "" + hour();
    
    if (minute() < 10)  minuteStr = "0" + minute();
    else  minuteStr = "" + minute();
    
    if (second() < 10)  secondStr = "0" + second();
    else  secondStr = "" + second();
 
    return monthStr + "/" + dayStr + ", " + hourStr + ":" + minuteStr + ":" + secondStr;
  }
}

// EOF
enum ePlayerState
{
  rightWalk,
  leftWalk,
  rightDash,
  leftDash,
  down,
  
  neutral,
}

class Player
{
  final float GRAVITY = 1f;
  final float MASS = 1f;
  final float GROUND = 364f;
  final float WALK_ACCEL = 0.2f;
  final float DASH_ACCEL = 0.4f;
  final float FALL_ACCEL = MASS * GRAVITY;
  final float MAX_WALK_SPEED = 3f;
  final float MAX_DASH_SPEED = 4f;
  final float MAX_FALL_SPEED = 7f;
  final float BRAKE = 0.05f;  
  final float JUMP_FORCE = -4f;
  final float MAX_JUMP_FORCE = -15f;
  final float LEFT_END = 19.6f;
  final float RIGHT_END = 618f;
  
  YImage g_stand,
         g_standl,
         g_down,
         g_downl;
  Vector2D pos;
  Vector2D vmove, vWarm;
  Keyboard keyboard;
  ePlayerState state;
  int elapsedTime;
  boolean isJump;
  boolean isJumpOK;
  boolean isDash;
  boolean isWalk;
  boolean isLeft;
  boolean isDown;


  Player()
  {
    g_stand = new YImage("mikky.png");
    g_standl = new YImage("mikkyl.png");
    g_down = new YImage("mikky_down.png");
    g_downl = new YImage("mikky_downl.png");
    pos = new Vector2D(100f, GROUND);
    vmove = new Vector2D(0f, 0f);
    keyboard = new Keyboard();
    state = ePlayerState.leftWalk;
    Initialize();
  }
  
  
  private void Initialize()
  {
    elapsedTime = 0;
    isJump = false;
    isJumpOK = true;
    isDash = false;
    isWalk = false;
    isLeft = false;
    isDown = false;
  }
  
  
  public void Update(Game game)
  {
    keyboard.Update();
 
    if (keyboard.RIGHT() == 1 || keyboard.RIGHT() == 1)  isWalk = true;
    if (keyboard.RIGHT() == 0 && keyboard.LEFT() == 0)  isWalk = false;
    if (keyboard.RIGHT() == 1) state = ePlayerState.rightWalk;
    if (keyboard.LEFT() == 1) state = ePlayerState.leftWalk;
    if (keyboard.DOWN() >= 1 && !isJump) isDown = true;
    if (keyboard.DOWN() == 0) isDown = false;
 
    CalVector();

    vWarm = game.GetWarmPos();

    Move();  
   // HitCheck(game);
  }
  
  public void Draw()
  {
    if (state == ePlayerState.leftWalk && !isDown)  g_standl.DrawExRateGraphF(pos.x, pos.y, 1f);
    if (state == ePlayerState.rightWalk && !isDown) g_stand.DrawExRateGraphF(pos.x, pos.y, 1f);
    if (state == ePlayerState.leftWalk && isDown) g_downl.DrawExRateGraphF(pos.x, pos.y, 1f);
    if (state == ePlayerState.rightWalk && isDown) g_down.DrawExRateGraphF(pos.x, pos.y, 1f);
  
    // TEST
//    if (keyboard.RIGHT() == 0)  text("ok", 200, 200);
   // ellipse(pos.x, pos.y, 50, 50);
  }
  
  
  public void CalVector()
  {
    // braking
    switch(state)
    {
    case leftWalk:
    if (vmove.x < 0f)  vmove.x += BRAKE;
    if (vmove.x > 0f)  vmove.x = 0f;
    break;
    
    case rightWalk:
    if (vmove.x > 0f)  vmove.x -= BRAKE;
    if (vmove.x < 0f)  vmove.x = 0f;        
    break;
    
    default:  break;
    }
    
    // fall
    if (isJump)
    {
      vmove.y += FALL_ACCEL;
      if (vmove.y > MAX_FALL_SPEED)  vmove.y = MAX_FALL_SPEED;
      if (pos.y > GROUND)
      {
        vmove.y = 0f;
        pos.y = GROUND;
        isJump = false;
      }
    }
 
    // right walk
    if (keyboard.RIGHT() >= 1)
    {
      if (vmove.x < MAX_WALK_SPEED)  vmove.x += WALK_ACCEL;
      if (vmove.x > MAX_WALK_SPEED)  vmove.x = MAX_WALK_SPEED;
    }
    
    // left walk
    if (keyboard.LEFT() >= 1)
    {
      if (vmove.x > -MAX_WALK_SPEED)  vmove.x -= WALK_ACCEL;
      if (vmove.x < -MAX_WALK_SPEED)  vmove.x = -MAX_WALK_SPEED;
    }
    
    // jump
    if (keyboard.UP() >= 1 && !isJump && isJumpOK)
    {
      vmove.y += JUMP_FORCE;
      
      if(vmove.y < MAX_JUMP_FORCE)
      {  
        vmove.y = MAX_JUMP_FORCE; 
        isJump = true;
        isJumpOK = false;
      }
    }
    
    if (keyboard.UP() == 0 && pos.y < GROUND && !isJump)
    {
      isJump = true;
      isJumpOK = false;
    }
    
    if (keyboard.UP() == 0 && !isJump)  isJumpOK = true;
  }
  
  
  public void Move()
  {    
    pos = vmove.AddVec(pos);
    pos.x = max(min(pos.x, RIGHT_END), LEFT_END);
  }
  
  
  public void HitCheck(Game game)
  {
    boolean isHit = game.drops.IsHitWarm(pos);
    
    if(isHit)  game.GameOver();
  }
}
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
  
  public void Initialize()
  {
    switch(scene)
    {
    case Menu:  menu.Initialize();  break;
    case Game:  game.Initialize();  break;
    case GameOver: gameOver.Initialize();  break;
    default:  break;
    }
  }
  
  public void Finalize()
  {
    switch(scene)
    {
    case Game:  game.Finalize();  break;
    case GameOver: gameOver.Finalize();  break;
    default:  break;
    }
  }
  
  public void Update()
  {    
    switch(scene)
    {
    case Menu:  menu.Update(this);  break;
    case Game:  game.Update(this);  break;
    case GameOver:  gameOver.Update(this);  break;
    default:  break;
    } 
  }
  
  public void Draw()
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
  
  public void ChangeScene(eStageScene nextScene)
  {
    Initialize();
    scene = nextScene;
    Initialize();
    
    if(nextScene != eStageScene.GameOver)  score = 0;
  }
  
  
  public void SetScore(int score)
  {
    this.score = score;
  }
}



class Sound
{
  Minim minim;
  AudioPlayer player;

  
  Sound(String fname)
  {
    minim = new Minim(this);
    player = minim.loadFile(fname);
  }


  public void Play()
  {
    player.play();
  }
  
  
  public void Close()
  {
    player.close();
    minim.stop();
  }
}
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
  
  
  public void Initialize()
  {
  }
  
  
  public void Update(Game game)
  {
    vplayer = game.GetPlayerPos();
    HitCheck(game);
    Move();
  }
  
  
  public void Draw()
  {
    img.DrawRotaGraphF(pos.x, pos.y, 1f, rota);
    //ellipse(pos.x + 20, pos.y + 20, 30, 30);
  }
  
  
  public void Move()
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
  
  public void HitCheck(Game game)
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

class Vector2D
{
  float x, y;
  
  Vector2D(float x_, float y_)
  {
    x = x_;
    y = y_;
  }
  
  Vector2D()
  {
  }
 
  // @brief             \u30d9\u30af\u30c8\u30eb\u3092\u8a2d\u5b9a\u3059\u308b
  // @pram[in]  x_, y_  \u305d\u308c\u305e\u308c\u30d9\u30af\u30c8\u30eb\u306e\u65b9\u5411
  // @out
  public void SetVector(float x_, float y_)
  {
    x = x_;
    y = y_;
  }
  
  // @brief           Add vector
  // @param[in]  vec  \u8db3\u3057\u305f\u3044\u30d9\u30af\u30c8\u30eb
  // @out             \u52a0\u7b97\u3057\u305f\u30d9\u30af\u30c8\u30eb
  public Vector2D AddVec(Vector2D vec)
  {
    vec.x += this.x;
    vec.y += this.y;

    return vec;
  }
  
  
  public Vector2D Getposition(Player player)
  {
    return player.pos;
  }
  
  
  public boolean CirclePointCollision(Vector2D myPos, Vector2D colPos, float Radius)
  {
    final float WIDTH    = (colPos.x - myPos.x);
    final float HEIGHT   = (colPos.y - myPos.y);
    final float DISTANCE = (WIDTH + HEIGHT);
    final float RADIUS   = (Radius * Radius);
    final boolean IS_HIT = (RADIUS >= DISTANCE);
    
    return IS_HIT;
  }
  
  
  public boolean CirclesCollision(float myRange, float colRange, Vector2D myPos, Vector2D colPos)
  {
    final float HIT_RANGE = (myRange + colRange);
    final float LEN_X = (myPos.x - colPos.x);
    final float LEN_Y = (myPos.y - colPos.y);
    final boolean IS_HIT = (pow(HIT_RANGE, 2) >= pow(LEN_X, 2) + pow(LEN_Y, 2));
    
    return IS_HIT;
  }
  
}

// EOF
class YImage
{
  PImage img;
  Integer wid, hei;
  float wid2, hei2;
  
  YImage(String fname)
  {
    img = loadImage(fname);
    wid = img.width;
    hei = img.height;
    wid2 = wid / 2f;
    hei2 = hei / 2f;
  }
  
  public void DrawGraph(int X, int Y)
  {
    image(img, X, Y);
  }
  
  public void DrawGraphF(float X, float Y)
  {
    image(img, X, Y);
  }
  
  public void DrawExRateGraph(int X, int Y, float ExRate)
  {
     image(img, X - wid2, Y - hei2, wid * ExRate, hei * ExRate);
  }
  
  public void DrawExRateGraphF(float X, float Y, float ExRate)
  {
     image(img, X - wid2, Y - hei2, wid * ExRate, hei * ExRate);
  }
  
  public void DrawRotaGraph(int X, int Y, float ExRate, float Rotate)
  {
    pushMatrix();
    
    translate(X + wid2, Y + hei2);
    
    rotate( radians( map(Rotate, 1, 30, 0, 360) ) );
//    rotate( radians(Rotate) );
    
    imageMode(CENTER);
    
    image(img, 0, 0, 1, 1);
    
    imageMode(CORNER);
    translate( -(X + wid2), -( Y + hei2) );
    
    popMatrix();
  }
  
  public void DrawRotaGraphF(float X, float Y, float ExRate, float Rotate)
  {
    pushMatrix();
    
    translate(X + wid2, Y + hei2);
    
    rotate( radians( map(Rotate, 1, 30, 0, 360) ) );
    
    imageMode(CENTER);
    
    image(img, 0, 0, wid * ExRate, hei * ExRate);
    
    imageMode(CORNER);
    translate( -(X + wid2), -( Y + hei2) );
    
    popMatrix();
  }
  
  public int GetSizeX(){
    return wid;
  }
  
  public int GetSizeY(){
    return hei;
  }
}
  public void settings() {  size(640, 480); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Main" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
