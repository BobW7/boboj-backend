package com.bob.boboj.judge.codesandbox;

import com.bob.boboj.judge.codesandbox.impl.ExampleCodeSandBox;
import com.bob.boboj.judge.codesandbox.impl.RemoteCodeSandBox;
import com.bob.boboj.judge.codesandbox.impl.TirdPartyCodeSandBox;

/**
 * 代码沙箱静态工厂
 */
public class CodeSandBoxFactory {
    /**
     * 创建代码沙箱实例
     *
     * @param type 沙箱类型
     * @return
     */
    public static CodeSandBox newInstance(String type) {
        switch (type) {
            case "remote":
                return new RemoteCodeSandBox();
            case "thirdParty":
                return new TirdPartyCodeSandBox();
            case "example":
            default:
                return new ExampleCodeSandBox();
        }
    }
}
