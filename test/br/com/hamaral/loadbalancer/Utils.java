package br.com.hamaral.loadbalancer;

import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;

import br.com.hamaral.loadbalancer.model.Server;
import br.com.hamaral.loadbalancer.model.User;

@Ignore
public class Utils {

	public void validate(boolean result) {
		if (result) {
			System.out.println("Sucesso\n-------------------------");
		} else {
			System.out.println("Erro\n-------------------------");
		}
	}

	public String getFilePath() {
		return System.getProperty("user.dir") + "\\";
	}

	public List<String> getMockDataString() {
		List<String> data = new ArrayList<String>();
		data.add("4\n");
		data.add("2\n");
		data.add("1\n");
		data.add("3\n");
		data.add("linhaQueDeveSerDesconsiderada\n");
		data.add("0\n");
		data.add("1\n");
		data.add("0\n");
		data.add("1");
		return data;
	}

	public List<String> getMockDataStringFiltered() {
		List<String> data = new ArrayList<String>();
		data.add("4\n");
		data.add("2\n");
		data.add("1\n");
		data.add("3\n");
		data.add("0\n");
		data.add("1\n");
		data.add("0\n");
		data.add("1");
		return data;
	}

	public List<Integer> getMockDataInteger() {
		List<Integer> data = new ArrayList<Integer>();
		data.add(4);
		data.add(2);
		data.add(1);
		data.add(3);
		data.add(0);
		data.add(1);
		data.add(0);
		data.add(1);
		return data;
	}

	public List<Server> getMockDataServer() {
		List<User> usersData = new ArrayList<User>();
		usersData.add(new User(1, 1, 5));

		List<Server> serversData = new ArrayList<Server>();
		serversData.add(new Server(1, usersData, 2));
		return serversData;
	}

	public List<User> getMockDataListUsersPerTick() {
		List<User> usersData = new ArrayList<User>();
		usersData.add(new User(2, 2, 6));
		usersData.add(new User(3, 2, 6));
		usersData.add(new User(4, 2, 6));
		return usersData;
	}

}
