package state.model.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StateDto {
    private int HP;
    private int maxHP;
    private int MP;
    private int maxMP;
    private int INT;
    private int STR;
    private int money;


    public StateDto() {}

    public StateDto(int HP, int maxHP, int MP, int maxMP, int INT, int STR, int money) {
        this.HP = HP;
        this.maxHP = maxHP;
        this.MP = MP;
        this.maxMP = maxMP;
        this.INT = INT;
        this.STR = STR;
        this.money = money;
    }
}