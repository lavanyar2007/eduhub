package com.eduhub.eduhub_backend.controller;

import com.eduhub.eduhub_backend.component.Course;
import com.eduhub.eduhub_backend.component.CourseService;
import com.eduhub.eduhub_backend.component.DepartmentService;
import com.eduhub.eduhub_backend.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
//@RequestMapping("/course")  //this remove ambiguity
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
    static List<Course>courseList=new ArrayList<>();
    static{
        courseList.add(new Course("C100", "Cloud Computing", 5));
        courseList.add(new Course("C101", "Data Structures", 4));
        courseList.add(new Course("C102", "Java Programming", 3));
        courseList.add(new Course("C103", "Database Management", 4));
        courseList.add(new Course("C104", "Artificial Intelligence", 5));
    }



    @GetMapping("courses")
    public ResponseEntity<List<Course>> getCourses()
    {
        return new ResponseEntity<>(courseList, HttpStatus.OK);

    }

    @GetMapping("/course/{cc}")
    public ResponseEntity<Course> getCourse(@PathVariable("cc") String courseCode
                                                    ) {

        return courseList.stream().filter( c -> c.getCourseCode().equalsIgnoreCase(courseCode))
                .findFirst().map(ResponseEntity::ok).orElseThrow(
                        ()->new ResourceNotFoundException("Course","CourseCode",courseCode));
    }
    @GetMapping("/course/{cc}/{sn}/{c}")
    public ResponseEntity<Course> coursePathVariable(@PathVariable("cc") String courseCode,
                                                     @PathVariable("sn") String subjectName,
                                                     @PathVariable("c") int credits){
        Course course = new Course(courseCode,subjectName,credits);
        return new ResponseEntity<>(course,HttpStatus.OK);
    }

    @GetMapping("cquery")
    public ResponseEntity<Course> courseRequestVariable(@RequestParam String cc,
                                                        @RequestParam String sn,
                                                        @RequestParam int c){
        Course course = new Course(cc,sn,c);
        return  ResponseEntity.ok(course);
    }
    @PostMapping("course/create")
    public ResponseEntity<Course>createCourse(@RequestBody Course course){
        courseList.add(course);
        return ResponseEntity.ok(course);
    }

    @PutMapping("course/update/{cc}")
    public ResponseEntity updateCourse(@PathVariable("cc") String courseCode, @RequestBody Course updatedCourse){
        Course course= courseList.stream().filter( c -> c.getCourseCode().equalsIgnoreCase(courseCode))
                .findFirst().orElseThrow(
                        ()->new ResourceNotFoundException("Course","CourseCode",courseCode));
        course.setCourseCode(updatedCourse.getCourseCode());
        course.setCredits(updatedCourse.getCredits());
        return ResponseEntity.accepted().body(course);
    }

    @DeleteMapping("course/delete/{cc}")
    public ResponseEntity deleteCourse(@PathVariable("cc") String courseCode ){
        Course course= courseList.stream().filter( c -> c.getCourseCode().equalsIgnoreCase(courseCode))
                .findFirst().orElseThrow(
                        ()->new ResourceNotFoundException("Course","CourseCode",courseCode));
        courseList.remove(course);
        return ResponseEntity.accepted().body("course removed successfully");
    }

    @PutMapping("/query/{code}")
    public String queryCourse(@PathVariable String code){
        if(code.startsWith("*")){
            throw new IllegalArgumentException(("It is having a special character"));
        }
        else if(code.startsWith("6")){
            throw new RuntimeException();
        }
        return code;
    }

}
