package com.example.manage.service.Implement;

import com.example.manage.entity.AssignLogEntity;
import com.example.manage.entity.AssignLogEntityExample;
import com.example.manage.mapper.AssignLogEntityMapper;
import com.example.manage.service.AssignlogService;
import com.hdkj.redis.utils.MapRedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author xuminzhe
 * @version V1.0
 * @Project study
 * @Package com.example.manage.service.Implement
 * @Description
 * @Date 2017/11/30
 */
@Service
public class AssignlogServiceImplement implements AssignlogService {
    @Autowired
    private AssignLogEntityMapper assignLogEntityMapper;

    @Autowired
    @Qualifier("mapRedisTemplate")
    private MapRedisTemplate mapRedisTemplate;

    @Override
    public List<AssignLogEntity> query() {
        AssignLogEntityExample assignLogEntityExample=new AssignLogEntityExample();
        AssignLogEntityExample.Criteria criteria = assignLogEntityExample.createCriteria();
        Map<Object, Object> xmz = mapRedisTemplate.entries("xmz");
        mapRedisTemplate.put("assign","xmz","23");
        Object o = mapRedisTemplate.get("assign", "xmz");

        List<AssignLogEntity> assignLogEntities = assignLogEntityMapper.selectByExample(assignLogEntityExample);
        return assignLogEntities;
    }
}
