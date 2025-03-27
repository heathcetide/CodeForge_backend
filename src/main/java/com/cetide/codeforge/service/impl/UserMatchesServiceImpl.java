package com.cetide.codeforge.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cetide.codeforge.mapper.UserAnswerMapper;
import com.cetide.codeforge.mapper.UserMatchesMapper;
import com.cetide.codeforge.model.dto.PageResult;
import com.cetide.codeforge.model.dto.UserMatchesDTO;
import com.cetide.codeforge.model.entity.UserAnswer;
import com.cetide.codeforge.model.entity.UserMatches;
import com.cetide.codeforge.model.query.UserMatchesQuery;
import com.cetide.codeforge.service.UserMatchesService;
import java.lang.Boolean;
import java.lang.Override;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.cetide.codeforge.exception.BusinessException;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cetide.codeforge.common.constants.ResultCodeConstant;
import java.util.Date;

/**
 * 用户匹配PK管理的实现
 *
 * @author DELL
 * @date 2025-03-25 09:59:14
 */
@Service
public class UserMatchesServiceImpl extends ServiceImpl<UserMatchesMapper, UserMatches> implements UserMatchesService {

    @Autowired
    private UserMatchesMapper userMatchesMapper;

    @Autowired
    private UserAnswerMapper userAnswersMapper;

    @Override
    public Boolean startMatch(UserMatchesDTO userMatchesDTO) {
        QueryWrapper<UserAnswer> queryWrapper1 = Wrappers.query();
        queryWrapper1.eq("user_id", userMatchesDTO.getUserId1());
        List<UserAnswer> userAnswersList1 = userAnswersMapper.selectList(queryWrapper1);
        QueryWrapper<UserAnswer> queryWrapper2 = Wrappers.query();
        queryWrapper2.eq("user_id", userMatchesDTO.getUserId2());
        List<UserAnswer> userAnswersList2 = userAnswersMapper.selectList(queryWrapper2);
        if (userAnswersList1.isEmpty() || userAnswersList2.isEmpty()) {
            throw new BusinessException(ResultCodeConstant.CODE_000001_MSG);
        }
        UserMatches userMatchesDO = new UserMatches();
        userMatchesDO.setUserId1(userMatchesDTO.getUserId1());
        userMatchesDO.setUserId2(userMatchesDTO.getUserId2());
        userMatchesDO.setMatchStartTime(new Date());
        userMatchesDO.setCreateBy(userMatchesDTO.getUserId1());
        userMatchesDO.setCreateTime(new Date());
        userMatchesDO.setUpdateBy(userMatchesDTO.getUserId1());
        userMatchesDO.setUpdateTime(new Date());
        userMatchesMapper.insert(userMatchesDO);
        return true;
    }

    @Override
    public UserMatches endMatch(UserMatchesQuery userMatchesQuery) {
        UserMatches userMatchesDO = userMatchesMapper.selectById(userMatchesQuery.getMatchId());
        if (userMatchesDO == null) {
            throw new BusinessException(ResultCodeConstant.CODE_000001_MSG);
        }
        QueryWrapper<UserAnswer> queryWrapper1 = Wrappers.query();
        queryWrapper1.eq("user_id", userMatchesDO.getUserId1());
        List<UserAnswer> userAnswersList1 = userAnswersMapper.selectList(queryWrapper1);
        QueryWrapper<UserAnswer> queryWrapper2 = Wrappers.query();
        queryWrapper2.eq("user_id", userMatchesDO.getUserId2());
        List<UserAnswer> userAnswersList2 = userAnswersMapper.selectList(queryWrapper2);
        int correctCount1 = 0;
        int correctCount2 = 0;
        long totalTime1 = 0;
        long totalTime2 = 0;
        for (UserAnswer answer : userAnswersList1) {
            if (answer.getIsCorrect() != null && answer.getIsCorrect()) {
                correctCount1++;
            }
            if (answer.getAnswerTime() != null) {
                totalTime1 += answer.getAnswerTime().getTime();
            }
        }
        for (UserAnswer answer : userAnswersList2) {
            if (answer.getIsCorrect() != null && answer.getIsCorrect()) {
                correctCount2++;
            }
            if (answer.getAnswerTime() != null) {
                totalTime2 += answer.getAnswerTime().getTime();
            }
        }
        if (correctCount1 > correctCount2 || (correctCount1 == correctCount2 && totalTime1 < totalTime2)) {
            userMatchesDO.setWinnerUserId(userMatchesDO.getUserId1());
        } else if (correctCount2 > correctCount1 || (correctCount1 == correctCount2 && totalTime1 > totalTime2)) {
            userMatchesDO.setWinnerUserId(userMatchesDO.getUserId2());
        }
        userMatchesDO.setMatchEndTime(new Date());
        userMatchesDO.setUpdateTime(new Date());
        userMatchesMapper.updateById(userMatchesDO);
        return userMatchesDO;
    }

    @Override
    public UserMatches getMatchInfo(UserMatchesQuery userMatchesQuery) {
        UserMatches userMatchesDO = userMatchesMapper.selectById(userMatchesQuery.getMatchId());
        if (userMatchesDO == null) {
            throw new BusinessException(ResultCodeConstant.CODE_000001_MSG);
        }
        return userMatchesDO;
    }

    @Override
    public PageResult<UserMatches> getUserMatchHistory(UserMatchesQuery userMatchesQuery) {
        QueryWrapper<UserAnswer> queryWrapper = Wrappers.query();
        queryWrapper.eq("user_id", userMatchesQuery.getUserId());
        List<UserAnswer> userAnswersList = userAnswersMapper.selectList(queryWrapper);
        if (userAnswersList.isEmpty()) {
            throw new BusinessException(ResultCodeConstant.CODE_000001_MSG);
        }
        QueryWrapper<UserMatches> matchQueryWrapper = Wrappers.query();
        matchQueryWrapper.eq("user_id1", userMatchesQuery.getUserId()).or().eq("user_id2", userMatchesQuery.getUserId());
        Page<UserMatches> page = new Page<>(userMatchesQuery.getPageIndex(), userMatchesQuery.getPageSize());
//        IPage<UserMatches> userMatchesPage = userMatchesMapper.selectPage(page, matchQueryWrapper);
//        PageResult<UserMatches> pageResult = new PageResult<>(userMatchesPage);
//        return pageResult;
        return null;
    }
}
