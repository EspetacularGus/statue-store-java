package com.statuestore.controle;

import com.jfoenix.controls.JFXComboBox;
import com.statuestore.helpers.Stages;
import com.statuestore.modelo.Anuncio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;

public abstract class ControllerMenu implements Initializable {

    Stages stg = new Stages();
    public int filtroAnuncio = 0;

    @FXML StackPane stackPane;
    @FXML public JFXComboBox<String> cmbFiltro;
    @FXML public TableView<Anuncio> viewAnunc;

    private void setOnRowClicked() {
        viewAnunc.setRowFactory(e -> {
            TableRow<Anuncio> row = new TableRow<>();
            row.setOnMouseClicked(e2 -> {

                ControllerInfoAnunc.setParentMenu(this);
                ControllerInfoAnunc.setRowAnuncio(row.getItem());
                ControllerInfoAnunc.setPosition(row.getIndex());

                if(e2.getClickCount() == 2 && (!row.isEmpty())) {
                    if (!stg.isOpen())
                        stg.openStage("com/statuestore/view/InfoAnuncio.fxml", "");
                    else
                        stg.getStage().requestFocus();
                }
            });

            return row;
        });
    }

    @Override @SuppressWarnings("unchecked")
    public void initialize(java.net.URL url, java.util.ResourceBundle rb) {
        cmbFiltro.setItems(FXCollections.observableArrayList(
                "Todos os Anúncios",
                "Anúncios Ativos",
                "Anúncios Pendentes",
                "Anúncios Inativos",
                "Anúncios Reprovados",
                "Pagamento Pendente"));
        cmbFiltro.valueProperty().addListener((o, oldVal, newVal) -> cmbValueChanged(newVal));

        initTable();
        fillTable();
        setOnRowClicked();
    }

    private void cmbValueChanged(String newVal) {
        switch (newVal) {
            case "Todos os Anúncios":
                filtroAnuncio = 0;
                break;
            case "Anúncios Ativos":
                filtroAnuncio = Anuncio.ANUNC_ATIVO;
                break;
            case "Anúncios Pendentes":
                filtroAnuncio = Anuncio.ANUNC_ANALISE;
                break;
            case "Anúncios Inativos":
                filtroAnuncio = Anuncio.ANUNC_INATIVO;
                break;
            case "Anúncios Reprovados":
                filtroAnuncio = Anuncio.ANUNC_REPROVADO;
                break;
            case "Pagamento Pendente":
                filtroAnuncio = Anuncio.ANUNC_AGUARD_PAG;
                break;
        }
        btnAtualizarClicked();
    }

    @FXML
    public void btnAtualizarClicked() {
        fillTable();
    }

    public abstract void fillTable();
    public abstract void initTable();
    public abstract void infoClicked();
}
