package com.cetide.codeforge.common.constants;

public interface TaskConstants {
    // 任务状态
    int TASK_STATUS_PENDING = 0;    // 待执行
    int TASK_STATUS_RUNNING = 1;    // 执行中
    int TASK_STATUS_FINISHED = 2;   // 已完成
    int TASK_STATUS_FAILED = 3;     // 执行失败
    
    // 任务类型
    int TASK_TYPE_ASSET = 1;        // 资产扫描
    int TASK_TYPE_VULN = 2;         // 漏洞扫描
    
    // 漏洞等级
    int VULN_LEVEL_LOW = 1;         // 低危
    int VULN_LEVEL_MEDIUM = 2;      // 中危
    int VULN_LEVEL_HIGH = 3;        // 高危
} 