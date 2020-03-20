package test;

import javax.xml.bind.*;


public class Test {

	private static final String CAMINHO_ARQUIVO = "C:\\Users\\f0fp631\\Documents\\Produtos.xml";

	public static void main(String[] args) throws Exception {
				
		 JAXBContext jc = JAXBContext.newInstance(Messages.class);

	        Messages messages = new Messages();
	        messages.getMessages().add(new Message());
	        messages.getMessages().add(new Message());
	        messages.getMessages().add(new Message());
	        messages.getMessages().add(new Message());

	        Marshaller marshaller = jc.createMarshaller();
	        marshaller.setListener(new Marshaller.Listener() {

	            private int counter = 1;

	            public void beforeMarshal(Object object) {
	                if(object instanceof Message) {
	                    ((Message) object).setId(counter++);
	                }
	            }

	        });
	        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        marshaller.setAdapter(new IDAdapter());
	        marshaller.marshal(messages, System.out);
	    }
}
