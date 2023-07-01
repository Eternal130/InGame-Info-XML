package com.github.lunatrius.ingameinfo.integration.terrafirmacraftplus.tag;

//import com.dunk.tfc.Core.TFC_Core.getBodyTempStats;
import com.dunk.tfc.Core.Player.SkillStats;
import com.dunk.tfc.Core.TFC_Climate;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.api.SkillsManager;
import com.github.lunatrius.ingameinfo.reference.Reference;
import com.github.lunatrius.ingameinfo.tag.TagIntegration;
import com.github.lunatrius.ingameinfo.tag.registry.TagRegistry;
import net.minecraft.client.resources.I18n;

import java.util.Locale;

public abstract class TagTerraFirmaCraftPlus extends TagIntegration {
    @Override
    public String getCategory() {
        return "terrafirmacraftplus";
    }

    public static class Rainfall extends TagTerraFirmaCraftPlus {
        @Override
        public String getValue() {
            try {
                return String.valueOf((int) TFC_Climate.getRainfall(world, playerPosition.x, playerPosition.y, playerPosition.z));
            } catch (Throwable e) {
                log(this, e);
            }
            return "-1";
        }
    }

    public static class Humidity extends TagTerraFirmaCraftPlus {
        @Override
        public String getValue() {
            try {
                return String.format(Locale.ENGLISH, "%.1f%%", TFC_Core.getHumidity(world, playerPosition.x, playerPosition.y, playerPosition.z) * 100f);
            } catch (Throwable e) {
                log(this, e);
            }
            return "-1";
        }
    }

    public static class Temperature extends TagTerraFirmaCraftPlus {
        @Override
        public String getValue() {
            try {
                return String.format(Locale.ENGLISH, "%.2f", TFC_Climate.getHeightAdjustedTemp(world, playerPosition.x, playerPosition.y, playerPosition.z));
            } catch (Throwable e) {
                log(this, e);
            }
            return "-1";
        }
    }

    public static class AverageTemperature extends TagTerraFirmaCraftPlus {
        @Override
        public String getValue() {
            try {
                return String.format(Locale.ENGLISH, "%.2f", TFC_Climate.getBioTemperatureHeight(world, playerPosition.x, playerPosition.y, playerPosition.z));
            } catch (Throwable e) {
                log(this, e);
            }
            return "-1";
        }
    }

    public static class TemperatureWithHeatSources extends TagTerraFirmaCraftPlus {
        @Override
        public String getValue() {
            try {
                return String.format(Locale.ENGLISH, "%.2f", TFC_Climate.getHeightAdjustedTemp(world, playerPosition.x, playerPosition.y, playerPosition.z) + TFC_Core.getBodyTempStats(player).ambientTemperature);
            } catch (Throwable e) {
                log(this, e);
            }
            return "-1";
        }
    }

    public static class Season extends TagTerraFirmaCraftPlus {
        @Override
        public String getValue() {
            try {
                return TFC_Time.getSeason();
            } catch (Throwable e) {
                log(this, e);
            }
            return "";
        }
    }

    public static class Date extends TagTerraFirmaCraftPlus {
        @Override
        public String getValue() {
            try {
                return TFC_Time.getDateStringFromHours((int) TFC_Time.getTotalHours());
            } catch (Throwable e) {
                log(this, e);
            }
            return "";
        }
    }

    public static class Day extends TagTerraFirmaCraftPlus {
        @Override
        public String getValue() {
            try {
                return String.valueOf(TFC_Time.getDayOfMonth());
            } catch (Throwable e) {
                log(this, e);
            }
            return "";
        }
    }

    public static class Month extends TagTerraFirmaCraftPlus {
        @Override
        public String getValue() {
            try {
                return String.valueOf(TFC_Time.MONTHS[TFC_Time.getMonth()]);
            } catch (Throwable e) {
                log(this, e);
            }
            return "";
        }
    }

