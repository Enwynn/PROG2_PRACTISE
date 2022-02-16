import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.*;
import java.util.function.Predicate;

public class Exercise5 extends Application {

	private final Database db = new Database();
	private final ObservableList<Recording> recordings = FXCollections.observableArrayList(db.getRecordings());
	private final FilteredList<Recording> filteredList = new FilteredList<>(recordings, predicate -> true);

	private final GridPane filters = new GridPane();
	private final TitledPane filterPane = new TitledPane("Filter", filters);

	private final Spinner<Integer> minYearSpinner = new Spinner<>();

	private final Spinner<Integer> maxYearSpinner = new Spinner<>();

	private final ListView<String> genreListView = new ListView<>(FXCollections.observableList(db.getGenres()));

	private final TextField artistFilterField = new TextField();

	private final TextField titleFilterField = new TextField();

	private final ToggleGroup toggle = new ToggleGroup();
	private final RadioButton rbCD = new RadioButton("CD");
	private final RadioButton rbLP = new RadioButton("LP");
	private final RadioButton rbBoth = new RadioButton("Both");

	private final ObservableSet<String> selectedGenres = FXCollections.observableSet();

	private final Predicate<Recording> yearFilter = new Predicate<>() {
		@Override
		public boolean test(Recording recording) {
			return recording.getYear() >= minYearSpinner.getValue() && recording.getYear() <= maxYearSpinner.getValue();
		}
	};

	private final Predicate<Recording> typeFilter = new Predicate<>() {
		@Override
		public boolean test(Recording recording) {
			var selected = toggle.getSelectedToggle();

			if (selected == null)
				return true;
			else if (selected.equals(rbCD))
				return recording.getType().equals("CD");
			else if (selected.equals(rbLP))
				return recording.getType().equals("LP");
			else
				return true;
		}
	};

	private final Predicate<Recording> titleFilter = new Predicate<>() {

		@Override
		public boolean test(Recording recording) {
			var value = titleFilterField.getText();
			int valueLength = value.length();

			if (value == null || value.isEmpty()) {
				return true;
			}
			try {
				String tempTry = recording.getTitle().substring(0, valueLength);
			}

			catch(StringIndexOutOfBoundsException e) {
				return recording.getTitle().contains(value);
			}

			return recording.getTitle().toLowerCase().substring(0, valueLength).contains(value.toLowerCase());
		}
	};

	private final Predicate<Recording> artistFilter = new Predicate<>() {
		@Override
		public boolean test(Recording recording) {

			var value = artistFilterField.getText();
			int valueLength = value.length();

			if (value == null || value.isEmpty()) {
				return true;
			}
			try {
				String tempTry = recording.getArtist().substring(0, valueLength);
			}
			catch(StringIndexOutOfBoundsException e) {
				return recording.getArtist().contains(value);
			}
			return recording.getArtist().toLowerCase().substring(0, valueLength).contains(value.toLowerCase());
		}
	};

	private final Predicate<Recording> genreFilter = new Predicate<>() {
		@Override
		public boolean test(Recording recording) {
			if (selectedGenres.isEmpty())
				return true;
			for (String selectedGenre : selectedGenres) {
				if (recording.getGenre().contains(selectedGenre))
					return true;
			}
			return false;
		}
	};

