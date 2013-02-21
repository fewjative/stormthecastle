package com.joshdoctors.game.Entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import com.joshdoctors.game.gfx.Screen;
import com.joshdoctors.game.levels.Value;
import com.joshdoctors.game.sound.Sound;

public class Mob extends Rectangle {
	public int xC,yC;
	public int health;
	public int healthSpace=3,healthHeight=6;
	public int mobSize = 52;
	public int mobId = Value.mobAir;
	public int mobWalk = 0;
	public int totalDist = 0;
	public int dir = 2;//up=0,down=1,right=2,left=3
	public boolean inGame = false;
	public int walkFrame = 0,walkSpeed=100;
	public boolean hasUpward = false;
	public boolean hasDownward = false;
	public boolean hasLeft = false;
	public boolean hasRight = false;
	public int uniqueId;
	public boolean hasDied = false;
	public boolean playHeli = false;
	public int spawnFrame=0,spawnTime=0;
	public int startFrame=0,startTime=0;
	public int healthFrame=0,healthTime=0;
	public int subRounds=0;
	public int amtToSpawn=0;
	
	public Sound explosion = Sound.EXPLOSION;
	
	public Mob()
	{
		healthTime=2;
	}

//	public void spawnMob(int mobId)
//	{
//
//		for(int y=0;y<Screen.room.block.length;y++)
//		{
//			for(int x=0;x<Screen.room.block[0].length;x++)
//			{
//				if(Screen.room.block[y][x].groundId == Value.groundRoadStartHoriz || Screen.room.block[y][x].groundId == Value.groundRoadStartVert)
//				{
//					setBounds(Screen.room.block[y][x].x,Screen.room.block[y][x].y,mobSize,mobSize);
//				xC=x;
//				yC=y;
//				}
//			}
//		}
//		this.mobId = mobId;
//		inGame = true;
//		this.health = mobSize;
//	}
	
	public void spawnMob2(int myMobId,int spawnTime,int speed,int howMany,int whenToSpawn)
	{
		//System.out.println(myMobId);
	if(startFrame>=whenToSpawn)
	{
	  	 if(spawnFrame>=spawnTime && amtToSpawn < howMany)
	  	 {
				for(int y=0;y<Screen.room.block.length;y++)
				{
					for(int x=0;x<Screen.room.block[0].length;x++)
					{
						if(Screen.room.block[y][x].groundId == Value.groundRoadStartHoriz || Screen.room.block[y][x].groundId == Value.groundRoadStartVert)
						{
							setBounds(Screen.room.block[y][x].x,Screen.room.block[y][x].y,mobSize,mobSize);
						xC=x;
						yC=y;
						}
					}
				}
				
				this.mobId = myMobId;
				inGame = true;
				this.health = mobSize;
	  		   spawnFrame = 0;
	  		   amtToSpawn++;
	  		   Screen.mobSpawned++;
				walkSpeed=speed;
	  		   System.out.println("mob spawned, id: " + mobId + " " + walkSpeed);
	  		   
	  		   if(this.mobId==12)
	  		   	healthFrame=1;
	  		   else
	  		   if(this.mobId==8)
	  		   	healthFrame=2;
	  		   else
	  		    if(this.mobId==0)
		  		   	healthFrame=3;
	  		    else
	  		   	 if(this.mobId==4)
	  		   	healthFrame=4;
	  		   	 else
		  		   	 if(this.mobId==16)
		  		   	healthFrame=5;
		  		   	else
			  		   	 if(this.mobId==20)
			  		   	healthFrame=6;
	  	 }
	  	 else
	  		 spawnFrame +=1;
	  	 /////////////////////////////////////////////////

			
		
		}
		else
			startFrame++;
	}
	
	
	public void getDirection()
	{
		for(int y=0;y<Screen.room.block.length;y++)
		{
			for(int x=0;x<Screen.room.block[0].length;x++)
			{
				if(Screen.room.block[y][x].groundId == Value.groundRoadStartHoriz)//was [y][0]
				{
					if(x==0)
					{
						dir=2;
					}
					else
						if(x==Screen.room.block.length-1)
						{
							dir=3;
						}					
				}
				else
					if(Screen.room.block[y][x].groundId == Value.groundRoadStartVert)////was [y][0]
					{
						if(y==0)
						{
							dir=1;
						}
						else
							if(y==Screen.room.block[0].length-1)
							{
								dir=0;
							}					
					}
					
			}
		}
		System.out.println(dir);
	}
	
