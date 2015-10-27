package tdnext;

import java.awt.Color;

public class ThemeAPI {
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
		
		private static String _systemFontType;
		
		private final int _n = 2; //Total number of themes supported currently
		
	//Lavender Default
	public ThemeAPI(String s){
		_name = new String(s);
		setDetails(_name);
		System.out.println("ThemeAPI set as " + _name);
	}
	
	public String getThemeName(){
		return _name;
	}
	
	public int getNumber(){
		return _n;
	}
	
	public String getSystemFontType(){
		return _systemFontType;
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
			System.out.println("Invalid color");
			return null;
		}
	}
	
	public void setDetails(String s){
		System.out.println("Detail name: "+s);
		if(s.equals("Lavender") ){
			_red = new Color(255, 195, 206);
			_orange = new Color(255, 207, 121);
			_green = new Color(142, 210, 201);
			_white = new Color(236, 236, 240);
			_displayBg = new Color(255, 255, 255);
			_foreground = new Color(70, 32, 102);
			_displayFontColor = _foreground;
			_inputFontColor = _foreground;
			_background = new Color(230, 230, 250);
			_systemFontType = "Comic Sans MS";
			
			}else if(s.equals("Panda")){
				_red = new Color(200, 87, 80);
				_orange = new Color(213, 162, 83);
				_green = new Color(201, 167, 152);
				_white = new Color(219, 216, 214);
				_displayBg = new Color(104, 86, 66);
				_foreground = new Color(233, 224, 219);
				_displayFontColor = new Color(48, 31, 13);
				_inputFontColor = _displayFontColor;
				_background = new Color(79, 36, 18);
				_systemFontType = "Arial Black";
				
			}else if(s.equals("Sapphire")){
				
				_systemFontType = "Shellshock";
				
			}else if(s.equals("Forest")){
				_red = new Color(169, 125, 93);
				_orange = new Color(179, 165, 128);
				_green = new Color(222, 210, 158);
				_white = new Color(244, 240, 203);
				_displayBg = new Color(204, 204, 153);
				_foreground = new Color(88, 112, 88);
				_displayFontColor = new Color(104, 86, 66);
				_inputFontColor = _displayFontColor;
				_background = new Color(183, 198, 139);
				_systemFontType = "Impact";
				
			}else{
				System.out.println("Invalid theme selection");
				_red = Color.red;
				_orange = Color.orange;
				_green = Color.green;
				_white = Color.lightGray;
				_displayBg = null;
				_foreground = null;
				_displayFontColor = _foreground;
				_inputFontColor = _foreground;
				_background = null;
				_systemFontType = "Impact";
			}
	}
	
}
