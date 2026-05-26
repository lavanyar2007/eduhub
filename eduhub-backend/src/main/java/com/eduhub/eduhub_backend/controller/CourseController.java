package com.eduhub.eduhub_backend.controller;

import com.eduhub.eduhub_backend.component.CourseService;
import com.eduhub.eduhub_backend.component.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CourseController {
    @Autowired     //using dependency injection
    CourseService courseService;
    @Autowired  //using DI (IOC)
    DepartmentService departmentService;

//    <-----using constructor----->
//    public CourseController(CourseService courseService,DepartmentService departmentService){
//        this.courseService =courseService;
//        this.departmentService=departmentService;
//    }
    @GetMapping("get-course")
    public String getCourse(){
        return courseService.getCourse();
    }

    @GetMapping("get-dept")
    public String getDept(){
        return departmentService.getDepartment();
    }
}
