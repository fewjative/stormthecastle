package com.joshdoctors.game.levels;

import java.awt.Color;
import java.awt.Graphics;

import com.joshdoctors.game.gfx.Screen;
import com.joshdoctors.game.sound.Sound;

public class Room {
	
	public int worldWidth = 12;
	public int worldHeight = 8;
	public int blockSize = 52;
	public boolean playShooting=false;
	public boolean playHeli = false;
	public int fireFrame=0,fireTime=400;
	
	public Block[][] block;
	
	public Room()
	{
		define();
	}
	
	private void define() {
		block = new Block[worldHeight][worldWidth];
		
		for(int y=0;y<block.length;y++)
		{
			for(int x=0;x<block[0].length;x++)
			{
				block[y][x] = new Block((Screen.myWidth/2)-((worldWidth*blockSize)/2)+(x*blockSize),y*blockSize,blockSize,blockSize,Value.GroundGrass,Value.airAir);
			}
		}
		
	}

	public void draw(Graphics g)
	{
		for(int y = 0; y < block.length; y++)
      {
          for(int x = 0; x < block[0].length; x++)
          {
              block[y][x].draw(g);
           	if(block[y][x].contains(Screen.mse))
   			{
   				g.setColor(new Color(255,255,255,100));
   				g.fillRect(block[y][x].x,block[y][x].y,block[y][x].width,block[y][x].height);
   			}
          }
      }
		
		for(int y = 0; y < block.length; y++)
      {
          for(int x = 0; x < block[0].length; x++)
          {
             block[y][x].drawProjectiles(g);
              block[y][x].fight(g);
          }
      }
		
		
	}
	
	public void physics()
	{
		for(int y=0; y<block.length;y++)
		{
			for(int x=0;x<block[0].length;x++)
			{
				
				if(block[y][x].isShooting)
					playShooting = true;
			
				block[y][x].physics();
			}
		}
		
		if(playShooting && !Sound.LASER5.isActive())
		{
			if(fireFrame >=fireTime)
			{
				Sound.LASER5.playOnce();
				fireFrame = 0;
				playShooting=false;
			}
			else
				fireFrame+=1;
			
		}
		
		playShooting=false;
		
	}

}
