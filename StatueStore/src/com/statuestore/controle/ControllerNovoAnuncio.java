package com.statuestore.controle;

import com.jfoenix.controls.*;
import com.statuestore.bd.DataSource;
import com.statuestore.helpers.Dialog;
import com.statuestore.modelo.LoggedAnunciante;
import com.statuestore.modelo.Anuncio;
import com.statuestore.modelo.Pagamento;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ResourceBundle;

public class ControllerNovoAnuncio implements Initializable {

    private DataSource ds = new DataSource();
    private static ControllerMenu parentMenu;
    private static final String stringServer = "C:\\Users\\ninja\\Desktop\\Entrega 2019 (Statue Store)\\APLICACAO_WEB -\\StatueStoreWebApplic\\StatueStoreWebApplic\\imgAnunciantes\\";

    private float custoClique;
    private int totalCliques;
    private float custoTotal;
    private int tipoAnuncio;
    private int tipoPag;
    private String imagePath;
    private String newPath;

    //Geral
    @FXML private StackPane stackPane;
    @FXML private Pane pnlInicio;
    @FXML private Pane pnlInfo;

    @FXML private Pane pnlAnuncio;
    @FXML private Pane pnlAnuncioTexto;
    @FXML private Pane pnlAnuncioImg;
    @FXML private Pane pnlAnuncioVideo;

    @FXML private Pane pnlCliques;
    @FXML private Pane pnlPagamento;
    @FXML private Pane pnlBoleto;
    @FXML private Pane pnlCartao;
    @FXML private Pane pnlCodigo;

    @FXML private Pane pnlConfirm;
    @FXML private Pane pnlSucesso;
    @FXML private JFXButton btnContinuar;
    @FXML private ImageView icnBack;

    @FXML private JFXTextField txtNome;
    @FXML private JFXTextField txtMarca;
    @FXML private JFXTextField txtLink;
    @FXML private JFXTextField txtTitulo;
    @FXML private JFXTextField txtSubtitulo;
    @FXML private JFXTextArea txtTexto;

    @FXML private JFXSlider sldCliques;
    @FXML private Label lblCliques;
    @FXML private Label lblCustoClique;
    @FXML private Label lblCustoClique2;
    @FXML private Label lblTotal;
    @FXML private Label lblCliques2;
    @FXML private Label lblCusto;
    @FXML private Label lblTipoAnunc;
    @FXML private Label lblIndisp;
    @FXML private Label lblPagamento;
    @FXML private Label lblNomeAnunc;
    @FXML private Label lblTermos;

    @FXML private JFXComboBox<String> cmbTipoAnuncio;
    @FXML private JFXComboBox<String> cmbTipoPagamento;
    @FXML private JFXComboBox<String> cmbBandeira;

    @FXML private JFXButton btnImagem;
    @FXML private ImageView imgView;
    @FXML private ImageView imgAnuncio;
    @FXML private ImageView icnHelp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbTipoPagamento.valueProperty().addListener((o, oldVal, newVal) ->
                cmbTipoPagamentoValueChanged(newVal));
        cmbTipoAnuncio.valueProperty().addListener((o, oldVal, newVal) ->
                cmbTipoAnuncioValueChanged(newVal));
        sldCliques.valueProperty().addListener((o, oldVal, newVal) ->
                sldCliquesValueChanged(newVal));
        btnImagem.setOnAction(e -> btnImagemClicked());

        lblTermos.setOnMouseClicked(e -> Dialog.showScrollDialog(stackPane, "Regras para Anúncios",
                "•\tO anúncio não deve conter conteúdo \n" +
                "obsceno ou pornográfico, como anúncios\n" +
                "de sites adultos ou de sex shop.\n" +
                "Assim como não devem conter \n" +
                "violência explícita ou discursos de ódio,\n" +
                "•\tO link atribuído ao anúncio deve\n" +
                "estar livre de malwares ou outros\n" +
                "tipos de ameaças, assim como não \n" +
                "devem conter" +
                "fraudes ou \n roubo de dados.\n" +
                "•\tO link atribuído ao anúncio\n" +
                "deve contar com conteúdo condizente\n" +
                "ao colocado no anúncio. Exemplo: \n" +
                "Um anúncio de uma loja de sapatos \n" +
                "não deve redirecionar o usuário a\n" +
                "uma página de vendas de automóveis.\n" +
                "\n" +
                "Caso o anúncio seja recusado pelo administrador, \n" +
                "será enviado um e-mail com um código de \n" +
                "resgate para o anunciante, este só é \n" +
                "válido uma vez e poderá ser utilizado \n" +
                "para enviar outro anúncio gratuitamente."));

