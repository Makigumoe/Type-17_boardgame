package CodeGaming.Utils;

import CodeGaming.Card;
import CodeGaming.CardClazz.Buff;
import CodeGaming.Character;

public class SkillUtils {
    /**
     * 所有技能的效果都是一个函数，写在这里。
     */

    CardUtils cu = new CardUtils();

    public void require_fxxk() {
        System.out.println("good");
    }

    public boolean require_技能需求样例(Character caster, Character target) {
        /**
         * 技能的自定义参数，一般都有的：max_range,min_range
         * 对于AOE类型的能力，为了简化起见，都被设定为指向一个目标。如果波及到其他目标，那就用随机选取，不再精确指向多个目标。
         */
        boolean require_check = true;
        //
        int max_range = 1;
        int min_range = 0;
        int current_range = (int) Math.abs(caster.position + target.position);
        int move_require = 0;   //移动到合适位置，所需的额外移动力
        if (current_range > max_range) move_require += max_range - current_range;
        else if (current_range < min_range) move_require += current_range - min_range;
        caster.move_require = move_require;
        //
        int extra_cards_require = 0;  //一些强力技能可能要求额外消耗手牌
        //开始检查消耗是否充足
        if (caster.plan_cost.size() < extra_cards_require) require_check = false;
        int k = 0;
        for (Card card : caster.hand_card)
            if (card.card_type == "普通") k += 1;
        if (k < move_require) require_check = false;

        return require_check;
    }

    public void affect_技能生效样例(Character caster, Character target) throws NoSuchMethodException, ClassNotFoundException {
        /**
         * 分成若干步，进行生效。
         * 第一步，移动必须的距离
         */
        boolean success = true; //标记技能是不是成功起效
        for (int i = 0; i < caster.move_require; i++)
            if (caster.act_able())
                caster.move();
        if (caster.act_able()) {
            //把额外消耗，和这张牌都移入弃牌堆
            //
            //开始造成伤害
            if (cu.d20() > 12) {
                //造成伤害
                target.hp_current -= 1;
                //设置状态
                Buff burn = new Buff("燃烧");
                cu.player_add_buff(target, burn);
            }
        }
    }

    //示例：火球术，造成1点钝击伤害，1点燃烧伤害
    public boolean require_火球术(Character caster, Character target) {
        /**
         * 距离为0-3，额外消耗1任意手牌
         */
        boolean require_check = true;
        //
        int max_range = 3;
        int min_range = 0;
        int current_range = (int) Math.abs(caster.position + target.position);
        int move_require = 0;   //移动到合适位置，所需的额外移动力
        if (current_range > max_range) move_require += max_range - current_range;
        else if (current_range < min_range) move_require += current_range - min_range;
        caster.move_require = move_require;
        //
        int extra_cards_require = 1;  //一些强力技能可能要求额外消耗手牌
        //开始检查消耗是否充足
        if (caster.plan_cost.size() < extra_cards_require) require_check = false;   //额外手牌消耗
        int k = 0;
        for (Card card : caster.hand_card)
            if (card.card_type == "普通") k += 1; //移动带来的消耗
        if (k < move_require) require_check = false;

        return require_check;
    }

    public void affect_火球术(Character caster, Character target) throws NoSuchMethodException, ClassNotFoundException {
        /**
         * 分成若干步，进行生效。
         * 第一步，移动必须的距离
         */

        for (int i = 0; i < caster.move_require; i++)
            if (caster.act_able())
                caster.move();
        if (caster.act_able()) {
            System.out.println("kaishi");
            //把额外消耗，和这张牌都移入弃牌堆
            //
            //开始造成伤害
            if (cu.d20() > 12) {
                //造成伤害
                target.receive_damage(1, "燃烧");
                //设置状态
                Buff burn = new Buff("燃烧");
                cu.player_add_buff(target, burn);
            }
            if (cu.d20() > 6) {
                target.receive_damage(1, "钝击");
            }
        }
    }

}
