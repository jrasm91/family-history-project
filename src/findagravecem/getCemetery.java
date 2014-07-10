package findagravecem;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

public class getCemetery extends JFrame {


	private static final long serialVersionUID = -2325229178672913899L;
	private static final int WIDTH = 380;
	private static final int HEIGHT = 458;
	private int mySize = 20;
	private final String[] states = {"", "SEARCH ALL STATES",
			"Alaska","Alabama","Arkansas","Arizona","California","Colorado","Connecticut","District Of Columbia",
			"Delaware","Florida","Georgia","Hawaii","Iowa", "Idaho","Illinois","Indiana","Kansas","Ketuncky",
			"Louisiana","Massachusetts","Maryland","Maine","Michigan","Minnesota","Missouri","Mississippi","Montana","North Carolina",
			"North Dakota","Nebraska","New Hampshire","New Jersey","New Mexico","Nevada","New York", "Ohio","Oklahoma","Oregon",
			"Pennsylvania","Puerto Rico","Rhode Island","South Carolina","South Dakota","Tennessee","Texas","Utah","Virginia","Vermont","Washington",
			"Wisconsin","West Virginia","Wyoming"};
	private final String[] countries = {"","United States of America"};
	private JLabel firstNameL, middleNameL, lastNameL, countryL, stateL, lineL;
	private JTextField firstNameTF, middleNameTF, lastNameTF;
	private JButton pg1;
	private JComboBox<String> statesCB, countriesCB;
	private JCheckBox aCB;
	private static JTextArea textArea;
	private JScrollPane scrollPane;


	public static void main(String[] args) throws IOException {

		new getCemetery();
	}

