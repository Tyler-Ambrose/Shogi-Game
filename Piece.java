public class Piece{
    public int row;
    public int col;
    public char label;
    public String color;
    public boolean promoted;
    Piece(char label) {
        this.label = label;
        if(label == 'L'){
            row = 2;
            col = 6;
        }
        if(label == 'N'){
            row = 2;
            col = 5;
        }
        if(label == 'S'){
            row = 2;
            col = 4;
        }
        if(label == 'G'){
            row = 2;
            col = 3;
        }
        if(label == 'K'){
            row = 3;
            col = 0;
        }
        if(label == 'R'){
            row = 2;
            col = 1;
        }
        if(label == 'B'){
            row = 2;
            col = 2;
        }
        if(label == 'P'){
            row = 2;
            col = 7;
        }
        if(label == 'l'){
            row = 0;
            col = 6;
        }
        if(label == 'n'){
            row = 0;
            col = 5;
        }
        if(label == 's'){
            row = 0;
            col = 4;
        }
        if(label == 'g'){
            row = 0;
            col = 3;
        }
        if(label == 'k'){
            row = 0;
            col = 0;
        }
        if(label == 'r'){
            row = 0;
            col = 1;
        }
        if(label == 'b'){
            row = 0;
            col = 2;
        }
        if(label == 'p'){
            row = 0;
            col = 7;
        }

        color = Character.isLowerCase(label) ? "black" : "white";
    }

}