public class Ex2 {

    public static int binarySearch(int[] a, int number) {
        int tail = 0;
        int head = a.length - 1;

        while( head - tail > 1 ) {
            int mid = ( head + tail ) / 2;
            if( a[mid] > number ) {
                head = mid;   
            }
            else {
                tail = mid;
            }
        }

        return ( a[tail] == number ) ? tail : -1;   
    }
}
