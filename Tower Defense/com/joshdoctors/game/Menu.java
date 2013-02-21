package com.joshdoctors.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.*;

import com.joshdoctors.game.gfx.Screen;
import com.joshdoctors.game.levels.Block;
import com.joshdoctors.game.levels.Value;
import com.joshdoctors.game.sound.Sound;

public class Menu {
	

	
	public Rectangle buttonSelectLeftTop;
	public Rectangle buttonSelectRightTop;
	
	public Rectangle buttonSelectLeftMiddle;
	public Rectangle buttonSelectRightMiddle;
	
	public Rectangle buttonSelectLeftBot;
	public Rectangle buttonSelectRightBot;
	
	public Rectangle buttonBack;
	public Rectangle buttonCustom;
	
	public Rectangle[] buttonChoices = new Rectangle[3];//start //how to play //credits
	public Rectangle[] buttonLevels = new Rectangle[3];
	public static String[] myChoices = { "Start", "How To Play?","Credits"};
	public static int buttonSpacing = 5;
	public static int levelSpacing = 20;
	public int frame,time=5;
	public int px;
	public Rectangle[] buttonTiles = new Rectangle[4];
	
	public Rectangle buttonSave,buttonCancel,rectScreen,buttonReset;
	
	
	public Color standard = new Color(180,180,180);
	public Color black = new Color(0,0,0);
	public Color red = new Color(255,20,20);
	public Color green = new Color(100,255,0);
	public Color yellow = new Color(255,255,0);
	public Color orange = new Color(255,126,0);
	public Color blue = new Color(0,0,255);
	public Color white = new Color(255,255,255);
	
	boolean hasEnteredDirections;
	boolean hasEnteredCredits;
	boolean hasEnteredSelect=false;
	boolean hasMovedOut = false;
	boolean hasEnteredCustom = false;
	boolean hasHitNext = false;
	
	boolean lastOnTop= true;
	boolean lastOnMid = false;
	boolean lastOnBot = false;
	
	public Rectangle [][] levelDisplay = new Rectangle[8][12];
	public int [][] level = new int[8][12];
	public static int [][] levelBlock = new int[8][12];
	public int color;
	
	public static int loadLevel=-1;
	
	
	
	public Menu()
	{
		define();
		 for(Sound s: Sound.values())
				s.setVolume(-70.0f);//default @-10 , but for testing purposes -70
	 
		Sound.MENU.loop();
	}
	
