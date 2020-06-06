package com.company.skyfall.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ACToSet {
    public static ImageView v2 = new ImageView(new Image("com/company/skyfall/resources/images/aircraft/Player AC/Player AC alive/2/v2 full.png"));
    public static ImageView h2 = new ImageView(new Image("com/company/skyfall/resources/images/aircraft/Player AC/Player AC alive/2/h2 full.png"));
    public static ImageView v3 = new ImageView(new Image("com/company/skyfall/resources/images/aircraft/Player AC/Player AC alive/3/v3 full.png"));
    public static ImageView h3 = new ImageView(new Image("com/company/skyfall/resources/images/aircraft/Player AC/Player AC alive/3/h3 full.png"));
    public static ImageView v4 = new ImageView(new Image("com/company/skyfall/resources/images/aircraft/Player AC/Player AC alive/4/v4 full.png"));
    public static ImageView h4 = new ImageView(new Image("com/company/skyfall/resources/images/aircraft/Player AC/Player AC alive/4/h4 full.png"));
    public static Parent acBox(){
        HBox acHbox = new HBox(100);
        VBox hACVbox = new VBox(15); hACVbox.setPrefSize(120,120);
        //  hACVbox.setStyle("-fx-border-color:red;");
        HBox vACHbox = new HBox(15); vACHbox.setPrefSize(120,120);
        //   vACHbox.setStyle("-fx-border-color:red;");
        //acHbox.setStyle("-fx-border-image-source: url(\"border.png\");-fx-border-image-width:20;-fx-border-image-slice:25;");
        v2.setFitHeight(60);
        h2.setFitWidth(60);
        v3.setFitHeight(90);
        h3.setFitWidth(90);
        h4.setFitWidth(120);
        v4.setFitHeight(120);
        v2.setPreserveRatio(true);
        h2.setPreserveRatio(true);
        v3.setPreserveRatio(true);
        h3.setPreserveRatio(true);
        h4.setPreserveRatio(true);
        v4.setPreserveRatio(true);
        hACVbox.getChildren().addAll(h2,h3,h4);
        vACHbox.getChildren().addAll(v2,v3,v4);
        acHbox.getChildren().addAll(hACVbox,vACHbox);
        //acHbox.setStyle("-fx-border-color: rgb(255,0,255);-fx-background-color: rgba(255,0,255, 0.2);");
        acHbox.setPrefSize(370,150);
        acHbox.setAlignment(Pos.CENTER);
        acHbox.setPadding(new Insets(15,15,15,15));
        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(370,150);
        ImageView imgv = new ImageView(new Image("com/company/skyfall/resources/images/border.png"));
        imgv.setFitHeight(150);
        imgv.setFitWidth(370);
        stackPane.getChildren().addAll(imgv,acHbox);
        return  stackPane;
    }
}