	public getCemetery(){
		//Set the window's basic settings.
		setResizable(false);
		setTitle("Find A Grave: Help Utility");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Container pane = getContentPane();
		FlowLayout f = new FlowLayout();
		f.setAlignment(FlowLayout.LEFT);
		pane.setLayout(f);
		//Instantiate the labels:
		firstNameL = new JLabel("First Name: ");
		middleNameL = new JLabel("\r\nMiddle Name: ");
		lastNameL = new JLabel("Last Name: ");
		countryL = new JLabel("Country: ");
		stateL = new JLabel("State (USA): ");
		lineL = new JLabel("----------------------------------------------------------------------------------------");
		//Buttons too:
		pg1 = new JButton("GO!");
		pg1.setFont(new Font(pg1.getFont().getName(), pg1.getFont().getStyle(), 20));
		pg1.addActionListener(new GOListener());
		//ComboBoxes
		statesCB = new JComboBox<String>(states);
		statesCB.setEnabled(false);
		countriesCB = new JComboBox<String>(countries);
		countriesCB.addActionListener(new GOListener(1));
		//CheckBoxes
		aCB = new JCheckBox("Maiden Name        ");
		//Text fields next
		firstNameTF = new JTextField(13);
		middleNameTF = new JTextField(13);
		lastNameTF = new JTextField(14);
		textArea = new JTextArea(8, 27);
		scrollPane = new JScrollPane(textArea);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.black, 3));
		//Set font sizes
		firstNameL.setFont(new Font(firstNameL.getFont().getName(), firstNameL.getFont().getStyle(), mySize));
		middleNameL.setFont(new Font(firstNameL.getFont().getName(), firstNameL.getFont().getStyle(), mySize));
		lastNameL.setFont(new Font(firstNameL.getFont().getName(), firstNameL.getFont().getStyle(), mySize));
		countryL.setFont(new Font(firstNameL.getFont().getName(), firstNameL.getFont().getStyle(), mySize));
		stateL.setFont(new Font(firstNameL.getFont().getName(), firstNameL.getFont().getStyle(), mySize));
		statesCB.setFont(new Font(statesCB.getFont().getName(), statesCB.getFont().getStyle(), mySize));
		countriesCB.setFont(new Font(statesCB.getFont().getName(), statesCB.getFont().getStyle(), mySize));
		aCB.setFont(new Font(aCB.getFont().getName(), aCB.getFont().getStyle(), mySize));
		firstNameTF.setFont(new Font(firstNameTF.getFont().getName(), firstNameL.getFont().getStyle(), mySize));
		middleNameTF.setFont(new Font(middleNameTF.getFont().getName(), middleNameTF.getFont().getStyle(), mySize));
		lastNameTF.setFont(new Font(lastNameTF.getFont().getName(), lastNameTF.getFont().getStyle(), mySize));
		textArea.setFont(new Font(textArea.getFont().getName(), textArea.getFont().getStyle(), 15));	
		textArea.setEditable(false);
		//Add things to the pane in the order you want them to appear (left to right, top to bottom)
		pane.add(firstNameL);
		pane.add(firstNameTF);
		pane.add(middleNameL);
		pane.add(middleNameTF);
		pane.add(lastNameL);
		pane.add(lastNameTF);
		pane.add(countryL);
		pane.add(countriesCB);
		pane.add(stateL);
		pane.add(statesCB);
		pane.add(aCB);
		pane.add(pg1);
		pane.add(lineL);
		pane.add(scrollPane);
		setVisible(true);
	}

	private class GOListener implements ActionListener{
		private int temp;
		public GOListener(int i){
			temp = i;
		}
		public GOListener(){
			temp = 0;
		}
		public void actionPerformed(ActionEvent e){
			if(temp == 1){
				if(countriesCB.getSelectedIndex() == 1)
					statesCB.setEnabled(true);
				else{
					statesCB.setEnabled(false);
					statesCB.setSelectedIndex(0);
				}
			}
			if(temp == 0){		
				ArrayList<String> list = new ArrayList<String>();
				list.add(firstNameTF.getText());
				list.add(middleNameTF.getText());
				list.add(lastNameTF.getText());
				if((list.get(0) + list.get(1) + list.get(2)).equals(""))
					textArea.setText("FAIL! please enter SOMETHING of a name");
				else{
					list.add((String)countriesCB.getSelectedItem());
					list.add((String)statesCB.getSelectedItem());
					list.add(aCB.isSelected() + "");
					firstNameTF.setText("");
					middleNameTF.setText("");
					lastNameTF.setText("");
					if(list.get(4).indexOf("SEARCH ALL STATES") != -1){
						for(int i = 2; i < states.length; i++){
							statesCB.setSelectedIndex(i);
							update(getGraphics());
							list.set(4, states[i]);
							download(list);
						}
					}
					else
						download(list);
				}
			}
		}

		private void download(ArrayList<String> list){

			try {					
				ArrayList<Person> finalList;
				textArea.setText("searching www.findagrave.com... please wait.");
				update(getGraphics());
				finalList = getCem(list);
				if(finalList == null)
					textArea.setText(textArea.getText() + "\r\nresults resulted in 'null'");
				else{
					textArea.setText(textArea.getText() + "\r\nsearch complete. (" + finalList.size() + ") results found.\r\nattempting to write file...");
					update(getGraphics());
					String fileName = "SEARCHES/" + list.get(2).toUpperCase() + list.get(0) + list.get(1) + "/" + list.get(4).toUpperCase() + " (" + finalList.size() + ")" ;
					boolean result = writeCemeteryFile(finalList, fileName);
					if(result)
						textArea.setText(textArea.getText() + "\r\nfile " + list.get(2).toUpperCase() + "_" + list.get(4) + ".txt written with success");
					else
						textArea.setText(textArea.getText() + "\r\nfile " + list.get(2).toUpperCase() + "_" + list.get(4) + ".txt could not be written.");
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private static boolean writeCemeteryFile(ArrayList<Person> finalList, String fileName) throws IOException{
		int nameBUF = 32;
		int bdBUF = 14;
		int idBUF = 10;
		if(finalList == null)
			return false;
		String fileName2 = "C://Users/" + System.getProperty("user.name") + "/Desktop/Find A Grave/" + fileName;
		BufferedWriter newFile;
		try{
			new File(fileName2.substring(0, fileName2.lastIndexOf("/") + 1)).mkdirs();
			newFile = new BufferedWriter(new FileWriter(fileName2 + ".txt"));
			fileName = fileName2;
		}
		catch(FileNotFoundException e){
			if(fileName.lastIndexOf("/") != -1)
				new File(fileName.substring(0, fileName.lastIndexOf("/") + 1)).mkdirs();
			newFile = new BufferedWriter(new FileWriter(fileName + ".txt"));
		}

		newFile.write("\n\rTOTAL NAMES FOUND: " + finalList.size());
		newFile.write("\r\n" + 
				StringUtils.rightPad("NAME", nameBUF) + "   " + 
				StringUtils.rightPad("BIRTH", bdBUF) + "   " + 
				StringUtils.rightPad("DEATH", bdBUF)+ "   " + 
				StringUtils.rightPad("NUMBER", idBUF)); 

		String temp = "";
		for(int i = 0; i < (nameBUF + (bdBUF * 2) + idBUF + 15); i++)
			temp += "-";
		newFile.write("\r\n" + temp);
		ArrayList<String> tempList = new ArrayList<String>();
		for(int i = 0; i < finalList.size(); i++){
			if(!tempList.contains(finalList.get(i).getCemetery()))
				tempList.add(finalList.get(i).getCemetery());
		}
		String[] cemList = tempList.toArray(new String[tempList.size()]);
		Arrays.sort(cemList);
		int counter = 0;
		while(counter < cemList.length){
			String cemetery = cemList[counter];
			Person p = finalList.get(0);
			boolean firstOne = true;
			while(true){
				int i = 0;
				p = null;
				for(; i < finalList.size(); i++){
					if(finalList.get(i).getCemetery().equals(cemetery)){
						p = finalList.get(i);
						break;
					}    
				}
				if(p == null)
					break;
				if(firstOne){
					newFile.write("\r\n\r\n     " + p.getCemetery());
					firstOne = false;
				}
				newFile.write("\r\n" + 
						StringUtils.rightPad(p.getLastName(), nameBUF) + " | " + 
						StringUtils.rightPad(p.getBirth(), bdBUF) + " | " + 
						StringUtils.rightPad(p.getDeath(), bdBUF) + " | " + 
						StringUtils.rightPad(p.getId(), idBUF)); 
				finalList.remove(i);
			} counter++;
		}
		newFile.close();
		return  true; 
	}

	@SuppressWarnings("deprecation")
	private static ArrayList<Person> getCem(ArrayList<String> info) throws IOException {
		String firstName = info.get(0);
		String middleName = info.get(1);
		String lastName = info.get(2);
		String country = info.get(3);
		int countryNum = 4;
		String maiden = "";
		if(info.get(5).equals("true"))
			maiden = "&GSiman=1";
		if(country.equals("United States of America"))
			countryNum = 4;
		String state = info.get(4);
		ArrayList<String> states = new ArrayList<String>();
		String[] tempList = {"blank1","blank2","Alaska","Alabama","Arkansas","Arizona","California","Colorado","Connecticut","District Of Columbia","Delaware","Florida","Georgia","Hawaii","Iowa", "Idaho","Illinois","Indiana","Kansas","Ketuncky","Louisiana","Massachusetts","Maryland","Maine","Michigan","Minnesota","Missouri","Mississippi","Montana","North Carolina","North Dakota","Nebraska","New Hampshire","New Jersey","New Mexico","Nevada","New York", "Ohio","Oklahoma","Oregon","Pennsylvania","Puerto Rico","Rhode Island","South Carolina","South Dakota","Tennessee","Texas","Utah","Virginia","Vermont","Washington","Wisconsin","West Virginia","Wyoming"};
		for(int i = 0; i < tempList.length; i++)
			states.add(tempList[i].toLowerCase());
		int stateNum = states.indexOf(state.toLowerCase());
		ArrayList<Person> finalList = new ArrayList<Person>();
		String check = "Sorry, there are no records in the Find A Grave database matching your query.";
		String urlString = "http://www.findagrave.com/cgi-bin/fg.cgi?page=gsr" + 
				"&GSfn=" + firstName + 
				"&GSmn=" + middleName + 
				"&GSln=" + lastName + maiden + 
				"&GSbyrel=all" + "&GSby=" + "&GSdyrel=all" + "&GSdy=" + 
				"&GScntry=" + countryNum + 
				"&GSst=" + stateNum +  
				"&GScnty=0" + "&GSgrid=" + "&df=all" + "&GSob=n";
		URL url = new URL(urlString);
		boolean downloaded = false;
		String line = "";
		for(int count = 0; ((downloaded == false) && (count < 20)); count++){
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
			String inputLine = null;
			while((inputLine = in.readLine()) != null){
				if(inputLine.contains(check)){
					textArea.setText(textArea.getText() + "\r\nsearch complete. no results found.");
					return null;
				}
				if(inputLine.contains("Records <B>"))
					downloaded = true;
				line += inputLine;
			}
		}
		if(!downloaded){
			textArea.setText(textArea.getText() + "\r\ncould not access www.findagrave.com");
			return null;
		}
		line = line.replaceFirst("sh&GRid=", "");
		line = line.substring(line.indexOf("Records <B>"));
		line = line.substring(line.indexOf("(of <B>") + 7);
		String temp = line.substring(0, line.indexOf("</B>")).replaceAll(",", "");
		int numResults = Integer.parseInt(temp);
		for(int i = 0; i < numResults/40 + 1; i++){
			int limit = 40;
			if(i == numResults/40)
				limit = numResults%40;
			for(int j = 0; j < limit; j++){

				if((line.indexOf("b. ") == -1 || line.indexOf("d. ") == -1))
					return finalList;
				Person p = new Person();
				line = line.replaceAll("sh&GRid=", "");
				line = line.substring(line.indexOf("&GRid=") + 6);
				String tempLine = line;
				line = line.replaceAll("<i>", "-");
				line = line.replaceAll("</i>", "-");

				p.setId(tempLine.substring(0, line.indexOf("&")));
				tempLine = tempLine.substring(tempLine.indexOf(">") + 1);
				p.setLastName(tempLine.substring(0, tempLine.indexOf("<")));
				tempLine = tempLine.substring(tempLine.indexOf("b. ") + 3);
				p.setBirth(tempLine.substring(0, tempLine.indexOf("d. ")));
				tempLine = tempLine.substring(tempLine.indexOf("d. ") + 3);
				p.setDeath(tempLine.substring(0, tempLine.indexOf("<")));
				tempLine = tempLine.substring(tempLine.indexOf("<"));
				while (tempLine.indexOf("<") == 0)
					tempLine = tempLine.substring(tempLine.indexOf("&pt=") + 4);
				p.setCemetery(URLDecoder.decode(tempLine.substring(0, tempLine.indexOf("&\">"))));
				tempLine = tempLine.substring(tempLine.indexOf("<BR>") + 4);
				tempLine = tempLine.replaceAll("<BR>", ", ");
				String location = tempLine.substring(0, tempLine.indexOf("<"));
				p.setCemetery(p.getCemetery() + " (" + location + ")");
				finalList.add(p);
				if(finalList.size() == numResults)
					return finalList;
			}
			downloaded = false;
			line = line.substring(0, line.indexOf("\">Records"));
			line = line.substring(line.lastIndexOf("\"") + 1);
			URL newURL = new URL("http://www.findagrave.com" + line);
			for(int count = 0; ((downloaded == false) && (count < 10)); count++){
				line = "";
				BufferedReader in = new BufferedReader(new InputStreamReader(newURL.openStream()));
				String inputLine = null;
				while((inputLine = in.readLine()) != null){
					if(inputLine.contains("Records <B>"))
						downloaded = true;
					line += inputLine;
				}	
				line = line.substring(line.indexOf("(of") + 7);
			}
		}return finalList;
	}
}
