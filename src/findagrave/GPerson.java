package findagrave;

import java.util.ArrayList;

public class GPerson {

	int mid;
	String name, url, bio, burial;
	Location birth, death;
	FamilyLink familyLinks;	
	ArrayList<String> pictureLinks;	

	public GPerson(int mid){
		this.mid = mid;
		name = "";
		url = "http://www.findagrave.com/cgi-bin/fg.cgi?page=gr&GRid=" + mid;
		birth = new Location("", "");
		death = new Location("", "");
		bio = "";
		familyLinks = new FamilyLink();
		pictureLinks = new ArrayList<String>();	
	}

	public void setName(String name){
		this.name = name;
	}
	public void setBirth(Location birth){
		this.birth = birth;
	}
	public void setDeath(Location death){
		this.death = death;
	}
	public void setBio(String bio){
		this.bio = bio;
	}
	public void setFamilyLinks(FamilyLink link){
		familyLinks = link;
	}
	public void setBurial(String burial){
		this.burial = burial;
	}
	public void setPictureLinks(ArrayList<String> link){
		pictureLinks = link;
	}

	public String toString(){
		String buffer = "\n";
		buffer += "\nMemorial ID: " + mid;
		buffer += "\nName: " + name;
		buffer += "\nURL: " + url;
		buffer += "\nBirth Date: " + birth.getDate();
		buffer += "\nBirth Place: " + birth.getLocation();
		buffer += "\nDeath Date: " + death.getDate();
		buffer += "\nDeath Place: " + death.getLocation();
		buffer += "\nBio: " + bio;
		buffer += "\nFamily Links:" + familyLinks.toString();
		buffer += "\nBurial: " + burial;
		for(int i = 0; i < pictureLinks.size(); i++)
			buffer += "\nLink " + i + " " + pictureLinks.get(i);
		return buffer;	
	}

}
