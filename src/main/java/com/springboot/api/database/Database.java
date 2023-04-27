package com.springboot.api.database;

import com.springboot.api.model.Student;
import com.springboot.api.repositories.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Database {
    private static final Logger logger = LoggerFactory.getLogger(Database.class);
    @Bean
    CommandLineRunner initDatabase(StudentRepository studentRepository) {
        // logger
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Student studentA = new Student("Nguyễn Đình Tài", "Bắc Ninh", "XXX");
                Student studentB = new Student("Đỗ Phương Thảo", "Hưng Yên", "XXX");

                logger.info("insert data: " + studentRepository.save(studentA));
                logger.info("insert data: " + studentRepository.save(studentB));
            }
        };
    }
}
