package individual.blog.admin.service;

import individual.blog.admin.dto.*;
import individual.blog.domain.entity.Account;
import individual.blog.domain.entity.Role;
import individual.blog.domain.repository.AccountRepository;
import individual.blog.domain.repository.RoleRepository;
import individual.blog.reponse.ResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AccountRepository accountRepository;

    private final RoleRepository roleRepository;

    @Transactional
    public ResponseDto<?> userInfoList(){
        try{
            List<Account> accountList = accountRepository.findAllBy();
            List<UserInfoDto> userInfoDtoList = new ArrayList<>();

            if(!accountList.isEmpty()){
                for(Account account : accountList){
                    UserInfoDto userInfoDto = new UserInfoDto();
                    userInfoDto.setId(account.getId());
                    userInfoDto.setEmail(account.getEmail());
                    userInfoDto.setName(account.getName());
                    userInfoDto.setCreateAt(account.getCreateAt());
                    if(!account.getUserRoles().isEmpty()){
                        List<String> userRoleDtoList = new ArrayList<>();
                        for(Role role : account.getUserRoles()){
                            userRoleDtoList.add(role.getRoleName());
                        }
                        userInfoDto.setRole(userRoleDtoList);
                    }
                    userInfoDtoList.add(userInfoDto);
                }
            }
            return ResponseDto.setSuccess("A200", "유저 List 조회 성공", userInfoDtoList);
        }catch (Exception e){
            return ResponseDto.setFailed("A001", "유저 List 조회 실패");
        }
    }

    @Transactional
    public ResponseDto<?> userInfoDetail(Long id){
        try{
            Optional<Account> accountOptional = accountRepository.findById(id);
            if(accountOptional.isEmpty()){
                return ResponseDto.setFailed("A000", "유저 정보가 없습니다.");
            }
            Account account = accountOptional.get();
            UserInfoDetailDto userInfoDetailDto = new UserInfoDetailDto();
            userInfoDetailDto.setEmail(account.getEmail());
            userInfoDetailDto.setName(account.getName());
            userInfoDetailDto.setCreateAt(account.getCreateAt());
            if(!account.getUserRoles().isEmpty()){
                List<String> roleList = new ArrayList<>();
                for(Role roles : account.getUserRoles()){
                    roleList.add(roles.getRoleName());
                }
                userInfoDetailDto.setRoles(roleList);
            }
            return ResponseDto.setSuccess("A200", "유저 정보 조회 성공", userInfoDetailDto);
        }catch (Exception e){
            return ResponseDto.setFailed("A001", "유저 정보 조회 실패");
        }
    }

    @Transactional
    public ResponseDto<?> userInfoUpdate(UserInfoUpdateDto userInfoUpdateDto){
        try{
            Optional<Account> accountOptional = accountRepository.findById(userInfoUpdateDto.getId());
            Account accountEmail = accountRepository.findByEmail(userInfoUpdateDto.getEmail());
            if(accountOptional.isEmpty()){
                return ResponseDto.setFailed("A001", "유저 정보가 없습니다.");
            }

            Account account = accountOptional.get();
            if(accountEmail!=null){
                if(!account.getEmail().equals(accountEmail.getEmail())){
                    return ResponseDto.setFailed("A000", "이미 존재하는 이메일 입니다.");
                }
            }

            account.setEmail(userInfoUpdateDto.getEmail());
            account.setName(userInfoUpdateDto.getName());

            List<String> roleList = userInfoUpdateDto.getRoles();
            Set<Role> roleSet = new HashSet<>();
            if(!roleList.isEmpty()){
                for(String roleGet : roleList){
                    Role role = roleRepository.findByRoleName(roleGet);
                    if(role==null){
                        return ResponseDto.setFailed("R000", "없는 권한 입니다.");
                    }
                    roleSet.add(role);
                }
                account.setUserRoles(roleSet);
            }
            accountRepository.save(account);
            return ResponseDto.setSuccess("A200", "유저 정보 수정 완료", null);
        }catch (Exception e){
            return ResponseDto.setFailed("A002", "알 수 없는 이유로 수정 실패 했습니다. 관리자에게 문의 바랍니다.");
        }

    }

    @Transactional
    public ResponseDto<?> roleGet(){
        try{
            List<Role> RoleList = roleRepository.findAllBy();
            if(RoleList.isEmpty()){
                return ResponseDto.setFailed("R001", "권한이 비었습니다.");
            }
            List<RoleGetDto> roleGetDtos = new ArrayList<>();
            for(Role role : RoleList){
                RoleGetDto rgd = new RoleGetDto();
                rgd.setId(role.getId());
                rgd.setRole(role.getRoleName());
                roleGetDtos.add(rgd);
            }
            return ResponseDto.setSuccess("R200", "권한 조회 성공", roleGetDtos);
        }catch (Exception e){
            return ResponseDto.setFailed("R001", "권한 조회 실패");
        }
    }

    @Transactional
    public ResponseDto<?> roleCreate(RoleCreateDto roleCreateDto){
        try{

            Role role = roleRepository.findByRoleName(roleCreateDto.getRole());
            if(role!=null){
                return ResponseDto.setFailed("R001", "이미 있는 권한 입니다.");
            }
            Role createRole = new Role();
            createRole.setRoleName(roleCreateDto.getRole());
            createRole.setRoleDesc(roleCreateDto.getRoleDesc());
            createRole.setIsExpression("N");
            roleRepository.save(createRole);
            return ResponseDto.setSuccess("R200", "권한 생성 성공");
        }catch (Exception e){
            return ResponseDto.setFailed("R001", "권한 생성 실패");
        }
    }

    @Transactional
    public ResponseDto<?> roleDelete(Long id){
        try{
            Optional<Role> roleOptional = roleRepository.findById(id);
            if(roleOptional.isEmpty()){
                return ResponseDto.setFailed("R001", "권한을 찾을 수 없습니다.");
            }
            Role role = roleOptional.get();
            roleRepository.delete(role);
            return ResponseDto.setSuccess("R200", "권한 삭제 성공");
        }catch (Exception e){
            return ResponseDto.setFailed("R001", "권한 삭제 실패");
        }
    }

}
