package me.rejomy.lightpractice.manager;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.ImmutableClassToInstanceMap;
import me.rejomy.lightpractice.util.interfaces.Loadable;

public class InitManager implements Loadable {
    ClassToInstanceMap<Loadable> initializersOnLoad;
    ClassToInstanceMap<Loadable> initializersOnStart;
    ClassToInstanceMap<Loadable> initializersOnStop;

    public InitManager() {
        initializersOnLoad = new ImmutableClassToInstanceMap.Builder<Loadable>()
                .put(ConfigManager.class, new ConfigManager())
                .build();

        initializersOnStart = new ImmutableClassToInstanceMap.Builder<Loadable>()
                .build();

        initializersOnStop = new ImmutableClassToInstanceMap.Builder<Loadable>()
                .build();
    }

    @Override
    public void load() {
        for (Loadable loadable : initializersOnLoad.values()) {
            loadable.load();
        }
    }

    public void start() {
        for (Loadable loadable : initializersOnStart.values()) {
            loadable.load();
        }
    }

    public void stop() {
        for (Loadable loadable : initializersOnStop.values()) {
            loadable.load();
        }
    }
}
