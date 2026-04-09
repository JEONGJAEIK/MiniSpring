package com.createspring.spring.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 이벤트리스너 저장소
 * 트랜잭셔널 이벤트리스너는 별도의 저장소에서 다룬다.
 */
public class SimpleEventListenerFactory {

    /**
     * 이벤트리스너의 빈 이름을 가지고 있는 해시 맵
     */
    private static final Map<Class<?>, List<ApplicationListenerMethodAdapter>> listenerList = new HashMap<>();

    public static void setListener(Class<?> trigger, ApplicationListenerMethodAdapter adapter) {
        if (listenerList.containsKey(trigger)) {
            listenerList.get(trigger).add(adapter);
        } else {
            listenerList.put(trigger, new ArrayList<>(List.of(adapter)));
        }
    }

    public List<ApplicationListenerMethodAdapter> getAdapter(Class<?> clazz) {
        return listenerList.get(clazz);
    }
}
