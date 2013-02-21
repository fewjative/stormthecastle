package com.joshdoctors.game.levels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;

import com.joshdoctors.game.gfx.Screen;
import com.joshdoctors.game.sound.Sound;

public class Block extends Rectangle{
	
	private static final long serialVersionUID = 1L;
	public int groundId;
	public int airId;
	public Rectangle towerSquare;
	public int towerSquareSize = 104;
	public int towerSquareSizeCannon = 156;
	public int shotMob = -1;
	public boolean isShooting = false;
	public boolean isShooting2 = false;
	public int loseTime=100,loseFrame=0;
	public int fireTime=10,fireFrame=0;//def was 3
	public int xOff,yOff;
	public int stopDupe=-1;
	public boolean prev = false;
	public int upgradeDmgLevel=0,upgradeRangeLevel=0;
	public boolean onFirst = true;
	
	
	public Block(int x, int y, int width, int height, int groundId,int airId)
	{
		setBounds(x,y,width,height);
		this.groundId = groundId;
		this.airId = airId;	
	
		towerSquare = new Rectangle(x-(towerSquareSize/2),y-(towerSquareSize/2),width + (towerSquareSize),height + (towerSquareSize));
	}

	public void draw(Graphics g)
	{
		
   	 
		g.drawImage(Screen.tileset_ground[groundId],x,y,width,height,null);
		
		if(airId !=Value.airAir)
		{
			g.drawImage(Screen.tileset_air[airId], x, y, width, height, null);
			
		}
		
		if(isShooting2)
		{
			if(fireFrame>=fireTime)
			{
				xOff++;
				yOff++;
				fireFrame=0;
			//	isShooting2=false;
				if(xOff>52 || yOff>52)
				{
					xOff=0;
					yOff=0;
					isShooting2 = false;
				}
			}
			else
				fireFrame+=1;
			
//			if(this.airId!=Value.airAir)
//			{
//				g.drawImage(Screen.projectile[0], x, y+20+yOff,52, 52, null);
//				g.drawImage(Screen.projectile[0], x-13-xOff, y+20+yOff,52, 52, null);
//				g.drawImage(Screen.projectile[0], x+13+xOff, y+20+yOff,52, 52, null);
//				
//				g.drawImage(Screen.projectile[0], x-13-xOff, y,52, 52, null);
//				g.drawImage(Screen.projectile[0], x+13+xOff, y,52, 52, null);
//				
//				g.drawImage(Screen.projectile[0], x, y-20-yOff,52, 52, null);
//				g.drawImage(Screen.projectile[0], x-13-xOff, y-20-yOff,52, 52, null);
//				g.drawImage(Screen.projectile[0], x+13+xOff, y-20-yOff,52, 52, null);
//			}
			
			// projectile[0] = new ImageIcon("res/projecttile.png").getImage();
			 //Graphics2D g2d = (Graphics2D)g;
			// g2d.
			// Graphics2D g2d = (Graphics2D)(Screen.tileset_mob[8].getGraphics());
	   	// g2d.rotate(Math.toRadians(180), this.getWidth()/2, this.getHeight()/2);
	   	
			//ImageIcon img = new ImageIcon("res/projecttile2.png");
			//img.
	   	 //g2d.rotate()
		
//				Graphics2D g2d = (Graphics2D)(Screen.tileset_air[Value.mobTaxi].getGraphics());
//				g2d.rotate(Math.toRadians(180), this.getWidth()/2, this.getHeight()/2);
//		
			
		}
		
	}
	
