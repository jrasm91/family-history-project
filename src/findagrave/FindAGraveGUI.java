
package findagrave;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class FindAGraveGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private final int FONTSIZE = 20, TFSIZE = 15, WIDTH = 400, HEIGHT = 500;

	private final String[] states = {"Search All States",
			"Alaska","Alabama","Arkansas","Arizona","California","Colorado","Connecticut","District Of Columbia",
			"Delaware","Florida","Georgia","Hawaii","Iowa", "Idaho","Illinois","Indiana","Kansas","Ketuncky",
			"Louisiana","Massachusetts","Maryland","Maine","Michigan","Minnesota","Missouri","Mississippi","Montana","North Carolina",
			"North Dakota","Nebraska","New Hampshire","New Jersey","New Mexico","Nevada","New York", "Ohio","Oklahoma","Oregon",
			"Pennsylvania","Puerto Rico","Rhode Island","South Carolina","South Dakota","Tennessee","Texas","Utah","Virginia","Vermont","Washington",
			"Wisconsin","West Virginia","Wyoming"};
	private final String[] countries = {"Search All Countries", "The United States", 
			"Canada", "England"};	

	private JTextField firstNameTF, middleNameTF, lastNameTF, mmidTF, glimitTF, tlimitTF;
	private JRadioButton ancestorRB, descendantRB, normalRB;
	private JComboBox<String> statesCB, countriesCB;
	private JCheckBox maidenCB;
	private JButton goB1, goB2;
	private JTabbedPane tabbedPane;
	private JTextArea textArea1, textArea2;

	public FindAGraveGUI(){
		super("Find A Grave: Help Utility");
		initializeGUI();
	}

	private void initializeGUI(){
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("By Search", null, initializePanel1(), 
				"Searches by name, country, state, etc");	
		tabbedPane.addTab("By Memorial Number", null, initializePanel2(),
				"Searches for Ancestors/Descendants by ID");
		add(tabbedPane, BorderLayout.CENTER);
		setResizable(false);
		setVisible(true);
	}

	private JPanel initializePanel1(){
		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel1.add(createJLabel("First Name:    "));
		firstNameTF = new JTextField(TFSIZE); setSize(firstNameTF);	panel1.add(firstNameTF);
		panel1.add(createJLabel("Middle Name: "));
		middleNameTF = new JTextField(TFSIZE); setSize(middleNameTF); panel1.add(middleNameTF);
		panel1.add(createJLabel("Last Name:    "));
		lastNameTF = new JTextField(TFSIZE); setSize(lastNameTF);	panel1.add(lastNameTF);
		panel1.add(createJLabel("Country:        "));
		countriesCB = new JComboBox<String>(countries); setSize(countriesCB); panel1.add(countriesCB);
		panel1.add(createJLabel("State: (USA)  "));  
		statesCB = new JComboBox<String>(states); setSize(statesCB); panel1.add(statesCB);
		panel1.add(createJLabel(" Maiden Name"));
		maidenCB = new JCheckBox(); setSize(maidenCB); panel1.add(maidenCB);
		panel1.add(createJLabel("          "));
		goB1 = new JButton("GO!"); goB1.addActionListener(new GOListener()); setSize(goB1); panel1.add(goB1);
		textArea1 = new JTextArea(10, 29); setSize(textArea1, FONTSIZE - 5);
		JScrollPane scrollPane = new JScrollPane(textArea1); setSize(scrollPane); panel1.add(scrollPane);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.black, 3));
		return panel1;
	}

	private JPanel initializePanel2(){
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel2.add(createJLabel("Enter Memorial ID: "));
		mmidTF = new JTextField(TFSIZE - 5); setSize(mmidTF);	panel2.add(mmidTF);
		ancestorRB = new JRadioButton("Ancestors");
		setSize(ancestorRB, FONTSIZE-1);
		descendantRB = new JRadioButton("Descendants");
		setSize(descendantRB, FONTSIZE-1);
		normalRB = new JRadioButton("Normal");
		setSize(normalRB, FONTSIZE-1);
		ButtonGroup optionBG = new ButtonGroup();
		optionBG.add(ancestorRB);
		optionBG.add(descendantRB);
		optionBG.add(normalRB);
		panel2.add(ancestorRB); panel2.add(descendantRB); panel2.add(normalRB);
		panel2.add(createJLabel("G-Limit"));
		glimitTF = new JTextField(4); setSize(glimitTF); panel2.add(glimitTF);
		panel2.add(createJLabel("T-Limit"));
		tlimitTF = new JTextField(4); setSize(tlimitTF);	panel2.add(tlimitTF);
		goB2 = new JButton("GO!"); goB2.addActionListener(new GOListener()); setSize(goB2); panel2.add(goB2);
		textArea2 = new JTextArea(15, 29); setSize(textArea2, FONTSIZE - 5);
		JScrollPane scrollPane = new JScrollPane(textArea2); setSize(scrollPane); panel2.add(scrollPane);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.black, 3));
		return panel2;
	}

	private JLabel createJLabel(String text){
		JLabel c = new JLabel(text);
		c.setFont(new Font(c.getFont().getName(), c.getFont().getStyle(), FONTSIZE));
		return c;
	}
	private void setSize(JComponent c){
		c.setFont(new Font(c.getFont().getName(), c.getFont().getStyle(), FONTSIZE));
	}

	private void setSize(JComponent c, int fontSize){
		c.setFont(new Font(c.getFont().getName(), c.getFont().getStyle(), fontSize));
	}

	private class GOListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("Button Pressed: " + (tabbedPane.getSelectedIndex() + 1));
			switch(tabbedPane.getSelectedIndex() + 1){
			case 1: {
				String firstName = firstNameTF.getText();
				String middleName = middleNameTF.getText();
				String lastName = lastNameTF.getText();
				String country = (String) countriesCB.getSelectedItem();
				int valueC = 0;
				String state = (String) statesCB.getSelectedItem();
				int valueS = statesCB.getSelectedIndex() + 1;
				boolean maiden = maidenCB.isSelected();
				switch(country){
				case "The United States":	valueC = 4; break;
				case "Canada": 				valueC = 10; break;
				case "England":				valueC = 5; break;
				default :					valueC = 0; break;
				}
				System.out.println("First Name = " + firstName);
				System.out.println("Middle Name = " + middleName);
				System.out.println("Last Name = " + lastName);
				System.out.println("Country = " + country + " " + valueC);
				System.out.println("State = " + state + " " + valueS);
				System.out.println("Maiden Name = " + maiden);
			} break;
			case 2: {
				int limitG = -1, limitT = -1, mid = -1;
				String form = "default";
				try{
					mid = Integer.parseInt(mmidTF.getText());
					if(!glimitTF.getText().isEmpty())
						limitG = Integer.parseInt(glimitTF.getText());
					if(!tlimitTF.getText().isEmpty())
						limitT = Integer.parseInt(tlimitTF.getText());
				} catch(NumberFormatException e1){
					textArea2.setText(e1.getLocalizedMessage());
					update(getGraphics());
					return;
				}

				if(ancestorRB.isSelected()){
					form = "ancestors";
				}
				else if(descendantRB.isSelected()){
					form = "descendants";
				}
				else{
					form = "normal";
				}
				System.out.println("mid = " + mid);
				System.out.println("form = " + form);
				System.out.println("limitG = " + limitG);
				System.out.println("limitT = " + limitT);
			} break;
			}
		}
	}
}
