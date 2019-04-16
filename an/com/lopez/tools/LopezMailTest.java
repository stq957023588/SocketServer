package an.com.lopez.tools;
public class LopezMailTest {
	
	
	public static void main(String[] args) throws Exception{
//		String userName = "957023588@qq.com"; // 发件人邮箱
//		String password = ""; // 发件人密码
//		String smtpHost = "smtp.qq.com"; // 邮件服务器
 
		String to = "SixteenChapter@163.com"; // 收件人，多个收件人以半角逗号分隔
//		String cc = "33333333@qq.com"; // 抄送，多个抄送以半角逗号分隔
		String subject = "YMC题"; // 主题
		String body = "这是邮件的正文"; // 正文，可以用html格式的哟
 
		LopezEmail email = LopezEmail.defaultEntity(to, null, subject, body, null);
 
		email.send(); // 发送！

	}
}
