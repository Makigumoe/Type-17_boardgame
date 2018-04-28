package CodeGaming.Utils;

import CodeGaming.Card;
import CodeGaming.Character;

public class SkillUtils {
    /**
     * 所有技能的效果都是一个函数，写在这里。
     */

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

    public void affect_技能生效样例(Character caster, Character target) {
        /**
         * 分成若干步，进行生效。
         * 第一步，移动必须的距离
         */
        boolean success = true; //标记技能是不是成功起效
        for (int i = 0; i < caster.move_require; i++)
            if (caster.move_able)
                caster.move();
            else
                success = false;
    }


    public void spell_Fireball(Character caster) {
        System.out.println(caster.name + " successfully cast a fireball!");
    }
}
