package com.infosisarg.plugin;

import com.infosisarg.api.Broker;
import com.infosisarg.api.PlatformPlugin;
import com.infosisarg.api.PluginInterface;
import org.pf4j.Plugin;
import org.pf4j.PluginException;
import org.pf4j.PluginWrapper;

import java.sql.Wrapper;
import java.util.List;

public class ExamplePlugin extends PlatformPlugin {
    private PluginWrapper wrap;

    public ExamplePlugin(PluginWrapper wrapper) {
        super(wrapper);
        wrap = wrapper;
    }

    @Override
    public void init(Broker broker) {
        super.init(broker);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        List as = wrap.getPluginManager().getExtensions(wrap.getPluginId());
                        ((PluginExtension) as.get(0)).send("asd");
                        Thread.sleep(3000);

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
