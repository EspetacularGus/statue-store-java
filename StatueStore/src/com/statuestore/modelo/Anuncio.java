package com.statuestore.modelo;

import com.statuestore.bd.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Anuncio {

    private int id;
    private String nome;
    private String marca;
    private String link;

    private String data;
    private String dataValid;
    private int estado;

    private int numCliques;
    private int cliquesTotal;
    private int cliquesContados;
    private int cliquesMensais;
    private int cliquesSemanais;
    private int cliquesDiarios;

    private String tipoAnuncio;
    private String titulo;
    private String subtitulo;
    private String descricao;
    private String caminhoImagem;
    private String tipoPagamento;

    private static DataSource ds = new DataSource();
    private Anunciante anunciante;
    private String nomeAnunciante;

    public static final int ANUNC_ATIVO = 1;
    public static final int ANUNC_INATIVO = 2;
    public static final int ANUNC_REPROVADO = 3;
    public static final int ANUNC_ANALISE = 4;
    public static final int ANUNC_AGUARD_PAG = 5;
    public static final int ANUNC_DESABILITADO = 6;

    public static final String ANUNC_TEXTO = "Anúncio de Texto (Em Breve)";
    public static final String ANUNC_BANNER = "Anúncio em Banner - Imagem";
    public static final String ANUNC_POPUP_IMAGEM = "Anúncio Pop-Up - Imagem (Em Breve)";
    public static final String ANUNC_POPUP_VIDEO = "Anúncio Pop-Up - Vídeo (Em Breve)";

    private static ObservableList<Anuncio> anuncios = FXCollections.observableArrayList();

    public Anuncio(int id) {
        DataSource ds = new DataSource();

        try {
            ds.newCon();
            ds.setStatement("SELECT * FROM Anuncio WHERE idAnuncio = ?");
            ds.getStatement().setInt(1, id);
            ds.setResultSet(ds.getStatement());
            ds.getResultSet().next();

            setId(ds.getResultSet().getInt("idAnuncio"));
            setNome(ds.getResultSet().getString("nomeAnuncio"));
            setMarca(ds.getResultSet().getString("nomeMarca"));

            setAnunciante(ds.getResultSet().getInt("idAnunciante"));
            setTipoAnuncio(ds.getResultSet().getInt("idTipoAnuncio"));
            setTitulo(ds.getResultSet().getString("titulo"));
            setSubtitulo(ds.getResultSet().getString("subtitulo"));
            setDescricao(ds.getResultSet().getString("texto"));
            setCaminhoImagem(ds.getResultSet().getString("imagem"));
            setLink(ds.getResultSet().getString("linkSite"));

            ds.setStatement("SELECT * FROM Detalhe_Anuncio WHERE idAnuncio = ?");
            ds.getStatement().setInt(1, id);
            ds.setResultSet(ds.getStatement());
            ds.getResultSet().next();

            setCliquesTotal(ds.getResultSet().getInt("cliquesUteis"));
            setCliquesContados(ds.getResultSet().getInt("cliquesContados"));
            setNumCliques((ds.getResultSet().getInt("cliquesUteis")
                    - ds.getResultSet().getInt("cliquesContados")));
            setData(ds.getResultSet().getString("dataPedido"));
            setDataValid(ds.getResultSet().getString("dataValid"));
            setEstado(ds.getResultSet().getInt("idStatusAnuncio"));
            setTipoPagamento(ds.getResultSet().getInt("idTipoPgto"));
            ds.quit();

            setNomeAnunciante(anunciante.getNome());
            setCliquesMensais(0);
            setCliquesSemanais(0);
            setCliquesDiarios(0);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public static void fillAnuncios(int id) {
        anuncios = FXCollections.observableArrayList();
        int idAnuncio;

        try {
            ds.newCon();
            ds.setStatement("SELECT * FROM Detalhe_Anuncio WHERE idAnuncio IN (SELECT idAnuncio FROM Anuncio WHERE idAnunciante = ?) ORDER BY idDetalhe_Anuncio DESC");
            ds.getStatement().setInt(1, id);
            ds.setResultSet(ds.getStatement());

            while (ds.getResultSet().next()) {
                idAnuncio = ds.getResultSet().getInt("idAnuncio");
                anuncios.add(new Anuncio(idAnuncio));
            }

            ds.quit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void fillAnunciosWhere(int id, int filtro) {
        anuncios = FXCollections.observableArrayList();
        int idAnuncio;

        try {
            ds.newCon();
            ds.setStatement("SELECT * FROM Detalhe_Anuncio WHERE idAnuncio IN (SELECT idAnuncio FROM Anuncio WHERE idAnunciante = ?) AND idStatusAnuncio = ? ORDER BY idDetalhe_Anuncio DESC");
            ds.getStatement().setInt(1, id);
            ds.getStatement().setInt(2, filtro);
            ds.setResultSet(ds.getStatement());

            while (ds.getResultSet().next()) {
                idAnuncio = ds.getResultSet().getInt("idAnuncio");
                anuncios.add(new Anuncio(idAnuncio));
            }

            ds.quit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void fillAnuncios() {

        List<Anuncio> listAnuncios = new ArrayList<>();
        int idAnuncio;

        try {
            ds.newCon();
            ds.setStatement("SELECT * FROM Detalhe_Anuncio WHERE idStatusAnuncio != ? ORDER BY idDetalhe_Anuncio DESC");
            ds.getStatement().setInt(1, Anuncio.ANUNC_AGUARD_PAG);
            ds.setResultSet(ds.getStatement());

            while (ds.getResultSet().next()) {
                idAnuncio = ds.getResultSet().getInt("idAnuncio");
                listAnuncios.add(new Anuncio(idAnuncio));
            }
            ds.quit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        anuncios = FXCollections.observableArrayList(listAnuncios);
    }

    public static void fillAnunciosWhere(int filtro) {

        List<Anuncio> listAnuncios = new ArrayList<>();
        int idAnuncio;

        try {
            ds.newCon();
            ds.setStatement("SELECT * FROM Detalhe_Anuncio WHERE idStatusAnuncio = ? ORDER BY idDetalhe_Anuncio DESC");
            ds.getStatement().setInt(1, filtro);
            ds.setResultSet(ds.getStatement());

            while (ds.getResultSet().next()) {
                idAnuncio = ds.getResultSet().getInt("idAnuncio");
                listAnuncios.add(new Anuncio(idAnuncio));
            }
            ds.quit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        anuncios = FXCollections.observableArrayList(listAnuncios);
    }

    public static int totalAnuncios(int id, int estado) throws SQLException {

        ds.newCon();
        ds.setStatement("EXECUTE getEspecNumAnuncios ?, ?");
        ds.getStatement().setInt(1, id);
        ds.getStatement().setInt(2, estado);

        ds.setResultSet(ds.getStatement());
        ds.getResultSet().next();
        ds.quit();

        return ds.getResultSet().getInt(1);
    }

    public static int totalAnuncios(int id) throws SQLException {

        int result = 0;

        DataSource ds = new DataSource();

        ds.newCon();
        ds.setStatement("EXECUTE getNumAnuncios ?");
        ds.getStatement().setInt(1, id);

        ds.setResultSet(ds.getStatement());
        ds.getResultSet().next();
        result = ds.getResultSet().getInt(1);
        ds.quit();

        return result;
    }

    //Getters

    public String getNome() {
        return nome;
    }

    public String getMarca() {
        return marca;
    }

    public int getId() {
        return id;
    }

    public int getNumCliques() {
        return numCliques;
    }

    public String getData() {
        return data;
    }

    public int getIntEstado() {
        return estado;
    }

    public Anunciante getAnunciante() {
        return anunciante;
    }

    public static ObservableList<Anuncio> getAnuncios() {
        return anuncios;
    }

    public int getCliquesTotal() {
        return cliquesTotal;
    }

    public int getCliquesContados() {
        return cliquesContados;
    }

    public String getTipoAnuncio() {
        return tipoAnuncio;
    }

    public String getLink() {
        return link;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDataValid() {
        return dataValid;
    }

    public String getCaminhoImagem() {
        return caminhoImagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public String getEstado() {
        switch (estado) {
            case ANUNC_ATIVO:
                return "Ativo";
            case ANUNC_AGUARD_PAG:
                return "Aguardando Pagamento";
            case ANUNC_ANALISE:
                return "Aguardando Análise";
            case ANUNC_INATIVO:
                return "Inativo";
            case ANUNC_REPROVADO:
                return "Análise Reprovada";
            case ANUNC_DESABILITADO:
                return "Desabilitado";
        }
        return "N/A";
    }
    //Setters

    public void setCaminhoImagem(String caminhoImagem) {
        this.caminhoImagem = caminhoImagem;
    }

    public void setCliquesContados(int cliquesContados) {
        this.cliquesContados = cliquesContados;
    }

    public void setCliquesTotal(int cliquesTotal) {
        this.cliquesTotal = cliquesTotal;
    }

    private void setNome(String nome) {
        this.nome = nome;
    }

    private void setMarca(String marca) {
        this.marca = marca;
    }

    private void setId(int id) {
        this.id = id;
    }

    private void setNumCliques(int cliques) {
        this.numCliques = cliques;
    }

    private void setData(String data) {
        this.data = data;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public void setTipoAnuncio(int tipoAnuncio) {
        switch (tipoAnuncio) {
            case 1:
                this.tipoAnuncio = "Anúncio de Texto";
                break;
            case 2:
                this.tipoAnuncio = "Anúncio em Banner";
                break;
            case 3:
                this.tipoAnuncio = "Pop-Up de Imagem";
                break;
            case 4:
                this.tipoAnuncio = "Pop-Up de Vídeo";
                break;
        }
    }

    public void setAnunciante(int id) {
            this.anunciante = new Anunciante(id);
    }

    public void setLink(String link) {
        this.link = link;
    }

    public void setDataValid(String dataPedido) {
        this.dataValid = dataPedido;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNomeAnunciante() {
        return nomeAnunciante;
    }

    public void setNomeAnunciante(String nomeAnunciante) {
        this.nomeAnunciante = nomeAnunciante;
    }

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(int tipoPagamento) {
        switch (tipoPagamento) {
            case 1:
                this.tipoPagamento = Pagamento.BOLETO;
                break;
            case 2:
                this.tipoPagamento = Pagamento.CARTAO_CREDITO;
                break;
            case 3:
                this.tipoPagamento = Pagamento.CARTAO_DEBITO;
                break;
        }
    }

    public int getCliquesMensais() {
        return cliquesMensais;
    }

    public void setCliquesMensais(int cliquesMensais) {
        this.cliquesMensais = this.cliquesContados / 12;
    }

    public int getCliquesSemanais() {
        return cliquesSemanais;
    }

    public void setCliquesSemanais(int cliquesSemanais) {
        this.cliquesSemanais = this.cliquesMensais / 4;
    }

    public int getCliquesDiarios() {
        return cliquesDiarios;
    }

    public void setCliquesDiarios(int cliquesDiarios) {
        this.cliquesDiarios = this.cliquesSemanais / 7;
    }
}
