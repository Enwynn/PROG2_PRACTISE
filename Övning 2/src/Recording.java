import java.util.Collection;
import java.util.Objects;
import java.util.Set;

public class Recording implements Comparable<Recording> {
	private final int year;
	private final String artist;
	private final String title;
	private final String type;
	private final Set<String> genre;

	public Recording(String title, String artist, int year, String type, Set<String> genre) {
		this.title = title;
		this.year = year;
		this.artist = artist;
		this.type = type;
		this.genre = genre;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Recording)) return false;
		Recording recording = (Recording) o;
		return getYear() == recording.getYear() &&
				Objects.equals(getArtist(), recording.getArtist()) &&
				Objects.equals(getTitle(), recording.getTitle());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getYear(), getArtist(), getTitle());
	}

	public String getArtist() {
		return artist;
	}

	public Collection<String> getGenre() {
		return genre;
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	public int getYear() {
		return year;
	}

	@Override
	public int compareTo(Recording o) {
		if (title.equals(o.getTitle()) && artist.equals(o.getArtist())) {
			return 0;
		}
		if (year > o.getYear())
			return 1;
		else
			return -1;
	}



	@Override
	public String toString() {
		return String.format("{ %s | %s | %s | %d | %s }", artist, title, genre, year, type);
	}

}

