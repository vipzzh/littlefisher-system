package com.littlefisher.blog.intf.user;

import java.util.List;

import com.littlefisher.blog.model.user.UserDto;
import com.littlefisher.blog.request.user.GetUserList4PagerByCondRequest;

/**
 * Description:
 *
 * Created on 2017年5月17日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface IUserService {

    /**
     * Description: 查询所有用户
     *
     * @return List<UserDto>
     */
    List<UserDto> getAllUser();

    /**
     * Description: 根据条件查询用户列表
     *
     * @param req req
     * @return List<UserDto>
     */
    List<UserDto> getUserListByCond(GetUserList4PagerByCondRequest req);

    /**
     * Description: 根据id查询用户
     *
     * @param userId userId
     * @return UserDto
     */
    UserDto getUserById(Long userId);

    /**
     * Description: 新增用户
     *
     * @param userDto userDto
     * @return UserDto
     */
    UserDto addUser(UserDto userDto);

    /**
     * Description: 修改用户
     *
     * @param userDto userDto
     * @return UserDto
     */
    UserDto updateUser(UserDto userDto);

    /**
     * Description: 删除用户
     *
     * @param userId userId
     * @return int
     */
    int deleteUser(Long userId);
}
