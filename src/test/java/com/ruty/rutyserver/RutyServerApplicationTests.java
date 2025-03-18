package com.ruty.rutyserver;

import com.ruty.rutyserver.entity.Member;
import com.ruty.rutyserver.entity.Routine;
import com.ruty.rutyserver.entity.e.Categories;
import com.ruty.rutyserver.entity.e.Week;
import com.ruty.rutyserver.repository.MemberRepository;
import com.ruty.rutyserver.repository.RoutineHistoryRepository;
import com.ruty.rutyserver.repository.RoutineRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class RutyServerApplicationTests {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job yesterdayJob;

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private RoutineHistoryRepository routineHistoryRepository;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    @Transactional  // 트랜잭션 내에서 한 번에 처리
    public void setup() {
        // 테스트 실행 전에 1000개 데이터 생성
        System.out.println("[Setup] 1000개의 Routine 데이터 생성 시작...");

        List<Routine> routines = new ArrayList<>();
        Member testMember = memberRepository.findById(1L).orElseThrow();  // 존재하는 멤버 사용

        for (int i = 0; i < 1000; i++) {
            Routine routine = Routine.builder()
                    .member(testMember)  // 루틴의 소유자
                    .title("Test Routine " + i)
                    .description("Test Description " + i)
                    .startDate(LocalDate.now().minusDays(10))  // 10일 전 시작
                    .endDate(LocalDate.now().plusDays(10))  // 10일 후 종료
                    .category(Categories.HOUSE)
                    .isDone(false)
                    .weeks(List.of(Week.MON, Week.TUE))  // 월요일 포함
                    .build();
            routines.add(routine);
        }
        routineRepository.saveAll(routines);  // 1000개 루틴 한 번에 저장
        System.out.println("[Setup] 1000개의 Routine 데이터 생성 완료!");
    }

    @Test
    public void testBatchExecutionTime() throws Exception {

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("date", String.valueOf(System.currentTimeMillis()))
                .toJobParameters();

        // 배치 실행
        JobExecution jobExecution = jobLauncher.run(yesterdayJob, jobParameters);

        // 배치 실행 결과 검증
        assertThat(jobExecution.getStatus().isUnsuccessful()).isFalse();
    }

    @AfterEach
    @Transactional  // 트랜잭션 내에서 한 번에 처리
    public void cleanup() {
        System.out.println("[Cleanup] 테스트 데이터 삭제 중...");
        routineHistoryRepository.deleteAll();
        routineRepository.deleteAll();
        System.out.println("[Cleanup] 모든 테스트 데이터 삭제 완료!");
    }
}
