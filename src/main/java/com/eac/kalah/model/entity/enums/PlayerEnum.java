package com.eac.kalah.model.entity.enums;

/**
 * Created by eduardo on 19/10/16.
 */
public enum PlayerEnum {

    ONE("Player 1"),
    TWO("Player 2");

    private String description;

    PlayerEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static PlayerEnum get(String description) {
        PlayerEnum[] types = PlayerEnum.values();

        for (PlayerEnum type : types) {
            if (type.getDescription().equalsIgnoreCase(description)) {
                return type;
            }
        }

        return null;
    }
}