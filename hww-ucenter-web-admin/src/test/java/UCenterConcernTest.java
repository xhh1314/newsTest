//import com.hww.base.common.page.Pagination;
//import com.hww.base.util.R;
//import com.hww.ucenter.common.dto.UCenterUserDto;
//import com.hww.ucenter.common.vo.UCenterUserVo;
//import com.hww.ucenter.webadmin.service.UCenterUserService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.hww.UCenterWebAdminApplication;
//import com.hww.ucenter.common.dao.MemberDao;
//import com.hww.ucenter.common.entity.UCenterMember;
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.List;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = WebEnvironment.MOCK, classes = UCenterWebAdminApplication.class)
//public class UcenterConcernTest {
//	@Autowired
//	private MemberDao ucenterMemberDao;
//	@Autowired
//	private UCenterUserService UCenterUserService;
//
//	@Test
//	public void ucenterFollowTest() {
//		UCenterMember ucenterMember = new UCenterMember();
//		ucenterMember.setMemberId(1l);
//		ucenterMemberDao.concernUser(ucenterMember);
//
//	}
//
//	@Test
//	public void ucenterFindSqlAndKey() {
//		UCenterUserDto UCenterUserDto = new UCenterUserDto();
//		UCenterUserDto.setPageNo(2);
//		UCenterUserDto.setPageSize(10);
//		Pagination pagination = UCenterUserService.listUCenterUserByKeysAndPage(UCenterUserDto);
//		System.out.println();
//
//	}
//
//	@Test
//	public void insertUCenterUser() throws InvocationTargetException, IllegalAccessException {
//		UCenterUserVo UCenterUserVo = new UCenterUserVo();
//		UCenterUserVo.setPassword("123456");
//		UCenterUserVo.setPhoneNo("15901015121");
//		UCenterUserVo.setPseudonym("荣融");
//		UCenterUserVo.setRealName("蓉蓉");
//		UCenterUserVo.setSiteId(1);
//		UCenterUserVo.setStatus(new Short("1"));
//		R R = UCenterUserService.saveUCenterUser(UCenterUserVo);
//		System.out.println();
//	}
//
//}
