package com.example.manage.controller;

import com.example.manage.service.SpikeBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xuminzhe
 * @version V1.0
 * @Project study
 * @Package com.example.manage.controller
 * @Description
 * @Date 2017/12/12 17:07
 */
@RestController
public class SpikeController {
    @Autowired
    private SpikeBizService spikeBizService;
    @RequestMapping(value = "spike")
    public void spike(){
        spikeBizService.spike();
    }

}
