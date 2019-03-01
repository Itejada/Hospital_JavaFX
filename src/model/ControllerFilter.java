//package model;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.Initializable;
//import javafx.scene.input.KeyEvent;
//
//import java.net.URL;
//import java.util.List;
//import java.util.ResourceBundle;
//import java.util.stream.Collectors;
//
//public class ControllerFilter implements Initializable {
//
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//
//    }
//
//
//    public void btnCerca(ActionEvent event) {
//        List<Pacient> pacients = p.stream()
//                .filter(pacient -> pacient.getDNI().equals(txtDNI.getText()))
//                .collect(Collectors.toList());
//        if(txtDNI.getText().equals("")) {
//            updateTable(p);
//        }else updateTable(pacients);
//    }
//
//    private void updateTable(List<Pacient> pacients) {
//        data.clear();
//        data.addAll(pacients);
//        tablePacients.setItems(data);
//    }
//
//    public void changeText(KeyEvent keyEvent) {
//        data.clear();
//        List<Pacient> pacients = p.stream()
//                .filter(pacient -> pacient.getNom().contains(txtNom.getText()))
//                .filter((pacient -> pacient.getCognoms().contains(txtCognoms.getText())))
//                .collect(Collectors.toList());
//        data.addAll(pacients);
//
//    }
