
package com.company.skyfall.view;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ACHPBox {
    public static Parent createHPBox(int acType){
        ImageView h2 = new ImageView(new Image("com/company/skyfall/resources/images/aircraft/Player AC/Player AC alive/2/h2 full.png"));
        ImageView h3 = new ImageView(new Image("com/company/skyfall/resources/images/aircraft/Player AC/Player AC alive/3/h3 full.png"));
        ImageView h4 = new ImageView(new Image("com/company/skyfall/resources/images/aircraft/Player AC/Player AC alive/4/h4 full.png"));
        Label[] hp = new Label[3];
        hp[0] =   new Label("  HP:             200/200  ");
        hp[1] =   new Label("  HP:             300/300  ");
        hp[2] =   new Label("  HP:             400/400  ");
        Label[] type = new Label[3];
        type[0] = new Label("  TYPE:                       2  ");
        type[1] = new Label("  TYPE:                       3  ");
        type[2] = new Label("  TYPE:                       4  ");
        Label[] name = new Label[3];
        name[0] = new Label("  NAME:         ALPHA 2  ");
        name[1] = new Label("  NAME:         ALPHA 3  ");
        name[2] = new Label("  NAME:         ALPHA 4  ");
        Label[] status = new Label[3];
        status[0] = new Label("  STATUS:       ALIVE  ");
        status[1] = new Label("  STATUS:       ALIVE  ");
        status[2] = new Label("  STATUS:       ALIVE  ");
        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(500,150);
        ImageView imgv = new ImageView(new Image("com/company/skyfall/resources/images/border.png"));
        imgv.setFitWidth(500);
        imgv.setFitHeight(150);
        HBox hbox = new HBox();
        stackPane.getChildren().addAll(imgv,hbox);
        // stackPane.setStyle("-fx-background-color: rgba(255,0,255, 0.2);");
        VBox vBox1 = new VBox(10);
        VBox vBox2 = new VBox(10);
        h2.setFitWidth(80);
        h3.setFitWidth(120);
        h4.setFitWidth(160);
        h2.setPreserveRatio(true);
        h3.setPreserveRatio(true);
        h4.setPreserveRatio(true);
        for (int i = 0; i <= 2; i++) {
            name[i].setTextFill(Color.YELLOW);   name[i].setFont(new Font("Arial",15));
            hp[i].setTextFill(Color.YELLOW);     hp[i].setFont(new Font("Arial",15));
            type[i].setTextFill(Color.YELLOW);   type[i].setFont(new Font("Arial",15));
            status[i].setTextFill(Color.YELLOW); status[i].setFont(new Font("Arial",15));
        }
        vBox1.setAlignment(Pos.CENTER);
        vBox2.setAlignment(Pos.CENTER);
        switch (acType){
            case 2: vBox1.getChildren().addAll(name[0],type[0]);
                vBox2.getChildren().addAll(status[0],hp[0]);
                hbox.getChildren().addAll(h2,vBox1,vBox2);
                break;
            case 3: vBox1.getChildren().addAll(name[1],type[1]);
                vBox2.getChildren().addAll(status[1],hp[1]);
                hbox.getChildren().addAll(h3,vBox1,vBox2);
                break;
            case 4: vBox1.getChildren().addAll(name[2],type[2]);
                vBox2.getChildren().addAll(status[2],hp[2]);
                hbox.getChildren().addAll(h4,vBox1,vBox2);
                break;
            default: break;
        }
        hbox.setAlignment(Pos.CENTER);
        hbox.setPrefWidth(500);
        return stackPane;
    }
}