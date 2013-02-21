package com.joshdoctors.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import com.joshdoctors.game.gfx.Screen;
import com.joshdoctors.game.levels.Value;
import com.joshdoctors.game.sound.Sound;

public class Store {
	public static int shopWidth = 8;
	public static int buttonSize = 52;
	public static int cellSpace = 2;
	public static int storeSpace = 27;
	
	public static int upgradeSpace = 120;
	public Rectangle[] button = new Rectangle[shopWidth];
	public Rectangle[] buttonUpgrades = new Rectangle[3];
	public Rectangle buttonHealth;
	public Rectangle buttonCoins;
	public Rectangle buttonKills;
	public Rectangle buttonSound;
	public Rectangle buttonPlay;
	
	public static int iconSize  = 20;
	public static int iconSpace  = 3;
	public static int iconYoffset= 14;
	public static int iconXoffset= 5;
	public static int[] buttonId = {Value.airLaserTower,Value.airSpikeTower,Value.airCannon,Value.airAir,Value.airAir,Value.airAir,Value.airAir,Value.airRecycle};
	public static int itemBorder = 4;
	public static int heldId = -1;
	public static int realId = -1;
	public boolean holdsItem = false;
	public boolean hasSelected = false;
	public static int hasSelectedX,hasSelectedY;
	public static int hasSelectedBlockX,hasSelectedBlockY;
	
	public static boolean displayRed = false;
	public static int upgradeRangeLevel=0,upgradeDmgLevel=0;//dont need these but keeping because code uses and lazy to change
	public int upRng=0;
	
	
	public static int[] buttonPrice = { 10,20,30,0,0,0,0,0,0};
	public static int[] buttonUpgradePrice = { 15,30,45,20,40,50};
	public Color standard = new Color(180,180,180);
	public Color red = new Color(255,20,20);
	public Color green = new Color(100,255,0);
	public Color yellow = new Color(255,255,0);
	public Color orange = new Color(255,126,0);
	
	public static boolean playAudio = true;
	
	

	
	public Store()
	{
		define();
	}
	
