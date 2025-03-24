package com.cetide.codeforge.controller;


import com.cetide.codeforge.common.ApiResponse;
import com.cetide.codeforge.service.impl.FileServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

@RequestMapping("/api/files1")
@RestController
@Api(tags = "临时秘钥")
public class FileController1 {

    @Resource
    FileServiceImpl fileService;

    @GetMapping("/get")
    @ApiOperation("生成临时秘钥")
    public ApiResponse getTemporaryKey(){
        String key = fileService.createTemporaryKey();
        return ApiResponse.success(key);
    }

}
