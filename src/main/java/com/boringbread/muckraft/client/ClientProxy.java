package com.boringbread.muckraft.client;

import com.boringbread.muckraft.common.CommonProxy;
import com.boringbread.muckraft.common.item.ItemMuckCheese;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit()
    {
        super.preInit();
        ItemMuckCheese.preInitClient();
    }
}
