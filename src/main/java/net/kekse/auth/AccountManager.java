package net.kekse.auth;

import com.google.gson.*;
import net.kekse.ui.altmanager.GuiCookieAuth;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;


import java.io.*;
import java.util.ArrayList;

public class AccountManager {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final File file = new File(mc.mcDataDir, "accounts.json");
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static final ArrayList<Account> accounts = new ArrayList<>();

    static {
        if (!file.exists()) {
            try {
                if (file.getParentFile().exists() || file.getParentFile().mkdirs()) {
                    if (file.createNewFile()) {
                        System.out.print("Successfully created accounts.json!");
                    }
                }
            } catch (IOException e) {
                System.err.print("Couldn't create accounts.json!");
            }
        }
    }

    public static void load() {
        accounts.clear();
        try {
            JsonElement json = new JsonParser().parse(new BufferedReader(new FileReader(file)));
            if (json instanceof JsonArray) {
                JsonArray jsonArray = json.getAsJsonArray();
                for (JsonElement jsonElement : jsonArray) {
                    JsonObject jsonObject = jsonElement.getAsJsonObject();
                    accounts.add(Account.fromJson(jsonObject));
                }
            }
        } catch (FileNotFoundException e) {
            System.err.print("Couldn't find accounts.json!");
        }
    }

    public static void save() {
        try {
            JsonArray jsonArray = new JsonArray();
            for (Account account : accounts) {
                jsonArray.add(account.toJson());
            }
            PrintWriter printWriter = new PrintWriter(new FileWriter(file));
            printWriter.println(gson.toJson(jsonArray));
            printWriter.close();
        } catch (IOException e) {
            System.err.print("Couldn't save accounts.json!");
        }
    }

    public static void addCrackedAccount(String username) {
        if (accounts.stream()
                .anyMatch(acc -> acc.getUsername().equalsIgnoreCase(username) && acc.getType() == AccountType.CRACKED)) {
            return;
        }
        accounts.add(new Account(
                "",
                "accessToken",
                username,
                0L,
                AccountType.CRACKED
        ));
        save();
        System.out.println("Cracked account " + username + " added successfully!");
    }

    public static void addAccountFromCookieFile(File cookieFile, GuiScreen previousScreen) {
        GuiCookieAuth gui = new GuiCookieAuth(previousScreen);
        CookieAuth.addAccountFromCookieFile(cookieFile, gui);
    }
}