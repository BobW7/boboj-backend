package com.bob.boboj.codesandbox;

import com.bob.boboj.judge.codesandbox.CodeSandBox;
import com.bob.boboj.judge.codesandbox.CodeSandBoxFactory;
import com.bob.boboj.judge.codesandbox.CodeSandBoxLogProxy;
import com.bob.boboj.judge.codesandbox.impl.ExampleCodeSandBox;
import com.bob.boboj.judge.codesandbox.model.ExecuteCodeRequest;
import com.bob.boboj.judge.codesandbox.model.ExecuteCodeResponse;
import com.bob.boboj.model.enums.QuestionSubmitLanguageEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class CodeSandBoxTest {

    @Value("${codesandbox.type:example}")
    private String type;

    @Test
    void executeCode() {
        // 静态工厂指定沙箱类型
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance("example");

        String code = "int main";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1,2", "3,4");

        // 来自@Builder注解，链式调用，建造者模式
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse response = codeSandBox.executeCode(executeCodeRequest);
    }

    @Test
    void executeCodeByValue() {
        // 静态工厂指定沙箱类型
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);

        String code = "int main";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1,2", "3,4");

        // 来自@Builder注解，链式调用，建造者模式
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse response = codeSandBox.executeCode(executeCodeRequest);
    }

    @Test
    void executeCodeWithLog() {
        // 静态工厂指定沙箱类型
        CodeSandBox codeSandBox = CodeSandBoxFactory.newInstance(type);
        // 将沙箱对象传入代理增强，使其输出日志信息
        codeSandBox = new CodeSandBoxLogProxy(codeSandBox);

        String code = "int main";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1,2", "3,4");

        // 来自@Builder注解，链式调用，建造者模式
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        // 用增强后的代理类来调用execute方法
        ExecuteCodeResponse response = codeSandBox.executeCode(executeCodeRequest);
    }
}
