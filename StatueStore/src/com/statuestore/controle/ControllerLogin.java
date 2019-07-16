package com.statuestore.controle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import com.statuestore.helpers.Stages;
import com.statuestore.modelo.Funcionario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import com.statuestore.bd.DataSource;
import com.statuestore.modelo.LoggedAnunciante;
import com.statuestore.helpers.Dialog;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ControllerLogin implements Initializable {

    private Stages stg = new Stages();
    private DataSource ds = new DataSource();

    //Template
    public Pane pnlLogin;
    public StackPane stackPane;
    public JFXTextField txtCPF;
    public JFXPasswordField txtSenha;
    @FXML
    private JFXButton btnEntrarAnunc;
    @FXML
    private JFXButton btnEntrarFunc;

    //Tela inicial
    public Pane pnlEntrar;

    //Login Funcionário
    public Pane pnlFunc;

    //Login LoggedAnunciante
    public Pane pnlAnunc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Campo obrigatório!");

        validateField(txtCPF, validator);
        validateField(txtSenha, validator);
    }

    @FXML
    private void btnFuncClicked() {
        pnlEntrar.setVisible(false);
        pnlFunc.setVisible(true);
    }

    @FXML
    private void btnAnuncClicked() {
        pnlEntrar.setVisible(false);
        pnlAnunc.setVisible(true);
    }

    @FXML
    private void btnVoltarClicked() {
        pnlFunc.setVisible(false);
        pnlAnunc.setVisible(false);
        pnlEntrar.setVisible(true);
        txtCPF.setText("");
        txtSenha.setText("");
    }

    @FXML
    private void btnEntrarFuncClicked() {
        try {
            if (autenticarFuncionario().next()) {
                int nivel = ds.getResultSet().getInt("idNivelAcesso");
                if (nivel >= 3) {
                    int id = ds.getResultSet().getInt("idFuncionario");
                    new Funcionario(id);

                    pnlEntrar.getScene().getWindow().hide();
                    ds.quit();
                    new Stages("com/statuestore/view/MenuFunc.fxml", "");
                } else {
                    Dialog.showDialog(stackPane, "Acesso não permitido:",
                            "Você não tem permissão para acessar \n essa àrea do sistema.");
                }
            } else {
                Dialog.showDialog(stackPane, "Falha de Autenticação:",
                        "CPF e/ou Senha incorretos.");
            }
        } catch (Exception e) {
            Dialog.showDialog(stackPane, "Erro de conexão:",
                    "Erro ao se conectar com o servidor.");
        }
    }

    @FXML
    private void btnEntrarAnuncClicked() {
        try {
            if (autenticarAnunciante().next()) {
                int id = ds.getResultSet().getInt("idAnunciante");
                new LoggedAnunciante(id);

                pnlEntrar.getScene().getWindow().hide();
                Stages stg = new Stages("com/statuestore/view/MenuAnunc.fxml", "");
                stg.getStage().setOnCloseRequest(event -> System.exit(0));
            } else {
                Dialog.showDialog(stackPane, "Falha de Autenticação:",
                        "CPF e/ou Senha incorretos");
            }
        } catch (Exception e) {
            Dialog.showDialog(stackPane, "Erro de conexão:",
                    "Erro ao se conectar com o servidor.");
        }
    }

    @FXML
    private void lnkCadastrarClicked() {
        if (!stg.isOpen())
            stg.openStage("com/statuestore/view/CadastroAnunc.fxml", "");
        else
            stg.getStage().requestFocus();
    }

    private ResultSet autenticarAnunciante() {
        try {
            ds.newCon();
            ds.setStatement("EXECUTE autenticarAnunciante ?, ?");
            ds.getStatement().setString(1, txtCPF.getText()
                    .replace(".","")
                    .replace("-","")
                    .replace("/",""));
            ds.getStatement().setString(2, txtSenha.getText());
            ds.setResultSet(ds.getStatement());
            ds.quit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ds.getResultSet();
    }

    private ResultSet autenticarFuncionario() {
        try {
            ds.newCon();
            ds.setStatement("EXECUTE autenticarFuncionario ?, ?");
            ds.getStatement().setString(1, txtCPF.getText()
                    .replace(".","")
                    .replace("-","")
                    .replace("/",""));
            ds.getStatement().setString(2, txtSenha.getText());
            ds.setResultSet(ds.getStatement());
            ds.quit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ds.getResultSet();
    }

    private void validateField(JFXTextField txt, RequiredFieldValidator validator) {
        txt.getValidators().addAll(validator);
        txt.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal)
                if (!txt.validate()) {
                    btnEntrarAnunc.setDisable(true);
                    btnEntrarFunc.setDisable(true);
                } else {
                    btnEntrarAnunc.setDisable(false);
                    btnEntrarFunc.setDisable(false);
                }
        });
    }

    private void validateField(JFXPasswordField txt, RequiredFieldValidator validator) {
        txt.getValidators().addAll(validator);
        txt.focusedProperty().addListener((o, oldVal, newVal) -> {
            if (!newVal)
                if (!txt.validate()) {
                    btnEntrarAnunc.setDisable(true);
                    btnEntrarFunc.setDisable(true);
                } else {
                    btnEntrarAnunc.setDisable(false);
                    btnEntrarFunc.setDisable(false);
                }
        });
    }
}
