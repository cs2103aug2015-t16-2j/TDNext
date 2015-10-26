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
		if(s == "Lavender"){
			_red = new Color(255, 195, 206);
			_orange = new Color(255, 207, 121);
			_green = new Color(142, 210, 201);
			_white = new Color(236, 236, 240);
			_displayBg = new Color(255, 255, 255);
			_foreground = new Color(70, 32, 102);
			_displayFontColor = _foreground;
			_inputFontColor = _foreground;
			_background = new Color(230, 230, 250);
			_systemFontType = new String("Comic Sans MS");
			}else if(s == "Panda"){
				_red = new Color(184, 87, 80);
				_orange = new Color(213, 162, 83);
				_green = new Color(201, 167, 152);
				_white = new Color(249, 246, 244);
				_displayBg = new Color(255, 255, 255);
				_foreground = new Color(233, 224, 219);
				_displayFontColor = new Color(48, 31, 13);
				_inputFontColor = _displayFontColor;
				_background = new Color(79, 36, 18);
				_systemFontType = new String("Arial");
			}else if(s == "Sapphire"){
				
			}else if(s == "Forest"){
				
			}else{
				System.out.println("Invalid theme selection");
				_red = new Color(255, 195, 206);
				_orange = new Color(255, 207, 121);
				_green = new Color(142, 210, 201);
				_white = new Color(236, 236, 240);
				_displayBg = new Color(255, 255, 255);
				_foreground = new Color(70, 32, 102);
				_displayFontColor = _foreground;
				_inputFontColor = _foreground;
				_background = new Color(230, 230, 250);
				_systemFontType = new String("Comic Sans MS");
			}
	}
	
}