package collecti.on.dataypes;

public class Collection {
	public String username;
	public String title;
	public String description;
	public String photo;
	
	public Long[] itemIDs;
	
	public Collection (String username, String title, String description, String photo) {
		this.username = username;
		this.title= title;
		this.description = description;
		this.photo = photo;
	}

}
