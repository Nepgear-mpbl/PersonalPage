package cn.zhengzhaoyu.personalPage.common.kit;

import javax.servlet.http.HttpServletRequest;

public class IpKit {

    private static final String UNKNOWN = "unknown";
    private static final String[] HEADERS = new String[]{
            "x-forwarded-for", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "X-Real-IP"
    };

    private IpKit() {

    }

    public static String getRealIp(HttpServletRequest request) {
        int i = 0;
        String ip = request.getHeader(HEADERS[i]);
        while ((null == ip) || (0 == ip.length()) || UNKNOWN.equalsIgnoreCase(ip)) {
            if (i == HEADERS.length - 1) {
                ip = request.getRemoteAddr();
                break;
            }
            ip = request.getHeader(HEADERS[++i]);
        }
        return ip;
    }

}
