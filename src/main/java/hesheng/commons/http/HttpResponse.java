package hesheng.commons.http;

import org.apache.http.Header;
import org.apache.http.StatusLine;

/**
 * http请求返回实体类
 * @author admin
 *
 */
public class HttpResponse {
	/**
	 * 返回状态
	 */
	private StatusLine status;

	/**
	 * 返回头
	 */
	private Header[] header;
	
	/**
	 * 返回体
	 */
	private String body;



	public StatusLine getStatus() {
		return status;
	}

	public void setStatus(StatusLine status) {
		this.status = status;
	}

	public Header[] getHeader() {
		return header;
	}

	public void setHeader(Header[] header) {
		this.header = header;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}


	
}
