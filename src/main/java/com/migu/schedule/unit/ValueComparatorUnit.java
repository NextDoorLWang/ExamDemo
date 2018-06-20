package com.migu.schedule.unit;

import java.util.Comparator;
import java.util.Map;

public class ValueComparatorUnit {

    public static class ValueComparator implements Comparator<Map.Entry<Integer,Integer>>
    {
        public int compare(Map.Entry<Integer,Integer> m, Map.Entry<Integer,Integer> n)
        {
            return m.getValue()-n.getValue();
        }
    }



}
