package io.itimetraveler.cube;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import io.itimetraveler.cube.view.CubeRotateLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            CubeRotateLayout.RotateAnimListener listener = new CubeRotateLayout.RotateAnimListener() {
                @Override
                public void onAnimationStart() {
                }
                @Override
                public void onAnimationEnd() {
                }
            };

            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPurgeable = true;
            opt.inInputShareable = true;
            opt.inPreferredConfig = Bitmap.Config.ARGB_4444;

            Bitmap left = BitmapFactory.decodeResource(getResources(), R.drawable.mi_wallpaper, opt);
            Bitmap right = BitmapFactory.decodeResource(getResources(), R.drawable.example, opt);
            startCubeRotateAnim(left, right, true, true, listener);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void startCubeRotateAnim(Bitmap switchInBitmap, Bitmap switchOutBitmap, boolean isLeftOrDownOut, boolean isLeftRightAnim,
                                    CubeRotateLayout.RotateAnimListener rotateAnimListener){
        final CubeRotateLayout cubeRotateLayout = (CubeRotateLayout) findViewById(R.id.switch_mode_cube_layout);
        if(cubeRotateLayout == null){
            return;
        }
        cubeRotateLayout.setSwitchInBitmap(switchInBitmap);
        cubeRotateLayout.setSwitchOutBitmap(switchOutBitmap);
        cubeRotateLayout.setAnimListener(rotateAnimListener);
        cubeRotateLayout.setAnimDuration(1000);
        cubeRotateLayout.startRotateAnim(isLeftOrDownOut, isLeftRightAnim);
    }
}
