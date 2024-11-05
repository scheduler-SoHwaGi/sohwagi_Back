package org.project.sohwagi.schedule.adapter.out.gpt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.project.sohwagi.common.OutboundAdapter;
import org.project.sohwagi.schedule.util.gpt.GPTRequest;
import org.project.sohwagi.schedule.util.gpt.GPTResponse;
import org.project.sohwagi.schedule.util.gpt.Message;
import org.project.sohwagi.schedule.adapter.in.web.request.ScheduleRequest;
import org.project.sohwagi.schedule.application.port.out.CallGptPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

@OutboundAdapter
@RequiredArgsConstructor
public class GptAdapter implements CallGptPort {

	@Value("${openai.model:gpt-4o}")
	private String model;

	@Value("${openai.api.url:https://api.openai.com/v1/chat/completions}")
	private String apiUrl;

	private final RestTemplate restTemplate;

	private final ObjectMapper objectMapper;

	@Override
	public ScheduleRequest callGptForTextSchedule(String prompt) throws JsonProcessingException {
		String SYSTEM_MESSAGE =
			"너는 한 문장에서 일정 관련 정보를 추출하는 역할이야. "
				+ "prompt 문장을 일정으로 등록하려는데 JSON 형태로 일정 제목, 일정 날짜로 분류해줘. 해당 값이 없으면 null 표시해줘."
				+ "아래 양식 꼭 지켜줘. 현재 날짜는 "
				+ LocalDateTime.now()
				+ "이야. title, date 는 String 타입이고 date 예시: 10월 7일 월요일";

		List<Message> messages = new ArrayList<>();
		messages.add(new Message("system", SYSTEM_MESSAGE));
		messages.add(new Message("user", prompt));

		GPTRequest request = new GPTRequest(
			model, 0, 256, 1, 0, 0, messages);

		GPTResponse gptResponse = restTemplate.postForObject(
			apiUrl
			, request
			, GPTResponse.class
		);

		String rawJsonString = gptResponse.getChoices().get(0).getMessage().getContent();

		String jsonString = rawJsonString
			.replace("```json", "")
			.replace("```", "")
			.trim();

		return objectMapper.readValue(jsonString,
			ScheduleRequest.class);
	}
}
