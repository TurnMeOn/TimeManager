package wesayallright.schedule.InnerLayer.Group;

import java.util.Iterator;

import wesayallright.timemanager.InnerLayer.Group.Group;
import wesayallright.timemanager.InnerLayer.Group.GroupList;

/**
 * Created by mj on 17-4-16.
 * 群组类的迭代器
 * 如果迭代器创建之后groupList再被修改，就会抛出java.util.ConcurrentModificationException
 * 这时可以用reset重置迭代器，使迭代器与list重新同步
 */

public class GroupIterator implements Iterator {
    private int index;
    private int lastChange;
    private int max;
    private GroupList g;

    public GroupIterator(GroupList c) {
        g = c;
        reset();
    }

    private boolean isAvailable() {
        if (lastChange < g.changedTimes)
            throw new java.util.ConcurrentModificationException();
        return true;
    }

    public void reset() {
        index = 0;
        max = g.groupList.length;
        lastChange = g.changedTimes;
    }

    public Group next() {
        isAvailable();
        return g.groupList[index++];
    }

    public boolean hasNext() {
        isAvailable();
        return index < max;
    }

}
