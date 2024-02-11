package com.example.globalStudents.domain.chat.controller;


import com.example.globalStudents.domain.chat.dto.*;
import com.example.globalStudents.domain.chat.service.ChatService;
import com.example.globalStudents.global.apiPayload.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chats")
public class ChatController {
    private static ChatService chatService;

    @GetMapping("/")
    public ApiResponse<ChatListResponse> getChatList(@RequestParam(name = "user_id") String user_id){
        ChatListResponse data = chatService.getChatList(user_id);
        return ApiResponse.onSuccess(data);
    }

    @PostMapping("/create")
    public ApiResponse<ChatCreateResponse> createChatRoom(@RequestBody ChatCreateRequest chatCreateRequest){
        ChatCreateResponse data = chatService.createChatRoom(chatCreateRequest);
        return ApiResponse.onCreated(data);
    }

    @GetMapping("/{chat_room_id}")
    public ApiResponse<ChatReadResponse> getChatRoom(@RequestParam(name = "user_id") String user_id, @PathVariable String chat_room_id){
        ChatReadResponse data = chatService.getChatRoom(user_id, chat_room_id);
        return ApiResponse.onSuccess(data);
    }

    @PostMapping("/{chat_room_id}/send")
    public ApiResponse<ChatSendResponse> sendMessage(@PathVariable String chat_room_id, @RequestBody ChatSendRequest chatSendRequest){
        ChatSendResponse data = chatService.sendMessage(chat_room_id, chatSendRequest);
        return ApiResponse.onSuccess(data);
    }

}
