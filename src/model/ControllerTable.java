package model;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.scene.input.MouseEvent.MOUSE_CLICKED;

public class ControllerTable implements Initializable {
    boolean tablaCreada= false;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private TableView<Pacient> tablePacients;

    protected static String csvFile = null;
    private List<Pacient> p = new ArrayList<>();
    private ObservableList<Pacient> data;
    Controller controller = new Controller();

    @FXML
    private JFXDrawer drawer;
    @FXML
    private PieChart idPieChart;
    @FXML
    private PieChart PieChart2;
    @FXML
    private JFXHamburger hamburger;
    @FXML
    private JFXButton btnLoadFile;


    public void setAnchorPane(AnchorPane anchorPane) {

        // this.anchorPane.getParent();
        this.anchorPane.getChildren().clear();
        this.anchorPane.getChildren().add(anchorPane);
        anchorPane.getId();
        if (anchorPane.getId().equals("table")) {
            anchorPane.setBackground(controller.setBackgroundColor("#ffffff"));
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        data = FXCollections.observableArrayList();
        if (csvFile == null) {
            btnLoadFile.setText("Carregar CSV");
        } else {
            setTableView();
        }
        data = FXCollections.observableArrayList();
        if (csvFile == null) {
            btnLoadFile.setText("Carregar CSV");
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

                    node.addEventHandler(MOUSE_CLICKED, (e) -> {
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

    @FXML
    void clickLoadFile(MouseEvent event) {
        if (csvFile == null || event.getClickCount() == 2) {
            FileChooser fc = new FileChooser();
            fc.setTitle("Select csv file");
            File file = fc.showOpenDialog(null);
            csvFile = file.getAbsolutePath();
            setTableView();
            btnLoadFile.setText("Loaded");
            setChart();
        } else {
            btnLoadFile.setText("File is loaded");
        }
    }

    private void setTableView() {
        if (!tablaCreada) {
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
            tablaCreada=true;
        tablePacients.getColumns().addAll(DNI, Nom, Cognoms, DataNaix, Genre, Telefon, pes, Alçada);

    }else{
        }
        //data.add(new Pacient("111", "n", "co", LocalDate.of(2000, 12, 12), Persona.Genere.HOME, "55555", 5.4f, 100));
        loadData();
        data.addAll(p);
        tablePacients.setItems(data);


    }

    private void loadData() {
        Hospital hospital = new Hospital();
        p.clear();
        p.addAll(hospital.loadPacients(csvFile));
    }


    @FXML
    void btnChart(ActionEvent event) {
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
        /*                  ----------------------------------------------------------                                          */
        PieChart2.getData().clear();
        ArrayList<String> edades = new ArrayList<String>();
        p.forEach(pacient -> edades.add(calcularEdad(pacient.getDataNaixament())));

        long edad;
        //  edad=p.stream().filter(pacient -> calcularEdad(pacient.getDataNaixament()) == i).count();

        PieChart2.setTitle("Edats");
        for (int i = 0; i < 100; i++) {
            edad = Collections.frequency(edades, Integer.toString(i));
            if (edad != 0) {
                PieChart2.getData().add(new PieChart.Data("" + i, edad));
            }

        }

    }


    public String calcularEdad(LocalDate data) {
        /* Fuente
        https://es.stackoverflow.com/questions/13777/obtener-edad-a-partir-de-la-fecha-de-nacimiento-en-java*/
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dataPacient = data.toString();

        LocalDate fechaNac = LocalDate.parse(dataPacient, fmt);
        LocalDate ahora = LocalDate.parse(LocalDate.now().format(fmt));
        Period periodo = Period.between(fechaNac, ahora);

        return Integer.toString(periodo.getYears());
    }

    public TableView<Pacient> getTablePacients() {
        return tablePacients;
    }

    public ObservableList<Pacient> getData() {
        return data;
    }
}

