package ba.work.chbla.ba_eresamont.Interface;

import android.view.View;

import ba.work.chbla.ba_eresamont.Fragment.FirstFragment;

/**
 * Created by chbla on 27.11.2017.
 */

public interface IDAO {
    void ReadDBData_Firebase(View view,FirstFragment.OnHeadlineSelectedListener Callback);

}
