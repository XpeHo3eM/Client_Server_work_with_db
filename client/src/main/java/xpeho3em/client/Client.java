package xpeho3em.client;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import xpeho3em.client.model.Contract;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Client extends Application {
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final List<Contract> contracts = new ArrayList<>();


    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        getContracts();

        Pane root = new FlowPane(10, 10, createTableView(contracts));
        Scene scene = new Scene(root, 325, 500);

        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("Договора");
        stage.show();
    }

    private void getContracts() {
        URI uri = URI.create("http://localhost:8080/contracts");
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(uri)
                .build();

        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка");
            throw new RuntimeException("Ошибка запроса");
        }

        if (response.statusCode() == 200) {
            JsonElement jsonElement = JsonParser.parseString(response.body());

            if (!jsonElement.isJsonArray()) {
                System.out.println("Ответ от сервера не список договоров");
                return;
            }

            final int DAYS_FOR_ACTUAL = 60;

            JsonArray jsonArray = jsonElement.getAsJsonArray();
            LocalDate minDateForActual = LocalDate.now().minusDays(DAYS_FOR_ACTUAL);

            jsonArray.forEach(el -> {
                JsonObject jsonObject = el.getAsJsonObject();
                Contract contract = Contract.builder()
                        .number(jsonObject.get("number").getAsString())
                        .signingDate(LocalDate.parse(jsonObject.get("signingDate").getAsString()))
                        .lastUpdate(minDateForActual.isAfter(LocalDate.parse(jsonObject.get("lastUpdate").getAsString())))
                        .build();

                contracts.add(contract);
            });
        }
    }
    private TableView<Contract> createTableView(List<Contract> contracts) {
        TableView<Contract> tableView = new TableView<>(FXCollections.observableArrayList(contracts));
        tableView.setPrefHeight(500);
        tableView.setPrefWidth(325);

        TableColumn<Contract, String> number = new TableColumn<>("Номер договора");
        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        tableView.getColumns().add(number);

        TableColumn<Contract, LocalDate> signingDate = new TableColumn<>("Дата подписания");
        signingDate.setCellValueFactory(new PropertyValueFactory<>("signingDate"));
        tableView.getColumns().add(signingDate);

        TableColumn<Contract, Boolean> lastUpdate = new TableColumn<>("Актуальность");
        lastUpdate.setCellValueFactory(c -> {
            Boolean actual = c.getValue().getLastUpdate();
            CheckBox checkbox = new CheckBox() {
                @Override
                public void arm() {
                }
            };

            checkbox.selectedProperty().setValue(actual);

            return new SimpleObjectProperty(checkbox);
        });
        tableView.getColumns().add(lastUpdate);

        return tableView;
    }
}