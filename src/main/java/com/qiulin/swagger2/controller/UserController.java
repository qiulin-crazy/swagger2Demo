package com.qiulin.swagger2.controller;

import com.qiulin.swagger2.bean.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@Api(tags = "用户管理")
@RestController
@RequestMapping(value = "/users")
public class UserController {
    static Map<Long, UserDto> users = Collections.synchronizedMap(new HashMap<>());

    /**
     * 获取所有用户信息
     * @return 用户列表
     */
    @GetMapping(value = "/list")
    @ApiOperation(value = "获取用户列表")
    public List<UserDto> getUserList(){
        ArrayList<UserDto> list = new ArrayList<>(users.values());
        return list;
    }

    /**
     * 新增用户
     * @param userDto 新增用户对象
     * @return succeed
     */
    @PostMapping(value = "/insert")
    @ApiOperation(value = "新增用户",notes = "根据User对象创建用户")
    public String insertUser(@RequestBody UserDto userDto){
        users.put(userDto.getId(),userDto);
        return "success";
    }

    /**
     * 通过id 获取用户信息
     * @param id 用户Id
     * @return 用户
     */
    @GetMapping(value = "/{id}")
    @ApiOperation(value = "获取用户详情",notes = "根据用户id获取用户详情")
    public UserDto getUser(@PathVariable Long id){
        return users.get(id);
    }

    /**
     * 根据用户id修改用户信息
     * @param id id
     * @param userDto 修改后的用户
     * @return userDto
     */
    @PutMapping(value = "/{id}")
    @ApiImplicitParam(paramType = "path",dataType = "Long", name = "id",value = "用户编号",required = true,example = "1")
    @ApiOperation(value = "更新用户详细信息",notes = "根据url的id来指定更新对象，并根据传过来的user信息更新用户详细信息")
  public String putUser(@PathVariable Long id ,@RequestBody UserDto userDto){
      UserDto dto = users.get(id);
      dto.setAge(userDto.getAge());
      dto.setName(userDto.getName());
      users.put(id,dto);
      return "success";
  }

    /**
     * 根据id删除用户
     * @param id id
     * @return success
     */
  @DeleteMapping(value = "/{id}")
  @ApiOperation(value = "删除用户",notes = "根据传过来的id删除指定的用户")
  public String deleteUser(@PathVariable Long id){
        users.remove(id);
        return "success";
  }
}
