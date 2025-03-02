package individual.blog.users.service;

import individual.blog.domain.repository.BlogRepository;
import individual.blog.domain.entity.Account;
import individual.blog.domain.entity.Blog;
import individual.blog.domain.entity.Role;
import individual.blog.reponse.ResponseDto;
import individual.blog.domain.repository.RoleRepository;
import individual.blog.security.jwt.JwtUtil;
import individual.blog.users.dto.InfoDto;
import individual.blog.users.dto.SignInDto;
import individual.blog.users.dto.SignUpDto;
import individual.blog.domain.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Log4j2
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final AccountRepository accountRepository;

    private final UserDetailsService userDetailsService;

    private final JwtUtil jwtUtil;

    private final RoleRepository roleRepository;

    private final BlogRepository blogRepository;
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
            final String jwt = jwtUtil.createToken(userDetails);
            InfoDto infoDto = new InfoDto();
            infoDto.setName(account.getName());
            infoDto.setJwt(jwt);
            return ResponseDto.setSuccess("200","로그인 성공", infoDto);
        }catch (Exception e){
            return ResponseDto.setFailed("403", "로그인 다시 진행해 주시기 바랍니다.");
        }
    }

    @Transactional
    public ResponseDto<?> myProfile(User user){
        try{
            String email = user.getUsername();
            Account account = accountRepository.findByEmail(email);
            Long id = account.getId();
            List<Blog> blogList =blogRepository.findByAccount_Id(id);
            List<Blog> blogLists = new ArrayList<>();
            if(!blogList.isEmpty()){
                for(Blog blogs : blogList){
                    Blog blog = new Blog();
                    blog.setId(blogs.getId());
                    blog.setTitle(blogs.getTitle());
                    blog.setContent(blogs.getContent());
                    blog.setCreateAt(blogs.getCreateAt());
                    blogLists.add(blog);
                }
            }
            return ResponseDto.setSuccess("200", "블로그 데이터", blogLists);
        }catch (Exception e){
            return ResponseDto.setFailed("500", "블로그 정보 불러오기 실패");
        }
    }
}
