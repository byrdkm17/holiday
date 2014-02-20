package cc.aliza.production.holiday.commons.ext;

import com.jfinal.render.Render;
import com.jfinal.render.RenderException;
import com.jfinal.render.RenderFactory;

import java.io.*;

/**
 * Created by byrdkm17@gmail.com on 14-1-6.
 * Thanks
 */
public class ImageRender extends Render {
    private String imageName;
    private File file;

    public ImageRender(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public void render() {

        if (imageName != null) {
            file = new File(imageName);
        }

        if (file == null || !file.isFile() || file.length() > Integer.MAX_VALUE) {
            RenderFactory.me().getErrorRender(404).setContext(request, response).render();
            return;
        }

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(file));
            outputStream = response.getOutputStream();
            byte[] buffer = new byte[1024];
            for (int n = -1; (n = inputStream.read(buffer)) != -1; ) {
                outputStream.write(buffer, 0, n);
            }
            outputStream.flush();
        } catch (Exception e) {
            throw new RenderException(e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
