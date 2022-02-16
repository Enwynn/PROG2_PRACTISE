import java.util.*;

public class Searcher implements SearchOperations, Stats{

	private final Map< String, Recording> hashMapArtists = new HashMap<>();
	private final Map< String, Recording> hashMapTitles = new HashMap<>();
	private final SortedMap< Integer, Collection<Recording>> sortedMapYears = new TreeMap<>();

	private final Collection <String> hashSetGenres = new HashSet<>();
	private final Collection <Recording> hashSetRec = new HashSet<>();
	private final SortedSet<Recording> treeSetRec = new TreeSet<>();

	public Searcher (Collection<Recording> data) {
		addList(data);
	}

	private void addList (Collection<Recording> data) {
		for (Recording r: data){
			hashMapArtists.put(r.getArtist(), r);
			hashMapTitles.put(r.getTitle(), r);
			hashSetGenres.addAll(r.getGenre());
			treeSetRec.add(r);
			hashSetRec.add(r);

			if (!sortedMapYears.containsKey(r.getYear())) {
				sortedMapYears.put(r.getYear(), new TreeSet<>());
				sortedMapYears.get(r.getYear()).add(r);

			}
			else if (sortedMapYears.containsKey(r.getYear())) {
				sortedMapYears.get(r.getYear()).add(r);
			}
		}
	}

	@Override
	public boolean doesArtistExist(String name) {
		return hashMapArtists.containsKey(name);
	}
	public Collection<String> getGenres() {
		return Collections.unmodifiableCollection(hashSetGenres);
	}

	@Override
	public Optional<Recording> getRecordingByName(String title) {
		return Optional.ofNullable(hashMapTitles.get(title));
	}

	@Override
	public Collection<Recording> getRecordingsAfter(int year) {
		SortedMap <Integer, Collection<Recording>> sortedSubMapYears = sortedMapYears.tailMap(year);
		Collection<Recording> collectionRecYearsBetween = new TreeSet<>();
		sortedSubMapYears.values().forEach(collectionRecYearsBetween::addAll);
      	return Collections.unmodifiableCollection(collectionRecYearsBetween);
	}


	@Override
	public SortedSet<Recording> getRecordingsByArtistOrderedByYearAsc(String artist) {
		SortedSet <Recording> sortedSetRecByYear = new TreeSet<>(treeSetRec);
		sortedSetRecByYear.removeIf(a -> !a.getArtist().equals(artist));
		return Collections.unmodifiableSortedSet(sortedSetRecByYear);
	}

	@Override
	public Collection<Recording> getRecordingsByGenre(String genre) {
		HashSet<Recording> hashSetRecOneGenre = new HashSet<>(hashSetRec);
		hashSetRecOneGenre.removeIf(a -> !a.getGenre().contains(genre));
		return Collections.unmodifiableCollection(hashSetRecOneGenre);
	}

	@Override
	public Collection<Recording> getRecordingsByGenreAndYear(String genre, int yearFrom, int yearTo) {
		SortedMap <Integer, Collection<Recording>> sortedSubMapByYearAndGenre = sortedMapYears.subMap(yearFrom, yearTo + 1);
		Collection<Recording> finalGenreAndYear = new TreeSet<>();
		sortedSubMapByYearAndGenre.values().forEach(finalGenreAndYear::addAll);
		finalGenreAndYear.removeIf(a -> !a.getGenre().contains(genre));
		return Collections.unmodifiableCollection(finalGenreAndYear);
	}

	@Override
	public Collection<Recording> offerHasNewRecordings(Collection<Recording> offered) {
		HashSet<Recording> hashSetOffered = new HashSet<>(hashSetRec);
		HashSet<Recording> tempHashSet = new HashSet<>(hashSetRec);
		hashSetOffered.addAll(offered);
		hashSetOffered.removeAll(tempHashSet);
		return Collections.unmodifiableCollection(hashSetOffered);
	}

	@Override
	public long numberOfArtists() {
		return hashMapArtists.size();
	}

	@Override
	public long numberOfGenres() {
		return hashSetGenres.size();
	}

	@Override
	public long numberOfTitles() {
		return hashMapTitles.size();
	}
}
