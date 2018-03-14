package com.hww.ucenter.common.dao.impl;

import com.hww.app.common.dto.AppSearchDto;
import com.hww.base.common.page.Pagination;
import com.hww.base.common.util.Finder;
import com.hww.base.util.BeanMapper;
import com.hww.ucenter.common.dao.MemberDao;
import com.hww.ucenter.common.dto.MyConcernDto;
import com.hww.ucenter.common.dto.UCenterMemberDto;
import com.hww.ucenter.common.dto.UCenterThirdDto;
import com.hww.ucenter.common.entity.UCenterMember;
import com.hww.ucenter.common.entity.UCenterSign;
import com.hww.ucenter.common.vo.MyConcernVo;
import com.hww.ucenter.common.vo.ThirdMemberVo;
import com.hww.ucenter.common.vo.UCenterMemberVo;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Repository
public class MemberDaoImpl extends LocalEntityDaoImpl<Long, UCenterMember> implements MemberDao {

    @Override
    public UCenterMember selectByMemberName(String memberName) {
        if (!StringUtils.hasText(memberName)) {
            return null;
        }
        Finder f = Finder.create("from UCenterMember bean");
        f.append(" where bean.memberName=:memberName");
        f.setParam("memberName", memberName);
        List<UCenterMember> UCenterMembers = (List<UCenterMember>) find(f);
        if (UCenterMembers != null && UCenterMembers.size() > 0) {
            return UCenterMembers.get(0);
        }
        return null;
    }

    @Override
    public boolean updateUserLocation(UCenterSign UCenterSign) {
        Finder f = Finder.create("update UCenterMember u");
        f.append(" set");
        f.append(" u.nowLon = :longitude,");
        f.append(" u.nowLat = :latitude");
        f.append(" where memberId = :memberId");

        f.setParam("longitude", UCenterSign.getLongitude());
        f.setParam("latitude", UCenterSign.getLatitude());
        f.setParam("memberId", UCenterSign.getMemberId());
        return update(f);
    }

    @Override
    public String concernUser(UCenterMember UCenterMember) {

        Finder f = Finder
                .create("SELECT GROUP_CONCAT(tomember_id) AS tm FROM ucenter_follow where member_id =:memberId");
        f.setParam("memberId", UCenterMember.getMemberId());

        List<MyConcernVo> list = (List<MyConcernVo>) findJoin(f, MyConcernVo.class);

        if (list != null && list.size() > 0) {
            return list.get(0).getTm();
        }
        return "";
    }

    @Override
    public Integer concernUserCount(MyConcernDto dto) {

        Finder f = Finder.create("SELECT count(1) as cu");
        f.append(" FROM ucenter_follow");
        f.append(" where member_id = :memberId");
        f.setParam("memberId", dto.getMemberId());

        List<Map<String, Object>> list = (List<Map<String, Object>>) findJoin(f, Map.class);
        Integer cu = Integer.parseInt(list.get(0).get("cu").toString());
        return cu;
    }

    @Override
    public Integer concernMyCount(MyConcernDto dto) {

        Finder f = Finder.create("SELECT count(1) as cu");
        f.append(" FROM ucenter_follow");
        f.append(" where tomember_id = :memberId");
        f.setParam("memberId", dto.getMemberId());

        List<Map<String, Object>> list = (List<Map<String, Object>>) findJoin(f, Map.class);
        return Integer.parseInt(list.get(0).get("cu").toString());
    }

    @Override
    public List<UCenterMemberVo> searchMembers(AppSearchDto search) {
        if (!StringUtils.hasText(search.getKeywords())) {
            return new ArrayList<>();
        }
        Finder f = Finder.create("from UCenterMember bean");
        f.append(" where bean.nickName like :nickName");

        f.setParam("nickName", "%" + search.getKeywords() + "%");

        if (search.getPageNo() == null) {
            search.setPageNo(1);
        }
        if (search.getPageSize() == null) {
            search.setPageSize(10);
        }
        f.setFirstResult((search.getPageNo() - 1) * search.getPageSize());
        f.setMaxResults(search.getPageSize());

        List<UCenterMember> UCenterMembers = (List<UCenterMember>) find(f);
        if (UCenterMembers != null && UCenterMembers.size() > 0) {
            return BeanMapper.mapList(UCenterMembers, UCenterMemberVo.class);
        }
        return new ArrayList<>();
    }

