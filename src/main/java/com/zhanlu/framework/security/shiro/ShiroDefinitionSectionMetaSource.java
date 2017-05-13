package com.zhanlu.framework.security.shiro;

import com.zhanlu.framework.security.entity.Resource;
import com.zhanlu.framework.security.service.ResourceService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.List;

/**
 * 授权元数据根据两部分构成：
 * 1、数据库中动态生成，由注入的resourceManager根据资源、权限构造资源-权限的键值对
 * 2、使用spring的注入filterChainDefinitions，在配置文件中定义默认的安全定义，如登录页面，首页等
 *
 * @author yuqs
 * @since 0.1
 */
public class ShiroDefinitionSectionMetaSource implements FactoryBean<Ini.Section> {
    private static Log log = LogFactory.getLog(ShiroDefinitionSectionMetaSource.class);

    //注入资源管理对象
    @Autowired
    private ResourceService resourceService;
    //注入默认的授权定义
    private String filterChainDefinitions;
    //格式化数据，符合shiro的格式，如：perms["admin"]
    public static final String PREMISSION_FORMAT = "perms[\"{0}\"]";

    @Override
    public Section getObject() throws Exception {
        Ini ini = new Ini();
        ini.load(filterChainDefinitions);
        Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
        //由注入的资源管理对象获取所有资源数据，并且Resource的authorities的属性是EAGER的fetch类型
        List<Resource> resources = resourceService.findAll();
        for (Resource resource : resources) {
            if (StringUtils.isEmpty(resource.getSource())) {
                continue;
            }
            //如果资源的值为分号分隔，则循环构造元数据。分号分隔好处是对一批相同权限的资源，不需要逐个定义
            if (resource.getSource().indexOf(";") != -1) {
                String[] sources = resource.getSource().split(";");
                for (String source : sources) {
                    section.put(source, MessageFormat.format(PREMISSION_FORMAT, resource.getCode()));
                }
            } else {
                section.put(resource.getSource(), MessageFormat.format(PREMISSION_FORMAT, resource.getCode()));
            }
        }
        section.put("/**", "user");
        return section;
    }

    public void setFilterChainDefinitions(String filterChainDefinitions) {
        this.filterChainDefinitions = filterChainDefinitions;
    }

    @Override
    public Class<?> getObjectType() {
        return this.getClass();
    }

    @Override
    public boolean isSingleton() {
        return false;
    }


}
