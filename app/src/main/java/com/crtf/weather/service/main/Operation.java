package com.crtf.weather.service.main;

import java.util.Observer;

/**
 * @author crtf
 * @version 1.0
 * @date 2021年5月17日 星期一 14:09
 */
public interface Operation {
    /**
     * 添加观察位置变化的观察者
     */
    void addObserver(Observer observer);

    /**
     * 释放资源
     */
    void free();
}
