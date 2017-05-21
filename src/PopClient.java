import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Timer;

/**
 * Created by yjfu on 2017/5/3.
 */
public class PopClient {
    private Socket socket;
    private BufferedReader socketIn;
    private PrintStream socketOut;
    private String userName;
    private String psw;
    private String popServer;


    public void setUserName(String userName){
        this.userName = userName;
    }
    public void setPsw(String password){
        this.psw = password;
    }
    public void setPopServer(String popServer){
        this.popServer = popServer;
    }

    public boolean connect(){
        try{
            this.socket = new Socket(popServer,110);
            this.socketIn = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.socketOut = new PrintStream(this.socket.getOutputStream());
            this.socketIn.readLine();
            return true;
        }
        catch (Exception e){
            return false;
        }
    }
    public String LIST(){
        this.sentMassage("LIST");
        return this.getReply();
    }
    public String QUIT(){
        this.sentMassage("QUIT");
        return this.getReply();
    }
    public String RETR(int id){
        this.sentMassage("RETR "+id);
        return this.getReply();
    }
    public String USER(String userName){
        this.userName = userName;
        this.sentMassage("USER "+this.userName);
        return this.getReply();
    }

    public String PASS(String psw){
        this.psw = psw;
        this.sentMassage("PASS "+this.psw);
        return this.getReply();
    }
    static public void printMsg(String msg){
        System.out.println("pop服务器回复：");
        System.out.println(msg);
    }

    private String getReply(){
        String reply = "";
        try{
            reply += this.socketIn.readLine();
            while(this.socketIn.ready()) {
                reply += "\n"+this.socketIn.readLine();
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return reply;
    }

    private void sentMassage(String s){
        System.out.println("向pop服务器发送信息：");
        System.out.println(s);
        try{
            this.socketOut.println(s);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
