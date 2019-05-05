package demo.spring.boot.demospringboot.util.ssh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class SSHSDK {

    private static Logger LOGGER = LoggerFactory.getLogger(SSHSDK.class);

    public String CMD;

    /**
     * 模板方法+泛型 -> 处理逻辑延迟到调用者
     *
     * @param sh
     * @param callBack
     * @param <T>
     * @return
     */
    public <T> T executeLinuxCmd(String sh, CallBack<T> callBack) {
        String cmd = CMD + " " + sh;
        LOGGER.info("sh is :{}", cmd);
        Runtime run = Runtime.getRuntime();
        InputStream in = null;
        T result = null;
        try {
            Process process = run.exec(cmd);
            in = process.getInputStream();
            result = callBack.deal(in);
            in.close();
            process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 把字节转为List
     *
     * @param sh
     * @return
     * @throws IOException
     */
    public List<String> getResult(String sh) {
        return this.executeLinuxCmd(sh, in -> {
            Reader reader = new InputStreamReader(in);
            LineNumberReader lineNumberReader
                    = new LineNumberReader(reader);
            List<String> list = new ArrayList<>();
            String line;
            while ((null != (line = lineNumberReader.readLine()))) {
                list.add(line);
            }
            lineNumberReader.close();
            return list;
        });
    }

}
