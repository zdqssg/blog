package cn.tedu.blog.api.blog.service.impl;


import cn.tedu.blog.api.blog.mapper.UserMapper;
import cn.tedu.blog.api.blog.service.IUserService;
import cn.tedu.blog.common.model.User;
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
