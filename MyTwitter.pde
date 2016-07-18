/*
  @brief    Twitter class
  @sanko    https://t.co/0NSg6hYQ17(2016/6/25 Access)
  @sanko    https://t.co/KNK1bYiZ9R(2016/6/25 Access)
  @warning  これらは上記のサイトを参考にしたものです。僕のオリジナルではありません。
*/

import twitter4j.*;
 

class MyTwitter
{ 
  String consumerKey = "5DRncitwX8rkA2Xq0ZNqE9nRR";
  String consumerSecret = "7eRaVAps3OPTeTScWLtv9YiLhXgv9bJIMJeLMumK6emODRDPRY";
  String accessToken = "754875835056656384-Qprt4cbdAlT5Zr1DJv8lDoiRK8zJP4D";
  String accessSecret = "Dnuud7K7U3xws7ndOg48ETLPYByLAtxWYMn5lI1omkeqC";
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
  
  
  void Tweet(String msg)
  {
    try
    {
      myTwitter.updateStatus(msg + ", " + getDate() + "\n#星のミッキィ");
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