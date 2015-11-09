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
	  
	//@@author A0113507R
	  /* The code is used for author A0113507R only!
	   * DO NOT USE THIS CODE ON YOUR MACHINE!!!
	   * And it is for manual GUI testing only. 
	   * Since our group did not choose GUI automated testing as our extra feature,
	   * and there is time constrain,
	   * I did not continue to expend the code to compare graphics.
	   */
	System.out.println("Start of robot");
	robot.delay(4000);

//Check btnHelp
    System.out.println("Try btnHelp");
    robot.delay(1000);
	robot.mouseMove(532, 535);//to btnHelp
	robot.delay(500);
	leftClick();
	robot.delay(2000);
	enter();
	
	System.out.println("Pressed F1 for HELP");
	robot.keyPress(KeyEvent.VK_F1);
	robot.keyRelease(KeyEvent.VK_F1);
	robot.delay(2000);
	enter();
	
	
//Check btnTheme
    System.out.println("Try btnTheme");
    robot.delay(2000);
    robot.mouseMove(532,570); //to btnTheme
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
	robot.mouseMove(810, 530);//o OK
	leftClick();
	robot.delay(1000);
	
	robot.mouseMove(532,570); //to btnTheme
    leftClick();
	robot.delay(1000);
	robot.mouseMove(829,456);//Choices
	leftClick();
	robot.delay(500);
	robot.mouseMove(829,472);//to "Panda"
	System.out.println("Panda Theme selected");
	robot.delay(1000);
	leftClick();
	robot.delay(1000);
	robot.mouseMove(810, 530);//o OK
	leftClick();
	robot.delay(1000);
	
	robot.mouseMove(532,570); //to btnTheme
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
	robot.mouseMove(810, 530);//o OK
	leftClick();
	robot.delay(1000);

    robot.mouseMove(532,570); //to btnTheme
    leftClick();
	robot.delay(1000);
	robot.mouseMove(829,456);//Choices
	leftClick();
	robot.delay(500);
	robot.mouseMove(829,512);//to "Forest"
	System.out.println("Lavender Theme selected");
	robot.delay(1000);
	leftClick();
	robot.delay(1000);
	robot.mouseMove(810, 530);//OK
	leftClick();
	robot.delay(1000);
	
	    robot.keyPress(KeyEvent.VK_F2);
	    robot.keyRelease(KeyEvent.VK_F2);
	    robot.delay(500);
	    robot.keyPress(KeyEvent.VK_KP_DOWN);
	    robot.keyRelease(KeyEvent.VK_KP_DOWN);
	    robot.delay(1000);
	    robot.keyPress(KeyEvent.VK_KP_DOWN);
	    robot.keyRelease(KeyEvent.VK_KP_DOWN);
	    robot.delay(1000);
	    robot.keyPress(KeyEvent.VK_KP_DOWN);
	    robot.keyRelease(KeyEvent.VK_KP_DOWN);
	    robot.delay(1000);
	    robot.keyPress(KeyEvent.VK_KP_DOWN);
	    robot.keyRelease(KeyEvent.VK_KP_DOWN);
	    robot.delay(1000);
	    robot.keyPress(KeyEvent.VK_KP_UP);
	    robot.keyRelease(KeyEvent.VK_KP_UP);
	    robot.delay(1000);
	    robot.keyPress(KeyEvent.VK_KP_UP);
	    robot.keyRelease(KeyEvent.VK_KP_UP);
	    robot.delay(1000);
	    robot.keyPress(KeyEvent.VK_KP_UP);
	    robot.keyRelease(KeyEvent.VK_KP_UP);
	    robot.delay(1000);
	    robot.keyPress(KeyEvent.VK_KP_UP);
	    robot.keyRelease(KeyEvent.VK_KP_UP);
	    robot.delay(1000);
	    enter();
		
	    robot.mouseMove(534,569); //to btnTheme
	    leftClick();
		robot.delay(1000);
		robot.mouseMove(710, 510);//Cancel
		robot.delay(500);
		leftClick();
		System.out.println("Cancel");
		robot.delay(1000);
		
		 robot.keyPress(KeyEvent.VK_F2);
		 robot.keyRelease(KeyEvent.VK_F2);
		 robot.delay(500);
		 robot.keyPress(KeyEvent.VK_ESCAPE);
		 robot.keyRelease(KeyEvent.VK_ESCAPE);
		 robot.delay(500);
		
		System.out.println("End of Theme demo");
		robot.delay(5000);
		
