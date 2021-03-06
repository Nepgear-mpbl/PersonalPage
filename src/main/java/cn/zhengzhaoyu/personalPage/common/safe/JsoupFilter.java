package cn.zhengzhaoyu.personalPage.common.safe;

import cn.zhengzhaoyu.personalPage.common.model.Comment;
import cn.zhengzhaoyu.personalPage.common.model.Word;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;

public class JsoupFilter {
    /**
     * 用于过滤 content 字段的白名单，需要允许比较多的 tag
     */
    private static final Whitelist contentWhitelist = createContentWhitelist();
    private static final Document.OutputSettings notPrettyPrint = new Document.OutputSettings().prettyPrint(false);

    private JsoupFilter() {

    }

    private static Whitelist createContentWhitelist() {
        return Whitelist.relaxed()
                /*
                 * 必须要删除应用在 a 与 img 上的 protocols，否则就只有使用了这些 protocol 的才不被过滤，比较蛋疼
                 * 在 remove 的时候，后面的 protocols 要完全一个不露的对应上 jsoup 默认已经添加的，否则仍然会被过滤掉
                 * 在升级 jsoup 后需要测试这 a 与 img 的过滤是否正常
                 */
                .removeProtocols("a", "href", "ftp", "http", "https", "mailto")
                .removeProtocols("img", "src", "http", "https")

                .addAttributes("a", "href", "title", "target")  // 官方默认会将 target 给过滤掉

                /*
                 * 在 Whitelist.relaxed() 之外添加额外的白名单规则
                 */
                .addTags("div", "span", "embed", "object", "param")
                .addAttributes(":all", "style", "class", "id", "name")
                .addAttributes("object", "width", "height", "classid", "codebase")
                .addAttributes("param", "name", "value")
                .addAttributes("embed", "src", "quality", "width", "height", "allowFullScreen", "allowScriptAccess", "flashvars", "name", "type", "pluginspage");
    }

    public static void filterComment(Comment m) {
        String text = m.getCommentText();
        m.setCommentText(getText(text));
        String name = m.getCommentName();
        m.setCommentName(getText(name));
    }
    public static void filterWord(Word w) {
        String text = w.getWordText();
        w.setWordText(getText(text));
        String name = w.getWordName();
        w.setWordName(getText(name));
    }

    /**
     * 过滤 content，但保留换行回车符
     *
     * @param content 带过滤的字符串
     * @return 过滤后的字符串
     */
    public static String filterContentKeepNewline(String content) {
        return null == content ? null : Jsoup.clean(content, "", contentWhitelist, notPrettyPrint);
    }

    /**
     * 将回车换行符过滤成 <br> 标记。三次 replace 为兼容 windows、linux、mac os 输入
     * windows换行为：\r\n
     * linux 换行为：\n
     * mac os 换行为：\r
     *
     * @param content 待过滤的字符串
     * @return 过滤后的字符串
     */
    public static String filterNewlineToBrTag(String content) {
        return null == content ? null : content.replaceAll("\r\n", "<br>").replaceAll("\r", "<br>").replaceAll("\n", "<br>");
    }

    /**
     * 获取 html 中的纯文本信息，过滤所有 tag
     *
     * @param html 待过滤的字符串
     * @return 过滤后的字符串
     */
    public static String getText(String html) {
        return null == html ? null : Jsoup.clean(html, Whitelist.none());
    }

    /**
     * 使用Whitelist.simpleText() 白名单，获取 simple html 内容
     * 允许的 tag："b", "em", "i", "strong", "u"
     *
     * @param html 待过滤的字符串
     * @return 过滤后的字符串
     */
    public static String getSimpleHtml(String html) {
        return null == html ? null : Jsoup.clean(html, Whitelist.simpleText());
    }

    /**
     * 使用Whitelist.simpleText() 白名单，获取的 basic 内容
     * 允许的 tag："a", "b", "blockquote", "br", "cite", "code", "dd", "dl", "dt", "em",
     * "i", "li", "ol", "p", "pre", "q", "small", "span", "strike", "strong", "sub",
     * "sup", "u", "ul"
     *
     * @param html 待过滤的字符串
     * @return 过滤后的字符串
     */
    public static String getBasic(String html) {
        return null == html ? null : Jsoup.clean(html, Whitelist.basic());
    }

    /**
     * 使用Whitelist.basicWithImages() 白名单，获取的 basic with images 内容
     *
     * @param html 待过滤的字符串
     * @return 过滤后的字符串
     */
    public static String getBasicWithImages(String html) {
        return null == html ? null : Jsoup.clean(html, Whitelist.basicWithImages());
    }

    /**
     * 使用Whitelist.relaxed() 白名单，获取比较宽松的内容
     * 允许的 tag: "a", "b", "blockquote", "br", "caption", "cite", "code", "col",
     * "colgroup", "dd", "div", "dl", "dt", "em", "h1", "h2", "h3", "h4", "h5", "h6",
     * "i", "img", "li", "ol", "p", "pre", "q", "small", "span", "strike", "strong",
     * "sub", "sup", "table", "tbody", "td", "tfoot", "th", "thead", "tr", "u", "ul"
     *
     * @param html 待过滤的字符串
     * @return 过滤后的字符串
     */
    public static String getRelaxed(String html) {
        return null == html ? null : Jsoup.clean(html, Whitelist.relaxed());
    }

    /**
     * 使用指定的 Whitelist 进行过滤
     *
     * @param html      待过滤的字符串
     * @param whitelist 不过滤的标签
     * @return 过滤后的字符串
     */
    public static String getWithWhitelist(String html, Whitelist whitelist) {
        return null == html ? null : Jsoup.clean(html, whitelist);
    }

    /**
     * 使用指定的 tags 进行过滤
     *
     * @param html 待过滤的字符串
     * @param tags 需要过滤的标签
     * @return 过滤后的字符串
     */
    public static String getWithTags(String html, String... tags) {
        return null == html ? null : Jsoup.clean(html, Whitelist.none().addTags(tags));
    }

    /**
     * 获取第一个 img 的 src 属性值
     *
     * @param html 待过滤的字符串
     * @return 过滤后的字符串
     */
    public static String getFirstImgSrc(String html) {
        if (html == null) {
            return null;
        }
        Document doc = Jsoup.parseBodyFragment(html);
        Element image = doc.select("img").first();
        return null == image ? null : image.attr("src");
    }

}
