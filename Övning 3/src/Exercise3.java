import java.io.*;
import java.util.*;

public class Exercise3 {
    private final List<Recording> recordings = new ArrayList<>();

    public void exportRecordings(String fileName) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            for (Recording r : recordings) {
                fileWriter.write("<recording>\n");
                fileWriter.write("\t<artist>" + r.getArtist() + "</artist>\n");
                fileWriter.write("\t<title>" + r.getTitle() + "</title>\n");
                fileWriter.write("\t<year>" + r.getYear() + "</year>\n");
                fileWriter.write("\t<genres>\n");
                for (String genre : r.getGenre()) {
                    fileWriter.write("\t\t<genre>" + genre + "</genre>\n");
                }
                fileWriter.write( "\t</genres>\n");
                fileWriter.write("</recording>");
            }
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void importRecordings(String fileName) throws FileNotFoundException {
        String title = null;
        String artist = null;
        int year = 0;
        TreeSet<String> t = new TreeSet<>();

        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
            int linesToRead = Integer.parseInt(in.readLine());
            int linesRead = 0;
            String tempLine;

            while ((tempLine = in.readLine()) != null && linesRead != linesToRead) {
                if (tempLine.contains(";")) {
                    String[] artTitleYear = tempLine.split(";");
                    artist = artTitleYear[0];
                    title = artTitleYear[1];
                    year = Integer.parseInt(artTitleYear[2]);
                }

                if (tempLine.length() == 1) {
                    int numberOfGenres = Integer.parseInt(tempLine);
                    for (int i = 0; i < numberOfGenres; i++) {
                        t.add(in.readLine());
                    }
                    Recording r3 = new Recording(title, artist, year, new TreeSet<>(t));
                    recordings.add(r3);
                    t.clear();
                    linesRead++;
                }
            }
        } catch (IOException ioe) {
            throw new FileNotFoundException("Filen fanns inte");
        }
    }

    public Map<Integer, Double> importSales(String fileName) throws FileNotFoundException {
        Map<Integer, Double> finalMap = new HashMap<>();
        final int hashOp = 100;
        try (DataInputStream in = new DataInputStream(new FileInputStream(fileName))) {
            int year;
            int month;
            double price;
            int posts = in.readInt();
            for (int i = 0; i < posts; i++) {
                year = in.readInt();
                month = in.readInt();
                in.readInt();
                price = in.readDouble();
                try {
                    double o = finalMap.get((year * hashOp) + month);
                    finalMap.put((year * hashOp) + month, price + o);
                }
                catch (Exception e) {
                    finalMap.put((year * hashOp) + month, price);
                }
            }
        } catch (FileNotFoundException ioe) {
            throw new FileNotFoundException("Filen fanns inte");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return finalMap;
    }

    public List<Recording> getRecordings() {
        return Collections.unmodifiableList(recordings);
    }

    public void setRecordings(List<Recording> recordings) {
        this.recordings.clear();
        this.recordings.addAll(recordings);
    }
}

