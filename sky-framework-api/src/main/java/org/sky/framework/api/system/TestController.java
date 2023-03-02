package org.sky.framework.api.system;

import org.sky.framework.config.security.annotation.NeedLogin;
import org.sky.framework.config.web.annotation.RepeatSubmit;
import org.sky.framework.system.dao.DemoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DemoMapper demoMapper;

    @RepeatSubmit
    @NeedLogin
    @GetMapping("/")
    public Object test(){
        logger.info("hello");
        return demoMapper.selectMaster();
    }
}
