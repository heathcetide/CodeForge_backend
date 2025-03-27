package com.cetide.codeforge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cetide.codeforge.model.dto.PageResult;
import com.cetide.codeforge.model.dto.UserMatchesDTO;
import com.cetide.codeforge.model.entity.UserMatches;
import com.cetide.codeforge.model.query.UserMatchesQuery;
import java.lang.Boolean;

/**
 * 用户匹配PK管理
 *
 * @author heathcetide
 */
public interface UserMatchesService extends IService<UserMatches> {

    /**
     * 发起PK比赛:检查用户1和用户2是否存在，如果存在则记录比赛信息
     *
     * @param userMatchesDTO 用户匹配PK发起参数
     * @return
     */
    Boolean startMatch(UserMatchesDTO userMatchesDTO);

    /**
     * 结束PK比赛:检查比赛是否存在，如果存在则根据答题正确率和速度判定胜者
     *
     * @param userMatchesQuery 用户匹配PK查询参数
     * @return  用户匹配PK实体
     */
    UserMatches endMatch(UserMatchesQuery userMatchesQuery);

    /**
     * 获取PK比赛详情:检查比赛是否存在，如果存在则查询比赛详情
     *
     * @param userMatchesQuery 用户匹配PK查询参数
     * @return  用户匹配PK实体
     */
    UserMatches getMatchInfo(UserMatchesQuery userMatchesQuery);

    /**
     * 获取用户PK比赛历史:检查用户是否存在，如果存在则查询用户的历史PK比赛记录
     *
     * @param userMatchesQuery 用户匹配PK查询参数
     */
    PageResult<UserMatches> getUserMatchHistory(UserMatchesQuery userMatchesQuery);
}
