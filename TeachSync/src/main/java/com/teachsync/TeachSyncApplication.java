package com.teachsync;

import com.teachsync.dtos.course.CourseReadDTO;
import com.teachsync.dtos.user.UserReadDTO;
import com.teachsync.entities.Course;
import com.teachsync.entities.User;
import com.teachsync.utils.MiscUtil;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TeachSyncApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(TeachSyncApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(TeachSyncApplication.class, args);
    }

    @Bean
    public MiscUtil miscUtil() {
        return new MiscUtil();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        modelMapper.typeMap(Course.class, CourseReadDTO.class)
                .addMappings(mapper -> {
                    mapper.skip(CourseReadDTO::setCurrentPrice);
                    mapper.skip(CourseReadDTO::setMaterialList);
                    mapper.skip(CourseReadDTO::setClassroomList); });

        modelMapper.typeMap(User.class, UserReadDTO.class)
                .addMappings(mapper -> {
                    mapper.skip(UserReadDTO::setRequestMadeList);
                    mapper.skip(UserReadDTO::setChildList); });

        return modelMapper;
    }
}