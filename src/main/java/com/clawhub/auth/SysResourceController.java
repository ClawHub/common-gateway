package com.clawhub.auth;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.clawhub.auth.entity.SysResource;
import com.clawhub.auth.service.ShiroService;
import com.clawhub.result.ResultUtil;
import com.clawhub.util.ListToTreeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <Description> 权限管理 <br>
 *
 * @author LiZhiming<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018年02月08日<br>
 */
@RestController
@RequestMapping("resource")
public class SysResourceController {
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
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@RequestParam String param) {
        //权限插入表中
        resourceFacade.insertResource(JSONObject.parseObject(param, SysResource.class));
        //刷新系统权限
        shiroService.updatePermission();
        return ResultUtil.getSucc();
    }

    @GetMapping("/tree")
    public String tree() {
        List<SysResource> list = resourceFacade.getAllResource();
        System.out.println(list);
        JSONArray result = ListToTreeUtil.listToTree(JSONArray.parseArray(JSON.toJSONString(list)), "resourceId", "parentId", "children");
        return ResultUtil.getSucc(JSON.toJSONString(result));
    }


}