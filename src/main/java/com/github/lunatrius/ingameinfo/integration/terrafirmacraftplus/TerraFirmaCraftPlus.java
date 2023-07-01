package com.github.lunatrius.ingameinfo.integration.terrafirmacraftplus;

import com.dunk.tfc.Reference;
import com.github.lunatrius.ingameinfo.integration.Plugin;
import com.github.lunatrius.ingameinfo.integration.terrafirmacraftplus.tag.TagTerraFirmaCraftPlus;
import com.github.lunatrius.ingameinfo.reference.Names;

// NOTE: requires VM arguments: -Dfml.coreMods.load=com.dunk.tfc.TFCASMLoadingPlugin
@SuppressWarnings("UnusedDeclaration")
public class TerraFirmaCraftPlus extends Plugin {
    @Override
    public String getDependency() {
        return Names.Mods.TERRAFIRMACRAFTPLUS_MODID;
    }

    @Override
    public String getDependencyName() {
        return Names.Mods.TERRAFIRMACRAFTPLUS_NAME;
    }

    @Override
    public String getDependencyVersion() {
        return Reference.MOD_VERSION;
    }

    @Override
    public void load() {
        TagTerraFirmaCraftPlus.register();
    }
}
