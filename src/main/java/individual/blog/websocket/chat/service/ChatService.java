package individual.blog.websocket.chat.service;

import individual.blog.domain.entity.Account;
import individual.blog.domain.entity.ChatMessage;
import individual.blog.domain.entity.ChatRoom;
import individual.blog.domain.entity.ChatRoomUser;
import individual.blog.domain.enums.ChatType;
import individual.blog.domain.repository.AccountRepository;
import individual.blog.domain.repository.ChatMessageRepository;
import individual.blog.domain.repository.ChatRoomRepository;
import individual.blog.domain.repository.ChatRoomUserRepository;
import individual.blog.reponse.ResponseDto;
import individual.blog.websocket.chat.dto.ChatGetDto;
import individual.blog.websocket.chat.dto.ChatMessageGetDto;
import individual.blog.websocket.chat.dto.ChatRoomListDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

@Service
@AllArgsConstructor
@Slf4j
public class ChatService {

    private final AccountRepository accountRepository;

    private final ChatRoomUserRepository chatRoomUserRepository;

    private final ChatRoomRepository chatRoomRepository;

    private final ChatMessageRepository chatMessageRepository;


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

    @Transactional
    public ResponseDto<?> chatCreate(Long id, UserDetails userDetails){
        try{
            Account account = accountRepository.findByEmail(userDetails.getUsername());
            if(account == null){
                return ResponseDto.setFailed("001", "사용자가 없습니다. 다시 로그인 해주시기 바랍니다.");
            }

            Optional<Account> userOptional = accountRepository.findById(id);
            if(userOptional.isEmpty()){
                return ResponseDto.setFailed("002", "상대방이 없습니다.");
            }
            Account user = userOptional.get();

            // 채팅방 생성
            ChatRoom chatRoom = new ChatRoom();
            chatRoom.setLocalDate(LocalDate.now());
            chatRoom.setName(account.getName() + ", " + user.getName());
            chatRoomRepository.save(chatRoom); // 채팅방을 한 번만 저장

            log.info("id 아이디 " + user.getId() + " " + account.getId());

            // 채팅방 유저 1 (상대방)
            ChatRoomUser chatRoomUser = new ChatRoomUser();
            chatRoomUser.setChatRoom(chatRoom);
            chatRoomUser.setAccount(user);
            chatRoomUser.setType(ChatType.PRIVATE);
            chatRoomUserRepository.save(chatRoomUser);

            // 채팅방 유저 2 (현재 사용자)
            ChatRoomUser chatRoomUser2 = new ChatRoomUser();
            chatRoomUser2.setChatRoom(chatRoom);
            chatRoomUser2.setAccount(account);
            chatRoomUser2.setType(ChatType.PRIVATE);
            chatRoomUserRepository.save(chatRoomUser2);

            return ResponseDto.setSuccess("200", "채팅방 생성 성공",chatRoom.getId());
        } catch (Exception e){
            return ResponseDto.setFailed("002", "알 수 없는 오류 발생");
        }
    }

    //유저 id로 채팅방 찾기
    @Transactional
    public ResponseDto<?> chatPrivateGet(Long accountId, Long accountId2){
        try{
            List<ChatRoomUser> userList1 = chatRoomUserRepository.findByAccountIdAndType(accountId, ChatType.PRIVATE);
            if(userList1.isEmpty()){
                return ResponseDto.setFailed("001", "채팅방 새로 개설해야 합니다.");
            }

            List<ChatRoomUser> userList2 = chatRoomUserRepository.findByAccountIdAndType(accountId2, ChatType.PRIVATE);
            if(userList2.isEmpty()){
                return ResponseDto.setFailed("001", "채팅방 새로 개설해야 합니다.");
            }

            Optional<Long> roomIdOptional = userList1.stream()
                    .flatMap(user1 -> userList2.stream()
                            .filter(user2->user1.getChatRoom().getId().equals(user2.getChatRoom().getId()))
                            .map(user2-> user1.getChatRoom().getId())).findFirst();

            if(roomIdOptional.isEmpty()){
                return ResponseDto.setFailed("001", "채팅방 새로 개설해야 합니다.");
            }
            Long roomId = roomIdOptional.get();

            log.info("찾은 룸 아이디 "+roomId);

            List<ChatMessage> chatMessageList = chatMessageRepository.findByChatRoom_Id(roomId);
            ChatGetDto chatGetDto = new ChatGetDto();
            chatGetDto.setRoomId(roomId);

            if (!chatMessageList.isEmpty()){
                List<ChatMessageGetDto> chatMessageGetDtoList = new ArrayList<>();
                for(ChatMessage chatMessage : chatMessageList){
                    ChatMessageGetDto chatMessageGetDto = new ChatMessageGetDto();
                    chatMessageGetDto.setCreateAt(chatMessage.getCreateAt());
                    chatMessageGetDto.setEmail(chatMessage.getAccount().getEmail());
                    chatMessageGetDto.setContent(chatMessage.getContent());
                    chatMessageGetDto.setName(chatMessage.getAccount().getName());
                    chatMessageGetDto.setId(chatMessage.getId());
                    chatMessageGetDto.setChatRoom(chatMessage.getChatRoom());
                    chatMessageGetDtoList.add(chatMessageGetDto);
                }
                chatGetDto.setChatMessageGetDtoList(chatMessageGetDtoList);
            }

            return ResponseDto.setSuccess("200", "채팅방 가져오기 성공", chatGetDto);
        }catch (Exception e){
            return ResponseDto.setFailed("002", "알 수 없는 오류 발생");
        }
    }

    //방 id로 채팅방 찾기
    @Transactional
    public ResponseDto<?> chatMessageGet(Long id){
        try{
            Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(id);
            if(chatRoomOptional.isEmpty()){
                return ResponseDto.setFailed("001", "채팅방이 없습니다.");
            }
            ChatRoom chatRoom = chatRoomOptional.get();
            List<ChatMessage> chatMessageList = chatMessageRepository.findByChatRoom_Id(chatRoom.getId());
            ChatGetDto chatGetDto = new ChatGetDto();
            chatGetDto.setRoomId(chatRoom.getId());

            if (!chatMessageList.isEmpty()){
                List<ChatMessageGetDto> chatMessageGetDtoList = new ArrayList<>();
                for(ChatMessage chatMessage : chatMessageList){
                    ChatMessageGetDto chatMessageGetDto = new ChatMessageGetDto();
                    chatMessageGetDto.setCreateAt(chatMessage.getCreateAt());
                    chatMessageGetDto.setEmail(chatMessage.getAccount().getEmail());
                    chatMessageGetDto.setContent(chatMessage.getContent());
                    chatMessageGetDto.setName(chatMessage.getAccount().getName());
                    chatMessageGetDto.setId(chatMessage.getId());
                    chatMessageGetDto.setChatRoom(chatMessage.getChatRoom());
                    chatMessageGetDtoList.add(chatMessageGetDto);
                }
                chatGetDto.setChatMessageGetDtoList(chatMessageGetDtoList);
            }
            return ResponseDto.setSuccess("200", "채팅방 가져오기 성공", chatGetDto);
        }catch (Exception e){
            return ResponseDto.setFailed("002", "알 수 없는 오류 발생");
        }
    }


}
