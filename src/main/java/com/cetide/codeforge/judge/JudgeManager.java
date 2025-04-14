package com.cetide.codeforge.judge;

import com.cetide.codeforge.judge.codesandbox.model.JudgeInfo;
import com.cetide.codeforge.judge.strategy.DefaultJudgeStrategy;
import com.cetide.codeforge.judge.strategy.JavaLanguageJudgeStrategy;
import com.cetide.codeforge.judge.strategy.JudgeContext;
import com.cetide.codeforge.judge.strategy.JudgeStrategy;
import com.cetide.codeforge.model.entity.question.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 */
@Service
public class JudgeManager {

    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }

}
