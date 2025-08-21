import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ShogiGame {
    public Square[][] board = new Square[9][9];
    int turn;
    public ArrayList<Piece> whiteHand = new ArrayList<>();
    public ArrayList<Piece> blackHand = new ArrayList<>();
    public boolean whiteWon = false;
    public boolean blackWon = false;
    int dropRow = -1;


    ShogiGame() {
        this("0.LNSGKGSNL|1R5B1|PPPPPPPPP|9|9|9|ppppppppp|1b5r1|lnsgkgsnl");
    }

    ShogiGame(String boardString) {
        String[] TB = boardString.split("\\.");
        turn = Integer.parseInt(TB[0]);
        String[] rowString = TB[1].split("\\|");
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                Square square = new Square(r, c);
                board[r][c] = square;
            }
        }
        for (int r = 0; r < 9; r++) {
            char[] row = rowString[r].toCharArray();
            int c = 0;
            for (char value : row) {
                if (Character.isDigit(value)) {
                    c += Character.getNumericValue(value);
                    continue;
                }
                if (Character.isLetter(value)) {
                    getSquare(r, c).occupant = new Piece(value);
                    c++;
                }
            }
        }
    }

    public Square getSquare(int row, int column) {
        if (isValidPosition(row, column)) {
            return board[row][column];
        }
        return null;
    }

    public boolean isValidPosition(int row, int column) {
        return row >= 0 && row < 9 && column >= 0 && column < 9;
    }
    public boolean isPromotable(Piece piece, int row) {
        if (piece.label == 'k' || piece.label == 'K' || piece.label == 'g' || piece.label == 'G') {
            return false;
        }
        if (!Character.isUpperCase(piece.label) && row < 3) {
            return true;
        }
        return Character.isUpperCase(piece.label) && row > 5;
    }
    public ArrayList<Square> getOrthogonalMoves(int row, int col, String dir) {
        ArrayList<Square> moves = new ArrayList<>();
        Square currentSquare = getSquare(row, col);
        if (dir.equals("N")) {
            for (int r = row - 1; r >= 0; r--) {
                Square nextSquare = getSquare(r, col);
                if (nextSquare != null) {
                    if (nextSquare.occupant == null) {
                        moves.add(nextSquare);
                    } else {
                        if (!nextSquare.occupant.color.equals(currentSquare.occupant.color)) {
                            moves.add(nextSquare);
                        }
                        break;
                    }
                }
            }
        }
        if (dir.equals("S")) {
            for (int r = row + 1; r < 9; r++) {
                Square nextSquare = getSquare(r, col);
                if (nextSquare != null) {
                    if (nextSquare.occupant == null) {
                        moves.add(nextSquare);
                    } else {
                        if (!nextSquare.occupant.color.equals(currentSquare.occupant.color)) {
                            moves.add(nextSquare);
                        }
                        break;
                    }
                }
            }
        }
        if (dir.equals("E")) {
            for (int c = col - 1; c >= 0; c--) {
                Square nextSquare = getSquare(row, c);
                if (nextSquare != null) {
                    if (nextSquare.occupant == null) {
                        moves.add(nextSquare);
                    } else {
                        if (!nextSquare.occupant.color.equals(currentSquare.occupant.color)) {
                            moves.add(nextSquare);
                        }
                        break;
                    }
                }
            }
        }
        if (dir.equals("W")) {
            for (int c = col + 1; c < 9; c++) {
                Square nextSquare = getSquare(row, c);
                if (nextSquare != null) {
                    if (nextSquare.occupant == null) {
                        moves.add(nextSquare);
                    } else {
                        if (!nextSquare.occupant.color.equals(currentSquare.occupant.color)) {
                            moves.add(nextSquare);
                        }
                        break;  // Stop scanning in this direction after encountering an obstacle
                    }
                }
            }

        }
        return moves;
    }
    public ArrayList<Square> getDiagonalMoves(int row, int col, String dir) {
        ArrayList<Square> moves = new ArrayList<>();
        Square currentSquare = getSquare(row, col);

        if (dir.equals("NE")) {
            for (int r = row - 1, c = col + 1; r >= 0 && c < 9; r--, c++) {
                Square nextSquare = getSquare(r, c);
                if (nextSquare != null) {
                    if (nextSquare.occupant == null) {
                        moves.add(nextSquare);
                    } else {
                        if (!nextSquare.occupant.color.equals(currentSquare.occupant.color)) {
                            moves.add(nextSquare);
                        }
                        break;
                    }
                }
            }
        }

        if (dir.equals("NW")) {
            for (int r = row - 1, c = col - 1; r >= 0 && c >= 0; r--, c--) {
                Square nextSquare = getSquare(r, c);
                if (nextSquare != null) {
                    if (nextSquare.occupant == null) {
                        moves.add(nextSquare);
                    } else {
                        if (!nextSquare.occupant.color.equals(currentSquare.occupant.color)) {
                            moves.add(nextSquare);
                        }
                        break;
                    }
                }
            }
        }

        if (dir.equals("SE")) {
            for (int r = row + 1, c = col + 1; r < 9 && c < 9; r++, c++) {
                Square nextSquare = getSquare(r, c);
                if (nextSquare != null) {
                    if (nextSquare.occupant == null) {
                        moves.add(nextSquare);
                    } else {
                        if (!nextSquare.occupant.color.equals(currentSquare.occupant.color)) {
                            moves.add(nextSquare);
                        }
                        break;
                    }
                }
            }
        }

        if (dir.equals("SW")) {
            for (int r = row + 1, c = col - 1; r < 9 && c >= 0; r++, c--) {
                Square nextSquare = getSquare(r, c);
                if (nextSquare != null) {
                    if (nextSquare.occupant == null) {
                        moves.add(nextSquare);
                    } else {
                        if (!nextSquare.occupant.color.equals(currentSquare.occupant.color)) {
                            moves.add(nextSquare);
                        }
                        break;
                    }
                }
            }
        }
        return moves;
    }
    public ArrayList<Square> getMoves(int row, int col) {
        ArrayList<Square> moves = new ArrayList<>();
        Square currentSquare = getSquare(row, col);
        if (currentSquare.occupant != null) {
            Piece piece = currentSquare.occupant;
            // black pawn
            if (piece.label == 'p') {
                Square northSquare = getSquare(currentSquare.row - 1, currentSquare.col);
                if (northSquare.occupant == null) {
                    moves.add(northSquare);
                }
                if (northSquare.occupant != null && !northSquare.occupant.color.equals(piece.color)) {
                    moves.add(northSquare);
                }
            }
            // white pawn
            if (piece.label == 'P') {
                Square southSquare = getSquare(currentSquare.row + 1, currentSquare.col);
                if (southSquare.occupant == null) {
                    moves.add(southSquare);
                }
                if (southSquare.occupant != null && !southSquare.occupant.color.equals(piece.color)) {
                    moves.add(southSquare);
                }
            }
            //kings
            if (piece.label == 'k' || piece.label == 'K') {
                Square[] Squares = {/*N,E,S,W,NW,NE,SW,SE*/
                        getSquare(currentSquare.row - 1, currentSquare.col),
                        getSquare(currentSquare.row, currentSquare.col + 1),
                        getSquare(currentSquare.row, currentSquare.col - 1),
                        getSquare(currentSquare.row + 1, currentSquare.col),
                        getSquare(currentSquare.row - 1, currentSquare.col + 1),
                        getSquare(currentSquare.row - 1, currentSquare.col - 1),
                        getSquare(currentSquare.row + 1, currentSquare.col + 1),
                        getSquare(currentSquare.row + 1, currentSquare.col - 1)
                };
                for (Square sqr : Squares) {
                    if (sqr != null && sqr.occupant == null) {
                        moves.add(sqr);
                    }
                    if (sqr != null && sqr.occupant != null && !sqr.occupant.color.equals(piece.color)) {
                        moves.add(sqr);
                    }
                }
            }
            //black gold general
            if (piece.label == 'g' || piece.label == 't' || piece.label == 'm' || piece.label == 'c' || piece.label == 'z') {
                Square[] Squares = {/*N,E,S,W,NW,NE*/
                        getSquare(currentSquare.row - 1, currentSquare.col),
                        getSquare(currentSquare.row, currentSquare.col + 1),
                        getSquare(currentSquare.row, currentSquare.col - 1),
                        getSquare(currentSquare.row + 1, currentSquare.col),
                        getSquare(currentSquare.row - 1, currentSquare.col + 1),
                        getSquare(currentSquare.row - 1, currentSquare.col - 1),
                };
                for (Square sqr : Squares) {
                    if (sqr != null && sqr.occupant == null) {
                        moves.add(sqr);
                    }
                    if (sqr != null && sqr.occupant != null && !sqr.occupant.color.equals(piece.color)) {
                        moves.add(sqr);
                    }
                }
            }
            //white gold general
            if (piece.label == 'G' || piece.label == 'T' || piece.label == 'M' || piece.label == 'C' || piece.label == 'Z') {
                Square[] Squares = {/*N,E,S,W,SW,SE*/
                        getSquare(currentSquare.row - 1, currentSquare.col),
                        getSquare(currentSquare.row, currentSquare.col + 1),
                        getSquare(currentSquare.row, currentSquare.col - 1),
                        getSquare(currentSquare.row + 1, currentSquare.col),
                        getSquare(currentSquare.row + 1, currentSquare.col + 1),
                        getSquare(currentSquare.row + 1, currentSquare.col - 1),
                };
                for (Square sqr : Squares) {
                    if (sqr != null && sqr.occupant == null) {
                        moves.add(sqr);
                    }
                    if (sqr != null && sqr.occupant != null && !sqr.occupant.color.equals(piece.color)) {
                        moves.add(sqr);
                    }
                }
            }
            //black silver general
            if (piece.label == 's') {
                Square[] Squares = {/*N,NW,NE,SW,SE*/
                        getSquare(currentSquare.row - 1, currentSquare.col),
                        getSquare(currentSquare.row - 1, currentSquare.col + 1),
                        getSquare(currentSquare.row - 1, currentSquare.col - 1),
                        getSquare(currentSquare.row + 1, currentSquare.col + 1),
                        getSquare(currentSquare.row + 1, currentSquare.col - 1)
                };
                for (Square sqr : Squares) {
                    if (sqr != null && sqr.occupant == null) {
                        moves.add(sqr);
                    }
                    if (sqr != null && sqr.occupant != null && !sqr.occupant.color.equals(piece.color)) {
                        moves.add(sqr);
                    }
                }
            }
            //white silver general
            if (piece.label == 'S') {
                Square[] Squares = {/*S,NW,NE,SW,SE*/
                        getSquare(currentSquare.row + 1, currentSquare.col),
                        getSquare(currentSquare.row - 1, currentSquare.col + 1),
                        getSquare(currentSquare.row - 1, currentSquare.col - 1),
                        getSquare(currentSquare.row + 1, currentSquare.col + 1),
                        getSquare(currentSquare.row + 1, currentSquare.col - 1)
                };
                for (Square sqr : Squares) {
                    if (sqr != null && sqr.occupant == null) {
                        moves.add(sqr);
                    }
                    if (sqr != null && sqr.occupant != null && !sqr.occupant.color.equals(piece.color)) {
                        moves.add(sqr);
                    }
                }
            }
            //black lance
            if (piece.label == 'l') {
                moves.addAll(getOrthogonalMoves(currentSquare.row, currentSquare.col, "N"));
            }
            //white lance
            if (piece.label == 'L') {
                moves.addAll(getOrthogonalMoves(currentSquare.row, currentSquare.col, "S"));
            }
            //bishops
            if (piece.label == 'b' || piece.label == 'B') {
                moves.addAll(getDiagonalMoves(currentSquare.row, currentSquare.col, "NW"));
                moves.addAll(getDiagonalMoves(currentSquare.row, currentSquare.col, "NE"));
                moves.addAll(getDiagonalMoves(currentSquare.row, currentSquare.col, "SW"));
                moves.addAll(getDiagonalMoves(currentSquare.row, currentSquare.col, "SE"));
            }
            //rooks
            if (piece.label == 'r' || piece.label == 'R') {
                moves.addAll(getOrthogonalMoves(currentSquare.row, currentSquare.col, "N"));
                moves.addAll(getOrthogonalMoves(currentSquare.row, currentSquare.col, "E"));
                moves.addAll(getOrthogonalMoves(currentSquare.row, currentSquare.col, "S"));
                moves.addAll(getOrthogonalMoves(currentSquare.row, currentSquare.col, "W"));


            }
            //black knight
            if (piece.label == 'n') {
                Square[] Squares = {/*NNE,NNW*/
                        getSquare(currentSquare.row - 2, currentSquare.col + 1),
                        getSquare(currentSquare.row - 2, currentSquare.col - 1)
                };
                for (Square sqr : Squares) {
                    if (sqr != null && sqr.occupant == null) {
                        moves.add(sqr);
                    }
                    if (sqr != null && sqr.occupant != null && !sqr.occupant.color.equals(piece.color)) {
                        moves.add(sqr);
                    }
                }
            }
            //white knight
            if (piece.label == 'N') {
                Square[] Squares = {/*NNE,NNW*/
                        getSquare(currentSquare.row + 2, currentSquare.col + 1),
                        getSquare(currentSquare.row + 2, currentSquare.col - 1)
                };
                for (Square sqr : Squares) {
                    if (sqr != null && sqr.occupant == null) {
                        moves.add(sqr);
                    }
                    if (sqr != null && sqr.occupant != null && !sqr.occupant.color.equals(piece.color)) {
                        moves.add(sqr);
                    }
                }
            }
            //promoted rook
            if (piece.label == 'o' || piece.label == 'O') {
                moves.addAll(getOrthogonalMoves(currentSquare.row, currentSquare.col, "N"));
                moves.addAll(getOrthogonalMoves(currentSquare.row, currentSquare.col, "E"));
                moves.addAll(getOrthogonalMoves(currentSquare.row, currentSquare.col, "S"));
                moves.addAll(getOrthogonalMoves(currentSquare.row, currentSquare.col, "W"));
                Square[] Squares = {/*NE,NW,SW,SE*/
                        getSquare(currentSquare.row - 1, currentSquare.col + 1),
                        getSquare(currentSquare.row - 1, currentSquare.col - 1),
                        getSquare(currentSquare.row + 1, currentSquare.col + 1),
                        getSquare(currentSquare.row + 1, currentSquare.col - 1)
                };
                for (Square sqr : Squares) {
                    if (sqr != null && sqr.occupant == null) {
                        moves.add(sqr);
                    }
                    if (sqr != null && sqr.occupant != null && !sqr.occupant.color.equals(piece.color)) {
                        moves.add(sqr);
                    }
                }
            }
            //promoted bishop
            if (piece.label == 'y' || piece.label == 'Y') {
                moves.addAll(getDiagonalMoves(currentSquare.row, currentSquare.col, "NW"));
                moves.addAll(getDiagonalMoves(currentSquare.row, currentSquare.col, "NE"));
                moves.addAll(getDiagonalMoves(currentSquare.row, currentSquare.col, "SW"));
                moves.addAll(getDiagonalMoves(currentSquare.row, currentSquare.col, "SE"));
                Square[] Squares = {/*N,E,S,W*/
                        getSquare(currentSquare.row - 1, currentSquare.col),
                        getSquare(currentSquare.row, currentSquare.col + 1),
                        getSquare(currentSquare.row, currentSquare.col - 1),
                        getSquare(currentSquare.row + 1, currentSquare.col)
                };
                for (Square sqr : Squares) {
                    if (sqr != null && sqr.occupant == null) {
                        moves.add(sqr);
                    }
                    if (sqr != null && sqr.occupant != null && !sqr.occupant.color.equals(piece.color)) {
                        moves.add(sqr);
                    }
                }
            }
        }
        return moves;
    }
    public Map<Character, Integer> countPieceTypesCaptured(ArrayList<Piece> hand) {
        Map<Character, Integer> counts = new HashMap<>();
        for (Piece piece : hand) {
            counts.put(piece.label, counts.getOrDefault(piece.label, 0) + 1);
        }
        return counts;
    }
    public void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        if (board[fromRow][fromCol].occupant != null) {
            Piece piece = board[fromRow][fromCol].occupant;
            if (getSquare(toRow, toCol).occupant != null && Objects.equals(getSquare(fromRow, fromCol).occupant.color, "white") && !Objects.equals(getSquare(fromRow, fromCol).occupant.color, getSquare(toRow, toCol).occupant.color)) {
                Piece capturedPiece = getSquare(toRow, toCol).occupant;
                switch (capturedPiece.label) {
                    case 't':
                        capturedPiece.label = 'p';
                        break;
                    case 'T':
                        capturedPiece.label = 'P';
                        break;
                    case 'm':
                        capturedPiece.label = 's';
                        break;
                    case 'M':
                        capturedPiece.label = 'S';
                        break;
                    case 'c':
                        capturedPiece.label = 'l';
                        break;
                    case 'C':
                        capturedPiece.label = 'L';
                        break;
                    case 'z':
                        capturedPiece.label = 'n';
                        break;
                    case 'Z':
                        capturedPiece.label = 'N';
                        break;
                    case 'y':
                        capturedPiece.label = 'b';
                        break;
                    case 'Y':
                        capturedPiece.label = 'B';
                        break;
                    case 'o':
                        capturedPiece.label = 'r';
                        break;
                    case 'O':
                        capturedPiece.label = 'R';
                        break;
                }
                capturedPiece.label = Character.isUpperCase(capturedPiece.label) ? Character.toLowerCase(capturedPiece.label) : Character.toUpperCase(capturedPiece.label);
                whiteHand.add(capturedPiece);
            }
            if (getSquare(toRow, toCol).occupant != null && Objects.equals(getSquare(fromRow, fromCol).occupant.color, "black") && !Objects.equals(getSquare(fromRow, fromCol).occupant.color, getSquare(toRow, toCol).occupant.color)) {
                Piece capturedPiece = getSquare(toRow, toCol).occupant;
                switch (capturedPiece.label) {
                    case 't':
                        capturedPiece.label = 'p';
                        break;
                    case 'T':
                        capturedPiece.label = 'P';
                        break;
                    case 'm':
                        capturedPiece.label = 's';
                        break;
                    case 'M':
                        capturedPiece.label = 'S';
                        break;
                    case 'c':
                        capturedPiece.label = 'l';
                        break;
                    case 'C':
                        capturedPiece.label = 'L';
                        break;
                    case 'z':
                        capturedPiece.label = 'n';
                        break;
                    case 'Z':
                        capturedPiece.label = 'N';
                        break;
                    case 'y':
                        capturedPiece.label = 'b';
                        break;
                    case 'Y':
                        capturedPiece.label = 'B';
                        break;
                    case 'o':
                        capturedPiece.label = 'r';
                        break;
                    case 'O':
                        capturedPiece.label = 'R';
                        break;
                }
                capturedPiece.label = Character.isUpperCase(capturedPiece.label) ? Character.toLowerCase(capturedPiece.label) : Character.toUpperCase(capturedPiece.label);
                blackHand.add(capturedPiece);
            }
            if (getSquare(toRow, toCol).occupant != null && getSquare(toRow, toCol).occupant.label == 'K') {
                whiteWon = true;
            }
            if (getSquare(toRow, toCol).occupant != null && getSquare(toRow, toCol).occupant.label == 'k') {
                blackWon = true;
            }
            board[toRow][toCol].occupant = null;
            board[toRow][toCol].occupant = piece;
            board[fromRow][fromCol].occupant = null;
            if (isPromotable(piece, toRow)) piece.promoted = true;
            if (piece.label == 'p' && piece.promoted) {
                piece.row = 1;
                piece.col = 7;
                piece.label = 't';
            }
            if (piece.label == 'P' && piece.promoted) {
                piece.row = 3;
                piece.col = 7;
                piece.label = 'T';
            }
            if (piece.label == 's' && piece.promoted) {
                piece.row = 1;
                piece.col = 4;
                piece.label = 'm';
            }
            if (piece.label == 'S' && piece.promoted) {
                piece.row = 3;
                piece.col = 4;
                piece.label = 'M';
            }
            if (piece.label == 'l' && piece.promoted) {
                piece.row = 1;
                piece.col = 6;
                piece.label = 'c';
            }
            if (piece.label == 'L' && piece.promoted) {
                piece.row = 3;
                piece.col = 6;
                piece.label = 'C';
            }
            if (piece.label == 'n' && piece.promoted) {
                piece.row = 1;
                piece.col = 5;
                piece.label = 'z';
            }
            if (piece.label == 'N' && piece.promoted) {
                piece.row = 3;
                piece.col = 5;
                piece.label = 'Z';
            }
            if (piece.label == 'b' && piece.promoted) {
                piece.row = 1;
                piece.col = 2;
                piece.label = 'y';
            }
            if (piece.label == 'B' && piece.promoted) {
                piece.row = 3;
                piece.col = 2;
                piece.label = 'Y';
            }
            if (piece.label == 'r' && piece.promoted) {
                piece.row = 1;
                piece.col = 1;
                piece.label = 'o';
            }
            if (piece.label == 'R' && piece.promoted) {
                piece.row = 3;
                piece.col = 1;
                piece.label = 'O';
            }
            turn++;
            System.out.println("Black Hand" + countPieceTypesCaptured(blackHand));
            System.out.println("White Hand" + countPieceTypesCaptured(whiteHand));
        }
    }
    public Character matchRowToPiece(int dropRow) {
        if (dropRow == 0) {
            return 'R';
        }
        if (dropRow == 1) {
            return 'B';
        }
        if (dropRow == 2) {
            return 'G';
        }
        if (dropRow == 3) {
            return 'S';
        }
        if (dropRow == 4) {
            return 'N';
        }
        if (dropRow == 5) {
            return 'L';
        }
        if (dropRow == 6) {
            return 'P';
        }
        if (dropRow == 7) {
            return 'p';
        }
        if (dropRow == 8) {
            return 'l';
        }
        if (dropRow == 9) {
            return 'n';
        }
        if (dropRow == 10) {
            return 's';
        }
        if (dropRow == 11) {
            return 'g';
        }
        if (dropRow == 12) {
            return 'b';
        }
        if (dropRow == 13) {
            return 'r';
        } else {
            return null;
        }
    }
    public boolean pawnInRow(int col, Character color) {
        for (int i = 0; i < 9; i++) {
            if (color == 'b') {
                if (board[i][col] != null && board[i][col].occupant != null && board[i][col].occupant.label == 'p') {
                    return true;
                }
            }
            if (color == 'w') {
                if (board[i][col] != null && board[i][col].occupant != null && board[i][col].occupant.label == 'P') {
                    return true;
                }
            }
        }
        return false;
    }
    public void dropPiece(int dr, int toRow, int toCol) {
        Square destinationSquare = getSquare(toRow, toCol);
        if (turn % 2 == 0 && destinationSquare.occupant == null && countPieceTypesCaptured(blackHand).get(matchRowToPiece(dr)) == null || turn % 2 == 0 && destinationSquare.occupant == null && countPieceTypesCaptured(blackHand).get(matchRowToPiece(dr)) == 0) {
            dropRow = -1;
        }
        if (turn % 2 == 1 && destinationSquare.occupant == null && countPieceTypesCaptured(whiteHand).get(matchRowToPiece(dr)) == null || turn % 2 == 1 && destinationSquare.occupant == null && countPieceTypesCaptured(whiteHand).get(matchRowToPiece(dr)) == 0) {
            dropRow = -1;
        }
        if(dropRow > -1 && dropRow < 4) {
            if (turn % 2 == 1 && destinationSquare.occupant == null && countPieceTypesCaptured(whiteHand).get(matchRowToPiece(dr)) != null && countPieceTypesCaptured(whiteHand).get(matchRowToPiece(dr)) > 0) {
                board[toRow][toCol].occupant = new Piece(matchRowToPiece(dr));
                for (int i = 0; i < whiteHand.size(); i++) {
                    Piece currentPiece = whiteHand.get(i);
                    if (currentPiece.label == (matchRowToPiece(dr))) {
                        whiteHand.remove(i);
                        break;
                    }
                }
                turn++;
            }
        }
        if(dropRow == 4){
            if (turn % 2 == 1 && toRow < 7 && destinationSquare.occupant == null && countPieceTypesCaptured(whiteHand).get(matchRowToPiece(dr)) != null && countPieceTypesCaptured(whiteHand).get(matchRowToPiece(dr)) > 0) {
                board[toRow][toCol].occupant = new Piece(matchRowToPiece(dr));
                for (int i = 0; i < whiteHand.size(); i++) {
                    Piece currentPiece = whiteHand.get(i);
                    if (currentPiece.label == (matchRowToPiece(dr))) {
                        whiteHand.remove(i);
                        break;
                    }
                }
                turn++;
            }
        }
        if(dropRow == 5){
            if (turn % 2 == 1 && toRow < 8 && destinationSquare.occupant == null && countPieceTypesCaptured(whiteHand).get(matchRowToPiece(dr)) != null && countPieceTypesCaptured(whiteHand).get(matchRowToPiece(dr)) > 0) {
                board[toRow][toCol].occupant = new Piece(matchRowToPiece(dr));
                for (int i = 0; i < whiteHand.size(); i++) {
                    Piece currentPiece = whiteHand.get(i);
                    if (currentPiece.label == (matchRowToPiece(dr))) {
                        whiteHand.remove(i);
                        break;
                    }
                }
                turn++;
            }
        }
        if(dropRow == 6){
            if (getSquare(toRow + 1, toCol).occupant == null) {
                if (turn % 2 == 1 && toRow < 8 &&
                        !pawnInRow(toCol, 'w') &&
                        destinationSquare.occupant == null &&
                        countPieceTypesCaptured(whiteHand).get(matchRowToPiece(dr)) != null &&
                        countPieceTypesCaptured(whiteHand).get(matchRowToPiece(dr)) > 0) {
                    board[toRow][toCol].occupant = new Piece(matchRowToPiece(dr));
                    for (int i = 0; i < whiteHand.size(); i++) {
                        Piece currentPiece = whiteHand.get(i);
                        if (currentPiece.label == (matchRowToPiece(dr))) {
                            whiteHand.remove(i);
                            break;
                        }
                    }
                    turn++;
                }
            }
            if(getSquare(toRow + 1, toCol).occupant != null){
                if (turn % 2 == 1 && toRow < 8 && !pawnInRow(toCol, 'w') && getSquare(toRow + 1, toCol).occupant.label != 'k' && getSquare(toRow + 1, toCol).occupant.label != 'k' && destinationSquare.occupant == null && countPieceTypesCaptured(whiteHand).get(matchRowToPiece(dr)) != null && countPieceTypesCaptured(whiteHand).get(matchRowToPiece(dr)) > 0) {
                    board[toRow][toCol].occupant = new Piece(matchRowToPiece(dr));
                    for (int i = 0; i < whiteHand.size(); i++) {
                        Piece currentPiece = whiteHand.get(i);
                        if (currentPiece.label == (matchRowToPiece(dr))) {
                            whiteHand.remove(i);
                            break;
                        }
                    }
                    turn++;
                }
            }
        }
        if(dropRow == 7) {
            if (getSquare(toRow - 1, toCol).occupant == null) {
                if (turn % 2 == 0 && toRow < 8 && !pawnInRow(toCol, 'b') && destinationSquare.occupant == null && countPieceTypesCaptured(blackHand).get(matchRowToPiece(dr)) != null && countPieceTypesCaptured(blackHand).get(matchRowToPiece(dr)) > 0) {
                    board[toRow][toCol].occupant = new Piece(matchRowToPiece(dr));
                    for (int i = 0; i < blackHand.size(); i++) {
                        Piece currentPiece = blackHand.get(i);
                        if (currentPiece.label == (matchRowToPiece(dr))) {
                            blackHand.remove(i);
                            break;
                        }
                    }
                    turn++;
                }
            }
                if(getSquare(toRow - 1, toCol).occupant != null) {
                    if (turn % 2 == 0 && toRow < 8 && destinationSquare.occupant == null && !pawnInRow(toCol, 'b') && getSquare(toRow - 1, toCol).occupant.label != 'K' && countPieceTypesCaptured(blackHand).get(matchRowToPiece(dr)) != null && countPieceTypesCaptured(blackHand).get(matchRowToPiece(dr)) > 0) {
                        board[toRow][toCol].occupant = new Piece(matchRowToPiece(dr));
                        for (int i = 0; i < blackHand.size(); i++) {
                            Piece currentPiece = blackHand.get(i);
                            if (currentPiece.label == (matchRowToPiece(dr))) {
                                blackHand.remove(i);
                                break;
                            }
                        }
                        turn++;
                    }
                }
        }
        if(dropRow == 8){
            if (turn % 2 == 0 && toRow > 0 && destinationSquare.occupant == null && countPieceTypesCaptured(blackHand).get(matchRowToPiece(dr)) != null && countPieceTypesCaptured(blackHand).get(matchRowToPiece(dr)) > 0) {
                board[toRow][toCol].occupant = new Piece(matchRowToPiece(dr));
                for (int i = 0; i < blackHand.size(); i++) {
                    Piece currentPiece = blackHand.get(i);
                    if (currentPiece.label == (matchRowToPiece(dr))) {
                        blackHand.remove(i);
                        break;
                    }
                }
                turn++;
            }
        }
        if(dropRow == 9){
            if (turn % 2 == 0 && toRow > 1 && destinationSquare.occupant == null && countPieceTypesCaptured(blackHand).get(matchRowToPiece(dr)) != null && countPieceTypesCaptured(blackHand).get(matchRowToPiece(dr)) > 0) {
                board[toRow][toCol].occupant = new Piece(matchRowToPiece(dr));
                for (int i = 0; i < blackHand.size(); i++) {
                    Piece currentPiece = blackHand.get(i);
                    if (currentPiece.label == (matchRowToPiece(dr))) {
                        blackHand.remove(i);
                        break;
                    }
                }
                turn++;
            }
        }
        if(dropRow > 9){
            if (turn % 2 == 0 && destinationSquare.occupant == null && countPieceTypesCaptured(blackHand).get(matchRowToPiece(dr)) != null && countPieceTypesCaptured(blackHand).get(matchRowToPiece(dr)) > 0) {
                board[toRow][toCol].occupant = new Piece(matchRowToPiece(dr));
                for (int i = 0; i < blackHand.size(); i++) {
                    Piece currentPiece = blackHand.get(i);
                    if (currentPiece.label == (matchRowToPiece(dr))) {
                        blackHand.remove(i);
                        break;
                    }
                }
                turn++;
            }
        }


    }
}


