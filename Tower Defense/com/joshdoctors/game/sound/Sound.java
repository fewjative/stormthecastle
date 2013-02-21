package com.joshdoctors.game.sound;

import java.io.IOException;
import java.net.URL;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Receiver;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Transmitter;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.joshdoctors.game.gfx.Screen;

public enum Sound {

	EXPLOSION("/res/sounds/Explosion.wav"),LASER4("/res/sounds/Laser_Shoot4.wav"),LASER5("/res/sounds/Laser_Shoot5.wav"),COIN("/res/sounds/Pickup_Coin.wav"),POWERUP("/res/sounds/Powerup.wav"),BLIP7("/res/sounds/Blip_Select7.wav"),
	MENU("/res/sounds/heroicminority.wav"),GAME("/res/sounds/The Fall of Arcana.wav"),CLICK2("/res/sounds/menuselect.wav"),BUY("/res/sounds/chaching.wav"),CLICK("/res/sounds/click.wav"),HELIMOVING("/res/sounds/helimoving.wav"),TANKMOVING("/res/sounds/tankmoving2.wav"),EXPLOSION2("/res/sounds/explosion2.wav"),
	ERROR("/res/sounds/error.wav");
	


  private Sequence sequence;
  private Sequencer sequencer;
  private Thread thread;
  private Clip clip;
  private boolean isMidi = false;
  private int soundTime=1000,soundFrame=0,soundRun=0;
  private float newVol=0.0f;

  private Sound(String path) {
    URL url = Sound.class.getResource(path);
   
    try {
 
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
        DataLine.Info info = new DataLine.Info(Clip.class, audioIn.getFormat());
        clip = (Clip) AudioSystem.getLine(info);
        clip.open(audioIn);
//        FloatControl gainControl = 
//      		    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
//      		gainControl.setValue(-10.0f); // Reduce volume by 10 decibels.
//      		System.out.println(clip.getMicrosecondLength());
        audioIn.close();
      }
     catch (IOException e) {
      e.printStackTrace();
    } catch (LineUnavailableException e) {
      e.printStackTrace();
    } catch (UnsupportedAudioFileException e) {
      e.printStackTrace();
    }
  }
  
  public void setVolume(float db)
  {
	  FloatControl gainControl = 
 		    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
 		gainControl.setValue(db); // Reduce volume by 10 decibels.
  }
  
  public float getVolume()
  {
	  FloatControl gainControl = 
 		    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
 		return gainControl.getValue(); // Reduce volume by 10 decibels.
  }

  public void play() {
    this.thread = new Thread() {
      public void run() {
       
          clip.start();
          int timer = 5;
          while (clip != null && clip.isActive()) {
            try {
              Thread.sleep(500);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }    
      	}
	    };
	    thread.start();
   }
  
  public void fade()
  {
//	  while(soundRun <30)
//	  {
//		  
//			if(soundFrame >=soundTime)
//			{
//				setVolume(-50.0f);
//				soundFrame=0;
//			}
//			else
//			{
//				soundFrame+=1;
//				 soundRun++;
//			}
//	  
//		 
//	  }
	  while(getVolume() >-60.0f)
	  {
		 
		  if(soundFrame >=soundTime)
				{
					setVolume(getVolume()-0.1f);
					soundFrame=0;
				}
				else
				{
					soundFrame+=1;
					 soundRun++;
				}
	  		}
	//setVolume(-50.0f);
	  soundRun=0;
	 
  }

  
  
  public void playOnce() 
  {
	  play();
	  reset();
  }
  
  public boolean isActive()
  {
	  if(clip.isActive())
		  return true;
	  else return false;
  }

  public void loop() {
    if (this.thread == null) {
      reset();
      this.thread = new Thread() {
        public void run() {
          
            clip.loop(Clip.LOOP_CONTINUOUSLY);
          
        }
      };
      thread.start();
    }
  }

  public void stop() {
 
      clip.stop();

  }

  public void reset() {
    stop();
      clip.setMicrosecondPosition(0);
  }
}