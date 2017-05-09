package wesayallright.timemanager.InnerLayer.Item;

import android.support.v4.util.Pair;
import android.util.Log;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;

import wesayallright.timemanager.InnerLayer.ItemType;
import wesayallright.timemanager.InnerLayer.Package;

import static wesayallright.timemanager.InnerLayer.Package.*;

/**
 * Created by mj on 17-5-9.
 * 每一个日历项目
 */

public class ItemList {

    ArrayList<Item> itemLsit = new ArrayList<>();

    void readItem() {
        try {

            Calendar c = Calendar.getInstance();

            ArrayList<Integer>[] expectWeekday = new ArrayList[7];
            for (int i = 0; i < 7; i++)
                expectWeekday[i] = new ArrayList<>();

            Element rootElement = calendarXMLTree.getDocumentElement();
            int schoolWeek = Integer.valueOf(rootElement.getAttribute("schoolWeek"));
            NodeList items = rootElement.getElementsByTagName("item");
            for (int i = 0; i < items.getLength(); i++) {
                Element item = (Element) items.item(i);
                ItemType type = ItemType.valueOf(item.getAttribute("type"));
                String id = item.getAttribute("id");
                String name = item.getAttribute("name");
                int priority = Integer.valueOf(item.getAttribute("priority"));
                String detail = item.getAttribute("details");
                Date firstDate = dateFormatter.parse(item.getAttribute("firstDate"));
                Date lastDate = dateFormatter.parse(item.getAttribute("lastDate"));
                //first date and last date are both includes
                // 获得起始和结束的星期(相对)
                c.setTime(firstDate);
                int firstWeek = c.get(Calendar.WEEK_OF_YEAR) - schoolWeek + 2; // 获得的week是从0开始
                c.setTime(lastDate);
                int lastWeek = c.get(Calendar.WEEK_OF_YEAR) - schoolWeek + 2;

                if (type == ItemType.Course) {
                    detail = detail.split(":")[1]; // 教师: 小明
                }

                NodeList expectList = item.getElementsByTagName("expectTimes");
                NodeList exTime = expectList.item(0).getChildNodes();

                for (int ii = 0; ii < 7; ii++)
                    expectWeekday[ii].clear();

                for (int k = 0; k < exTime.getLength(); k++) {
                    Element x = (Element) exTime.item(k);
                    c.setTime(dateFormatter.parse(x.getAttribute("time")));
                    int w = c.get(Calendar.WEEK_OF_YEAR) + 1;
                    int d = c.get(Calendar.DAY_OF_WEEK) - 1;
                    d = d == 0 ? 7 : d; // 这里获得的星期1-7分别对应日-六, 所以这样转换.
                    expectWeekday[d - 1].add(w);
                }

                for (int ii = 0; ii < 7; ii++) {
                    expectWeekday[ii].sort((e1, e2) -> e1.compareTo(e2));
                }

                NodeList timeList = item.getElementsByTagName("time");
                for (int j = 0; j < timeList.getLength(); j++) {
                    Element time = (Element) timeList.item(j);
                    int day = Integer.valueOf(time.getAttribute("day"));
                    int startHour = Integer.valueOf(time.getAttribute("startTime").split("-")[0]);
                    int startMinute = Integer.valueOf(time.getAttribute("startTime").split("-")[1]);
                    int endHour = Integer.valueOf(time.getAttribute("endTime").split("-")[0]);
                    int endMinute = Integer.valueOf(time.getAttribute("endTime").split("-")[1]);

                    StringBuilder week = new StringBuilder();
                    for (int count = 0; count < expectWeekday[day - 1].size(); count++) {
                        if (expectWeekday[day - 1].get(count) < firstWeek)
                            continue;
                        if(expectWeekday[day - 1].get(count) > lastWeek)
                            break;
                        if (count == 0) {
                            if (expectWeekday[day - 1].get(0) != firstWeek) {
                                week.append(Integer.toString(firstWeek));
                                week.append(Integer.toString(expectWeekday[day - 1].get(0) - 1));
                            }
                        }

                        else if(expectWeekday[day - 1].get(count)-expectWeekday[day - 1].get(count - 1) != 1) {
                            week.append(expectWeekday[day - 1].get(count) - 1);
                        }

                        if (count != expectWeekday[day - 1].size() - 1 ) {
                            if (expectWeekday[day - 1].get(count + 1)-expectWeekday[day - 1].get(count) != 1)
                                week.append(expectWeekday[day - 1].get(count) + 1);
                        }

                        else {
                            if (expectWeekday[day - 1].get(count) < lastWeek) {
                                week.append(expectWeekday[day-1].get(count) + 1);
                            }
                            if (expectWeekday[day - 1].get(count) != lastWeek) {
                                week.append(Integer.toString(lastWeek));
                            }
                        }
                    }


                    itemLsit.add(
                            new Item(
                                    week.toString(), //schoolWeek + j, TODO:星期为啥是String
                                    day,
                                    startHour,
                                    startMinute,
                                    endHour,
                                    endMinute,
                                    name,
                                    time.getAttribute("place"),
                                    detail,
                                    0xffffffff,// TODO: color
                                    priority
                            )
                    );
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("读取ITEM", "解析日期错误");
        }
    }
}