        icnHelp.setOnMouseClicked(e -> Dialog.showScrollDialog(stackPane, "Tipos de Anúncio",
                "- Banner:\n" +
                "É exibido em qualquer lugar do aplicativo, \n" +
                "contendo uma imagem estática e um link atribuído.\n" + "\n" +
                "- Anúncio de Texto (Em Breve):\n" +
                "Toda a informação é transmitida através de texto, \n exibidos " +
                "em uma página estabelecida \n apenas para estes.\n" +
                "\n" +
                "- Pop-Up Estático (Em Breve):\n" +
                "Uma imagem surge na tela contendo seu anúncio, \n é exibido de 30 em 30 " +
                "minutos, \n ficando por 5 segundos na tela, \n contém link atribuído.\n" +
                "\n" +
                "- Pop-Up Vídeo (Em Breve):\n" +
                "Um vídeo de sua marca é exibido de \n 30 em 30 minutos na tela, \n onde " +
                "o usuário é obrigado a assistir pelo menos \n 15 segundos do mesmo, \n contém link atribuído."));
        //Inserir eventos de validação de campos
        fillComboBoxes();
    }

    private void btnImagemClicked() {
        FileChooser chooser = new FileChooser();
        File file = chooser.showOpenDialog(pnlAnuncio.getScene().getWindow());
        if (file != null) {
            imagePath = file.toURI().toString();
            imgView.setImage(new Image(imagePath));
            imgAnuncio.setImage(new Image(imagePath));
        }
    }

    @FXML
    private void icnBackClicked() {
        if (pnlInicio.isVisible()) {
            pnlInicio.getScene().getWindow().hide();
            ds.quit();
        } else if (pnlInfo.isVisible()) {
            pnlInfo.setVisible(false);
            pnlInicio.setVisible(true);
        } else if (pnlAnuncio.isVisible()) {
            pnlAnuncio.setVisible(false);
            pnlAnuncioVideo.setVisible(false);
            pnlAnuncioImg.setVisible(false);
            pnlAnuncioTexto.setVisible(false);
            pnlInfo.setVisible(true);
        } else if (pnlCliques.isVisible()) {
            pnlCliques.setVisible(false);
            pnlAnuncio.setVisible(true);
        } else if (pnlPagamento.isVisible()) {
            pnlPagamento.setVisible(false);
            pnlCliques.setVisible(true);
        } else if (pnlConfirm.isVisible()) {
            pnlConfirm.setVisible(false);
            pnlPagamento.setVisible(true);
        } else if (pnlSucesso.isVisible()) {
            icnBack.setVisible(false);
        }
    }

    public void btnContinuarClicked() {
        if (pnlInicio.isVisible()) {
            pnlInicio.setVisible(false);
            pnlInfo.setVisible(true);
        } else if (pnlInfo.isVisible()) {
            pnlInfo.setVisible(false);
            pnlAnuncio.setVisible(true);
            btnContinuar.setText("CONTINUAR");
        } else if (pnlAnuncio.isVisible()) {
            pnlAnuncio.setVisible(false);
            pnlCliques.setVisible(true);
            btnContinuar.setText("CONTINUAR");
        } else if (pnlCliques.isVisible()) {
            pnlCliques.setVisible(false);
            pnlPagamento.setVisible(true);
            btnContinuar.setText("IR PARA O PAGAMENTO");
        } else if (pnlPagamento.isVisible()) {
            lblNomeAnunc.setText(txtNome.getText());
            pnlPagamento.setVisible(false);
            pnlConfirm.setVisible(true);
            btnContinuar.setText("TUDO CERTO");
        } else if (pnlConfirm.isVisible()) {
            if (cadastrarAnuncio()) {
                pnlConfirm.setVisible(false);
                pnlSucesso.setVisible(true);
                icnBack.setVisible(false);
                btnContinuar.setText("VOLTAR PARA O MENU");
            } else {
                Dialog.showDialog(stackPane, "Algo errado!", "Algo deu errado :/ Se o erro persistir," +
                        "por favor entre em contato com a loja");
            }
        } else if (pnlSucesso.isVisible()) {
            pnlSucesso.getScene().getWindow().hide();
        }
    }

    private void fillComboBoxes() {
        ObservableList<String> tipoAnuncio = FXCollections.observableArrayList(
                Anuncio.ANUNC_BANNER, Anuncio.ANUNC_TEXTO, Anuncio.ANUNC_POPUP_IMAGEM, Anuncio.ANUNC_POPUP_VIDEO);
        cmbTipoAnuncio.setItems(tipoAnuncio);

        ObservableList<String> tipoPagamento = FXCollections.observableArrayList(Pagamento.CARTAO_CREDITO,
                Pagamento.CARTAO_DEBITO, Pagamento.BOLETO);
        cmbTipoPagamento.setItems(tipoPagamento);
    }

    private void cmbTipoPagamentoValueChanged(String newVal) {
        pnlBoleto.setVisible(false);
        pnlCartao.setVisible(false);
        pnlCodigo.setVisible(false);

        ObservableList<String> bandeiras = FXCollections.observableArrayList("Visa", "MasterCard", "Elo",
                "American Exp.", "Hipercard");
        cmbBandeira.setItems(bandeiras);
        lblPagamento.setText(newVal);

        switch (newVal) {
            case Pagamento.BOLETO:
                pnlBoleto.setVisible(true);
                tipoPag = 1;
                break;
            case Pagamento.CARTAO_CREDITO:
                pnlCartao.setVisible(true);
                tipoPag = 2;
                break;
            case Pagamento.CARTAO_DEBITO:
                pnlCartao.setVisible(true);
                tipoPag = 3;
                cmbBandeira.getItems().removeAll("American Exp.", "Hipercard");
                break;
            case Pagamento.COD_RESGATE:
                pnlCodigo.setVisible(true);
                tipoPag = 4;
                break;
        }
    }

    private void cmbTipoAnuncioValueChanged(String newVal) {
        pnlAnuncioTexto.setVisible(false);
        pnlAnuncioVideo.setVisible(false);
        pnlAnuncioImg.setVisible(false);

        lblTipoAnunc.setText(newVal);

        switch (newVal) {
            case Anuncio.ANUNC_TEXTO:
                //pnlAnuncioTexto.setVisible(true);
                custoClique = (float)2.00;
                btnContinuar.setDisable(true);
                lblIndisp.setVisible(true);
                //tipoAnuncio = 1;
                break;
            case Anuncio.ANUNC_BANNER:
                pnlAnuncioImg.setVisible(true);
                custoClique = (float)3.00;
                lblIndisp.setVisible(false);
                tipoAnuncio = 2;
                btnContinuar.setDisable(false);
                break;
            case Anuncio.ANUNC_POPUP_IMAGEM:
                //pnlAnuncioImg.setVisible(true);
                custoClique = (float)4.00;
                btnContinuar.setDisable(true);
                lblIndisp.setVisible(true);
                //tipoAnuncio = 3;
                break;
            case Anuncio.ANUNC_POPUP_VIDEO:
                //pnlAnuncioVideo.setVisible(true);
                custoClique = (float)5.00;
                btnContinuar.setDisable(true);
                lblIndisp.setVisible(true);
                //tipoAnuncio = 4;
                break;
        }

        lblCustoClique.setText("Custo por Clique: R$" + custoClique);
        lblCustoClique2.setText("Custo por Clique: R$" + custoClique);
    }

    private void sldCliquesValueChanged(Number newVal) {
        totalCliques = (int)Float.parseFloat(newVal.toString());
        lblCliques.setText(totalCliques + " Cliques");
        lblCliques2.setText(totalCliques + "");

        custoTotal = totalCliques * custoClique;
        lblTotal.setText("Custo Total: R$" + custoTotal);
        lblCusto.setText("R$" + custoTotal);
    }

    private boolean cadastrarAnuncio() {
        String nomeAnuncio = txtNome.getText();
        String nomeMarca = txtMarca.getText();
        String linkSite = txtLink.getText();
        String titulo;
        String subtitulo;
        String texto;
        String path;

        if (txtTitulo.getText().isEmpty()) {
            titulo = "N/A";
            subtitulo = "N/A";
            texto = "N/A";
            path = imagePath;

            System.out.println(path);
            try {
                java.awt.Image image = ImageIO.read(new URL(path));
                path = uploadImage(image);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            titulo = txtTitulo.getText();
            subtitulo = txtSubtitulo.getText();
            texto = txtTexto.getText();
            path = "N/A";
        }


        try {
            ds.newCon();
            ds.setStatement("EXECUTE cadastrarAnuncio ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?");
            ds.getStatement().setString(1, nomeAnuncio); //nomeAnuncio
            ds.getStatement().setString(2, nomeMarca); //nomeMarca
            ds.getStatement().setString(3, linkSite); //linkSite
            ds.getStatement().setString(4, newPath); //imagem
            ds.getStatement().setString(5, titulo); //titulo
            ds.getStatement().setString(6, subtitulo); //subtitulo
            ds.getStatement().setString(7, texto); //texto
            ds.getStatement().setInt(8, tipoAnuncio); //idTipoAnuncio
            ds.getStatement().setInt(9, LoggedAnunciante.getId()); //idAnunciante
            ds.getStatement().setInt(10, totalCliques); //cliquesUteis
            ds.getStatement().setFloat(11, custoTotal); //custoTotal
            ds.getStatement().setInt(12, tipoPag); //idTipoPgto
            ds.getStatement().setNull(13, Types.INTEGER); //idCartao
            ds.getStatement().execute();
            ds.quit();

            parentMenu.btnAtualizarClicked();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String uploadImage(java.awt.Image img) {
        BufferedImage bimage = new BufferedImage(
                img.getWidth(null),
                img.getHeight(null),
                BufferedImage.TYPE_INT_ARGB);
        bimage.getGraphics().drawImage(img, 0, 0, null);
        bimage.getGraphics().dispose();

        String nomeAnuncio = txtNome.getText();
        String path = stringServer + nomeAnuncio + ".png";
        newPath = "..\\imgAnunciantes\\" + nomeAnuncio + ".png";
        try {
            ImageIO.write(bimage, "png", new File(path));
            return path;
        } catch (IOException e) {
            e.printStackTrace(); Dialog.showDialog(stackPane, "Não foi possível enviar a imagem!",
                    "Algo deu errado :/ Se o erro persistir," +
                            " \n por favor entre em contato com a loja");
            return null;
        }
    }

    public static void setParentMenu(ControllerMenu menu) {
        parentMenu = menu;
    }
}
