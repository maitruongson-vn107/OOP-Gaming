package com.company.skyfall.model;

import com.company.skyfall.view.ACToSet;
import com.company.skyfall.view.OptionLayout;
import com.company.skyfall.view.PlayLayout;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.util.Random;

public class Board extends Parent {
    private VBox rows = new VBox();
    private boolean enemy;
    public static double soundLevel = 1;
    private int airCrafts = 3;
    private int numBulletType2 = 3;
    private int numBulletType3 = 1;
    public  boolean didRepo = false;
    public  AirCraft acToMove = null;
    public Cell preCell = new Cell(10, 10, this);

    private static MediaPlayer soundPlayer = new MediaPlayer(new Media(
            new File(new File("src/com/company/skyfall/view/explosion.mp3").getAbsolutePath()).toURI().toString()
    ));

    public static void playSound() {
        soundPlayer.stop();
        soundPlayer.play();
        soundPlayer.setVolume(Board.soundLevel);
    }
    /**
     * Setting up and checking condition for Board
     */

    // Build Board
    public Board(boolean enemy, EventHandler<? super MouseEvent> handler) {
        this.enemy = enemy;
        for (int y = 0; y < 10; y++) {
            HBox row = new HBox();
            for (int x = 0; x < 10; x++) {
                Cell c = new Cell(x, y, this);
                c.setOnMouseClicked(handler);
                row.getChildren().add(c);
            }
            rows.getChildren().add(row);
        }
        getChildren().add(rows);
    }
    public   EventHandler onDragDetected = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            Dragboard db;
            ClipboardContent ct = new ClipboardContent();
            try {
                Cell c = (Cell) (event.getSource());
                if (!PlayLayout.running) return;
                db = c.startDragAndDrop(TransferMode.ANY);
                if (!didRepo && c.airCraft != null && c.airCraft.getHP()<c.airCraft.getType()*100 && c.airCraft.getHP()>0) {
                    int gap = (c.airCraft.isVertical())?(c.y-c.airCraft.getHead().y):(c.x-c.airCraft.getHead().x);
                    ct.putString(String.valueOf(gap)+"00");
                    acToMove = c.airCraft;
                    db.setContent(ct);
                    event.consume();
                } } catch (Exception ex){
                if (PlayLayout.running) return;
                ImageView imgv = (ImageView) (event.getSource());
                db = imgv.startDragAndDrop(TransferMode.ANY);
                String dragInfor = "";
                if (imgv == ACToSet.v2) dragInfor = String.valueOf((int )(event.getSceneY()-648)/30)+"2"+"1";
                if (imgv == ACToSet.h2) dragInfor = String.valueOf((int )(event.getSceneX()-725)/30)+"2"+"0";
                if (imgv == ACToSet.v3) dragInfor = String.valueOf((int )(event.getSceneY()-648)/30)+"3"+"1";
                if (imgv == ACToSet.h3) dragInfor = String.valueOf((int )(event.getSceneX()-725)/30)+"3"+"0";
                if (imgv == ACToSet.v4) dragInfor = String.valueOf((int )(event.getSceneY()-648)/30)+"4"+"1";
                if (imgv == ACToSet.h4) dragInfor = String.valueOf((int )(event.getSceneX()-725)/30)+"4"+"0";
                ct.putString(dragInfor);
                db.setContent(ct);
                event.consume();
            }
        }
    };
    public  EventHandler onDragOver = new EventHandler<DragEvent>() {
        @Override
        public void handle(DragEvent event) {
            Cell c = (Cell) event.getSource();
            event.acceptTransferModes(TransferMode.ANY);
            String dragInfor = event.getDragboard().getString();
            if (dragInfor.charAt(1) == '0'){
                if (acToMove != null) {
                    boolean ver = acToMove.isVertical();
                    int gap = Integer.parseInt(String.valueOf(dragInfor.charAt(0)));
                    for (int k = 0; k <= 2;k++){
                        if (PlayLayout.AC[k] != null && PlayLayout.AC[k] != acToMove)
                            try{
                                if (PlayLayout.AC[k].getHP() > 0) changeImagePlayerAlive(PlayLayout.AC[k].getHead());
                                else changeImagePlayerDead(PlayLayout.AC[k].getHead());    }
                            catch (Exception ex) {
                                ex.printStackTrace();
                            }
                    }
                    for (int k = 0; k < 10; k++)
                        for (int h = 0; h < 10; h++)
                            if (getCell(k,h).getAirCraft() == null || getCell(k,h).getAirCraft() == acToMove) {
                                getCell(k, h).setFill(Color.TRANSPARENT);
                                getCell(k, h).wasShot = false;
                            }
                    if (ver)
                        try {changeImagePlayerAlive( c.x, c.y-gap,true,acToMove.getType());}
                        catch (Exception ex){
                            ex.printStackTrace();
                        }
                    else {
                        try {
                            changeImagePlayerAlive(c.x-gap,c.y,false,acToMove.getType());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
            else {
                boolean ver = (dragInfor.charAt(2) == '1');
                int gap = Integer.parseInt(String.valueOf(dragInfor.charAt(0)));
                int type = Integer.parseInt(String.valueOf(dragInfor.charAt(1)));
                for (int k = 0; k <= 2; k++){
                    if (PlayLayout.AC[k] != null && PlayLayout.AC[k] !=acToMove )
                        try{  changeImagePlayerAlive(PlayLayout.AC[k].getHead()); }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                }
                for (int k = 0; k < 10;k++)
                    for (int h = 0; h < 10; h++)
                        if (getCell(k,h).getAirCraft() == null ) {
                            getCell(k, h).setFill(Color.TRANSPARENT);
                            getCell(k, h).wasShot = false;
                        }
                if (ver)
                    try {changeImagePlayerAlive( c.x, c.y-gap,true,type);}
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                else {
                    try {
                        changeImagePlayerAlive(c.x-gap,c.y,false,type);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            }
            event.consume();
        }
    };
    public EventHandler onDragDropped = new EventHandler<DragEvent>() {
        @Override
        public void handle(DragEvent event) {
            Cell c = (Cell) event.getSource();
            boolean suc = false;
            boolean ver = true;
            int gap;
            String dragInfor = event.getDragboard().getString();
            if (dragInfor.charAt(1) == '0'){
                if (acToMove != null) {
                    ver = acToMove.isVertical();
                    gap = Integer.parseInt(String.valueOf(dragInfor.charAt(0)));
                    if (ver)
                        suc = reposAirCraft(acToMove, c.x, c.y - gap);
                    else suc = reposAirCraft(acToMove, c.x - gap, c.y);
                }
                event.setDropCompleted(true);
                event.consume();
                if (!suc) {
                    for (int k = 0; k <= 2;k++){
                        try{
                            if (PlayLayout.AC[k].getHP() > 0) changeImagePlayerAlive(PlayLayout.AC[k].getHead());
                            else changeImagePlayerDead(PlayLayout.AC[k].getHead());    }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                } if (suc) {
                    PlayLayout.enemyTurn = true;
                    if (OptionLayout.getLevel()) {
                        PlayLayout.enemyMoveEasy();
                    }
                    else {
                        PlayLayout.enemyMoveHard();
                    }
                }
                for (int k = 0; k < 10;k++)
                    for (int h = 0; h < 10; h++)
                        if (getCell(k,h).getAirCraft() == null ) {
                            getCell(k, h).setFill(Color.TRANSPARENT);
                            getCell(k, h).wasShot = false;
                        }
            }
            else {  gap = Integer.parseInt(String.valueOf(dragInfor.charAt(0)));
                ver = (dragInfor.charAt(2) == '1');
                int type = Integer.parseInt(String.valueOf(dragInfor.charAt(1)));
                AirCraft aircr = new AirCraft(type,ver);
                if (ver)
                {
                    if (isOkToSetAirCraft(aircr,c.x,c.y-gap))
                    {
                        setAirCraft(aircr,c.x,c.y-gap);
                        PlayLayout.AC[type-2] = getCell(c.x,c.y-gap).getAirCraft();
                        PlayLayout.acSet[type-2] = true;
                        switch (type) {
                            case 4: PlayLayout.acHBox.getChildren().removeAll(ACToSet.v4);
                                PlayLayout.acVBox.getChildren().removeAll(ACToSet.h4);
                                break;
                            case 3: PlayLayout.acHBox.getChildren().removeAll(ACToSet.v3);
                                PlayLayout.acVBox.getChildren().removeAll(ACToSet.h3);
                                break;
                            case 2:
                                PlayLayout.acHBox.getChildren().removeAll(ACToSet.v2);
                                PlayLayout.acVBox.getChildren().removeAll(ACToSet.h2);
                                break;

                        }
                    }
                } else {
                    if (isOkToSetAirCraft(aircr,c.x-gap,c.y)) {
                        setAirCraft(aircr,c.x-gap,c.y);
                        PlayLayout.AC[type-2] = aircr;
                        PlayLayout.acSet[type-2] = true;
                        switch (type) {
                            case 4: PlayLayout.acHBox.getChildren().removeAll(ACToSet.v4);
                                PlayLayout.acVBox.getChildren().removeAll(ACToSet.h4);
                                break;
                            case 3: PlayLayout.acHBox.getChildren().removeAll(ACToSet.v3);
                                PlayLayout.acVBox.getChildren().removeAll(ACToSet.h3);
                                break;
                            case 2:
                                PlayLayout.acHBox.getChildren().removeAll(ACToSet.v2);
                                PlayLayout.acVBox.getChildren().removeAll(ACToSet.h2);
                                break;

                        }
                    }
                }
                for (int k = 0; k <= 2; k++) {
                    if (PlayLayout.AC[k] != null)
                        try {
                            changeImagePlayerAlive(PlayLayout.AC[k].getHead());
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                }
                for (int k = 0; k < 10;k++)
                    for (int h = 0; h < 10; h++)
                        if (getCell(k,h).getAirCraft() == null ) {
                            getCell(k, h).setFill(Color.TRANSPARENT);
                            getCell(k, h).wasShot = false;
                        }
                boolean start = true;
                for (int k = 0; k <= 2; k++){
                    if (!PlayLayout.acSet[k]) start = false;
                }
                if (start) PlayLayout.startGame();
                event.setDropCompleted(true);
                event.consume();
            }
        }
    };
    //add dragged and dropped ability to cell
    public void dragEffect(){
        for (int i = 0; i <= 9; i++)
            for (int j = 0; j <= 9; j++){
                Cell c = getCell(i,j);
                c.setOnDragDetected(onDragDetected);
                c.setOnDragOver(onDragOver);
                c.setOnDragDropped(onDragDropped);
            }
    }

    // Get position (x,y) on Board
    public Cell getCell(int x, int y) {
        try{
            return (Cell) ((HBox) rows.getChildren().get(y)).getChildren().get(x);
        } catch (Exception ex){

        }
        return null;
    }

    // Check validity of point (x,y)
    private boolean isValidPoint(int x, int y) {
        return 0 <= x && x < 10 && 0 <= y && y < 10;
    }

    // Check edge-shared cells around point (x,y)
    private boolean checkFourDirection(int x, int y) {
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        for (int i = 0; i < 4; i++) {
            int xx = x + dx[i];
            int yy = y + dy[i];

            if ((isValidPoint(xx, yy))) {
                Cell cell = getCell(xx, yy);
                if (cell.airCraft != null) return false;
            }
        }
        return true;
    }

    // Check condition to set AC on (x,y)
    public boolean isOkToSetAirCraft(AirCraft airCraft, int x, int y) {
        int type = airCraft.getType();

        if (airCraft.isVertical()) {
            for (int j = y; j < y + type; j++) {
                if (!isValidPoint(x, j)) return false;

                Cell cell = getCell(x, j);
                if (cell.airCraft != null) return false;

                if (!checkFourDirection(x, j)) return false;
            }
        } else {
            for (int i = x; i < x + type; i++) {
                if (!isValidPoint(i, y)) return false;

                Cell cell = getCell(i, y);
                if (cell.airCraft != null) return false;

                if (!checkFourDirection(i, y)) return false;
            }
        }
        return true;
    }

    // Set AC on point (x,y)
    public boolean  setAirCraft(AirCraft airCraft, int x, int y) {
        if (isOkToSetAirCraft(airCraft, x, y)) {
            int type = airCraft.getType();
            airCraft.setHead(getCell(x, y));
            if (airCraft.isVertical()) {
                for (int j = y; j < y + type; j++) {
                    Cell cell = getCell(x, j);
                    cell.airCraft = airCraft;
                    if (enemy) {
                        cell.setFill(Color.TRANSPARENT);
                        cell.setStroke(Color.WHITE);
                    }
                }
            } else {
                for (int i = x; i < x + type; i++) {
                    Cell cell = getCell(i, y);
                    cell.airCraft = airCraft;
                    if (enemy) {
                        cell.setFill(Color.TRANSPARENT);
                        cell.setStroke(Color.WHITE);
                    }
                }
            }
            if (!this.enemy) changeImagePlayerAlive(getCell(x,y));
            return true;
        }
        return false;
    }
    //reposition aircraft
    public boolean reposAirCraft(AirCraft airCraft, int x, int y) {
        //AC being shot && difference of head position && reposition
        if (!airCraft.isDie() && airCraft.lostHP() && !didRepo
                && !airCraft.getHead().equals(getCell(x, y))) {

            //check new position's conditions
            if (isOkToSetAirCraft(airCraft, x, y)) {

                //turn current position's aircraft to null
                if (airCraft.isVertical()) {
                    for (int i = 0; i < airCraft.getType() ; i++) {
                        Cell cell = getCell(airCraft.getHead().x, airCraft.getHead().y + i);
                        cell.airCraft = null;
                        cell.wasShot = false;
                        cell.setFill(Color.TRANSPARENT);
                        cell.setStroke(Color.WHITE);

                        Cell cellTmp = getCell(x, y + i);
                        cellTmp.wasShot = false;
                    }
                }
                else {
                    for (int i = 0; i < airCraft.getType() ; i++) {
                        Cell cell = getCell(airCraft.getHead().x + i, airCraft.getHead().y);
                        cell.airCraft =null;
                        cell.wasShot = false;
                        cell.setFill(Color.TRANSPARENT);
                        cell.setStroke(Color.WHITE);

                        Cell cellTmp = getCell(x + i, y);
                        cellTmp.wasShot = false;
                    }

                }

                setAirCraft(airCraft, x, y);
                didRepo = true;
                return true;
            }
            return false;
        }
        return false;
    }
    public Cell lastAC() {
        if (airCrafts == 1) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    Cell cell = getCell(i, j);
                    if (cell.airCraft != null && cell.airCraft.lostHP() && !cell.airCraft.isDie()) {
                        return cell.airCraft.getHead();
                    }
                }
            }
        }
        return null;
    }

    public class Cell extends Rectangle {
        public int x, y;
        AirCraft airCraft = null;
        public boolean wasShot = false;

        private Board board;

        public Board getBoard() {
            return board;
        }

        Cell(int x, int y, Board board) {
            super(30, 30);
            this.x = x;
            this.y = y;
            this.board = board;
            this.wasShot = false;
            setFill(Color.TRANSPARENT);
            setStroke(Color.WHITE);
        }

        public AirCraft getAirCraft() {
            return airCraft;
        }

        /**
         * Shoot methods
         */
        //Bullet type 1
        public boolean shootType1() {
            Board.playSound();
            wasShot = true;
            if (airCraft != null) {
                if (airCraft.isDie()) return false;
                airCraft.hitType1();
                if (this.getBoard().enemy )
                    setFill(Color.rgb(255, 74, 54));
                else setStroke(Color.rgb(255, 74, 54));
                if (!airCraft.isAlive()) {
                    board.airCrafts--;
                    if (!this.getBoard().enemy) changeImagePlayerDead(this);
                    else changeImageEnemyDead(this);
                }

                return true;
            }
            setFill(Color.rgb(33, 233, 255));
            return false;
        }

        //Bullet type 2
        public boolean shootType2() {
            Board.playSound();
            boolean tmp = false;
            // 3*3 block
            int[] dx = {-1, -1, -1, 0, 0, 0, 1, 1, 1};
            int[] dy = {-1, 0, 1, -1, 0, 1, -1, 0, 1};

            for (int i = 0; i < 9; i++) {
                int xx = x + dx[i];
                int yy = y + dy[i];

                if ((isValidPoint(xx, yy))) {
                    Cell cell = getCell(xx, yy);

                    cell.wasShot = true;
                    if (cell.airCraft != null) {
                        if (cell.airCraft.isDie()) continue;
                        tmp = true;
                        cell.airCraft.hitType2();
                        if (cell.getBoard().enemy)
                            cell.setFill(Color.rgb(255, 233, 33));
                        else cell.setStroke(Color.rgb(255, 233, 33));
                        if (!cell.airCraft.isAlive()) {
                            board.airCrafts--;
                            if (!this.getBoard().enemy) changeImagePlayerDead(this);
                            else changeImageEnemyDead(this);
                        }
                    } else
                        cell.setFill(Color.rgb(33, 233, 255));
                }
            }
            return tmp;
        }

        // Bullet type 3
        public boolean shootType3() {
            Board.playSound();
            wasShot = true;
            if (airCraft != null) {
                if (airCraft.isDie()) return false;
                airCraft.setDie(true);
                board.airCrafts--;
                airCraft.hitType3();
                if (!this.getBoard().enemy) changeImagePlayerDead(this);
                else changeImageEnemyDead(this);
                return true;
            } else
                setFill(Color.rgb(44, 255, 47));
            return false;
        }
        // shooted by type 2
        public int countNumberOfCellHaveAirCraft() {
            int[] dx = {-1, -1, -1, 0, 0, 0, 1, 1, 1};
            int[] dy = {-1, 0, 1, -1, 0, 1, -1, 0, 1};
            int count = 0;

            for (int i = 0; i < 9; i++) {
                int xx = x + dx[i];
                int yy = y + dy[i];
                if (isValidPoint(xx, yy) && (getCell(xx, yy).getAirCraft() != null)) count++;

            }
            return count;
        }
    }
    public void changeImagePlayerAlive(int x,int y, boolean vertical,int type){
        Cell head = null;
        try {
            head = getCell(x,y);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (vertical) {
            switch (type) {
                case 2:
                    try{
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/2/v2.1.png").toString()))));
                    } catch (Exception ex){

                    }
                    try{
                        head = getCell(head.x, head.y + 1);
                    } catch (Exception ex){

                    }
                    try{
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/2/v2.2.png").toString()))));
                    } catch (Exception ex){

                    }
                    break;
                case 3:
                    try {
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/3/v3.1.png").toString()))));
                    } catch (Exception ex){

                    }
                    try {
                        head = getCell(head.x, head.y + 1);
                    } catch (Exception ex){

                    }
                    try {
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/3/v3.2.png").toString()))));
                    } catch (Exception ex){

                    }
                    try {
                        head = getCell(head.x, head.y + 1);
                    } catch (Exception ex){

                    }
                    try {
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/3/v3.3.png").toString()))));
                    } catch (Exception ex){

                    }
                    break;
                case 4:
                    try {
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/4/v4.1.png").toString()))));
                    } catch (Exception ex){

                    }
                    try {
                        head = getCell(head.x, head.y + 1);
                    } catch (Exception ex){

                    }
                    try {
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/4/v4.2.png").toString()))));
                    } catch (Exception ex){

                    }
                    try {
                        head = getCell(head.x, head.y + 1);
                    } catch (Exception ex){

                    }
                    try {
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/4/v4.3.png").toString()))));
                    } catch (Exception ex){

                    }
                    try {
                        head = getCell(head.x, head.y + 1);
                    } catch (Exception ex){

                    }
                    try {
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/4/v4.4.png").toString()))));
                    } catch (Exception ex){

                    }
                    break;
            }
        } else {
            switch (type) {
                case 2:
                    try {
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/2/h2.1.png").toString()))));
                    } catch (Exception ex){

                    }
                    try {
                        head = getCell(head.x + 1, head.y);
                    } catch (Exception ex){

                    }
                    try {
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/2/h2.2.png").toString()))));
                    } catch (Exception ex){

                    }
                    break;
                case 3:
                    try{
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/3/h3.1.png").toString()))));
                    }catch (Exception ex){

                    }
                    try{
                        head = getCell(head.x + 1, head.y);
                    }catch (Exception ex){

                    }
                    try{
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/3/h3.2.png").toString()))));
                    }catch (Exception ex){

                    }
                    try {
                        head = getCell(head.x + 1, head.y);
                    }catch (Exception ex){

                    }
                    try{
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/3/h3.3.png").toString()))));
                    } catch (Exception ex){

                    }
                    break;
                case 4:
                    try {
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/4/h4.1.png").toString()))));
                    } catch (Exception ex){

                    }
                    try {
                        head = getCell(head.x + 1, head.y);
                    } catch (Exception ex){

                    }
                    try {
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/4/h4.2.png").toString()))));
                    } catch (Exception ex){

                    }
                    try {
                        head = getCell(head.x + 1, head.y);
                    } catch (Exception ex){

                    }
                    try {
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/4/h4.3.png").toString()))));
                    } catch (Exception ex){

                    }
                    try {
                        head = getCell(head.x + 1, head.y);
                    } catch (Exception ex){

                    }
                    try {
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/4/h4.4.png").toString()))));
                    }catch (Exception ex){

                    }
                    break;
            }
        }

    }

    private void changeImageEnemyDead(Cell cell) {
        if (cell.airCraft != null) {
            Cell head = cell.airCraft.getHead();
            if (cell.airCraft.isVertical()) {
                switch (cell.airCraft.getType()) {
                    case 2:
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Enemy AC/Enemy AC dead/2/v2.1.png").toString()))));
                        head = getCell(head.x, head.y + 1);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Enemy AC/Enemy AC dead/2/v2.2.png").toString()))));
                        break;
                    case 3:
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Enemy AC/Enemy AC dead/3/v3.1.png").toString()))));
                        head = getCell(head.x, head.y + 1);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Enemy AC/Enemy AC dead/3/v3.2.png").toString()))));
                        head = getCell(head.x, head.y + 1);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Enemy AC/Enemy AC dead/3/v3.3.png").toString()))));
                        break;
                    case 4:
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Enemy AC/Enemy AC dead/4/v4.1.png").toString()))));
                        head = getCell(head.x, head.y + 1);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Enemy AC/Enemy AC dead/4/v4.2.png").toString()))));
                        head = getCell(head.x, head.y + 1);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Enemy AC/Enemy AC dead/4/v4.3.png").toString()))));
                        head = getCell(head.x, head.y + 1);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Enemy AC/Enemy AC dead/4/v4.4.png").toString()))));
                        break;
                }
            } else {
                switch (cell.airCraft.getType()) {
                    case 2:
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Enemy AC/Enemy AC dead/2/h2.1.png").toString()))));
                        head = getCell(head.x + 1, head.y);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Enemy AC/Enemy AC dead/2/h2.2.png").toString()))));
                        break;
                    case 3:
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Enemy AC/Enemy AC dead/3/h3.1.png").toString()))));
                        head = getCell(head.x + 1, head.y);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Enemy AC/Enemy AC dead/3/h3.2.png").toString()))));
                        head = getCell(head.x + 1, head.y);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Enemy AC/Enemy AC dead/3/h3.3.png").toString()))));
                        break;
                    case 4:
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Enemy AC/Enemy AC dead/4/h4.1.png").toString()))));
                        head = getCell(head.x + 1, head.y);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Enemy AC/Enemy AC dead/4/h4.2.png").toString()))));
                        head = getCell(head.x + 1, head.y);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Enemy AC/Enemy AC dead/4/h4.3.png").toString()))));
                        head = getCell(head.x + 1, head.y);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Enemy AC/Enemy AC dead/4/h4.4.png").toString()))));
                        break;
                }
            }
        }
    }
    private void changeImagePlayerAlive(Cell cell) {
        if(cell.airCraft != null) {

            Cell head = cell.airCraft.getHead();
            if (cell.airCraft.isVertical()) {
                switch (cell.airCraft.getType()) {
                    case 2:
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/2/v2.1.png").toString()))));
                        head = getCell(head.x, head.y + 1);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/2/v2.2.png").toString()))));
                        break;
                    case 3:
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/3/v3.1.png").toString()))));
                        head = getCell(head.x, head.y + 1);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/3/v3.2.png").toString()))));
                        head = getCell(head.x, head.y + 1);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/3/v3.3.png").toString()))));
                        break;
                    case 4:
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/4/v4.1.png").toString()))));
                        head = getCell(head.x, head.y + 1);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/4/v4.2.png").toString()))));
                        head = getCell(head.x, head.y + 1);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/4/v4.3.png").toString()))));
                        head = getCell(head.x, head.y + 1);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/4/v4.4.png").toString()))));
                        break;
                }
            } else {
                switch (cell.airCraft.getType()) {
                    case 2:
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/2/h2.1.png").toString()))));
                        head = getCell(head.x + 1, head.y);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/2/h2.2.png").toString()))));
                        break;
                    case 3:
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/3/h3.1.png").toString()))));
                        head = getCell(head.x + 1, head.y);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/3/h3.2.png").toString()))));
                        head = getCell(head.x + 1, head.y);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/3/h3.3.png").toString()))));
                        break;
                    case 4:
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/4/h4.1.png").toString()))));
                        head = getCell(head.x + 1, head.y);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/4/h4.2.png").toString()))));
                        head = getCell(head.x + 1, head.y);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/4/h4.3.png").toString()))));
                        head = getCell(head.x + 1, head.y);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC alive/4/h4.4.png").toString()))));
                        break;
                }
            }
        }
    }
    private void changeImagePlayerDead(Cell cell) {
        if (cell.airCraft != null) {
            Cell head = cell.airCraft.getHead();
            if (cell.airCraft.isVertical()) {
                switch (cell.airCraft.getType()) {
                    case 2:
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC dead/2/v2.1.png").toString()))));
                        head = getCell(head.x, head.y + 1);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC dead/2/v2.2.png").toString()))));
                        break;
                    case 3:
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC dead/3/v3.1.png").toString()))));
                        head = getCell(head.x, head.y + 1);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC dead/3/v3.2.png").toString()))));
                        head = getCell(head.x, head.y + 1);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC dead/3/v3.3.png").toString()))));
                        break;
                    case 4:
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC dead/4/v4.1.png").toString()))));
                        head = getCell(head.x, head.y + 1);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC dead/4/v4.2.png").toString()))));
                        head = getCell(head.x, head.y + 1);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC dead/4/v4.3.png").toString()))));
                        head = getCell(head.x, head.y + 1);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC dead/4/v4.4.png").toString()))));
                        break;
                }
            } else {
                switch (cell.airCraft.getType()) {
                    case 2:
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC dead/2/h2.1.png").toString()))));
                        head = getCell(head.x + 1, head.y);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC dead/2/h2.2.png").toString()))));
                        break;
                    case 3:
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC dead/3/h3.1.png").toString()))));
                        head = getCell(head.x + 1, head.y);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC dead/3/h3.2.png").toString()))));
                        head = getCell(head.x + 1, head.y);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC dead/3/h3.3.png").toString()))));
                        break;
                    case 4:
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC dead/4/h4.1.png").toString()))));
                        head = getCell(head.x + 1, head.y);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC dead/4/h4.2.png").toString()))));
                        head = getCell(head.x + 1, head.y);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC dead/4/h4.3.png").toString()))));
                        head = getCell(head.x + 1, head.y);
                        head.setFill(new ImagePattern(new Image((getClass().getResource(
                                "../view/aircraft/Player AC/Player AC dead/4/h4.4.png").toString()))));
                        break;
                }
            }
        }
    }

    // check the 3*3 block on (x,y) to shot by bullet 3
    public  boolean isAbleToShotThisCell(int x, int y) {
        int[] dx = {-1, -1, -1, 0, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 0, 1, -1, 0, 1};

        for (int i = 0; i < 9; i++) {
            int xx = x + dx[i];
            int yy = y + dy[i];

            if ((isValidPoint(xx, yy))) {
                Cell cell = getCell(xx, yy);
                if (cell.wasShot == true) return false;
            }
        }
        return true;
    }

    // find Alive AC and Was shot
    public Cell findAliveAirCraft(){
        for(int i = 0; i < 10; i++)
            for(int j = 0; j < 10; j++){
                Cell cell = getCell(i,j);
                if (cell.airCraft != null && cell.wasShot && !cell.airCraft.isDie()) return cell;
            }
        Cell cell = new Cell(10,10,this);
        return cell;
    }

    public Cell findEdgeSharedCell(int x, int y){
        Random random = new Random();

        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        for (int i = 0; i < 4; i++) {
            int xx = x + dx[i];
            int yy = y + dy[i];
            if (isValidPoint(xx,yy)){
                Cell cell = getCell(xx,yy);
                if (cell.airCraft != null && cell.wasShot)
                    return cell;
            }
        }
        while (true){
            int tmp = random.nextInt(4);
            int xx = x + dx[tmp];
            int yy = y + dy[tmp];
            if (isValidPoint(xx,yy)){
                Cell cell = getCell(xx,yy);
                return cell;
            }
        }
    }

    public int countNumberOfEdgeShared(int x, int y) {
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};
        int count = 0;

        for (int i = 0; i < 4; i++) {
            int xx = x + dx[i];
            int yy = y + dy[i];
            if (isValidPoint(xx, yy) && !getCell(xx, yy).wasShot) count++;

        }
        return count;
    }

    public int findMaxNumberOfEdgeShared(){
        int max = 0;
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        for(int x = 0; x < 10; x++)
            for(int y = 0; y < 10; y++)
                if (!getCell(x,y).wasShot){
                    int count = 0;
                    for(int i = 0; i < 4; i++) {
                        int xx = x + dx[i];
                        int yy = y + dy[i];
                        if (isValidPoint(xx, yy) && !getCell(xx,yy).wasShot) count ++;

                    }
                    if (count > max) max = count;
                }
        return max;
    }

    public int getAirCrafts() {
        return airCrafts;
    }

    public int getNumBulletType2() {
        return numBulletType2;
    }

    public void setNumBulletType2(int numBulletType2) {
        this.numBulletType2 = numBulletType2;
    }

    public int getNumBulletType3() {
        return numBulletType3;
    }

    public void setNumBulletType3(int numBulletType3) {
        this.numBulletType3 = numBulletType3;
    }

}
