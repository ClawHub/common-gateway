package com.clawhub.auth;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.clawhub.auth.config.MyShiroRealm;
import com.clawhub.auth.entity.SysUser;
import com.clawhub.auth.util.PasswordGenarator;
import com.clawhub.auth.vo.SearchModel;
import com.clawhub.constants.StatusConstant;
import com.clawhub.result.ResultUtil;
import com.clawhub.util.IDGenarator;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <Description> 用户网关<br>
 *
 * @author LiZhiming<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2018/9/11 <br>
 */
@RestController
@RequestMapping("/user")
public class SysUserController {
    /**
     * The User facade.
     */
    @Reference
    private UserFacade userFacade;

    @Autowired
    private MyShiroRealm myShiroRealm;

    @PostMapping("/list")
    public String list(@RequestBody String param) {
        JSONObject body = JSONObject.parseObject(param);
        SysUser sysUser = body.getObject("user", SysUser.class);
        SearchModel searchModel = body.getObject("search", SearchModel.class);
        return userFacade.queryUserByPage(searchModel, sysUser);
    }

    @PostMapping("/add")
    public String add(@RequestBody String param) {
        JSONObject body = JSONObject.parseObject(param);
        SysUser sysUser = body.getObject("user", SysUser.class);
        List<String> roleIds = body.getJSONArray("roleIds").toJavaList(String.class);
        userFacade.addUser(buildAddUserInfo(sysUser), roleIds);
        return ResultUtil.getSucc();
    }

    @PostMapping("/batchDel")
    public String batchDel(@RequestBody String param) {
        JSONObject body = JSONObject.parseObject(param);
        List<String> ids = body.getJSONArray("ids").toJavaList(String.class);
        SysUser currentUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        userFacade.batchDelUser(ids,currentUser);
        return ResultUtil.getSucc();
    }

    private SysUser buildAddUserInfo(SysUser sysUser) {
        SysUser currentUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        sysUser.setCreateOperatorId(currentUser.getUserId());
        sysUser.setCreateOperatorName(currentUser.getUsername());
        sysUser.setIsDelete(StatusConstant.UN_DELETED);
        sysUser.setId(IDGenarator.getID());
        sysUser.setUserId("user-" + IDGenarator.getID());
        sysUser.setState(StatusConstant.UN_LOCKED);
        sysUser.setCreateTime(System.currentTimeMillis());
        String salt = "salt-" + IDGenarator.getID();
        sysUser.setSalt(salt);
        sysUser.setPassword(PasswordGenarator.getPassword(sysUser.getPassword(), salt, 2));
        return sysUser;
    }
}
