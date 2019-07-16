package com.statuestore.controle;

import com.statuestore.modelo.Anuncio;
import com.statuestore.modelo.Funcionario;
import com.statuestore.helpers.Dialog;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.ArrayList;

public class ControllerMenuFunc extends ControllerMenu {

    @SuppressWarnings("unchecked")
    @Override
    public void initTable() {
        cmbFiltro.getItems().remove("Pagamento Pendente");
        cmbFiltro.setValue("Anúncios Pendentes");

        TableColumn<Anuncio,String> marcaCol = new TableColumn<>("Nome do Anúncio");
        TableColumn<Anuncio,String> nomeCol = new TableColumn<>("Marca");
        TableColumn<Anuncio,Integer> anuncianteCol = new TableColumn<>("Anunciante");
        TableColumn<Anuncio,String> dataCol = new TableColumn<>("Data do Pedido");
        TableColumn<Anuncio,Integer> estadoCol = new TableColumn<>("Estado");

        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        marcaCol.setCellValueFactory(new PropertyValueFactory<>("marca"));
        anuncianteCol.setCellValueFactory(new PropertyValueFactory<>("nomeAnunciante"));
        dataCol.setCellValueFactory(new PropertyValueFactory<>("data"));
        estadoCol.setCellValueFactory(new PropertyValueFactory<>("estado"));

        viewAnunc.getColumns().addAll(marcaCol, nomeCol, anuncianteCol, dataCol, estadoCol);
    }

    @Override
    public void fillTable() {
        if (filtroAnuncio != 0)
            Anuncio.fillAnunciosWhere(filtroAnuncio);
        else
            Anuncio.fillAnuncios();
        viewAnunc.setItems(Anuncio.getAnuncios());
    }

    @Override
    public void infoClicked() {
        ArrayList<String> listInfo = new ArrayList<>();

        listInfo.add("Nome: " + Funcionario.getNome());
        listInfo.add("Sobrenome: " + Funcionario.getSobrenome());
        listInfo.add("CPF: " + Funcionario.getCpf());
        listInfo.add("Função: " + Funcionario.getFuncao());
        listInfo.add("Nível de Acesso: " + Funcionario.getNivelAcesso());

        Dialog.showListDialog(stackPane,"Suas informações:", listInfo);
    }
}
