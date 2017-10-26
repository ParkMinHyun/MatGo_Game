import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Audio_  extends Thread implements Runnable
{
   private int bgmEnd;
   private String bgmName;
   public Audio_(String bgm)
   {
     
      bgmName=bgm;
      start();
   }
   
   public void run()
   {
      int i=0;
        try
        {
            AudioInputStream bgm = AudioSystem.getAudioInputStream(new File(bgmName));//게임 실행 BGM AudioInputStream 객체에 주기
            Clip bgmClip = AudioSystem.getClip();//클립에 할당해주기
            bgmClip.open(bgm);//파일 열기
            bgmClip.start();
           while(true)
           {
              i++;
               try{
                   sleep(10);
               }
               catch(InterruptedException e){}
               
               if(i==1500)//3초일 때 				
               {
                  bgmClip.stop();//노래 멈추기
                  bgmClip.close();//노래 파일 닫기
                  i=0;
                  break;
               }
              }
        }
        catch (Exception ex)
        {
        }
    }
}