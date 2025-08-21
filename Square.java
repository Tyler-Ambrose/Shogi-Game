import java.util.HashMap;
import java.util.ArrayList;

public class Square {
    public int row;
    public int col;
    public Piece occupant = null;

    public Square(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Square getNeighbor(String dir) {
        int newRow = row;
        int newCol = col;

        switch (dir) {
            case "N":
                newRow--;
                break;
            case "E":
                newCol++;
                break;
            case "S":
                newRow++;
                break;
            case "W":
                newCol--;
                break;
            case "NE":
                newRow--;
                newCol++;
                break;
            case "NW":
                newRow--;
                newCol--;
                break;
            case "SE":
                newRow++;
                newCol++;
                break;
            case "SW":
                newRow++;
                newCol--;
                break;
            case "NNW":
                newRow-=2;
                newCol--;
                break;
            case "NNE":
                newRow-=2;
                newCol++;
                break;
            case "SSW":
                newRow+=2;
                newCol--;
                break;
            case "SSE":
                newRow+=2;
                newCol++;
                break;

        }

        if (isValidPosition(newRow, newCol)) {
            return new Square(newRow, newCol);
        } else {
            return null;
        }
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 9 && col >= 0 && col < 9;
    }


}

