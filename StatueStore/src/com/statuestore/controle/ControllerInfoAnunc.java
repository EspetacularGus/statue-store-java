package com.statuestore.controle;

import com.jfoenix.controls.JFXButton;
import com.statuestore.bd.DataSource;
import com.statuestore.helpers.Dialog;
import com.statuestore.modelo.Anunciante;
import com.statuestore.modelo.LoggedAnunciante;
import com.statuestore.modelo.Anuncio;
import com.statuestore.modelo.Funcionario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

public class ControllerInfoAnunc implements Initializable {

    private static ControllerMenu parentMenu;

    private static Anuncio rowAnuncio;
    private static int position;
    private boolean anuncLogged = Funcionario.getId() == 0;
    private DataSource ds = new DataSource();
    private int estadoAnunc = rowAnuncio.getIntEstado();
    private Anunciante anunciante = rowAnuncio.getAnunciante();

    @FXML
    private JFXButton btnFechar;
    @FXML
    private JFXButton btnDesabilitar;
    @FXML
    private JFXButton btnAceitar;
    @FXML
    private JFXButton btnRecusar;
    @FXML
    private JFXButton btnPagar;
    @FXML
    private JFXButton btnDeletar;

    @FXML
    private StackPane stackPane;
    @FXML
    private AnchorPane anchorPane;

    //Tab Anuncio
    @FXML
    private Label lblNomeAnunc;
    @FXML
    private Label lblMarca;
    @FXML
    private Label lblPagamento;
    @FXML
    private Label lblDataPedido;
    @FXML
    private Label lblDataValid;
    @FXML
    private Label lblTitulo;
    @FXML
    private Label lblSubtitulo;
    @FXML
    private Label lblDescricao;
    @FXML
    private Label lblLink;
    @FXML
    private Label lblImagem;
    @FXML
    private Label lblInativo;

    //Tab LoggedAnunciante
    @FXML
    private Tab tabAnunciante;
    @FXML
    private Label lblNomeAnunciante;
    @FXML
    private Label lblSobrenome;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblID;
    @FXML
    private Label lblTelefone1;
    @FXML
    private Label lblTelefone2;
    @FXML
    private Label lblEndereco;
    @FXML
    private Label lblCidade;
    @FXML
    private Label lblCpf;

