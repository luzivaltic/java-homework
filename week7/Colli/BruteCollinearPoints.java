import java.util.Arrays;

public class BruteCollinearPoints {
    private LineSegment[] segments;
    private int numSegment;

    public BruteCollinearPoints(Point[] points){
        if( points == null ) {
            throw new IllegalArgumentException();
        }
        for(int i = 0 ;i < points.length; i++) {
            if( points[i] == null ){
                throw new IllegalArgumentException();
            }   
        }
        for(int i = 0 ;i < points.length; i++) {
            for(int j = i + 1; j < points.length; j++) {
                if( points[i].compareTo( points[j] ) == 0 ){
                    throw new IllegalArgumentException();
                }
            }   
        }
        Point[] newPoints = points.clone();

        segments = new LineSegment[2];
        Arrays.sort(newPoints);
        
        for(int i = 0 ;i < points.length; i++)
        for(int j = i + 1 ;j < points.length; j++)
        for(int k = j + 1 ; k < points.length; k++){
            if( newPoints[i].slopeTo(newPoints[j]) == newPoints[j].slopeTo(newPoints[k]) ){
                for(int l = k + 1 ; l < points.length; l++){
                    if( newPoints[j].slopeTo(newPoints[k]) == newPoints[k].slopeTo(newPoints[l]) ) {
                        segments[ numSegment++ ] = new LineSegment(newPoints[i], newPoints[l]);   
                        if( numSegment == segments.length ) {
                            resizeSegments(numSegment * 2);
                        }
                    }
                }   
            }
        }
    }

    private void resizeSegments(int newSiz) {
        segments = Arrays.copyOf(segments, newSiz);
    }

    public int numberOfSegments() {
        return numSegment;
    }
    public LineSegment[] segments() {
        return Arrays.copyOf(segments, numSegment);
    }     
}
