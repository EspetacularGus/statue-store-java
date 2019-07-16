package com.statuestore.controle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.statuestore.bd.DataSource;
import com.statuestore.helpers.Dialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.sql.SQLException;

public class ControllerCadastroAnunc implements Initializable {

    //Dados Pessoais
    public JFXTextField txtNome; //*
    public JFXTextField txtSobrenome; //*
    public JFXTextField txtEmail; //*
    public JFXTextField txtCPF; //*
    public JFXTextField txtNascimento; //*
    public JFXTextField txtTelefone2;
    public JFXTextField txtTelefone; //*
    public JFXPasswordField txtSenha; //*
    public JFXPasswordField txtConfSenha; //*

    //Endereço
    public JFXTextField txtCEP; //*
    public JFXTextField txtComplemento;
    public JFXTextField txtNumero; //*
    public JFXTextField txtBairro; //*
    public JFXTextField txtEndereco; //*
    public JFXTextField txtCidade; //*
    public JFXComboBox<String> cmbPais; //*
    public JFXComboBox<String> cmbEstado; //*

    @FXML private StackPane stackPane;
    @FXML private Pane pnlEndereco;
    @FXML private Pane pnlDadosPessoais;
    @FXML private Pane pnlSucesso;
    @FXML private  JFXButton btnContinuar;
    @FXML private JFXButton btnLimpar;
    @FXML private ImageView icnBack;

    private DataSource ds = new DataSource();

    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle rb) {
        ObservableList<String> paisesList = FXCollections.observableArrayList(
                "Brasil", "Uruguai", "Argentina");
        cmbPais.setItems(paisesList);

        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Campo obrigatório!");

        btnContinuar.setDisable(true);
        validateField(txtNome, validator);
        validateField(txtSobrenome, validator);
        validateField(txtCPF, validator);
        validateField(txtEmail, validator);
        validateField(txtNascimento, validator);
        validateField(txtTelefone, validator);
        validateField(txtSenha, validator);
        validateField(txtConfSenha, validator);
        validateField(txtCEP, validator);
        validateField(txtBairro, validator);
        validateField(txtNumero, validator);
        validateField(txtEndereco, validator);
        validateField(txtCidade, validator);
        validateField(cmbEstado, validator);
        validateField(cmbPais, validator);

        ObservableList<String> estadList = FXCollections.observableArrayList(
                "Acre", "Alagoas", "Amapá", "Amazonas",
                "Bahia", "Ceará", "Distrito Federal", "Espírito Santo",
                "Goiás", "Maranhão", "Mato Grosso", "Mato Grosso do Sul",
                "Minas Gerais", "Pará", "Paraíba", "Paraná", "Pernambuco",
                "Piauí", "Rio de Janeiro", "Rio Grande do Norte",
                "Rio Grande do Sul", "Rondônia", "Roraima",
                "Santa Catarina", "São Paulo", "Sergipe", "Tocantins");
        cmbEstado.setItems(estadList);
    }

    @FXML
    private void btnContinuarClicked() {
        btnContinuar.setDisable(true);
        if(pnlDadosPessoais.isVisible()) {
            pnlDadosPessoais.setVisible(false);
            pnlEndereco.setVisible(true);
        } else if(pnlEndereco.isVisible()) {
            if (cadastrarAnunc()) {
                pnlEndereco.setVisible(false);
                pnlSucesso.setVisible(true);
                btnLimpar.setVisible(false);
                icnBack.setVisible(false);
                btnContinuar.setText("Voltar");
                btnContinuar.setDisable(false);
            }
        } else {
            pnlSucesso.getScene().getWindow().hide();
        }
    }

    @FXML
    private void icnBackClicked() {
        btnContinuar.setDisable(false);
        if(pnlDadosPessoais.isVisible()) {
            pnlDadosPessoais.getScene().getWindow().hide();
        } else if(pnlEndereco.isVisible()) {
            pnlEndereco.setVisible(false);
            pnlDadosPessoais.setVisible(true);
        }
    }

    private boolean cadastrarAnunc() {
        try {
            ds.newCon();
            ds.setStatement("EXECUTE cadastrarAnunciante ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?;");

            ds.getStatement().setString(1, txtEmail.getText());
            ds.getStatement().setString(2, txtConfSenha.getText());
            ds.getStatement().setString(3, txtNome.getText());
            ds.getStatement().setString(4, txtSobrenome.getText());
            ds.getStatement().setString(5, txtCPF.getText()
                    .replace(".", "")
                    .replace("-", "")
                    .replace("/", ""));
            ds.getStatement().setString(6, txtNascimento.getText()
                    .replace("/","-")
                    .replace(".","-"));
            ds.getStatement().setString(7, txtTelefone.getText()
                    .replace("(", "")
                    .replace(")", "")
                    .replace("-",""));
            ds.getStatement().setString(8, txtTelefone2.getText()
                    .replace("(", "")
                    .replace(")", "")
                    .replace("-",""));
            ds.getStatement().setString(9, txtCEP.getText()
                    .replace(".", "")
                    .replace("-", "")
                    .replace("/", ""));
            ds.getStatement().setString(10, cmbPais.getValue());
            ds.getStatement().setString(11, cmbEstado.getValue());
            ds.getStatement().setString(12, txtCidade.getText());
            ds.getStatement().setString(13, txtBairro.getText());
            ds.getStatement().setString(14, txtEndereco.getText());
            ds.getStatement().setInt(15, Integer.parseInt(txtNumero.getText()));
            ds.getStatement().execute();
            ds.quit();

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            ds.quit();
            Dialog.showDialog(stackPane, "Campos Incompletos",
                    "Para continuar, é necessário preencher \n todos os campos corretamente");
            return false;
        }
    }


    private void validateField(JFXTextField txt, RequiredFieldValidator validator) {
        txt.getValidators().addAll(validator);
        txt.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal)
                if (!txt.validate())
                    btnContinuar.setDisable(true);
                else
                    btnContinuar.setDisable(false);
        });
    }

    private void validateField(JFXPasswordField txt, RequiredFieldValidator validator) {
        txt.getValidators().addAll(validator);
        txt.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal)
                if (!txt.validate())
                    btnContinuar.setDisable(true);
                else
                    btnContinuar.setDisable(false);
        });
    }

    private void validateField(JFXComboBox cmb, RequiredFieldValidator validator) {
        cmb.getValidators().add(validator);
        cmb.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal)
                if (!cmb.validate())
                    btnContinuar.setDisable(true);
                else
                    btnContinuar.setDisable(false);
        });
    }
}
