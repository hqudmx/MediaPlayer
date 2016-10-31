package homework.netbox.com.com.mediaplayer.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hqudmx on 2016/9/3.
 */
public class FileUtils {
    /**
     * 辅助工具类, 用于读取sd卡根目录中的音乐文件
     */

    public static List<String> getFiles(File path) {
        // 存放音乐文件
        ArrayList<String> files = new ArrayList<String>();
        // 存放文件(暂存)
        File musics[] = path.listFiles();
        if (musics != null) {
            for (int i = 0; i < musics.length; i++) {
                if (musics[i].getName().indexOf(".") >= 0) {
                    /**
                     * 判断音乐类型 .mp3 .mar .wma .MP3 huark.txt substring() 字符串截取
                     */
                    // 截取文件后缀名,从.开始到结尾
                    String file = musics[i].getName().substring(
                            musics[i].getName().indexOf("."));
                    if (file.endsWith(".mp3")
                            || file.toLowerCase().equals(".mar")
                            || file.toLowerCase().equals(".ogg")
                            || file.toLowerCase().equals(".wma")) {
                        // 如果是音乐文件,将其添加到files列表中
                        files.add(musics[i].getName());
                    }
                }
            }
        }
        return files;
    }
}


