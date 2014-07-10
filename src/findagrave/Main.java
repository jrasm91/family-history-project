package findagrave;

public class Main {


	public static void main(String[] args) {
		//FindAGraveGUI gui = new FindAGraveGUI();
		WebpageParser parser = new WebpageParser();
		for(int i = 1; i < 10; i++){
			GPerson p = parser.makePerson(i);
			System.out.println(p);
			System.out.println(newLine());

		}
	}

	private static String newLine(){
		String buffer = "\n";
		for(int i = 0; i < 10; i++)
			buffer += "--=======---";

		return buffer;
	}
}