    public static class Year extends TagTerraFirmaCraftPlus {
        @Override
        public String getValue() {
            try {
                return String.valueOf(1000 + TFC_Time.getYear());
            } catch (Throwable e) {
                log(this, e);
            }
            return "";
        }
    }

    public static class Skill extends TagTerraFirmaCraftPlus {
        private final String skillName;

        public Skill(final String skillName) {
            this.skillName = skillName;
        }

        @Override
        public String getValue() {
            try {
                final SkillStats skillStats = TFC_Core.getSkillStats(player);
                return String.format(Locale.ENGLISH, "%s (%s), %.1f%%", I18n.format(this.skillName), skillStats.getSkillRank(this.skillName).getLocalizedName(), skillStats.getPercToNextRank(this.skillName) * 100);
            } catch (Throwable e) {
                log(this, e);
            }
            return "";
        }

        @Override
        public String getLocalizedDescription() {
            return I18n.format(Reference.MODID.toLowerCase() + ".tag.fmtskill.desc", I18n.format(this.skillName));
        }
    }

    public static class SkillRank extends TagTerraFirmaCraftPlus {
        private final String skillName;

        public SkillRank(final String skillName) {
            this.skillName = skillName;
        }

        @Override
        public String getValue() {
            try {
                return TFC_Core.getSkillStats(player).getSkillRank(this.skillName).getLocalizedName();
            } catch (Throwable e) {
                log(this, e);
            }
            return "";
        }

        @Override
        public String getLocalizedDescription() {
            return I18n.format(Reference.MODID.toLowerCase() + ".tag.fmtskillrank.desc", I18n.format(this.skillName));
        }
    }

    public static class SkillProgress extends TagTerraFirmaCraftPlus {
        private final String skillName;

        public SkillProgress(final String skillName) {
            this.skillName = skillName;
        }

        @Override
        public String getValue() {
            try {
                return String.format(Locale.ENGLISH, "%.1f", TFC_Core.getSkillStats(player).getPercToNextRank(this.skillName) * 100);
            } catch (Throwable e) {
                log(this, e);
            }
            return "";
        }

        @Override
        public String getLocalizedDescription() {
            return I18n.format(Reference.MODID.toLowerCase() + ".tag.fmtskillprogress.desc", I18n.format(this.skillName));
        }
    }

    public static void register() {
        TagRegistry.INSTANCE.register(new Rainfall().setName("tfcplusrainfall"));
        TagRegistry.INSTANCE.register(new Humidity().setName("tfcplushumidity"));
        TagRegistry.INSTANCE.register(new Temperature().setName("tfcplustemperature"));
        TagRegistry.INSTANCE.register(new AverageTemperature().setName("tfcplusaveragetemperature"));
        TagRegistry.INSTANCE.register(new TemperatureWithHeatSources().setName("tfcplustemperatureheatsources"));
        TagRegistry.INSTANCE.register(new Season().setName("tfcplusseason"));
        TagRegistry.INSTANCE.register(new Date().setName("tfcplusdate"));
        TagRegistry.INSTANCE.register(new Day().setName("tfcplusday"));
        TagRegistry.INSTANCE.register(new Month().setName("tfcplusmonth"));
        TagRegistry.INSTANCE.register(new Year().setName("tfcplusyear"));

        for (SkillsManager.Skill skill : SkillsManager.instance.getSkillsArray()) {
            String skillName = skill.skillName.toLowerCase();
            if (skillName.startsWith("skill.")) {
                skillName = skillName.substring(6);
            }

            TagRegistry.INSTANCE.register(new Skill(skill.skillName).setName("tfcplusskill" + skillName));
            TagRegistry.INSTANCE.register(new SkillRank(skill.skillName).setName("tfcplusskillrank" + skillName));
            TagRegistry.INSTANCE.register(new SkillProgress(skill.skillName).setName("tfcplusskillprogress" + skillName));
        }
    }
}
