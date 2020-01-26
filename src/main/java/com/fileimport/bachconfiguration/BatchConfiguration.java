package com.fileimport.bachconfiguration;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fileimport.dto.Payload;
import lombok.SneakyThrows;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.net.MalformedURLException;
import java.util.Date;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

//    @Value("${thread.count}")
//    private Integer threadCount;

    @Value("${productJob.path}")
    private String path;

    @SneakyThrows
    @Bean(name = "productJob")
    @Autowired
    public Job productJob(JsonItemReader reader) {
        return jobBuilderFactory.get("productJob")
                .incrementer(new RunIdIncrementer())
                .flow(productStep(reader))
                .end()
                .build();
    }

    @Bean
    @StepScope
    public JsonItemReader<Payload> jsonItemReader(@Value("#{jobParameters[INPUT_FILE_PATH]}") String file) {
//        ObjectMapper mapper = new ObjectMapper();
//
//        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

        JacksonJsonObjectReader reader = new JacksonJsonObjectReader(Payload.class);
//        reader.setMapper(mapper);

        return new JsonItemReaderBuilder()
                .jsonObjectReader(reader)
                .resource(new PathResource(path + file))
                //.resource(new PathResource("/home/paulo/Documents/massa/data_1.json"))
                .name("productRead")
                .build();


    }

    @Bean
    public ProductProcessor processor() {
        return new ProductProcessor();
    }

    @Bean
    public ProductWrite write() {
        return new ProductWrite();
    }

    @Bean
    public Step productStep(ItemReader reader) throws MalformedURLException {
        return stepBuilderFactory
                .get("productStep")
                .listener(productStepListener())
                .chunk(100)
                .reader(reader)
                .processor(processor())
                .writer(write())
                .taskExecutor(taskExecutor())
                .build();
    }
    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor taskExecutor = new SimpleAsyncTaskExecutor();
        taskExecutor.setConcurrencyLimit(1);
        return taskExecutor;
    }


    @Bean
    public StepExecutionListener productStepListener() {
        return new StepExecutionListener() {

            @Override
            public void beforeStep(StepExecution stepExecution) {
                stepExecution.getExecutionContext().put("start",
                        new Date().getTime());
                System.out.println("Step name:" + stepExecution.getStepName()
                        + " Started");
            }

            @Override
            public ExitStatus afterStep(StepExecution stepExecution) {
                long elapsed = new Date().getTime()
                        - stepExecution.getExecutionContext().getLong("start");
                System.out.println("Step name:" + stepExecution.getStepName()
                        + " Ended. Running time is " + elapsed + " milliseconds.");
                System.out.println("Read Count:" + stepExecution.getReadCount() +
                        " Write Count:" + stepExecution.getWriteCount());
                return ExitStatus.COMPLETED;
            }
        };
    }
}
