package com.hidevlop.mvc.controller;


import com.bcgg.path.model.PathFinderInput;
import com.hidevlop.mvc.service.PathFinderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/path")
public class PathFinderController {

    private PathFinderService pathFinderService;
    @Autowired
    public void PathFinderService(PathFinderService pathFinderService){
        this.pathFinderService = pathFinderService;
    }

    /**
     * 경로 생성 API
     * @param pathFinderInput
     * @return
     */
    @PostMapping
    public ResponseEntity<?> createPathFinder(
            @RequestBody PathFinderInput pathFinderInput
    ){
        String result = pathFinderService.createPathFinder(pathFinderInput);

        return ResponseEntity.ok(result);
    }


}
