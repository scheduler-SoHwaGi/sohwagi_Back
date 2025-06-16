package org.project.sohwagi.infra.llm;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OpenAiClient implements LlmClient {

  private final ChatClient chatClient;
  private final ObjectMapper objectMapper;

  public OpenAiClient(ChatClient.Builder chatClientBuilder, ObjectMapper objectMapper) {
    this.chatClient = chatClientBuilder.build();
    this.objectMapper = objectMapper;
  }


  @Override
  public LlmResult.Extract_Schedule_Information extractScheduleInformation(String input) {
    String systemMessage =
        "너는 한 문장에서 일정 관련 정보를 추출하는 역할이야. "
            + "prompt 문장을 일정으로 등록하려는데 JSON 형태로 일정 제목, 일정 날짜로 분류해줘. 해당 값이 없으면 null 표시해줘."
            + "일정 날짜는 꼭 월, 일, 요일, 시각 나타내야하는데 값이 없으면 계산해서 알려줘."
            + "아래 양식 꼭 지켜줘. 현재 날짜는 "
            + LocalDateTime.now()
            + "이야. title, date 는 String 타입이고 date 예시: 2025년 4월 7일 월요일 오전 9시 00분";

    String rawJsonString = chatClient
        .prompt()
        .system(systemMessage)
        .user(input)
        .call()
        .content();

    log.info("Result : {}", rawJsonString);

    String jsonString = rawJsonString
        .replace("```json", "")
        .replace("```", "")
        .trim();

    return objectMapper.convertValue(jsonString, LlmResult.Extract_Schedule_Information.class);
  }
}
