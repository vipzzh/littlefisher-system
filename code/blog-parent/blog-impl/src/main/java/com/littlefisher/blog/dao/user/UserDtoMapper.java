package com.littlefisher.blog.dao.user;

import tk.mybatis.mapper.common.Mapper;

import java.util.List;

import com.littlefisher.blog.model.user.UserDto;
import com.littlefisher.blog.request.user.GetUserList4PagerByCondRequest;

/**
 * Description: user Mapper 接口
 *
 * Created on 2017年11月24日
 *
 * @author jinyanan
 * @version 1.0
 * @since v1.0
 */
public interface UserDtoMapper extends Mapper<UserDto> {

    /**
     * 根据条件查询
     *
     * @param req req
     * @return List<UserDto>
     */
    List<UserDto> selectByCond(GetUserList4PagerByCondRequest req);
}