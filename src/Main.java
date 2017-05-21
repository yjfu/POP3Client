import java.util.Scanner;

/**
 * Created by yjfu on 2017/5/3.
 */
public class Main {
    static public void main(String[] args){
        PopClient pc = null;
        String popServer = null;
        String user = null;
        String psw = null;
        String msg = null;

        while(true){
            pc = new PopClient();
            System.out.println("请输入欲连接的pop服务器,或输入quit退出：");
            Scanner scanner = new Scanner(System.in);
            popServer = scanner.nextLine();
            if(popServer.equalsIgnoreCase("quit"))
                break;
            pc.setPopServer(popServer);
            if(!pc.connect()){
                System.out.println("连接失败！");
                continue;
            }

            System.out.println("请输入用户名：");
            user = scanner.nextLine();
            msg = pc.USER(user);
            PopClient.printMsg(msg);

            System.out.println("请输入密码：");
            psw = scanner.nextLine();
            if(pc.PASS(psw).startsWith("-")){
                System.out.println("用户名或密码错误！");
                continue;
            }
            PopClient.printMsg(msg);

            while(true) {
                System.out.println("请输入指令(list/retr/quit)：");
                String command = scanner.nextLine();
                if(command.equalsIgnoreCase("list")){
                    PopClient.printMsg(pc.LIST());
                }
                else if(command.equalsIgnoreCase("retr")){
                    System.out.println("请输入邮件id：");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    PopClient.printMsg(pc.RETR(id));
                }
                else if(command.equalsIgnoreCase("quit")){
                    PopClient.printMsg(pc.QUIT());
                    break;
                }
                else {
                    System.out.println("无效指令！");
                }
            }
        }
    }
}
