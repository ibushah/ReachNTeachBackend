package com.example.hubProject.Controller;

import com.example.hubProject.Commons.ApiResponse;
import com.example.hubProject.Commons.AuthToken;
import com.example.hubProject.Commons.ResponseDTO;
import com.example.hubProject.Config.JwtTokenUtil;
import com.example.hubProject.DTO.UserEmailDTO;
import com.example.hubProject.DTO.LoginUser;
import com.example.hubProject.DTO.UserDto;
import com.example.hubProject.DTO.UserPasswordDTO;
import com.example.hubProject.Model.User;
import com.example.hubProject.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserServiceImpl userService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ApiResponse<AuthToken> register(@RequestBody LoginUser loginUser) throws AuthenticationException {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));
        final User user = userService.findOne(loginUser.getEmail());
        final String token = jwtTokenUtil.generateToken(user);

        if(user!=null)
        return new ApiResponse<>(200, "success",new AuthToken(token,user.getEmail(),user.getUserType()));
        else
            return new ApiResponse<>(400, "Incorrect details",new AuthToken());

    }


    @PostMapping("/user")
    public ApiResponse<User> saveUser(@RequestBody UserDto user){
        return new ApiResponse<>(HttpStatus.OK.value(), "Your account has been created",userService.save(user));
    }

    @GetMapping("/allusers")
    public List<User> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> forgotPassword(@RequestBody UserEmailDTO userEmailDTO)
    { return   userService.forgotPassword(userEmailDTO); }

//    @PostMapping("/newuser")
//    public ResponseEntity<String> postNewUser(@RequestBody UserEmailDTO userEmailDTO)
//    { return userService.postNewUser(userEmailDTO);}
//

    @DeleteMapping("/deleteuser/{email}")
    public void delete(@PathVariable("email") String email)
    {
         userService.delete(email);
    }

    @GetMapping("/getuser/{email}")
    public User getUser(@PathVariable("email") String email)
    {
       return userService.getUser(email);
    }

    @PutMapping("/updateuser/{email}")
    public ResponseEntity<String> updateUser(@PathVariable("email") String email,@RequestBody UserDto userDto)
    {
        return userService.updateUser(email,userDto);
    }

    @PutMapping("/changepassword/{token}")
    public ResponseEntity<String> changePasswordById(@PathVariable("token") String token,@RequestBody UserPasswordDTO userPasswordDTO)
    {
        return  userService.changePasswordById(token,userPasswordDTO);
    }

    @GetMapping("/checkuser/{email}")
    public User sharedUserCheck(@PathVariable("email") String email)
    {
        return userService.sharedUserCheck(email);
    }


}