	public void click(int mouseButton)
	{
		if(mouseButton ==1 && Screen.hasEnteredGame)
		{
			if(!hasSelected)
			{
				for(int i=0;i<button.length;i++)
				{
					if(button[i].contains(Screen.mse))
					{
						if(buttonId[i] !=Value.airAir)
						{
							if(Screen.money >= buttonPrice[i])
							{
								if(buttonId[i]==Value.airRecycle)
								{
									holdsItem = false;
								}
								else
								{
								heldId = buttonId[i];
								realId = i;
								holdsItem = true;
								Sound.CLICK2.playOnce();
								}
								
							}
						}
					}
				}
				
				if(buttonPlay.contains(Screen.mse))
				{
					Screen.play = true;
				}
				
				if(buttonSound.contains(Screen.mse))
				{
					playAudio = !playAudio;
					if(playAudio)
					{
						for(Sound s: Sound.values())
							s.setVolume(-70.0f);//default -10
					}
					else
					{
						for(Sound s: Sound.values())
							s.setVolume(-70.0f);
					}
				}
				
				
				if(holdsItem)
				{
					if(Screen.money >= buttonPrice[realId])
					{
						for(int y=0;y<Screen.room.block.length;y++)
						{
							for(int x=0;x<Screen.room.block[0].length;x++)
							{
								if(Screen.room.block[y][x].contains(Screen.mse))//if(Screen.room.block[y][x].contains(Screen.mse) && !( Screen.room.block[y][x].groundId > 0 && Screen.room.block[y][x].groundId < 11) &&  Screen.room.block[y][x].airId==Value.airAir)
								{
									if(!( Screen.room.block[y][x].groundId > 0 && Screen.room.block[y][x].groundId < 11) &&  Screen.room.block[y][x].airId==Value.airAir)
											{
											Screen.room.block[y][x].airId = heldId;
											Screen.money -=buttonPrice[realId];
											System.out.println(buttonPrice[realId]);
											holdsItem = false;
											hasSelected = false;
											Screen.displayRange = false;
											Sound.BUY.playOnce();
											displayRed=false;
										}
									else
									{
										displayRed=true;
										Sound.ERROR.playOnce();
									}
								}
							}
						}
					}
				}
			}
			
		
				for(int y=0;y<Screen.room.block.length;y++)
				{
					for(int x=0;x<Screen.room.block[0].length;x++)
					{
						if(Screen.room.block[y][x].contains(Screen.mse) && (Screen.room.block[y][x].airId==Value.airLaserTower || Screen.room.block[y][x].airId==Value.airSpikeTower || Screen.room.block[y][x].airId==Value.airCannon) &&!holdsItem)
						{
							hasSelected = true;
							hasSelectedX = Screen.room.block[y][x].x;
							hasSelectedY = Screen.room.block[y][x].y;
							hasSelectedBlockX =x;
							hasSelectedBlockY = y;
							Screen.displayRange = true;
							Sound.CLICK2.playOnce();
							System.out.println("X coord: " +hasSelectedX +" " + "Y coord " + hasSelectedY);
							System.out.println("X block: " + hasSelectedBlockX + " Y block: " + hasSelectedBlockY);
							System.out.println("upgrade level: " + Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].upgradeRangeLevel);
						}
						
					}
				}
				
				if(hasSelected)
				{
					for(int y=0;y<Screen.room.block.length;y++)
					{
						for(int x=0;x<Screen.room.block[0].length;x++)
						{
							if(Screen.room.block[y][x].contains(Screen.mse) && !(Screen.room.block[y][x].airId==Value.airLaserTower || Screen.room.block[y][x].airId==Value.airSpikeTower || Screen.room.block[y][x].airId==Value.airCannon))
							{
								hasSelected = false;
								Screen.displayRange = false;								
							}							
						}
					}
					
					for(int i=0;i<buttonUpgrades.length;i++)
					{
						if(buttonUpgrades[i].contains(Screen.mse))
						{
							if(i==0)
							{
								if(Screen.money >=buttonUpgradePrice[Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].upgradeRangeLevel] && Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].upgradeRangeLevel <3)
								{
									Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].towerSquareSize+=26;
									Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].towerSquare.setBounds(hasSelectedX-(Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].towerSquareSize/2),hasSelectedY-(Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].towerSquareSize/2),Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].width + (Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].towerSquareSize),Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].height + (Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].towerSquareSize));
									Screen.money -=buttonUpgradePrice[Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].upgradeRangeLevel];
									Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].getRangeLevel();
									Sound.POWERUP.playOnce();
									return;
								}
								else
									Sound.ERROR.playOnce();
							}
							else
								if(i==1)
								{
									if(Screen.money >=buttonUpgradePrice[upgradeDmgLevel+3] && Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].upgradeDmgLevel <3)
									{
										Sound.POWERUP.playOnce();
										Screen.money -=buttonUpgradePrice[Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].upgradeDmgLevel+3];
										Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].upgradeDmgLevel++;
									}
									else
										Sound.ERROR.playOnce();
								}
								else
							if(i==2)
							{
								//need to take into account the upgrades
//								System.out.println("Should be selling");
//								System.out.println(hasSelectedY);
//								System.out.println(hasSelectedX);
//								System.out.println(Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].airId);
								//if(Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].airId==Value.airLaserTower || Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].airId==Value.airSpikeTower)
								
								Screen.money +=(buttonPrice[Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].airId-2])/2;
								
								for(int j=0;j<Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].upgradeRangeLevel;j++)
								{
									Screen.money += buttonUpgradePrice[j]/2;
								}
								
								for(int k=0;k<Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].upgradeDmgLevel;k++)
								{
									Screen.money += buttonUpgradePrice[k+3]/2;
								}
								
								
								Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].airId=Value.airAir;
								Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].isShooting = false;
								Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].shotMob=-1;
								hasSelected = false;
								Screen.displayRange = false;
								Sound.COIN.playOnce();
							}
								
						}
					}
				
				
			}
		}
	}
	
	
	private void define() {
		for(int i=0;i<button.length;i++)
		{
			button[i] = new Rectangle((Screen.myWidth/2)-((shopWidth*(buttonSize+cellSpace))/2)+((buttonSize+cellSpace)*i),Screen.room.block[Screen.room.worldHeight-1][0].y+Screen.room.blockSize+storeSpace,buttonSize,buttonSize);
		}
		
		for(int i=0;i<buttonUpgrades.length;i++)
		{
			buttonUpgrades[i] = new Rectangle(button[i].x+(i*upgradeSpace),Screen.room.block[Screen.room.worldHeight-1][0].y+Screen.room.blockSize+storeSpace,buttonSize*2,buttonSize);
		}
		
		buttonHealth = new Rectangle(Screen.room.block[0][0].x-1,button[0].y-13,iconSize,iconSize);
		buttonCoins = new Rectangle(Screen.room.block[0][0].x-1,button[0].y+button[0].height-iconSize-13,iconSize,iconSize);
		buttonKills = new Rectangle(Screen.room.block[0][0].x-1,button[0].y+button[0].height-iconSize+18,iconSize,iconSize);
		
		buttonPlay = new Rectangle(button[button.length-1].x + 67,Screen.room.block[Screen.room.worldHeight-1][0].y+Screen.room.blockSize+storeSpace-5,65,26);
		buttonSound = new Rectangle(buttonPlay.x,buttonPlay.y+26+10,buttonPlay.width,buttonPlay.height);
	}
	
	
	
	public void draw(Graphics g)
	{		
		upRng = Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].upgradeRangeLevel;
		
		if(!hasSelected)
		{
			for(int i=0;i<button.length;i++)
			{
				if(button[i].contains(Screen.mse))
				{
					g.setColor(new Color(255,255,255,100));
					g.fillRect(button[i].x,button[i].y,button[i].width,button[i].height);
				}
				g.drawImage(Screen.tileset_str[0], button[i].x,button[i].y,button[i].width,button[i].height,null);
				
				if(buttonId[i] != Value.airAir)
				{
					g.drawImage(Screen.tileset_air[buttonId[i]], button[i].x+itemBorder,button[i].y+itemBorder,button[i].width-(itemBorder*2),button[i].height-(itemBorder*2),null);
				}
				if(buttonPrice[i] > 0 )
				{
					g.setFont(new Font("Courier New",Font.BOLD,16));
					if(buttonPrice[i] > Screen.money)
						g.setColor(red);
					else
						g.setColor(standard);
					g.drawString("$" + buttonPrice[i],button[i].x+itemBorder,button[i].y+itemBorder+10);
				}
			}
			
			
			if(buttonPlay.contains(Screen.mse))
			{
				g.setColor(new Color(255,255,255,100));
				g.fillRect(buttonPlay.x,buttonPlay.y,buttonPlay.width,buttonPlay.height);
			}
			
			if(buttonSound.contains(Screen.mse))
			{
				g.setColor(new Color(255,255,255,100));
				g.fillRect(buttonSound.x,buttonSound.y,buttonSound.width,buttonSound.height);
			}
			
			g.drawImage(Screen.tileset_str[4],buttonPlay.x,buttonPlay.y,buttonPlay.width,buttonPlay.height,null);
			
			if(playAudio)
				g.drawImage(Screen.tileset_str[5],buttonSound.x,buttonSound.y,buttonSound.width,buttonSound.height,null);
				else
					g.drawImage(Screen.tileset_str[6],buttonSound.x,buttonSound.y,buttonSound.width,buttonSound.height,null);
			
		
			
		}
		
		g.drawImage(Screen.tileset_str[1],buttonHealth.x,buttonHealth.y,buttonHealth.height,buttonHealth.width,null);
		g.drawImage(Screen.tileset_str[2],buttonCoins.x,buttonCoins.y,buttonCoins.height,buttonCoins.width,null);
		g.drawImage(Screen.tileset_str[3],buttonKills.x,buttonKills.y,buttonKills.height,buttonKills.width,null);
		g.setFont(new Font("Courier New",Font.BOLD,16));
		
		
		if(Screen.health<=20)
		{
			g.setColor(red);
		}
		else
			if(Screen.health<=45)
			{
				g.setColor(orange);
			}
			else
				if(Screen.health<=80)
				{
					g.setColor(yellow);
				}
				else
						g.setColor(green);
					
		
		g.drawString(""+Screen.health, buttonHealth.x+buttonHealth.width+iconSpace+ iconXoffset, buttonHealth.y + iconYoffset);
		
		g.setColor(standard);
		
		g.drawString(""+Screen.money, buttonCoins.x+buttonCoins.width+iconSpace+iconXoffset, buttonCoins.y + iconYoffset);
		g.drawString(""+Screen.kills, buttonKills.x+buttonKills.width+iconSpace+iconXoffset, buttonKills.y + iconYoffset);
		
		
		
	if(holdsItem)
	{
		for(int y=0;y<Screen.room.block.length;y++)
		{
			for(int x=0;x<Screen.room.block[0].length;x++)
			{
				if(Screen.room.block[y][x].groundId==0)
					Screen.room.block[y][x].groundId =11;
				
				if(Screen.room.block[y][x].contains(Screen.mse))
				{
					if((Screen.room.block[y][x].groundId > 0 && Screen.room.block[y][x].groundId < 11)  || Screen.room.block[y][x].airId!=Value.airAir)
						displayRed=true;
					else	displayRed=false;
				}
			}
		}
		
	
			if(Screen.mse.x < 34 || Screen.mse.x > 666 || Screen.mse.y <=0 || Screen.mse.y >416)
				displayRed=true;
			
				if(displayRed)
					g.setColor(new Color(255,0,0,100));
				else
					g.setColor(new Color(0,0,0,100));
				
				if(Screen.mse.x <104)
				{
					if(Screen.mse.y <73)
						g.fillRect(35,0, button[0].width-(itemBorder*2)+Screen.mse.x, button[0].height-(itemBorder*2)+(Screen.mse.y+35));
					else
						if(Screen.mse.y > 343)
							g.fillRect(35, Screen.mse.y  - ((button[0].width-(itemBorder*2))/2)+ itemBorder-52, button[0].width-(itemBorder*2)+Screen.mse.x,  71+(416-Screen.mse.y));
						else
					g.fillRect(35, Screen.mse.y  - ((button[0].width-(itemBorder*2))/2)+ itemBorder-52, button[0].width-(itemBorder*2)+Screen.mse.x, button[0].height-(itemBorder*2)+104);
				}
				else
					if(Screen.mse.x > 583)
					{
						if(Screen.mse.y >343)
							g.fillRect(Screen.mse.x + itemBorder - ((button[0].width- (itemBorder*2))/2)-52,Screen.mse.y  - ((button[0].width-(itemBorder*2))/2)+ itemBorder-52,64+(666-Screen.mse.x),71+(416-Screen.mse.y));
						else
								g.fillRect(Screen.mse.x + itemBorder - ((button[0].width- (itemBorder*2))/2)-52,Screen.mse.y  - ((button[0].width-(itemBorder*2))/2)+ itemBorder-52,64+(666-Screen.mse.x),148);
					}
					else
				if(Screen.mse.y > 343)	
					g.fillRect(Screen.mse.x + itemBorder - ((button[0].width- (itemBorder*2))/2)-52,Screen.mse.y  - ((button[0].width-(itemBorder*2))/2)+ itemBorder-52,button[0].width-(itemBorder*2)+104, 71+(416-Screen.mse.y));
				else
					g.fillRect(Screen.mse.x + itemBorder - ((button[0].width- (itemBorder*2))/2)-52,Screen.mse.y  - ((button[0].width-(itemBorder*2))/2)+ itemBorder-52,button[0].width-(itemBorder*2)+104,button[0].height-(itemBorder*2)+104);
				
			g.drawImage(Screen.tileset_air[heldId],Screen.mse.x + itemBorder - ((button[0].width- (itemBorder*2))/2),Screen.mse.y  - ((button[0].width-(itemBorder*2))/2)+ itemBorder,button[0].width-(itemBorder*2),button[0].height-(itemBorder*2),null);
	}
	else
	{
		for(int y=0;y<Screen.room.block.length;y++)
		{
			for(int x=0;x<Screen.room.block[0].length;x++)
			{
				if(Screen.room.block[y][x].groundId==11)
					Screen.room.block[y][x].groundId =0;
			}
		}
		
	}
	if(hasSelected)
	{
		g.setFont(new Font("Courier New",Font.BOLD,16));
		
		g.setColor(new Color(255,0,0,100));
		///////////////////////////
		//g.drawRect(Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].towerSquare.x, Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].towerSquare.y, Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].towerSquare.width, Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].towerSquare.height);
		g.setColor(new Color(0,0,0,100));
		if(hasSelectedX <104)
		{
			if(hasSelectedY <73)
				g.fillRect(35,0, hasSelectedX+(70)+(upRng*13), hasSelectedY+104+(upRng*13));
			else
				if(hasSelectedY > 343)
					g.fillRect(35, hasSelectedY-52-(upRng*13), hasSelectedX+70+(upRng*13),  52+(416-hasSelectedY)+(upRng*13));
				else
			g.fillRect(35, hasSelectedY-52-(upRng*13), hasSelectedX+70+(upRng*13), 157+(upRng*26));
		}
		else
			if(hasSelectedX > 583)
			{
				if(hasSelectedY >343)
					g.fillRect(hasSelectedX-52-(upRng*13),hasSelectedY-52-(upRng*13),45+(666-hasSelectedX)+(upRng*13),52+(416-hasSelectedY)+(upRng*13));
				else
						g.fillRect(hasSelectedX-52-(upRng*13),hasSelectedY-52-(upRng*13),45+(666-hasSelectedX)+(upRng*13),52*3+(upRng*26));
			}
			else
		if(hasSelectedY > 343)	
			g.fillRect(hasSelectedX-52-(upRng*13),hasSelectedY -52-(upRng*13),52*3+(upRng*26), 52+(416-hasSelectedY)+(upRng*13));
		else
			g.fillRect(hasSelectedX-52-(upRng*13) ,hasSelectedY -52-(upRng*13),52*3+(upRng*26),52*3+(upRng*26));
		//////////////////
		g.setColor(standard);
		g.drawString("Upgrades:",buttonUpgrades[0].x,buttonUpgrades[0].y-5);
		
		g.setColor(standard);
		
		for(int y=0;y<Screen.room.block.length;y++)
		{
			for(int x=0;x<Screen.room.block[0].length;x++)
			{
				Screen.room.block[y][x].getRangeLevel();
				//System.out.println("my upgrade Level: "+ Screen.room.block[y][x].upgradeRangeLevel + " " +y + " " + x);
				if(hasSelectedBlockX==x &&hasSelectedBlockY==y)
				{
					g.drawString("Level: "+Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].upgradeRangeLevel,buttonUpgrades[0].x+5,buttonUpgrades[0].y+(17+52));
					g.drawString("Level: "+Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].upgradeDmgLevel,buttonUpgrades[1].x+5,buttonUpgrades[1].y+(17+52));
				}
			}
		}
		
		for(int i=0;i<buttonUpgrades.length;i++)
		{
			if(buttonUpgrades[i].contains(Screen.mse))
			{
				g.setColor(new Color(255,255,255,100));
				g.fillRect(buttonUpgrades[i].x,buttonUpgrades[i].y,buttonUpgrades[i].width,buttonUpgrades[i].height);
			}
			
			
				g.drawImage(Screen.upgrades[i], buttonUpgrades[i].x,buttonUpgrades[i].y,buttonUpgrades[i].width,buttonUpgrades[i].height,null);
				
			
				
				if(i==0)
				{
					if(Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].upgradeRangeLevel <3 )
					{
						if(buttonUpgradePrice[Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].upgradeRangeLevel] > Screen.money)
							g.setColor(red);
						else
							g.setColor(standard);
						g.drawString("$" + buttonUpgradePrice[Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].upgradeRangeLevel],buttonUpgrades[i].x+5,buttonUpgrades[i].y+17);
					}
					else 
					{
						g.setColor(red);
						g.drawString("MAX Level",buttonUpgrades[0].x+5,buttonUpgrades[0].y+17);
					}
					
				}
				else
					if(i==1)
					{
						if(Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].upgradeDmgLevel <3)
						{
							if(buttonUpgradePrice[Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].upgradeDmgLevel+3] > Screen.money)
								g.setColor(red);
							else
								g.setColor(standard);
							g.drawString("$" + buttonUpgradePrice[Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].upgradeDmgLevel+3],buttonUpgrades[i].x+5,buttonUpgrades[i].y+17);
						}
						else
						{
							g.setColor(red);
							g.drawString("MAX Level",buttonUpgrades[1].x+5,buttonUpgrades[1].y+17);	
						}
						
						g.setColor(standard);
					}
				System.out.println(Screen.room.block[hasSelectedBlockY][hasSelectedBlockX].upgradeDmgLevel);				
		}
		
	}
	
	}
}