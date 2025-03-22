package individual.blog.websocket.chat.service;

import individual.blog.domain.entity.Account;
import individual.blog.domain.entity.ChatMessage;
import individual.blog.domain.entity.ChatRoom;
import individual.blog.domain.repository.AccountRepository;
import individual.blog.domain.repository.ChatMessageRepository;
import individual.blog.domain.repository.ChatRoomRepository;
import individual.blog.websocket.chat.dto.ChatMessageDto;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Slf4j
public class ChatWriteService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    private final AccountRepository accountRepository;

    public ChatWriteService(ChatMessageRepository chatMessageRepository, ChatRoomRepository chatRoomRepository, AccountRepository accountRepository) {
        this.chatMessageRepository = chatMessageRepository;
        this.chatRoomRepository = chatRoomRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public boolean chatWriteService(ChatMessageDto chatMessageDto, Long id){
        try{
            Account account = accountRepository.findByEmail(chatMessageDto.getEmail());
            if(account==null){
                log.info("사용자 정보가 없다");
                return false;
            }

            Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(id);

            if(chatRoomOptional.isEmpty()){
                log.info("채팅방이 없음");
                return false;
            }
            ChatRoom chatRoom = chatRoomOptional.get();
            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setContent(chatMessageDto.getContent());
            chatMessage.setCreateAt(LocalDate.now());
            chatMessage.setAccount(account);
            chatMessage.setChatRoom(chatRoom);
            chatMessageRepository.save(chatMessage);
            return true;

        }catch (Exception e){
            log.info("알 수 없는 오류");
            return false;
        }
    }
}