	public void drawProjectiles(Graphics g)
	{
		if(isShooting2 && this.airId==Value.airSpikeTower)
		{
			if(fireFrame>=fireTime)
			{
				xOff++;
				yOff++;
				fireFrame=0;
			//	isShooting2=false;
				if(xOff>52 || yOff>52)
				{
					xOff=0;
					yOff=0;
					isShooting2 = false;
				}
			}
			else
				fireFrame+=1;
			
			if(this.airId!=Value.airAir)
			{
			g.drawImage(Screen.projectile[0], x, y+20+yOff,52, 52, null);
			g.drawImage(Screen.projectile[0], x-13-xOff, y+20+yOff,52, 52, null);
			g.drawImage(Screen.projectile[0], x+13+xOff, y+20+yOff,52, 52, null);
			
			g.drawImage(Screen.projectile[0], x-13-xOff, y,52, 52, null);
			g.drawImage(Screen.projectile[0], x+13+xOff, y,52, 52, null);
			
			g.drawImage(Screen.projectile[0], x, y-20-yOff,52, 52, null);
			g.drawImage(Screen.projectile[0], x-13-xOff, y-20-yOff,52, 52, null);
			g.drawImage(Screen.projectile[0], x+13+xOff, y-20-yOff,52, 52, null);
			}
		}
		
		if(isShooting2 && this.airId==Value.airCannon)
		{
			int target = hasPrevious();
			g.drawString("lol",Screen.mobs[target].x,Screen.mobs[target].y);
			int xDiff = Math.abs(Screen.mobs[target].x - this.x);
			int yDiff = Math.abs(Screen.mobs[target].y - this.y);
			System.out.println(xDiff + " " + yDiff);
			if(fireFrame>=fireTime)
			{
				if(xDiff >yDiff)
				{
				if(yDiff!=0)xOff+=(xDiff/yDiff);
				
				yOff++;
				}
				else if(xDiff<yDiff)
				{
					xOff++;
					if(xDiff!=0)yOff+=(yDiff/xDiff);
					
				}
				else
				{
					xOff++;
					yOff++;
				}
				
				fireFrame=0;
			//	isShooting2=false;
				if(xOff>52 || yOff>52)
				{
					xOff=0;
					yOff=0;
					isShooting2 = false;
				}
			}
			else
				fireFrame+=1;
			
			if(this.airId!=Value.airAir)
			{
				if(Screen.mobs[target].x > this.x)
				{
					if(Screen.mobs[target].y > this.y)
						g.drawImage(Screen.projectile[1], x+xOff, y+20+yOff,52, 52, null);
					else
						if(Screen.mobs[target].y < this.y)
						g.drawImage(Screen.projectile[1], x+xOff, y+20-yOff,52, 52, null);
						else
							g.drawImage(Screen.projectile[1], x+xOff, y+20,52, 52, null);
				}
				else
				{
					if(Screen.mobs[target].y > this.y)
						g.drawImage(Screen.projectile[1], x-xOff, y+20+yOff,52, 52, null);
					else
						if(Screen.mobs[target].y < this.y)
						g.drawImage(Screen.projectile[1], x-xOff, y+20-yOff,52, 52, null);
						else
							g.drawImage(Screen.projectile[1], x-xOff, y+20,52, 52, null);
				}
			}
		}
		
		
		
	}
	
	public void getRangeLevel()
	{
//		if(this.airId==Value.airCannon)
//		{
//			this.upgradeRangeLevel = (towerSquareSizeCannon-156)/26;
//			System.out.println("air cannon");
//			
//		}
//		else
//		{
//			this.upgradeRangeLevel = (towerSquareSize-104)/26;	
//		}
		this.upgradeRangeLevel = (towerSquareSize-104)/26;	
	}
	
	
	public void fight(Graphics g)
	{
		if(Screen.isDebug)
		{
			g.setColor(new Color(0,0,0,100));
			if(airId==Value.airLaserTower || airId==Value.airSpikeTower)
				g.fillRect(towerSquare.x,towerSquare.y,towerSquare.width,towerSquare.height);
		}
		
		if(isShooting)
		{
			g.setColor(new Color(255,255,0));
			g.drawLine(x+(width/2), y+(height/2), Screen.mobs[shotMob].x+(Screen.mobs[shotMob].width/2),  Screen.mobs[shotMob].y+(Screen.mobs[shotMob].height/2));
			
		}
		
	}
	
	public int hasPrevious()
	{

		int first = -1;
		
		for(int j=Screen.mobs.length-1;j>=0;j--)
		{
			if(Screen.mobs[j].inGame && (towerSquare).intersects(Screen.mobs[j]))
			{
				first = j;
			}
			
		}
		return first;
	}
	
