package ohm.softa.a07.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import ohm.softa.a07.api.OpenMensaAPI;
import ohm.softa.a07.model.Meal;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainController implements Initializable {

	HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
		loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

	OkHttpClient client = new OkHttpClient.Builder()
		.addInterceptor(loggingInterceptor)
		.build();

	Retrofit retrofit = new Retrofit.Builder()
		.addConverterFactory(GsonConverterFactory.create())
		.baseUrl("https://openmensa.org/api/v2/")
		.client(client)
		.build();

	openMensaAPI = retrofit.create(OpenMensaAPI .class);
	// use annotation to tie to component in XML
	@FXML
	private Button btnRefresh;

	@FXML
	private Button btnClose;
	@FXML
	private ListView<String> mealsList;

	@FXML
	private ListView<Meal> meals;

	@FXML
	private CheckBox chkVegetarian;

	@FXML
	private ObservableList<>
	private OpenMensaAPI openMensaAPI;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// set the event handler (callback)
		btnRefresh.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// create a new (observable) list and tie it to the view
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
				String today = sdf.format(new Date());
				Call<List<Meal>> call = openMensaAPI.getMeals(today);


				ObservableList<String> list = FXCollections.observableArrayList("Hans", "Dampf");
				mealsList.setItems(list);
			}
		});

		btnClose.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Stage stage = (Stage) btnClose.getScene().getWindow();
				stage.close();
			}
		});
	}
}
