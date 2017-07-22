package cn.zhengzhaoyu.personalPage.word;

import cn.zhengzhaoyu.personalPage.common.model.Word;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * Created by Nepge on 2017/7/22.
 *
 * @author Rain
 * @version 1.0
 * @since 1.0
 */

public class WordService {
    public static final WordService me = new WordService();
    private static final Word wordDao = new Word().dao();

    public Ret addWord(String name, String text) {
        Word word = new Word();
        word.setWordName(name).setWordText(text);
        if (word.save()) {
            return Ret.by("status", true).set("message", "留言成功");
        } else {
            return Ret.by("status", false).set("message", "数据库错误");
        }
    }

    public Page<Record> getWords(int pageSize, int pageNumber) {
        return Db.paginate(pageNumber, pageSize, wordDao.getSqlPara("word.getAll"));
    }

    public Ret deleteWord(int wordId) {
        Word word = wordDao.findFirst(wordDao.getSqlPara("word.findById", wordId));
        if (null == word) {
            return Ret.by("status", false).set("message", "留言不存在");
        }
        if (word.delete()) {
            return Ret.by("status", true).set("message", "删除成功");
        } else {
            return Ret.by("status", false).set("message", "数据库错误");
        }
    }
}
