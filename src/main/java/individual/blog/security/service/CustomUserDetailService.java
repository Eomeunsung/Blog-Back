package individual.blog.security.service;

import individual.blog.domain.entity.Account;
import individual.blog.domain.repository.AccountRepository;
import individual.blog.security.custom.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CustomUserDetailService implements UserDetailsService {


    private final AccountRepository accountRepository;

    @Autowired
    public CustomUserDetailService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email);

        if(account == null){
            throw new UsernameNotFoundException("User not found");
        }

//        List<SimpleGrantedAuthority> authorities = account.getUserRoles().stream()
//                .map(role -> new SimpleGrantedAuthority(role.getRoleName())) // 수정된 부분
//                .collect(Collectors.toList());
//        log.info("커스텀 유저 권한 "+authorities);

//        return new User(
//                account.getEmail(),
//                account.getPassword(),
//                authorities
//        );
        return new CustomUserDetails(account);
    }
}
