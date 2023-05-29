package com.hidevlop.websocket.path.controller;


import com.hidevlop.websocket.path.service.PathFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/path")
public class PathFinderController {

    private PathFinderService pathFinderService;
    @Autowired
    public void PathFinderService(PathFinderService pathFinderService){
        this.pathFinderService = pathFinderService;
    }

    @GetMapping("/generation")
    public ResponseEntity<?> createPathFinder(
            @RequestParam Long scheduleId
    ){
        var result = pathFinderService.createPath(scheduleId);
        return ResponseEntity.ok(result);
    }


}
