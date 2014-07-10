package findagravecem;

import java.util.ArrayList;

public class Person {

	private String id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String birth;
	private String death;
	private ArrayList<String> parentLinks;
	private ArrayList<String> childrenLinks;
	private ArrayList<String> spouseLinks;
	private String bio;
	private String cemetery;
	private String record;
	private ArrayList<String> pictureLinks;

	public Person(){
		id = null;
		firstName = null;
		middleName = null;
		lastName = null;
		birth = null;
		death = null;
		parentLinks = null;
		childrenLinks = new ArrayList<String>();
		parentLinks = new ArrayList<String>();;
		spouseLinks = new ArrayList<String>();;
		bio = null;
		cemetery = null;
		record = null;
		pictureLinks = new ArrayList<String>();
	}

	public void setId(String newID){
		id = newID.trim();
	}
	public void setFirstName(String newName){
		firstName = newName.trim();
	}
	public void setMiddleName(String newName){
		middleName = newName.trim();
	}
	public void  setLastName(String newName){
		lastName = newName.trim();
	}
	public void setBirth(String newBirth){
		birth = newBirth.trim();
	}
	public void setDeath(String newDeath){
		death = newDeath.trim();
	}
	public void setParentLinks(ArrayList<String> newParentLinks){
		parentLinks = newParentLinks;
	}
	public void setSpouseLinks(ArrayList<String> newSpouseLinks){
		spouseLinks = newSpouseLinks;
	}
	public void setChildrenLinks(ArrayList<String> newChildrenLinks){
		childrenLinks = newChildrenLinks;
	}
	public void setBio(String newBio){
		bio = newBio.trim();
	}
	public void setCemetery(String newCemetery){
		cemetery = newCemetery.trim();
	}
	public void setRecord(String newrecord) {
		record = newrecord.trim();
	}
	public void setPictureLinks(ArrayList<String> newPictureLink){
		pictureLinks = newPictureLink;
	}

	public String getId(){
		return id;
	}
	public String getFirstName(){
		return firstName;
	}
	public String getMiddleName(){
		return middleName;
	}
	public String getLastName(){
		return lastName;
	}
	public String getBirth(){
		return birth;
	}
	public String getDeath(){
		return death;
	}
	public ArrayList<String> getParentLinks(){
		return parentLinks;
	}
	public ArrayList<String> getSpouseLinks(){
		return spouseLinks;
	}
	public ArrayList<String> getChildrenLinks(){
		return childrenLinks;
	}
	public String getBio(){
		return bio;
	}
	public String getCemetery(){
		return cemetery;
	}
	public String getRecord() {
		return record;
	}
	public ArrayList<String> getPictureLinks(){
		return pictureLinks;
	}

	public void addParent(String newParent){
		if(!parentLinks.contains(newParent.trim()))
			parentLinks.add(newParent.trim());
	}
	public void addChild(String newChild){
		if(!childrenLinks.contains(newChild.trim()))
			childrenLinks.add(newChild.trim());
	}
	public void addSpouse(String newSpouse){
		if(!spouseLinks.contains(newSpouse.trim()))
			spouseLinks.add(newSpouse.trim());
	}
	public void addPicture(String newPicture){
		if(!pictureLinks.contains(newPicture.trim()))
			pictureLinks.add(newPicture.trim());
	}

	public String toStringTXT(){
		String output = new String();
		output += "\r\nID:\t\t" + id + "\r\n";
		output += "NAME:\t\t" + lastName + "\r\n";
		output += "BORN:\t\t" + birth + "\r\n";
		output += "DEATH:\t\t" + death + "\r\n";
		output += "CEMETERY:\t" + cemetery + "\r\n";
		output += "RECORD ADDED:\t" + record + "\r\n\r\n";
		if(childrenLinks.size() != 0){
			output += "Children Links:\r\n";
			for(int i = 0; i < childrenLinks.size(); i++)
				output += "\t" + childrenLinks.get(i) + "\r\n";
		}
		if(parentLinks.size() != 0){
			output += "PARENT LINKS:\r\n";
			for(int i = 0; i < parentLinks.size(); i++)
				output += "\t" + parentLinks.get(i) + "\r\n";
		}
		if(spouseLinks.size() != 0){
			output += "SPOUSE LINKS:\r\n";
			for(int i = 0; i < spouseLinks.size(); i++)
				output += "\t" + spouseLinks.get(i) + "\r\n";
		}
		if(pictureLinks.size() != 0){
			output += "PICTURE LINKS:\r\n";
			for(int i = 0; i < pictureLinks.size(); i++)
				output += "\t" + pictureLinks.get(i) + "\r\n";
		}
		if(!(bio.equals("") || bio.equals(null)))
			output += "\r\nBIO:\r\n\t" + bio + "\r\n";
		return output;
	}
	public String toString(){
		String output = new String();
		output += "ID:\t\t" + id + "\r\n";
		output += "NAME:\t\t" + lastName + "\r\n";
		output += "BORN:\t\t" + birth + "\r\n";
		output += "DEATH:\t\t" + death + "\r\n";
		output += "CEMETERY:\t" + cemetery + "\r\n";
		output += "RECORD ADDED:\t" + record + "\r\n";
		output += "CHILDREN LINKS:\r\n";
		for(int i = 0; i < childrenLinks.size(); i++)
			output += "\t" + childrenLinks.get(i) + "\r\n";
		output += "PARENT LINKS:\r\n";
		for(int i = 0; i < parentLinks.size(); i++)
			output += "\t" + parentLinks.get(i) + "\r\n";
		output += "SPOUSE LINKS:\r\n";
		for(int i = 0; i < spouseLinks.size(); i++)
			output += "\t" + spouseLinks.get(i) + "\r\n";
		output += "PICTURE LINKS:\r\n";
		for(int i = 0; i < pictureLinks.size(); i++)
			output += "\t" + pictureLinks.get(i) + "\r\n";
		String tempBio = bio;
		if(tempBio != null && tempBio.length() > 50)
			tempBio = bio.substring(0, 50);
		output += "BIO:\r\n\t" + tempBio + "\r\n";
		return output;
	}
}
