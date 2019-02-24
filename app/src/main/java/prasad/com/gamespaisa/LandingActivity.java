package prasad.com.gamespaisa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class LandingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i=new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
