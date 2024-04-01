package com.bob.boboj.judge.codesandbox.impl;

import com.bob.boboj.judge.codesandbox.CodeSandBox;
import com.bob.boboj.judge.codesandbox.model.ExecuteCodeRequest;
import com.bob.boboj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 第三方代码沙箱（调用可能现成的代码沙箱，例如go-judge）
 */
public class TirdPartyCodeSandBox implements CodeSandBox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {

        System.out.println("第三方代码沙箱");
        return new ExecuteCodeResponse();
    }
}