	public void physics()
	{
		

		if(walkFrame >=walkSpeed)
		{
			if(dir==2)
			{
			x +=1;
			}
			else if(dir ==0)
			{
				y-=1;
			}
			else
				if(dir==1)
				{
					y+=1;
				}
				else if(dir==3)
				{
					x-=1;
				}
			
			mobWalk+=1;
			totalDist+=1;
			
			if(mobWalk==Screen.room.blockSize)
			{
				if(dir==2)
				{
				xC +=1;
				hasRight = true;
				}
				else if(dir ==0)
				{
					yC-=1;
					hasUpward = true;
				}
				else
					if(dir==1)
					{
						yC+=1;
						hasDownward = true;
					}
					else if(dir==3)
					{
						xC-=1;
						hasLeft = true;
					}
				
				
				if(!hasUpward){
					try{
						if(Screen.room.block[yC+1][xC].groundId >=1 && !(Screen.room.block[yC+1][xC].groundId==11))
							dir = 1;
					}catch(Exception e) {}
				}
				
				if(!hasDownward){
					try{
						if(Screen.room.block[yC-1][xC].groundId >=1 && !(Screen.room.block[yC-1][xC].groundId==11))
							dir = 0;
					}catch(Exception e) {}
				}
				
				if(!hasLeft){
					try{
						if(Screen.room.block[yC][xC+1].groundId >=1 && !(Screen.room.block[yC][xC+1].groundId==11))
							dir = 2;
					}catch(Exception e) {}
				}
				
				if(!hasRight){
					try{
						if(Screen.room.block[yC][xC-1].groundId >=1 && !(Screen.room.block[yC][xC-1].groundId==11))
							dir = 3;
					}catch(Exception e) {}
				}
				
				if(Screen.room.block[yC][xC].groundId == Value.groundRoadFinHoriz || Screen.room.block[yC][xC].groundId == Value.groundRoadFinVert)
				{
					deleteMobNoMoney();
					looseHealth();
					hasDied = true;
				}
				
				hasUpward = false;
				hasDownward = false;
				hasLeft = false;
				hasRight = false;
				mobWalk = 0;
				
				
			}
			walkFrame=0;
		}
		else
			walkFrame+=1;
	}
	
	public void deleteMob() {
		inGame = false;
		dir =2;
		mobWalk = 0;
		totalDist=0;
		
		if(Sound.EXPLOSION2.isActive())
		{
			Sound.EXPLOSION2.reset();
			Sound.EXPLOSION2.playOnce();
		}			
		else
			Sound.EXPLOSION2.playOnce();
		
		Screen.room.block[0][0].getMoneyAndKills(mobId);
		
	}
	
	public void deleteMobNoMoney() {
		inGame = false;
		dir =2;
		mobWalk = 0;
		totalDist=0;
		
	}

	private void looseHealth() {
		Screen.health -=1;
		
	}

	public void loseHealth(int i)
	{
		if(healthFrame>=healthTime)
		{
			health -=i;
			checkDeath();
			healthFrame=0;
		}
		else
			healthFrame++;
	}
	
	public void checkDeath()
	{
		if(health<=0)
		{
			deleteMob();
			hasDied = true;
		}
	}
 
	public boolean isDead()
	{
		if(inGame)
			return false;
		else return true;
	}
	

   
	public void draw(Graphics g)
	{
		if(inGame)
		{
			if(dir==0)
			{
				g.drawImage(Screen.tileset_mob[mobId], x, y, width, height, null);
			}
			else
				if(dir==1)
				{
					g.drawImage(Screen.tileset_mob[mobId+2], x, y, width, height, null);
				}
				else
					if(dir==2)
					{
						g.drawImage(Screen.tileset_mob[mobId+1], x, y, width, height, null);
					}
					else
						if(dir==3)
						{
							g.drawImage(Screen.tileset_mob[mobId+3], x, y, width, height, null);
						}
			
			g.setColor(new Color(180,50,50));
			g.fillRect(x,y-(healthSpace+healthHeight),width,healthHeight);
			
			g.setColor(new Color(50,180,50));
			g.fillRect(x,y-(healthSpace+healthHeight),health,healthHeight);
			
			g.setColor(new Color(0,0,0));
			g.drawRect(x,y-(healthSpace+healthHeight),health-1,healthHeight-1);
			
			g.setColor(new Color(255,255,255));
    	   g.setFont(new Font("Courier New", Font.BOLD, 14));
			g.drawString(uniqueId+"",x+20+13,y+10+(13));
								
		}
			
	}
}
