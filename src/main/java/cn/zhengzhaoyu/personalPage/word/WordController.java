package cn.zhengzhaoyu.personalPage.word;

import cn.zhengzhaoyu.personalPage.common.controller.BaseController;
import cn.zhengzhaoyu.personalPage.common.service.LogService;
import com.jfinal.aop.Before;
import com.jfinal.ext.interceptor.GET;
import com.jfinal.ext.interceptor.POST;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * Created by Nepge on 2017/7/22.
 *
 * @author Rain
 * @version 1.0
 * @since 1.0
 */
public class WordController extends BaseController {
    private static final WordService ws = new WordService();
    private static final LogService ls = new LogService();

    @Before({GET.class})
    public void index() {
        int pageNum = 1;
        if (null != getParaToInt()) {
            pageNum = getParaToInt();
        }
        Page<Record> words = ws.getWords(6, pageNum);
        if (0 == words.getList().size() && 0 != words.getTotalRow()) {
            renderError(404);
        }
        setAttr("words", words);
        render("index.html");
    }

    @Before({POST.class})
    public void uploadWord() {
        String name = getPara("name");
        String text = getPara("text");
        ls.addLog("Word add.", getIp());
        renderJson(ws.addWord(name, text));
    }

    @Before({POST.class})
    public void removeWord() {
        int wordId = getParaToInt();
        renderJson(ws.deleteWord(wordId));
    }
}