    @Override
    public UCenterMember thirdLogin(UCenterThirdDto thirdDto) {

        Finder f = Finder.create("from UCenterMember");
        f.append(" where 1 = 1");
        if (thirdDto.getQqUuid() != null) {
            f.append(" and qqUuid = :qqUuid");
            f.setParam("qqUuid", thirdDto.getQqUuid());
        } else if (thirdDto.getWeChatUuid() != null) {
            f.append(" and weChatUuid = :weChatUuid");
            f.setParam("weChatUuid", thirdDto.getWeChatUuid());
        } else if (thirdDto.getWeiBoUuid() != null) {
            f.append(" and weiBoUuid = :weiBoUuid");
            f.setParam("weiBoUuid", thirdDto.getWeiBoUuid());
        }else {
        	return null;
        }
        List<UCenterMember> UCenterMembers = (List<UCenterMember>) find(f);
        if (UCenterMembers != null && UCenterMembers.size() > 0) {
            return UCenterMembers.get(0);
        }
        return null;
    }

    @Override
    public UCenterMember ucenterMemberByPhone(ThirdMemberVo vo) {

        Finder f = Finder.create("from UCenterMember");
        f.append(" where phoneNo = :phoneNo");
        f.setParam("phoneNo", vo.getPhoneNo());
        List<UCenterMember> UCenterMembers = (List<UCenterMember>) find(f);
        if (UCenterMembers != null && UCenterMembers.size() > 0) {
            return UCenterMembers.get(0);
        }
        return null;
    }

    @Override
    public List<UCenterMember> listUcenterMemberByIdS(Long[] ids) {
        if (ids.length == 0)
            return null;
        Finder finder = Finder.create("from UCenterMember");
        finder.append("where memberId in :memberIds");
        finder.setParamList("memberIds", Arrays.asList(ids));
        return this.findWithGenericity(finder);

    }

    @Override
    public Pagination listUcenterMemberByKeysAndPage(UCenterMemberDto UCenterMemberDto) {
        Finder hql = Finder.create("from UCenterMember where 1=1");
        if (UCenterMemberDto.getSiteId() != null) {
            hql.append(" and siteId=:siteIdP").setParam("siteIdP", UCenterMemberDto.getSiteId());
        }
        if (UCenterMemberDto.getStatus() != null) {
            hql.append(" and status=:statusP").setParam("statusP", UCenterMemberDto.getStatus());
        }
        if (UCenterMemberDto.getMemberId() != null) {
            // hql.append(" and memberId=:memberIdP").setParam("memberIdP",
            // UCenterMemberDto.getMemberId());
            String s = " and memberId like '%" + UCenterMemberDto.getMemberId() + "%'";
            hql.append(s);
        }
        if (UCenterMemberDto.getPhoneNo() != null) {
            // hql.append(" and phoneNo=:phoneNoP").setParam("phoneNoP",
            // UCenterMemberDto.getPhoneNo());
            String s = " and phoneNo like '%" + UCenterMemberDto.getPhoneNo() + "%'";
            hql.append(s);
        }
        if (UCenterMemberDto.getWeChatNo() != null) {
            // hql.append(" and weChatNo=:weChatNoP").setParam("weChatNoP",
            // UCenterMemberDto.getWeChatNo());
            String s = " and weChatNo like '%" + UCenterMemberDto.getWeChatNo() + "%'";
            hql.append(s);
        }
        if (UCenterMemberDto.getQqNo() != null) {
            // hql.append(" and qqNo=:qqNoP").setParam("qqNoP", UCenterMemberDto.getQqNo());
            String s = " and qqNo like '%" + UCenterMemberDto.getQqNo() + "%'";
            hql.append(s);
        }
        if (UCenterMemberDto.getWeiBoNo() != null) {
            // hql.append(" and weiBoNo=:weiBoNoP").setParam("weiBoNoP",
            // UCenterMemberDto.getWeiBoNo());
            String s = " and weiBoNo like '%" + UCenterMemberDto.getWeiBoNo() + "%'";
            hql.append(s);
        }
        hql.append(" order by createTime desc");
        Pagination p = this.find(hql, UCenterMemberDto.getPageNo(), UCenterMemberDto.getPageSize());
        if (p == null || p.getList() == null || p.getList().size() == 0)
            return null;

        if (p.getList() != null) {
            p.setPageNo(UCenterMemberDto.getPageNo());
            p.setPageSize(UCenterMemberDto.getPageSize());
            List<UCenterMemberVo> memberDtoList = BeanMapper.mapList(p.getList(), UCenterMemberVo.class);
            p.setList(memberDtoList);
        }
        return p;
    }

    @Override
    public UCenterMember getUcenterMemberByPhoneNumber(String phoneNo,Long countryId) {
        Finder finder = Finder.create("from UCenterMember where 1=1");
        finder.append(" and phoneNo=:phoneNo");
        finder.setParam("phoneNo", phoneNo);
        if(countryId!=null) {
        	 finder.append(" and countryId=:countryId");
        	 finder.setParam("countryId", countryId);
        }
       
       
        List<UCenterMember> UCenterMemberList = (List<UCenterMember>) this.find(finder);
        if (null == UCenterMemberList || UCenterMemberList.size() == 0) {
            return null;
        }
        return UCenterMemberList.get(0);
    }
}
