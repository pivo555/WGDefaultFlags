package org.example.plugin.handler;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.List;
import java.util.Locale;

import static org.bukkit.Bukkit.getServer;

public class RgClaimEventHandler implements Listener
{

    private final JavaPlugin plugin;
    public RgClaimEventHandler(JavaPlugin plugin)
    {
        this.plugin = plugin;
    }

    //For messages without '/'
    /*@EventHandler
    public void onChatPreprocess(AsyncChatEvent event)
    {
        var player = event.getPlayer();
        var message = event.message();
        getServer().sendMessage(message);
    }*/

    @EventHandler
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event)
    {
        try
        {
            Player player = event.getPlayer();
            String commandMessage = event.getMessage();
            commandMessage = commandMessage.toLowerCase(Locale.ROOT);
            String[] commandItems = commandMessage.split(" ");
            if(commandItems.length==3)
            {
                if(commandItems[0].equals("/rg") || commandItems[0].equals("/region"))
                {
                    if(commandItems[1].equals("claim"))
                    {
                        new java.util.Timer().schedule(
                            new java.util.TimerTask()
                            {
                                @Override
                                public void run()
                                {
                                    String regionName = commandItems[2];

                                    RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
                                    var world = BukkitAdapter.adapt(player.getWorld());
                                    RegionManager regions = container.get(world);
                                    var region = regions.getRegion(regionName);

                                    var worldguardPlayer = WorldGuardPlugin.inst().wrapPlayer(player);
                                    if(region.isOwner(worldguardPlayer))
                                    {

                                        List<String> flags = plugin.getConfig().getStringList("flags");
                                        for(int i=0; i<flags.size(); i++)
                                        {
                                            var items = flags.get(i).replaceAll("\\s+","").toLowerCase(Locale.ROOT).split(":");
                                            var stringFlag = items[0];
                                            var stringValue = items[1];
                                            StateFlag.State value=StateFlag.State.DENY;
                                            if(stringValue.equals("allow"))
                                            {
                                                value = StateFlag.State.ALLOW;
                                            }

                                            region.setFlag(getFlag(stringFlag), value);
                                        }
                                    }
                                }
                            },
                            10000
                        );
                    }
                }
            }
        }
        catch (Exception e)
        {
            TextComponent exceptionMessage = Component.text(ChatColor.RED + e.getMessage());
            getServer().sendMessage(exceptionMessage);
        }
    }

    private StateFlag getFlag(String stringFlag)
    {
        //Protection-Related
        //Включает в себя много флагов. В том чиcле и ПВП
        if(stringFlag.equals("build"))
        {
            return Flags.BUILD;
        }
        //Можно ли использовать двери, рычаги и т. д. (но не инвентарь)
        //Можно ли монтировать транспортные средства (включая животных)
        if(stringFlag.equals("interact"))
        {
            return Flags.INTERACT;
        }
        //Можно ли добывать блоки
        if(stringFlag.equals("block-break"))
        {
            return Flags.BLOCK_BREAK;
        }
        //Можно ли размещать блоки
        if(stringFlag.equals("block-place"))
        {
            return Flags.BLOCK_PLACE;
        }
        //Можно ли использовать двери, рычаги и т. д. (но не инвентарь)
        if(stringFlag.equals("use"))
        {
            return Flags.USE;
        }
        //Могут ли игроки причинять вред дружественным животным (коровам, овцам и т. д.)
        if(stringFlag.equals("damage-animals"))
        {
            return Flags.DAMAGE_ANIMALS;
        }
        //Можно ли получить доступ к сундукам
        if(stringFlag.equals("chest-access"))
        {
            return Flags.CHEST_ACCESS;
        }
        //Можно ли монтировать транспортные средства (включая животных)
        if(stringFlag.equals("ride"))
        {
            return Flags.RIDE;
        }
        //Разрешен ли бой игрока против игрока
        if(stringFlag.equals("pvp"))
        {
            return Flags.PVP;
        }
        //Разрешено ли спать в кровати
        if(stringFlag.equals("sleep"))
        {
            return Flags.SLEEP;
        }
        //Можно ли активировать якоря возрождения
        if(stringFlag.equals("respawn-anchors"))
        {
            return Flags.RESPAWN_ANCHORS;
        }
        //Допускается ли детонация или повреждение тротила
        if(stringFlag.equals("tnt"))
        {
            return Flags.TNT;
        }
        //Можно ли размещать транспортные средства (лодки, вагонетки)
        if(stringFlag.equals("vehicle-place"))
        {
            return Flags.PLACE_VEHICLE;
        }
        //Могут ли транспортные средства быть уничтожены
        if(stringFlag.equals("vehicle-destroy"))
        {
            return Flags.DESTROY_VEHICLE;
        }
        //Можно ли использовать кремень и сталь
        if(stringFlag.equals("lighter"))
        {
            return Flags.LIGHTER;
        }
        //Можно ли топтать сельхозугодья и черепашьи яйца
        if(stringFlag.equals("block-trampling"))
        {
            return Flags.TRAMPLE_BLOCKS;
        }
        //Будут ли игроки в ботинках морозохода образовывать лед
        if(stringFlag.equals("frosted-ice-form"))
        {
            return Flags.FROSTED_ICE_FORM;
        }
        //Можно ли вращать элементы внутри рамок элементов
        if(stringFlag.equals("item-frame-rotation"))
        {
            return Flags.ITEM_FRAME_ROTATE;
        }
        //Могут ли фейерверки наносить урон сущностям
        if(stringFlag.equals("firework-damage"))
        {
            return Flags.FIREWORK_DAMAGE;
        }
        //Можно ли использовать наковальни
        if(stringFlag.equals("use-anvil"))
        {
            return Flags.USE_ANVIL;
        }
        //Можно ли использовать капельницу(это растение , которое порождает в пышных пещерах)
        if(stringFlag.equals("use-dripleaf"))
        {
            return Flags.USE_DRIPLEAF;
        }

        //Mobs, Fire, and Explosions
        //Могут ли криперы наносить урон
        if(stringFlag.equals("creeper-explosion"))
        {
            return Flags.CREEPER_EXPLOSION;
        }
        //Whether enderdragons can do block damage
        if(stringFlag.equals("enderdragon-block-damage"))
        {
            return Flags.ENDERDRAGON_BLOCK_DAMAGE;
        }
        //Могут ли огненные шары гастов и черепа-иссушители нанести урон
        if(stringFlag.equals("ghast-fireball"))
        {
            return Flags.GHAST_FIREBALL;
        }
        //Могут ли взрывы нанести ущерб
        if(stringFlag.equals("other-explosion"))
        {
            return Flags.OTHER_EXPLOSION;
        }
        //Может ли огонь распространяться
        if(stringFlag.equals("fire-spread"))
        {
            return Flags.FIRE_SPREAD;
        }
        //Будут ли эндермены грифить
        if(stringFlag.equals("enderman-grief"))
        {
            return Flags.ENDER_BUILD;
        }
        //Будут ли снеговики создавать снег под собой
        if(stringFlag.equals("snowman-trails"))
        {
            return Flags.SNOWMAN_TRAILS;
        }
        //Будут ли опустошители грифить
        if(stringFlag.equals("ravager-grief"))
        {
            return Flags.RAVAGER_RAVAGE;
        }
        //Могут ли мобы навредить игрокам
        if(stringFlag.equals("mob-damage"))
        {
            return Flags.MOB_DAMAGE;
        }
        //Могут ли мобы спавниться
        if(stringFlag.equals("mob-spawning"))
        {
            return Flags.MOB_SPAWNING;
        }

        //deny-spawn(сюда нужно передавать набор типов сущностей) в след версии
        //мб и настройку Region Groups(nonowners и тд) добавить

        //Могут ли неигровые сущности уничтожать картины
        if(stringFlag.equals("entity-painting-destroy"))
        {
            return Flags.ENTITY_PAINTING_DESTROY;
        }
        //Могут ли сущности, не являющиеся игроками, разрушать рамки
        if(stringFlag.equals("entity-item-frame-destroy"))
        {
            return Flags.ENTITY_ITEM_FRAME_DESTROY;
        }
        //Могут ли иссушители наносить урон
        if(stringFlag.equals("wither-damage"))
        {
            return Flags.WITHER_DAMAGE;
        }

        //Natural Events
        //Может ли лава вызвать пожар
        if(stringFlag.equals("lava-fire"))
        {
            return Flags.LAVA_FIRE;
        }
        //Может ли молния ударить
        if(stringFlag.equals("lightning"))
        {
            return Flags.LIGHTNING;
        }
        //Будет ли вода разливаться
        if(stringFlag.equals("water-flow"))
        {
            return Flags.WATER_FLOW;
        }
        //Будет ли лава разливаться
        if(stringFlag.equals("lava-flow"))
        {
            return Flags.LAVA_FLOW;
        }
        //Будет ли снег образовывать плитки на земле
        if(stringFlag.equals("snow-fall"))
        {
            return Flags.SNOW_FALL;
        }
        //Будет ли таять снег
        if(stringFlag.equals("snow-melt"))
        {
            return Flags.SNOW_MELT;
        }
        //Будет ли образовываться лед
        if(stringFlag.equals("ice-form"))
        {
            return Flags.ICE_FORM;
        }
        //Будет ли таять лед
        if(stringFlag.equals("ice-melt"))
        {
            return Flags.ICE_MELT;
        }
        //Будет ли образовываться иней
        if(stringFlag.equals("frosted-ice-melt"))
        {
            return Flags.FROSTED_ICE_MELT;
        }
        //Будут ли расти грибы
        if(stringFlag.equals("mushroom-growth"))
        {
            return Flags.MUSHROOMS;
        }
        //Будут ли листья увядать
        if(stringFlag.equals("leaf-decay"))
        {
            return Flags.LEAF_DECAY;
        }
        //Будет ли расти трава
        if(stringFlag.equals("grass-growth"))
        {
            return Flags.GRASS_SPREAD;
        }
        //Будет ли распространяться мицелий
        if(stringFlag.equals("mycelium-spread"))
        {
            return Flags.MYCELIUM_SPREAD;
        }
        //Будут ли расти виноградные лозы (и водоросли)
        if(stringFlag.equals("vine-growth"))
        {
            return Flags.VINE_GROWTH;
        }
        //Будут ли расти сельскохозяйственные культуры (пшеница, картофель, бахчевые культуры и т. д.)
        if(stringFlag.equals("crop-growth"))
        {
            return Flags.CROP_GROWTH;
        }
        //Будет ли сохнуть почва
        if(stringFlag.equals("soil-dry"))
        {
            return Flags.SOIL_DRY;
        }
        //Умрут ли кораллы, если они не в воде.
        if(stringFlag.equals("coral-fade"))
        {
            return Flags.CORAL_FADE;
        }

        //Movement
        //Могут ли игроки войти в регион
        if(stringFlag.equals("entry"))
        {
            return Flags.ENTRY;
        }
        //Могут ли игроки покинуть регион
        if(stringFlag.equals("exit"))
        {
            return Flags.EXIT;
        }
        //Могут ли игроки покинуть регион через телепорт.
        //Это вступает в силу только в том случае, если игроку иным образом запрещено покидать регион.
        if(stringFlag.equals("exit-via-teleport"))
        {
            return Flags.EXIT_VIA_TELEPORT;
        }
        //exit-override
        //exit-override
        //entry-deny-message
        //exit-deny-message
        //notify-enter
        //notify-leave
        //greeting
        //greeting-title
        //farewell
        //farewell-title
        //Можно ли использовать жемчуг края
        if(stringFlag.equals("enderpearl"))
        {
            return Flags.ENDERPEARL;
        }
        //Можно ли использовать плоды хоруса для телепортации
        if(stringFlag.equals("chorus-fruit-teleport"))
        {
            return Flags.CHORUS_TELEPORT;
        }
        //teleport
        //spawn
        //teleport-message

        //Map Making
        //Можно ли забрать предметы
        if(stringFlag.equals("item-pickup"))
        {
            return Flags.ITEM_PICKUP;
        }
        //Можно ли выбрасывать предметы
        if(stringFlag.equals("item-drop"))
        {
            return Flags.ITEM_DROP;
        }
        //Будет ли выпадать экспа
        if(stringFlag.equals("exp-drops"))
        {
            return Flags.EXP_DROPS;
        }
        //deny-message
        //Whether players are invincible(непобедимый)
        if(stringFlag.equals("invincible"))
        {
            return Flags.INVINCIBILITY;
        }
        //Получают ли сущности урон от падения
        if(stringFlag.equals("fall-damage"))
        {
            return Flags.FALL_DAMAGE;
        }
        //game-mode
        //time-lock
        //weather-lock
        //Должны ли игроки естественным образом восстанавливать здоровье в состоянии сытости или в мирном режиме.
        if(stringFlag.equals("natural-health-regen"))
        {
            return Flags.HEALTH_REGEN;
        }
        //Должны ли игроки естественным образом терять чувство голода из-за уровней насыщения/истощения.
        if(stringFlag.equals("natural-hunger-drain"))
        {
            return Flags.HUNGER_DRAIN;
        }
        //heal-delay
        //heal-amount
        //heal-min-health
        //heal-max-health
        //feed-delay
        //feed-amount
        //feed-min-hunger
        //feed-max-hunger
        //blocked-cmds
        //allowed-cmds

        //Miscellaneous
        //Можно ли использовать поршни
        if(stringFlag.equals("pistons"))
        {
            return Flags.PISTONS;
        }
        //Могут ли игроки отправлять сообщения в чат
        if(stringFlag.equals("send-chat"))
        {
            return Flags.SEND_CHAT;
        }
        //Могут ли игроки получать сообщения из чата
        if(stringFlag.equals("receive-chat"))
        {
            return Flags.RECEIVE_CHAT;
        }
        //Могут ли зелья иметь брызговые эффекты
        if(stringFlag.equals("potion-splash"))
        {
            return Flags.POTION_SPLASH;
        }

        return Flags.PVP;
    }

//The End
}
