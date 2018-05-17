package CodeGaming;

import CodeGaming.CardClazz.Buff;
import CodeGaming.CardClazz.Ground;
import CodeGaming.Utils.CardUtils;
import scala.tools.cmd.gen.AnyVals;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

public class BattleField {
    public ArrayList<Ground> envirment = new ArrayList<Ground>();

    public Ground get_random_ground() {
        int len = envirment.size();
        int ran = new Random().nextInt(len);    //返回一个0~len-1的整数
        Ground result = envirment.get(ran);
        envirment.remove(result);   //把地形从牌堆中取出，并返回
        return result;
    }

    public void leave_ground(Character player) throws ClassNotFoundException, NoSuchMethodException {
        Ground ground = player.current_ground;
        Class<?> cu = Class.forName("CodeGaming.Utils.CardUtils");
        //不一定存在消耗，所以不一定存在对应的leave函数
        try {
            Method method = cu.getMethod("leave_" + ground.name, Character.class);
            method.invoke(cu.newInstance(), player);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //不管发生了啥，都已经离开了，应当将原来的地形加入到公共排队中
        envirment.add(ground);
    }

    public void enter_ground(Character player, Ground ground) {
        ArrayList<Buff> buffs_copy = new ArrayList<Buff>(); //不能直接取等于，否则就只是引用原来的buffs了
        buffs_copy.addAll(player.buffs);
        CardUtils cu = new CardUtils();
        for (int i = 0; i < buffs_copy.size(); i++)
            cu.buff_enter_ground(player, buffs_copy.get(i), ground);
    }
}
