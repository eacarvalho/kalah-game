package com.eac.kalah.model.entity.enums;

/**
 * Created by eduardo on 19/10/16.
 */
public enum PitEnum {
    PIT_1(1),
    PIT_2(2),
    PIT_3(3),
    PIT_4(4),
    PIT_5(5),
    PIT_6(6);

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
