package com.cetide.codeforge.judge.strategy;

import com.cetide.codeforge.judge.codesandbox.model.JudgeInfo;

/**
 * 判题策略
 *
 * @author heathcetide
 */
public interface JudgeStrategy {

    /**
     * 执行判题
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
