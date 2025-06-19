package com.tioprasetioa.www.mtsn3rohul.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> A = new ArrayList<String>();
        A.add("Kelas VII.1");
        A.add("Kelas VII.2");
        A.add("Kelas VII.3");
        A.add("Kelas VII.4");
        A.add("Kelas VII.5");
        A.add("Kelas VII.6");
        A.add("Kelas VII.7");
        A.add("Kelas VII.8");
        A.add("Kelas VII.9");
        A.add("Kelas VII.10");



        List<String> B = new ArrayList<String>();
        B.add("Kelas VIII.1");
        B.add("Kelas VIII.2");
        B.add("Kelas VIII.3");
        B.add("Kelas VIII.4");
        B.add("Kelas VIII.5");
        B.add("Kelas VIII.6");
        B.add("Kelas VIII.7");
        B.add("Kelas VIII.8");
        B.add("Kelas VIII.9");

        List<String> C = new ArrayList<String>();
        C.add("Kelas IX.1");
        C.add("Kelas IX.2");
        C.add("Kelas IX.3");
        C.add("Kelas IX.4");
        C.add("Kelas IX.5");
        C.add("Kelas IX.6");
        C.add("Kelas IX.7");
        C.add("Kelas IX.8");
        C.add("Kelas IX.9");

        expandableListDetail.put("KELAS 7 (Tujuh)", A);
        expandableListDetail.put("KELAS 8 (Delapan)", B);
        expandableListDetail.put("KELAS 9 (Sembilan)", C);


        return expandableListDetail;
    }
}