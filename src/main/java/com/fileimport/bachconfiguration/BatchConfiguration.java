package com.fileimport.bachconfiguration;


import com.fileimport.dto.Payload;
import com.fileimport.model.Industry;
import com.fileimport.model.Product;
import com.fileimport.model.ProductOrigin;
import com.fileimport.model.ProductType;
import com.fileimport.repository.IndustryRepository;
import com.fileimport.repository.ProductOriginRepository;
import com.fileimport.repository.ProductRepository;
import com.fileimport.repository.ProductTypeRepository;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;




    @Bean
    public Job productJob(Step productStep) {
        return jobBuilderFactory.get("productJob")
                .incrementer(new RunIdIncrementer())
                .flow(productStep)
                .end()
                .build();
    }

    @Bean
    @StepScope
    public JsonItemReader jsonItemReader()  {


       return new JsonItemReaderBuilder()
                .jsonObjectReader(new JacksonJsonObjectReader(Payload.class))
                .resource(new PathResource("/home/paulo/Documents/massa/teste.json"))
                .name("data")
                .build();


        //return null;
    }

    @Bean
    public ProductProcessor processor() {
        return new ProductProcessor();
    }

    @Bean ProductWrite write(){
        return new ProductWrite();
    }

//    @Bean
//    public RepositoryItemWriter writer() {
//        RepositoryItemWriter write = new RepositoryItemWriter();
//        write.setRepository(repository);
//        write.setMethodName("save");
//
//        return write;
//    }


    @Bean
    public Step productStep() throws MalformedURLException {
        return stepBuilderFactory
                .get("productStep")
                .listener(productStepListener())
                .chunk(10)
                .reader(jsonItemReader())
                .processor(processor())
                .writer(write())
                .build();
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
                        + " Ended. Running time is "+ elapsed +" milliseconds.");
                System.out.println("Read Count:" + stepExecution.getReadCount() +
                        " Write Count:" + stepExecution.getWriteCount());
                return ExitStatus.COMPLETED;
            }
        };
    }
}
