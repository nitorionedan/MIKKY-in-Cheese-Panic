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
  
  
  void Update(Game game)
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
  
  void Draw()
  {
    if (state == ePlayerState.leftWalk && !isDown)  g_standl.DrawExRateGraphF(pos.x, pos.y, 1f);
    if (state == ePlayerState.rightWalk && !isDown) g_stand.DrawExRateGraphF(pos.x, pos.y, 1f);
    if (state == ePlayerState.leftWalk && isDown) g_downl.DrawExRateGraphF(pos.x, pos.y, 1f);
    if (state == ePlayerState.rightWalk && isDown) g_down.DrawExRateGraphF(pos.x, pos.y, 1f);
  
    // TEST
//    if (keyboard.RIGHT() == 0)  text("ok", 200, 200);
   // ellipse(pos.x, pos.y, 50, 50);
  }
  
  
  void CalVector()
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
  
  
  void Move()
  {    
    pos = vmove.AddVec(pos);
    pos.x = max(min(pos.x, RIGHT_END), LEFT_END);
  }
  
  
  void HitCheck(Game game)
  {
    boolean isHit = game.drops.IsHitWarm(pos);
    
    if(isHit)  game.GameOver();
  }
}