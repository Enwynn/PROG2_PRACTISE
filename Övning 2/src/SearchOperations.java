
import java.util.Collection;
import java.util.Optional;
import java.util.SortedSet;

interface SearchOperations {

	/**
	 * Kontrollerar om det finns en artist med det sökta namnet.
	 * <p>
	 * Exempel:
	 * doesArtistExist("Phoebe Bridgers") => false
	 * doesArtistExist("Miles Davis") => true
	 *
	 * @param name Artistens namn
	 * @return true om artisten finns, false om inte
	 */
	boolean doesArtistExist(String name);

	/**
	 * Ger en omodifierbar samling med genrer.
	 * <p>
	 * Exempel:
	 * getGenres() => en samling med 19 genrer.
	 *
	 * @return en omodifierbar samling med genrer
	 */
	Collection<String> getGenres();

	/**
	 * Hämtar inspelning med den sökta titeln.
	 * <p>
	 * Exempel:
	 * getRecordingByName("Punisher").isPresent() == false
	 * getRecordingByName("Giant Steps").isPresent() == true
	 *
	 * @param title Inspelningens titel
	 * @return ett Optional-objekt som innehåller den sökta inspelningen om den hittades
	 */
	Optional<Recording> getRecordingByName(String title);

	/**
	 * Hämtar en omodifierbar samling med inspelningar från och med det angivna året.
	 * <p>
	 * Exempel:
	 * getRecordingsAfter(2010) => samling med 2 objekt
	 *
	 * @param year året som sökningen startar från (och inkluderar)
	 * @return en omodifierbar samling med inspelningar
	 */
	Collection<Recording> getRecordingsAfter(int year);

	/**
	 * Hämtar en omodifierbar samling med inspelningar av artisten
	 * sorterade i stigande ordning på år.
	 * <p>
	 * Exempel:
	 * getRecordingsByArtistOrderedByYearAsc("John Coltrane") => samling med 2 objekt
	 *
	 * @param artist den sökta artisten
	 * @return ett omodifierbar samling med inspelningar
	 */
	SortedSet<Recording> getRecordingsByArtistOrderedByYearAsc(String artist);

	/**
	 * Hämtar en omodifierbar samling med inspelningar i genren.
	 * <p>
	 * Exempel:
	 * getRecordingsByGenre("Jazz") => samling med 19 objekt
	 *
	 * @param genre den sökta genren
	 * @return ett omodifierbar samling med inspelningar
	 */
	Collection<Recording> getRecordingsByGenre(String genre);

	/**
	 * Hämtar en omodifierbar samling med inspelningar i genren gjorda
	 * mellan de angivna åren
	 * <p>
	 * Exempel:
	 * getRecordingsByGenreAndYear("Jazz", 1950, 1960) => samling med 7 objekt
	 *
	 * @param genre    den efterfrågade genren
	 * @param yearFrom första året i intervallet
	 * @param yearTo   sista året i intervallet
	 * @return en omodifierbar samling
	 */
	Collection<Recording> getRecordingsByGenreAndYear(String genre, int yearFrom, int yearTo);

	/**
	 * Tar emot en samling och returnerar en ny samling
	 * som bara innehåller de som inte redan fanns i databasen.
	 *
	 * @param offered En samling med inspelningar
	 * @return en omodifierbar samling med de inspelningar som inte redan finns
	 */
	Collection<Recording> offerHasNewRecordings(Collection<Recording> offered);

	/**
	 * Hämtar en omodifierbar samling med inspelningar innan det angivna året.
	 * <p>
	 * Exempel:
	 * optionalGetRecordingsBefore(1960) => samling med 10 objekt daterade < 1960
	 *
	 * @param year året som sökningen löper till (och exkluderar)
	 * @return ett omodifierbar samling med inspelningar
	 */
	default Collection<Recording> optionalGetRecordingsBefore(int year) {
		return null;
	}

	/**
	 * Hämtar en omodifierbar samling med inspelningar av den sökta artisten.
	 * Samlingen ska vara sorterad på titel i stigande ordning A -> Z
	 * <p>
	 * Exempel:
	 * optionalGetRecordingsByArtistOrderedByTitleAsc(1960) => sorterad samling med 10 objekt
	 *
	 * @param artist den sökta artisten
	 * @return ett omodifierbar samling med inspelningar sorterade på titel
	 */
	default SortedSet<Recording> optionalGetRecordingsByArtistOrderedByTitleAsc(String artist) {
		return null;
	}

	/**
	 * Hämtar en omodifierbar samling med inspelningar från det angivna året.
	 * <p>
	 * Exempel:
	 * optionalGetRecordingsFrom(1983) => samling med 6 objekt
	 *
	 * @param year det sökta året
	 * @return en omodifierbar samling med inspelningar
	 */
	default Collection<Recording> optionalGetRecordingsFrom(int year) {
		return null;
	}
}
