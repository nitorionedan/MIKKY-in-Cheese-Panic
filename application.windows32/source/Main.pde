import ddf.minim.*;

Minim minim;
AudioPlayer player;

SceneMng sceneMng;


void setup()
{
  size(640, 480);
  frameRate(60);
  textSize(20);
  fill(0);

  Initialize();
}


void Initialize()
{
  sceneMng = new SceneMng();
  minim = new Minim(this);
  player = minim.loadFile("inst.mp3");
  player.loop();
}


void dispose()
{
  sceneMng.Finalize();
  player.close();
  minim.stop();
  super.stop();
  println("exited");
}


void draw()
{
  background(255);

  Update();
  Draw();
}


void Update()
{
  sceneMng.Update();
}


void Draw()
{
  sceneMng.Draw();
}


// EOF