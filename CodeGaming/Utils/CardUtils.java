package CodeGaming.Utils;

import CodeGaming.CardClazz.Buff;
import CodeGaming.CardClazz.Ground;
import CodeGaming.Character;

import java.util.ArrayList;

public class CardUtils {
    public int d20() {
        return (int) Math.floor(Math.random() * 20 + 1);
    }

    //离开某个地形的消耗，不一定要有
    public void leave_沼泽(Character character) {
        /**
         * 如果导致了行动不能的状态，那就直接塞进player.buffs
         */
        character.speed_adjust -= 10;
    }

    public void buff_enter_ground(Character player, Buff buff, Ground ground) {
        /**
         * 直接暴力遍历所有情况。
         * 如果导致了行动不能的状态，那就直接塞进player.buffs
         */
        if (buff.name == "燃烧") {
            if (ground.name == "水")
                player.buffs.remove(buff);
        }
    }

    public void buff_enter_buff(Character player, Buff new_buff) {
        ArrayList<Buff> prepare_to_remove = new ArrayList<Buff>();
        Boolean add_check = true;
        for (int i = 0; i < player.buffs.size(); i++) {
            Buff orgin_buff = player.buffs.get(i);
            if (new_buff.name == "燃烧") {
                if (orgin_buff.name == "湿润") {
                    prepare_to_remove.add(orgin_buff);  //因起效，湿润状态消失了
                    add_check = false;
                }
            }
        }
        //
        if (add_check)
            player.buffs.add(new_buff);
        player.buffs.removeAll(prepare_to_remove);
    }

    public void player_add_buff(Character player, Buff buff) {
        /**
         *
         * 遍历player所具有的buff，以及现在所在的地形，来决定发生什么效果
         */
        buff_enter_ground(player, buff, player.current_ground);   //根据地形产生效果
        buff_enter_buff(player, buff);  //根据已有状态，产生效果
    }
}
