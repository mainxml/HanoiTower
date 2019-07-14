package xyz.okxy.hanoitower;

import java.util.Stack;

/**
 * 每个柱子就是一个栈
 *
 * @author zcp
 * @since 2018/9/7
 */
public class PillarStack<T> extends Stack<T> {

    /** 柱子名 */
    private String mStackName;

    public PillarStack(String stackName) {
        mStackName = stackName;
    }

    public String getStackName() {
        return mStackName;
    }
}
