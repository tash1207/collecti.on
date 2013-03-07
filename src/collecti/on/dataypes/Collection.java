package collecti.on.dataypes;

public class Collection {
	public String id;
	public String username;
	public String title;
	public String description;
	public String category;
	public Boolean is_private;
	public String photo;
	
	public Long[] itemIDs;
	
	public Collection (String id, String username, String title, String description, 
			String category, Boolean is_private, String photo) {
		this.id = id;
		this.username = username;
		this.title= title;
		this.description = description;
		this.category = category;
		this.is_private = is_private;
		this.photo = photo;
	}

}
