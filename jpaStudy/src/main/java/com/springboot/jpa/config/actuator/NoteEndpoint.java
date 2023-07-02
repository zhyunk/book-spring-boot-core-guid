package com.springboot.jpa.config.actuator;

import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "note") // 어노테이션 사용으로 , 액추에이터에 엔드포인트로 자동 등록됨. id 값은 경로가 된다.
public class NoteEndpoint {
    private Map<String, Object> noteContent = new HashMap<>();

    @ReadOperation
    public Map<String, Object> getNote() {
        return noteContent;
    }

    @WriteOperation
    public Map<String, Object> writeNote(String key, Object value) {
        noteContent.put(key, value);
        return noteContent;
    }

    @DeleteOperation
    public Map<String, Object> deleteNote(String key) {
        noteContent.remove(key);
        return noteContent;
    }
}
