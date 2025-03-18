package com.ruty.rutyserver.batch;

import com.ruty.rutyserver.entity.Routine;
import com.ruty.rutyserver.entity.RoutineHistory;
import com.ruty.rutyserver.entity.e.Week;
import com.ruty.rutyserver.repository.RoutineHistoryRepository;
import com.ruty.rutyserver.repository.RoutineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class YesterdayBatchItem {
    private final RoutineRepository routineRepository;
    private final RoutineHistoryRepository routineHistoryRepository;

    @Bean
    public RepositoryItemReader<Routine> yesterdayReader() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        Week week = Week.fromDayOfWeek(yesterday.getDayOfWeek());

        // id값으로 데이터를 10개씩 읽어와서 처리하도록 함.
        return new RepositoryItemReaderBuilder<Routine>()
                .name("yesterdayReader")
                .pageSize(10)
                .methodName("findAllByDateAndDayOfWeek") //
                .arguments(Arrays.asList(yesterday, week)) // 날짜와 요일을 넘김
                .repository(routineRepository)
                .sorts(Map.of("id", Sort.Direction.ASC)) // id기반으로 정렬해서 데이터가 꼬이지 않도록 함.
                .build();
    }

    @Bean
    public ItemProcessor<Routine, RoutineHistory> yesterdayProcessor() {

        return new ItemProcessor<Routine, RoutineHistory>() {
            @Override
            public RoutineHistory process(Routine item) throws Exception {
                RoutineHistory routineHistory = RoutineHistory.builder()
                        .member(item.getMember())
                        .routine(item)
                        .date(LocalDate.now().minusDays(1))
                        .isDone(item.getIsDone())
                        .build();

                item.setIsDone(false);
                routineRepository.save(item);
                return routineHistory;
            }
        };
    }

    @Bean
    public RepositoryItemWriter<RoutineHistory> yesterdayWriter() {
        return new RepositoryItemWriterBuilder<RoutineHistory>()
                .repository(routineHistoryRepository)
                .methodName("save")
                .build();
    }
}