	public int hasMostDist()
	{
		int first = -1;
		
		for(int x=Screen.mobs.length-1;x>=0;x--)
		{
			if(Screen.mobs[x].inGame && (towerSquare).intersects(Screen.mobs[x]))
					first = x;
		}
		
		for(int j=0;j<Screen.mobs.length;j++)
		{
			if(Screen.mobs[j].inGame && Screen.mobs[j].totalDist > Screen.mobs[first].totalDist && (towerSquare).intersects(Screen.mobs[j]))
			{
					first = j;
					System.out.println("inside first " + first);
			}
//			else
//			{
//				for(int x=Screen.mobs.length-1;x>=0;x--)
//				{
//					if(Screen.mobs[x].inGame && (towerSquare).intersects(Screen.mobs[x]))
//					{
//						first = x;
//					}		
//				}
//			}
			
		}
		
		
		System.out.println("first " + first);
		if((towerSquare).intersects(Screen.mobs[first]))
			return first;
		else return -1;


	}
	
	public boolean mobsInRange()
	{
		boolean inRange= false;
		for(int j=0;j<Screen.mobs.length;j++)
		{
			if(Screen.mobs[j].inGame && (towerSquare).intersects(Screen.mobs[j]))
			{
					inRange=true;
			}
		}
		return inRange;
	}
	
	public void physics()
	{
		
		
		int [] shotMobs= {-1,-1,-1,-1,-1,-1,-1,-1};
		getRangeLevel();
		
		if(shotMob !=-1 && towerSquare.intersects(Screen.mobs[shotMob]))//shotMob !=-1 && towerSquare.intersects(Screen.mobs[shotMob])
		{
			isShooting = true;
		}
		else
			isShooting = false;
		
		
		if(!isShooting)
		{
			if(airId==Value.airLaserTower)//uses || to add more towers
			{
				if(hasPrevious() != -1)
				{
					isShooting = true;
					shotMob = hasPrevious();
				}
				
//				if(hasMostDist()!=-1)
//				{
//					isShooting = true;
//						shotMob = hasMostDist();	
//				}
				
				
			}
		
		}
		
		if(!isShooting2)
		{
			if(airId==Value.airSpikeTower  || airId==Value.airCannon)
			{
				if(mobsInRange())
				{
					isShooting2 = true;
					System.out.println("mobs in range");
				}
			}
		}
		
		if(isShooting && airId==Value.airLaserTower)
		{
	
			if(loseFrame >=loseTime)
			{
				if(this.upgradeDmgLevel==0)
					Screen.mobs[shotMob].loseHealth(2);
				else
					if(this.upgradeDmgLevel==1)
						Screen.mobs[shotMob].loseHealth(3);
					else
						if(this.upgradeDmgLevel==2)
							Screen.mobs[shotMob].loseHealth(4);
						else
					if(this.upgradeDmgLevel==3)
						Screen.mobs[shotMob].loseHealth(6);
				
				loseFrame = 0;
			}
			else
				loseFrame+=1;
			
			
			if(Screen.mobs[shotMob].isDead())
			{
				stopDupe = shotMob;
				isShooting = false;
				shotMob = -1;
				Screen.hasWon();
			}
					
		}
		
		if(isShooting2 && airId==Value.airSpikeTower)
		{
			if(loseFrame >=loseTime+100)
			{
				shotMobs  = collectMobs();
				
				//add for loop here to go through all of the collected mobs
				
				for(int i=0;i<shotMobs.length;i++)
				{
					if(shotMobs[i]!=-1)
					{
					if(this.upgradeDmgLevel==0)
						Screen.mobs[shotMobs[i]].loseHealth(4);
					else
						if(this.upgradeDmgLevel==1)
							Screen.mobs[shotMobs[i]].loseHealth(5);
						else
							if(this.upgradeDmgLevel==2)
								Screen.mobs[shotMobs[i]].loseHealth(6);
							else
						if(this.upgradeDmgLevel==3)
							Screen.mobs[shotMobs[i]].loseHealth(7);
					}
				
				}
				
				loseFrame = 0;
			}
			else
				loseFrame+=1;
			
			for(int k=0;k<shotMobs.length;k++)
			{
				if(shotMobs[k]!=-1 && Screen.mobs[shotMobs[k]].isDead())
				{
					stopDupe = shotMobs[k];
					isShooting = false;
					shotMob = -1;
					Screen.hasWon();
				}
			}
			
		}
		
		if(isShooting2 && airId==Value.airCannon)
		{
			if(loseFrame >=loseTime+100)
			{
				shotMobs  = collectMobsAtTile();
				
				//add for loop here to go through all of the collected mobs
				
				for(int i=0;i<shotMobs.length;i++)
				{
					if(shotMobs[i]!=-1)
					{
					if(this.upgradeDmgLevel==0)
						Screen.mobs[shotMobs[i]].loseHealth(4);
					else
						if(this.upgradeDmgLevel==1)
							Screen.mobs[shotMobs[i]].loseHealth(5);
						else
							if(this.upgradeDmgLevel==2)
								Screen.mobs[shotMobs[i]].loseHealth(6);
							else
						if(this.upgradeDmgLevel==3)
							Screen.mobs[shotMobs[i]].loseHealth(7);
					}
				
				}
				
				loseFrame = 0;
			}
			else
				loseFrame+=1;
			
			for(int k=0;k<shotMobs.length;k++)
			{
				if(shotMobs[k]!=-1 && Screen.mobs[shotMobs[k]].isDead())
				{
					stopDupe = shotMobs[k];
					isShooting = false;
					shotMob = -1;
					Screen.hasWon();
				}
			}
			
		}
	}
	
