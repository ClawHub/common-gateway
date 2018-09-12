package com.clawhub.auth;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.clawhub.auth.entity.SysRole;
import com.clawhub.auth.entity.SysUser;
import com.clawhub.auth.vo.SearchModel;
import com.clawhub.constants.StatusConstant;
import com.clawhub.result.ResultUtil;
import com.clawhub.util.IDGenarator;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <Description> 角色网关<br>
 *
 * @author LiZhiming<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018/9/12 <br>
 */
@RestController
@RequestMapping("/role")
public class SysRoleController {

    @Reference
    private RoleFacade roleFacade;

    @PostMapping("/list")
    public String list(@RequestBody String param) {
        JSONObject body = JSONObject.parseObject(param);
        SysRole sysRole = body.getObject("role", SysRole.class);
        SearchModel searchModel = body.getObject("search", SearchModel.class);
        return roleFacade.queryRoleByPage(searchModel, sysRole);
    }

    @PostMapping("/add")
    public String add(@RequestBody String param) {
        JSONObject body = JSONObject.parseObject(param);
        SysRole sysRole = body.getObject("role", SysRole.class);
        List<String> resourceIds = body.getJSONArray("resourceIds").toJavaList(String.class);
        roleFacade.addRole(buildAddRoleInfo(sysRole), resourceIds);
        return ResultUtil.getSucc();
    }

    @PostMapping("/batchDel")
    public String batchDel(@RequestBody String param) {
        JSONObject body = JSONObject.parseObject(param);
        List<String> roleIds = body.getJSONArray("roleIds").toJavaList(String.class);
        SysUser currentUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        roleFacade.batchDelRole(roleIds, currentUser);
        return ResultUtil.getSucc();
    }


    private SysRole buildAddRoleInfo(SysRole sysRole) {
        SysUser currentUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        sysRole.setCreateOperatorId(currentUser.getUserId());
        sysRole.setCreateOperatorName(currentUser.getUsername());
        sysRole.setCreateTime(System.currentTimeMillis());
        sysRole.setIsDelete(StatusConstant.UN_DELETED);
        sysRole.setState(StatusConstant.UN_LOCKED);
        sysRole.setId(IDGenarator.getID());
        sysRole.setRoleId("role-" + IDGenarator.getID());
        return sysRole;
    }

}
