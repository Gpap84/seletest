/*
This file is part of the Seletest by Giannis Papadakis <gpapadakis84@gmail.com>.

Copyright (c) 2014, Giannis Papadakis <gpapadakis84@gmail.com>
All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice,
      this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice,
      this list of conditions and the following disclaimer in the documentation
      and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.automation.seletest.core.services.network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UIKeyboardInteractive;
import com.jcraft.jsch.UserInfo;

/**
 * SSHUtils class.
 * @author Giannis Papadakis(mailTo:gpapadakis84@gmail.com)
 *
 */
@Component
@Slf4j
public class SSHUtils {

    /**
     * Execute command
     * @param host
     * @param user
     * @param passwd
     * @param cmd
     * @return String the reply of remote session
     */
    public String execCmd (String host, String user, String passwd, String cmd){
        String replyy = "";
        String reply = null;
        try{
            JSch jsch=new JSch();
            Session session=jsch.getSession(user, host, 22);
            UserInfo ui = new MyUserInfo(passwd);
            session.setUserInfo(ui);
            session.setTimeout(600000);
            session.connect();
            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand(cmd);
            channel.setInputStream(null);
            InputStreamReader in = new InputStreamReader(channel.getInputStream());
            OutputStreamWriter out = new OutputStreamWriter(channel.getOutputStream());
            BufferedWriter bw = new BufferedWriter(out);
            BufferedReader br = new BufferedReader(in);
            channel.connect();
            while ((reply = br.readLine()) != null) {
                bw.write(reply);
                replyy=replyy+"\n"+reply;
                bw.flush();
                Thread.sleep(100);
            }
            while(true){
                if(channel.isClosed()){
                    break;
                } try{
                    Thread.sleep(1500);
                } catch(Exception ee){
                }
            }
            in.close();
            out.close();
            br.close();
            bw.close();
            channel.disconnect();
            session.disconnect();
        }
        catch(Exception e){
            log.error("ERROR , Possible no connection with : "+user+" "+passwd+ " "+host+"\n\t\t please check LAN and vpn connection or host");
        }
        return replyy;
    }
}

class MyUserInfo implements UserInfo, UIKeyboardInteractive{

    @Getter @Setter public String password;

    public MyUserInfo(String passwd) {
        password = passwd;
    }

    @Override
    public boolean promptYesNo(String str){
        return true;
    }

    @Override
    public String getPassphrase(){
        return null;
    }

    @Override
    public boolean promptPassphrase(String message){
        return true;
    }

    @Override
    public boolean promptPassword(String message){
        return true;
    }

    @Override
    public void showMessage(String message){

    }

    @Override
    public String[] promptKeyboardInteractive(String destination,
            String name,
            String instruction,
            String[] prompt,
            boolean[] echo){
        return new String[]{password};
    }


}
