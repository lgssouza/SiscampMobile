package br.com.siscamp.network;

import android.app.Application;
import br.com.siscamp.network.NetworkQueue;

public class GoogleVolleyApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		NetworkQueue.getInstance().init(this);
	}

}
