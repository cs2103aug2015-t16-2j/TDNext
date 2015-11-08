package tdnext;

import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import javax.swing.JTextField;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.Font;
import javax.swing.border.TitledBorder;

import javax.swing.border.EtchedBorder;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import tdnext.TDNextLogicAPI.ColourType;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import net.miginfocom.swing.MigLayout;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;

public class GUI2 extends JFrame {

	//@@author A0113507R
	private static JPanel contentPane;
	private static JTextField textInput;
	private static JTextArea textArea;
	private static JScrollPane scrollPane;
	private static JButton btnHelp;
	private static JButton btnTheme;
	private static JPanel panelDisplay;
	private static JPanel panelCmd;
	private static JTextField txtStatus;
	private static String theme;
	private static String lastThemeChosen;

	private static ArrayList<Task> parsedInfo;
	private static TDNextLogicAPI logicAPI = new TDNextLogicAPI();
	private static Logger guiLog= Logger.getLogger("GUI");
	private static Date today = new Date();

	private final static String helpMsg =
			"Hi! See below for our list of commands availavle:"
			+"\n\n"
			+ "Create-\n"
			+"To create a task with deadline:\n"
			+"	ADD <task description> BY <deadline> WITH <importance>\n"
			+"To create an event with a set date and/or time, use this command:\n"
			+"	ADD <event description> ON <date, time> WITH <importance> \n"
			+"To create to-do task that has no date or time, use this command:\n"
			+"	ADD <task description> WITH <importance> \n"
			+"\n"
			+"Read-\n"
			+"To read all the tasks in the list, use this command:\n"
			+"	DISPLAY\n"
			+"\n"
			+"Update-\n"
			+"To update a task, use this command:\n"
			+"	CHANGE <number on the list> \n"
			+"\n"
			+"Delete-\n"
			+"To delete a task, use this command:\n"
			+"	DELETE <number on the list>\n"
			+"\n"
			+"Clear\n"
			+"To delete all tasks, use this command:\n"
			+"	CLEAR<ALL>\n"
			+"\n"
			+"Search\n"
			+"To search for a particular task, use this command:\n"
			+"	SEARCH <keyword>\n"
			+"\n"
			+"Sort-\n"
			+"To sort, use this command:\n"
			+"	SORT <importance/deadline/task/event/to-do>\n"
			+"\n"
			+"Exit-\n"
			+"To exit, use this command:\n"
			+"	EXIT";
	
	//Themes
	private static ThemeAPI lavender = new ThemeAPI("Lavender");
	private static ThemeAPI Wood = new ThemeAPI("Wood");
	private static ThemeAPI forest = new ThemeAPI("Forest");
	private static ThemeAPI sapphire = new ThemeAPI("Sapphire");

	//Colors used in GUI display 
	/*
	private static Color red = new Color(255, 195, 206);
	private static Color orange = new Color(255, 207, 121);
	private static Color green = new Color(142, 210, 201);
	private static Color white = new Color(236, 236, 240);
	private static Color displayBackground= new Color(255, 255, 255);
	private static Color foreground = new Color(70, 32, 102);
	private static Color displayFontColor = foreground;
	private static Color inputFontColor = foreground;
	private static Color background = new Color(230, 230, 250);
	private static String systemFont = "Arial";*/
	
	private static String setLastTheme(){
		lastThemeChosen = null;
	try {
		lastThemeChosen = logicAPI.getTheme();
	} catch (TDNextException e) {
		// TODO Auto-generated catch block
		ImageIcon errorIconS = new ImageIcon (GUI2.class.getResource("/error Icon XS.png"));
		JOptionPane.showMessageDialog(null, "Theme is not initialised. Please contact us.",
				"Theme Error!", JOptionPane.INFORMATION_MESSAGE, errorIconS);
		e.printStackTrace();
	}
	return lastThemeChosen;
	}
	
	private static ThemeAPI lastTheme = new ThemeAPI(setLastTheme());
	
