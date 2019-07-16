package com.statuestore.controle;

import com.statuestore.helpers.Dialog;
import com.statuestore.modelo.LoggedAnunciante;
import com.statuestore.modelo.Anuncio;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;

public class ControllerMenuAnunc extends ControllerMenu{

    @SuppressWarnings("unchecked")
    @Override
    public void initTable() {
        cmbFiltro.setValue("Todos os Anúncios");

        TableColumn<Anuncio,String> marcaCol = new TableColumn<>("Nome do Anúncio");
        TableColumn<Anuncio,String> nomeCol = new TableColumn<>("Marca");
        TableColumn<Anuncio,Integer> cliquesCol = new TableColumn<>("Cliques Restantes");
        TableColumn<Anuncio,String> dataCol = new TableColumn<>("Data do Pedido");
        TableColumn<Anuncio,String> estadoCol = new TableColumn<>("Estado");

        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        marcaCol.setCellValueFactory(new PropertyValueFactory<>("marca"));
        cliquesCol.setCellValueFactory(new PropertyValueFactory<>("numCliques"));
        dataCol.setCellValueFactory(new PropertyValueFactory<>("data"));
        estadoCol.setCellValueFactory(new PropertyValueFactory<>("estado"));

        viewAnunc.getColumns().addAll(marcaCol, nomeCol, cliquesCol, dataCol, estadoCol);
    }

    @Override
    public void infoClicked() {
            ArrayList<String> listInfo = new ArrayList<>();
            listInfo.add("ID: " + LoggedAnunciante.getId());
            listInfo.add("Nome: " + LoggedAnunciante.getNome());
            listInfo.add("Sobrenome: " + LoggedAnunciante.getSobrenome());
            listInfo.add("CPF: " + LoggedAnunciante.getCpf());
            listInfo.add("Total de Anúncios: " + LoggedAnunciante.getNumAnuncios());
            listInfo.add("Anúncios Ativos: " + LoggedAnunciante.getNumAnunciosWhere(Anuncio.ANUNC_ATIVO));

            Dialog.showListDialog(stackPane,"Suas Informações: ", listInfo);
    }

    @Override
    public void fillTable() {
        if (filtroAnuncio != 0)
            Anuncio.fillAnunciosWhere(LoggedAnunciante.getId(), filtroAnuncio);
        else
            Anuncio.fillAnuncios(LoggedAnunciante.getId());
        viewAnunc.setItems(Anuncio.getAnuncios());
    }

    @FXML
    private void btnAnuncClicked() {
        if (!stg.isOpen()) {
            ControllerNovoAnuncio.setParentMenu(this);
            stg.openStage("com/statuestore/view/NovoAnuncio.fxml", "");
        } else {
            stg.getStage().requestFocus();
        }
    }
}
