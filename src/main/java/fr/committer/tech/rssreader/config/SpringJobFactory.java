package fr.committer.tech.rssreader.config;

import lombok.extern.slf4j.Slf4j;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

@Slf4j
public final class SpringJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

    private transient AutowireCapableBeanFactory beanFactory;

    @Override
    public void setApplicationContext(ApplicationContext ctx) {
        beanFactory = ctx.getAutowireCapableBeanFactory();
    }

    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object job = super.createJobInstance(bundle);
        log.info("Job instance creation");
        beanFactory.autowireBean(job);
//        beanFactory.autowireBeanProperties(job, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
        beanFactory.initializeBean(job, job.getClass().getName());
        return job;
    }
}
