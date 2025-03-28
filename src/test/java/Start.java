import java.io.File;
import java.util.Arrays;
import net.minecraft.client.main.Main;

public class Start {
    public static void main(String[] args) {

        File nativesDir = new File("test_natives",
                System.getProperty("os.name").startsWith("Windows") ? "windows" : "linux");
        System.setProperty("org.lwjgl.librarypath", nativesDir.getAbsolutePath());

        final String appdataDirectory = System.getenv("APPDATA");
        File runDir = new File(appdataDirectory != null ? appdataDirectory : System.getProperty("user.home", "."), ".minecraft/");
        File gameDir = new File(runDir, ".");
        File assetsDir = new File(runDir, "assets/");

        String[] defaultArgs = new String[]{
                "--version", "1.8.9",
                "--accessToken", "0",
                "--assetIndex", "1.8",
                "--userProperties", "{}",
                "--gameDir", gameDir.getAbsolutePath(),
                "--assetsDir", assetsDir.getAbsolutePath()
        };

        Main.main(concat(defaultArgs, args));
    }

    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }
}