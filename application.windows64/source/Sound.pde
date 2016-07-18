import ddf.minim.*;


class Sound
{
  Minim minim;
  AudioPlayer player;

  
  Sound(String fname)
  {
    minim = new Minim(this);
    player = minim.loadFile(fname);
  }


  void Play()
  {
    player.play();
  }
  
  
  void Close()
  {
    player.close();
    minim.stop();
  }
}