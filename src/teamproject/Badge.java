/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teamproject;

/**
 *
 * @author Joseph
 */
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
}