//Enter events
	System.out.println("Events start");
	
    /*
     * Image comparison not working yet
	ImageIcon test = new ImageIcon("emptytest.png");
    String testImage = test.toString();
    System.out.println("emptytest.png toString: " + testImage);
    BufferedImage image = robot.createScreenCapture(new Rectangle(440, 210, 124,154));
    System.out.println("Try Screenshot 1");
    robot.delay(2000);
    String screenShot = new String();
    screenShot = image.toString();
    System.out.println("scrrenShot toString: " + screenShot);
    if(testImage == screenShot){
    	System.out.println("Pass.");
    }else{
    	System.out.println("Fail.");
    }
    */
    
	robot.delay(2000);
    robot.mouseMove(138, 548);//to textInput
    robot.delay(500);
    leftClick();
    robot.delay(500);
    robot.mouseMove(300, 700);//mouse does not block
    robot.delay(500);
    
    System.out.println("Realistic tasks as ME");
    
    type("add Watch Running Man latest episode");
    robot.delay(1000);
    enter();
    
    type("add Watch The Walking Dead S6 Ep3");
    robot.delay(1000);
    enter();
    
    type("add Make appointment for BabeQui's grooming");
    robot.delay(1000);
    enter();
    
    type("add Resolve CS2103T V0.3 bugs by 26th Oct");
    robot.delay(1000);
    enter();
    
    type("add collect pocket money on 1st Dec");
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
    
    type("edit 5 Make appointment for Lya's grooming");
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
    
    type("add complete EG2401 slides by 30th Oct");
    robot.delay(1000);
    enter();
    
    type("add complete CS2101 OP2 slides by 1st Nov");
    robot.delay(1000);
    enter();
    
    type("add do CS2103T online assessment by 31st Oct");
    robot.delay(1000);
    enter();
    
    type("add CS2103T V0.5 deadline on 9th Nov ");
    robot.delay(1000);
    enter();
    
    type("add plan for dec trip by 2nd Dec");
    robot.delay(1000);
    enter();
    
    type("add Book appointment for Lya's important checkup");
    robot.delay(1000);
    enter();
    
    type("add Fix i6's screen, important");
    robot.delay(1000);
    enter();
    
    type("add Buy milk");
    robot.delay(1000);
    enter();
    
    type("add Buy ice cream");
    robot.delay(1000);
    enter();
    
    type("add Call Wei and check-on her, important");
    robot.delay(1000);
    enter();
    
    type("add clean timberland boots");
    robot.delay(1000);
    enter();
    
    type("add CG3207 Final Exam on 21st Nov 9am");
    robot.delay(1000);
    enter();
    
    type("add EG2401 Final Exam on 24th Nov 1pm");
    robot.delay(1000);
    enter();
    
    type("add CS2103T Final Exam on 26th Nov 1pm");
    robot.delay(1000);
    enter();
    
    type("add EE2021 Final Exam on 28th Nov 1pm");
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
    
    type("search cs2103t");
    robot.delay(1000);
    enter();
    System.out.println("Search for CS2103T- related tasks");
    robot.delay(2000);
    
    type("sort default");
    System.out.println("Try SORT BY DEFAULT");
    robot.delay(2000);
    enter();
    robot.delay(2000);
    
    type("sort name");
    System.out.println("Try SORT BY NAME");
    robot.delay(2000);
    enter();
    robot.delay(500);
    System.out.println("Try scrolling 2");
    robot.delay(2000);
    robot.mouseWheel(500);//scroll to the top
    robot.delay(3000);
    
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
    
  /*  type("clear");
    robot.delay(1000);
    enter();
    
//Exit GUI
    robot.delay(2000);
    type("exit");//exit GUI2
    System.out.println("End of robot");
    robot.delay(2000);
    enter();*/
    System.exit(0);//exit robot
  }
 
//@@author Alvin Alexander -generated
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