package model;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Controller  implements Initializable {

    @FXML
    private JFXButton btnTeacher;
    @FXML
    private JFXButton btnStuden;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private JFXDrawer drawer;




    public void setAnchorPane(AnchorPane anchorPane) {

        // this.anchorPane.getParent();
        this.anchorPane.getChildren().clear();
        this.anchorPane.getChildren().add(anchorPane);

        anchorPane.getId();
        if (anchorPane.getId().equals("table")) {
            anchorPane.setBackground(setBackgroundColor("#8AB3D4"));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            VBox box = FXMLLoader.load(getClass().getResource("\\..\\sample\\drawer_menu.fxml"));
          //   box.setBackground(setBackgroundColor("50F556"));
            drawer.setSidePane(box);
            drawer.setVisible(true);

            for (Node node : box.getChildren()) {
                if (node.getId() != null) {

                    node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                        switch (node.getId()) {

                            case "boton1":
                                setAnchorPane(getSample("sample"));
                                anchorPane.setBackground(setBackgroundColor("#4CAF50"));
                                break;

                            case "boton2":
                                setAnchorPane(getSample("table"));
                                anchorPane.setBackground(setBackgroundColor("#5CAFF0"));
                                break;

                            case "boton3":
                                setAnchorPane(getSample("llista_profe"));
                                anchorPane.setBackground(setBackgroundColor("#4CAFF0"));
                                break;

                            case "boton4":
                                anchorPane.setBackground(setBackgroundColor(randomColor().toString()));
                                break;
                            case "boton5":
                                setAnchorPane(getSample("filter"));
                                anchorPane.setBackground(setBackgroundColor("#4CAFF0"));
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
        transicion(hamburger,drawer);
    }

    public AnchorPane getSample(String sample) {
        try {
            return FXMLLoader.load(getClass().getResource("\\..\\sample\\" + sample + ".fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public Background setBackgroundColor(String codeColor) {
        return new Background(new BackgroundFill(Paint.valueOf(codeColor), CornerRadii.EMPTY, Insets.EMPTY));
    }

    public void transicion( JFXHamburger _hamburger, JFXDrawer _drawer){

        HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(_hamburger);
        transition.setRate(-1);
        _hamburger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            transition.setRate(transition.getRate() * -1);
            transition.play();
            if (_drawer.isOpened()) {
                _drawer.close();
            } else {
                _drawer.open();
            }
        });
    }

    @FXML
    void clickTeacher(MouseEvent event) throws IOException {
        VBox box = FXMLLoader.load(getClass().getResource("\\..\\sample\\drawer_menu.fxml"));
        //   box.setBackground(setBackgroundColor("50F556"));
        drawer.setSidePane(box);
        drawer.setVisible(false);
        setAnchorPane(getSample("llista_profe"));
    }

    @FXML
    void clickStuden(MouseEvent  event) throws IOException{
        VBox box = FXMLLoader.load(getClass().getResource("\\..\\sample\\drawer_menu.fxml"));
        //   box.setBackground(setBackgroundColor("50F556"));
        drawer.setSidePane(box);
        drawer.setVisible(false);
       setAnchorPane(getSample("table"));

    }
    public Paint randomColor() {
        /*Random color fuente
        https://www.quora.com/How-can-I-randomize-colors-in-Java */
        Random random = new Random();
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);
        return Color.rgb(r, g, b);
    }
}

