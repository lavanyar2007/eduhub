package com.eduhub.eduhub_backend.controller;

import com.eduhub.eduhub_backend.component.User;
import com.eduhub.eduhub_backend.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/user")  //this remove ambiguity
public class UserController {

    static List<User>userList= new ArrayList<>();
    static {
        userList.add(new User(101,"Abi","1306"));
        userList.add(new User(102,"Bright","1205"));
        userList.add(new User(103,"Lavi","0907"));
        userList.add(new User(104,"Jeni","0506"));
        userList.add(new User(105,"Karthi","0507"));
    }
//    http://localhost:8080/user/users
    @GetMapping("users")
    public ResponseEntity<List<User>>getusers(){
        return ResponseEntity.ok(userList);
    }
//    http://localhost:8080/user/103
    @GetMapping("{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") String id){
        if(id.matches(".*[^a-zA-Z0-9].*")){
            throw new IllegalArgumentException(" This UserId having special character");
        }
        int userId=Integer.parseInt(id);
        return userList.stream().filter(u->u.getUserId()==userId).findFirst()
                .map(ResponseEntity::ok).orElseThrow(()-> new ResourceNotFoundException("User","UserId",String.valueOf(userId)));
    }

//    http://localhost:8080/user/uquery?id=101
    @GetMapping("uquery")
    public ResponseEntity<User> resquestUser(@RequestParam String id){
        if(id.matches(".*[^a-zA-Z0-9].*")){
            throw new IllegalArgumentException(" This UserId having special character");
        }
        int userId=Integer.parseInt(id);
        return userList.stream().filter(u->u.getUserId()==userId).findFirst()
                .map(ResponseEntity::ok).orElseThrow(()-> new ResourceNotFoundException("User","UserId",String.valueOf(userId)));
    }
    @PostMapping("create")
    public ResponseEntity<User> createUser(@RequestBody User user){
        userList.add(user);
        return ResponseEntity.ok(user);
    }

    @PutMapping("update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") int userId , @RequestBody User updateUser){
        User user = userList.stream().filter(u->u.getUserId()==userId).findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("User","UserId",String.valueOf(userId)));
        user.setPassword(updateUser.getPassword());
        return ResponseEntity.ok(user);

    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") int userId){
        User user = userList.stream().filter(u->u.getUserId()==userId).findFirst()
                .orElseThrow(()->new ResourceNotFoundException("User","UserId",String.valueOf(userId)));
        userList.remove(user);
        return ResponseEntity.accepted().body("User removed successfully");
    }


}
