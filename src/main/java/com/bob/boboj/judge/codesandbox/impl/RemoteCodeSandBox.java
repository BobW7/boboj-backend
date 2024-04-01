package com.bob.boboj.judge.codesandbox.impl;

import com.bob.boboj.judge.codesandbox.CodeSandBox;
import com.bob.boboj.judge.codesandbox.model.ExecuteCodeRequest;
import com.bob.boboj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 远程代码沙箱（实际调用接口的沙箱）
 */
public class RemoteCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {

        System.out.println("远程代码沙箱");
        return new ExecuteCodeResponse();
    }
}
