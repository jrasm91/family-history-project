package familysearch;

public class Parameter {

	private String value;
	private String field;
	
	public Parameter(){
		value = "";
		field = "";
	}
	public Parameter(String field, String value){
		this.value = value;
		this.field = field;
	}
	public String getValue(){
		return value;
	}
	public String getField(){
		return field;
	}
	public void setValue(String value){
		this.value = value;
	}
	public void setField(String field){
		this.field = field;
	}
	public String toString(){
		return field + value;
	}
}
