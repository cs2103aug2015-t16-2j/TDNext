package tdnext;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Theme {
	//@@author A0113507R
	private static Logger themeLog= Logger.getLogger("Theme");
	
		private String _name;
	
		//Color-coding
		private Color _red;
		private Color _orange;
		private Color _green;
		private Color _white;
		
		private Color _displayBg; //Background for panelDisplay and textInput
		
		private Color _displayFontColor; //Font Color for panelDisplay's textAreas
		private Color _inputFontColor; //Font Color for textInput
		private Color _foreground; //Font Color for system
		private Color _background ; //Background Color for system

	public Theme(String s){
		_name = s;
		setDetails(_name);
		themeLog.log(Level.INFO, "Theme created is: " + s + ".");
	}
	
	public String getThemeName(){
		return _name;
	}
	
	public Color getColor(String c){
		switch(c.toLowerCase()){
		case "red":
			return _red; 
		case "orange":
			return _orange; 
		case "green":
			return _green;
		case "white":
			return _white;
		case "displaybg":
			return _displayBg;
		case "foreground":
			return _foreground;
		case "background":
			return _background;
		case "displayfont":
			return _displayFontColor;
		case "inputfont":
			return _inputFontColor;
		default:
			return null;
		}
	}
	
	public void setDetails(String s){
		if(s.equals("Wood")){
				_red = new Color(190, 107, 100);
				_orange = new Color(213, 162, 83);
				_green = new Color(201, 167, 152);
				_white = new Color(215, 182, 131);
				_displayBg = new Color(104, 86, 66);
				_foreground = new Color(233, 224, 219);
				_displayFontColor = new Color(188, 117, 63);
				_inputFontColor = _displayFontColor;
				_background = new Color(79, 36, 18);
				
			}else if(s.equals("Sapphire")){
				_red = new Color(79, 153, 204);
				_orange = new Color(116, 194, 225);
				_green = new Color(172, 209, 233);
				_white = new Color(210, 235, 251);
				_displayBg = new Color(88, 116, 152);
				_foreground = new Color(163, 214, 245);
				_displayFontColor = new Color(0, 91, 154);
				_inputFontColor = _displayFontColor;
				_background = _displayFontColor;
				
			}else if(s.equals("Forest")){
				_red = new Color(189, 145, 113);
				_orange = new Color(179, 165, 128);
				_green = new Color(183, 198, 139);
				_white = new Color(244, 240, 203);
				_displayBg = new Color(239, 235, 214);
				_foreground = new Color(68, 92, 68);
				_displayFontColor = new Color(104, 86, 66);
				_inputFontColor = _displayFontColor;
				_background = new Color(183, 198, 139);
			}else{
				_red = new Color(255, 195, 206);
				_orange = new Color(255, 207, 121);
				_green = new Color(142, 210, 201);
				_white = new Color(236, 236, 240);
				_displayBg = new Color(255, 255, 255);
				_foreground = new Color(70, 32, 102);
				_displayFontColor = _foreground;
				_inputFontColor = _foreground;
				_background = new Color(230, 230, 250);
			}
	}
}
