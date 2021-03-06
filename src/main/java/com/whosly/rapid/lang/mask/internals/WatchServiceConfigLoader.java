package com.whosly.rapid.lang.mask.internals;

import java.util.ServiceLoader;

import com.whosly.rapid.lang.mask.internals.tacitly.DefaultWatchServiceConfiguration;
import com.whosly.rapid.lang.mask.spi.IWatchServiceConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * spi 加载
 */
public class WatchServiceConfigLoader {
    private static final Logger log = LoggerFactory.getLogger(WatchServiceConfigLoader.class);
    private static Object oject = new Object();
    private static IWatchServiceConfiguration watchServiceConfiguration;

    /**
     * 获取配置的加载策略实现
     *
     * @return 配置的加载策略实现
     */
    public static IWatchServiceConfiguration get(){
        if(watchServiceConfiguration == null){
            synchronized (oject){
                if(watchServiceConfiguration == null){
                    watchServiceConfiguration = getInternals();
                }
            }
        }

        return watchServiceConfiguration;
    }

    private static IWatchServiceConfiguration getInternals(){
        ServiceLoader<IWatchServiceConfiguration> loaders = ServiceLoader
                .load(IWatchServiceConfiguration.class);

        IWatchServiceConfiguration watchServiceConfiguration = null;
        // 优先选择自定义spi实现
        for (IWatchServiceConfiguration in : loaders) {
            if(!(in instanceof DefaultWatchServiceConfiguration)){
                watchServiceConfiguration = in;
                break;
            }
        }

        // 如果没有自定义spi, 则采用默认
        if(watchServiceConfiguration == null) {
            IWatchServiceConfiguration defaultWatchServiceConfiguration = null;
            // 防止 java.lang.NoClassDefFoundError
            try{
                // 找出默认实例
                for (IWatchServiceConfiguration in : loaders) {
                    if(in instanceof DefaultWatchServiceConfiguration){
                        defaultWatchServiceConfiguration = in;
                        break;
                    }
                }
            } catch (Exception e) {
                log.error("WatchServiceConfigLoader 出现异常！", e);
            }

            watchServiceConfiguration = defaultWatchServiceConfiguration;
        } else {
//            // 否则，nothing
//            if(defaultWatchServiceConfiguration != null){
//            log.debug("销毁默认IWatchServiceConfiguration：{}.", defaultWatchServiceConfiguration.getClass().getCanonicalName());
//                defaultWatchServiceConfiguration = null;
//            }
        }

        // 配置初始化加载
        watchServiceConfiguration.load();

        log.debug("初始化 IWatchServiceConfiguration, 初始化实例对象 -> 类型: {}, class: {}。", watchServiceConfiguration.watchConfigureType(), watchServiceConfiguration.getClass());
        return watchServiceConfiguration;
    }

}
