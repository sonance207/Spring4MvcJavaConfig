package com.johnmcgrath.config;

import com.johnmcgrath.interceptors.ExecutionTimerInterceptor;
import com.johnmcgrath.interceptors.HeaderInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import javax.sql.DataSource;

@Configuration // adds the middle ware or the beans at startup
@ComponentScan("com.johnmcgrath.*")
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {




    @Bean
    public DataSource dataSource() {
        final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
        dsLookup.setResourceRef(true);
        DataSource dataSource = dsLookup.getDataSource("jdbc/springdb");
        return dataSource;
    }

    //@Bean // changes the default behavior of request mapping methods.
    //public RequestMappingHandlerMapping requestMappingHandlerMapping() {
    //    RequestMappingHandlerMapping rmhm = new RequestMappingHandlerMapping();
    //    rmhm.setUseSuffixPatternMatch(true);
    //    rmhm.setUseTrailingSlashMatch(true);
    //    return rmhm;
    //}

    @Bean()
    public UrlBasedViewResolver urlBasedViewResolver() {
        UrlBasedViewResolver  resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry){
        registry.addViewController("/").setViewName("home");
    }

    @Autowired
    private HeaderInterceptor headerInterceptor;

    @Autowired
    private ExecutionTimerInterceptor executionTimerInterceptor;

    @Override // use generate to create override method; inject the custom HeaderInterceptor class
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(headerInterceptor);
        registry.addInterceptor(executionTimerInterceptor).addPathPatterns("/location");
    }
}