package com.eac.kalah.model.entity.enums;

/**
 * Created by eduardo on 19/10/16.
 */
public enum PitEnum {
    ONE(0),
    TWO(1),
    THREE(2),
    FOUR(3),
    FIVE(4),
    SIX(5);

    private int position;

    PitEnum(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public static PitEnum get(int position) {
        PitEnum[] types = PitEnum.values();

        for (PitEnum type : types) {
            if (type.getPosition() == position) {
                return type;
            }
        }

        return null;
    }
}