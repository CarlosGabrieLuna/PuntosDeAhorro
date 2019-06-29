package Tools;

public class User {
	
	private String id;
	private String idiom;
	private String name;
	private String lastName;
	private String secondLastname;
	private int points;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getSecondLastname() {
		return secondLastname;
	}
	public void setSecondLastname(String secondLastname) {
		this.secondLastname = secondLastname;
	}
	public String getCompleteName()
	{
		return getName()+" "+getLastName()+" "+getSecondLastname();
	}
	public String getIdiom() {
		
		if(this.idiom.equals("ESP"))
			return "es-MX";
		if(this.idiom.equals("CHN"))
			return "yue-Hant-HK";
		if(this.idiom.equals("ENG"))
			return "en-US";
		if(this.idiom.equals("FRN"))
			return "fr-FR";
		if(this.idiom.equals("ARB"))
			return "ar-IL";
		if(this.idiom.equals("ALM"))
			return "de-DE";
		
		return "es-MX";
	}
	public void setIdiom(String idiom) {
		this.idiom = idiom;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	

}
