package individual.blog.users.service;

import individual.blog.domain.entity.Account;
import individual.blog.domain.entity.Blog;
import individual.blog.domain.entity.Friend;
import individual.blog.domain.enums.FriendStatus;
import individual.blog.domain.repository.AccountRepository;
import individual.blog.domain.repository.FriendRepository;
import individual.blog.reponse.ResponseDto;
import individual.blog.users.dto.AccountGetDto;
import individual.blog.users.dto.ProfileBlogDto;
import individual.blog.users.dto.ProfileDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class FriendService {

    private final AccountRepository accountRepository;

    private final FriendRepository friendRepository;

    @Transactional
    public ResponseDto<?> friendGet(User user){
        try{
            Account account = accountRepository.findByEmail(user.getUsername());
            if(account == null){
                return ResponseDto.setFailed("F001", "없는 유저 입니다. 다시 로그인 해주시기 바랍니다.");
            }

            List<Friend> friendsList = friendRepository.findByAccount_Id(account.getId());
            return ResponseDto.setSuccess("F200", "친구 목록 조회 성공", friendsList);
        }catch (Exception e){
            return ResponseDto.setFailed("F002", "오류가 발생했습니다.");
        }
    }

    @Transactional
    public ResponseDto<?> searchFriend(String search){
        try{
            List<Account> accountList = accountRepository.findByEmailContainingOrNameContaining(search, search);
            List<AccountGetDto> accountGetDtoList = new ArrayList<>();
            if(!accountList.isEmpty()){
                for(Account act : accountList){
                    AccountGetDto accountGetDto = new AccountGetDto();
                    accountGetDto.setId(act.getId());
                    accountGetDto.setName(act.getName());
                    accountGetDto.setEmail(act.getEmail());
                    accountGetDtoList.add(accountGetDto);
                }
            }

            return ResponseDto.setSuccess("200", "조회 성공", accountGetDtoList);
        }catch (Exception e){
            return ResponseDto.setFailed("F001", "조회 실패");
        }
    }

    @Transactional
    public ResponseDto<?> search(User user){
        try{
            Account accountId = accountRepository.findByEmail(user.getUsername());
            List<Account> accountList = accountRepository.findAllBy();
            List<AccountGetDto> accountGetDtoList = new ArrayList<>();
            if(!accountList.isEmpty()){
                for(Account account : accountList){
                    if(account.getId() == accountId.getId()){
                        continue;
                    }
                    AccountGetDto accountGetDto = new AccountGetDto();
                    accountGetDto.setId(account.getId());
                    accountGetDto.setEmail(account.getEmail());
                    accountGetDto.setName(account.getName());
                    accountGetDtoList.add(accountGetDto);
                }
            }
            return ResponseDto.setSuccess("200", "조회 성공", accountGetDtoList);
        }catch (Exception e) {
            return ResponseDto.setFailed("F001", "조회 실패");
        }
    }

    @Transactional
    public ResponseDto<?> friendAdd(Long id, User user){
        log.info("들어온 친추 "+id+" "+user.getUsername());
        try{
            Account account = accountRepository.findByEmail(user.getUsername());

            if(account==null){
                return ResponseDto.setFailed("001", "사용자가 없습니다. 다시 로그인 해주시기 바랍니다.");
            }

            Optional<Account> friendOptional = accountRepository.findById(id);

            if(friendOptional.isEmpty()){
                return ResponseDto.setFailed("002", "유저가 없습니다. 다시 시도 해주시기 바랍니다.");
            }

            Account friendId = friendOptional.get();
            Friend friend = new Friend();
            friend.setAccount(account);
            friend.setFriend(friendId);
            friend.setStatus(FriendStatus.PENDING);
            friendRepository.save(friend);
            return ResponseDto.setSuccess("200", "요청 보내기 성공", null);
        }catch (Exception e){
            return ResponseDto.setFailed("003", "요청 보내기 실패");
        }
    }

    @Transactional
    public ResponseDto<?> friendProfile(Long id){
        try{
            Optional<Account> accountOptional = accountRepository.findById(id);
            if(accountOptional.isEmpty()){
                return ResponseDto.setFailed("001","사용자가 없습니다.");
            }
            Account account = accountOptional.get();

            ProfileDto profileDto = new ProfileDto();
            profileDto.setEmail(account.getEmail());
            profileDto.setName(account.getName());
            profileDto.setCreateAt(account.getCreateAt());
            if(!account.getBlogs().isEmpty()){
                List<ProfileBlogDto> profileBlogDtoList = new ArrayList<>();
                for(Blog blog : account.getBlogs()){
                    ProfileBlogDto profileBlogDto = new ProfileBlogDto();
                    profileBlogDto.setId(blog.getId());
                    profileBlogDto.setTitle(blog.getTitle());
                    profileBlogDto.setCreateAt(blog.getCreateAt());
                    profileBlogDtoList.add(profileBlogDto);
                }
                profileDto.setBlogData(profileBlogDtoList);
            }
            return ResponseDto.setSuccess("200", "친구 프로필 조회 성공", profileDto);
        }catch (Exception e){
            return ResponseDto.setFailed("002","알 수 없는 오류가 발생하였습니다.");
        }
    }
}
