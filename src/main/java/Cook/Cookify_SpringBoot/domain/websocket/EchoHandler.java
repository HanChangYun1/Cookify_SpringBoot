package Cook.Cookify_SpringBoot.domain.websocket;

import Cook.Cookify_SpringBoot.domain.member.entity.GoogleMember;
import Cook.Cookify_SpringBoot.domain.member.exception.MemberException;
import Cook.Cookify_SpringBoot.domain.member.exception.MemberExceptionType;
import Cook.Cookify_SpringBoot.domain.member.repository.GoogleMemberRepository;
import Cook.Cookify_SpringBoot.domain.member.security.SessionMember;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@RequiredArgsConstructor
public class EchoHandler extends TextWebSocketHandler {

    // 전체 로그인 유저
    private List<WebSocketSession> sessions = new ArrayList<>();

    // 1대1 매핑
    private Map<String, WebSocketSession> userSessionMap = new HashMap<>();

    private final GoogleMemberRepository googleMemberRepository;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Socket 연결");
        String senderId = sendPushUsername(session);
        sessions.add(session);
        log.info(senderId);
        userSessionMap.put(senderId, session);
        log.info("userSessionMap:{}", userSessionMap);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("session = " + sendPushUsername(session));
        String msg = message.getPayload();
        log.info("msg = " + msg);

        if (!StringUtils.isEmpty(msg)) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(msg);
            String pushCategory = jsonNode.path("category").asText();
            String replyWriter = jsonNode.path("writer").asText();
            String sendedPushUser = jsonNode.path("receiver").asText();

            log.info("category: {}", pushCategory);
            log.info("writer: {}", replyWriter);
            log.info("user: {}", sendedPushUser);

            WebSocketSession sendedPushSession = userSessionMap.get(sendedPushUser);	//로그인상태일때 알람 보냄
            log.info("sendedPushSession:{}", sendedPushSession);

            if ("like".equals(pushCategory) && sendedPushSession != null) {
                log.info("좋아요들어옴");
                String recipeId = jsonNode.path("recipeId").asText();
                log.info("recipeId:{}", recipeId);
                String title = jsonNode.path("title").asText();
                log.info("title:{}", title);
                TextMessage textMsg = new TextMessage(replyWriter + "님이 회원님의" + title + " 게시물을 좋아합니다: " +
                        "<a href='/recipe/" + recipeId + "' style=\"color:black\"><strong>" + title + "</strong></a>");
                sendedPushSession.sendMessage(textMsg);
                log.info("textMsg:{}", textMsg);

            } else if ("follow".equals(pushCategory) && sendedPushSession != null) {
                log.info("팔로우 들어옴");
                TextMessage textMsg = new TextMessage(replyWriter + "님이 "+ sendedPushUser+"회원님을 팔로우하기 시작했습니다.");
                sendedPushSession.sendMessage(textMsg);

            } else if ("directMessage".equals(pushCategory) && sendedPushSession != null) {
                WebSocketSession receiverSession = userSessionMap.get(sendedPushUser);
                if (receiverSession != null) {
                    TextMessage textMsg = new TextMessage(replyWriter + "님으로부터 새로운 메시지: " + msg);
                    receiverSession.sendMessage(textMsg);
                }
            }

        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("Socket 연결 해제");
        sessions.remove(session);
        String removedUserId = sendPushUsername(session);
        userSessionMap.remove(removedUserId, session);
    }


    //알람을 보내는 유저(댓글작성, 좋아요 누르는 유저)
    private String sendPushUsername(WebSocketSession session) {
        String loginUsername;

        // attributes에서 HTTP 세션을 가져옴
        HttpSession httpSession = (HttpSession) session.getAttributes().get("HTTP_SESSION");
        log.info("httpSession:{}", httpSession);
        // HttpSession에서 유저 정보를 가져옴 (예: email)
        SessionMember sessionUser = (SessionMember) httpSession.getAttribute("user");
        log.info("sessionUser:{}", sessionUser);
        String email = sessionUser.getEmail();
        log.info("email:{}", email);

        if (email== null) {
            loginUsername = null;
        } else {
            GoogleMember member = googleMemberRepository.findByEmail(email).orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_Member));
            loginUsername = member.getName();
        }
        return loginUsername;
    }
}
