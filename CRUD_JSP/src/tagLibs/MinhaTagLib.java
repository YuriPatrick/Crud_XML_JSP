package tagLibs;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * TagLib para ocultar botão.  
*/
public class MinhaTagLib extends TagSupport {

	private static final long serialVersionUID = 1L;

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();// returns the instance of JspWriter

		ServletRequest request = pageContext.getRequest();
		boolean ler = true;
		Integer produtoId = (Integer) request.getAttribute("produtoId");

		try {
			out.print("<input type=\"number\" class=\"form-control\" id=\"idProd\" value=\"" + produtoId
					+ "\" min=\"1\" placeholder=\"ID Produto\" " + "name=\"idProd\"" + (ler ? "readonly" : "") + "/>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return SKIP_BODY;
	}

}