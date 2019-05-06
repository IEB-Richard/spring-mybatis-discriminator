package tk.mybatis.simple.mapper;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.type.Enabled;

public class RoleMapperTest extends BaseMapperTest {

	@Test
	public void testSelectById() {
		SqlSession sqlSession = getSqlSession();
		try {
			// Get the RoleMapper interface
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			SysRole role = roleMapper.selectById(2L);
			Assert.assertNotNull(role);
			Assert.assertEquals(Enabled.enabled, role.getEnabled());
		} finally {
			// Please don't really change database data, that will affect other tests
			sqlSession.rollback();
			// Please close the SQL session after each test case.
			sqlSession.close();
		}
	}

	@Test
	public void testUpdateById() {
		SqlSession sqlSession = getSqlSession();
		try {
			// Get the RoleMapper interface
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			SysRole role = roleMapper.selectById(2L);
			role.setEnabled(Enabled.disabled);
			// disable it now
			int result = roleMapper.updateById(role);
			Assert.assertEquals(1, result);
			
			// get data from database again
			role = roleMapper.selectById(2L);
			Assert.assertEquals(Enabled.disabled, role.getEnabled());
		} finally {
			// Please don't really change database data, that will affect other tests
			sqlSession.rollback();
			// Please close the SQL session after each test case.
			sqlSession.close();
		}
	}

	@Test
	public void testSelectRoleByUserIdChoose() {
		SqlSession sqlSession = getSqlSession();
		try {
			// Get the RoleMapper interface
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);

			// because currently all records in db table are enabled, 
			// let's get one disable it first
			SysRole role = roleMapper.selectById(2L);
			role.setEnabled(Enabled.disabled);
			roleMapper.updateById(role);
			
			// Let's get the roles of user with id 1L
			List<SysRole> roleList = roleMapper.selectRoleByUserId(1L);
			for(SysRole r: roleList) {
				System.out.println("Role Name: " + r.getRoleName());
				
			}
			
		} finally {
			// Please don't really change database data, that will affect other tests
			sqlSession.rollback();			
			// Please close the SQL session after each test case.
			sqlSession.close();
		}
	}
}
