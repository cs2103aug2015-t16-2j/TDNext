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
import javax.imageio.ImageIO;
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
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.InputStream;

import javax.swing.SwingConstants;

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

	private final static String help =
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
	private static ThemeAPI lavender = new ThemeAPI("Lavender");
	private static ThemeAPI panda = new ThemeAPI("Panda");
	private static ThemeAPI forest = new ThemeAPI("Forest");
	private static ThemeAPI sapphire = new ThemeAPI("Sapphire");
	private static ThemeAPI original = new ThemeAPI("");


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
	private static JTextField txtStatus;

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
			updateStatus("Last command '" + input + "' was valid and is processed.");
		} catch (Exception e) {
			updateStatus("Last command '" + input + "' was invalid. Pls edit your input.");
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private static void clearInput(JTextField textInput){
		textInput.setText("");
	}

	private static String getParsedInoString(ArrayList<Task> parsedInfo, int i){
		String output = new String();
			output = parsedInfo.get(i).getIndex() + ". " + parsedInfo.get(i).toString();
		return output;
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
			setDefaultScroll();
		}
	}
	
	private static void updateStatus(String status){
		txtStatus.setText(status);
	}

	private static void setDefaultScroll(){
		final JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
		Runnable run1 = new Runnable() {
			public void run() {
				verticalScrollBar.setValue(0);
			}
		};
		SwingUtilities.invokeLater(run1);
	}

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
		if(s.equals("Panda")){
			red = panda.getColor("red");
			orange = panda.getColor("orange");
			green = panda.getColor("green");
			white = panda.getColor("white");
			displayFontColor = panda.getColor("displayfont");
			inputFontColor = panda.getColor("inputfont");
			displayBackground = panda.getColor("displaybg");
			foreground = panda.getColor("foreground");
			background = panda.getColor("background");
			guiLog.log(Level.INFO, "Panda theme is selected now.");

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
			guiLog.log(Level.INFO, "Default theme is selected now.");

		}
	}

	final static ImageIcon helpIcon = new ImageIcon(GUI2.class.getResource("/Help Icon S.png"));
	final static ImageIcon themeIcon = new ImageIcon(GUI2.class.getResource("/theme Icon S.png"));
	
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
		contentPane.setLayout(new MigLayout("", "[377px][6px][88px,grow]", "[][377.00px][44.00px][41.00px]"));
		contentPane.validate();
		
		contentPane.add(txtStatus, "cell 0 0 3 1,growx,aligny center");
		
		contentPane.add(scrollPane, "cell 0 1 3 1,grow");
	}

	private static void setPanelDisplay(){
		scrollPane.setViewportView(panelDisplay);
		panelDisplay.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Task List", TitledBorder.LEADING, TitledBorder.TOP, new Font(systemFont, Font.PLAIN, 16), foreground));
		panelDisplay.setBackground(displayBackground);
		panelDisplay.setForeground(displayFontColor);
		panelDisplay.setLayout(new GridLayout(0, 1, 0, 1));
		panelDisplay.setFont(new Font(systemFont, Font.PLAIN, 16));
	}

	private static void setPanelCmd(){
		panelCmd.setBackground(background);
		panelCmd.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Type in your commands here: ", TitledBorder.LEADING, TitledBorder.TOP, new Font(systemFont, Font.PLAIN, 16), foreground));
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
		btnHelp.setFont(new Font(systemFont, Font.PLAIN, 12));
		contentPane.add(btnHelp, "cell 2 2,alignx center,aligny center");
	}

	private static void setBtnTheme(){
		contentPane.add(btnTheme, "cell 2 3,alignx center,aligny center");
		btnTheme.setBackground(background);
		btnTheme.setForeground(foreground);
		btnTheme.setFont(new Font(systemFont, Font.PLAIN, 12));
	}
	
	private static void setStatusBar(){
		txtStatus.setHorizontalAlignment(SwingConstants.TRAILING);
		txtStatus.setColumns(5);
		txtStatus.setBackground(background);
		txtStatus.setFont(new Font(systemFont, Font.PLAIN, 10));
		txtStatus.setForeground(foreground);
		txtStatus.setEditable(false);
		updateStatus("This is the status bar ......");
	}

	private static void setAll(){
		setContentPane();
		setPanelDisplay();
		setPanelCmd();
		setBtnHelp();
		setBtnTheme();
		setStatusBar();
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
		panelDisplay.removeAll();
		panelDisplay.repaint();
		txtStatus.repaint();
		txtStatus.revalidate();
		addTextArea();
		setTextAreaSize();
		setDefaultScroll();
	}
	
	private static void refreshUI(String name){
		setTheme(name);
		setAll();
		updateStatus("The theme chosen is: " + name + " ......");
		refresh();
	}

	//Keyboard-related
	
	static Action showTheme = new AbstractAction(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			String[] options = {"Lavender", "Panda", "Sapphire", "Forest", "Default"};
			theme = (String) JOptionPane.showInputDialog(null,
					"Choose your theme", "Themes",
					JOptionPane.INFORMATION_MESSAGE, themeIcon,
					options, options[0]);
			guiLog.log(Level.INFO, "Theme button pressed through 'F2'.");
			updateStatus("You pressed 'F2' for THEME ......");
			
			if(theme != null)
				refreshUI(theme);
		}
		};
		

	static Action showMsg = new AbstractAction() {
			/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, help, "HELP", JOptionPane.INFORMATION_MESSAGE, helpIcon);
				guiLog.log(Level.INFO, "Help button pressed through 'F1'.");
				updateStatus("You pressed 'F1' for HELP ......");
			}
		};
		
	

	//End of functions added by Maple

	/**
	 * Launch the application.
	 * @throws TDNextException 
	 */
	public static void main(String[] args) throws TDNextException {

		logic1 = new TDNextLogicAPI();
			parsedInfo = logic1.startProgram();

		EventQueue.invokeLater(new Runnable() {
			public void run() {
					GUI2 frame = new GUI2();
					frame.setVisible(true);
					frame.setResizable(false);

					//Scrolling with Keyboard
					JScrollBar vertical = scrollPane.getVerticalScrollBar();
					JScrollBar horizontal = scrollPane.getHorizontalScrollBar();
					
					KeyStroke up = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true);
					KeyStroke down = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true);
					KeyStroke left = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true);
					KeyStroke right = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true);
					KeyStroke f1 = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0, true);
					KeyStroke f2 = KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0, true);
					
					vertical.setUnitIncrement(vertical.getMaximum()/10);
					horizontal.setUnitIncrement(horizontal.getMaximum()/10);
					
					InputMap imV = vertical.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
					InputMap imH = horizontal.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
					
					imV.put(down, "positiveUnitIncrement");
					imV.put(up, "negativeUnitIncrement");
					imH.put(right, "positiveUnitIncrement");
					imH.put(left, "negativeUnitIncrement");
					
					//Keyboard Help
					InputMap imHelp = btnHelp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
					ActionMap amHelp = btnHelp.getActionMap();
					imHelp.put(f1, "showMsg");
					amHelp.put("showMsg", showMsg);
					
					//Keyboard Theme
					InputMap imTheme = btnTheme.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
					ActionMap amTheme = btnTheme.getActionMap();
					imTheme.put(f2, "showTheme");
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

		btnHelp = new JButton("HELP  (F1)");
		guiLog.log(Level.INFO, "GUI Initialised: 'btnHelp'.");

		btnTheme = new JButton("THEME  (F2)");
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
				guiLog.log(Level.INFO, "Latest command: " + getInput(textInput) + " is passed.");
				addTextArea();
				setTextAreaSize();
			}
		});

		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, help, "HELP", JOptionPane.INFORMATION_MESSAGE, helpIcon);
				guiLog.log(Level.INFO, "Help button pressed.");
				updateStatus("You clicked HELP ......");
			}
		});

		btnTheme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] options = {"Lavender", "Panda", "Sapphire", "Forest", "Default"};
				theme = (String) JOptionPane.showInputDialog(null,
						"Choose your theme", "Themes",
						JOptionPane.INFORMATION_MESSAGE, themeIcon,
						options, options[0]);
				guiLog.log(Level.INFO, "THEME button pressed.");
				updateStatus("You clicked THEME ......");
				if(theme != null)
					refreshUI(theme);
			}
		});

	}
}