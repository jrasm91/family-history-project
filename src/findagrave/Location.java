package findagrave;

public class Location {

	private String date;
	private String location;
	
	public Location() {
		date = "";
		location = "";
	}
	
	public Location(String date, String location){
		this.date = date;
		this.location = location;
	}
	
	public String getDate(){
		return date;
	}
	
	public String getLocation(){
		return location;
	}
}
