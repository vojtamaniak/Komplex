package cz.vojtamaniak.komplex;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import cz.vojtamaniak.komplex.commands.CommandAfk;
import cz.vojtamaniak.komplex.commands.CommandBreak;
import cz.vojtamaniak.komplex.commands.CommandClearChat;
import cz.vojtamaniak.komplex.commands.CommandFeed;
import cz.vojtamaniak.komplex.commands.CommandFly;
import cz.vojtamaniak.komplex.commands.CommandGod;
import cz.vojtamaniak.komplex.commands.CommandHat;
import cz.vojtamaniak.komplex.commands.CommandHeal;
import cz.vojtamaniak.komplex.commands.CommandHelpOp;
import cz.vojtamaniak.komplex.commands.CommandList;
import cz.vojtamaniak.komplex.commands.CommandMail;
import cz.vojtamaniak.komplex.commands.CommandNear;
import cz.vojtamaniak.komplex.commands.CommandPtime;
import cz.vojtamaniak.komplex.commands.CommandReply;
import cz.vojtamaniak.komplex.commands.CommandTell;
import cz.vojtamaniak.komplex.commands.CommandWorkbench;
import cz.vojtamaniak.komplex.listeners.EntityListener;
import cz.vojtamaniak.komplex.listeners.PlayerListener;

public class Komplex extends JavaPlugin {
	
	private Logger log;
	private MessageManager msgManager;
	private ConfigManager confManager;
	private HashMap<String, User> users;
	private Database database;
	private CommandSender lastPMSender;
	
	@Override
	public void onEnable(){
		log = getLogger();
		msgManager = new MessageManager(this);
		confManager = new ConfigManager(this);
		users = new HashMap<String, User>();
		database = new Database(this);
		lastPMSender = null;
		
		registerExecutors();
		registerListeners();
		
		msgManager.init();
		confManager.init();
		database.load();
		
		log.info("is enabled.");
	}
	
	@Override
	public void onDisable(){
		log.info("is disabled.");
	}
	
	public MessageManager getMessageManager(){
		return msgManager;
	}
	
	private void registerExecutors(){
		getCommand("afk").setExecutor(new CommandAfk(this));
		getCommand("break").setExecutor(new CommandBreak(this));
		getCommand("feed").setExecutor(new CommandFeed(this));
		getCommand("fly").setExecutor(new CommandFly(this));
		getCommand("clearchat").setExecutor(new CommandClearChat(this));
		getCommand("god").setExecutor(new CommandGod(this));
		getCommand("hat").setExecutor(new CommandHat(this));
		getCommand("heal").setExecutor(new CommandHeal(this));
		getCommand("ptime").setExecutor(new CommandPtime(this));
		getCommand("workbench").setExecutor(new CommandWorkbench(this));
		getCommand("helpop").setExecutor(new CommandHelpOp(this));
		getCommand("list").setExecutor(new CommandList(this));
		getCommand("mail").setExecutor(new CommandMail(this));
		getCommand("tell").setExecutor(new CommandTell(this));
		getCommand("reply").setExecutor(new CommandReply(this));
		getCommand("near").setExecutor(new CommandNear(this));
	}
	
	private void registerListeners(){
		Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
		Bukkit.getPluginManager().registerEvents(new EntityListener(this), this);
	}
	
	public void addUser(User user){
		if(!users.containsValue(user)){
			users.put(user.getPlayer().getName(), user);
		}
	}
	
	public User getUser(String name) {
		return users.get(name);
	}
	
	public void removeUser(String name){
		users.remove(name);
	}
	
	public ConfigManager getConfigManager(){
		return confManager;
	}
	
	public Database getDB(){
		return database;
	}
	
	public void setLastConsolePM(CommandSender sender){
		this.lastPMSender = sender;
	}
	
	public CommandSender getLastConsolePMSender(){
		return lastPMSender;
	}
}