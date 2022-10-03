import javafx.scene.layout.BorderPane;

public abstract class Facilities {
	private String name;
	
	//Constructor
	public Facilities() {
		
	}
	public Facilities(String name) {
		this.name = name;
	}
	
	//Get & Set of facility's name
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public abstract void newRecord(Facilities[] facilities, int[] facilitiesSize, BorderPane mainPane);
	public abstract void showInfo(Facilities[] facilities, BorderPane mainPane);
	public abstract boolean search(String search);
	public abstract void searchGUI(Facilities[] facilities, int[] facilitiesStorage, int[] facilitiesSize, BorderPane mainPane);
}
