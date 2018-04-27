package CodeGaming;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import CodeGaming.Utils.*;

public class Character {
    public String name = "";
    public String info = "";
    public int hp = 0;
    public int speed = 0;
    public double position = 0.5; //记录角色的位置。初始位置是0.5，所以敌对双方初始位置距离就是1。
    public ArrayList<Card> hand_card = new ArrayList<Card>();
    public ArrayList<Card> deck_card = new ArrayList<Card>();

    //作战计划
    public Character plan_target;
    public Card plan_used;
    public ArrayList<Card> plan_cost;

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
        Class<?> su = Class.forName("CodeGaming.Utils.SkillUtils");
        String name = plan_used.name;
        Method method = su.getMethod("require_" + name, Character.class, Character.class);
        try {
            method.invoke(su.newInstance(), this, plan_target);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void excuting() throws ClassNotFoundException, NoSuchMethodException,
            InstantiationException, IllegalAccessException, InvocationTargetException {

        Class<?> clazz = Class.forName("CodeGaming.Utils.SkillUtils");
        Method method = clazz.getMethod("spell_Fireball", Character.class);
        method.invoke(clazz.newInstance(), this);
    }

    public void move()
    {
        /**
         *
         */
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
        return "name:" + this.name + "\ninfo:" + this.info;
    }
}
