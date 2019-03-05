package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static model.ControllerTable.csvFile;

public class ControllerFilter implements Initializable {

    @FXML
    private TableView<Pacient> tablePacients;

    @FXML
    private TextField tel_filter;
    @FXML
    private CheckBox genereAltre_filter, genereHome_filter, genereDona_filter;
    @FXML
    private TextField pes_filter, altura_filter;
    @FXML
    private TextField data_filter;
    @FXML
    private TextField cognom_filter, dni_filter, nom_filter;

    private List<Pacient> p = new ArrayList<>();
    private ObservableList<Pacient> data;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data = FXCollections.observableArrayList();
        if (csvFile == null) {
            System.out.println("csv vacio");
        } else {
            setTableView();
        }

    }
    private void setTableView() {
        //data.clear();
        TableColumn DNI = new TableColumn("DNI");
        TableColumn Nom = new TableColumn("Nom");
        TableColumn Cognoms = new TableColumn("Cognoms");
        TableColumn DataNaix = new TableColumn("Data de Naixament");
        TableColumn Genre = new TableColumn("Gènere");
        TableColumn Telefon = new TableColumn("Telèfon");
        TableColumn pes = new TableColumn("Pes");
        TableColumn Alçada = new TableColumn("Alçada");

        // COMPTE!!!! les propietats han de tenir getters i setters
        DNI.setCellValueFactory(new PropertyValueFactory<Pacient, String>("DNI"));
        Nom.setCellValueFactory(new PropertyValueFactory<Pacient, String>("Nom"));
        Cognoms.setCellValueFactory(new PropertyValueFactory<Pacient, String>("Cognoms"));
        DataNaix.setCellValueFactory(new PropertyValueFactory<Pacient, String>("DataNaixament"));
        Genre.setCellValueFactory(new PropertyValueFactory<Pacient, String>("genere"));
        Telefon.setCellValueFactory(new PropertyValueFactory<Pacient, String>("Telefon"));
        pes.setCellValueFactory(new PropertyValueFactory<Pacient, Float>("Pes"));
        Alçada.setCellValueFactory(new PropertyValueFactory<Pacient, Integer>("Alçada"));

        tablePacients.getColumns().addAll(DNI, Nom, Cognoms, DataNaix, Genre, Telefon, pes, Alçada);


        //data.add(new Pacient("111", "n", "co", LocalDate.of(2000, 12, 12), Persona.Genere.HOME, "55555", 5.4f, 100));
        loadData();
        data.addAll(p);
        tablePacients.setItems(data);

    }
    private void loadData() {
        Hospital hospital = new Hospital();
        p.addAll(hospital.loadPacients(csvFile));
    }


    public void btnCerca(ActionEvent event) {
        List<Pacient> pacients = p.stream()
                .filter(pacient -> pacient.getDNI().equals(dni_filter.getText()))
                .collect(Collectors.toList());
        if (dni_filter.getText().equals("")) {
            updateTable(p);
        } else updateTable(pacients);
    }

    private void updateTable(List<Pacient> pacients) {
        data.clear();
        data.addAll(pacients);
        tablePacients.setItems(data);
    }

    public void changeText(KeyEvent keyEvent) {
        data.clear();
        List<Pacient> pacients = p.stream()
                .filter(pacient -> pacient.getNom().contains(nom_filter.getText()))
                .filter((pacient -> pacient.getCognoms().contains(cognom_filter.getText())))
                .filter((pacient -> pacient.getDNI().contains( dni_filter.getCharacters().toString())))
                .collect(Collectors.toList());
        data.addAll(pacients);

    }

    public void clickTable(MouseEvent event) {
        //Cal verificar si hi ha alguna selecció feta al fer doble click
        if (event.getClickCount() == 2 && !tablePacients.getSelectionModel().isEmpty()) {
            System.out.println(tablePacients.getSelectionModel().getSelectedItem().getNom());
        }
    }
}
