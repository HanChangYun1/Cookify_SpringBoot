package Cook.Cookify_SpringBoot.domain.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

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

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("Socket 연결");
        sessions.add(session);
        log.info(sendPushUsername(session));
        String senderId = sendPushUsername(session);
        userSessionMap.put(senderId, session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("session = " + sendPushUsername(session));
        String msg = message.getPayload();
        log.info("msg = " + msg);

        if (!StringUtils.isEmpty(msg)) {
            String[] strs = msg.split(",");

            if (strs != null && strs.length == 5) {
                String pushCategory = strs[0];			//카테고리 분류
                String replyWriter = strs[1];			//팔로우, 좋아요 보낸 유저
                String sendedPushUser = strs[2];		//푸시 알림 받을 유저

                WebSocketSession sendedPushSession = userSessionMap.get(sendedPushUser);	//로그인상태일때 알람 보냄

                if ("like".equals(pushCategory) && sendedPushSession != null) {
                    String boardId = strs[3];				//게시글번호
                    String title = strs[4];					//게시글제목
                    TextMessage textMsg = new TextMessage(replyWriter + "님이 회원님의 게시물을 좋아합니다: " +
                            "<a href='/porfolDetail/" + boardId + "' style=\"color:black\"><strong>" + title + "</strong></a>");
                    sendedPushSession.sendMessage(textMsg);

                } else if ("follow".equals(pushCategory) && sendedPushSession != null) {
                    TextMessage textMsg = new TextMessage(replyWriter + "님이 회원님을 팔로우하기 시작했습니다.");
                    sendedPushSession.sendMessage(textMsg);

                } else if ("directMessage".equals(pushCategory) && sendedPushSession != null) {
                    String receiverId = strs[5];
                    WebSocketSession receiverSession = userSessionMap.get(receiverId);
                    if (receiverSession != null) {
                        TextMessage textMsg = new TextMessage(replyWriter + "님으로부터 새로운 메시지: " + msg);
                        receiverSession.sendMessage(textMsg);
                    }
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("Socket 연결 해제");
        sessions.remove(session);
        userSessionMap.remove(sendPushUsername(session), session);
    }

    //알람을 보내는 유저(댓글작성, 좋아요 누르는 유저)
    private String sendPushUsername(WebSocketSession session) {
        String loginUsername;

        if (session.getPrincipal() == null) {
            loginUsername = null;
        } else {
            loginUsername = session.getPrincipal().getName();
        }
        return loginUsername;
    }
}
