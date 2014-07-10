package familysearch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class FamilySearch{

	private final int RELOAD_LIMIT = 10;
	private final int JAVASCRIPT_WAIT = 7500;
	private final int NUM_PAGE_RESULTS = 20;

	public FamilySearch(){

	}
	public FamilySearch(String filename, String url){
		long start_time = System.currentTimeMillis();
		System.out.println("getting java scripted webpage...");
		String sourceCode = getJavaScriptedWebPage(url);
		System.out.println("getting links...");
		ArrayList<String> links = getLinks(sourceCode);
		System.out.println("getting info from links...");
		ArrayList<FSPerson> person = getInfo(links);
		System.out.println("writing info to file...");
		writeFile(person, filename);
		String time = getTimeString(System.currentTimeMillis() - start_time);
		System.out.println("\ncompleted in " + time);
	}

	public String getJavaScriptedWebPage(String webpage) {
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http.client.protocol.ResponseProcessCookies").setLevel(Level.OFF);
		WebClient client = new WebClient(BrowserVersion.FIREFOX_10);	
		boolean loaded = false;
		int count = 0;
		HtmlPage currentPage = null;
		while(!loaded && count < RELOAD_LIMIT){
			try { 
				currentPage = client.getPage(webpage);
				loaded = true;
			} catch (FailingHttpStatusCodeException e){ //catches i think what could be a server err on their part
				System.err.println("ERROR: failing http status on try: " + ++count);
				if(count == RELOAD_LIMIT){
					System.err.println("ERROR: webpage could not be loaded after " + count + " tries");
					System.exit(0);
				}
			} catch ( MalformedURLException e){ //the URL is invalid (or malformed) + returns false
				System.err.println("Malformed URL");
				System.exit(0);
			} catch( IOException e) { // any other IO exception and prints stack trace (returning false)
				e.printStackTrace();
				System.exit(0);
			}
		} 
		client.waitForBackgroundJavaScript(JAVASCRIPT_WAIT); 
		String sourceCode = currentPage.asXml();
		client.closeAllWindows();
		return sourceCode;	
	}

	public ArrayList<String> getLinks(String textSource){
		ArrayList<String> urlList = new ArrayList<String>(); // ArrayList for the future HTML links
		int count = 0; // now parsing the page for the 20 links that are found on it, or until links an no longer found.
		while(textSource.indexOf("<a href=\"https://familysearch.org") != -1 && count++ < NUM_PAGE_RESULTS){
			textSource = textSource.substring(textSource.indexOf("<a href=\"https://familysearch.org") + 9);
			urlList.add(textSource.substring(0, textSource.indexOf("\"")));
		}
		if(urlList.size() == 0){
			System.err.println("Error: Empty List (no links found)");
			System.exit(0);
		}
		return urlList;
	}

	public ArrayList<FSPerson> getInfo(ArrayList<String> links){
		ArrayList<FSPerson> list = new ArrayList<FSPerson>();
		WebClient client = new WebClient(BrowserVersion.FIREFOX_10);	
		client.setThrowExceptionOnScriptError(false);
		for(int i = 0; i < links.size(); i++){
			boolean loaded = false;
			int count = 0;
			HtmlPage currentPage = null;
			while(!loaded && count < RELOAD_LIMIT){ // loop is tried until loaded information is true or a max RELOADED_LIMIT is reached
				try { // attempts to get the page requested
					currentPage = client.getPage(links.get(i));
					loaded = true;
				} catch (FailingHttpStatusCodeException e){ //catches i think what could be a server err on their part
					System.err.println("FAILING HTTP STATUS CODE on try: " + ++count);
					if(count == RELOAD_LIMIT){
						System.err.println("ERROR: webpage could not be loaded after " + count + " times");
						System.exit(0);
					}
				} catch ( MalformedURLException e){ //the URL is invalid (or malformed) + returns false
					System.err.println("Malformed URL");
					System.exit(0);
				} catch( IOException e) { // any other IO exception and prints stack trace (returning false)
					e.printStackTrace();
					System.exit(0);
				}
			}
			String text = currentPage.asText();
			Scanner scan = new Scanner(text);
			scan.useDelimiter("[\n\t\r]");
			FSPerson person = new FSPerson();
			String has = scan.next();
			while(scan.hasNext() && has.indexOf("PRINT") == -1) 
				has = scan.next();
			has = scan.next();
			person.addTitle(scan.next());
			while(scan.hasNext() && has.indexOf(":") == -1) 
				has = scan.next();
			while(scan.hasNext() && has.indexOf(":") != -1){				
				person.addParam(new Parameter(has, scan.next()));
				scan.next(); has = scan.next();
			}
			while(scan.hasNext() && has.indexOf("head") == -1)
				has = scan.next();

			while(scan.hasNext() && has.indexOf("Citing this Record") == -1){
				Household h1 = new Household();
				h1.setRelation(has.trim());
				h1.setName(scan.next().trim());
				h1.setGender(scan.next().trim());
				h1.setAge(scan.next().trim());
				h1.setBirthplace(scan.next().trim());
				person.addHousehold(h1);
				scan.next();
				has = scan.next();
			}
			scan.close();
			list.add(person);
		}
		return list;
	}

	public void writeFile(ArrayList<FSPerson> data, String filename){
		String fileName = filename;
		String fileName2 = "C://Users/" + System.getProperty("user.name") + "/Desktop/Family Search/" + fileName;
		BufferedWriter newFile = null;
		try{
			new File(fileName2.substring(0, fileName2.lastIndexOf("/") + 1)).mkdirs();
			newFile = new BufferedWriter(new FileWriter(fileName2 + ".txt"));
			if(data != null)
				for(int i = 0; i < data.size(); i++){
					newFile.write(data.get(i).toString());
					String div = "";
					for(int k = 0; k < 23; k++)
						div += "-----";
					newFile.write("\r\n" + div + "\r\n");
				}
			newFile.close();
		} catch(FileNotFoundException e){
			System.out.println("Bad File Name");
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
	}

	public String getTimeString(long difference) {
		StringBuffer buf = new StringBuffer();
		int hours = (int) (difference / (1000*60*60));
		int minutes = (int) (( difference % (1000*60*60) ) / (1000*60));
		int seconds = (int) (( ( difference % (1000*60*60) ) % (1000*60) ) / 1000);
		int mill = (int) (( ( difference % (1000*60*60) ) % (1000*60) ) % 1000);
		if(hours != 0)
			buf.append(String.format("%02d", hours)).append(" hr, ");
		if(minutes != 0)
			buf.append(String.format("%02d", minutes)).append(" min, ");
		buf.append(String.format("%02d", seconds)).append(".").append(String.format("%02d sec", mill));
		return "( " + buf + " )";
	}

}


