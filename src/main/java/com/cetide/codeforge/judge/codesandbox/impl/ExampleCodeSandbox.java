package com.cetide.codeforge.judge.codesandbox.impl;



import com.cetide.codeforge.judge.codesandbox.CodeSandbox;
import com.cetide.codeforge.judge.codesandbox.model.ExecuteCodeRequest;
import com.cetide.codeforge.judge.codesandbox.model.ExecuteCodeResponse;
import com.cetide.codeforge.judge.codesandbox.model.JudgeInfo;
import com.cetide.codeforge.model.enums.JudgeInfoMessageEnum;
import com.cetide.codeforge.model.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * 示例代码沙箱（仅为了跑通业务流程）
 */
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("测试执行成功");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);
        return executeCodeResponse;
    }
}
