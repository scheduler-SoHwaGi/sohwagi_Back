package org.project.sohwagi.infra.llm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.project.sohwagi.common.exception.CustomException;
import org.project.sohwagi.common.exception.ErrorCode;
import org.project.sohwagi.infra.llm.LlmResult.ExtractedScheduleInformation;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.retry.NonTransientAiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OpenAiClient implements LlmClient {

  private final ChatClient chatClient;
  private final ObjectMapper objectMapper;
  private final PromptTemplate promptTemplate;

  public OpenAiClient(
      ChatClient.Builder chatClientBuilder,
      ObjectMapper objectMapper,
      @Value("classpath:/prompts/add-schedule-prompt.txt") Resource promptResource) {
    this.chatClient = chatClientBuilder.build();
    this.objectMapper = objectMapper;
    this.promptTemplate = new PromptTemplate(promptResource);
  }


  @Override
  public ExtractedScheduleInformation extractScheduleInformation(String input) {
    try {
      Prompt prompt = promptTemplate.create(
          Map.of("now", LocalDateTime.now(ZoneId.of("Asia/Seoul"))));
      String rawJsonString = chatClient
          .prompt(prompt)
          .user(input)
          .call()
          .content();

      log.info("Result : {}", rawJsonString);

      String jsonString = rawJsonString
          .replace("```json", "")
          .replace("```", "")
          .trim();

      return objectMapper.readValue(jsonString, ExtractedScheduleInformation.class);
    } catch (NonTransientAiException e) {
      throw new CustomException(ErrorCode.INTERNAL_LLM_SERVER_ERROR);
    } catch (JsonProcessingException e) {
      throw new CustomException(ErrorCode.LLM_RESULT_PARSING_ERROR);
    }
  }
}
