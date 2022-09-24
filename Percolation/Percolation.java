import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int siz;
    private boolean[][] site_table;
    private int opened_site = 0;
    private WeightedQuickUnionUF union_find;
    private boolean[] bottomContain;

    private void outside_range_throw() {
        throw new IllegalArgumentException();
    }

    private int index(int row , int col) {
        return (row - 1) * siz + col;
    }

    private void union_check(int fir_index,  int sec_index) {
        if( union_find.find(fir_index) != union_find.find(sec_index) ) {
            boolean isBottomContain = ( bottomContain[union_find.find( fir_index )] || bottomContain[union_find.find( sec_index )] );
            union_find.union(fir_index, sec_index);
            bottomContain[union_find.find(fir_index)] = isBottomContain;
        }
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if( n <= 0 ) outside_range_throw();
        siz = n;
        site_table = new boolean[siz + 1][siz + 1];
        bottomContain = new boolean[ siz * siz + 1 ];

        for(int j = 1 ;j <= siz; j++){
            bottomContain[ index(siz, j) ] = true;
        }

        union_find = new WeightedQuickUnionUF(siz*siz + 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        if( Math.min(row, col) <= 0 || Math.max(row , col) > siz ){
            outside_range_throw();
        }
        if( site_table[row][col] ) return;
        
        site_table[row][col] = true; opened_site++;

        if( row == 1 ) {
            union_check( 0 , index(row, col));
        }

        if( col + 1 <= siz && isOpen(row, col+1) ){
            union_check(index(row, col) , index(row, col+1));
        }
        if( col - 1 > 0 && isOpen(row, col-1) ){
            union_check(index(row, col), index(row, col-1));
        }
        if( row + 1 <= siz && isOpen(row+1, col) ){
            union_check(index(row, col), index(row+1, col));
        }
        if( row - 1 > 0 && isOpen(row-1, col) ){
            union_check(index(row, col), index(row-1, col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        if( Math.min(row, col) <= 0 || Math.max(row , col) > siz ){
            outside_range_throw();
        }
        return site_table[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        if( Math.min(row, col) <= 0 || Math.max(row , col) > siz ){
            outside_range_throw();
        }
        return ( union_find.find(0) == union_find.find( index(row, col) ) );
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return opened_site;
    }

    // does the system percolate?
    public boolean percolates(){
        return bottomContain[ union_find.find(0) ];
    }

    // public void printTable() {
    //     for(int i = 1; i <= siz; i++){
    //         for(int j =1 ;j <= siz; j++){
    //             if( isFull(i, j) ) System.out.print("O");
    //             else if( isOpen(i, j) ) System.out.print("X");
    //             else System.out.print(".");
    //         }
    //         System.out.println("");
    //     }   
    // }

    // test client (optional)
    // public static void main(String[] args) {
    //     Percolation perco = new Percolation(2);
    //     perco.open(2, 2);
    //     perco.open(1, 1);
    //     perco.open(2, 1);
    //     perco.printTable();
    //     System.out.println( perco.percolates() );
    // }
}