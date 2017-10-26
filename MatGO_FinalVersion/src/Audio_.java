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
            AudioInputStream bgm = AudioSystem.getAudioInputStream(new File(bgmName));//���� ���� BGM AudioInputStream ��ü�� �ֱ�
            Clip bgmClip = AudioSystem.getClip();//Ŭ���� �Ҵ����ֱ�
            bgmClip.open(bgm);//���� ����
            bgmClip.start();
           while(true)
           {
              i++;
               try{
                   sleep(10);
               }
               catch(InterruptedException e){}
               
               if(i==1500)//3���� �� 				
               {
                  bgmClip.stop();//�뷡 ���߱�
                  bgmClip.close();//�뷡 ���� �ݱ�
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