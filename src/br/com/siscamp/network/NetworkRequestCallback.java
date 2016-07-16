package br.com.siscamp.network;

public interface NetworkRequestCallback<T> {

	public void onRequestResponse(T response);
	public void onRequestError(Exception error);
	
}

