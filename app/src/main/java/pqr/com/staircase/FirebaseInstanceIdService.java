package pqr.com.staircase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by ibm on 19/11/2018.
 */

public class FirebaseInstanceIdService extends com.google.firebase.iid.FirebaseInstanceIdService {


    @Override
    public void onTokenRefresh() {
        String token = FirebaseInstanceId.getInstance().getToken();

        Log.d("token",token);

        AgentData.getInstance().setToken(token);
    }
}
