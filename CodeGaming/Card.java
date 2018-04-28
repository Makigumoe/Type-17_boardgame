package CodeGaming;

public class Card {
    public String name = "";
    public String info = "";
    public String card_type = "";

    @Override
    public String toString() {
        return "name:" + this.name + "\ninfo:" + this.info;
    }
}
