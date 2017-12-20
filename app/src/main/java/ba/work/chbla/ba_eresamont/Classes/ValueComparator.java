package ba.work.chbla.ba_eresamont.Classes;

import java.util.Comparator;
import java.util.Map;

/**
 * Created by chbla on 10.12.2017.
 */

public class ValueComparator implements Comparator<String> {
    Map map;
    public ValueComparator(Map map) {
        this.map = map;
    }
    public int compare(String keyA, String keyB) {
        int result;
        Comparable valueA = (Comparable) map.get(keyA);
        Comparable valueB = (Comparable) map.get(keyB);
        if (map.get(keyA).toString().contains(".")&&
                map.get(keyB).toString().contains(".")){
            int indexA =  map.get(keyA).toString().indexOf('.');
            int indexB =  map.get(keyB).toString().indexOf('.');
            int a=Integer.parseInt(map.get(keyA).toString().substring(0, indexA));
            int b=Integer.parseInt(map.get(keyB).toString().substring(0, indexB));
            result =a-b;
        }
        else{
            result =valueA.compareTo(valueB);
        }
        return result;
    }
}