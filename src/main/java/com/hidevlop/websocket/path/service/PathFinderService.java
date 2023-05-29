package com.hidevlop.websocket.path.service;


import com.bcgg.path.di.ServiceLocator;
import com.bcgg.path.model.PathFinderInput;
import com.bcgg.path.model.Point;
import com.bcgg.path.pathfinder.PathFinder;
import com.bcgg.path.pathfinder.PathFinderResult;
import com.bcgg.path.pathfinder.PathFinderState;
import com.google.gson.Gson;
import com.hidevlop.websocket.path.domain.entity.DaySchedule;
import com.hidevlop.websocket.path.domain.entity.Place;
import com.hidevlop.websocket.path.domain.repo.DayScheduleRepository;
import com.hidevlop.websocket.path.domain.repo.PlaceRepository;
import com.hidevlop.websocket.path.domain.type.PlaceType;
import io.reactivex.rxjava3.functions.Consumer;
import kotlin.Pair;
import kotlin.ranges.ClosedRange;
import lombok.RequiredArgsConstructor;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
@RequiredArgsConstructor
public class PathFinderService {

    PathFinder pathFinder;
    private final DayScheduleRepository dayScheduleRepository;
    private final PlaceRepository placeRepository;


    public  Map<String,List<String>> createPath(Long scheduleId){


        List<DaySchedule> daySchedules = dayScheduleRepository.findBySchedule_Id(scheduleId);
        List<String> paths = new ArrayList<>();
        Map<String,List<String>> pathMap = new HashMap<>();
        for (int i = 0; i < daySchedules.size(); i++) {
            DaySchedule daySchedule = daySchedules.get(i);
            List<Place> places = placeRepository.findAllByHopeDateAndSchedule_Id(daySchedule.getHopeDate(), scheduleId);


            for (Place place : places){
                Point startPoint =
                        new Point(null, 0.0, 0.0, Point.Classification.Travel, 0L);
                Point endPoint =
                        new Point(null, 0.0, 0.0, Point.Classification.Travel, 0L);
                Point point =
                        new Point(null, 0.0, 0.0, Point.Classification.Travel, 0L);

                Collection<Point> points = new HashSet<>();

                if (PlaceType.STARTPLACE.equals(place.getPlaceType())){
                     startPoint = new Point(
                            place.getName(),
                            place.getLat(),
                            place.getLng(),
                            place.getClassification(),
                            place.getStayTimeHour()
                    );
                }
                else if (PlaceType.ENDPLACE.equals(place.getPlaceType())){
                   endPoint = new Point(
                            place.getName(),
                            place.getLat(),
                            place.getLng(),
                            place.getClassification(),
                            place.getStayTimeHour()
                    );
                } else {
                    point = new Point(
                            place.getName(),
                            place.getLat(),
                            place.getLng(),
                            place.getClassification(),
                            place.getStayTimeHour()
                    );
                    points.add(point);
                }

                PathFinderInput pathFinderInput = new PathFinderInput(
                        daySchedule.getHopeDate(),
                        daySchedule.getStartTime(),
                        daySchedule.getEndHopeTime(),
                        daySchedule.getMealTime(),
                        startPoint,
                        endPoint,
                        points
                        );

                String pathFinder = createPathFinder(pathFinderInput);
                paths.add(pathFinder);
            }
            pathMap.put(String.valueOf(i),paths);
        }
        return pathMap;
    }

    public String createPathFinder(PathFinderInput pathFinderInput){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");
        this.pathFinder  = new PathFinder(ServiceLocator.INSTANCE.getDirectionsRepository(), pathFinderInput);
        final String[] response = {""};
        pathFinder.getPath().subscribe(
                new Subscriber<PathFinderState>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                    }

                    @Override
                    public void onNext(PathFinderState pathFinderState) {

                        if (pathFinderState instanceof PathFinderState.Finding) {

                            System.out.print("\r");
                            PathFinderState.Finding findingState = (PathFinderState.Finding) pathFinderState;

                            double progress = findingState.getSearchEdgesCount() / (double) findingState.getAllEdgesCount() * 100;

                            System.out.printf("%.2f%% (%d / %d)",
                                    progress,
                                    findingState.getSearchEdgesCount(),
                                    findingState.getAllEdgesCount()
                            );

                        } else if (pathFinderState instanceof PathFinderState.Found) {

                            System.out.println();
                            PathFinderState.Found foundState = (PathFinderState.Found) pathFinderState;
                            long endTime = System.nanoTime();
                            List<Pair<Point, ClosedRange<LocalTime>>> path = foundState.getPath();

                            List<String> result = new ArrayList<>();
                            for (int i = 0; i < path.size(); i++) {
                                Pair<Point, ClosedRange<LocalTime>> entry = path.get(i);
                                Point point = entry.getFirst();
                                ClosedRange<LocalTime> timeRange = entry.getSecond();

                                String formattedString = String.format("#%d : %s\t\t%s ~ %s",
                                        i + 1,
                                        point,
                                        timeRange.getStart().format(format),
                                        timeRange.getEndInclusive().format(format));

                                result.add(formattedString);
                            }

                            String joinedResult = String.join("\n", result);
                            System.out.println(joinedResult);
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
//                        throw  new PathFinderException(t.getMessage());
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        pathFinder.getResult().subscribe(
                                new Consumer<PathFinderResult>() {
                                    @Override
                                    public void accept(PathFinderResult pathFinderResult) throws Throwable {
                                        Gson gson = ServiceLocator.INSTANCE.getGson();
                                        String s = gson.toJson(pathFinderResult);
                                        System.out.println(gson.toJson(pathFinderResult));
                                        response[0] = s;
                                    }
                                },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Throwable {
//                                        throw new PathFinderException(throwable.getMessage());
                                        throwable.printStackTrace();
                                    }
                                }
                        );


                    }
                }
        );

        return response[0];
    }



}
