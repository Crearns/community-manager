package com.cms.community.service.impl;

import com.cms.common.util.PageBean;
import com.cms.common.vo.community.CommunityDetailsVo;
import com.cms.common.vo.community.CommunitySquareVo;
import com.cms.common.vo.community.MyCommunityVo;
import com.cms.community.dao.CommunityDao;
import com.cms.community.service.CommunityService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Creams
 */
@Service
public class CommunityServiceImpl implements CommunityService {

    @Autowired
    private CommunityDao communityDao;


    @Override
    public List<CommunitySquareVo> getSquareList(int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);

        List<CommunitySquareVo> squareList = communityDao.getSquareList();
        int count = squareList.size();
        PageBean<CommunitySquareVo> pageData = new PageBean<>(currentPage, pageSize, count);
        pageData.setItems(squareList);
        return pageData.getItems();
    }

    @Override
    public CommunityDetailsVo searchDetailsInfo(int communityId) {
        return communityDao.detailsInfo(communityId);
    }

    @Override
    public List<MyCommunityVo> getCommunityByUserId(String userId) {
        return communityDao.getCommunityByUserId(userId);
    }
}
