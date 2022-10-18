import java.awt.Color;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {

    private Picture picture;

    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture){
        if( picture == null ){
            throw new IllegalArgumentException();
        }
        this.picture = new Picture(picture);
    }
 
    // current picture
    public Picture picture(){
        return new Picture(picture);
    }
 
    // width of current picture
    public int width(){
        return picture.width();
    }
 
    // height of current picture
    public int height(){
        return picture.height();
    }

    // energy of pixel at column x and row y
    public double energy(int x, int y){
        if( x < 0 || x > width() - 1 || y < 0 || y > height() - 1 ){
            throw new IllegalArgumentException();   
        }

        if( x == 0 || x == width() - 1 || y == 0 || y == height() - 1 ){
            return 1000;
        }

        Color xMinus = picture.get(x-1, y);
        Color xPlus = picture.get(x+1, y);

        double red_x = xMinus.getRed() - xPlus.getRed();
        double blue_x = xMinus.getBlue() - xPlus.getBlue();
        double green_x = xMinus.getGreen() - xPlus.getGreen();
        
        Color yMinus = picture.get(x, y-1);
        Color yPlus = picture.get(x, y+1);
        double red_y = yMinus.getRed() - yPlus.getRed();
        double blue_y = yMinus.getBlue() - yPlus.getBlue();
        double green_y = yMinus.getGreen() - yPlus.getGreen();

        double x2 = red_x * red_x + blue_x * blue_x + green_x*green_x;
        double y2 = red_y * red_y + blue_y * blue_y + green_y * green_y;

        return Math.sqrt( x2 + y2 );
    }

    private class EnergyPixel {
        public double energy;
        public int prevPixel;
        
        public EnergyPixel(double energy , int prevPixel) {
            this.energy = energy;
            this.prevPixel = prevPixel;   
        }
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam(){
        EnergyPixel[][] energyPixels = new EnergyPixel[width()][height()]; 
        
        for(int j = 0 ; j < height(); j++){
            energyPixels[0][j] = new EnergyPixel(1000, -1);
        }
        
        for(int i = 1;i < width(); i++){
            for(int j = 0 ;j < height(); j++){
                int prev = j;   
                if( j - 1 > 0 && energyPixels[i-1][j-1].energy < energyPixels[i-1][prev].energy ) {
                    prev = j-1;
                }

                if( j + 1 < height() && energyPixels[i-1][j+1].energy < energyPixels[i-1][prev].energy ){
                    prev = j+1;
                }
                energyPixels[i][j] = new EnergyPixel( energyPixels[i-1][prev].energy + energy(i, j) , prev);
            }
        }

        int minPos = 0;
        for(int j = 0 ;j < height(); j++){
            if( energyPixels[width()-1][j].energy < energyPixels[width()-1][minPos].energy ) {
                minPos = j;
            }   
        }

        int[] seam = new int[width()];
        for(int i = width()-1; i >= 0 ; i--){
            seam[i] = minPos;
            minPos = energyPixels[i][minPos].prevPixel;  
        }
        return seam;
    }
 
    // sequence of indices for vertical seam
    public int[] findVerticalSeam(){
        EnergyPixel[][] energyPixels = new EnergyPixel[width()][height()]; 
        
        for(int i = 0 ; i < width(); i++){
            energyPixels[i][0] = new EnergyPixel(1000, -1);
        }
        
        for(int j = 1;j < height(); j++){
            for(int i = 0 ;i < width(); i++){
                int prev = i;   
                if( i - 1 > 0 && energyPixels[i-1][j-1].energy < energyPixels[prev][j-1].energy ) {
                    prev = i-1;
                }

                if( i + 1 < width() && energyPixels[i+1][j-1].energy < energyPixels[prev][j-1].energy ){
                    prev = i+1;
                }
                energyPixels[i][j] = new EnergyPixel( energyPixels[prev][j-1].energy + energy(i, j) , prev);
            }
        }

        int minPos = 0;
        for(int i = 0 ;i < width(); i++){
            if( energyPixels[i][height()-1].energy < energyPixels[minPos][height()-1].energy ) {
                minPos = i;
            }   
        }

        int[] seam = new int[height()];
        for(int j= height()-1; j >= 0 ; j--){
            seam[j] = minPos;
            minPos = energyPixels[minPos][j].prevPixel;  
        }
        return seam;

    }
 
    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam){
        if( seam == null || seam.length != width() ) {
            throw new IllegalArgumentException();
        }
        for(int i = 1; i < width(); i++){
            if( Math.abs( seam[i] - seam[i-1] ) > 1 || seam[i] < 0 || seam[i] > height() - 1 ) {
                throw new IllegalArgumentException();
            }
        }
        Picture newPicture = new Picture( width() , height() - 1 );
        for(int i = 0 ; i < width(); i++) {
            for(int j = 0;j < seam[i]; j++){
               newPicture.set(i, j,  picture.get(i, j) ); 
            }   
            for(int j = seam[i];j < height() - 1; j++){
                newPicture.set(i, j, picture.get(i, j+1) );   
            }
        }
        this.picture = newPicture;
    }
 
    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam){
        if( seam == null || seam.length != height() ) {
            throw new IllegalArgumentException();
        }
        for(int j = 1; j < height(); j++){
            if( Math.abs( seam[j] - seam[j-1] ) > 1 || seam[j] < 0 || seam[j] >= width() ) {
                throw new IllegalArgumentException();
            }
        }
        
        Picture newPicture = new Picture( width() - 1 , height() );
        for(int j = 0 ; j < height(); j++) {
            for(int i = 0;i < seam[j]; i++){
               newPicture.set(i, j,  picture.get(i, j) ); 
            }   
            for(int i = seam[j];i < width() - 1; i++){
                newPicture.set(i, j, picture.get(i+1, j) );  
            }
        }
        this.picture = newPicture;
    }
 
    //  unit testing (optional)
    public static void main(String[] args){

    }
 
}