package com.statuestore.helpers;

import com.jfoenix.controls.*;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import java.util.List;

public class Dialog {

    /*public final int BOTAO_ENTENDI = 0;
      private static boolean ativo = true;*/

    private Dialog() {

    }

    public static void showDialog(javafx.scene.layout.StackPane pane, String title, String message) {

        JFXDialogLayout layout = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(pane, layout, JFXDialog.DialogTransition.CENTER, false);

        JFXButton btnFechar = new JFXButton("ENTENDI");
        btnFechar.setButtonType(JFXButton.ButtonType.FLAT);
        btnFechar.setTextFill(javafx.scene.paint.Paint.valueOf("e91d63"));
        btnFechar.setFont(Font.font("Product Sans", FontWeight.BOLD,14));
        btnFechar.setOnAction(e -> {
            dialog.close();
            pane.setVisible(false);
        });

        layout.setHeading(new Text(title));
        layout.setBody(new Text(message));
        layout.setActions(btnFechar);
        dialog.setMaxSize(200,100);
        pane.setVisible(true);
        dialog.show();
    }

    @SuppressWarnings("unchecked")
    public static void showListDialog(StackPane pane, String title, List<String> list) {

        //Creating Dialog Layout

        JFXDialogLayout layout = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(pane, layout, JFXDialog.DialogTransition.CENTER, false);

        //Creating Dialog Button(s)

        JFXButton btnFechar = new JFXButton("ENTENDI");
        btnFechar.setButtonType(JFXButton.ButtonType.FLAT);
        btnFechar.setTextFill(javafx.scene.paint.Paint.valueOf("e91d63"));
        btnFechar.setFont(Font.font("Product Sans", FontWeight.BOLD,14));
        btnFechar.setOnAction(e -> {
            dialog.close();
            pane.setVisible(false);
        });

        //Creating ListView

        JFXListView lv = new JFXListView();
        lv.getItems().addAll(list);
        //lv.setDisable(true);

        //Adding itens into Layout

        layout.setHeading(new Text(title));
        layout.setBody(lv);
        layout.setActions(btnFechar);

        pane.setVisible(true);
        dialog.show();
    }

    /*boolean proceed;

    public boolean confirmDialog(StackPane pane, String title, String body, String btnProc, String btnCanc) {
        //Creating Dialog Layout

        JFXDialogLayout layout = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(pane, layout, JFXDialog.DialogTransition.CENTER, false);

        //Creating Dialog Button(s)

        JFXButton btnProceed = new JFXButton(btnProc);
        btnProceed.setButtonType(JFXButton.ButtonType.FLAT);
        btnProceed.setTextFill(javafx.scene.paint.Paint.valueOf("e91d63"));
        btnProceed.setFont(Font.font("Product Sans", FontWeight.BOLD,14));
        btnProceed.setOnAction(e -> {
            dialog.close();
            pane.setVisible(false);
            proceed = true;
        });

        JFXButton btnCancel = new JFXButton(btnCanc);
        btnCancel.setButtonType(JFXButton.ButtonType.FLAT);
        btnCancel.setTextFill(javafx.scene.paint.Paint.valueOf("e91d63"));
        btnCancel.setFont(Font.font("Product Sans", FontWeight.BOLD,14));
        btnCancel.setOnAction(e -> {
            dialog.close();
            pane.setVisible(false);
            proceed = false;
        });

        //Adding itens into Layout

        layout.setHeading(new Text(title));
        layout.setBody(new Text(body));
        layout.setActions(btnCancel, btnProceed);

        pane.setVisible(true);
        dialog.show();
    }*/

    public static void showImageDialog(StackPane pane, String title, String path) {

        //Creating Dialog Layout

        JFXDialogLayout layout = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(pane, layout, JFXDialog.DialogTransition.CENTER, false);

        //Creating Dialog Button(s)

        JFXButton btnFechar = new JFXButton("ENTENDI");
        btnFechar.setButtonType(JFXButton.ButtonType.FLAT);
        btnFechar.setTextFill(javafx.scene.paint.Paint.valueOf("e91d63"));
        btnFechar.setFont(Font.font("Product Sans", FontWeight.BOLD,14));
        btnFechar.setOnAction(e -> {
            dialog.close();
            pane.setVisible(false);
        });

        //Creating ListView

        String file = "file:\\"
                + "C:\\Users\\ninja\\Desktop\\Entrega 2019 (Statue Store)\\APLICACAO_WEB -\\StatueStoreWebApplic\\StatueStoreWebApplic"
                + path.substring(2);
        System.out.println(file);
        ImageView imageView = new ImageView();
        imageView.setFitWidth(500);
        imageView.setFitHeight(300);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setImage(new Image(file));

        //Adding itens into Layout

        layout.setHeading(new Text(title));
        layout.setBody(imageView);
        layout.setActions(btnFechar);

        pane.setVisible(true);
        dialog.show();
    }

    public static void showScrollDialog(javafx.scene.layout.StackPane pane, String title, String message) {

        JFXDialogLayout layout = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(pane, layout, JFXDialog.DialogTransition.CENTER, false);

        ScrollPane scrollPane = new ScrollPane();

        Label label = new Label(message);
        label.setWrapText(true);
        label.maxWidth(280);
        label.setFont(Font.font("Product Sans", 14));

        scrollPane.setContent(label);
        scrollPane.setMaxWidth(300);
        scrollPane.setMaxHeight(400);

        JFXButton btnFechar = new JFXButton("ENTENDI");
        btnFechar.setButtonType(JFXButton.ButtonType.FLAT);
        btnFechar.setTextFill(javafx.scene.paint.Paint.valueOf("e91d63"));
        btnFechar.setFont(Font.font("Product Sans", FontWeight.BOLD,14));
        btnFechar.setOnAction(e -> {
            dialog.close();
            pane.setVisible(false);
        });

        layout.setHeading(new Text(title));
        layout.setBody(scrollPane);
        layout.setActions(btnFechar);
        pane.setVisible(true);
        dialog.show();
    }
}

