package com.cetide.codeforge.model.enums;




public enum NotifyMessageType {
    /**
     * 系统消息
     */
    SYSTEM_MESSAGE("系统消息","系统消息"),
    /**
     * 通知消息
     */
    ADMIN_MESSAGE("管理员","通知公告");
    private final String sender;

    private final String type;

    NotifyMessageType(String sender, String type) {
        this.sender = sender;
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "NotifyMessageType{" +
                "sender='" + sender + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
