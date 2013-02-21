package com.joshdoctors.game.levels;

public class Value {
	public static int GroundGrass = 0;
	public static int groundRoad = 1;//need to change this since we have multiple ground tiles
	public static int groundRoadStartHoriz = 7;
	public static int groundRoadStartVert = 8;
	public static int groundRoadFinVert = 9;
	public static int groundRoadFinHoriz = 10;
	
	public static int airAir = -1;
	public static int airRecycle = 1;
	public static int airLaserTower =2;
	public static int airSpikeTower =3;
	public static int airCannon =4;
	
	public static int mobAir = -1;
	public static int mobTank = 0;
	public static int mobHeli = 4;
	public static int mobPoliceCar = 8;
	public static int mobTaxi = 12;
	public static int mobJet = 16;
	public static int mobSuperTank = 20;
	
	public static int[] deathReward = { 5,0,0,0,10,0,0,0,2,0,0,0,1,0,0,0,15,0,0,0,20,0,0,0};

}
