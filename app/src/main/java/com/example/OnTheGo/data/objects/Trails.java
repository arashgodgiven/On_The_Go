package com.example.OnTheGo.data.objects;
import java.util.ArrayList;
import java.util.List;

public class Trails {
    private static List<Trail> trailList = new ArrayList<>();

    public static void addTrail(Trail trail) {
        trailList.add(trail);
    }

    public static List<Trail> getTrailList() {
        return trailList;
    }
}
