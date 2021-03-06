package com.boringbread.muckraft.config;

import com.boringbread.muckraft.Muckraft;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Level;

import java.io.File;

public class Config
{
    //creates config file
    private static final String CATEGORY_DIMENSIONS = "dimensions";

    //access these for config values
    public static Configuration config;
    public static int stageOneID;
    public static int stageTwoID;
    public static int stageThreeID;
    public static int stageFourID;
    public static int stageFiveID;
    public static int stageSixID;
    public static int[] dimensionIDs;


    public static void preInitCommon(FMLPreInitializationEvent event)
    {
        File directory = event.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "muckraft.cfg"));
        Config.readConfig();
    }

    public static void postInitCommon()
    {
        if (config.hasChanged())
        {
            config.save();
        }
    }

    public static void readConfig()
    {
        Configuration cfg = config;

        try
        {
            cfg.load();
            initDimensionConfig(cfg);
        }
        catch (Exception exception)
        {
            Muckraft.logger.log(Level.ERROR, "Problem loading config file!", exception);
        }
        finally
        {
            if (cfg.hasChanged())
            {
                cfg.save();
            }
        }
    }

    private static void initDimensionConfig(Configuration cfg)
    {
        //get config values and assign them to variables
        Muckraft.logger.log(Level.DEBUG, "Initializing Dimension Configs");
        stageOneID = cfg.getInt("Stage One Dimension ID", CATEGORY_DIMENSIONS, 6969, -9999, 9999, "Set the dimension ID for stage one: ");
        stageTwoID = cfg.getInt("Stage Two Dimension ID", CATEGORY_DIMENSIONS, 6970, -9999, 9999, "Set the dimension ID for stage two: ");
        stageThreeID = cfg.getInt("Stage Three Dimension ID", CATEGORY_DIMENSIONS, 0, -9999, 9999, "Set the dimension ID for stage three: ");
        stageFourID = cfg.getInt("Stage Four Dimension ID", CATEGORY_DIMENSIONS, 6971, -9999, 9999, "Set the dimension ID for stage four: ");
        stageFiveID = cfg.getInt("Stage Five Dimension ID", CATEGORY_DIMENSIONS, 6972, -9999, 9999, "Set the dimension ID for stage five: ");
        stageSixID = cfg.getInt("Stage Six Dimension ID", CATEGORY_DIMENSIONS, 6973, -9999, 9999, "Set the dimension ID for stage six: ");
        dimensionIDs = new int[]{stageOneID, stageTwoID, stageThreeID, stageFourID, stageFiveID, stageSixID};
    }
}
