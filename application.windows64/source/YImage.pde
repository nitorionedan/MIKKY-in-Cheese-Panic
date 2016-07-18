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
  
  void DrawGraph(int X, int Y)
  {
    image(img, X, Y);
  }
  
  void DrawGraphF(float X, float Y)
  {
    image(img, X, Y);
  }
  
  void DrawExRateGraph(int X, int Y, float ExRate)
  {
     image(img, X - wid2, Y - hei2, wid * ExRate, hei * ExRate);
  }
  
  void DrawExRateGraphF(float X, float Y, float ExRate)
  {
     image(img, X - wid2, Y - hei2, wid * ExRate, hei * ExRate);
  }
  
  void DrawRotaGraph(int X, int Y, float ExRate, float Rotate)
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
  
  void DrawRotaGraphF(float X, float Y, float ExRate, float Rotate)
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
  
  int GetSizeX(){
    return wid;
  }
  
  int GetSizeY(){
    return hei;
  }
}