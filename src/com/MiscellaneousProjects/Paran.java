package com.MiscellaneousProjects;

import java.util.*;

/**
 * Created by pinaki on 12/27/2016.
 */
public class Paran {

    HashMap<Integer, Integer> matchparan;
    ArrayList<Integer> openparans;

    String evalstring;

    public Paran(String evalstring) {
        this.evalstring = evalstring;
        openparans=new ArrayList<>();
        matchparan=new HashMap<>();
    }



    public HashMap<Integer, Integer> getMatchparan() {
        return matchparan;
    }

    public HashMap<Integer, Integer> getReverseMatchParan () {

        List<Map.Entry<Integer, Integer>> list = new LinkedList<Map.Entry<Integer, Integer>>(this.matchparan.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        HashMap<Integer, Integer> sortedmap = new LinkedHashMap<Integer, Integer>();
        for(Map.Entry<Integer, Integer>entry : list)
        {
            sortedmap.put(entry.getKey(), entry.getValue());
        }

        return sortedmap;
    }

    public void matchparanthesis () {

        char[] chararray = this.evalstring.toCharArray();
        int lastopenindex=-1;

        for (int i=0; i<chararray.length; i++){
            if(chararray[i]=='('){
                lastopenindex++;
                this.openparans.add(lastopenindex,i);
            }
            else if(chararray[i]==')') {
                System.out.println("INDEX OF LAST OPEN PARAN: " + lastopenindex);
                System.out.println("VALUE AT LASTOPENINDEX: " + openparans.get(lastopenindex));
                matchparan.put(openparans.get(lastopenindex), i);
                this.openparans.remove(lastopenindex);
                lastopenindex--;
            }
        }

    }

    public void printMatchparanthesis () {

        System.out.println("\nPRINTING PARAN SET FOR STRING: ");
        System.out.println(this.evalstring);
        System.out.println();

        if(this.matchparan.isEmpty()){
            System.out.println("NO PARANS IN THIS STRING");
        }
        else {
            for (Integer key : matchparan.keySet()){
                System.out.println("OPEN PARAN: " + key + " CLOSE PARAN: " + matchparan.get(key));
            }
        }


    }

    public void printMatchparanthesis (HashMap<Integer, Integer> mymap) {

        System.out.println("\nPRINTING PARAN SET FOR STRING: ");
        System.out.println(this.evalstring);
        System.out.println();

        if(mymap.isEmpty()){
            System.out.println("NO PARANS IN THIS STRING");
        }
        else {
            for (Integer key : mymap.keySet()){
                System.out.println("Close PARAN: " + key + " Open PARAN: " + mymap.get(key));
            }
        }


    }

}
