package reproductor;

public class User {
	private String name;
	private String password;
	private int edad;
	private PlayList playlist;

	public User(String name, String password, int edad, PlayList playlist) {
		this.name = name;
		this.password = password;
		this.edad = edad;
		this.playlist = playlist;
	}

	public User() {
		this.name = "";
		this.password = "";
		this.edad = 0;
		this.playlist = null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getEdad() {
		return edad;
	}

	public void setEdad(int edad) {
		this.edad = edad;
	}

	public PlayList getPlaylist() {
		return playlist;
	}

	public void setPlaylist(PlayList playlist) {
		this.playlist = playlist;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", password=" + password + ", edad=" + edad + "]";
	}

}
