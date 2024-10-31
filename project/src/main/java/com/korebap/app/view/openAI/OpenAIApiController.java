package com.korebap.app.view.openAI;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.korebap.app.biz.openAI.SimSimeDTO;

@RestController
public class OpenAIApiController {

	private final String API_KEY = "API-KEY";
	private final String API_URL = "https://api.openai.com/v1/chat/completions";

	@RequestMapping(value="/goOpenAI.do", method=RequestMethod.POST)
	public @ResponseBody String goOpenAI(@RequestBody SimSimeDTO message, JSONParser parser) {
		System.out.println("OpenAIApiController.java goOpenAI POST 요청 시작");
		//String message = obj.get("message");
		//System.out.println(msg.getMessage());
//		try {
//			JSONObject jsonObject = (JSONObject) parser.parse(message);
//			message = (String) jsonObject.get("message");
//		} catch (ParseException e) {
//			e.printStackTrace();
//			System.out.println("파싱 오류");
//		}
		
		System.out.println(message.getMessage());
		HttpClient client = HttpClient.newHttpClient();

		// 요청 본문 생성
		String requestBody = String.format("{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"user\",\"content\":\"%s\"}],\"temperature\":0.8}", message.getMessage());

		// HTTP 요청 생성
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(API_URL))
				.header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + API_KEY)
				.POST(HttpRequest.BodyPublishers.ofString(requestBody))
				.build();	

		// API 호출
		HttpResponse<String> response = null;
		try {
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("OpenAIApiController.java goOpenAI() 응답"+response.body());
		JSONObject jsonObject;
		String responseMessage = "";
		String content = "";
		try {
			jsonObject = (JSONObject) parser.parse(response.body());
			JSONArray choices = (JSONArray) jsonObject.get("choices");
			JSONObject returnMessage = (JSONObject) ((JSONObject) choices.get(0)).get("message");
			content = (String) returnMessage.get("content");
			responseMessage = "{\"content\":\"" + content + "\"}";
		} catch (ParseException e) {
			System.out.println("파싱 오류");
			e.printStackTrace();
		}
		

		// 응답 반환
		System.out.println(content);
		System.out.println(responseMessage);
		System.out.println("OpenAIApiController.java goOpenAI POST 요청 끝");
		return content;
	}
}
