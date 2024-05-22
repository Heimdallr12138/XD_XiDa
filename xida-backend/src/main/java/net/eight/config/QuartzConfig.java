package net.eight.config;

import net.eight.jobs.DisbandExpiredTeam;
import net.eight.jobs.UserRecommendationCache;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Quartz配置
 *
 * @author  
 * @date 2024/04/28
 */
@Configuration
public class QuartzConfig {

    /**
     * 工作触发时间
     */
    @Value("${super.job}")
    private String jobTriggerTime;

    /**
     * 解散团队到期工作细节
     *
     * @return {@link JobDetail}
     */
    @Bean
    public JobDetail disbandExpireTeamJobDetail() {
        return JobBuilder.newJob(DisbandExpiredTeam.class).storeDurably().build();
    }

    /**
     * 解散团队触发到期
     *
     * @return {@link Trigger}
     */
    @Bean
    public Trigger disbandExpireTeamTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(jobTriggerTime);
        return TriggerBuilder.newTrigger()
                .forJob(disbandExpireTeamJobDetail())
                .withSchedule(cronScheduleBuilder).build();
    }

    /**
     * 用户推荐缓存工作细节
     *
     * @return {@link JobDetail}
     */
    @Bean
    public JobDetail userRecommendationCacheJobDetail() {
        return JobBuilder.newJob(UserRecommendationCache.class).storeDurably().build();
    }

    /**
     * 用户推荐缓存触发
     *
     * @return {@link Trigger}
     */
    @Bean
    public Trigger userRecommendationCacheTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(jobTriggerTime);
        return TriggerBuilder.newTrigger()
                .forJob(userRecommendationCacheJobDetail())
                .withSchedule(cronScheduleBuilder).build();
    }
}
