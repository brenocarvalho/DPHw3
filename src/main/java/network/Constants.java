package network;

public interface Constants {
	public final long MAX_TIMEOUT = 100;
	public final int MAX_WAITING_SERVER_TIME = 100, MAX_WAITING_ITERATIONS = 4, LISTEN_INTERVAL = 50;
	public enum SERVER_STATUS {Avaible, Busy, NoResponse, Dead};
}
