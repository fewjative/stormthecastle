package com.joshdoctors.game.gfx;

import java.awt.*;
import java.awt.image.*;
import java.io.File;

import javax.swing.*;

import com.joshdoctors.game.Game;
import com.joshdoctors.game.Menu;
import com.joshdoctors.game.KeyHandler;
import com.joshdoctors.game.Store;
import com.joshdoctors.game.Entity.Mob;
import com.joshdoctors.game.levels.Room;
import com.joshdoctors.game.levels.Value;
import com.joshdoctors.game.loading.Save;
import com.joshdoctors.game.sound.Sound;

public class Screen extends JPanel implements Runnable
{
	private static final long serialVersionUID = 1L;

	public Thread thread = new Thread(this);
     
    public static Image[] tileset_ground = new Image[100]; // 100 images
    public static Image[] tileset_air = new Image[100];
    public static Image[] tileset_str = new Image[100];
    public static Image[] tileset_mob = new Image[100];
    public static Image[] title_screen = new Image[100];
    public static Image[] level_select = new Image[100];
    public static Image[] projectile = new Image[100];
    public static Image[] upgrades = new Image[100];
     
    public static int myWidth, myHeight;
    public static int money = 10;
    public static int health = 100;
    public static int kills = 0;
    public static int killsToWin =0;
    public static int mobsToSpawn=0;
    public static int level = 1;
    public static int round = 1;
    public static String[] rounds = new String[30]; 
    public static String[] subrounds; 
    public static int maxLevel = 10;
    public static int winTime = 4000,winFrame=0;
    public int spawnFrame=0;
    public static int mobSpawned = 0;
    
    public static boolean isFirst = true;
    public static boolean hasEnteredGame = false;
    public static boolean isDebug = false;
    public static boolean displayRange = false;
    public static boolean hasWon = false;
    public static boolean hasLoaded = false;
    public static boolean play = false;
    public static boolean playHeli = false;
    public static boolean playTank = false;
   
    
    public static Point mse = new Point(0,0);
     
    public static Room room;
    public static Save save;
    public static Store store;
    public static Menu menu;
     
    public Game game;
    
    public static Mob[] mobs = new Mob[1000];
    
    public Screen(Game game)
    {
   	 this.game = game;
   	 rounds[0] = "";
   	 game.addMouseListener(new KeyHandler());
   	 game.addMouseMotionListener(new KeyHandler());
        thread.start();
    }
     
     
    public void define()
    {
   	
        room = new Room();
        save = new Save();
        store = new Store();
        menu = new Menu();
        money = 200;//default 15
        kills = 0;
         
  
        for( int i = 0 ; i < tileset_ground.length ; i++ )
        {
            tileset_ground[i] = new ImageIcon("res/tileset_ground.png").getImage();
            tileset_ground[i] = createImage(new FilteredImageSource(tileset_ground[i].getSource(), new CropImageFilter(0, 26 * i, 26, 26))); // crop image
        }
         
        for( int i = 0 ; i < tileset_air.length ; i++ )
        {
            tileset_air[i] = new ImageIcon("res/tileset_air.png").getImage();
            tileset_air[i] = createImage(new FilteredImageSource(tileset_air[i].getSource(), new CropImageFilter(0, 26 * i, 26, 26)));
        }
        
        for( int i = 0 ; i < tileset_mob.length ; i++ )
        {
            tileset_mob[i] = new ImageIcon("res/tileset_mob.png").getImage();
            tileset_mob[i] = createImage(new FilteredImageSource(tileset_mob[i].getSource(), new CropImageFilter(0, 26 * i, 26, 26)));
        }
         
        
        tileset_str[0] = new ImageIcon("res/cell.png").getImage();
        tileset_str[1] = new ImageIcon("res/heart.png").getImage();
        tileset_str[2] = new ImageIcon("res/coin.png").getImage();
        tileset_str[3] = new ImageIcon("res/kill.png").getImage();
        tileset_str[4] = new ImageIcon("res/playButton.png").getImage();
        tileset_str[5] = new ImageIcon("res/soundButton.png").getImage();
        tileset_str[6] = new ImageIcon("res/soundButtonOff.png").getImage();
        
        
        tileset_str[7] = new ImageIcon("res/buttonSave.png").getImage();
        tileset_str[8] = new ImageIcon("res/buttonReset.png").getImage();
        tileset_str[9] = new ImageIcon("res/buttonCancel.png").getImage();
        
        tileset_str[10] = new ImageIcon("res/cellEraser.png").getImage();
        
        title_screen[0] = new ImageIcon("res/titlescreen.png").getImage();
        title_screen[1] = new ImageIcon("res/titleselectleft.png").getImage();
        title_screen[2] = new ImageIcon("res/titleselectright.png").getImage();
        title_screen[3] = new ImageIcon("res/editorhelp.png").getImage();
        
        title_screen[4] = new ImageIcon("res/titlescreen_background.png").getImage();
        title_screen[5] = new ImageIcon("res/titlescreen_background_top.png").getImage();
        title_screen[6] = new ImageIcon("res/titlescreen_background_lines.png").getImage();
        title_screen[7] = new ImageIcon("res/titlescreen_background_top_2.png").getImage();
        
        
        level_select[0] = new ImageIcon("res/levelone.png").getImage();
        level_select[1] = new ImageIcon("res/leveltwo.png").getImage();
        level_select[2] = new ImageIcon("res/levelthree.png").getImage();
        level_select[3] = new ImageIcon("res/levelselectscreen.png").getImage();
        
        upgrades[0] = new ImageIcon("res/upgraderange.png").getImage();
        upgrades[1] = new ImageIcon("res/upgradedmg.png").getImage();
        upgrades[2] = new ImageIcon("res/upgradesell.png").getImage();
        
        projectile[0] = new ImageIcon("res/projecttile.png").getImage();
        projectile[1] = new ImageIcon("res/projecttilecannon.png").getImage();

      	save.loadSave(new File("save/mission1.miss"));
        	
        
        for(int i=0;i<mobs.length;i++)
        {
      	  mobs[i] = new Mob();
      	  mobs[i].uniqueId = i;
 
        }
        
    }
     
