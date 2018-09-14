package com.clawhub.boss;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.clawhub.boss.service.entity.TupleConfig;
import com.clawhub.boss.service.facade.ServiceFacade;
import com.clawhub.result.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <Description> 基础网关<br>
 *
 * @author LiZhiming<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018/9/4 <br>
 */
@RestController
@RequestMapping("service")
public class ServiceController {
    @Reference
    private ServiceFacade serviceFacade;

    @PostMapping("/addService")
    public String addService(@RequestBody String param) {
        JSONObject body = JSONObject.parseObject(param);
        String serviceName = body.getString("serviceName");
        String description = body.getString("description");
        serviceFacade.addService(serviceName, description);
        return ResultUtil.getSucc();
    }

    @GetMapping("/getAllService")
    public String getAllService() {
        return ResultUtil.getSucc(serviceFacade.getAllService());
    }

    @GetMapping("/getService/{serviceName}")
    public String getService(@PathVariable String serviceName) {
        System.out.println(serviceName);
        return ResultUtil.getSucc(serviceFacade.getService(serviceName));
    }

    @PostMapping("/addTuple")
    public String addTuple(@RequestBody String param) {
        JSONObject body = JSONObject.parseObject(param);
        String className = body.getString("className");
        String description = body.getString("description");
        TupleConfig config = body.getObject("config", TupleConfig.class);
        serviceFacade.addTuple(className, description, config);
        return ResultUtil.getSucc();
    }

    @GetMapping("/getTuple/{className}")
    public String getTuple(@PathVariable String className) {
        return ResultUtil.getSucc(serviceFacade.getTuple(className));
    }

    @GetMapping("/getAllTuple")
    public String getAllTuple() {
        return ResultUtil.getSucc(serviceFacade.getAllTuple());
    }

    @PostMapping("/addStrategy")
    public String addStrategy(@RequestBody String param) {
        JSONObject body = JSONObject.parseObject(param);
        String strategyName = body.getString("strategyName");
        List<String> classNames = body.getJSONArray("classNames").toJavaList(String.class);
        serviceFacade.addStrategy(strategyName, classNames);
        return ResultUtil.getSucc();
    }

    @GetMapping("/getAllStrategy")
    public String getAllStrategy() {
        return ResultUtil.getSucc(serviceFacade.getAllStrategy());
    }

    @GetMapping("/getStrategy/{strategyName}")
    public String getStrategy(@PathVariable String strategyName) {
        return ResultUtil.getSucc(serviceFacade.getStrategy(strategyName));
    }

    @PostMapping("/bindServiceStrategy")
    public String bindServiceStrategy(@RequestBody String param) {
        JSONObject body = JSONObject.parseObject(param);
        String serviceName = body.getString("serviceName");
        String strategyName = body.getString("strategyName");
        serviceFacade.bindServiceStrategy(serviceName, strategyName);
        return ResultUtil.getSucc();
    }

    /**
     * Welcome string.
     *
     * @return the string
     */
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String welcome() {
        return ResultUtil.getSucc();
    }
}
