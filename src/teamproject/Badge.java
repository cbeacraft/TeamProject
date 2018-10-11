package teamproject;

public class Badge {
    private String id;
    private String description;
	
	//constructor
    public Badge(String id, String description) {
        this.id = id;
        this.description = description;
    }
	
    public void setId(String ident) {
	id = ident;
        }

    public void setDescription(String desc) {
	description = desc;
        }
	
    public String getId() {
    	return id;
	}
	
    public String getDescription() {
	return description;
	}
    @Override
    public String toString(){
        return null;
    }
}
