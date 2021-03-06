package com.littlefisher.blog.cmd.user;

import java.util.List;

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
public class QryAllUserCmd extends AbstractCommand<List<UserDto>> {

    @Override
    public List<UserDto> execute() {
        UserDtoMapper userDtoMapper = this.getMapper(UserDtoMapper.class);
        return userDtoMapper.selectAll();
    }

}
