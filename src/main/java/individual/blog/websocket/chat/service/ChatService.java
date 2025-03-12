package individual.blog.websocket.chat.service;

import individual.blog.domain.entity.Account;
import individual.blog.domain.entity.ChatRoom;
import individual.blog.domain.entity.ChatRoomUser;
import individual.blog.domain.repository.AccountRepository;
import individual.blog.domain.repository.ChatRoomRepository;
import individual.blog.domain.repository.ChatRoomUserRepository;
import individual.blog.reponse.ResponseDto;
import individual.blog.websocket.chat.dto.ChatRoomListDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ChatService {

    private final AccountRepository accountRepository;

    private final ChatRoomUserRepository chatRoomUserRepository;

    private final ChatRoomRepository chatRoomRepository;


    @Transactional
    public ResponseDto<?> chatRoomList(UserDetails userDetails){
        try{
            Account account = accountRepository.findByEmail(userDetails.getUsername());
            if(account == null){
                return ResponseDto.setFailed("001","유저가 없습니다. 다시 로그인 해주시기 바랍니다.");
            }

            List<ChatRoomUser> chatRoomUserList = chatRoomUserRepository.findByAccount_Id(account.getId());
            List<ChatRoomListDto> chatRoomListDtoList = new ArrayList<>();
            if(!chatRoomUserList.isEmpty()){
                for(ChatRoomUser chatRoomUser : chatRoomUserList){
                    Optional<ChatRoom> chatRoom = chatRoomRepository.findById(chatRoomUser.getChatRoom().getId());
                    if(!chatRoom.isEmpty()){
                        ChatRoomListDto chatRoomListDto = new ChatRoomListDto();
                        chatRoomListDto.setId(chatRoom.get().getId());
                        chatRoomListDto.setName(chatRoom.get().getName());
                        chatRoomListDto.setCreateAt(chatRoom.get().getLocalDate());
                        chatRoomListDtoList.add(chatRoomListDto);
                    }
                }
                return ResponseDto.setSuccess("200", "채팅방 목록 조회 성공", chatRoomListDtoList);
            }
            return ResponseDto.setSuccess("201","채팅방 목록조회 성공(채팅방 없음)",null);
        }catch (Exception e){
            return ResponseDto.setFailed("002", "알 수 없는 오류 발생");
        }
    }
}
