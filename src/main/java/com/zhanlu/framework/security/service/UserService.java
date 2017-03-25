package com.zhanlu.framework.security.service;

import java.util.List;

import com.zhanlu.framework.common.page.Page;
import com.zhanlu.framework.common.page.PropertyFilter;
import com.zhanlu.framework.common.service.CommonService;
import com.zhanlu.framework.common.utils.Digests;
import com.zhanlu.framework.common.utils.EncodeUtils;
import com.zhanlu.framework.security.entity.Role;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import com.zhanlu.framework.security.dao.UserDao;
import com.zhanlu.framework.security.entity.Org;
import com.zhanlu.framework.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * 用户管理类
 * @author yuqs
 * @since 0.1
 */
@Service
public class UserService extends CommonService<User, Long> {
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	@Autowired
	private UserDao userDao;

	@PostConstruct
	@Override
	public void initDao() {
		super.commonDao = userDao;
	}

	/**
	 * 保存、更新用户实体
	 * @param entity
	 */
	@Transactional
	@Override
	public User save(User entity) {
		if (StringUtils.isNotBlank(entity.getPlainPassword())) {
			entryptPassword(entity);
		}
		userDao.save(entity);
		return entity;
	}

	/**
	 * 根据用户名称，获取用户实体
	 * @param username
	 * @return
	 */
	public User findUserByName(String username) {
		return userDao.findUniqueBy("username", username);
	}
	
	/**
	 * 根据用户名判断是否唯一
	 * @param newUserName
	 * @param oldUserName
	 * @return
	 */
	public boolean isUserNameUnique(String newUserName, String oldUserName) {
		return userDao.isPropertyUnique("username", newUserName, oldUserName);
	}
	
	/**
	 * 根据分页对象、所属部门ID号，分页查询用户列表
	 * @param page
	 * @param orgId
	 * @return
	 */
	public Page<User> searchUser(final Page<User> page, Long orgId) {
		Org org = new Org(orgId);
		
		String hql = "from User user where user.org=?";
		return userDao.findPage(page, hql, org);
	}

	/**
	 * 根据orgId获取部门用户
	 * @param orgId
	 * @return
	 */
	public List<User> getByOrg(Long orgId) {
		if(orgId == null || orgId == 0L) {
			return userDao.getAll();
		}
		return userDao.find("from User user where user.org=?", new Org(orgId));
	}
	
	/**
	 * 根据用户ID查询该用户所拥有的权限列表
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getAuthoritiesName(Long userId) {
		String sql = "select a.name from sec_user u " + 
					" left outer join sec_role_user ru on u.id=ru.user_id " + 
					" left outer join sec_role r on ru.role_id=r.id " + 
					" left outer join sec_role_authority ra on r.id = ra.role_id " + 
					" left outer join sec_authority a on ra.authority_id = a.id " +                     
					" where u.id=? ";
		SQLQuery query = userDao.createSQLQuery(sql, userId);
		return query.list();
	}
	
	/**
	 * 根据用户ID查询该用户所拥有的角色列表
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getRolesName(Long userId) {
		String sql = "select r.name from sec_user u " + 
					" left outer join sec_role_user ru on u.id=ru.user_id " + 
					" left outer join sec_role r on ru.role_id=r.id " + 
					" where u.id=? ";
		SQLQuery query = userDao.createSQLQuery(sql, userId);
		return query.list();
	}
	
	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(EncodeUtils.hexEncode(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
		user.setPassword(EncodeUtils.hexEncode(hashPassword));
	}

}
