package demo.spring.boot.demospringboot.controller.pub;


import com.jcraft.jsch.JSchException;
import demo.spring.boot.demospringboot.util.ssh.Shell;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;


/**
 * 测试远程SSH
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "/ssh")
public class SSHController {


    @GetMapping(value = "/test")
    public ArrayList<String> test(
            @RequestParam(value = "ip", defaultValue = "39.107.236.187") String ip,
            @RequestParam(value = "username", required = false, defaultValue = "root") String username,
            @RequestParam(value = "password", required = false, defaultValue = "Ys20140913") String password,
            @RequestParam(value = "port", required = false, defaultValue = "6000") int port,
            @RequestParam(value = "cmd") String cmd
    ) {


        Shell shell = new Shell(ip, username, password, port);
        try {
            shell.execute(cmd);
        } catch (JSchException e) {
            e.printStackTrace();
        }
        ArrayList<String> standardOutput = shell.getStandardOutput();
        return standardOutput;

    }


}
