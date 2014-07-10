package familysearch;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class FamilySearchGUI extends JFrame{

	private static final long serialVersionUID = 1936976022749340275L;
	private static final int WIDTH = 380;
	private static final int HEIGHT = 415;
	private final int mySize = 20;	
	private JLabel urlL, lineL, fileL;
	private JTextField urlTF, fileTF;
	private JButton b1;
	private JTextArea textArea;
	private JScrollPane scrollPane;

	public static void main(String[] args) throws IOException {
		new FamilySearchGUI();
	}

	public FamilySearchGUI(){

		//GUI basic setup stuff
		setResizable(false);
		setTitle("Family Search: Help Utility");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//url stuff
		urlL = new JLabel("Search URL: ");
		setFont(urlL, mySize);
		urlTF = new JTextField(10);
		setFont(urlTF, mySize);
		//file stuff
		fileL = new JLabel("    File Name: ");
		setFont(fileL, mySize);
		fileTF = new JTextField(10);
		setFont(fileTF, mySize);
		//GO!!! Button stuff
		b1 = new JButton("GO!");
		b1.addActionListener(new GOListener());
		setFont(b1, mySize);
		//Line Divide stuff
		lineL = new JLabel("----------------------------------------------------------------------------------------");
		//Console stuff
		textArea = new JTextArea(9, 22);
		setFont(textArea, mySize);
		scrollPane = new JScrollPane(textArea);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.black, 3));
		textArea.setEditable(false);

		//add stuff
		Container pane = getContentPane();
		FlowLayout f = new FlowLayout();
		f.setAlignment(FlowLayout.CENTER);
		pane.setLayout(f);
		pane.add(urlL);
		pane.add(urlTF);
		pane.add(fileL);
		pane.add(fileTF);
		pane.add(b1);
		pane.add(lineL);
		pane.add(scrollPane);
		setVisible(true);
	}
	private void setFont(JComponent c, int fontSize){
		c.setFont(new Font(c.getFont().getName(), c.getFont().getStyle(), fontSize));
	}

	private class GOListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			FamilySearch fam = new FamilySearch();
			long start_time = System.currentTimeMillis();
			textArea.setText("File: " + "'" + fileTF.getText() + ".txt'");
			textArea.setText(textArea.getText()+ "\nURL: " + urlTF.getText());
			update(getGraphics());
			textArea.setText(textArea.getText()+ "\ntesting file name...");
			update(getGraphics());
			fam.writeFile(null, fileTF.getText());
			textArea.setText(textArea.getText()+ "\ngetting java scripted webpage...");
			update(getGraphics());
			String sourceCode = fam.getJavaScriptedWebPage(urlTF.getText());
			textArea.setText(textArea.getText()+ "\ngetting links...");
			update(getGraphics());
			ArrayList<String> links = fam.getLinks(sourceCode);
			textArea.setText(textArea.getText()+ "\ngetting info from links...");
			update(getGraphics());
			ArrayList<FSPerson> person = fam.getInfo(links);
			textArea.setText(textArea.getText()+ "\nwriting info to file...");
			update(getGraphics());
			fam.writeFile(person, fileTF.getText());
			String time = fam.getTimeString(System.currentTimeMillis() - start_time);
			textArea.setText(textArea.getText()+ "\ncompleted in " + time);
			update(getGraphics());
		}
	}
}
