package server.remote;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Robot;

public class RoboMouse extends RoundSprite{
	 
	  Robot robot;
	  float localX, localY;
		PointerInfo pointerInfo = MouseInfo.getPointerInfo();
		
	  public RoboMouse(float x, float y, float radius, 
	               float speedX, float speedY){
	    super(x, y, radius, speedX, speedY);
	    localX = pointerInfo.getLocation().x;
	    localY = pointerInfo.getLocation().y;
	    try { 
	      robot = new Robot();
	    } 
	    catch (AWTException e) {
	      e.printStackTrace(); 
	    }
	  }
	 
	  void checkBoundaries(){
	    if (x>width-radius+localX){
	      x = width-radius+localX;
	      speedX *= -1;
	    }
	    if (x<radius+localX){
	      x = radius+localX;
	      speedX *= -1;
	    }
	    if (y>height-radius+localY){
	      y = height-radius+localY;
	      speedY *= -1;
	    }
	    if (y<radius+localY){
	      y = radius+localY;
	      speedY *= -1;
	    }
	  }
	 
	  public void move(){
	    x += speedX;
	    y += speedY;
	    robot.mouseMove(pointerInfo.getLocation().x+(int)x, 
	    		pointerInfo.getLocation().y+(int)y);
	  }
	}