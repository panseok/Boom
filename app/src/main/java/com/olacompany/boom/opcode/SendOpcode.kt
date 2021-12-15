package com.olacompany.boom.opcode

enum class SendOpcode {
    SERVER_PONG,
    SERVER_NOTICE_REQUEST,
    CONNECT_LOGIN_SERVER,
    USER_LOGIN_STATUS,
    USER_LOGIN,
    USER_REGISTER,
    USER_CHECK_NAME,
    USER_CONNECT_CHANNEL,
    USER_PROFILE_IMAGE_CHANGE,
    USER_MEMO_CHANGE,
    USER_OPEN_PROFILE,
    LOBBY_CREATE_ROOM,
    LOBBY_ROOM_GETLIST,
    ROOM_JOIN_REQUEST,
    ROOM_JOIN_USER,
    ROOM_SEND_CHAT,
    ROOM_EXIT_USER,
    ROOM_BAN_USER,
    ROOM_OPEN_INVITE_FRIEND,
    ROOM_INVITE_FRIEND,
    FRIEND_JOIN_ACTIVITY,
    FRIEND_SEARCH,
    FRIEND_REMOVE,
    FRIEND_REQUEST,
    FRIEND_REQUEST_ADD,
    FRIEND_REQUEST_ACCEPT,
    FRIEND_REQUEST_REFUSE,
    FRIEND_OPEN_REQUEST,
    GAME_START_REQUEST,
    GAME_BOOM_SPIN
}