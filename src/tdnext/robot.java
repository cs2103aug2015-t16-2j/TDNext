package tdnext;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
 
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
	robot.mouseMove(531, 524);
	robot.delay(500);
	leftClick();
	robot.delay(2000);
	enter();
	enter();
	
	/*
//Check btnTheme
    System.out.println("Try btnTheme");
    robot.delay(2000);
    robot.mouseMove(532,559);
    leftClick();
	robot.delay(1000);
	robot.mouseMove(829,456);
	leftClick();
	robot.delay(500);
	robot.mouseMove(829,492);
	robot.delay(500);
	leftClick();
	robot.mouseMove(808, 515);
	leftClick();
	*/
	  
//Enter events
	System.out.println("Events start");
	robot.delay(2000);
    robot.mouseMove(138, 548);
    robot.delay(500);
    leftClick();
    robot.delay(500);
    robot.mouseMove(300, 549);
    
    type("add autoTask1");
    robot.delay(3000);
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
    
    type("sort by name");
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
    
    System.out.println("Try scrolling");
    robot.mouseMove(545, 340);
    robot.delay(1000);
    robot.mouseWheel(-400);
    robot.delay(2000);
    
    type("sort by deadline");
    robot.delay(1000);
    enter();
    robot.delay(3000);
    
    type("sort by default");
    robot.delay(1000);
    enter();
    robot.delay(3000);
    
    type("clear");
    robot.delay(1000);
    enter();

//Exit GUI
    System.out.println("End of robot");
    robot.delay(1000);
    type("exit");
    robot.delay(2000);
    enter();
    System.exit(0);
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
	  robot.keyRelease(KeyEvent.VK_V);
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