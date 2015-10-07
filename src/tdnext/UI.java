package tdnext;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JDesktopPane;

import java.awt.Color;
import java.awt.SystemColor;
import java.util.ArrayList;

import javax.swing.JInternalFrame;
import javax.swing.border.TitledBorder;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

import java.awt.Font;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import java.awt.GridLayout;

import net.miginfocom.swing.MigLayout;

import javax.swing.SwingConstants;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingWorker;

public class UI extends JFrame {

	private JPanel contentPane;
	private static JTextField textInput;
	private static JTextArea textArea;
	private static ArrayList<Task> parsedInfo;	
	
	//Functions added by Maple
	private static String getInput(JTextField textInput){
		return textInput.getText();
	}
	
	private static void passInput(String input){
		ArrayList<Task> output = new ArrayList<Task>();
		TDNextLogicAPI logic1 = new TDNextLogicAPI();
		output = logic1.executeCommand(input);
		parsedInfo = output;
	}
	
	private static void clearInput(JTextField textInput){
		textInput.setText(" ");
	}
	
	private static String getDisplay(ArrayList<Task> parsedInfo){
		String output = new String();
		for (int i = 0; i < parsedInfo.size(); i++ ){
			output = output + parsedInfo.get(i).toString();
		}
		
		return output;
	}
	
	/*private static ColourType getColourType(ArrayList<Task> parsedInfo){
		return parsedInfo.getColour();
	}
	
	private static void setColourIcon(ColourType colour){
	//
	}*/
	
/*	private static void updateArea(final JTextArea textArea){
		while(true){
			if(refresh){
		textArea.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent event) {
				textArea.setText(getDisplay(parsedInfo));
			}
			public void ancestorMoved(AncestorEvent event) {
			}
			public void ancestorRemoved(AncestorEvent event) {
			}
		});
		}
			refresh=false;
		}
	}
*/	
	
	//End of functions added by Maple

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		TDNextLogicAPI logic1 = new TDNextLogicAPI();
		parsedInfo = logic1.startProgram();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI frame = new UI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 555, 370);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.window);
		contentPane.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "TDnext", TitledBorder.CENTER, TitledBorder.ABOVE_TOP, null, new Color(72, 61, 139)));
		setContentPane(contentPane);;
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBounds(22, 36, 511, 295);
		panel.setBackground(new Color(230, 230, 250));
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnHelp = new JButton("HELP");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = 
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
				JOptionPane.showMessageDialog(null, message);	
			}
		});
		btnHelp.setBounds(410, 29, 97, 29);
		btnHelp.setFont(new Font("Chalkboard SE", Font.PLAIN, 13));
		panel.add(btnHelp);
		
		textInput = new JTextField();
		textInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			passInput(getInput(textInput));
			clearInput(textInput);
		}
	});
		textInput.setBounds(4, 265, 404, 28);
		textInput.setFont(new Font("Bookman Old Style", Font.PLAIN, 13));
		panel.add(textInput);
		textInput.setColumns(10);
	
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 402, 232);
		panel.add(scrollPane);
		
		
		
		textArea = new JTextArea();
		textArea.addAncestorListener(new AncestorListener() {
			public void ancestorAdded(AncestorEvent event) {
				textArea.setText(getDisplay(parsedInfo));
			}
			public void ancestorMoved(AncestorEvent event) {
			}
			public void ancestorRemoved(AncestorEvent event) {
			}
		});
		textArea.setEditable(false);
		scrollPane.setViewportView(textArea);
		textArea.setFont(new Font("Bookman Old Style", Font.PLAIN, 15));
		
		JLabel lblTypeYourCommand = new JLabel("Type your command here:");
		lblTypeYourCommand.setHorizontalAlignment(SwingConstants.CENTER);
		lblTypeYourCommand.setBounds(4, 239, 187, 29);
		lblTypeYourCommand.setForeground(new Color(0, 0, 0));
		lblTypeYourCommand.setFont(new Font("Chalkboard SE", Font.PLAIN, 15));
		panel.add(lblTypeYourCommand);
		
		JButton btnEnter = new JButton("ENTER");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showMessageDialog(null, "Under Construction!");
				passInput(getInput(textInput));
				clearInput(textInput);
			}
		});
		btnEnter.setBounds(407, 266, 100, 29);
		btnEnter.setFont(new Font("Chalkboard SE", Font.PLAIN, 13));
		btnEnter.setForeground(new Color(0, 0, 0));
		panel.add(btnEnter);
		
		JButton btnDefault = new JButton("DEFAULT");
		btnDefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Under Construction!");
				passInput("SORT DEFAULT");
			}
			
		});
		btnDefault.setBounds(410, 207, 97, 29);
		btnDefault.setFont(new Font("Chalkboard SE", Font.PLAIN, 13));
		btnDefault.setForeground(new Color(0, 0, 153));
		panel.add(btnDefault);
		
		JButton btnDeadline = new JButton("DEADLINE");
		btnDeadline.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Under Construction!");
				passInput("SORT BY DEADLINE");
			}
		});
		btnDeadline.setBounds(410, 175, 97, 29);
		btnDeadline.setFont(new Font("Chalkboard SE", Font.PLAIN, 13));
		btnDeadline.setForeground(new Color(255, 153, 0));
		panel.add(btnDeadline);
		
		JButton btnName = new JButton("NAME");
		btnName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Under Construction!");
				passInput("SORT BY NAME");
			}
		});
		btnName.setBounds(410, 143, 97, 29);
		btnName.setFont(new Font("Chalkboard SE", Font.PLAIN, 13));
		btnName.setForeground(new Color(204, 0, 0));
		panel.add(btnName);
		
		JButton btnSearch = new JButton("SEARCH");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String keyword;
			keyword = JOptionPane.showInputDialog("Enter keyword:");
			passInput("SEARCH "+ keyword);
			}
		});
		
		btnSearch.setBounds(410, 60, 97, 29);
		btnSearch.setFont(new Font("Chalkboard SE", Font.PLAIN, 13));
		panel.add(btnSearch);
		
		JLabel lblSortBy = new JLabel("Sort by:");
		lblSortBy.setBounds(420, 125, 61, 16);
		panel.add(lblSortBy);
	}
}