	public int[] collectMobs()
	{
		System.out.println("In collect mob");
		int blockX = (this.x-35)/52;
		int blockY =  (this.y)/52;
		System.out.println(blockX + " " + blockY);
		int [] shotMobs = {-1,-1,-1,-1,-1,-1,-1,-1};
		for(int j=0;j<Screen.mobs.length;j++)
		{
			if(Screen.mobs[j].inGame && (towerSquare).intersects(Screen.mobs[j]))
			{
					//top three
					if(Screen.room.block[blockY-1][blockX-1].intersects(Screen.mobs[j]) && shotMobs[0]==-1)
						shotMobs[0]=j;
					
					if(Screen.room.block[blockY-1][blockX].intersects(Screen.mobs[j]) && shotMobs[1]==-1)
						shotMobs[1]=j;
					
					if(Screen.room.block[blockY-1][blockX+1].intersects(Screen.mobs[j]) && shotMobs[2]==-1)
						shotMobs[2]=j;
					
					//mid two
					if(Screen.room.block[blockY][blockX-1].intersects(Screen.mobs[j]) && shotMobs[3]==-1)
						shotMobs[3]=j;
					
					if(Screen.room.block[blockY][blockX+1].intersects(Screen.mobs[j]) && shotMobs[4]==-1)
						shotMobs[4]=j;
					
					//bot three
					if(Screen.room.block[blockY+1][blockX-1].intersects(Screen.mobs[j]) && shotMobs[5]==-1)
						shotMobs[5]=j;
					
					if(Screen.room.block[blockY+1][blockX].intersects(Screen.mobs[j]) && shotMobs[6]==-1)
						shotMobs[6]=j;
					
					if(Screen.room.block[blockY+1][blockX+1].intersects(Screen.mobs[j]) && shotMobs[7]==-1)
						shotMobs[7]=j;
					
			}
		}
		
//		for(int i=0;i<shotMobs.length;i++)
//		{
//			System.out.println(shotMobs[i]);
//		}
		
		return shotMobs;
	}
	
	public int[] collectMobsAtTile()
	{
		System.out.println("In collect at tile mob");
		
		int target = hasPrevious();
		int blockX = (Screen.mobs[target].x-35)/52;
		int blockY =  (Screen.mobs[target].y)/52;
		Rectangle rect  = new Rectangle(blockX*52,blockY*52,52,52);
		System.out.println(blockX + " " + blockY);
		int [] shotMobs=new int[50];
		int amt=0;
		for(int k=0;k<shotMobs.length;k++)
		{
			shotMobs[k]=-1;
		}
		for(int j=0;j<Screen.mobs.length;j++)
		{
			if(Screen.mobs[j].inGame && (towerSquare).intersects(Screen.mobs[j]) && (rect).intersects(Screen.mobs[j]))
			{
					shotMobs[amt]=j;	
					amt++;
			}
		}
		
//		for(int i=0;i<shotMobs.length;i++)
//		{
//			System.out.println(shotMobs[i]);
//		}
		
		return shotMobs;
	}
	
	public void getMoneyAndKills(int mobId)
	{
		if(mobId !=-1)
		{
			Screen.money +=Value.deathReward[mobId];
			Screen.kills +=1;
		}
		
	}
}
