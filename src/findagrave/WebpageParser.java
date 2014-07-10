package findagrave;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Scanner;
import java.util.logging.Level;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class WebpageParser {
	private final int RELOAD_LIMIT = 10;

	WebClient client;
	HtmlPage currentPage;

	public WebpageParser(){
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
		java.util.logging.Logger.getLogger("org.apache.http.client.protocol.ResponseProcessCookies").setLevel(Level.OFF);
		client = new WebClient(BrowserVersion.CHROME_16);	
		client.setThrowExceptionOnScriptError(false);
		currentPage = null;
	}

	public GPerson makePerson(int mid){
		if(!loadPage("http://www.findagrave.com/cgi-bin/fg.cgi?page=gr&GRid=" + mid))
			return null;
		GPerson person = new GPerson(mid);
		person.setName(findName());
		person.setBirth(findBirth());
		person.setDeath(findDeath());
		person.setBio(findBio());
		person.setFamilyLinks(findFamilyLinks());
		person.setBurial(findBurial());
		return person;
	}

	private String findName(){
		String text = currentPage.asText();
		if(text.indexOf("(") <= 2)
			return null;
		return text.substring(0, text.indexOf("(") - 1);
	}

	private Location findBirth(){
		String text = currentPage.asText();
		text = text.substring(text.indexOf("Birth:") + 6);						//text is set to have correct starting index
		String temp = text.substring(0, text.indexOf("Death:"));				//temp is set to include everything from Birth -> up until Death
		String date = temp.substring(0, temp.indexOf("\r\n"));					//date = temp from beginning until first new line
		String place = (temp.substring(temp.indexOf("\r\n"))).trim();			//place = second half of string (after the first new line)
		return new Location(date.trim(), place.replaceAll("\r\n", " || "));
	}

	private Location findDeath(){
		String text = currentPage.asText();
		text = text.substring(text.indexOf("Death:") + 6);						//text is set to have correct starting index
		text = text.substring(0, text.indexOf("\r\n\r\n"));				//temp is set to include everything from death until first two new lines
		String date = text.substring(0, text.indexOf("\r\n"));					//date is set to include everything from beginning until first new line
		String place = (text.substring(text.indexOf("\r\n"))).trim();			//place is set to everything after the first new line
		return new Location(date.trim(), place.replaceAll("\r\n", " || "));
	}

	private String findBio(){
		String text = currentPage.asText();
		text = text.substring(text.indexOf("Death:") + 6);
		text = text.substring(text.indexOf("\r\n\r\n"));
		if(text.contains("Family links:"))
			return (text.substring(0, text.indexOf("Family links:"))).trim();
		return text.substring(0, text.indexOf("Burial:")).trim();
	}

	private FamilyLink findFamilyLinks(){
		String source = currentPage.asXml();
		FamilyLink family = new FamilyLink();
		if(!source.contains("Family links:"))
			return family;
		source = source.substring(source.indexOf("Family links:"));
		source = source.substring(0, source.indexOf("Burial:"));
		source = source.replaceAll("<i>", "");
		source = source.replaceAll(" </i>", "(i)");
		String temp = source;
		while(temp.contains("href=\"")){
			temp = temp.substring(temp.indexOf("href=\"") + 6);
			String link = temp.substring(0, temp.indexOf("\">"));
			String name = temp.substring(temp.indexOf("\">") + 2, temp.indexOf("<"));
			Link l = cleanLink(name, link);
			if(source.indexOf("Children") < source.indexOf(link))
				family.addChild(l);
			else if(source.indexOf("Spouses") < source.indexOf(link))
				family.addSpouse(l);
			else 
				family.addParent(l);
		}
		return family;
	}

	private String findBurial(){
		String text = currentPage.asText();
		text = text.substring(text.indexOf("Burial:") + 7);
		text = text.substring(0, text.indexOf("\r\n \r\n"));
		text = text.trim();
		return text.replaceAll("\r\n", " || ");
	}

	private Link cleanLink(String name, String link){
		if(!name.contains("(") || !name.contains(")"))
			return null;
		name = name.replaceAll("\r\n", "");
		while(name.contains("  "))
			name = name.replaceAll("  ", " ");
		link = "www.findagrave.com/cgi-bin/" + link;
		return new Link(name, link);
	}

	private boolean loadPage(String url){ //returns true is the page was loaded, false otherwise
		currentPage = null;
		for(int count = 0; count < RELOAD_LIMIT; count++){
			try { // attempts to get the page requested
				currentPage = client.getPage(url);
				return true;
			} catch (FailingHttpStatusCodeException e){ //catches i think what could be a server err on their part
				System.err.println("webpage was not loaded on " + ++count + " try");
				if(count == RELOAD_LIMIT){
					System.err.println("webpage was not loaded after " + count + " tries");
					return false;
				}
			} catch (MalformedURLException e){ //the URL is invalid (or malformed) + returns false
				System.err.println("Malformed URL");
				return false;
			} catch( IOException e) { // any other IO exception and prints stack trace (returning false)
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
}
