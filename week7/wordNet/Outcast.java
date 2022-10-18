public class Outcast {
    private WordNet wordNet;
    public Outcast(WordNet wordnet){
        this.wordNet = wordnet;
    }
    
    public String outcast(String[] nouns) {
        String res = new String();
        int maxDis = -1;
        for(int i = 0;i < nouns.length; i++){
            int curDis = 0;
            for(int j = 0 ;j < nouns.length; j++){
                curDis += wordNet.distance(nouns[i], nouns[j]);
            }
            if( curDis > maxDis ) {
                res = nouns[i];
                maxDis = curDis;
            }   
        }
        return res;
    }
    public static void main(String[] args) {

    } // see test client below
 }