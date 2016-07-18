
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
 
  // @brief             ベクトルを設定する
  // @pram[in]  x_, y_  それぞれベクトルの方向
  // @out
  void SetVector(float x_, float y_)
  {
    x = x_;
    y = y_;
  }
  
  // @brief           Add vector
  // @param[in]  vec  足したいベクトル
  // @out             加算したベクトル
  Vector2D AddVec(Vector2D vec)
  {
    vec.x += this.x;
    vec.y += this.y;

    return vec;
  }
  
  
  Vector2D Getposition(Player player)
  {
    return player.pos;
  }
  
  
  boolean CirclePointCollision(Vector2D myPos, Vector2D colPos, float Radius)
  {
    final float WIDTH    = (colPos.x - myPos.x);
    final float HEIGHT   = (colPos.y - myPos.y);
    final float DISTANCE = (WIDTH + HEIGHT);
    final float RADIUS   = (Radius * Radius);
    final boolean IS_HIT = (RADIUS >= DISTANCE);
    
    return IS_HIT;
  }
  
  
  boolean CirclesCollision(float myRange, float colRange, Vector2D myPos, Vector2D colPos)
  {
    final float HIT_RANGE = (myRange + colRange);
    final float LEN_X = (myPos.x - colPos.x);
    final float LEN_Y = (myPos.y - colPos.y);
    final boolean IS_HIT = (pow(HIT_RANGE, 2) >= pow(LEN_X, 2) + pow(LEN_Y, 2));
    
    return IS_HIT;
  }
  
}

// EOF