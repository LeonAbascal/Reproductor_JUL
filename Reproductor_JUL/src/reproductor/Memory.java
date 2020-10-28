package reproductor;

import java.util.ArrayList;

public class Memory {
	private ArrayList<User> users ;

	public Memory(ArrayList<User> users) {
		super();
		this.users = users;
	}

	public Memory() {
		super();
		this.users =  new ArrayList<User>();
	}

	public ArrayList<User> getUsers() {
		return users;
	}

}
