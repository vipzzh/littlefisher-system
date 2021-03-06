package com.littlefisher.blog.cmd.user;

import com.littlefisher.blog.dao.user.UserDtoMapper;
import com.littlefisher.blog.model.user.UserDto;
import com.littlefisher.core.interceptor.AbstractCommand;

/**
 * Description:
 *
 * Created on 2017年5月17日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public class AddUserCmd extends AbstractCommand<UserDto> {

    /**
     * userDto
     */
    private UserDto userDto;

    /**
     * Description: 构造函数
     *
     * @param userDto userDto
     */
    public AddUserCmd(UserDto userDto) {
        super();
        this.userDto = userDto;
    }

    @Override
    public UserDto execute() {
        UserDtoMapper userDtoMapper = this.getMapper(UserDtoMapper.class);
        userDtoMapper.insert(userDto);
        return userDto;
    }

}
