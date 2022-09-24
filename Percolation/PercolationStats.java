import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int siz , number_test;
    private double[] threshold;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if( n <= 0 || trials <= 0 ){
            throw new IllegalArgumentException();
        }
        siz = n;
        number_test = trials;
        threshold = new double[trials];
        
        for(int i = 0; i < number_test; i++){
            Percolation perco = new Percolation( siz );
            while( !perco.percolates() ) {
                int new_row = StdRandom.uniformInt(1, siz + 1);
                int new_col = StdRandom.uniformInt(1, siz + 1);
                perco.open(new_row, new_col);
            }
            
            threshold[i] = (double)perco.numberOfOpenSites() / ( siz * siz );
        }

    }

    // sample mean of percolation threshold
    public double mean(){        
        return StdStats.mean( this.threshold );
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev( this.threshold );
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ( 1.96f * Math.sqrt( stddev() / number_test ) );
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ( 1.96f * Math.sqrt( stddev() / number_test ) );
    }

   // test client (see below)
   public static void main(String[] args){
        if( args.length != 2 ) {
            return;
        }
        PercolationStats perstats = new PercolationStats( Integer.parseInt( args[0] ) , Integer.parseInt(args[1]) );
        System.out.println( "mean                    = " + perstats.mean() );
        System.out.println( "stddev                  = " + perstats.stddev() );
        System.out.println( "95% confidence interval = " + "[" + perstats.confidenceLo() + "," + perstats.confidenceHi() + "]" ); 
    }

}