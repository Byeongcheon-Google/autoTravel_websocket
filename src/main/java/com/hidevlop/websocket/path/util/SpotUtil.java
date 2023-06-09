package com.hidevlop.websocket.path.util;

import com.bcgg.path.pathgenerator.model.Spot;
import com.bcgg.path.util.DistanceCalculator;
import com.bcgg.path.util.VectorUtil;

import java.util.ArrayList;
import java.util.List;

public class SpotUtil {
    public static List<Spot.K> generateKSpots(Spot midSpot, Spot longestSpot, int kCount) {
        List<Spot.K> spotKList = new ArrayList<>();
        spotKList.add(new Spot.K(longestSpot.getLatitude(), longestSpot.getLongitude()));

        for (int i = 1; i < kCount; i++) {
            spotKList.add(VectorUtil.rotatedKSpot(spotKList.get(0), midSpot, (2 * Math.PI / kCount) * i));
        }

        return spotKList;
    }

    public static Spot getLongestSpot(List<Spot> spotList, Spot midSpot) {
        //먼 좌표 구하기
        Spot longestPoint = spotList.get(0);
        double longestDistance = 0.0;
        for (Spot spot : spotList) {
            double distance = DistanceCalculator.getDistance(midSpot.getLatitude(), midSpot.getLongitude(), spot.getLatitude(), spot.getLongitude());
            if (distance > longestDistance) {
                longestPoint = spot;
                longestDistance = distance;
            }
        }
        return longestPoint;
    }
}