	public void click(int mouseButton) throws IOException
	{
		
		if(mouseButton ==1)
		{
			System.out.println(hasEnteredSelect);
			System.out.println(hasEnteredDirections);
			System.out.println(hasEnteredCredits);
			Sound.CLICK2.playOnce();
			for(int i=0;i<buttonChoices.length;i++)
			{
				if(hasEnteredSelect && buttonLevels[i].contains(Screen.mse) && (!hasEnteredDirections && !hasEnteredCredits) &&!hasHitNext && !hasEnteredCustom)
				{
					if(i==0)
					{
						loadLevel=1;
						//Sound.MENU.fade();
						Sound.MENU.stop();
						Sound.GAME.loop();
					}
					else
						if(i==1)
						{
							loadLevel=2;
							Sound.MENU.stop();
							Sound.GAME.loop();
						}
						else
							if(i==2)
							{
								loadLevel = 3;
								Sound.MENU.stop();
								Sound.GAME.loop();
							}
						
				}
				
				if(hasEnteredSelect && buttonCustom.contains(Screen.mse) && (!hasEnteredDirections && !hasEnteredCredits))
				{
					hasEnteredCustom = true;
				}
				
				if(buttonChoices[i].contains(Screen.mse) && (!hasEnteredDirections && !hasEnteredCredits && !hasEnteredSelect))
				{
				
					if(i==0)
					{	
						hasEnteredSelect = true;
						hasEnteredDirections = false;
						hasEnteredCredits = false;
						break;
					}
					else
					if(i==1)
					{
						hasEnteredDirections = true;
						hasEnteredCredits = false;
						hasEnteredSelect = false;
						break;
					}
					else
						if(i==2)
						{
							hasEnteredCredits = true;
							hasEnteredDirections = false;
							hasEnteredSelect= false;
							break;
						}
					
				}
				
				if(buttonBack.contains(Screen.mse) && !hasEnteredCustom)
				{
					hasEnteredDirections = false;
					hasEnteredCredits = false;
					hasEnteredSelect = false;
					
					lastOnTop = true;
					lastOnBot = false;
					lastOnMid = false;
					
				}
				
				if(buttonBack.contains(Screen.mse) && hasEnteredCustom)
				{
					hasHitNext = true;
					
				}
				
				
				
				
			}
			
			if(hasHitNext)
			{
				for(int m=0;m<buttonTiles.length;m++)
				{
					if(buttonTiles[m].contains(Screen.mse)){
						if(m==0)
							color=0;
						else
							if(m==1)
								color=1;
							else
								if(m==2)
									color=2;
								else
									color=-1;
						
						System.out.println("clicked on tile");
						System.out.println("Color is: " + color);
					}
				}
				
				if(rectScreen.contains(Screen.mse))
				{
					boolean hasStart = false;
					boolean hasEnd = false;
					
					if(color==1 || color==2)
					{
						
						for(int x=0;x<level.length;x++)
						{
							for(int y=0;y<level[0].length;y++)
							{
								if(level[x][y] ==1)
									hasStart= true;
								
								if(level[x][y] ==2)
									hasEnd= true;
							}
						}
						
					}
					
					if(!hasStart && color==1)
					{
					int blockX=(Screen.mse.x-38)/52;
					int blockY = Screen.mse.y/52;
					level[blockY][blockX]  = color;
					System.out.println("clicked the screen @ block " +blockX + blockY);
					}
					else
					if(!hasEnd && color==2)
					{

					int blockX=(Screen.mse.x-38)/52;
					int blockY = Screen.mse.y/52;
					level[blockY][blockX]  = color;
					System.out.println("clicked the screen @ block " +blockX + blockY);
					}
					else if(!hasStart && !hasEnd)
					{

						int blockX=(Screen.mse.x-38)/52;
						int blockY = Screen.mse.y/52;
						level[blockY][blockX]  = color;
						System.out.println("clicked the screen @ block " +blockX + blockY);
					}
						
				}	
				
				if(buttonReset.contains(Screen.mse))
				{
					for(int x=0;x<level.length;x++)
					{
						for(int y=0;y<level[0].length;y++)
						{
							level[x][y] = -1;
						}
					}
					
				}
				
				if(buttonCancel.contains(Screen.mse))
				{
					for(int x=0;x<level.length;x++)
					{
						for(int y=0;y<level[0].length;y++)
						{
							level[x][y] = -1;
						}
					}
					
					hasHitNext=false;
					hasEnteredSelect=true;
					hasEnteredCustom = false;
					
				}
				
				if(buttonSave.contains(Screen.mse))
				{
					for(int x=0;x<level.length;x++)
					{
						for(int y=0;y<level[0].length;y++)
						{
							System.out.print(level[x][y] + " ");
						}
						System.out.println();
					}
					
					for(int x=0;x<level.length;x++)
					{
						for(int y=0;y<level[0].length;y++)
						{
							if(level[x][y] ==1)
								getStartTile(x,y);
								
							if(level[x][y] ==2)
									getEndTile(x,y);
									
							if(level[x][y] ==-1)
									levelBlock[x][y]=0;
							
							if(level[x][y] ==0)
								getRoadTile(x,y);
						}
					}
					System.out.println("finished loading val to block");

					try{
						File file = new File("save/customMission.miss");
						  // Create file 
						System.out.println("Starting to make file");
						  FileWriter fstream = new FileWriter(file);
						  BufferedWriter out = new BufferedWriter(fstream);
						  out.write("R");
						  out.newLine();
						  out.write("1:12,200,10,15,3000");
						  out.newLine();
						  out.write("2:12,200,20,15,1000X8,200,10,15,1000");
						  out.newLine();
						  out.write("R");
						  out.newLine();
						  System.out.println("Round entered correctly");
						  out.newLine();
						  
						  
							for(int x=0;x<levelBlock.length;x++)
							{
								for(int y=0;y<levelBlock[0].length;y++)
								{
									//System.out.println(levelBlock[x][y]);
									out.write(""+levelBlock[x][y]);
								}
								out.newLine();
							}
							out.newLine();
							for(int x=0;x<levelBlock.length;x++)
							{
								for(int y=0;y<levelBlock[0].length;y++)
								{
									out.write("-1");
								}
								out.newLine();
							}
	
							
						 
						  //Close the output stream
						  out.close();
						  
						  loadLevel=4;
							Sound.MENU.stop();
							Sound.GAME.loop();
						  }catch(IOException e) {
								e.printStackTrace();
							}
					System.out.println("should have saved the game");
				}
			}
		}
	}
	
