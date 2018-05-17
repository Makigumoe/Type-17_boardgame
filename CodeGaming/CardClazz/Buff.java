package CodeGaming.CardClazz;

import CodeGaming.Card;

public class Buff extends Card {
    public Buff() {
        this.card_type = "状态";
    }

    public Buff(String name) {
        this.card_type = "状态";
        this.name = name;
        if (name == "燃烧") this.info = "身上着火了";
    }
}
