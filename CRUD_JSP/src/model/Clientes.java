package model;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe responsável com as funções de manipulação do cliente com HashMap {@link Cliente}
 **/
public class Clientes {
	private Map<Integer, Cliente> clientes = new HashMap<>();
	private static int id;

	public static int clienteID() {
		return ++id;
	}

	public Map<Integer, Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(Map<Integer, Cliente> clientes) {
		this.clientes = clientes;
	}

	public static int getId() {
		return id;
	}

	public static void setId(int id) {
		Clientes.id = id;
	}

	public void adicionaCliente(Cliente c) {
		clientes.put(c.getId(), c);
	}

	public void excluirCliente(Integer id) {
		clientes.remove(id);
	}

	public Cliente getCliente(Integer id) {
		return clientes.get(id);
	}

}
