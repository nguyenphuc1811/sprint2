package com.example.be.controller.auth_controller;


import com.example.be.dto.request.ChangePasswordForm;
import com.example.be.dto.request.SignInForm;
import com.example.be.dto.request.SignUpForm;
import com.example.be.dto.request.UpdateUserForm;
import com.example.be.dto.response.JwtResponse;
import com.example.be.dto.response.ResponseMessage;
import com.example.be.model.user.Role;
import com.example.be.model.user.User;
import com.example.be.sercurity.jwt.JwtProvider;
import com.example.be.sercurity.userPrincipcal.UserPrinciple;
import com.example.be.service.IRoleService;
import com.example.be.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@RequestMapping("/api/auth")
@RestController
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private IRoleService iRoleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm, BindingResult bindingResult) {
        new SignUpForm().validate(iUserService.findAll(), signUpForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }
        if (iUserService.existsByUsername(signUpForm.getUsername())) {
            return new ResponseEntity<>(new ResponseMessage("Tên đăng " + signUpForm.getUsername() + " nhập đã được sử dụng, vui lòng chọn tên khác"), HttpStatus.BAD_REQUEST);
        }
        if (iUserService.existsByEmail(signUpForm.getEmail())) {
            return new ResponseEntity<>(new ResponseMessage("Email " + signUpForm.getEmail() + " đã được sử dụng"), HttpStatus.BAD_REQUEST);
        }
        User user = new User(signUpForm.getUsername(), passwordEncoder.encode(signUpForm.getPassword()), signUpForm.getName(), signUpForm.getEmail());
        Set<String> strRoles = signUpForm.getRoles();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role roleAdmin = iRoleService.roleAdmin().orElseThrow(() -> new RuntimeException("Role not found 1"));
                    roles.add(roleAdmin);
                    break;
                case "employee":
                    Role roleEmployee = iRoleService.roleEmployee().orElseThrow(() -> new RuntimeException("Role not found 2"));
                    roles.add(roleEmployee);
                    break;
                default:
                    Role roleCustomer = iRoleService.roleCustomer().orElseThrow(() -> new RuntimeException("Role not found 3"));
                    roles.add(roleCustomer);
            }
        });
        user.setRoles(roles);
        iUserService.save(user);
        return new ResponseEntity<>(new ResponseMessage("Đăng kí thành công"), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SignInForm signInForm) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.createToken(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(token, userPrinciple.getName(), userPrinciple.getId(), userPrinciple.getUsername(), userPrinciple.getEmail(), userPrinciple.getPassword(), userPrinciple.getAvatar()
                , userPrinciple.getPhoneNumber(),
                userPrinciple.getAddress(),
                userPrinciple.getAge(),
                userPrinciple.getGender(),
                userPrinciple.getDateOfBirth()
                , userPrinciple.getAuthorities()));
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordForm changePasswordForm, BindingResult bindingResult) {
        if (!Objects.equals(changePasswordForm.getNewPassword(), changePasswordForm.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "confirmPassword", "Mật khẩu xác nhận không trùng với mật khẩu mới");
            return new  ResponseEntity<>(new ResponseMessage("Mật khẩu xác nhận " +
                    changePasswordForm.getConfirmPassword() +" không trùng với mật khẩu mới " + changePasswordForm.getNewPassword()),HttpStatus.BAD_REQUEST);
        }
        User user = iUserService.findByUsername(changePasswordForm.getUsername()).orElse(null);
        assert user != null;
        if (!passwordEncoder.matches(changePasswordForm.getPassword(), user.getPassword())) {
            bindingResult.rejectValue("password", "password", "Bạn đã nhập sai mật khẩu cũ");
        }
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldErrors(), HttpStatus.BAD_REQUEST);
        }
        if (passwordEncoder.matches(changePasswordForm.getPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordForm.getNewPassword()));
            iUserService.changePassword(user.getPassword(), user.getUsername());
            return new ResponseEntity<>(new ResponseMessage("Cập nhật mật khẩu thành công"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseMessage("Thay đổi mật khẩu thất bại"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);

            return new ResponseEntity<>(new ResponseMessage("Đăng xuất thành công"), HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(new ResponseMessage("Đăng xuất thất bại"), HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserForm updateUserForm, BindingResult bindingResult) {
        new UpdateUserForm().validate(iUserService.findAll(), updateUserForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getFieldErrors(), HttpStatus.NOT_ACCEPTABLE);
        }
        iUserService.updateUser(updateUserForm);
        return new ResponseEntity<>(new ResponseMessage("Chỉnh sửa thông tin thành công"), HttpStatus.ACCEPTED);
    }

    @GetMapping("/customer")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(iUserService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<?> profile(@PathVariable("id") int id) {
        return new ResponseEntity<>(iUserService.findById(id), HttpStatus.ACCEPTED);
    }
}
