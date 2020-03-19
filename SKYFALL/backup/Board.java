import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class Board extends Parent {
    private VBox rows = new VBox();
    private boolean enemy = false;
    public int aircrafts = 3;

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

    public Cell getCell(int x, int y) {
        return (Cell)((HBox)rows.getChildren().get(y)).getChildren().get(x);
    }

    private boolean isValidPoint(int x, int y){
        if (0 <= x && x < 10 && 0 <= y && y < 10) return true;
        return false;
    }

    public boolean checkFourDirection(int x, int y){
        int[] dx = {1, -1, 0, 0};
        int[] dy = {0, 0, -1, 1};

        for(int i = 0; i < 4; i++){
            int xx = x + dx[i];
            int yy = y + dy[i];

            if ((isValidPoint(xx,yy))){
                Cell cell = getCell(xx,yy);
                if (cell.aircraft != null) return false;
            }
        }
        return true;
    }

    public boolean isOkToSetAirCraft(AirCraft airCraft, int x, int y){
        int type = airCraft.model;

        if (airCraft.vertical){
            for (int j = y; j < y + type; j++) {
                if (!isValidPoint(x, j)) return false;

                Cell cell = getCell(x,j);
                if (cell.aircraft != null) return false;

                if (!checkFourDirection(x,j)) return false;
            }
        }
        else {
            for (int i = x; i < x + type; i++) {
                if (!isValidPoint(i, y)) return false;

                Cell cell = getCell(i, y);
                if (cell.aircraft != null) return false;

                if (!checkFourDirection(i, y)) return false;
            }
        }
        return true;
    }

    public boolean setAirCraft(AirCraft airCraft, int x, int y){
        if (isOkToSetAirCraft(airCraft, x, y)){
            int type = airCraft.model;

            if (airCraft.vertical){
                for (int j = y; j < j + type; j++){
                    Cell cell = getCell(x, j);
                    cell.aircraft = airCraft;
                }
            }
            else {
                for (int i = x; i < i + type; i++){
                    Cell cell = getCell(i, y);
                    cell.aircraft = airCraft;
                }
            }
            return true;
        }
        return false;
    }

    public class Cell extends Rectangle {
        public int x, y;
        public AirCraft aircraft = null;
        public boolean wasShot = false;

        private Board board;

        public Cell(int x, int y, Board board) {
            this.x = x;
            this.y = y;
            this.board = board;
        }

        public boolean shoot() {
            wasShot = true;

            if (aircraft != null) {
                aircraft.shoot_center();
                if (!aircraft.isAlive()) {
                    board.aircrafts--;
                }
                return true;
            }
            else{
                int[] dx = {1, -1, 0, 0};
                int[] dy = {0, 0, -1, 1};
                boolean da_ban_trung = false;
                for(int i = 0; i < 4; i++){
                    int xx = x + dx[i];
                    int yy = y + dy[i];

                    if ((isValidPoint(xx,yy))){
                        Cell cell = getCell(xx,yy);
                        if (cell.aircraft != null){
                            cell.aircraft.shoot_neighbor();
                            if (!cell.aircraft.isAlive())
                                board.aircrafts--;
                            da_ban_trung = true;
                        }
                    }
                }
                if (da_ban_trung) return true;
            }
            return false;
        }
    }
}
