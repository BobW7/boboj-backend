package com.bob.boboj.judge.codesandbox.impl;

import com.bob.boboj.judge.codesandbox.CodeSandBox;
import com.bob.boboj.judge.codesandbox.model.ExecuteCodeRequest;
import com.bob.boboj.judge.codesandbox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * 示例代码沙箱（跑通业务流程用）
 */
@Slf4j
public class ExampleCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("示例代码沙箱");
        return new ExecuteCodeResponse();
    }
}
