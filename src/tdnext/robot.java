package tdnext;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
 
/**
 * A Java Robot example class.
 * @author Alvin Alexander, <a href="http://devdaily.com" title="http://devdaily.com">http://devdaily.com</a>
 * @modifier Maple
 */
public class robot
{
  Robot robot = new Robot();
 
  public static void main(String[] args) throws AWTException
  {
    new robot();
  }
   
  public robot() throws AWTException
  {  
	System.out.println("Start of robot");
	robot.delay(4000);


//Check btnHelp
    System.out.println("Try btnHelp");
    robot.delay(1000);
	robot.mouseMove(531, 524);//to btnHelp
	robot.delay(500);
	leftClick();
	robot.delay(2000);
	enter();
	enter();
	
	
//Check btnTheme
    System.out.println("Try btnTheme");
    robot.delay(2000);
    robot.mouseMove(532,559); //to btnTheme
    leftClick();
	robot.delay(1000);
	robot.mouseMove(829,456);//Choices
	leftClick();
	robot.delay(500);
	robot.mouseMove(829,452);//to "Lavender"
	System.out.println("Lavender Theme selected");
	robot.delay(1000);
	leftClick();
	robot.delay(1000);
	robot.mouseMove(810, 515);//o OK
	leftClick();
	enter();
	
	robot.mouseMove(532,559); //to btnTheme
    leftClick();
	robot.delay(1000);
	robot.mouseMove(829,456);//Choices
	leftClick();
	robot.delay(500);
	robot.mouseMove(829,492);//to "Panda"
	System.out.println("Panda Theme selected");
	robot.delay(1000);
	leftClick();
	robot.delay(1000);
	robot.mouseMove(810, 515);//o OK
	leftClick();
	enter();
	
	 	robot.mouseMove(532,559); //to btnTheme
	    leftClick();
		robot.delay(1000);
		robot.mouseMove(829,456);//Choices
		leftClick();
		robot.delay(500);
		robot.mouseMove(829,492);//to "Sapphire"
		System.out.println("Sapphire Theme selected");
		robot.delay(1000);
		leftClick();
		robot.delay(1000);
		robot.mouseMove(810, 515);//o OK
		leftClick();
		enter();
	
	  
//Enter events
	System.out.println("Events start");
	
    ImageIcon test = new ImageIcon("emptytest.png");
    String testImage = test.toString();
    System.out.println("emptytest.png toString: " + testImage);
    BufferedImage image = robot.createScreenCapture(new Rectangle(440, 210, 124,154));
    System.out.println("Try Sceenshot 1");
    robot.delay(2000);
    String screenShot = new String();
    screenShot = image.toString();
    System.out.println("scrrenShot toString: " + screenShot);
    if(testImage == screenShot){
    	System.out.println("Pass.");
    }else{
    	System.out.println("Fail.");
    }
    
	robot.delay(2000);
    robot.mouseMove(138, 548);//to textInput
    robot.delay(500);
    leftClick();
    robot.delay(500);
    robot.mouseMove(300, 700);//mouse does not block
    robot.delay(500);
    
    type("add autoTask1");
    robot.delay(1000);
    enter();
    
    type("add autoTask2");
    robot.delay(1000);
    enter();
    
    type("add autoTask3 important");
    robot.delay(1000);
    enter();
    
    type("add autoTask4 by 22/10/2015");
    robot.delay(1000);
    enter();
    
    type("add autoTask5 by 30/11/2015");
    robot.delay(1000);
    enter();
    
    type("delete 5");
    robot.delay(1000);
    enter();
    robot.delay(3000);
    
    type("undo");
    robot.delay(1000);
    enter();
    robot.delay(3000);
    
    type("edit 5 autoTask edited ver");
    robot.delay(1000);
    enter();
    robot.delay(3000);
    
    type("undo");
    robot.delay(1000);
    enter();
    robot.delay(3000);
    
    type("done 1");
    robot.delay(1000);
    enter();
    robot.delay(3000);
    
    type("sort name");
    robot.delay(1000);
    enter();
    robot.delay(3000);
    
    type("add autoTask6 on 23/10/2015");
    robot.delay(1000);
    enter();
    
    type("add autoTask7 on 24/10/2015");
    robot.delay(1000);
    enter();
    
    type("add autoTask8 on 23/11/2015");
    robot.delay(1000);
    enter();
    
    type("add autoTask9 on 15/12/2015");
    robot.delay(1000);
    enter();
    
    type("add autoTask10 on 23/11/2016");
    robot.delay(1000);
    enter();
    
    type("add autoTask11 important");
    robot.delay(1000);
    enter();
    
    type("add autoTask12 important");
    robot.delay(1000);
    enter();
    
    type("add autoTask13");
    robot.delay(1000);
    enter();
    
    type("add autoTask14");
    robot.delay(1000);
    enter();
    
    type("add autoTask15");
    robot.delay(1000);
    enter();
    
    type("add autoTask16");
    robot.delay(1000);
    enter();
    
    //Try Screenshot
    /*
    BufferedImage image2 = robot.createScreenCapture(new Rectangle(440, 210, 124,154));
    System.out.println("Try Sceenshot 2");
    robot.delay(2000);
    String screenShot2 = new String();
    screenShot2 = image2.toString() + "/n";
    robot.delay(4000);
    */
    
    System.out.println("Try scrolling 1");
    robot.delay(2000);
    robot.mouseMove(545, 340);
    robot.delay(1000);
    robot.mouseWheel(500);//scroll up
    robot.delay(2000);
    robot.mouseWheel(-500);//scroll down
    robot.delay(2000);
    
    type("sort deadline");
    System.out.println("Try SORT BY DEADLINE");
    robot.delay(2000);
    enter();
    robot.delay(500);
    System.out.println("Try scrolling 2");
    robot.delay(2000);
    robot.mouseWheel(500);//scroll to the top
    robot.delay(3000);
    
    type("sort default");
    System.out.println("Try SORT BY DEFAULT");
    robot.delay(2000);
    enter();
    robot.delay(500);
    System.out.println("Try scrolling 3");
    robot.delay(2000);
    robot.mouseWheel(500);//scroll to the top
    robot.delay(3000);
    
    type("clear");
    robot.delay(1000);
    enter();

//Exit GUI
    robot.delay(2000);
    type("exit");//exit GUI2
    System.out.println("End of robot");
    robot.delay(2000);
    enter();
    System.exit(0);//exit robot
  }
   
  private void leftClick()
  { 
	robot.delay(100);
    robot.mousePress(InputEvent.BUTTON1_MASK);
    robot.mouseRelease(InputEvent.BUTTON1_MASK);
    
  }
 
  private void enter(){
	  robot.delay(50);
	  robot.keyPress(KeyEvent.VK_ENTER);
	  robot.keyRelease(KeyEvent.VK_ENTER);
  }
  
  private void type(String s)
  {
    byte[] bytes = s.getBytes();
    for (byte b : bytes)
    {
      int code = b;
      // keycode only handles [A-Z] (which is ASCII decimal [65-90])
      if (code > 96 && code < 123) code = code - 32;
      robot.delay(40);
      robot.keyPress(code);
      robot.keyRelease(code);
    }
  }
}