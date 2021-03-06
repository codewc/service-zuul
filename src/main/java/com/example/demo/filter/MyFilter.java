package com.example.demo.filter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 
 * @author wangchun
 * {@link TokenFilter}
 */
@Component
@Deprecated
public class MyFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(MyFilter.class);
	@Override
	@Deprecated
	public boolean shouldFilter() {
		return Boolean.FALSE;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURI().toString()));
		Object accessToken = request.getParameter("token");
		if (accessToken == null) {
			log.warn("token is empty");
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(401);
			try {
				ctx.getResponse().getWriter().write("token is empty ,我不允许你访问，至少给我一个值吧");
			} catch (Exception e) {
				// TODO: handle exception
			}
			return null;
		}
		log.info("ok");
		return null;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "pre";
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 0;
	}

}
