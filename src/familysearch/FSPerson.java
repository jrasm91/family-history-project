package familysearch;

import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

public class FSPerson {

	private final int BUFFER = 49;
	private final int B1 = 30;
	private final int B2 = 30;
	private final int B3 = 8;
	private final int B4 = 5;
	private final int B5 = 0;

	String title;
	ArrayList<Parameter> params;
	ArrayList<Household> household;

	public FSPerson(){
		title = "";
		params = new ArrayList<Parameter>();
		household = new ArrayList<Household>();
	}
	public FSPerson(ArrayList<Parameter> params){
		title = "";
		this.params = params;
	}
	public void addParam(Parameter param){
		params.add(param);
	}
	public ArrayList<Parameter> getParams(){
		return params;
	}
	public void addTitle(String title){
		this.title = title;
	}
	public void addHousehold(Household newH){
		household.add(newH);
	}
	public String toString(){
		String toReturn = "";
		toReturn += "\r\n" + title + "\r\n";
		for(int i = 0; i < params.size(); i++)
			if(!params.get(i).getValue().isEmpty())
				toReturn += "\r\n" + StringUtils.leftPad(params.get(i).getField(), BUFFER) + params.get(i).getValue();

		if(household.size() != 0)
			toReturn += "\r\n\r\n" +
					StringUtils.rightPad("Relation", B1) + 
					StringUtils.rightPad("Name", B2) + 
					StringUtils.rightPad("Gender", B3) + 
					StringUtils.rightPad("Age", B4) + 
					StringUtils.rightPad("Birthplace", B5);

		for(int i = 0; i < household.size(); i++)
			toReturn += "\r\n " + 
					StringUtils.rightPad(household.get(i).getRelation(), B1) + 
					StringUtils.rightPad(household.get(i).getName(), B2) + 
					StringUtils.rightPad(household.get(i).getGender(), B3) +
					StringUtils.rightPad(household.get(i).getAge(), B4) +
					StringUtils.rightPad(household.get(i).getBirthplace(), B5); 
		return toReturn;
	}
}
