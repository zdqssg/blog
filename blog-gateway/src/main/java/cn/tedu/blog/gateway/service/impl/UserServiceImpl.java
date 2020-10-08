package cn.tedu.blog.gateway.service.impl;


import cn.tedu.blog.common.model.User;
import cn.tedu.blog.gateway.mapper.UserMapper;
import cn.tedu.blog.gateway.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zdq247209@163.com
 * @since 2020-10-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
