package com.springboot.rest.service;

import dto.MemberDto;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class RestTemplateService {
    private final String BASE_PATH = "http://localhost:8888";

    /**
     * GET 매서드 요청
     */
    public String getName() {
        URI uri = UriComponentsBuilder
                .fromUriString(BASE_PATH)
                .path("/api/v1/crud-api")
                .encode()
                .build().toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

        return responseEntity.getBody();
    }

    public String getNameWithPathVariable() {
        URI uri = UriComponentsBuilder
                .fromUriString(BASE_PATH)
                .path("/api/v1/crud-api/{name}")
                .encode()
                .build()
                .expand("Flature")
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

        return responseEntity.getBody();
    }

    public String getNameWithParameter() {
        URI uri = UriComponentsBuilder
                .fromUriString(BASE_PATH)
                .path("/api/v1/crud-api/param")
                .queryParam("name", "Flature")
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

        return responseEntity.getBody();
    }


    /**
     * POST 매서드 요청
     */
    public ResponseEntity<MemberDto> postWithParamAndBody() {
        URI uri = UriComponentsBuilder
                .fromUriString(BASE_PATH)
                .path("/api/v1/crud-api")
                .queryParam("name", "Flature")
                .queryParam("email", "flature@wikibooks.co.kr")
                .queryParam("organization", "Wikibookx")
                .encode()
                .build()
                .toUri();

        return new RestTemplate().postForEntity(
                uri,
                MemberDto.builder()
                        .name("falture!!")
                        .email("flature@gmail.com")
                        .organization("around hub studio")
                        .build(),
                MemberDto.class
        );
    }

    public ResponseEntity<MemberDto> postWithHeader() {
        URI uri = UriComponentsBuilder
                .fromUriString(BASE_PATH)
                .path("/api/v1/crud-api/add-header")
                .encode()
                .build()
                .toUri();

        RequestEntity<MemberDto> requestEntity = RequestEntity
                .post(uri)
                .header("my-header", "wikibooks api")
                .body(
                    MemberDto.builder()
                            .name("falture!!")
                            .email("flature@gmail.com")
                            .organization("around hub studio")
                            .build()
                );

        return new RestTemplate().exchange(
                requestEntity,
                MemberDto.class
        );
    }
}
