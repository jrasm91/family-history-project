package findagrave;

import java.util.ArrayList;

public class FamilyLink {
	ArrayList<Link> parents, spouses, children; 
	public FamilyLink() {
		parents = new ArrayList<Link>();
		spouses = new ArrayList<Link>();
		children = new ArrayList<Link>();
	}
	public ArrayList<Link> getParents(){
		return parents;
	}
	public ArrayList<Link> getSpouses(){
		return spouses;
	}
	public ArrayList<Link> getChildren(){
		return children;
	}
	public void addParent(Link link){
		if(link == null)
			return;
		if(!parents.contains(link))
			parents.add(link);
	}
	public void addSpouse(Link link){
		if(link == null)
			return;
		if(!spouses.contains(link))
			spouses.add(link);
	}
	public void addChild(Link link){
		if(link == null)
			return;
		if(!children.contains(link))
			children.add(link);
	}
	public String toString(){
		String buffer = "";
		if(parents.size() != 0)
			buffer += "\n  Parents";
		for(int i = 0; i < parents.size(); i++)
			buffer += "\n    " + parents.get(i);
		if(spouses.size() != 0)
			buffer += "\n  Spouses";
		for(int i = 0; i < spouses.size(); i++)
			buffer += "\n    " + spouses.get(i);
		if(children.size() != 0)
			buffer += "\n  Children";
		for(int i = 0; i < children.size(); i++)
			buffer += "\n    " + children.get(i);
		return buffer;
	}
}