	private void getStartTile(int x, int y)
	{
//		if(x==0)
//		{
//			if(level[x+1][y]==0 )
//				levelBlock[x][y].groundId=7;
//			
//			if(level[x][y+1]==0 )
//				levelBlock[x][y].groundId=8;
//		}
//		else
//		{
//			if(level[x+1][y]==0 )
//				levelBlock[x][y].groundId=7;
//			
//			if(level[x][y+1]==0 )
//				levelBlock[x][y].groundId=8;
//		}
		levelBlock[x][y]=7;
	}
	
	private void getEndTile(int x, int y)
	{
//		if(x==0)
//		{
//			if(level[x+1][y]==0 )
//				levelBlock[x][y].groundId=7;
//			
//			if(level[x][y+1]==0 )
//				levelBlock[x][y].groundId=8;
//		}
//		else
//		{
//			if(level[x+1][y]==0 )
//				levelBlock[x][y].groundId=7;
//			
//			if(level[x][y+1]==0 )
//				levelBlock[x][y].groundId=8;
//		}
		levelBlock[x][y]=10;
	}
	
	private void getRoadTile(int x,int y)
	{
//		public static int GroundGrass = 0;
//		public static int groundRoad = 1;//need to change this since we have multiple ground tiles
//		public static int groundRoadStartHoriz = 7;
//		public static int groundRoadStartVert = 8;
//		public static int groundRoadFinVert = 9;
//		public static int groundRoadFinHoriz = 10;
//		0  0  0  0  0  0  0  0  0  0  0  0
//		7  1  6  0  5  1  1  1  1  1  6  0
//		0  0  2  0  2  0  0  0  0  0  2  0
//		0  0  2  0  3  1  1  6  0  0  2  0
//		0  0  2  0  0  0  0  2  0  0  2  0
//		0  0  2  0  0  0  0  2  0  0  2  0
//		0  0  3  1  1  1  1  4  0  0  3  10
//		0  0  0  0  0  0  0  0  0  0  0  0
		

//-1 -1 -1 -1 -1 -1 -1 -1 -1 -1 -1 -1 
//-1 -1 -1 -1 -1  0  0  0 -1 -1 -1 -1 
//-1 -1 -1 -1 -1  0 -1  0 -1 -1 -1 -1 
// 1  0  0  0 -1  0 -1  0 -1 -1 -1 -1 
//-1 -1 -1  0 -1  0 -1  0  0 0 0 2 
//-1 -1 -1  0 -1  0 -1 -1 -1 -1 -1 -1 
//-1 -1 -1  0  0  0 -1 -1 -1 -1 -1 -1 
//-1 -1 -1 -1 -1 -1 -1 -1 -1 -1 -1 -1 
		System.out.println("X: " + x + " Y: "+ y);

//		if(x !=0 || x!=7)
//		{
//		
//			if(level[x+1][y]>-1 && level[x-1][y]>-1 )
//				levelBlock[x][y]=2;
//
//		}
//		System.out.println("passed 1st");
//		
//		if(y!=0 || y!=11)
//		{
//			if(level[x][y+1]>-1 && level[x][y-1]>-1 )
//				levelBlock[x][y]=1;
//		}
//		
//		System.out.println("passed 2nd");
//		
//		if(x!=0 && y!=11)
//		{
//			if(level[x-1][y]>-1 && level[x][y+1]>-1 )
//				levelBlock[x][y]=3;
//		}
//		System.out.println("passed 3rd");
//		
//		if(x!=0 && y!=0)
//		{
//			if(level[x-1][y]>-1 && level[x][y-1]>-1 )
//				levelBlock[x][y]=4;
//		}
//		System.out.println("passed 4th");
//		
//		if(x!=7 && y!=11)
//		{
//			if(level[x+1][y]>-1 && level[x][y+1]>-1 )
//				levelBlock[x][y]=5;
//		}
//		
//		System.out.println("passed 5st");
//	
//		if(x!=7 && y!=0)
//		{
//			if(level[x+1][y]>-1 && level[x][y-1]>-1 )
//			{
//				System.out.println(x + " " + y);
//				levelBlock[x][y]=6;
//			}
//		}
//		System.out.println("passed 6st");
//		
//		System.out.println("end:(");
		
		if(x !=0 && x!=7)
		{
		
			if(level[x+1][y]>-1 && level[x-1][y]>-1 )
				levelBlock[x][y]=2;

		}
		System.out.println("passed 1st");
		
		if(y!=0 && y!=11)
		{
			if(level[x][y+1]>-1 && level[x][y-1]>-1 )
				levelBlock[x][y]=1;
		}
		
		System.out.println("passed 2nd");
		
		if(x!=0 && y!=11)
		{
			if(level[x-1][y]>-1 && level[x][y+1]>-1 )
				levelBlock[x][y]=3;
		}
		System.out.println("passed 3rd");
		
		if(x!=0 && y!=0)
		{
			if(level[x-1][y]>-1 && level[x][y-1]>-1 )
				levelBlock[x][y]=4;
		}
		System.out.println("passed 4th");
		
		if(x!=7 && y!=11)
		{
			if(level[x+1][y]>-1 && level[x][y+1]>-1 )
				levelBlock[x][y]=5;
		}
		
		System.out.println("passed 5st");
	
		if(x!=7 && y!=0)
		{
			if(level[x+1][y]>-1 && level[x][y-1]>-1 )
			{
				System.out.println(x + " " + y);
				levelBlock[x][y]=6;
			}
		}
		System.out.println("passed 6st");
		
		System.out.println("end:(");


		
			
	}