    public static void loadLevel()
    {
   	 if(menu.loadLevel > 0)
   	 {
        if(menu.loadLevel==1)
        {
      	  save.loadSave(new File("save/mission1.miss"));
      	  hasLoaded = true;
      	  hasEnteredGame = true;
        }
        else
      	  if(menu.loadLevel==2)
      	  {
      		  save.loadSave(new File("save/mission2.miss"));
      		  hasLoaded = true;
      		  hasEnteredGame = true;
      	  }else
      		  if(menu.loadLevel==3)
      	  {
      		  save.loadSave(new File("save/mission3.miss"));
      		  hasLoaded = true;
      		  hasEnteredGame = true; 
      	  }
      		  else if(menu.loadLevel==4)
      		  {
         		  save.loadSave(new File("save/customMission.miss"));
         		  hasLoaded = true;
         		  hasEnteredGame = true; 
         	  }
      			  
        for(Sound s: Sound.values())
				s.setVolume(-70.0f);//default @-10 , but for testing purposes -70
  	 
   	 }
   	
    }
    public static void hasWon()
    {
   	 if(kills==killsToWin)
   	 {
   		 hasWon = true;
   		 kills = 0;
   		 money = 0;
   		 
   		 for(int i=0; i< Screen.mobs.length;i++)
   		 {
   			 Screen.mobs[i].deleteMob();
   		 }
   	 }
    }
    
//    public static BufferedImage rotate(BufferedImage image, double angle) {
//       double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
//       int w = image.getWidth(), h = image.getHeight();
//       int neww = (int)Math.floor(w*cos+h*sin), newh = (int)Math.floor(h*cos+w*sin);
//       GraphicsConfiguration gc = getDefaultConfiguration();
//       BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
//       Graphics2D g = result.createGraphics();
//       g.translate((neww-w)/2, (newh-h)/2);
//       g.rotate(angle, w/2, h/2);
//       g.drawRenderedImage(image, null);
//       g.dispose();
//       return result;
//   }
//   
    public void paintComponent(Graphics g)
    {
   	
   	 super.paintComponent(g);
        if(isFirst)
        {
            myWidth = getWidth();
            myHeight = getHeight();
            define();
            isFirst = false;
        }
        
        if(hasEnteredGame)
        {
	        g.setColor(new Color(70,70,70));
	        g.fillRect(0, 0, getWidth(), getHeight());
	        g.setColor(new Color(0,0,0));
	        g.drawLine(room.block[0][0].x-1, 0, room.block[0][0].x-1, room.block[room.worldHeight-1][0].y+room.blockSize+1);//left
	        g.drawLine(room.block[0][room.worldWidth-1].x+room.blockSize, 0, room.block[0][room.worldWidth-1].x+room.blockSize, room.block[room.worldHeight-1][0].y+room.blockSize+1);//right
	        g.drawLine(room.block[0][0].x-1,  room.block[room.worldHeight-1][0].y+room.blockSize+1, room.block[0][room.worldWidth-1].x+room.blockSize, room.block[room.worldHeight-1][0].y+room.blockSize+1);//down
	        
	        
	        room.draw(g);
	        
	        for(int i=0;i<mobs.length;i++)
	        {
	      	  if(mobs[i].inGame){
	      		  mobs[i].draw(g);
	      	  }
	        }
	        store.draw(g);
	        
	        if(health<1)
	        {
	      	  g.setColor(new Color(240,20,20));
	      	  g.fillRect(0, 0, myWidth, myHeight);
	      	  g.setColor(new Color(255,255,255));
	      	  g.setFont(new Font("Courier New", Font.BOLD, 14));
	      	  g.drawString("Game Over", 10, 20);
	        }
	        
	        if(hasWon){
	      	  g.setColor(new Color(255,255,255));
	      	  g.fillRect(0, (550/2)-20, getWidth(), 50);
	      	  g.setColor(new Color(0,0,0));
	      	  g.setFont(new Font("Courier New", Font.BOLD, 14));
	      	  if(level>maxLevel)
	      	  {
	      		  g.drawString("You beat the game! You can close the window!",  getWidth()/2-60, 550/2);
	      	  }else
	      		  g.drawString("You beat level "+ level+". Please wait for the next level!", getWidth()/2-60, 550/2);
	        }
        }
        else        
        {
      	  menu.draw(g); 
        }
    }
    
   
	public void populateRounds()
	{
		
	}
    
