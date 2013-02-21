package com.joshdoctors.game;


import javax.swing.*;

import com.joshdoctors.game.gfx.Screen;

import java.awt.*;
 
public class Game extends JFrame
{
    public static String title = "Tower Defence";
    public static Dimension size = new Dimension(700, 550);
             
    public Game()
    {
        setTitle(title);
        setSize(size);
        setResizable(false); // negates a lot of bugs
        setLocationRelativeTo(null); // centred
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setFocusable(true);
         
        init();
    }
     

    public void init()
    {
        setLayout(new GridLayout(1, 1, 0, 0)); 
        Screen screen = new Screen(this);
        add(screen);
        setVisible(true);
    }
     
    public static void main(String args[])
    {
        Game game = new Game();
    }
     
}