	private static Color red = lastTheme.getColor("Red");
	private static Color orange = lastTheme.getColor("Orange");
	private static Color green = lastTheme.getColor("Green");
	private static Color white = lastTheme.getColor("white");
	private static Color displayBackground = lastTheme.getColor("displayBG");
	private static Color foreground = lastTheme.getColor("foreground");
	private static Color displayFontColor = lastTheme.getColor("displayfont");
	private static Color inputFontColor = lastTheme.getColor("inputfont");
	private static Color background = lastTheme.getColor("background");
	private static String systemFont = "Arial";

	//By Maple: Input and display related
	private String getInput(JTextField textInput){
		return textInput.getText();
	}

	private void passInput(String input){
		ArrayList<Task> output = new ArrayList<Task>();
		try {
			output = logicAPI.executeCommand(input);
			parsedInfo = output;
			clearInput(textInput);
			input = trimInput(input);
			updateStatus(setStatusUsingSplit(input));
			guiLog.log(Level.INFO, "Latest command: " + input + " is passed.");
		} catch (Exception e) {
			updateStatus("Oh no! :( Please check your entry again. Refer to 'HELP' if needed!");
			guiLog.log(Level.INFO, "Latest command: " + input + " is invalid.");
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private String setStatusUsingSplit(String s){
	String[] output = splitInput(s);
	
	switch(output[0].toLowerCase()){
	case "add":
		return "Item '" + output[1] + "' is added to your list.";
	case "delete":
		return "Item with index '" + output[1] + "' is deleted from your list.";
	case "undo":
		return "Your previous action is undone.";
	case "done":
		return "Great! You have mark item with index '" + output[1] + "' as done!";
	case "sort":
		return "Your items are sorted!";
	case "search":
		return "You have searched for '" + output[1] + "'. Return to display all using 'sort' or 'undo'.";
	case "clear":
		return "You have cleared everything. Use 'undo' if that was a mistake!";
	default:
		return "Last entry" + output[0] + " " + output[1] + " is processed. What next?";
	}
	}
	
	private String[] splitInput(String input){
		String result[] = new String[2];
		result = input.split(" ", 2);
		return result;
	}
	
	private String trimInput(String input){
		if(input.length() > 45){
			return input.substring(0, 40)+".....";
		}
		return input;
	}

	private static void clearInput(JTextField textInput){
		textInput.setText("");
	}

	private static String getParsedInoString(ArrayList<Task> parsedInfo, int i){
		int index = parsedInfo.get(i).getIndex();
		if(index > 9){
		return " " + index + ".  " + parsedInfo.get(i).toString() + "  ";
		}else if (index > 99){
		return index + "." + parsedInfo.get(i).toString() + "  ";
		}else{
		return "   " + index + ".  " + parsedInfo.get(i).toString() + "  ";
		}
	}

	private static JTextArea createTextAreas(String s, int i){
		textArea = new JTextArea(s);
		setStyle(i);
		return textArea;
	}

	private static void addTextArea(){
		for(int i = parsedInfo.size() - 1; i >= 0; i--){
			String s = new String(getParsedInoString(parsedInfo, i));
			panelDisplay.add(createTextAreas(s, i), 0);
			panelDisplay.revalidate();
		}
		setDefaultScroll();
	}
	
	private static void updateStatus(String status){
		txtStatus.setText(status);
	}
	
	//@@author A0125283J
	private static void setDefaultScroll(){
		final JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
		Runnable runScrollnCrusor = new Runnable() {
			public void run() {
				verticalScrollBar.setValue(0);
				textInput.requestFocusInWindow();
			}
		};
		SwingUtilities.invokeLater(runScrollnCrusor);
	}
	
	//@@author A0113507R
	
	//By Maple: Color related
	static ColourType getColorType(ArrayList<Task> parsedInfo, int i){
		ColourType cT= parsedInfo.get(i).getColour();
		return cT;
	}

	static Color decideColor(ColourType cT){
		Color c = null;
		switch(cT){
		case RED:
			c= red;
			break;
		case WHITE:
			c= white;
			break;
		case GREEN:
			c= green;
			break;
		case YELLOW:
			c= orange;
			break;
		}
		return c;
	}

	//Theme
	static void setTheme(String s){
		
		if(s.equals("Wood")){
			red = Wood.getColor("red");
			orange = Wood.getColor("orange");
			green = Wood.getColor("green");
			white = Wood.getColor("white");
			displayFontColor = Wood.getColor("displayfont");
			inputFontColor = Wood.getColor("inputfont");
			displayBackground = Wood.getColor("displaybg");
			foreground = Wood.getColor("foreground");
			background = Wood.getColor("background");
			guiLog.log(Level.INFO, "Wood theme is selected now.");
			try {
				logicAPI.setTheme(s);
			} catch (TDNextException e) {
				ImageIcon errorIconS = new ImageIcon (GUI2.class.getResource("/error Icon XS.png"));
				JOptionPane.showMessageDialog(null, "Theme is not passed. Please contact us.",
						"Theme Error!", JOptionPane.INFORMATION_MESSAGE, errorIconS);
				System.out.println(s);
				
				e.printStackTrace();
			}
			

		}else if(s.equals("Lavender")){
			red = lavender.getColor("red");
			orange = lavender.getColor("orange");
			green = lavender.getColor("green");
			white = lavender.getColor("white");
			displayFontColor = lavender.getColor("displayfont");
			inputFontColor = lavender.getColor("inputfont");
			displayBackground = lavender.getColor("displaybg");
			foreground = lavender.getColor("foreground");
			background = lavender.getColor("background");
			guiLog.log(Level.INFO, "Lavender theme is selected now.");
			try {
				logicAPI.setTheme(s);
			} catch (TDNextException e) {
				ImageIcon errorIconS = new ImageIcon (GUI2.class.getResource("/error Icon XS.png"));
				JOptionPane.showMessageDialog(null, "Theme is not passed. Please contact us.",
						"Theme Error!", JOptionPane.INFORMATION_MESSAGE, errorIconS);
				System.out.println(s);
				
				e.printStackTrace();
			}
			

		}else if(s.equals("Forest")){
			red = forest.getColor("red");
			orange = forest.getColor("orange");
			green = forest.getColor("green");
			white = forest.getColor("white");
			displayFontColor = forest.getColor("displayfont");
			inputFontColor = forest.getColor("inputfont");
			displayBackground = forest.getColor("displaybg");
			foreground = forest.getColor("foreground");
			background = forest.getColor("background");
			guiLog.log(Level.INFO, "Forest theme is selected now.");
			try {
				logicAPI.setTheme(s);
			} catch (TDNextException e) {
				ImageIcon errorIconS = new ImageIcon (GUI2.class.getResource("/error Icon XS.png"));
				JOptionPane.showMessageDialog(null, "Theme is not passed. Please contact us.",
						"Theme Error!", JOptionPane.INFORMATION_MESSAGE, errorIconS);
				System.out.println(s);
				
				e.printStackTrace();
			}
			

		}else if(s.equals("Sapphire")){
			red = sapphire.getColor("red");
			orange = sapphire.getColor("orange");
			green = sapphire.getColor("green");
			white = sapphire.getColor("white");
			displayFontColor = sapphire.getColor("displayfont");
			inputFontColor = sapphire.getColor("inputfont");
			displayBackground = sapphire.getColor("displaybg");
			foreground = sapphire.getColor("foreground");
			background = sapphire.getColor("background");
			guiLog.log(Level.INFO, "Sapphire theme is selected now.");
			try {
				logicAPI.setTheme(s);
			} catch (TDNextException e) {
				ImageIcon errorIconS = new ImageIcon (GUI2.class.getResource("/error Icon XS.png"));
				JOptionPane.showMessageDialog(null, "Theme is not passed. Please contact us.",
						"Theme Error!", JOptionPane.INFORMATION_MESSAGE, errorIconS);
				System.out.println(s);
				
				e.printStackTrace();
			}
		}else{
		try {
			logicAPI.setTheme(null);
		} catch (TDNextException e) {
			ImageIcon errorIconS = new ImageIcon (GUI2.class.getResource("/error Icon XS.png"));
			JOptionPane.showMessageDialog(null, "Theme is not passed. Please contact us.",
					"Theme Error!", JOptionPane.INFORMATION_MESSAGE, errorIconS);
			System.out.println(s);
			
			e.printStackTrace();
		}
		}
		
	}

	//@@author
	final static ImageIcon helpIcon = new ImageIcon(GUI2.class.getResource("/Help Icon S.png"));
	final static ImageIcon themeIcon = new ImageIcon(GUI2.class.getResource("/theme Icon S.png"));
	
	//@@author A0113507R
	static void setStyle(int i){
		textArea.setBackground(decideColor(getColorType(parsedInfo, i)));
		textArea.setEditable(false);
		textArea.setFont(new Font(systemFont, Font.BOLD, 16));
		textArea.setBorder(new LineBorder(displayBackground));
	}

	static void setStyleExtra(){
		textArea.setEditable(false);
		textArea.setFont(new Font(systemFont, Font.PLAIN, 16));
		textArea.setBorder(new LineBorder(displayBackground));
		textArea.setBackground(displayBackground);
	}

	private static void setContentPane(){
		contentPane.setBackground(background);
		contentPane.setBorder(null);
		contentPane.setLayout(new MigLayout("", "[360px:80%,center][20%,center]", "[80%,center][center][10%,center][10%,center]"));
		
		contentPane.validate();
		
		contentPane.add(txtStatus, "cell 0 1 2 1,growx,aligny center");
		contentPane.add(scrollPane, "cell 0 0 2 1,grow");
		
	}

	private static void setPanelDisplay(){
		scrollPane.setViewportView(panelDisplay);
		panelDisplay.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Task List", TitledBorder.LEADING, TitledBorder.TOP, new Font(systemFont, Font.PLAIN, 18), foreground));
		panelDisplay.setBackground(displayBackground);
		panelDisplay.setForeground(displayFontColor);
		panelDisplay.setLayout(new GridLayout(0, 1, 0, 0));
		panelDisplay.setFont(new Font(systemFont, Font.PLAIN, 16));
	}

	private static void setPanelCmd(){
		panelCmd.setBackground(background);
		panelCmd.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Type in what to do next: ", TitledBorder.LEADING, TitledBorder.TOP, new Font(systemFont, Font.PLAIN, 17), foreground));
		contentPane.add(panelCmd, "cell 0 2 1 2,growx,aligny center");
		panelCmd.setLayout(new BorderLayout(0, 0));
		panelCmd.add(textInput);
		textInput.setFont(new Font(systemFont, Font.PLAIN, 16));
		textInput.setForeground(inputFontColor);
		textInput.setColumns(10);
	}

	private static void setBtnHelp(){
		btnHelp.setBackground(background);
		btnHelp.setForeground(foreground);
		btnHelp.setFont(new Font(systemFont, Font.BOLD, 12));
		contentPane.add(btnHelp, "cell 1 2,growx,aligny bottom");
	}

	private static void setBtnTheme(){
		contentPane.add(btnTheme, "cell 1 3,growx,aligny bottom");
		btnTheme.setBackground(background);
		btnTheme.setForeground(foreground);
		btnTheme.setFont(new Font(systemFont, Font.BOLD, 12));
	}
	
	@SuppressWarnings("deprecation")
	private static void setStatusBar(){
		txtStatus.setBackground(background);
		txtStatus.setFont(new Font(systemFont, Font.BOLD, 10));
		txtStatus.setForeground(foreground);
		txtStatus.setEditable(false);
		setWelcomeStatus(today.getDay());
	}
	
	private static void setWelcomeStatus(int i){
		switch(i){
		case 1:
		updateStatus("Fresh Monday! Let's start new schedule using 'add <task> <on/by> <date>' , shall we?");
		break;
		case 2:
		updateStatus("It's Tuesday! Wrong item description? Use 'Edit <index> <new task>' to fix it!");
		break;
		case 3:
		updateStatus("It's Wednesday! You can use 'undo' to undo the last action! Correct mistaks faster!");
		break;
		case 4:
		updateStatus("It's Thursday! Start deleting unwanted task using 'delete <index>' before things get messy!");
		break;
		case 5:
		updateStatus("Wow! It's Friday! Remember to archive your completed items using 'done <index>' !");
		break;
		case 6:
		updateStatus("Sweet Saturday! Can't find the item you want? Try 'search <keyword>' now!");
		break;
		default:
		updateStatus("It's Sunday! Try 'sort name' or 'sort deadline' ! Scanning through is so easy!");
	}
	}

	private static void setAll(){
		setContentPane();
		setPanelDisplay();
		setPanelCmd();
		setStatusBar();
		setBtnHelp();
		setBtnTheme();
	}

	private static void setTextAreaSize(){
		if(parsedInfo.size() < 14){
			for(int j=0; j<(14-parsedInfo.size()); j++){
				textArea = new JTextArea("");
				setStyleExtra();
				panelDisplay.add(textArea);
				panelDisplay.revalidate();
			}
			}
	}

	private static void refresh(){
		contentPane.repaint();
		contentPane.revalidate();
		
		textInput.repaint();
		textInput.revalidate();
		
		scrollPane.repaint();
		scrollPane.revalidate();
		
		btnTheme.repaint();
		btnTheme.revalidate();
		
		btnHelp.repaint();
		btnHelp.revalidate();
		
		panelCmd.repaint();
		panelCmd.revalidate();
		
		txtStatus.repaint();
		txtStatus.revalidate();
		
		panelDisplay.removeAll();
		panelDisplay.repaint();
		
		addTextArea();
		setTextAreaSize();
		setDefaultScroll();
	}
	
	private static void refreshUI(String name){
		setTheme(name);
		setAll();
		updateStatus("Wow! You have chosen " + name + " as your theme! Isn't it wonderful?");
		refresh();
	}

	//Keyboard-related
	
	private static Action showTheme = new AbstractAction(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			String[] options = {"Lavender", "Wood", "Sapphire", "Forest"};
			theme = (String) JOptionPane.showInputDialog(null,
					"Choose your theme", "Themes",
					JOptionPane.INFORMATION_MESSAGE, themeIcon,
					options, options[0]);
			guiLog.log(Level.INFO, "Theme button pressed through 'F2'.");
			updateStatus("I see you pressed 'F2' for THEME! Are you satisfy with the current skintone?");
			
			if(theme != null)
				refreshUI(theme);
		}
		};
		

	private static Action showMsg = new AbstractAction() {
		private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, helpMsg, "HELP", JOptionPane.INFORMATION_MESSAGE, helpIcon);
				guiLog.log(Level.INFO, "Help button pressed through 'F1'.");
				
				updateStatus("I see you pressed 'F1' for HELP. Excellent choice when you can't remember the commands!");
			}
		};
		
	

	//End of functions added by Maple

	/**
	 * Launch the application.
	 * @throws TDNextException 
	 */
	public static void main(String[] args) {
			try {
				parsedInfo = logicAPI.startProgram();
				
			} catch (TDNextException e) {
				
				ImageIcon errorIconS = new ImageIcon (GUI2.class.getResource("/error Icon XS.png"));
				JOptionPane.showMessageDialog(null, "Please restart program. If the problem persists,"
						+ "check project manual for trouble-shooting or contact us.",
						"Initialisation Error!", JOptionPane.INFORMATION_MESSAGE, errorIconS);
				e.printStackTrace();
			}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
					GUI2 frame = new GUI2();
					
					frame.setVisible(true);
					frame.setResizable(false);

					//Scrolling with Keyboard
					JScrollBar vertical = scrollPane.getVerticalScrollBar();
					JScrollBar horizontal = scrollPane.getHorizontalScrollBar();
					
					KeyStroke key_up = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true);
					KeyStroke key_down = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true);
					KeyStroke key_left = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true);
					KeyStroke key_right = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true);
					KeyStroke key_f1 = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0, true);
					KeyStroke key_f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0, true);
					
					vertical.setUnitIncrement(vertical.getMaximum()/10);
					horizontal.setUnitIncrement(horizontal.getMaximum()/10);
					
					InputMap imV = vertical.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
					InputMap imH = horizontal.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
					
					imV.put(key_down, "positiveUnitIncrement");
					imV.put(key_up, "negativeUnitIncrement");
					imH.put(key_right, "positiveUnitIncrement");
					imH.put(key_left, "negativeUnitIncrement");
					
					//Keyboard Help
					InputMap imHelp = btnHelp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
					ActionMap amHelp = btnHelp.getActionMap();
					imHelp.put(key_f1, "showMsg");
					amHelp.put("showMsg", showMsg);
					
					//Keyboard Theme
					InputMap imTheme = btnTheme.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
					ActionMap amTheme = btnTheme.getActionMap();
					imTheme.put(key_f2, "showTheme");
					amTheme.put("showTheme", showTheme);
					
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI2() {
		setTitle("Welcome to TDNext");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPane = new JPanel();
		guiLog.log(Level.INFO, "GUI Initialised: 'contentPane'.");

		scrollPane = new JScrollPane();
		guiLog.log(Level.INFO, "GUI Initialised: 'scrollPane'.");

		panelDisplay = new JPanel();
		guiLog.log(Level.INFO, "GUI Initialised: 'panelDisplay'.");

		panelCmd = new JPanel();
		guiLog.log(Level.INFO, "GUI Initialised: 'panelCmd'.");

		textInput = new JTextField();
		guiLog.log(Level.INFO, "GUI Initialised: 'textInput'.");

		ImageIcon helpIconXS = new ImageIcon (GUI2.class.getResource("/Help Icon XS.png"));
		btnHelp = new JButton(" HELP  (F1)", helpIconXS);
		guiLog.log(Level.INFO, "GUI Initialised: 'btnHelp'.");
		
		ImageIcon themeIconXS = new ImageIcon (GUI2.class.getResource("/theme Icon XS.png"));
		btnTheme = new JButton("THEME (F2)", themeIconXS);
		guiLog.log(Level.INFO, "GUI Initialised: 'btnTheme'.");
		
		txtStatus = new JTextField();
		guiLog.log(Level.INFO, "GUI Initialised: 'txtStatus'.");

		setBounds(100, 100, 500, 500);
		setContentPane(contentPane);
		
		setAll();

		addTextArea();
		setTextAreaSize();

		textInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelDisplay.removeAll();
				panelDisplay.repaint();
				passInput(getInput(textInput));
				addTextArea();
				setTextAreaSize();
			}
		});

		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, helpMsg, "HELP", JOptionPane.INFORMATION_MESSAGE, helpIcon);
				textInput.requestFocusInWindow();
				guiLog.log(Level.INFO, "Help button pressed.");
				updateStatus("I see you clicked the 'HELP' button. Do you know you can press 'F1' to access 'HELP too?");
			}
		});

		btnTheme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] options = {"Lavender", "Wood", "Sapphire", "Forest"};
				theme = (String) JOptionPane.showInputDialog(null,
						"Choose your theme", "Themes",
						JOptionPane.INFORMATION_MESSAGE, themeIcon,
						options, options[0]);
				guiLog.log(Level.INFO, "THEME button pressed.");
				updateStatus("You clicked 'THEME' button! Do you know you can press 'F2' to access 'THEME' too?");
				if(theme != null)
					refreshUI(theme);
			}
		});

	}
}