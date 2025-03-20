package com.cetide.blog.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.cetide.blog.mapper.ShortLinkGroupMapper;
import com.cetide.blog.model.dto.req.ShortLinkGroupSortRequest;
import com.cetide.blog.model.dto.req.ShortLinkGroupUpdateRequest;
import com.cetide.blog.model.entity.ShortLinkGroup;
import com.cetide.blog.model.entity.user.User;
import com.cetide.blog.model.vo.ShortLinkGroupVO;
import com.cetide.blog.service.ShortLinkGroupService;
import com.cetide.blog.util.AuthContext;
import com.cetide.blog.util.RandomGenerator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShortLinkGroupServiceImpl extends ServiceImpl<ShortLinkGroupMapper, ShortLinkGroup>
        implements ShortLinkGroupService {

    @Override
    public void saveGroup(String groupName) {
        String gid;
        do {
            gid = RandomGenerator.generateRandomString(6);
        } while (!hasGid(gid));
        ShortLinkGroup group = new ShortLinkGroup();
        group.setName(groupName);
        group.setUsername(AuthContext.getCurrentUser().getUsername());
        group.setGid(gid);
        baseMapper.insert(group);
    }

    @Override
    public List<ShortLinkGroupVO> listGroup() {
        LambdaQueryWrapper<ShortLinkGroup> wrapper = Wrappers.lambdaQuery(ShortLinkGroup.class)
                .eq(ShortLinkGroup::getDeleted,0)
                .eq(ShortLinkGroup::getUsername, AuthContext.getCurrentUser().getUsername())
                .orderByDesc(ShortLinkGroup::getSortOrder, ShortLinkGroup::getUpdatedAt);
        List<ShortLinkGroup> list = baseMapper.selectList(wrapper);
        return BeanUtil.copyToList(list, ShortLinkGroupVO.class);
    }

    /**
     * 更新短链接分组
     * @param shortLinkGroupUpdateRequest
     */
    @Override
    public void updateGroup(ShortLinkGroupUpdateRequest shortLinkGroupUpdateRequest) {
        LambdaUpdateWrapper<ShortLinkGroup> eq = Wrappers.lambdaUpdate(ShortLinkGroup.class)
                .eq(ShortLinkGroup::getGid, shortLinkGroupUpdateRequest.getGid())
                .eq(ShortLinkGroup::getUsername, AuthContext.getCurrentUser().getUsername())
                .eq(ShortLinkGroup::getDeleted, 0);
        ShortLinkGroup shortLinkGroup = new ShortLinkGroup();
        shortLinkGroup.setName(shortLinkGroupUpdateRequest.getName());
        baseMapper.update(shortLinkGroup, eq);
    }

    @Override
    public void deleteGroup(String gid) {
        LambdaUpdateWrapper<ShortLinkGroup> eq = Wrappers.lambdaUpdate(ShortLinkGroup.class)
                .eq(ShortLinkGroup::getGid, gid)
                .eq(ShortLinkGroup::getUsername, AuthContext.getCurrentUser().getUsername())
                .eq(ShortLinkGroup::getDeleted, 0);
        ShortLinkGroup shortLinkGroup = new ShortLinkGroup();
        shortLinkGroup.setDeleted(1);
        baseMapper.update(shortLinkGroup, eq);
    }

    @Override
    public void sortGroup(List<ShortLinkGroupSortRequest> requestList) {
        requestList.forEach(each -> {
            ShortLinkGroup group = new ShortLinkGroup();
            group.setSortOrder(each.getSortOrder());
            group.setGid(each.getGid());
            LambdaUpdateWrapper<ShortLinkGroup> eq = Wrappers.lambdaUpdate(ShortLinkGroup.class)
                    .eq(ShortLinkGroup::getGid, each.getGid())
                    .eq(ShortLinkGroup::getUsername, AuthContext.getCurrentUser().getUsername())
                    .eq(ShortLinkGroup::getDeleted, 0);
            baseMapper.update(group, eq);
        });
    }

    private boolean hasGid(String gid) {
        LambdaQueryWrapper<ShortLinkGroup> wrapper = new LambdaQueryWrapper<>();
        ShortLinkGroup shortLinkGroup = baseMapper.selectOne(wrapper);
        return shortLinkGroup == null;
    }
}