	private void define() {
		
		rectScreen = new Rectangle(38,0,624,416);
		for(int i=0;i<buttonChoices.length;i++)
		{
			buttonChoices[i] = new Rectangle(700/2-(150/2),300+(i*20)+(i*buttonSpacing)+20,150,20);
		}
		
		for(int i=0;i<buttonLevels.length;i++)
		{
			buttonLevels[i] = new Rectangle(50+(180*i)+(i*levelSpacing),(550/2)-60,180,120);
		}
		
		for(int i=0;i<buttonTiles.length;i++)
		{
			buttonTiles[i] = new Rectangle(74+(146*i),445,52,52);
		}
		
		buttonSave = new Rectangle(buttonTiles[3].x+78,buttonTiles[3].y-20,65,26);
		buttonReset = new Rectangle(buttonSave.x,buttonSave.y+30,65,26);
		buttonCancel = new Rectangle(buttonReset.x,buttonReset.y+30,65,26);



		
		
		buttonSelectLeftTop = new Rectangle(100,300,26,26);
		buttonSelectRightTop = new Rectangle(225,300,26,26);
		
		buttonSelectLeftMiddle = new Rectangle(100,320,26,26);
		buttonSelectRightMiddle = new Rectangle(225,320,26,26);
		
		buttonSelectLeftBot = new Rectangle(100,340,26,26);
		buttonSelectRightBot = new Rectangle(225,340,26,26);
		
		buttonCustom = new Rectangle(700/2-(150/2),400,150,30);
		buttonBack = new Rectangle(700/2-(150/2),450,150,30);
		
		for(int x=0;x<level.length;x++)
		{
			for(int y=0;y<level[0].length;y++)
			{
				level[x][y] = -1;
				levelBlock[x][y] = 0;
			}
		}
		
		for(int y=0;y<levelDisplay.length;y++)
		{
			for(int x=0;x<levelDisplay[0].length;x++)
			{
				levelDisplay[y][x] = new Rectangle(38+(52*x),52*y,52,52);
			}
		}
		
	}
	
