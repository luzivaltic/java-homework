import edu.princeton.cs.algs4.Stack;

public class Board {
    private int[][] tiles;
    private int siz;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        if( tiles == null ){
            throw new IllegalArgumentException();
        }
        siz = tiles.length;
        this.tiles = clone_tiles(tiles);
    }
                                           
    // string representation of this board
    public String toString(){
        String represenString = siz + "\n";
        for(int i = 0 ;i < siz; i++){
            for(int j = 0 ;j < siz; j++){
                represenString += " " + tiles[i][j];
            }
            represenString += "\n";
        }
        return represenString;
    }

    // board siz n
    public int dimension(){
        return this.siz;
    }

    // number of tiles out of place
    public int hamming(){
        int ham = 0;
        for(int i = 0; i < siz; i++)
        for(int j = 0 ;j < siz; j++)if( i != siz -1 || j != siz - 1 ) {
            int corVal = i * siz + j + 1;
            if( tiles[i][j] != corVal ){
                ham++;      
            }
        }
        return ham;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int mat = 0;
        for(int i = 0; i < siz; i++)
        for(int j = 0 ;j < siz; j++) if( tiles[i][j] != 0 ) {
            int corPosRow = ( tiles[i][j] - 1 ) / siz;   
            int corPosCol = ( tiles[i][j] - 1 ) % siz;
            mat += Math.abs(i - corPosRow) + Math.abs(j - corPosCol);
        }
        return mat;
    }

    // is this board the goal board?
    public boolean isGoal(){
        return ( hamming() == 0 );
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if( y instanceof Board ) {
            Board curBoard = (Board) y;
            return this.toString().equals(curBoard.toString());
        }
        return false;   
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> neighborList = new Stack<Board>();
        for(int i = 0; i < siz; i++)
        for(int j = 0;j < siz; j++){
            if( tiles[i][j] == 0 ) {
                if( i -1 >= 0 ) {
                    tiles[i][j] = tiles[i-1][j];
                    tiles[i-1][j] = 0;
                    neighborList.push( new Board(tiles) );
                    tiles[i-1][j] = tiles[i][j];
                    tiles[i][j] = 0;
                }

                if( i + 1 < siz ) {
                    tiles[i][j] = tiles[i+1][j];
                    tiles[i+1][j] = 0;
                    neighborList.push( new Board(tiles) );
                    tiles[i+1][j] = tiles[i][j];
                    tiles[i][j] = 0;
                }

                if( j - 1 >= 0 ) {
                    tiles[i][j] = tiles[i][j-1];
                    tiles[i][j-1] = 0;
                    neighborList.push( new Board(tiles) );
                    tiles[i][j-1] = tiles[i][j];
                    tiles[i][j] = 0;
                }

                if( j + 1 < siz ) {
                    tiles[i][j] = tiles[i][j+1];
                    tiles[i][j+1] = 0;
                    neighborList.push( new Board(tiles) );
                    tiles[i][j+1] = tiles[i][j];
                    tiles[i][j] = 0;
                }
                
                break;
            }
        }
        return neighborList;
    }

    private int[][] clone_tiles( int[][] tiles ){
        int[][] cloneTiles = new int[siz][siz];
        for(int i =0 ;i < siz; i++)
        for(int j = 0 ;j < siz; j++) {
            cloneTiles[i][j] = tiles[i][j];   
        }
        return cloneTiles;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){
        int[][] cloneTiles = clone_tiles(tiles);
        for(int i = 0; i < siz; i++)
        for(int j = 0;j < siz; j++) if( cloneTiles[i][j] != 0 ) {
            
            for(int k = 0 ;k < siz; k++)
            for(int l = 0 ; l < siz; l++) if( ( i != k || j != l ) && cloneTiles[k][l] != 0 ) {
                int temp = cloneTiles[i][j];
                cloneTiles[i][j] = cloneTiles[k][l];
                cloneTiles[k][l] = temp;
                return new Board(cloneTiles);
            }
        }
        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args){

    }

}