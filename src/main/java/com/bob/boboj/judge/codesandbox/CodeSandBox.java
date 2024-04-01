package com.bob.boboj.judge.codesandbox;

import com.bob.boboj.judge.codesandbox.model.ExecuteCodeRequest;
import com.bob.boboj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */
public interface CodeSandBox {
    /**
     * 执行代码
     * 可以增加一个查看代码沙箱状态的接口
     *
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
