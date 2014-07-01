package client.remote;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.io.IOException;
import java.util.Random;

public class FireEvent_Test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		Random random = new Random();
		System.out.println(random.nextInt(1000000000));//10억가지 랜덤 수
//		
//		Robot robot;
//		
//
//		Runtime.getRuntime().exec("notepad");
//		try {				
//			
//			robot = new Robot();
//			robot.mousePress(InputEvent.BUTTON1_MASK );
//			robot.mouseRelease(InputEvent.BUTTON1_MASK );
////			robot.mouseMove(200, 200);			
//			
////			robot.keyPress(java.awt.event.KeyEvent.VK_CONTROL);
////			robot.keyPress(java.awt.event.KeyEvent.VK_SHIFT);
//			robot.keyPress(java.awt.event.KeyEvent.VK_O);
//							
////			robot.keyRelease(java.awt.event.KeyEvent.VK_CONTRo
//			robot.keyRelease(java.awt.event.KeyEvent.VK_O);					
//			
//			Where_mouse_point();
//			
//		} catch (AWTException e) {
//			// TODO Auto-generated catch bloc					
//			e.printStackTrace();
//		}	
//	}
//	private static void Where_mouse_point(){
//		PointerInfo pointerInfo = MouseInfo.getPointerInfo();
//		System.out.println("Mouse Position" + pointerInfo.getLocation());
//		
//		Dimension res = Toolkit.getDefaultToolkit().getScreenSize();
//		  System.out.println("해상도 : " + res.width + " x " + res.height);  
//
//
	}
}
