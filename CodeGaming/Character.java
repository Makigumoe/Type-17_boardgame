package CodeGaming;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import CodeGaming.CardClazz.Buff;
import CodeGaming.CardClazz.Ground;
import CodeGaming.Utils.*;

public class Character {
    public String name = "";
    public String info = "";
    public int hp = 0;
    public int hp_current = 0;    //当前血量
    public int speed = 0;
    public double position = 0.5; //记录角色的位置。初始位置是0.5，所以敌对双方初始位置距离就是1。
    public ArrayList<Card> hand_card = new ArrayList<Card>();
    public ArrayList<Card> deck_card = new ArrayList<Card>();
    public ArrayList<Card> deck_abandon = new ArrayList<Card>();
    public ArrayList<Buff> buffs = new ArrayList<Buff>();
    public BattleField battle_field;
    public Ground current_ground;
    public int speed_adjust;    //调整角色的速度，譬如，吟唱法术，会使出手变慢

    //各抗性
    int res_blunt;

    //作战计划
    public Character plan_target;
    public Card plan_used;
    public ArrayList<Card> plan_cost;
    Boolean spell_able = false;

    //在作战计划中，得出的一些参数
    public int move_require;
    public boolean move_able;

    public Character() {
        /**
         * 创建角色时，就分配一个skill_util过来
         */
    }


    public void planning() throws ClassNotFoundException, NoSuchMethodException,
            InstantiationException, IllegalAccessException, InvocationTargetException {
        /**
         * 检查是否满足发动条件
         * 直接根据名字来查找对应的函数，不管是来自装备还是技能
         * 需要的变量永远只有2个：发动者caster，目标target
         */
        spell_able = false;
        Class<?> su = Class.forName("CodeGaming.Utils.SkillUtils");
        String name = plan_used.name;
        Method method = su.getMethod("require_" + name, Character.class, Character.class);
        try {
            Boolean result = (Boolean) method.invoke(su.newInstance(), this, plan_target);
            if (!result)
                System.out.println("不满足释放需求！");
            else {
                spell_able = true;
                System.out.println("可以释放。");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void excuting() throws ClassNotFoundException, NoSuchMethodException,
            InstantiationException, IllegalAccessException, InvocationTargetException {
        if (spell_able && act_able()) {
            Class<?> clazz = Class.forName("CodeGaming.Utils.SkillUtils");
            Method method = clazz.getMethod("affect_" + plan_used.name, Character.class, Character.class);
            method.invoke(clazz.newInstance(), this, plan_target);
        }
    }

    public void move() throws NoSuchMethodException, ClassNotFoundException {
        /**
         *  每次调用该函数，移动一次
         *  1.从公共牌堆中，获取一张地图牌
         *  2.消耗离开当前牌堆的条件
         *  3.进入新的地图牌，检查新地形的影响
         */
        Ground ground = battle_field.get_random_ground();
        battle_field.leave_ground(this);
        battle_field.enter_ground(this, ground);

    }

    public boolean act_able() {
        boolean ans = true;
        for (int i = 0; i < buffs.size(); i++)
            if (buffs.get(i).name == "晕眩")  //再加点别的，嗯
                ans = false;
        return ans;
    }

    public void receive_damage(Integer amount, String dmg_type) {
        /**
         * 主要是用来处理，对特定类型伤害的减免的问题
         */
        int final_dmg = amount;
        if (dmg_type == "钝击")
            final_dmg = Math.max(0, final_dmg - res_blunt);
        hp_current -= final_dmg;
        System.out.println(name + "受到" + final_dmg + "点" + dmg_type + "伤害。");
    }

    @Deprecated
    public void use_a_card() {
        //使用一张手牌中的牌，需求参数包括：
        //1、使用的牌
        //2、额外消耗掉的牌
        //3、
    }

    @Override
    public String toString() {
        return "name:" + this.name
                + "\ninfo:" + this.info
                + "\nhp:" + hp_current + "/" + hp;
    }
}
