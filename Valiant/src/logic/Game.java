/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.awt.Graphics2D;

/**
 *
 * @author Adam
 */
public abstract class Game{
    
    public String name;
    
    public Game(String n){
        name = n;
    }
    
    public abstract void render(Graphics2D g, int framesPassed);
    
    public abstract void start();
    
}