	@Override
	public void start(Stage primaryStage) {

		rbCD.setId("rbCD");
		rbLP.setId("rbLP");
		rbBoth.setId("rbBoth");

		artistFilterField.textProperty().addListener((observable, oldValue, newValue) -> updateFilters());
		artistFilterField.setId("artistField");

		titleFilterField.textProperty().addListener((observable, oldValue, newValue) -> updateFilters());
		titleFilterField.setId("titleField");

		minYearSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1950, 2020, 1950));
		minYearSpinner.valueProperty().addListener((observableValue, oldValue, newValue) -> {

			if (minYearSpinner.getValue() > (maxYearSpinner.getValue())) {
				maxYearSpinner.getValueFactory().setValue(maxYearSpinner.getValue() + 1);
			}
			updateFilters();
		});
		minYearSpinner.setId("minSpinner");

		maxYearSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1950, 2020, 2020));
		maxYearSpinner.valueProperty().addListener((observableValue, oldValue, newValue) -> {

			if (minYearSpinner.getValue() > (maxYearSpinner.getValue())) {
				minYearSpinner.getValueFactory().setValue(minYearSpinner.getValue() - 1);
			}

			updateFilters();
		});
		maxYearSpinner.setId("maxSpinner");

		Button resetButton = new Button("Reset filters");
		resetButton.setId("resetButton");
		resetButton.setOnAction(event -> {
			filteredList.setPredicate(null);
			artistFilterField.clear();
			titleFilterField.clear();
			toggle.selectToggle(rbBoth);
			minYearSpinner.getValueFactory().setValue(1950);
			maxYearSpinner.getValueFactory().setValue(2020);
			filterPane.setExpanded(false);
			selectedGenres.clear();
			genreListView.getSelectionModel().clearSelection();
		});

		toggle.getToggles().addAll(rbCD, rbLP, rbBoth);
		toggle.selectedToggleProperty().addListener((observable, oldValue, newValue) -> updateFilters());
		toggle.selectToggle(rbBoth);
		rbBoth.setSelected(true);

		genreListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		genreListView.getSelectionModel().getSelectedItems().addListener((ListChangeListener<String>) c -> {
			selectedGenres.clear();
			selectedGenres.addAll(genreListView.getSelectionModel().getSelectedItems());
			updateFilters();
		});

		MyVBox typeBox = new MyVBox(new Label("Type filter:"), new HBox(20, rbCD, rbLP, rbBoth));
		MyVBox yearBox = new MyVBox(new Label("Year filter:"), new HBox(new MyVBox(new Label("From:"), new Label("To:")), new VBox(new MyVBox(minYearSpinner), new MyVBox(maxYearSpinner)) ));
		MyVBox artistBox = new MyVBox(new Label("Artist filter:"), artistFilterField);
		MyVBox titleBox = new MyVBox(new Label("Title filter:"), titleFilterField);
		MyVBox genreBox = new MyVBox(new Label("Genre filter:"), genreListView);

		filters.add(artistBox, 0, 0);
		filters.add(titleBox, 0, 1);
		filters.add(typeBox, 0, 2);
		filters.add(yearBox, 0, 3);
		filters.add(resetButton, 0, 4);
		filters.add(genreBox, 1, 0, 2, 5);

		filters.setPadding(new Insets(10));
		filters.setHgap(20);
		filters.setVgap(20);

		filterPane.setExpanded(false);
		filterPane.setId("filters");

		Label title = new Label("Record Collection");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 36));
		FlowPane fp = new FlowPane(title);
		fp.setAlignment(Pos.CENTER);

		BorderPane bp = new BorderPane();
		bp.setTop(fp);
		bp.setCenter(tableViewCreator());
		bp.setBottom(filterPane);

		Scene scene = new Scene(bp);
		primaryStage.setWidth(1200);
		primaryStage.setHeight(800);
		primaryStage.setTitle("Record Collection");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private TableView tableViewCreator() {
		TableView<Recording> tv = new TableView();
		tv.setId("table");
		tv.setItems(filteredList);
		tv.getColumns().addAll(tableColumns());
		return tv;
	}

	private List tableColumns() {
		// Add all below to list.
		List<TableColumn> tvList = new ArrayList<>();
		TableColumn<Recording, String> artTable = new TableColumn<>("Artist");
		artTable.setCellValueFactory(new PropertyValueFactory<>("artist"));

		TableColumn<Recording, String> titleTable = new TableColumn<>("Title");
		titleTable.setCellValueFactory(new PropertyValueFactory<>("title"));

		TableColumn<Recording, String> yearTable = new TableColumn<>("Year");
		yearTable.setCellValueFactory(new PropertyValueFactory<>("year"));

		TableColumn<Recording, String> typeTable = new TableColumn<>("Type");
		typeTable.setCellValueFactory(new PropertyValueFactory<>("type"));

		TableColumn<Recording, Set<String>> genreTable = new TableColumn<>("Genre(s)");
		genreTable.setCellValueFactory(new PropertyValueFactory<>("genre"));

		tvList.add(artTable);
		tvList.add(titleTable);
		tvList.add(yearTable);
		tvList.add(typeTable);
		tvList.add(genreTable);

		return tvList;
	}

	private void updateFilters() {
		filteredList.setPredicate(artistFilter.and(yearFilter).and(typeFilter).and(genreFilter).and(titleFilter));
	}

	static class MyVBox extends VBox {

		MyVBox(Node... items) {
			super(items);
			setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
			setPadding(new Insets(10));
			setSpacing(20);
			setAlignment(Pos.CENTER);
		}
	}
}