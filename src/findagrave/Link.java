package findagrave;

public class Link {
	String name;
	String link;
	public Link(String name, String link) {
		this.name = name;
		this.link = link;
	}
	public String getName(){
		return name;
	}
	public String getLink(){
		return link;
	}
	public String toString(){
		return name + ", " + link;
	}
	public boolean equals(Object obj){
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Link l = (Link) obj;
		if (name == null){
			if (l.name != null)
				return false;
		} else if (!name.equals(l.name))
			return false;
		if (link == null) {
			if (l.link != null)
				return false;
		} else if (!link.equals(l.link))
			return false;
		return true;


	}
}
