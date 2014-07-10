package familysearch;

public class Household {
	
	String relation;
	String name;
	String gender;
	String age;
	String birthplace;
	
	public Household(){
		relation = "";
		name = "";
		gender = "";
		age = "";
		birthplace = "";
	}
	
	public Household(String relation, String name, String gender, String age, String birthplace){
		this.relation = relation;
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.birthplace = birthplace;
	}
	
	public void setRelation(String relation){
		this.relation = relation;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setGender(String gender){
		this.gender = gender;
	}
	public void setAge(String age){
		this.age = age;
	}
	public void setBirthplace(String birthplace){
		this.birthplace = birthplace;
	}
	public String getRelation(){
		return relation;
	}
	public String getName(){
		return name;
	}
	public String getGender(){
		return gender;
	}
	public String getAge(){
		return age;
	}
	public String getBirthplace(){
		return birthplace;
	}
	public String toString(){
		return relation + "\t" + name + "\t" + gender + "\t" + age + "\t" + birthplace;		
	}
}