	public void draw(Graphics g)
	{
		g.setFont(new Font("minecraftia",Font.BOLD,14));
		if(!hasEnteredSelect)
		{
			
		g.drawImage(Screen.title_screen[6],-px,0,700,550,null);
		g.drawImage(Screen.title_screen[6],700-px,0,700,550,null);
		
		if(px % 16<8)	
			g.drawImage(Screen.title_screen[5],0,0,700,550,null);
		else
			g.drawImage(Screen.title_screen[7],0,0,700,550,null);
		
		g.setFont(new Font("minecraftia",Font.BOLD,14));
		
		if(frame>=time)
		{
			px++;
			frame=0;
		}
		frame++;
		
		if(px>700)
			px=0;
		
		g.setColor(new Color(255,255,255,50));
		
		for(int i=0;i<buttonChoices.length;i++)
		{
			if(buttonChoices[0].contains(Screen.mse))
			{
				lastOnTop = true;
				lastOnBot = false;
				lastOnMid = false;
			}
			if(buttonChoices[1].contains(Screen.mse))
			{
				lastOnTop = false;
				lastOnBot = false;
				lastOnMid = true;
			}
			if(buttonChoices[2].contains(Screen.mse))
			{
				lastOnTop = false;
				lastOnBot = true;
				lastOnMid = false;
			}
			
			
				
				
				if(lastOnTop)
				{
					g.fillRect(buttonChoices[0].x,buttonChoices[0].y,buttonChoices[0].width,buttonChoices[0].height);
					g.drawImage(Screen.title_screen[1], buttonChoices[0].x - 28,buttonChoices[0].y-3,26,26,null);
					g.drawImage(Screen.title_screen[2], buttonChoices[0].x + buttonChoices[0].width,buttonChoices[0].y-3,26,26,null);	
					
					//g.drawImage(Screen.tileset_mob[3], buttonChoices[0].x - 28,buttonChoices[0].y-3,26,26,null);  use the mob image for this maybe?
					//g.drawImage(Screen.tileset_mob[1], buttonChoices[0].x + buttonChoices[0].width,buttonChoices[0].y-3,26,26,null);	
				}
				
				if(lastOnMid)
				{
					g.fillRect(buttonChoices[1].x,buttonChoices[1].y,buttonChoices[1].width,buttonChoices[1].height);
					g.drawImage(Screen.title_screen[1], buttonChoices[1].x - 28,buttonChoices[1].y-3,26,26,null);
					g.drawImage(Screen.title_screen[2], buttonChoices[1].x + buttonChoices[1].width,buttonChoices[1].y-3,26,26,null);	
				}
				
				if(lastOnBot)
				{
					g.fillRect(buttonChoices[2].x,buttonChoices[2].y,buttonChoices[2].width,buttonChoices[2].height);
					g.drawImage(Screen.title_screen[1], buttonChoices[2].x - 28,buttonChoices[2].y-3,26,26,null);
					g.drawImage(Screen.title_screen[2], buttonChoices[2].x + buttonChoices[2].width,buttonChoices[2].y-3,26,26,null);	
				}
				
		}
		
		
		
		
		g.setColor(new Color(0,0,0));
		for(int i=0;i<myChoices.length;i++)
		{
			if(i==0)
				g.drawString(myChoices[i], (700/2-(100/2))+20, buttonChoices[i].y+17);
			else
				if(i==1)
					g.drawString(myChoices[i], (700/2-(100/2))-15, buttonChoices[i].y+17);
				else
					if(i==2)
						g.drawString(myChoices[i], (700/2-(100/2))+17, buttonChoices[i].y+17);
		}
			
		
			
			if(hasEnteredDirections)
			{
				g.setColor(yellow);
				g.fillRect(0, 0, 700, 550);
				g.setColor(red);
				g.fillRect(buttonBack.x, buttonBack.y, buttonBack.width, buttonBack.height);
				g.setColor(black);
				g.drawString("BACK",buttonBack.x+50,buttonBack.y+20);
			}
			
			if(hasEnteredCredits)
			{
				g.setColor(blue);
				g.fillRect(0, 0, 700, 550);
				g.setColor(red);
				g.fillRect(buttonBack.x, buttonBack.y, buttonBack.width, buttonBack.height);
				g.setColor(black);
				g.drawString("BACK",buttonBack.x+50,buttonBack.y+20);
			}
			
	}
			
			if(hasEnteredSelect && !hasEnteredCustom)
			{
				g.drawImage(Screen.level_select[3],0,0,700,550,null);
				g.setColor(new Color(255,255,255,50));
				for(int i=0;i<buttonLevels.length;i++)
				{
					g.drawImage(Screen.level_select[i], buttonLevels[i].x,buttonLevels[i].y, buttonLevels[i].width, buttonLevels[i].height, null);
					if(buttonLevels[i].contains(Screen.mse))
					{
						g.fillRect(buttonLevels[i].x,buttonLevels[i].y,buttonLevels[i].width,buttonLevels[i].height);
					}
				}
				g.setColor(red);
				
				if(buttonBack.contains(Screen.mse))
				{
					g.setColor(new Color(175,40,40));
					g.fillRect(buttonBack.x, buttonBack.y, buttonBack.width, buttonBack.height);
					g.setColor(red);
				}
				else
					g.fillRect(buttonBack.x, buttonBack.y, buttonBack.width, buttonBack.height);
				
				if(buttonCustom.contains(Screen.mse))
				{
					g.setColor(new Color(175,40,40));
					g.fillRect(buttonCustom.x, buttonCustom.y, buttonCustom.width, buttonCustom.height);
				}
				else
					g.fillRect(buttonCustom.x, buttonCustom.y, buttonCustom.width, buttonCustom.height);
					
				
			
				
				g.setColor(black);
				g.drawString("BACK",buttonBack.x+50,buttonBack.y+20);
				g.drawString("Custom Level",buttonCustom.x+10,buttonCustom.y+20);
			}
			
			if(hasEnteredSelect && hasEnteredCustom && !hasHitNext)
			{
				g.drawImage(Screen.title_screen[3],0,0,700,550,null);//add 
				g.setColor(red);
				g.fillRect(buttonBack.x, buttonBack.y, buttonBack.width, buttonBack.height);
				g.setColor(black);
				g.drawString("Continue",buttonBack.x+35,buttonBack.y+20);
			}
			
			if(hasHitNext)
			{
				g.setColor(new Color(74,74,74));
				g.fillRect(0, 0, 700, 550);
				//g.drawImage(Screen.title_screen[2],0,0,700,550,null);//add 
//				public int worldWidth = 12;
//				public int worldHeight = 8;
//				public int blockSize = 52;
				for(int y=0;y<levelDisplay.length;y++)
				{
					for(int x=0;x<levelDisplay[0].length;x++)
					{
						
						if(level[y][x]==0)
							g.setColor(new Color(74,74,74));
						else
							if(level[y][x]==1)
								g.setColor(blue);
							else
						if(level[y][x]==2)
							g.setColor(red);
							else
								g.setColor(new Color(139,181,74));
						
						g.fillRect(levelDisplay[y][x].x,levelDisplay[y][x].y,levelDisplay[y][x].width,levelDisplay[y][x].height);
						g.setColor(black);
						g.drawRect(levelDisplay[y][x].x,levelDisplay[y][x].y,levelDisplay[y][x].width,levelDisplay[y][x].height);
					}
				}
				
				for(int i=0;i<buttonTiles.length;i++)
				{
					
					
					if(i==0)
					{
						g.setColor(new Color(74,74,74));
						g.fillRect(buttonTiles[i].x,buttonTiles[i].y,buttonTiles[i].width,buttonTiles[i].height);
						g.setFont(new Font("Courier New",Font.BOLD,16));
						g.setColor(standard);
						g.drawString("Road Tile",buttonTiles[i].x-15,buttonTiles[i].y-7);
						
					}
					else
						if(i==1)
						{
							g.setColor(blue);
							g.fillRect(buttonTiles[i].x,buttonTiles[i].y,buttonTiles[i].width,buttonTiles[i].height);
							g.setFont(new Font("Courier New",Font.BOLD,16));
							g.setColor(standard);
							g.drawString("Start Tile",buttonTiles[i].x-17,buttonTiles[i].y-7);
						}
						else
							if(i==2)
							{
								g.setColor(red);
								g.fillRect(buttonTiles[i].x,buttonTiles[i].y,buttonTiles[i].width,buttonTiles[i].height);
								g.setFont(new Font("Courier New",Font.BOLD,16));
								g.setColor(standard);
								g.drawString("End Tile",buttonTiles[i].x-12,buttonTiles[i].y-7);
							}
							else
							{
								g.setFont(new Font("Courier New",Font.BOLD,16));
								g.setColor(standard);
								g.drawString("Eraser",buttonTiles[i].x-4,buttonTiles[i].y-7);
							}
					
					g.setColor(red);
					if(color==-1)
					{
						g.drawString("Active",buttonTiles[3].x-3,buttonTiles[3].y+65);
					}
						else
						{
							g.drawString("Active",buttonTiles[color].x-3,buttonTiles[color].y+65);
						}
					if(i==3)
						g.drawImage(Screen.tileset_str[10], buttonTiles[i].x,buttonTiles[i].y,buttonTiles[i].width,buttonTiles[i].height,null);
					else
						g.drawImage(Screen.tileset_str[0], buttonTiles[i].x,buttonTiles[i].y,buttonTiles[i].width,buttonTiles[i].height,null);
					
				}
				
				g.drawImage(Screen.tileset_str[7],buttonSave.x,buttonSave.y,65,26,null);
				g.drawImage(Screen.tileset_str[8],buttonReset.x,buttonReset.y,65,26,null);
				g.drawImage(Screen.tileset_str[9],buttonCancel.x,buttonCancel.y,65,26,null);
				}
			
		}


	
	
	
}
