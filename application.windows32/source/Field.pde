class Field
{
  YImage img;
  
  
  Field()
  {
    img = new YImage("background.png");
  }
  
  
  void Initialize()
  {
  }
  
  
  void Update()
  {
  }
  
  
  void Draw()
  {
    img.DrawGraph(0, 0);
  }
}