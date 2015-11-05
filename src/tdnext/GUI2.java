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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Color;
import javax.swing.border.LineBorder;
import java.awt.Font;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
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

import java.awt.Robot;
import java.awt.AWTException;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class GUI2 extends JFrame {

	private static JPanel contentPane;
	private static JTextField textInput;
	private static JTextArea textArea;
	private static JScrollPane scrollPane;
	private static JButton btnHelp;
	private static JButton btnTheme;
	private static JPanel panelDisplay;
	private static JPanel panelCmd;
	private static String theme;

	private static ArrayList<Task> parsedInfo;

	private static TDNextLogicAPI logic1;
	private static Logger guiLog= Logger.getLogger("GUI");

	private final String help =
			"This is the help section. Please see below for more information:"
			+"\n\n"
			+ "Create-\n"
			+"To create a task with deadline, use this command:\n"
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
	private ThemeAPI lavender = new ThemeAPI("Lavender");
	private ThemeAPI panda = new ThemeAPI("Panda");
	private ThemeAPI forest = new ThemeAPI("Forest");
	private ThemeAPI sapphire = new ThemeAPI("Sapphire");
	private ThemeAPI original = new ThemeAPI("");


	//Colors used in GUI display
	private static Color red = Color.red;
	private static Color orange = Color.orange ;
	private static Color green = Color.green;
	private static Color white = Color.lightGray;
	private static Color displayBackground= null;
	private static Color displayFontColor = null;
	private static Color inputFontColor = null;
	private static Color foreground = null;
	private static Color background = null;
	private static String systemFont = "Aria";

	//By Maple: Input and display related
	private String getInput(JTextField textInput){
		return textInput.getText();
	}

	private void passInput(String input){
		ArrayList<Task> output = new ArrayList<Task>();
		try {
			output = logic1.executeCommand(input);
			parsedInfo = output;
			clearInput(textInput);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private static void clearInput(JTextField textInput){
		textInput.setText("");
	}

	private String getParsedInoString(ArrayList<Task> parsedInfo, int i){
		String output = new String();
			int j = i+1;
			output = j + ". " + parsedInfo.get(i).toString();
	//		System.out.println("getDisplay: " + output);
		return output;
	}

	private JTextArea createTextAreas(String s, int i){
		textArea = new JTextArea(s);
	//	System.out.println("CreateLines: " + s);
		setStyle(i);
		return textArea;
	}

	private void addTextArea(){
		for(int i = parsedInfo.size() - 1; i >= 0; i--){
			String s = new String(getParsedInoString(parsedInfo, i));
			System.out.println(s);
			panelDisplay.add(createTextAreas(s, i), 0);
			panelDisplay.revalidate();

			JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
			Runnable run1 = new Runnable() {
				public void run() {
					verticalScrollBar.setValue(0);
				}
			};
			SwingUtilities.invokeLater(run1);
		}

	}
	/*
	JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
	JScrollBar horizontalBar = scrollPane.getHorizontalScrollBar();
	InputMap v = verticalBar.getInputMap();
	InputMap h = horizontalBar.getInputMap();
	KeyStroke up = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true);
	KeyStroke down = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true);
	KeyStroke left = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true);
	KeyStroke right = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true);
	*/

	//By Maple: Color related
	static ColourType getColorType(ArrayList<Task> parsedInfo, int i){
		ColourType cT= parsedInfo.get(i).getColour();
	//	System.out.println("color is:" + cT);
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
	void setTheme(String s){
		if(s.equals("Panda")){
			red = panda.getColor("red");
			System.out.println(red);
			orange = panda.getColor("orange");
			green = panda.getColor("green");
			white = panda.getColor("white");
			displayFontColor = panda.getColor("displayfont");
			inputFontColor = panda.getColor("inputfont");
			displayBackground = panda.getColor("displaybg");
			foreground = panda.getColor("foreground");
			background = panda.getColor("background");

			System.out.println("set Panda Theme");

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

			System.out.println("system font set to: " + systemFont);
			System.out.println("set Lavender Theme");

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

			System.out.println("system font set to: " + systemFont);
			System.out.println("set Forest Theme");

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

			System.out.println("system font set to: " + systemFont);
			System.out.println("set Sapphire Theme");

		}else{
			red = original.getColor("red");
			orange = original.getColor("orange");
			green = original.getColor("green");
			white = original.getColor("white");
			displayFontColor = original.getColor("displayfont");
			inputFontColor = original.getColor("inputfont");
			displayBackground = original.getColor("displaybg");
			foreground = original.getColor("foreground");
			background = original.getColor("background");

			System.out.println("system font set to: " + systemFont);
			System.out.println("set Sapphire Theme");

		}
	}

	//Interface-related
	static void setStyle(int i){
	//	System.out.println("Style set");
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

	private void setContentPane(){
		setBounds(100, 100, 500, 500);
		contentPane.setBackground(background);
		contentPane.setBorder(null);
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[377px][6px][88px]", "[364px][34px][30px]"));
		contentPane.validate();
		contentPane.add(scrollPane, "cell 0 0 3 1,grow");
		System.out.println(red.toString());
	}

	private void setPanelDisplay(){
		scrollPane.setViewportView(panelDisplay);
		panelDisplay.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Task List", TitledBorder.LEADING, TitledBorder.TOP, new Font(systemFont, Font.PLAIN, 16), foreground));
		panelDisplay.setBackground(displayBackground);
		panelDisplay.setForeground(displayFontColor);
		panelDisplay.setLayout(new GridLayout(0, 1, 0, 1));
		panelDisplay.setFont(new Font(systemFont, Font.PLAIN, 16));
	}

	private void setPanelCmd(){
		panelCmd.setBackground(background);
		panelCmd.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Type in your commands here: ", TitledBorder.LEADING, TitledBorder.TOP, new Font(systemFont, Font.PLAIN, 16), foreground));
		contentPane.add(panelCmd, "cell 0 1 1 2,growx,aligny top");
		panelCmd.setLayout(new BorderLayout(0, 0));
		panelCmd.add(textInput);
		textInput.setFont(new Font(systemFont, Font.PLAIN, 16));
		textInput.setForeground(inputFontColor);
		textInput.setColumns(10);
	}

	private void setBtnHelp(){
		btnHelp.setBackground(background);
		btnHelp.setForeground(foreground);
		btnHelp.setFont(new Font(systemFont, Font.PLAIN, 14));
		contentPane.add(btnHelp, "cell 2 1,growx,aligny center");
	}

	private void setBtnTheme(){
		btnTheme.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		contentPane.add(btnTheme, "cell 2 2,growx,aligny center");
		btnTheme.setBackground(background);
		btnTheme.setForeground(foreground);
		btnTheme.setFont(new Font(systemFont, Font.PLAIN, 14));
	}

	private void setAll(){
		setContentPane();
		setPanelDisplay();
		setPanelCmd();
		setBtnHelp();
		setBtnTheme();
	}

	private void setTextAreaSize(){
		if(parsedInfo.size() < 14){
			for(int j=0; j<(14-parsedInfo.size()); j++){
				textArea = new JTextArea("");
				setStyleExtra();
				panelDisplay.add(textArea);
				panelDisplay.revalidate();
			}
			}
	}

	private void refresh(){
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
		panelDisplay.removeAll();
		panelDisplay.repaint();
		for(int i =0; i<parsedInfo.size(); i++){
			String s = new String(getParsedInoString(parsedInfo, i));
			panelDisplay.add(createTextAreas(s, i), -1);
			panelDisplay.revalidate();
		}
		setTextAreaSize();
	}


	//End of functions added by Maple

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

		logic1 = new TDNextLogicAPI();
		try {
			parsedInfo = logic1.startProgram();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1);
		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI2 frame = new GUI2();
					frame.setVisible(true);
					frame.setResizable(false);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Error! Please restart program.");
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI2() {
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

		btnHelp = new JButton("HELP");
		guiLog.log(Level.INFO, "GUI Initialised: 'btnHelp'.");

		btnTheme = new JButton("THEME");
		guiLog.log(Level.INFO, "GUI Initialised: 'btnTheme'.");

		setAll();

		addTextArea();
		setTextAreaSize();

		textInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panelDisplay.removeAll();
				panelDisplay.repaint();
				passInput(getInput(textInput));
				guiLog.log(Level.INFO, "Last input displayed.");
				addTextArea();
				setTextAreaSize();

				Robot rob;
				try {
					rob = new Robot();
					rob.mouseWheel(500);
				} catch (AWTException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, help);
				guiLog.log(Level.INFO, "Help button pressed.");
			}
		});

		btnTheme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] options = {"Lavender", "Panda", "Sapphire", "Forest", "Default"};
				theme = (String) JOptionPane.showInputDialog(null,
						"Choose your theme", "Input",
						JOptionPane.INFORMATION_MESSAGE, null,
						options, options[0]);
				System.out.println("Theme chosen is " + theme);
				if(theme != null){
				setTheme(theme);
				setAll();
				refresh();
				}
			}
		});

	}
}
