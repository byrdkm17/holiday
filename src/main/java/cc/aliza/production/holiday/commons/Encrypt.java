package cc.aliza.production.holiday.commons;

import com.jfinal.log.Logger;
import org.apache.commons.lang.StringUtils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;

/**
 * Created by byrdkm17@gmail.com on 14-1-15.
 * Thanks
 */
public class Encrypt {
    private static Logger logger = Logger.getLogger(Encrypt.class);

    private final static String HASH_MD5 = "md5";
    private final static String HASH_SHA1 = "sha-1";

    private static Encrypt instance = new Encrypt();

    public static String md5(String input) {
        return instance.encrypt(input, HASH_MD5);
    }

    public static String sha1(String input) {
        return instance.encrypt(input, HASH_SHA1);
    }

    private String encrypt(String input, String hashName) {

        String output = StringUtils.EMPTY;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(hashName);
            messageDigest.update(input.getBytes("UTF-8"));
            byte[] bytes = messageDigest.digest();
            output = hex(bytes);
        } catch (Exception e) {
            if (logger.isErrorEnabled()) {
                logger.error(e.getMessage(), e);
            }
        }
        return output;
    }

    private String hex(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte aByte : bytes) {
            stringBuilder.append(Integer.toHexString((aByte & 0xFF) | 0x100).substring(1, 3));
        }
        return stringBuilder.toString();
    }

    public static String URLEncoder(String s, String enc) {
        try {
            return URLEncoder.encode(s, enc);
        } catch (Exception e) {
            return "unknown";
        }
    }

    public static String URLDecoder(String s, String enc) {
        try {
            return URLDecoder.decode(s, enc);
        } catch (Exception e) {
            if (e.getCause() == null) {
                return e.getMessage();
            } else {
                return e.getCause().getMessage();
            }
        }
    }
}
