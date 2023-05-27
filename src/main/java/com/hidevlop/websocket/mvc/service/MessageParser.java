package com.hidevlop.websocket.mvc.service;

import com.hidevlop.websocket.model.Command;
import com.hidevlop.websocket.mvc.domain.dto.HopeTimeDto;
import com.hidevlop.websocket.mvc.domain.dto.PlaceDto;
import com.hidevlop.websocket.mvc.domain.dto.PlacesDto;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonTime = new JSONObject();
        JSONObject jsonObject;

        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        JSONObject date = (JSONObject) jsonObject.get("date");
        hopeTimeDto.setLocalDate(this.str2Date(date.toString()));

        if (Command.STARTTIME.toString().equals(command)) {

            jsonTime = (JSONObject) jsonObject.get("startTime");
            hopeTimeDto.getTimes().add(this.str2Time(String.valueOf(jsonTime)));


        } else if (Command.ENDTIME.toString().equals(command)) {

            jsonTime = (JSONObject) jsonObject.get("endTime");
            hopeTimeDto.getTimes().add(this.str2Time(String.valueOf(jsonTime)));

        } else if (Command.MEALTIMES.toString().equals(command)){
            JSONArray mealTimeArr = (JSONArray) jsonObject.get("mealTimes");
            for (Object mealTime : mealTimeArr){
                hopeTimeDto.getTimes().add(this.str2Time((String) mealTime));
            }
        }

        return hopeTimeDto;
    }


    public PlacesDto parsePlaces(String jsonString, String command) {

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject;

        try {
            jsonObject = (JSONObject) jsonParser.parse(jsonString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        PlacesDto placesDto = new PlacesDto();
        PlacesDto resultPlaces = new PlacesDto();
        PlaceDto placeDto = new PlaceDto();
        JSONArray date = (JSONArray) jsonObject.get("date");
        placesDto.setDate(this.str2Date(String.valueOf(date)));


        if (Command.POINTS.toString().equals(command)) {
            JSONArray points = (JSONArray) jsonObject.get("points");

            resultPlaces = this.parsPlace(placesDto, placeDto, points);

        } else if (Command.STARTPLACE.toString().equals(command)) {
            JSONArray startPlace = (JSONArray) jsonObject.get("startPlace");
            resultPlaces = this.parsPlace(placesDto, placeDto, startPlace);

        } else if (Command.ENDPLACE.toString().equals(command)) {
            JSONArray endPlace = (JSONArray) jsonObject.get("endPlace");
            resultPlaces = this.parsPlace(placesDto, placeDto, endPlace);
        }

        return resultPlaces;
    }

    private PlacesDto parsPlace(PlacesDto placesDto,PlaceDto placeDto, JSONArray pointsArr) {

        for (Object point : pointsArr) {
            JSONObject pointData = (JSONObject) point;

            JSONObject userId = (JSONObject) pointData.get("user");
            placeDto.setUsername(String.valueOf(userId));
            JSONObject id = (JSONObject) pointData.get("id");
            placeDto.setUsername(String.valueOf(id));
            JSONObject name = (JSONObject) pointData.get("name");
            placeDto.setName(String.valueOf(name));
            JSONObject address = (JSONObject) pointData.get("address");
            placeDto.setAddress(String.valueOf(address));

            try {
                JSONObject lat = (JSONObject) pointData.get("lat");
                placeDto.setLat(Double.valueOf(String.valueOf(lat)));
                JSONObject lng = (JSONObject) pointData.get("lng");
                placeDto.setLng(Double.valueOf(String.valueOf(lng)));
                JSONObject stayTimeHour = (JSONObject) pointData.get("stayTimeHour");
                placeDto.setStayTimeHour(Long.valueOf(String.valueOf(stayTimeHour)));
            } catch (Exception e){
                throw new NullPointerException();
            }

            JSONObject classification = (JSONObject) pointData.get("classification");
            placeDto.setClassification(String.valueOf(classification));

            placesDto.getPlaces().add(placeDto);
        }
        return placesDto;
    }
}
