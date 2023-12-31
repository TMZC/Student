package login.servlet;

import impl.LoginImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.Operator;

public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LoginImpl loginImpl = new LoginImpl();
	Operator operator = new Operator();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		if (type.equals("login")) {
			if(request.getParameter("ope_name")!=""&&request.getParameter("ope_pwd")!="")
			{
			
			// 传页面的用户信息过来验证，并返回数据到客户端，剩下的由JS脚本作判断处理
			
			operator.setName(request.getParameter("ope_name"));
			operator.setPwd(request.getParameter("ope_pwd"));

			// 设置会话的过期时间为1分钟（以秒为单位）
			int sessionTimeoutInSeconds = 180;
			request.getSession().setMaxInactiveInterval(sessionTimeoutInSeconds);
			
			// 火狐必须要这个设置才能识别成字符串
			response.setContentType("text/plain");
			response.getWriter().write(loginImpl.login(request, operator));
			}
			else {
				response.getWriter().write("用户名和密码不能为空！");
			}

		} else if (type.equals("logout")) {
			// 注销后清除会话中的全部信息
			request.getSession().invalidate();

		} else if (type.equals("get_rol_id")) {
			// 获取登录的用户的角色ID
			Operator operator = (Operator) request.getSession().getAttribute(
					"log_operator");
			response.getWriter().write(operator.getRole().getId() + "");

		}
	}
}