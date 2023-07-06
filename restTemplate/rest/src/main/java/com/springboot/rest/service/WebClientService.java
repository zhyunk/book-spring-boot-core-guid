package com.springboot.rest.service;

import dto.MemberDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class WebClientService {
    private final String BASE_PATH = "http://localhost:8888";

    /**
     * GET 메서드 요청 
     */
    public String getName() {
        WebClient webClient = WebClient.builder()
                .baseUrl(BASE_PATH)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return webClient.get()
                .uri("/api/v1/crud-api")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getNameWithPathVariable() {
        WebClient webClient = WebClient.create(BASE_PATH);

        ResponseEntity<String> responseEntity = webClient
                .get() // get method
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/api/v1/crud-api/{name}")
                                .build("flature"))
                .retrieve() // 요청에 대한 응답을 받았을 때 그값을 추출하는 방법 중 하나!
                .toEntity(String.class)
                .block(); // 블로킹 형식으로 동작하게 하는 설정

        return responseEntity.getBody();
    }

    public String getNameWithParameter() {
        WebClient webClient = WebClient.create(BASE_PATH);

        return webClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder.path("/api/v1/crud-api")
                                .queryParam("name", "flature")
                                .build())
                .exchangeToMono(clientResponse -> {
                    // 응답 상태값에 따라 결과를 다르게 설정 가능
                    if (clientResponse.statusCode().equals(HttpStatus.OK)) 
                        return clientResponse.bodyToMono(String.class);
                    else
                        return clientResponse.createException().flatMap(Mono::error);
                })
                .block();
    }

    /**
     * POST 메서드 요청
     */
    public ResponseEntity<MemberDto> postWithParamAndBody() {
        WebClient webClient = WebClient.builder()
                .baseUrl(BASE_PATH)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        MemberDto memberDto = MemberDto.builder()
                .name("zhyun")
                .email("zhyun@wlgus.kim")
                .organization("organization")
                .build();

        return webClient
                .post()
                .uri(uriBuilder ->
                        uriBuilder.path("/api/v1/crud-api")
                                .queryParam("name", "zhyun kim")
                                .queryParam("email", "gimwlgus@kakao.com")
                                .queryParam("organization", "wikibooks")
                                .build())
                .bodyValue(memberDto) // HTTP Body 에 값 추가
                .retrieve()
                .toEntity(MemberDto.class)
                .block();
    }

    public ResponseEntity<MemberDto> postWithHeader() {
        WebClient webClient = WebClient.builder()
                .baseUrl(BASE_PATH)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        MemberDto memberDto = MemberDto.builder()
                .name("zhyun")
                .email("zhyun@wlgus.kim")
                .organization("organization")
                .build();

        return webClient
                .post()
                .uri(uriBuilder ->
                        uriBuilder.path(BASE_PATH).build())
                .bodyValue(memberDto) // HTTP Body 에 값 추가
                .header("my-header", "🐢💨") // 커스텀 Header 값 추가
                .retrieve()
                .toEntity(MemberDto.class)
                .block();

    }
}
