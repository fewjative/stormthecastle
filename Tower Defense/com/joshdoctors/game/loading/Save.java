package com.joshdoctors.game.loading;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import com.joshdoctors.game.Menu;
import com.joshdoctors.game.gfx.Screen;

public class Save {
	public void loadSave(File loadPath)
	{
		try {
			System.out.println("attempting to load file...");
			Scanner loadScanner = new Scanner(loadPath);
			System.out.println("loaded file");
			if(loadScanner.hasNext())
			{
				String str = loadScanner.nextLine();
			//	Screen.killsToWin = loadScanner.nextInt();
				//Screen.mobsToSpawn = Screen.killsToWin;
				if(str.equals("R"))
				{
					str=loadScanner.nextLine();
					while(!str.equals("R"))
					{
						System.out.println(str.substring(0,1));
						Screen.rounds[Integer.parseInt(str.substring(0,1))]=str.substring(2);
						str=loadScanner.nextLine();
					}
				}

				
				if(Menu.loadLevel==4)
				{
					System.out.println("loading custom");
					while(str.length() <11)
					{
						str=loadScanner.nextLine();
					}
//						for(int y=0;y<Screen.room.block.length;y++)
//						{
//							for(int x=0;x<Screen.room.block[0].length;x++)
//							{
//								System.out.println(str);
////								System.out.print("Row " + y + " " +Integer.parseInt(str.substring(x,x+1)));
//								Screen.room.block[y][x].groundId = Integer.parseInt(str.substring(x,x+1));
//							
//							}
//							loadScanner.nextLine();
//							System.out.println("\nbreak");
//						}
					
					for(int y=0;y<Screen.room.block.length;y++)
						{
							for(int x=0;x<Screen.room.block[0].length;x++)
							{
//								System.out.print("Row " + y + " " +Integer.parseInt(str.substring(x,x+1)));
								Screen.room.block[y][x].groundId = Menu.levelBlock[y][x];
							
							}
							System.out.println("\nbreak");
						}
						
						for(int y=0;y<Screen.room.block.length;y++)
						{
							for(int x=0;x<Screen.room.block[0].length;x++)
							{
								System.out.print(Screen.room.block[y][x].groundId + " ");
							}
							System.out.println();
						}
						
						for(int y=0;y<Screen.room.block.length;y++)
						{
							for(int x=0;x<Screen.room.block[0].length;x++)
							{
								//Screen.room.block[y][x].airId = Integer.parseInt(str.substring(x,x+2));
								//System.out.println(Integer.parseInt(str.substring(2*x,(2*x)+1)));
								Screen.room.block[y][x].airId = -1;
							}
							loadScanner.nextLine();
						}
						System.out.println("finished loading");
						System.out.println(str);
						System.out.println(loadScanner.hasNext());
						//str = loadScanner.nextLine();
					
				}
				else
				{
					for(int y=0;y<Screen.room.block.length;y++)
					{
						for(int x=0;x<Screen.room.block[0].length;x++)
						{
							Screen.room.block[y][x].groundId = loadScanner.nextInt();
						}
					}
					
					for(int y=0;y<Screen.room.block.length;y++)
					{
						for(int x=0;x<Screen.room.block[0].length;x++)
						{
							Screen.room.block[y][x].airId = loadScanner.nextInt();
						}
					}
				}
				
				
			}
			
			loadScanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
