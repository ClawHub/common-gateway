package com.clawhub.auth;

import com.alibaba.fastjson.JSONObject;
import com.clawhub.auth.entity.SysResource;
import com.clawhub.auth.service.ShiroService;
import com.clawhub.util.json.JsonUtil;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <Description> 权限管理 <br>
 *
 * @author LiZhiming<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年02月08日<br>
 */
@RestController
@CrossOrigin
@RequestMapping("resourceCtr")
//@Api(value = "/resourceCtr", tags = "资源管理")
public class SysResourceController {
    /**
     * 日志
     */
    private Logger logger = LoggerFactory.getLogger(SysResourceController.class);

    /**
     * The Resource facade.
     */
    @Reference
    private ResourceFacade resourceFacade;

    /**
     * The Shiro service.
     */
    @Autowired
    private ShiroService shiroService;

    /**
     * Insert string.
     *
     * @param param the param
     * @return the string
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
//    @ApiOperation(notes = "新增资源", value = "新增资源", produces = "application/json")
    public String insert(@RequestParam String param) {
        logger.info("SysResourceController.insert");
        logger.info(param);
        //权限插入表中
        resourceFacade.insertResource(JSONObject.parseObject(param, SysResource.class));
        //刷新系统权限
        shiroService.updatePermission();
        return JsonUtil.getSucc("新增权限成功");
    }


}