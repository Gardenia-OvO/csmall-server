package cn.hqu.csmall.resource.config;

import cn.hqu.csmall.commons.config.CsmallCommonsConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 导入其它配置类的配置类
 *
 * @author java@tedu.cn
 * @version 0.0.1
 */
@Slf4j
@Configuration
@Import({
        CsmallCommonsConfiguration.class
})
public class ImportConfiguration {

    public ImportConfiguration() {
        log.debug("创建配置类对象：ImportConfiguration");
    }

}
