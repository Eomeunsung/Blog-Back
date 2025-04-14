package individual.blog.users.service;

import individual.blog.domain.entity.Account;
import individual.blog.domain.entity.Blog;
import individual.blog.domain.entity.Role;
import individual.blog.domain.repository.AccountRepository;
import individual.blog.domain.repository.BlogRepository;
import individual.blog.domain.repository.RoleRepository;
import individual.blog.reponse.ResponseDto;
import individual.blog.security.jwt.JwtUtil;
import individual.blog.security.jwt.repository.RefreshTokenRepository;
import individual.blog.users.dto.*;
import individual.blog.util.CustomFileUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final AccountRepository accountRepository;

    private final UserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;

    private final RoleRepository roleRepository;

    private final BlogRepository blogRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    private final CustomFileUtil customFileUtil;

//    @Value("PORT_URL")
//    private final String port;

    @Transactional
    public ResponseDto<?> create(SignUpDto signUpDto){
        try{
            Account account = new Account();

            if(accountRepository.findByEmail(signUpDto.getEmail())!=null){
                return ResponseDto.setFailed("500", "이미 존재하는 이메일 입니다.");
            }
            account.setEmail(signUpDto.getEmail());
            account.setName(signUpDto.getName());
            account.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
            account.setProfileImg("default.png");
            account.setCreateAt(LocalDate.now());
            Set<Role> role = roleRepository.findByRoleNameIn(signUpDto.getRoles());
            account.setUserRoles(role);
            accountRepository.save(account);
            return ResponseDto.setSuccess("200", "회원가입이 완료되었습니다.");
        }catch (Exception e){
//            throw new RuntimeException("회원 가입 실패");
            return ResponseDto.setFailed("500", "다시 회원가입 진행해 주시기 바랍니다.");
        }
    }

    @Transactional
    public ResponseDto<?> signIn(SignInDto signInDto){
        try{
            String email = signInDto.getEmail();
            String password = signInDto.getPassword();

            Account account = accountRepository.findByEmail(email);
            if(account==null){
                return ResponseDto.setFailed("403","존재하지 않는 회원입니다.");
            }
            if(!passwordEncoder.matches(password, account.getPassword())){
                return ResponseDto.setFailed("403","존재하지 않는 회원입니다.");
            }
            final UserDetails userDetails = userDetailsService.loadUserByUsername(account.getEmail());
            final String accessToken = jwtUtil.createToken(userDetails);
            final String refreshToken = jwtUtil.refreshCreateToken(userDetails);
            InfoDto infoDto = new InfoDto();
            log.info("로그인 이메일 "+account.getName());
            infoDto.setName(account.getName());
            infoDto.setEmail(email);
            infoDto.setAccessToken(accessToken);
            infoDto.setRefreshToken(refreshToken);
            refreshTokenRepository.save(email, refreshToken);
            return ResponseDto.setSuccess("200","로그인 성공", infoDto);
        }catch (Exception e){
            return ResponseDto.setFailed("403", "로그인 다시 진행해 주시기 바랍니다.");
        }
    }

    @Transactional
    public ResponseDto<?> myProfile(UserDetails userDetails){
        try{
            String email = userDetails.getUsername();
            Account account = accountRepository.findByEmail(email);
            Long id = account.getId();
            if(account == null ){
                return ResponseDto.setFailed("500", "로그인 다시 진행해 주십시오.");
            }
            ProfileDto profileDto = new ProfileDto();
            profileDto.setName(account.getName());
            profileDto.setEmail(account.getEmail());
            profileDto.setCreateAt(account.getCreateAt());
//            profileDto.setImgUrl(port+"/"+account.getProfileImg());
            profileDto.setImgUrl(account.getProfileImg());
            List<Blog> blogList = blogRepository.findByAccount_Id(id);
            List<ProfileBlogDto> profileBlogDtoList = new ArrayList<>();
            if(!blogList.isEmpty()){
                for(Blog blog : blogList){
                    ProfileBlogDto profileBlogDto = new ProfileBlogDto();
                    profileBlogDto.setId(blog.getId());
                    profileBlogDto.setTitle(blog.getTitle());
                    profileBlogDto.setCreateAt(blog.getCreateAt());
                    profileBlogDtoList.add(profileBlogDto);
                }
                profileDto.setBlogData(profileBlogDtoList);
            }

            return ResponseDto.setSuccess("200", "블로그 데이터", profileDto);
        }catch (Exception e){
            return ResponseDto.setFailed("500", "블로그 정보 불러오기 실패");
        }
    }

    @Transactional
    public ResponseDto<?> myProfileUpdate(NameDto nameDto, UserDetails userDetails){
        try{
            Account account = accountRepository.findByEmail(userDetails.getUsername());
            if(account ==null){
                return ResponseDto.setFailed("U000", "다시 로그인 해주시기 바랍니다.");
            }
            if(nameDto.getImgUrl()!=null){
                if(!account.getProfileImg().equals("default.png")){
                    String deleteImg = account.getProfileImg();
                    customFileUtil.deleteProfileImg(deleteImg);
                }
            }
            account.setName(nameDto.getName());
            account.setProfileImg(nameDto.getImgUrl());
            accountRepository.save(account);
            return ResponseDto.setSuccess("U200", "닉네임 수정 완료");
        }catch (Exception e){
            return ResponseDto.setFailed("U001", "오류가 발생하였습니다. 다시 로그인해 주시기 바랍니다.");
        }
    }
}
