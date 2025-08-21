import processing.core.PApplet;
import processing.core.PImage;
import java.util.List;

public class Sketch extends PApplet {
    ShogiGame game = new ShogiGame();
    PImage mono_vector;
    Piece selectedPiece = null;
    int selectedRow = -1;
    int selectedCol = -1;
    int fromRow;
    int fromCol;
    public void settings() {
        size(550, 700);
    }
    public void setup() {
        mono_vector = loadImage("mono_vector.png");
    }
    public void draw() {
//        System.out.println(game.dropRow);
//        System.out.println(game.matchRowToPiece(game.dropRow));

        background(255);
        //draw 9x9 board
        fill(210, 180, 140);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                rect(i * 50, j * 50, 50, 50);
            }
        }
        fill(255);
        for(int i = 0; i < 2 ; i++){
            for(int j = 0; j < 14 ; j++) {
                rect(450 + i * 50, j * 50, 50, 50);
            }
        }

        // Highlight selected square
        if (selectedRow != -1 && selectedCol != -1) {
            fill(255, 0, 0, 100);
            rect(selectedCol * 50, selectedRow * 50, 50, 50);
        }
        // Highlight selected dropRow
        if (game.dropRow != -1) {
            fill(255, 0, 0, 100);
            rect(450, game.dropRow * 50, 100, 50);
        }
        // Highlight legal moves for the selected piece
        if (selectedPiece != null) {
            List<Square> legalMoves = game.getMoves(selectedRow, selectedCol);
            for (Square move : legalMoves) {
                fill(0, 255, 0, 100);
                rect(move.col * 50, move.row * 50, 50, 50);
            }
        }

        //draw pieces
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (game.board[i][j] != null && game.board[i][j].occupant != null) {
                    int y = game.board[i][j].occupant.row;
                    int x = game.board[i][j].occupant.col;
                    copy(mono_vector, 90 * x, 100 * y, 90, 100, 50 * j, 50 * i, 50, 50);
                }
            }
        }
        //draw drop pieces
        for(int j = 0; j < 7 ; j++) {
            copy(mono_vector, 90 * (j + 1), 100 * 2, 90, 100, 450, 50 * (j),50, 50 );
        }
        for(int j = 7; j > 0; j --) {
            copy(mono_vector, 90 * (j), 0, 90, 100, 450, 700 - (50 * j), 50, 50);
        }
        //win screens
        if (game.whiteWon) {
            fill(0);
            textSize(32);
            textAlign(CENTER, CENTER);
            text("White Wins!", 225, 600);
        }
        if (game.blackWon) {
            fill(0);
            textSize(32);
            textAlign(CENTER, CENTER);
            text("Black Wins!", 225,500);
        }
        //Display turn
        if(game.turn % 2 == 1 && !game.whiteWon && !game.blackWon){
            fill(0);
            textSize(32);
            textAlign(CENTER, CENTER);
            text("White's turn", 225, 500);
        }
        if(game.turn % 2 == 0 && !game.whiteWon && !game.blackWon){
            fill(0);
            textSize(32);
            textAlign(CENTER, CENTER);
            text("Black's turn", 225, 500);
        }
        //Display captured pieces
        if(game.countPieceTypesCaptured(game.blackHand).get('n') != null) {
            text(game.countPieceTypesCaptured(game.blackHand).get('n').toString(), 525, 475);
        }
        if(game.countPieceTypesCaptured(game.blackHand).get('p') != null) {
            text(game.countPieceTypesCaptured(game.blackHand).get('p').toString(), 525, 375);
        }
        if(game.countPieceTypesCaptured(game.blackHand).get('l') != null) {
            text(game.countPieceTypesCaptured(game.blackHand).get('l').toString(), 525, 425);
        }
        if(game.countPieceTypesCaptured(game.blackHand).get('s') != null) {
            text(game.countPieceTypesCaptured(game.blackHand).get('s').toString(), 525, 525);
        }
        if(game.countPieceTypesCaptured(game.blackHand).get('g') != null) {
            text(game.countPieceTypesCaptured(game.blackHand).get('g').toString(), 525, 575);
        }
        if(game.countPieceTypesCaptured(game.blackHand).get('b') != null) {
            text(game.countPieceTypesCaptured(game.blackHand).get('b').toString(), 525, 625);
        }
        if(game.countPieceTypesCaptured(game.blackHand).get('r') != null) {
            text(game.countPieceTypesCaptured(game.blackHand).get('r').toString(), 525, 675);
        }
        if(game.countPieceTypesCaptured(game.whiteHand).get('N') != null) {
            text(game.countPieceTypesCaptured(game.whiteHand).get('N').toString(), 525, 225);
        }
        if(game.countPieceTypesCaptured(game.whiteHand).get('P') != null) {
            text(game.countPieceTypesCaptured(game.whiteHand).get('P').toString(), 525, 325);
        }
        if(game.countPieceTypesCaptured(game.whiteHand).get('L') != null) {
            text(game.countPieceTypesCaptured(game.whiteHand).get('L').toString(), 525, 275);
        }
        if(game.countPieceTypesCaptured(game.whiteHand).get('S') != null) {
            text(game.countPieceTypesCaptured(game.whiteHand).get('S').toString(), 525, 175);
        }
        if(game.countPieceTypesCaptured(game.whiteHand).get('G') != null) {
            text(game.countPieceTypesCaptured(game.whiteHand).get('G').toString(), 525, 125);
        }
        if(game.countPieceTypesCaptured(game.whiteHand).get('B') != null) {
            text(game.countPieceTypesCaptured(game.whiteHand).get('B').toString(), 525, 75);
        }
        if(game.countPieceTypesCaptured(game.whiteHand).get('R') != null) {
            text(game.countPieceTypesCaptured(game.whiteHand).get('R').toString(), 525, 25);
        }

        }
    public void mousePressed() {
        if(mouseX < 450 && mouseY < 450 && mouseX > 0 && mouseY > 0){
        int col = mouseX / 50;
        int row = mouseY / 50;
        if (game.dropRow == -1 && selectedPiece == null && game.board[row][col] != null && game.board[row][col].occupant != null && game.board[row][col].occupant.color.equals("black") && game.turn % 2 == 0){
            selectedPiece = game.board[row][col].occupant;
            selectedRow = row;
            selectedCol = col;
            fromRow = row;
            fromCol = col;
        }
        else if (game.dropRow == -1 && selectedPiece == null && game.board[row][col] != null && game.board[row][col].occupant != null && game.board[row][col].occupant.color.equals("white") && game.turn % 2 == 1) {
            selectedPiece = game.board[row][col].occupant;
            selectedRow = row;
            selectedCol = col;
            fromRow = row;
            fromCol = col;
        }
        else if (selectedPiece != null) {
            List<Square> legalMoves = game.getMoves(selectedRow, selectedCol);
            boolean isHighlighted = false;
            for (Square move : legalMoves) {
                if (move.row == row && move.col == col) {
                    isHighlighted = true;
                    break;
                }
            }
            if (isHighlighted) {
                game.movePiece(fromRow, fromCol, row, col);
            }
            selectedPiece = null;
            selectedRow = -1;
            selectedCol = -1;
        }
        else if (game.dropRow > -1) {
            selectedRow = mouseY/50;
            selectedCol = mouseX/50;
            game.dropPiece(game.dropRow, selectedRow, selectedCol);
            game.dropRow = -1;
        }

        }
        else if (game.turn%2 == 1 && mouseX > 450 && mouseX < 550 && mouseY > 0 && mouseY < 350) {
            if (selectedPiece == null) {
                int clickedDropRow = mouseY / 50;
                if (game.dropRow == -1 || game.dropRow != clickedDropRow) {
                    game.dropRow = clickedDropRow;
                } else {
                    game.dropRow = -1;
                }
            }
        }
        else if (game.turn%2 == 0 && mouseX > 450 && mouseX < 550 && mouseY < 700 && mouseY > 350) {
            if (selectedPiece == null) {
                int clickedDropRow = mouseY / 50;
                if (game.dropRow == -1 || game.dropRow != clickedDropRow) {
                    game.dropRow = clickedDropRow;
                } else {
                    game.dropRow = -1;
                }
            }
        }
    }
    public static void main(String[] args) {
        PApplet.main("Sketch");
    }
}