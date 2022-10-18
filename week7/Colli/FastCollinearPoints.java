import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] segments;
    private int numSegment;

    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }
        Point[] fastPoints = points.clone();
        segments = new LineSegment[2];
        Arrays.sort(fastPoints);
        for (int i = 0; i < points.length; i++) {
            Point[] newPoints = Arrays.copyOf(fastPoints, points.length);
            Arrays.sort(newPoints, fastPoints[i].slopeOrder());
            int count = 0;
            double preSlope = Double.NEGATIVE_INFINITY;

            for (int j = 1; j < fastPoints.length; j++) {
                if (fastPoints[i].slopeTo(newPoints[j]) != preSlope || j == fastPoints.length - 1) {
                    int endPoint = j - 1;
                    if (j == points.length - 1
                            && fastPoints[i].slopeTo(newPoints[j]) == preSlope) {
                        endPoint = j;
                        count++;
                    }
                    if (count >= 3) {
                        if ( fastPoints[i].compareTo( newPoints[endPoint - count + 1] ) < 0 ) {
                            segments[numSegment++] = new LineSegment(fastPoints[i], newPoints[endPoint]);
                            if (numSegment == segments.length) {
                                resizeSegments(segments.length * 2);
                            }
                        }
                    }
                    count = 1;
                    preSlope = fastPoints[i].slopeTo(newPoints[j]);
                } else {
                    count++;
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

    public static void main(String[] args) {
    }
}
