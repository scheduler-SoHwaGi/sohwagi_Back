package org.project.sohwagi.gpt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.sohwagi.gpt.dto.GPTRequest;
import org.project.sohwagi.gpt.dto.GPTResponse;
import org.project.sohwagi.gpt.dto.Message;
import org.project.sohwagi.schedule.dto.ScheduleRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GPTServiceImpl implements GPTService {

	@Value("${openai.model:gpt-4o}")
	private String model;

	@Value("${openai.api.url:https://api.openai.com/v1/chat/completions}")
	private String apiUrl;

	// RestTemplate 객체를 주입받음
	private final RestTemplate restTemplate;

	// ObjectMapper 객체를 주입받음
	private final ObjectMapper objectMapper;

	@Override
	public ScheduleRequest gptCall(String prompt) throws JsonProcessingException {
		String SYSTEM_MESSAGE =
			"너는 한 문장에서 일정 관련 정보를 추출하는 역할이야. "
				+ "prompt 문장을 일정으로 등록하려는데 JSON 형태로 일정 제목, 일정 날짜로 분류해줘. 해당 값이 없으면 null 표시해줘."
				+ "아래 양식 꼭 지켜줘. 현재 날짜는 "
				+ LocalDateTime.now()
				+ "이야. title, date 는 String 타입이고 date 예시: 10월 7일 (월)";

		// 메시지 리스트를 생성하고 시스템 메시지와 사용자 메시지를 추가
		List<Message> messages = new ArrayList<>();
		messages.add(new Message("system", SYSTEM_MESSAGE));
		messages.add(new Message("user", prompt));

		// GPT 요청 객체를 생성, 모델명, 파라미터 등 설정
		GPTRequest request = new GPTRequest(
			model, 0, 256, 1, 0, 0, messages);

		// OpenAI API에 POST 요청을 보내고 응답을 GPTResponse 객체로 받음
		GPTResponse gptResponse = restTemplate.postForObject(
			apiUrl
			, request
			, GPTResponse.class
		);

		// GPT 응답에서 첫 번째 선택지의 메시지 내용을 JSON 문자열로 가져옴
		String rawJsonString = gptResponse.getChoices().get(0).getMessage().getContent();

		String jsonString = rawJsonString
			.replace("```json", "")  // 시작 태그 제거
			.replace("```", "")      // 끝 태그 제거
			.trim();                 // 앞뒤 공백 제거

		// JSON 문자열을 GPTCallTodoRequest 객체로 변환하여 반환
		return objectMapper.readValue(jsonString,
			ScheduleRequest.class);
	}
}
