package com.itheima;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 * @Version 1.0
 */
public class TestJob {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("applicationContext-job.xml");
    }
}
