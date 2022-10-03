import javafx.scene.layout.BorderPane;

public abstract class Person {
	private String id, name, sex;
	
	//Constructor
	public Person() {
		
	}
	public Person(String id, String name, String sex) {
		this.id = id;
		this.name = name;
		this.sex = sex;
	}
	
	//Get & Set of 4 data (id, name, sex, age)
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

	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	//Method
	public abstract void newRecord(Person[] peoples, int[] peopleSize, BorderPane mainPane);
	public abstract void showInfo(Person[] peoples, BorderPane mainPane);
	public abstract boolean search(String search);
	public abstract void searchGUI(Person[] peoples, int[] peopleStorage, int[] peopleSize, BorderPane mainPane);
	
}
