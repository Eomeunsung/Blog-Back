package individual.blog.admin.controller;

import individual.blog.admin.dto.RoleCreateDto;
import individual.blog.admin.dto.UserInfoUpdateDto;
import individual.blog.admin.service.AdminService;
import individual.blog.reponse.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/user/info")
    public ResponseEntity<ResponseDto<?>> usersInfoList(){
        ResponseDto responseDto = adminService.userInfoList();
        if(responseDto.getCode().equals("A200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/info/{id}")
    public ResponseEntity<ResponseDto<?>> userInfoDetail(@PathVariable Long id){
        ResponseDto responseDto = adminService.userInfoDetail(id);
        if(responseDto.getCode().equals("A200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/user/info")
    public ResponseEntity<ResponseDto<?>> userInfoDate(@RequestBody UserInfoUpdateDto userInfoUpdateDto){
        ResponseDto responseDto = adminService.userInfoUpdate(userInfoUpdateDto);
        if(responseDto.getCode().equals("A200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("/role")
    public ResponseEntity<ResponseDto<?>> roleGet(){
        ResponseDto responseDto = adminService.roleGet();
        if(responseDto.getCode().equals("R200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/role")
    public ResponseEntity<ResponseDto<?>> roleCreate(@RequestBody RoleCreateDto roleCreateDto){
        ResponseDto responseDto = adminService.roleCreate(roleCreateDto);
        if(responseDto.getCode().equals("R200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/role/{id}")
    public ResponseEntity<ResponseDto<?>> roleDelete(@PathVariable Long id){
        ResponseDto responseDto = adminService.roleDelete(id);
        if(responseDto.getCode().equals("R200")){
            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(responseDto, HttpStatus.BAD_REQUEST);
        }
    }


}
