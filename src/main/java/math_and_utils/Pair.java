package math_and_utils;

/**
 * I believe i stole this somewhere on THE INTERNET :D , and then edited it a little bit
 * @author rasto
 */
public class Pair<K, V> {

    private final K element0;
    private final V element1;

    /**
     * Creates new pair
     * @param <K> type for element accessible by first()
     * @param <V> type for element accessible by second()
     * @param element0 accessible by first()
     * @param element1 accessible by second()
     * @return 
     */
    public static <K, V> Pair<K, V> createPair(K element0, V element1) {
        return new Pair<K, V>(element0, element1);
    }

    /**
     * Sets elements
     * @param element0 accessible by first()
     * @param element1 accessible by second()
     */
    public Pair(K element0, V element1) {
        this.element0 = element0;
        this.element1 = element1;
    }

    /**
     * 
     * @return element0 which is type of K
     */
    public K first() {
        return element0;
    }

    /**
     * 
     * @return element1 which is type of V
     */
    public V second() {
        return element1;
    }

}