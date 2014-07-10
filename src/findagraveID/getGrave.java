
package findagraveID;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.lang3.StringUtils;

import findagravecem.Person;


public class getGrave extends JFrame{

	private static final long serialVersionUID = 1936976022749340275L;
	private static final int WIDTH = 380, defaultT = 200, defaultG = 10;
	private static final int HEIGHT = 300;
	private final int mySize = 20;	
	private JLabel mmidL, lineL, limitTL, limitGL;
	private JTextField mmidTF, limitTTF, limitGTF;
	private JButton b1;
	private JTextArea textArea;
	private JScrollPane scrollPane;
	private JRadioButton ancestorRB, descendantRB, normalRB;
	private ButtonGroup option;	
	private int depth, count, limitT, limitG;
	private String defaultFolder = "C://Users/" + System.getProperty("user.name") + "/Desktop/Find A Grave/INFORMATION/";
	private ArrayList<ArrayList<Person>> outprint;
	private boolean all;

	public static void main(String[] args) throws IOException {
		new getGrave();
	}

	public getGrave(){
		outprint = new ArrayList<ArrayList<Person>>();
		all = true;
		depth = 0;
		count = 0;
		limitT = defaultT;
		limitG = defaultG;
		//GUI basic setup stuff
		setResizable(false);
		setTitle("Find A Grave: Help Utility");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);		
		//T - limit stuff
		limitTTF = new JTextField(3);
		setFont(limitTTF, mySize);
		limitTL = new JLabel("T-limit:");
		setFont(limitTL, mySize);
		//G - limit stuff
		limitGL = new JLabel("G-limit:");
		setFont(limitGL, mySize);
		limitGTF = new JTextField(3);
		setFont(limitGTF, mySize);
		//Memorial ID stuff
		mmidL = new JLabel("Enter Memorial ID: ");
		setFont(mmidL, mySize);
		mmidTF = new JTextField(10);
		setFont(mmidTF, mySize);
		//Button Group stuff
		option = new ButtonGroup();
		ancestorRB = new JRadioButton();
		ancestorRB.setText("Ancestors");
		setFont(ancestorRB, mySize-1);
		descendantRB = new JRadioButton();
		descendantRB.setText("Descendants");
		setFont(descendantRB, mySize-1);
		normalRB = new JRadioButton("title", true);
		normalRB.setText("Normal");
		setFont(normalRB, mySize-1);
		option.add(ancestorRB);
		option.add(descendantRB);
		option.add(normalRB);
		//GO!!! Button stuff
		b1 = new JButton("GO!");
		b1.addActionListener(new GOListener());
		setFont(b1, mySize);
		//Line Divide stuff
		lineL = new JLabel("----------------------------------------------------------------------------------------");
		//Console stuff
		textArea = new JTextArea(4, 22);
		setFont(textArea, mySize);
		scrollPane = new JScrollPane(textArea);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.black, 3));
		textArea.setEditable(false);

		Container pane = getContentPane();
		FlowLayout f = new FlowLayout();
		f.setAlignment(FlowLayout.CENTER);
		pane.setLayout(f);
		pane.add(mmidL);
		pane.add(mmidTF);
		pane.add(ancestorRB);
		pane.add(descendantRB);
		pane.add(normalRB);
		pane.add(limitGL);
		pane.add(limitGTF);
		pane.add(limitTL);
		pane.add(limitTTF);
		pane.add(b1);
		pane.add(lineL);
		pane.add(scrollPane);
		setVisible(true);
	}

	private void setFont(JComponent c, int fontSize){
		c.setFont(new Font(c.getFont().getName(), c.getFont().getStyle(), fontSize));
	}

	public class GOListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			String graveNum = mmidTF.getText();
			mmidTF.setText("");
			if(graveNum.equals("")){
				textArea.setText("please insert a memorial number to begin");
				return;
			}				
			if(normalRB.isSelected()){
				textArea.setText("downloading information (" + graveNum + ")");
				update(getGraphics());
				if(writeGraveFile(getGrave(graveNum), defaultFolder))
					textArea.setText("file written successfully");
				else
					textArea.setText("file NOT written successfully");
				return;
			}
			limitT = defaultT;
			limitG = defaultG;
			try{
				if(!limitTTF.getText().isEmpty())
					limitT = Integer.parseInt(limitTTF.getText());
				if(!limitGTF.getText().isEmpty())
					limitG = Integer.parseInt(limitGTF.getText());
			} catch(NumberFormatException e1){
				textArea.setText("please insert correct limits");
				return;
			}
			if(ancestorRB.isSelected()){
				outprint = new ArrayList<ArrayList<Person>>();
				count = 0;		
				depth = 0;
				all = true;
				getAncestors(graveNum, defaultFolder);
				textArea.setText(count + " - ancestors successfully downloaded");
				if(count < limitT && all)
					textArea.setText(count + " - ancestors successfully downloaded (all)");
				Person p1 = outprint.get(0).get(0);
				String fileName = "INFORMATION/" + p1.getLastName().toUpperCase() + " (" + p1.getId() + ")" + "/Ancestors";
				writeSummaryFile(outprint, fileName, "ANCESTORS");
				textArea.setText(textArea.getText() + "\nDONE");
				limitTTF.setText("");
				limitGTF.setText("");
				return;
			}
			if(descendantRB.isSelected()){
				outprint = new ArrayList<ArrayList<Person>>();
				count = 0;		
				depth = 0;
				all = true;
				getDescendants(graveNum, defaultFolder);
				textArea.setText(count + " - descendants successfully downloaded");
				if(count < limitT && all)
					textArea.setText(count + " - descendants successfully downloaded (all)");
				Person p1 = outprint.get(0).get(0);
				String fileName = "INFORMATION/" + p1.getLastName().toUpperCase() + " (" + p1.getId() + ")" + "/Descendants";
				writeSummaryFile(outprint, fileName, "DESCENDANTS");
				textArea.setText(textArea.getText() + "\nDONE");
				limitTTF.setText("");
				limitGTF.setText("");
				return;
			}
		}

		private void getAncestors(String graveNum, String folderName){
			textArea.setText("downloading information (" + graveNum + ")");
			limitTTF.setText("" + count);
			limitGTF.setText("" + depth);
			update(getGraphics());
			Person p = getGrave(graveNum);
			if(writeGraveFile(p, folderName)){
				if(outprint.size() == depth)
					outprint.add(new ArrayList<Person>());
				outprint.get(depth).add(p);
				count++;
				for(int i = 0; i < p.getParentLinks().size() && count < limitT; i++){
					String link = p.getParentLinks().get(i);
					String number = link.substring(0, link.indexOf(" "));
					String extraFolder = p.getLastName() + " (" + p.getId() + ")/";
					depth++;
					if(depth <= limitG)
						getAncestors(number, folderName + extraFolder);
					else{
						all = false;
						depth--;
					}
				}
				depth--;
			}
		}

		private void getDescendants(String graveNum, String folderName){
			textArea.setText("downloading information (" + graveNum + ")");
			limitTTF.setText("" + count);
			limitGTF.setText("" + depth);
			update(getGraphics());
			Person p = getGrave(graveNum);
			if(writeGraveFile(p, folderName)){
				if(outprint.size() == depth)
					outprint.add(new ArrayList<Person>());
				outprint.get(depth).add(p);
				count++;
				for(int i = 0; i < p.getChildrenLinks().size() && count < limitT; i++){
					String link = p.getChildrenLinks().get(i);
					String number = link.substring(0, link.indexOf(" "));
					String extraFolder = p.getLastName() + " (" + p.getId() + ")/";
					depth++;
					if(depth <= limitG)
						getDescendants(number, folderName + extraFolder);
					else{
						all = false;
						depth--;
					}
				}
				depth--;
			}
		}

		private Person getGrave(String number){
			try{
				Person person = new Person();
				URL webpage; // write html for the grave part of the file
				webpage = new URL("http://www.findagrave.com/cgi-bin/fg.cgi?page=gr&GRid=" + number);
				BufferedReader in = new BufferedReader(new InputStreamReader(webpage.openConnection().getInputStream()));
				String inputLine = null;
				String line = "";
				while((inputLine = in.readLine()) != null)
					line += inputLine;
				if(line.indexOf("Birth:&nbsp") == -1){ // case 0: NO EXIST - end
					textArea.setText("invalid memorial number.");
					return null;
				}
				person.setId(number);
				line = line.replaceAll("<i>", "-");
				line = line.replaceAll("</i>", "-");
				line = line.substring(line.indexOf("<HTML><HEAD><TITLE>") + 19);
				int num1 = line.indexOf("(");
				int num2 = line.indexOf("-");
				if(num1 > num2)
					num1 = num2;
				person.setLastName(line.substring(0, (num1 -1))); //set name
				line = line.substring(line.indexOf("Birth:&nbsp"));
				for(int i = 0; i <2; i++)
					line = line.substring(line.indexOf(">") + 1);
				person.setBirth(line.substring(0, line.indexOf("</td>")).replaceAll("<br>", ", "));
				line = line.substring(line.indexOf("</td>"));
				line = line.substring(line.indexOf("Death:&nbsp"));
				for(int i = 0; i <2; i++)
					line = line.substring(line.indexOf(">") + 1);
				person.setDeath(line.substring(0, line.indexOf("</td>")).replaceAll("<br>", ", "));
				line = line.substring(line.indexOf("</td>"));
				String temp1 = line.substring(line.indexOf(">") + 1, line.indexOf("&nbsp"));
				while(temp1.indexOf("<") == 0)
					temp1 = temp1.substring(temp1.indexOf(">") + 1);
				temp1 = temp1.replaceAll("<BR>", ", ");
				while(temp1.indexOf("<") != -1)
					temp1 = temp1.replace(temp1.substring(temp1.indexOf("<"), temp1.indexOf(">") + 1), " ");
				temp1 = temp1.replaceAll("<br>", "");
				person.setBio(temp1);
				line = line.substring(line.indexOf("&nbsp"));
				if(line.indexOf("Family links:") != -1){
					line = line.substring(line.indexOf("Family links:"));
					String family = line.substring(line.indexOf("Family links:"), line.indexOf(">Burial"));
					family = family.replaceAll("[*]", "");
					if(family.indexOf("Parent") != -1){
						family = family.substring(family.indexOf("&nbsp;&nbsp;"));
						while(family.indexOf("&nbsp;&nbsp;") == 0){
							String tempAdd 
							= family.substring(family.indexOf("GRid") + 5, family.indexOf("</a><br"));
							tempAdd = tempAdd.replaceAll("\">", " ");
							family = family.substring(family.indexOf("</a><br>") + 8);
							person.addParent(tempAdd);
						} 
					}
					if(family.indexOf("Spouse") != -1){
						family = family.substring(family.indexOf("&nbsp;&nbsp;"));
						while(family.indexOf("&nbsp;&nbsp;") == 0){
							String tempAdd = family.substring(family.indexOf("GRid") + 5, family.indexOf("</a><br"));
							tempAdd = tempAdd.replaceAll("\">", " ");
							family = family.substring(family.indexOf("</a><br>") + 8);
							person.addSpouse(tempAdd);
						} 
					}
					if(family.indexOf("Children") != -1){
						family = family.substring(family.indexOf("&nbsp;&nbsp;"));
						while(family.indexOf("&nbsp;&nbsp;") == 0){
							String tempAdd 
							= family.substring(family.indexOf("GRid") + 5, family.indexOf("</a><br"));
							tempAdd = tempAdd.replaceAll("\">", " ");
							family = family.substring(family.indexOf("</a><br>") + 8);
							person.addChild(tempAdd);
						} 
					}
					if(family.indexOf("Sibling") != -1){
						System.out.println("Sibling");
						family = family.substring(family.indexOf("&nbsp;&nbsp;"));
						while(family.indexOf("&nbsp;&nbsp;") == 0){
							String tempAdd 
							= family.substring(family.indexOf("GRid") + 5, family.indexOf("</a><br"));
							tempAdd = tempAdd.replaceAll("\">", " ");
							family = family.substring(family.indexOf("</a><br>") + 8);
//							person.addChild(tempAdd);
							System.out.println(tempAdd);
						} 
					}
				} 
				line = line.substring(line.indexOf(">Burial") + 1);
				line = line.substring(line.indexOf(">") + 1);
				while(line.indexOf("<") == 0)
					line = line.substring(line.indexOf(">") + 1);
				String tempCem  = line.substring(0, line.indexOf("</td>")).replaceAll("<BR>", " ");
				tempCem  = tempCem.replaceAll("<br>", ", ");
				while(tempCem.indexOf(">") != -1)
					tempCem = tempCem.replaceAll(tempCem.substring(tempCem.indexOf("<"), tempCem.indexOf(">") + 1), "");
				person.setCemetery(tempCem);
				line = line.substring(line.indexOf("Record added:"));
				person.setRecord(line.substring(14, line.indexOf("<br>")));
				URLConnection nc2 = new URL("http://www.findagrave.com/cgi-bin/fg.cgi?page=pv&GRid=" + number).openConnection();
				BufferedReader in2 = new BufferedReader(new InputStreamReader(nc2.getInputStream()));
				String inputLine2 = null;
				line = "";
				while((inputLine2 = in2.readLine()) != null)
					line += inputLine2;
				while(line.indexOf(".jpg") != -1){
					String temp = line.substring(0, line.indexOf(".jpg") + 4);
					line = line.substring(line.indexOf(".jpg") + 4);
					temp = temp.substring(temp.lastIndexOf("\"") + 1);
					temp = temp.replaceAll("/photoThumbnails", "");
					person.addPicture(temp);
				}
				return person;
			}catch(IOException e){
				textArea.setText("error with server");
				return null;
			}
		}

		private boolean writeGraveFile(Person nPerson, String folderName) {
			if(nPerson == null)
				return false;
			if(folderName == null)
				folderName = "";
			String fileName = folderName + nPerson.getLastName() + " (" + nPerson.getId() + ")/" +  nPerson.getLastName();
			fileName = fileName.replaceAll("\"", "-");
			BufferedWriter newFile = null;
			try{
				new File(fileName.substring(0, fileName.lastIndexOf("/") + 1)).mkdirs();
				newFile = new BufferedWriter(new FileWriter(fileName + ".txt"));
				newFile.write(nPerson.toStringTXT());
				newFile.close();
				for(int i = 0; i < nPerson.getPictureLinks().size(); i++){
					BufferedImage image = ImageIO.read(new URL(nPerson.getPictureLinks().get(i)));
					ImageIO.write(image, "jpg", new File(fileName + " - " + (i + 1) + ".jpg" ));
				}
			}
			catch(IOException e){
				e.printStackTrace();
				return false;
			}
			return true;	
		}

		private boolean writeSummaryFile(ArrayList<ArrayList<Person>> finalList, String fileName, String relatives) {
			int nameBUF = 35;
			int idBUF = 10;
			if(finalList == null)
				return false;
			String fileName2 = "C://Users/" + System.getProperty("user.name") + "/Desktop/Find A Grave/" + fileName;
			BufferedWriter newFile;
			try{
				new File(fileName2.substring(0, fileName2.lastIndexOf("/") + 1)).mkdirs();
				newFile = new BufferedWriter(new FileWriter(fileName2 + ".txt"));
				fileName = fileName2;
				int size = 0;
				for(int i = 0; i < finalList.size(); i++)
					size += finalList.get(i).size();
				newFile.write("\r\n" + finalList.get(0).size() + " ROOT PERSON DOWNLOADED");
				newFile.write("\r\n" + (size-1) + " TOTAL " + relatives + " DOWNLOADED");
				newFile.write("\r\nRESULTS FROM " + (finalList.size() - 1) + " GENERATIONS");
				String ok = "NOT ";
				if(count < limitT && all)
					ok = "";
				newFile.write("\r\nALL " + relatives + " WERE " + ok + "DOWNLOADED");
				newFile.write("\r\n" + StringUtils.rightPad("NAME", nameBUF) + StringUtils.rightPad("   NUMBER", idBUF)); 
				String temp = "";
				for(int i = 0; i < (nameBUF + idBUF + 15); i++)
					temp += "-";
				newFile.write("\r\n" + temp);
				String type = "";
				for(int i = 0; i < finalList.size(); i++){
					if(i == 0)
						type = "ROOT PERSON";
					else if(i == 1){
						if(relatives.equals("ANCESTORS"))
							type = "PARENT(S)";
						else
							type = "CHILDREN";
					}
					else if(i == 2)
						type = "GRAND" + type;
					else
						type = "GREAT-" + type;
					newFile.write(StringUtils.rightPad("\r\n\r\n     (" + finalList.get(i).size() + ") -" + type + " downloaded", nameBUF + idBUF));
					for(int j = 0; j <finalList.get(i).size(); j ++){
						Person p = finalList.get(i).get(j);
						newFile.write("\r\n" + 
								StringUtils.rightPad(p.getLastName(), nameBUF) + " | " + 
								StringUtils.rightPad(p.getId(), idBUF)); 
					}
				}
				newFile.close();
				return  true; 
			} catch(IOException e){
				return false;
			}
		}
	}
}



