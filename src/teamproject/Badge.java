package teamproject;

public class Badge {
    private String id;
    private String description;
	
	//constructor
    public Badge(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
	
    
    @Override
    public String toString(){
        return null;
    }
}
