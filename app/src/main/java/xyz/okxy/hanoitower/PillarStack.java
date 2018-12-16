package xyz.okxy.hanoitower;

import java.util.Stack;

/**
 * 每个柱子就是一个栈
 * @author zcp
 * @since 2018/9/7
 */
public class PillarStack<T> extends Stack<T> {

    private String mName;

    public PillarStack(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

}
