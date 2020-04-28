package tagLibs;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * Classe TagLib para ocultar o input ID Cliente.  
*/
public class TagLibIDcliente extends TagSupport {
	private static final long serialVersionUID = 1L;

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();// returns the instance of JspWriter

		ServletRequest request = pageContext.getRequest();
		boolean ler = true;
		Integer clienteId = (Integer) request.getAttribute("clienteId");

		try {
			out.print("<input type=\"number\" class=\"form-control\" id=\"idClie\" value=\"" + clienteId
					+ "\" min=\"1\" placeholder=\"ID Cliente\" " + "name=\"idClie\"" + (ler ? "readonly" : "") + "/>");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return SKIP_BODY;
	}
}
