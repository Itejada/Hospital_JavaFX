package model;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class ControllerLlista implements Initializable {

    private String csvFile = null;
    private List<Pacient> p = new ArrayList<>();

    public ObservableList<Pacient> getData() {
        return data;
    }

    private ObservableList<Pacient> data;

    @FXML
    TableView<Pacient> tablePacients;
    @FXML
    JFXButton btnLoadFile;
    @FXML
    JFXTextField txtDNI, txtNom, txtCognoms;
    @FXML
    PieChart idPieChart;


    Controller controller = new Controller();
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;

    public void setAnchorPane(AnchorPane _anchorPane) {

        // this._anchorPane.getParent();
        this.anchorPane.getChildren().clear();
        this.anchorPane.getChildren().add(_anchorPane);
        drawer.open();
        _anchorPane.getId();
        if (_anchorPane.getId().equals("table")) {
            _anchorPane.setBackground(controller.setBackgroundColor("#ffffff"));

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data = FXCollections.observableArrayList();
        if (csvFile == null) {
            btnLoadFile.setText("Click per carregar CSV");
        } else {
            setTableView();
        }
        try {
            VBox box = FXMLLoader.load(getClass().getResource("\\..\\sample\\drawer_menu.fxml"));
            box.setBackground(controller.setBackgroundColor("50F556"));
            drawer.setSidePane(box);
            drawer.setVisible(false);

            for (Node node : box.getChildren()) {
                if (node.getId() != null) {

                    node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        switch (node.getId()) {

                            case "boton1":
                                setAnchorPane(controller.getSample("sample"));
                                anchorPane.setBackground(controller.setBackgroundColor("#4CAF50"));
                                break;

                            case "boton2":
                                setAnchorPane(controller.getSample("table"));
                                anchorPane.setBackground(controller.setBackgroundColor("#5CAFF0"));
                                break;

                            case "boton3":
                                setAnchorPane(controller.getSample("llista_profe"));
                                anchorPane.setBackground(controller.setBackgroundColor("#4CAFF0"));
                                break;

                            case "boton4":
                                anchorPane.setBackground(controller.setBackgroundColor(controller.randomColor().toString()));
                                break;
                            case "boton5":
                                setAnchorPane(controller.getSample("filter"));
                                anchorPane.setBackground(controller.setBackgroundColor("#4CAFF0"));
                                break;
                            case "exit":
                                System.exit(0);
                                break;
                        }
                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        controller.transicion(hamburger, drawer);
    }

    private void setTableView() {
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


    public void clickLoadFile(ActionEvent event) {
        if (csvFile == null) {
            FileChooser fc = new FileChooser();
            fc.setTitle("Select csv file");
            File file = fc.showOpenDialog(null);
            csvFile = file.getAbsolutePath();
            setTableView();
            btnLoadFile.setText("Loaded");
        } else {
            btnLoadFile.setText("File is loaded");
        }
    }

    public void btnCerca(ActionEvent event) {
        List<Pacient> pacients = p.stream()
                .filter(pacient -> pacient.getDNI().equals(txtDNI.getText()))
                .collect(Collectors.toList());
        if (txtDNI.getText().equals("")) {
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
                .filter(pacient -> pacient.getNom().contains(txtNom.getText()))
                .filter((pacient -> pacient.getCognoms().contains(txtCognoms.getText())))
                .collect(Collectors.toList());
        data.addAll(pacients);
        tablePacients.setItems(data);
    }

    public void clickTable(MouseEvent event) {
        //Cal verificar si hi ha alguna selecció feta al fer doble click
        if (event.getClickCount() == 2 && !tablePacients.getSelectionModel().isEmpty()) {
            System.out.println(tablePacients.getSelectionModel().getSelectedItem().getNom());
        }
    }

    /**
     * Exemple de PieChart
     * Quants pacients homes i quants pacients dones hi ha
     *
     * @param event
     */
    public void btnChart(ActionEvent event) {
        setChart();
    }

    public void loadChart(Event event) {
        setChart();
    }

    private void setChart() {
        idPieChart.getData().clear();
        long dones = p.stream()
                .filter(pacient -> pacient.getGenere() == Persona.Genere.DONA)
                .count();
        long homes = p.stream()
                .filter(pacient -> pacient.getGenere() == Persona.Genere.HOME)
                .count();
        idPieChart.setTitle("Gènere");
        idPieChart.getData().add(new PieChart.Data(Persona.Genere.DONA.toString(), dones));
        idPieChart.getData().add(new PieChart.Data(Persona.Genere.HOME.toString(), homes));
/*                    ----------------------------------------------------------                                     */


    }
}