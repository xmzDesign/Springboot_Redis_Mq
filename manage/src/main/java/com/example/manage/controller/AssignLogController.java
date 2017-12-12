package com.example.manage.controller;

import com.example.manage.entity.AssignLogEntity;
import com.example.manage.service.AssignlogService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xuminzhe
 * @version V1.0
 * @Project study
 * @Package com.example.manage.controller
 * @Description
 * @Date 2017/11/30
 */
@RestController
public class AssignLogController {

    @Resource
    private AssignlogService assignlogService;

    @RequestMapping(value = "assign")
    public List query(){
        List<AssignLogEntity> query = assignlogService.query();
        return query;

    }



}