    public void mobSpawner()
    {
   	int rdCount = 0;
   	
   	if(rounds[round].contains("X"))
   	{
   		String [] rds= rounds[round].split("X");
   		for(String s: rds)
   		{
   			String [] values = s.split(",");
   			mobs[mobSpawned].spawnMob2(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]));
   			
   		}
   	}
   	else
   	{
   		String [] values= rounds[round].split(",");
   		mobs[mobSpawned].spawnMob2(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]));
   	}
   		
   	
    }
     
    public void run()
    {
   	 long lastTime = System.nanoTime();
   		double nsPerTick = 1000000000/60D;
   		
   		int frames = 0;
   		int ticks = 0;
   		
   		long lastTimer = System.currentTimeMillis();
   		double delta = 0;
   		
        while(true)
        {      
      	  
      	 
     			long now = System.nanoTime();
     			delta +=(now - lastTime)/nsPerTick;
     			lastTime = now;
     			boolean shouldRender = true; //change to true to limite to 60 FPS
     			
     			while(delta >=1)
     			{
     				ticks++;
     				delta -=1;
     				shouldRender = true;
     			}
     			
     			try {
     				Thread.sleep(2);
     			} catch (InterruptedException e) {
     				e.printStackTrace();
     			}
     			if(shouldRender){
     			frames++;
     			}
     			
     			if(System.currentTimeMillis() - lastTimer >= 1000){
     				lastTimer +=1000;
     				game.setTitle("Ticks: " + ticks + " - Frames: " + frames);
     				frames = 0;
     				ticks = 0;
     			}
     			
     			if(!hasLoaded)
     			{
     				loadLevel();
     				
     				if(hasLoaded)
     				{
	     				for(int i=0;i<mobs.length;i++)
	     				{
	     					mobs[i].getDirection();
	     				}
     				}
     			}
     			
            if(!isFirst && health>0 && !hasWon && hasEnteredGame && hasLoaded && play)
            {
                room.physics();
                //mobSpawner(2400,Value.mobHeli,20);
                //mobSpawner(1000,Value.mobTank,30);
                //mobSpawner(1000,Value.mobSuperTank,30);
                mobSpawner();
                for(int i=0;i<mobs.length;i++)
                {
               	// mobs[i].spawn();
               	 if(mobs[i].inGame)
               	 {           		 
               		 
               		 if(mobs[i].mobId==Value.mobHeli)
               			playHeli=true;
               		 
               		 if(mobs[i].mobId==Value.mobTank)
                			playTank=true;
               		 
               		 mobs[i].physics();
               		 
               	 }
                }
                
                if(!playHeli && Sound.HELIMOVING.isActive())
        			{
        				Sound.HELIMOVING.reset();
        				playHeli=false;
        			}

          		if(!Sound.HELIMOVING.isActive() && playHeli)
          		{
          				Sound.HELIMOVING.setVolume(-10.0f);
	          			Sound.HELIMOVING.playOnce();
	          			playHeli=false;
       
          		}
          		//////////////////////////////////////////
          	  if(!playTank && Sound.TANKMOVING.isActive())
       			{
       				Sound.TANKMOVING.reset();
       				playTank=false;
       			}

         		if(!Sound.TANKMOVING.isActive() && playTank)
         		{
         				Sound.TANKMOVING.setVolume(-10.0f);
	          			Sound.TANKMOVING.playOnce();
	          			playTank=false;      
         		}
          		

          		playHeli=false;
          		playTank=false;	
            }else
            {
            	if(hasWon)
            	{
            		if(winFrame >=winTime)
            		{
            			level+=1;
            			hasWon = false;
            			define();
            		}else
            			winFrame +=1;
            	}
            }
                 
            repaint();
             
             
        }
    }
}
