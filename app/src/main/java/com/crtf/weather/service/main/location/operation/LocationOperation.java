package com.crtf.weather.service.main.location.operation;

import com.crtf.weather.data.pojo.baidu.general.Location;
import com.crtf.weather.service.main.Operation;
/**
 * @author crtf
 * @date 2021年5月15日 星期六 0:41
 * @version 1.0
 */
import java.util.Observer;

/**
 * @see Location 位置信息
 * @author crtf
 * @date 2021年5月13日 星期四 18:43
 */
public interface LocationOperation extends Operation {



    /**
     * 定位
     * @return
     */
    void positioning();

}
