package individual.blog.users.service;

import individual.blog.domain.entity.Account;
import individual.blog.domain.entity.Blog;
import individual.blog.domain.entity.Friend;
import individual.blog.domain.enums.FriendStatus;
import individual.blog.domain.repository.AccountRepository;
import individual.blog.domain.repository.FriendRepository;
import individual.blog.reponse.ResponseDto;
import individual.blog.users.dto.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseDto<?> friendGet(UserDetails userDetails){
        try{
            Account account = accountRepository.findByEmail(userDetails.getUsername());
            if(account == null){
                return ResponseDto.setFailed("001", "없는 유저 입니다. 다시 로그인 해주시기 바랍니다.");
            }

            List<Friend> friendsList = friendRepository.findByAccount_IdOrFriend_IdAndStatus(account.getId(), account.getId(), FriendStatus.ACCEPTED);
            List<FriendGetDto> friendGetDtoList = new ArrayList<>();
            if(!friendsList.isEmpty()){
                for(Friend friend : friendsList){
                    FriendGetDto friendGetDto = new FriendGetDto();

                    if(friend.getFriend().getId() == account.getId() && friend.getStatus().equals(FriendStatus.ACCEPTED)){
                        friendGetDto.setId(friend.getAccount().getId());
                        friendGetDto.setName(friend.getAccount().getName());
                    }else{
                        if(friend.getStatus().equals(FriendStatus.ACCEPTED)){
                            friendGetDto.setId(friend.getFriend().getId());
                            friendGetDto.setName(friend.getFriend().getName());
                        }

                    }
                    friendGetDtoList.add(friendGetDto);
                }
            }

            return ResponseDto.setSuccess("200", "친구 목록 조회 성공", friendGetDtoList);
        }catch (Exception e){
            return ResponseDto.setFailed("002", "오류가 발생했습니다.");
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

    //추천 친구 목록
    @Transactional
    public ResponseDto<?> search(UserDetails userDetails){
        try{
            Account accountId = accountRepository.findByEmail(userDetails.getUsername());
            List<Account> accountList = accountRepository.findAllBy();
            List<FriendListGetDto> friendListGetDtoList = new ArrayList<>();
            if(!accountList.isEmpty()){
                for(Account account : accountList){
                    if(account.getId() == accountId.getId()){
                        continue;
                    }
                    Friend friend = friendRepository.findByAccount_IdAndFriend_Id(accountId.getId(), account.getId());
                    Friend friend2 = friendRepository.findByAccount_IdAndFriend_Id(account.getId(), accountId.getId());

                    if ((friend != null && friend.getStatus().equals(FriendStatus.ACCEPTED)) ||
                            (friend2 != null && friend2.getStatus().equals(FriendStatus.ACCEPTED))) {
                        continue;
                    }
                    FriendListGetDto friendListGetDto = new FriendListGetDto();
                    friendListGetDto.setId(account.getId());
                    friendListGetDto.setEmail(account.getEmail());
                    friendListGetDto.setName(account.getName());
                    if(friend!=null){
                        if(friend.getStatus().equals(FriendStatus.PENDING)){
                            friendListGetDto.setStatus("PENDING");
                        }
                    }else if(friend2 !=null){
                        if(friend2.getStatus().equals(FriendStatus.PENDING)){
                            friendListGetDto.setStatus("PENDING");
                        }
                    }
                    friendListGetDtoList.add(friendListGetDto);
                }
            }
            return ResponseDto.setSuccess("200", "조회 성공", friendListGetDtoList);
        }catch (Exception e) {
            return ResponseDto.setFailed("F001", "조회 실패");
        }
    }

    //친구 요청 보내기
    @Transactional
    public ResponseDto<?> friendAdd(Long id, UserDetails userDetails){
        try{
            Account account = accountRepository.findByEmail(userDetails.getUsername());

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
            profileDto.setImgUrl(account.getProfileImg());
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

    //요청 받은 친구 목록
    @Transactional
    public ResponseDto<?> friendRequest(UserDetails userDetails){
        try{
            Account account = accountRepository.findByEmail(userDetails.getUsername());
            if(account==null){
                return ResponseDto.setFailed("001", "사용자가 없습니다. 다시 로그인 바랍니다.");
            }
            List<Friend> friendList = friendRepository.findByFriend_Id(account.getId());

            if(friendList.isEmpty()){
                return ResponseDto.setFailed("002", "친구 요청 없음");
            }
            List<FriendRequestDto> friendRequestDtos = new ArrayList<>();
            for(Friend friend : friendList) {
                if(friend.getStatus().equals(FriendStatus.PENDING)){
                    FriendRequestDto friendRequestDto = new FriendRequestDto();
                    friendRequestDto.setAccountId(friend.getFriend().getId());
                    friendRequestDto.setFriendId(friend.getAccount().getId());
                    friendRequestDto.setAccountName(friend.getFriend().getName());
                    friendRequestDto.setFriendName(friend.getAccount().getName());
                    friendRequestDtos.add(friendRequestDto);
                }
            }
            return ResponseDto.setSuccess("200", "친구 요청 데이터", friendRequestDtos);
        }catch (Exception e){
            return ResponseDto.setFailed("003", "알 수 없는 오류 발생");
        }
    }

    //친구 요청 수락
    @Transactional
    public ResponseDto<?> friendAccept(Long id, UserDetails userDetails){
      try{
          Account account = accountRepository.findByEmail(userDetails.getUsername());
          if(account==null){
              return ResponseDto.setFailed("001", "사용자가 없습니다. 다시 로그인 바랍니다.");
          }

          Friend friend = friendRepository.findByAccount_IdAndFriend_Id(id, account.getId());
          if(friend==null){
              return ResponseDto.setFailed("002", "친구 요청이 오류 났습니다.");
          }
          friend.setStatus(FriendStatus.ACCEPTED);

          friendRepository.save(friend);
          return ResponseDto.setSuccess("200", "친구 요청 수락 완료",null);
      }catch (Exception e){
          return ResponseDto.setFailed("003", "알 수 없는 오류 발생");
      }

    }

    @Transactional
    public ResponseDto<?> friendDelete(Long id, UserDetails userDetails){
        try{
            Account account = accountRepository.findByEmail(userDetails.getUsername());
            if(account==null){
                return ResponseDto.setFailed("001", "사용자가 없습니다. 다시 로그인 바랍니다.");
            }

            Friend friend = friendRepository.findByAccount_IdAndFriend_Id(id, account.getId());
            Friend friend2 = friendRepository.findByAccount_IdAndFriend_Id(account.getId(), id);

            if(friend!=null && friend.getStatus().equals(FriendStatus.ACCEPTED)){
                friendRepository.delete(friend);
                return ResponseDto.setSuccess("200", "친구 삭제 성공");
            }else if(friend2!=null && friend2.getStatus().equals(FriendStatus.ACCEPTED)){
                friendRepository.delete(friend2);
                return ResponseDto.setSuccess("200", "친구 삭제 성공");
            }
            return ResponseDto.setFailed("002", "친구 삭제 오류 발생했습니다.");
        }catch (Exception e){
            return ResponseDto.setFailed("003", "알 수 없는 오류 발생");
        }
    }
}
