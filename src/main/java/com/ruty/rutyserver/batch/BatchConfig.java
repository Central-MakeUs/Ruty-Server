package com.ruty.rutyserver.batch;

import com.ruty.rutyserver.entity.Routine;
import com.ruty.rutyserver.entity.RoutineHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {
    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    private final YesterdayBatchItem yesterdayBatchItem;
    @Bean
    public Job yesterdayJob() {
        return new JobBuilder("yesterdayJob", jobRepository)
                .start(yesterdayStep())
                .build();
    }

    @Bean
    public Step yesterdayStep() {
        return new StepBuilder("yesterdayStep", jobRepository)
                .<Routine, RoutineHistory>chunk(10, platformTransactionManager)
                .reader(yesterdayBatchItem.yesterdayReader())
                .processor(yesterdayBatchItem.yesterdayProcessor())
                .writer(yesterdayBatchItem.yesterdayWriter())
                .build();
    }
}
