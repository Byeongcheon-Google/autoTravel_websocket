package com.hidevlop.websocket.path.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hidevlop.websocket.chatiing.model.Command;
import com.hidevlop.websocket.path.domain.dto.HopeTimeDto;
import com.hidevlop.websocket.path.domain.dto.PlaceDto;
import com.hidevlop.websocket.path.domain.dto.PlacesDto;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Component
public class MessageParser {

    public LocalTime str2Time(String item) {


        //String -> LocalTime 형변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.parse(item, formatter);

        return localTime;
    }

    public LocalDate str2Date(String item) {


        //String -> LocalTime 형변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(item, formatter);

        return localDate;
    }

    public HopeTimeDto parseHopeTime(String jsonString, String command) {

        HopeTimeDto hopeTimeDto = new HopeTimeDto();
        hopeTimeDto.setTimes(new ArrayList<>());
        JsonParser JsonParser = new JsonParser();
        String time;
        JsonObject jsonObject;


        jsonObject = (JsonObject) JsonParser.parse(jsonString);



        String dateString = jsonObject.get("date").getAsString();
        hopeTimeDto.setLocalDate(this.str2Date(dateString));

        if (Command.STARTTIME.toString().equals(command)) {

            time = jsonObject.get("startTime").getAsString();
            hopeTimeDto.getTimes().add(this.str2Time(time));


        } else if (Command.ENDTIME.toString().equals(command)) {

            time = jsonObject.get("endTime").getAsString();;
            hopeTimeDto.getTimes().add(this.str2Time(time));

        } else if (Command.MEALTIMES.toString().equals(command)){
            JsonArray mealTimeArr = jsonObject.get("mealTimes").getAsJsonArray();
            for (JsonElement mealTime : mealTimeArr){
                hopeTimeDto.getTimes().add(this.str2Time(mealTime.getAsString()));
            }
        }

        return hopeTimeDto;
    }


    public PlacesDto parsePlaces(String jsonString, String command) {


        JsonParser JsonParser = new JsonParser();
        JsonObject JsonObject;


        JsonObject = (JsonObject) JsonParser.parse(jsonString);



        PlacesDto placesDto = new PlacesDto();
        PlacesDto resultPlaces = new PlacesDto();
        PlaceDto placeDto = new PlaceDto();
        String date = JsonObject.get("date").getAsString();
        placesDto.setDate(this.str2Date(date));


        if (Command.POINTS.toString().equals(command)) {
            JsonArray points = JsonObject.get("points").getAsJsonArray();

            resultPlaces = this.parsPlace(placesDto, placeDto, points);

        } else if (Command.STARTPLACE.toString().equals(command)) {
            JsonArray startPlace = (JsonArray) JsonObject.get("startPlace");
            resultPlaces = this.parsPlace(placesDto, placeDto, startPlace);

        } else if (Command.ENDPLACE.toString().equals(command)) {
            JsonArray endPlace = (JsonArray) JsonObject.get("endPlace");
            resultPlaces = this.parsPlace(placesDto, placeDto, endPlace);
        }

        return resultPlaces;
    }

    private PlacesDto parsPlace(PlacesDto placesDto,PlaceDto placeDto, JsonArray pointsArr) {
        placesDto.setPlaces(new ArrayList<PlaceDto>());
        for (Object point : pointsArr) {
            JsonObject pointData = (JsonObject) point;

            String userId = pointData.get("userId").getAsString();
            placeDto.setUsername(userId);
            Long id = pointData.get("id").getAsLong();
            placeDto.setId(id);
            String name = pointData.get("name").getAsString();
            placeDto.setName(name);
            String address =pointData.get("address").getAsString();
            placeDto.setAddress(address);

            try {
                Double lat = pointData.get("lat").getAsDouble();
                placeDto.setLat(lat);
                Double lng = pointData.get("lng").getAsDouble();
                placeDto.setLng(lng);
                Long stayTimeHour = pointData.get("stayTimeHour").getAsLong();
                placeDto.setStayTimeHour(stayTimeHour);
            } catch (Exception e){
                throw new NullPointerException();
            }

            String classification = pointData.get("classification").getAsString();
            placeDto.setClassification(classification);

            placesDto.getPlaces().add(placeDto);
        }
        return placesDto;
    }
}