    //Tab Estatisticas
    @FXML
    private LineChart graficoCliques;
    @FXML
    private Label lblCliquesRest;
    @FXML
    private Label lblCliquesComp;
    @FXML
    private Label lblCliquesTot;
    @FXML
    private Label lblCliquesSemana;
    @FXML
    private Label lblCliquesMes;
    @FXML
    private Label lblCliquesDiario;

    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle rb) {
        btnFechar.setOnAction(e -> btnFecharClicked());
        btnDesabilitar.setOnAction(e -> btnDesabilitarClicked());
        btnDeletar.setOnAction(e -> btnDeletarClicked());

        fillInfo();

        lblLink.setOnMouseClicked(e -> {
            Desktop d = Desktop.getDesktop();
            try {
                d.browse(new URI("http://" + lblLink.getText()));
            } catch (IOException | URISyntaxException ex) {
                ex.printStackTrace();
            }
        });

        lblImagem.setOnMouseClicked(e -> Dialog.showImageDialog(stackPane,
                "Imagem do Anúncio", lblImagem.getText()));

        if (anuncLogged) {
            btnDeletar.setVisible(true);

            switch (estadoAnunc) {
                case Anuncio.ANUNC_ATIVO:
                    btnDesabilitar.setVisible(true);
                    setDesabilitar();
                    break;
                case Anuncio.ANUNC_DESABILITADO:
                    btnDesabilitar.setVisible(true);
                    setHabilitar();
                    break;
                case Anuncio.ANUNC_AGUARD_PAG:
                    btnPagar.setOnAction(e -> btnPagarClicked());
                    btnPagar.setVisible(true);
                    break;
                case Anuncio.ANUNC_INATIVO:
                    lblInativo.setVisible(true);
                    break;
            }
        } else {
            switch (estadoAnunc) {
                case Anuncio.ANUNC_ANALISE:
                    btnAceitar.setOnAction(e -> btnAceitarClicked());
                    btnRecusar.setOnAction(e -> btnRecusarClicked());
                    btnAceitar.setVisible(true);
                    btnRecusar.setVisible(true);
                    break;
                case Anuncio.ANUNC_ATIVO:
                    setDesabilitar();
                    break;
                case Anuncio.ANUNC_INATIVO:
                case Anuncio.ANUNC_REPROVADO:
                case Anuncio.ANUNC_DESABILITADO:
                    setHabilitar();
                    break;
            }
        }
    }

    private void btnFecharClicked() {
        anchorPane.getScene().getWindow().hide();
        parentMenu.fillTable();
    }

    private void btnDesabilitarClicked() {
        if (btnDesabilitar.getText().equals("DESABILITAR ANÚNCIO")) {
            if (anuncLogged) {
                atualizaEstado(Anuncio.ANUNC_DESABILITADO);
                Dialog.showDialog(stackPane, "Anúncio desabilitado com sucesso!",
                        "O anúncio foi desabilitado.");
                setHabilitar();
            } else {
                atualizaEstado(Anuncio.ANUNC_INATIVO);
                Dialog.showDialog(stackPane, "Anúncio desativado com sucesso!",
                        "O anúncio agora está inativo para o usuário.");
                setHabilitar();
            }
        } else if (btnDesabilitar.getText().equals("HABILITAR ANÚNCIO")) {
            atualizaEstado(Anuncio.ANUNC_ATIVO);

            Dialog.showDialog(stackPane, "Anúncio habilitado com sucesso!",
                    "O anúncio está ativo e passará a aparecer em nossa plataforma.");
            setDesabilitar();
        }
    }

    private void btnAceitarClicked() {
        atualizaEstado(Anuncio.ANUNC_ATIVO);
        Dialog.showDialog(stackPane, "Tem certeza que deseja aceitar o anúncio?",
                "O anúncio será aprovado.");

        try {
            ds.newCon();
            ds.setStatement("UPDATE Detalhe_Anuncio SET dataValid = GETDATE() WHERE idAnuncio = ?");
            ds.getStatement().setInt(1, rowAnuncio.getId());
            ds.getStatement().executeUpdate();
            ds.quit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        btnAceitar.setVisible(false);
        btnRecusar.setVisible(false);
        setDesabilitar();
        //parentMenu.viewAnunc.getItems().remove(rowAnuncio);
    }

    private void btnRecusarClicked() {
        atualizaEstado(Anuncio.ANUNC_REPROVADO);
        Dialog.showDialog(stackPane, "Tem certeza que deseja recusar o anúncio?",
                "Por favor, digite a seguir o motivo para que o anúncio não possa ser aprovado");

        btnAceitar.setVisible(false);
        btnRecusar.setVisible(false);
        setHabilitar();
        parentMenu.viewAnunc.getItems().remove(rowAnuncio);
    }

    private void btnPagarClicked() {
    }

    private void btnDeletarClicked() {
        try {
            ds.newCon();
            ds.setStatement("DELETE FROM Detalhe_Anuncio WHERE idAnuncio = ?");
            ds.getStatement().setInt(1, rowAnuncio.getId());
            ds.getStatement().execute();
            ds.quit();

            btnDesabilitar.setVisible(false);
            btnDeletar.setVisible(false);
            parentMenu.viewAnunc.getItems().remove(rowAnuncio);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void atualizaEstado(int estado) {
        try {
            ds.newCon();
            ds.setStatement("UPDATE Detalhe_Anuncio SET idStatusAnuncio = ? WHERE idAnuncio = ?");
            ds.getStatement().setInt(1, estado);
            ds.getStatement().setInt(2, rowAnuncio.getId());
            ds.getStatement().execute();
            ds.quit();

            Anuncio.getAnuncios().remove(rowAnuncio);
            rowAnuncio.setEstado(estado);
            parentMenu.viewAnunc.getItems().add(rowAnuncio);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setHabilitar() {
        btnDesabilitar.setText("HABILITAR ANÚNCIO");
        btnDesabilitar.setTextFill(javafx.scene.paint.Paint.valueOf("#348649"));
        btnDesabilitar.setRipplerFill(javafx.scene.paint.Paint.valueOf("#39c34d"));
        btnDesabilitar.setVisible(true);
    }

    private void setDesabilitar() {
        btnDesabilitar.setText("DESABILITAR ANÚNCIO");
        btnDesabilitar.setTextFill(javafx.scene.paint.Paint.valueOf("#ff2b2b"));
        btnDesabilitar.setRipplerFill(javafx.scene.paint.Paint.valueOf("#e81e1e"));
        btnDesabilitar.setVisible(true);
    }

    private void fillInfo() {
        lblNomeAnunc.setText(rowAnuncio.getNome() + ":");
        lblMarca.setText(rowAnuncio.getMarca());
        lblDataPedido.setText(rowAnuncio.getData());
        lblDataValid.setText(rowAnuncio.getDataValid());
        lblPagamento.setText(rowAnuncio.getTipoPagamento());
        lblTitulo.setText(rowAnuncio.getTitulo());
        lblSubtitulo.setText(rowAnuncio.getSubtitulo());
        lblDescricao.setText(rowAnuncio.getDescricao());
        lblLink.setText(rowAnuncio.getLink());
        lblImagem.setText(rowAnuncio.getCaminhoImagem());
        lblNomeAnunciante.setText(anunciante.getNome());
        lblSobrenome.setText(anunciante.getSobrenome());
        lblCpf.setText(anunciante.getCpf());
        lblEmail.setText(anunciante.getEmail());
        lblTelefone1.setText(anunciante.getTelefone1());
        lblTelefone2.setText(anunciante.getTelefone2());
        lblCidade.setText(anunciante.getCidade());
        lblEndereco.setText(anunciante.getEndereco());
        lblCliquesComp.setText(rowAnuncio.getCliquesTotal() + "");
        lblCliquesTot.setText(rowAnuncio.getCliquesContados() + "");
        lblCliquesRest.setText(rowAnuncio.getNumCliques() + "");
        lblCliquesMes.setText(rowAnuncio.getCliquesMensais() + "");
        lblCliquesSemana.setText(rowAnuncio.getCliquesSemanais() + "");
        lblCliquesDiario.setText(rowAnuncio.getCliquesDiarios() + "");
    }

    static void setRowAnuncio(Anuncio anunc) {
        rowAnuncio = anunc;
    }

    static void setPosition(int index) {
        position = index;
    }

    static void setParentMenu(ControllerMenu menu) {
        parentMenu = menu;
    }
}
