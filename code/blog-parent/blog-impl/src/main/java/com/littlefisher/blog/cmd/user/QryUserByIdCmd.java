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
public class QryUserByIdCmd extends AbstractCommand<UserDto> {

    /**
     * userId
     */
    private Long userId;

    /**
     * Description: 构造函数
     *
     * @param userId userId
     */
    public QryUserByIdCmd(Long userId) {
        this.userId = userId;
    }

    @Override
    public UserDto execute() {
        UserDtoMapper userDtoMapper = this.getMapper(UserDtoMapper.class);
        return userDtoMapper.selectByPrimaryKey(userId);
    }

}
